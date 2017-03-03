package triviaNight;

import java.io.*;
import java.net.*;

public class TriviaNightA1TCPServer {
	public static void main(String argv[]) throws Exception {
		String clientSentence;
		String textFromWeb = "Could not connect to provided server address.";

		String clientWebServer = "www.towson.edu"; //client entered server // default value
		int clientPort = 80; 					   //client entered port   // default value

		ServerSocket welcomeSocket = new ServerSocket(6789);
		while (true) {
			Socket connectionSocket = welcomeSocket.accept();
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			clientSentence = inFromClient.readLine();

			//Separating client server and ports
			clientWebServer = getWebServer(clientSentence);
			clientPort = getWebPort(clientSentence);

			System.out.println("Received: " + clientSentence);

            //Creating websocket
            Socket clientWebSocket = new Socket(clientWebServer, clientPort);

            //Connecting to address
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientWebSocket.getOutputStream())));
            out.println("GET /index.html HTTP/1.0");
            out.println();
            out.flush();

            //Reading line by line HTML of web server
            BufferedReader inFromWeb = new BufferedReader(new InputStreamReader(clientWebSocket.getInputStream()));
            StringBuilder fromWeb = new StringBuilder();
            String line;
            while((line = inFromWeb.readLine()) != null){
                fromWeb.append(line + '\n');
            }
            textFromWeb = fromWeb.toString();

            clientWebSocket.close();


            textFromWeb = textFromWeb.toUpperCase() + '\n';

            outToClient.writeBytes(textFromWeb);

		}
	}

    private static String getWebServer(String sentence){
        int slash = sentence.indexOf('/');
        return sentence.substring(0, slash);
    }

    private static int getWebPort(String sentence){
        int slash = sentence.indexOf('/');
        String num = sentence.substring(slash + 1, sentence.length());
        return Integer.parseInt(num);
    }
}
