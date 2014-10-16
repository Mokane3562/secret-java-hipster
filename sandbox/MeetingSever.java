

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * <code>Usage: Java MeetingSever "port number" "thread pool size"</code>
 * 
 * A meeting server exists to serve client requests from a meeting client. These
 * requests take the form of a course at MUN, to which the server returns the
 * meeting times for each section. Whenever a client connects to this server,
 * its request is handled by a <code>RequestHandler</code> from the thread pool.
 * The <code>RequestHandler</code> then returns meeting times for each section
 * and exits the connection.
 * 
 * @author mokane3562
 */
public class MeetingSever implements Runnable {
	private final ServerSocket serverSocket;
	private final ExecutorService pool;
	static private Logger logger = Logger.getLogger("MeetingSever");

	/**
	 * Creates a new Meeting Sever(sic). The server runs until terminated by ^C.
	 * 
	 * @param port
	 *            the port number, or 0 to use a port number that is
	 *            automatically allocated
	 * @param poolSize
	 *            the number of threads in the pool
	 * @throws IOException
	 *             if an I/O error occurs when opening the socket
	 */
	//TODO Find a nicer way to close the server.
	public MeetingSever(int port, int poolSize) throws IOException {
		serverSocket = new ServerSocket(port);
		pool = Executors.newFixedThreadPool(poolSize);
	}

	/**
	 * Prints all threads running in the current threadgroup to the logger.
	 */
	public void dumpThreads() {
		int count = Thread.activeCount();
		Thread[] threads = new Thread[count];
		int num = Thread.enumerate(threads);
		for (int i = 0; i < num; i++) {
			logger.info(threads[i].toString());
		}
	}

	/**
	 * {@inheritDoc} Starts the server and opens it for connections.
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			logger.info("Opening connection to server on port " + serverSocket.getLocalPort());
			while(true) {
				Socket sock = serverSocket.accept();
				logger.info("Client has connected at " + sock);
				pool.execute(new RequestHandler(sock));
			}
		} catch (IOException e) {
			pool.shutdown();
			logger.warning(e.getMessage());
		}
	}

	/**
	 * Shuts down the thread pool in a semi-orderly fashion.
	 */
	public void shutdownAndAwaitTermination() {
		pool.shutdown(); // Disable new tasks from being submitted
		try {
			// Wait a while for existing tasks to terminate
			if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
				pool.shutdownNow(); // Cancel currently executing tasks
				// Wait a while for tasks to respond to being cancelled
				if (!pool.awaitTermination(60, TimeUnit.SECONDS))
					logger.warning("Pool did not terminate");
			}
		} catch (InterruptedException ie) {
			// (Re-)Cancel if current thread also interrupted
			pool.shutdownNow();
			// Preserve interrupt status
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * {@inheritDoc} Returns a <code>String</code> representation of the
	 * <code>MeetingSever</code>.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MeetingSever [serverSocket=" + serverSocket + ", pool=" + pool + "]";
	}

	/**
	 * Each instance of <code>RequestHandler</code> is designed to handle a
	 * single client. When a client connects to the server, a
	 * <code>RequestHandler</code> from the thread pool is sent to complete the
	 * clients request.
	 * 
	 * @author mokane3562
	 */
	protected static class RequestHandler implements Runnable {
		private final Socket socket;

		/**
		 * Creates a new client request handler from a client connection.
		 * 
		 * @param socket
		 *            the socket connecting the client
		 */
		RequestHandler(Socket socket) {
			this.socket = socket;
		}

