

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Server provides a service.
 * 
 * @author mokane3562
 */
public class LineServer implements Runnable {
	private final ServerSocket serverSocket;
	private final ExecutorService pool;
	static private Logger logger = Logger.getLogger("LineServer");

	/**
	 * @param port
	 *            the port number, or 0 to use a port number that is
	 *            automatically allocated
	 * @param poolSize
	 *            the number of threads in the pool
	 * @throws IOException
	 *             if an I/O error occurs when opening the socket
	 */
	public LineServer(int port, int poolSize) throws IOException {
		serverSocket = new ServerSocket(port);
		pool = Executors.newFixedThreadPool(poolSize);
	}

	public void dumpThreads() {
		int count = Thread.activeCount();
		Thread[] threads = new Thread[count];
		int num = Thread.enumerate(threads);
		for (int i = 0; i < num; i++) {
			System.out.println(threads[i]);
		}
	}

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
			e.printStackTrace();
		}
	}

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

	@Override
	public String toString() {
		return "LineServer [serverSocket=" + serverSocket + ", pool=" + pool + "]";
	}

	/**
	 * Handles a single request to the server.
	 * 
	 * @author mokane3562
	 */
	class RequestHandler implements Runnable {
		private final Socket socket;

		RequestHandler(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() { // Read and service request on socket
			try {
				// I/O
				BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter wt = new PrintWriter(socket.getOutputStream(), true);

				// Find meeting times
				String line;
				while((line = rd.readLine()) != null) {
					line = line.toUpperCase();
					wt.println(line);
					System.out.println(line);
				}

				// Cleanup
				logger.info("Closing connection to " + socket);
				socket.close();
				rd.close();
				wt.close();
				logger.info("Done\n");
			} catch (IOException e) {
				e.printStackTrace();
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
				System.err.println("Wrong usage.");
				System.out.println("Usage: Java LineServer <port number> <thread pool size>");
				System.exit(0);
			}

			LineServer server = new LineServer(port, poolSize);
			Thread serverThread = new Thread(server);
			serverThread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
