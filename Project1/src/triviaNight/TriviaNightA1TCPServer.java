package triviaNight;

import java.io.*;
import java.net.*;

public class TriviaNightA1TCPServer {
	public static void main(String argv[]) throws Exception {
		String clientSentence;
		String capitalizedSentence;
		String textFromWeb = "Could not connect to provided server address.";

		String clientServer = "www.towson.edu"; //client entered server // default values
		int clientPort = 80; 					//client entered port   // default values

		ServerSocket welcomeSocket = new ServerSocket(6789);
		while (true) {
			Socket connectionSocket = welcomeSocket.accept();
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			clientSentence = inFromClient.readLine();
			System.out.println("Received: " + clientSentence);


			capitalizedSentence = clientSentence.toUpperCase() + '\n';
			outToClient.writeBytes(capitalizedSentence);

			/**
			//Following should perform the httpget request
			//then read the entire page to a string
			URLConnection connection = null;
			String targetURL = "www.towson.edu";
			URL url = new URL(targetURL);
			connection = (URLConnection) url.openConnection();
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			textFromWeb = response.toString();
			rd.close();
			*/


		}
	}
}