		/**
		 * {@inheritDoc} Executes the service for the client.
		 * 
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() { // Read and service request on socket
			try {
				// I/O
				BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter wt = new PrintWriter(socket.getOutputStream(), true);

				// Get the DOM otg
				String line = rd.readLine();

				Document slotBookDoc = DomUtil.parseDoc("slot-book.xml");

				Element slotBookRoot = slotBookDoc.getDocumentElement();
				NodeList list = slotBookRoot.getElementsByTagName("course");

				// Iterate through the list of courses matching the client query
				ArrayList<Node> matchesList = MeetingSever.binarySearch(list, line, 0, list.getLength() - 1);
				for (Node node : matchesList) {
					Element element = (Element) node;

					String code = element.getAttribute("subject");
					String cNum = element.getAttribute("number");
					String section = element.getAttribute("seq");
					String m1 = new String();
					String m2 = new String();

					// Obtain a list of meeting times for that course
					NodeList meetElementList = element.getElementsByTagName("meeting");

					// Organize meeting times into at most 2 distinct times
					Element meetElement1 = (Element) meetElementList.item(0);
					if (meetElement1 != null) {
						m1 = meetElement1.getAttribute("day") + " " + meetElement1.getAttribute("start") + " " + meetElement1.getAttribute("end") + " " + meetElement1.getAttribute("building") + " " + meetElement1.getAttribute("room");

						/*Iterate through every meeting time after the first.
						 * If it compares to the same starting time as the first,
						 * append it's meeting day to the first.
						 * Otherwise, mark it down as the second main meeting time.
						 * If a second main meeting time already exists,
						 * append the day of the current meeting time to the second main meeting time.
						 * This does not consider the possibility of a third main meeting time.
						 * For the record, this is a case of "my code works and I don't really know why"
						 */
						for (int j = 1; j < meetElementList.getLength(); j++) {
							Element meetElementJ = (Element) meetElementList.item(j);
							if (meetElement1.getAttribute("start").equals(meetElementJ.getAttribute("start"))) { //
								m1 = meetElementJ.getAttribute("day") + " " + m1;
							} else if (!meetElement1.getAttribute("start").equals(meetElementJ.getAttribute("start")) && !m2.equals("")) {
								m2 = meetElementJ.getAttribute("day") + " " + m2;
								continue;// Because we only need to see if m2 exists once, we skip the check every time after
							} else {
								m2 = meetElementJ.getAttribute("day") + " " + meetElementJ.getAttribute("start") + " " + meetElementJ.getAttribute("end") + " " + meetElementJ.getAttribute("building") + " " + meetElementJ.getAttribute("room");
							}
						}
					}
					wt.printf("%s %s-%s\n" + "%s\n" + "%s\n\n", code, cNum, section, m1, m2);
					wt.flush(); // ask it to be sent
					System.out.printf("" + "%s %s-%s\n" + "%s\n" + "%s\n\n", code, cNum, section, m1, m2);

				}

				// Cleanup
				logger.info("Closing connection to " + socket);
				socket.close();
				rd.close();
				wt.close();
				logger.info("Done\n");
			} catch (IOException e) {
				logger.warning(e.getMessage());
			} catch (ParserConfigurationException e) {
				logger.warning(e.getMessage());
			} catch (SAXException e) {
				logger.warning(e.getMessage());
			}
		}
	}

	/**
	 * Returns a list containing every course node matching a given key. The key
	 * takes the form of a course name and number.
	 * 
	 * @param list
	 *            the nodelist of courses to be searched
	 * @param key
	 *            the search term
	 * @param indexMin
	 *            the offset for the search start
	 * @param indexMax
	 *            the maximum range to search to
	 * @return an arraylist of course nodes matching the search term
	 */
	private static ArrayList<Node> binarySearch(NodeList list, String key, int indexMin, int indexMax) {
		ArrayList<Node> resultsList = new ArrayList<Node>();
		if (indexMax < indexMin)
			return resultsList;
		else {
			int middle = indexMin + ((indexMax - indexMin) / 2); // made sure this is right
			Element course = (Element) list.item(middle);
			String subject = course.getAttribute("subject");
			String number = course.getAttribute("number");
			String toCompare = subject + " " + number;

			if (key.compareTo(toCompare) < 0)
				return binarySearch(list, key, indexMin, middle - 1);
			else if (key.compareTo(toCompare) > 0)
				return binarySearch(list, key, middle + 1, indexMax);
			else {
				// Some Turing Machine type jazz to check for extra slots
				String toComp;
				do {
					middle--;
					Element crs = (Element) list.item(middle);
					String subj = crs.getAttribute("subject");
					String num = crs.getAttribute("number");
					toComp = subj + " " + num;
				} while(key.equals(toComp));
				do {
					middle++;
					Element crs = (Element) list.item(middle);
					String subj = crs.getAttribute("subject");
					String num = crs.getAttribute("number");
					toComp = subj + " " + num;
					if (key.equals(toComp))
						resultsList.add(list.item(middle));
				} while(key.equals(toComp));

				return resultsList;
			}
		}
	}

	public static void main(String[] args) {
		try {
			// Args handler
			int port = 0;
			int poolSize = 0;
			try {
				port = Integer.parseInt(args[0]);
				poolSize = Integer.parseInt(args[1]);
			} catch (Exception e) {
				System.out.println("Usage: Java MeetingSever <port number> <thread pool size>");
				System.exit(0);
			}

			// Start the server
			MeetingSever server = new MeetingSever(port, poolSize);
			Thread serverThread = new Thread(server);
			serverThread.start();
		} catch (IOException e) {
			logger.warning(e.getMessage());
		}
	}
}
