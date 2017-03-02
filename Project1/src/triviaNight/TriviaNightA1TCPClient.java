package triviaNight;

import java.io.*;
import java.net.*;

public class TriviaNightA1TCPClient {
	public static void main(String argv[]) throws Exception {
		String sentence;
		String modifiedSentence;

		//Reading in the server/port info from client
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter in the server and port for the web server as follows 'server/port#' : ");
		sentence = inFromUser.readLine();
		System.out.println(sentence);

		Socket clientSocket = new Socket("localhost", 6789);

		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		outToServer.writeBytes(sentence + '\n');
		modifiedSentence = inFromServer.readLine();
		System.out.println("FROM SERVER: " + modifiedSentence);
		clientSocket.close();
	}
}
