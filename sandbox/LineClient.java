

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class LineClient {
	public static void main(String[] args) {
		try {
			// Args handler
			String host = new String();
			int port = 0;
			try {
				host = args[0];
				port = Integer.parseInt(args[1]);
			} catch (Exception e) {
				System.out.println("Usage: Java LineClient <hostname> <port number>");
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
			String line;
			while((line = in.readLine()) != null) {
				if (line.equals("quit"))
					break;
				wt.println(line);
				wt.flush(); // ask it to be sent
				System.out.println(rd.readLine());
			}

			// Cleanup
			System.out.println("Press enter to close connection...");
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
