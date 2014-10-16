import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * <code>Usage: Java MeetingClient 'hostname' 'port number' 'course subject' 'course number'</code>
 * 
 * A meeting client is a user who connects to a meeting server service. The user
 * connects to the server with the hostname, port number, course subject, and
 * course number as command line arguments. The server then finds each course
 * matching the clients query and returns meeting times for each section.
 * 
 * @author mokane3562
 */
public class MeetingClient {
	public static void main(String[] args) {
		try {
			// Args handler
			String host = new String();
			int port = 0;
			String subject = new String();
			String courseNumber = new String();
			try {
				host = args[0];
				port = Integer.parseInt(args[1]);
				subject = args[2];
				courseNumber = args[3];
			} catch (Exception e) {
				System.out.println("Usage: Java MeetingClient <hostname> <port number> <course subject> <course number>");
				System.exit(0);
			}

			// Connect to server
			InetAddress serverIP = InetAddress.getByName(host);
			Socket sock = new Socket(serverIP, port);

			// I/O
			BufferedReader rd = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			PrintWriter wt = new PrintWriter(sock.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

			// Computationz
			wt.printf("%s %s\n", subject, courseNumber);
			wt.flush(); // ask it to be sent
			String line;
			while((line = rd.readLine()) != null) {
				System.out.println(line);
			}

			// Cleanup
			System.out.printf("Closing connection to %s\n", sock);
			sock.close();
			rd.close();
			wt.close();
			System.out.print("Done, press enter to continue...");
			in.readLine();
			in.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
