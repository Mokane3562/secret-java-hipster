

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class HttpRequestViewer {

	public static void sendNotFound(PrintStream ps) {
		ps.println("HTTP/1.0 404 Not Found");
		ps.println("Connection: close");
		ps.println(); // end of header
		ps.flush();
	}

	public static void sendReply(String path, PrintStream ps) {
		ps.println("HTTP/1.0 200 OK");
		ps.println("Connection: close");
		ps.println("Content-Type: text/html; charset=utf-8");
		ps.println(); // end of header
		ps.println("<html>");
		ps.println("<body>");
		ps.println("<pre>" + "path: " + path + "</pre>");
		ps.println("<pre>" + new Date() + "</pre>");
		ps.println("</body>");
		ps.println("</html>");
		ps.flush();
	}
	
	public static void main(String[] args) {
		try {
			int port = Integer.parseInt(args[0]);
			ServerSocket serverSocket = new ServerSocket(port);
			while(true) {
				Socket socket = serverSocket.accept();
				Scanner connectionIn = new Scanner(socket.getInputStream());
				PrintStream connectionOut = new PrintStream(socket.getOutputStream(), false);
				
				// get request
				if (!connectionIn.hasNextLine()) {
					connectionOut.close();
					connectionIn.close();
					socket.close();
					continue;
				}
				String line = connectionIn.nextLine();
				String[] words = line.split("\\s+");
				
				// print request header on console
				System.out.println("Request header");
				System.out.println(line);
				while(connectionIn.hasNextLine()) {
					line = connectionIn.nextLine();
					if (line.length() == 0)
						break; // end of header
					System.out.println(line);
				}
				if (words[1].equals("/favicon.ico")) { // possible bug?
					sendNotFound(connectionOut);
				} else {
					sendReply(words[1], connectionOut);
				}
				connectionOut.close();
				connectionIn.close();
				socket.close();
			}
		} catch (IOException e) {
			System.out.println("error: " + e);
		}
	}
}