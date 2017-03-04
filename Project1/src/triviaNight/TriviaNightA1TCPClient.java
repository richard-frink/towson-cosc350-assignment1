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

		//Checking user input
		while(!checkInput(sentence)) {
			System.out.print("Not in format required. Enter the server and port for the web server as follows 'server/port#' : ");
			sentence = inFromUser.readLine();
		}

		//Establishing connection to server
		Socket clientSocket = new Socket("localhost", 6789);

		//Creating out and in streams
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		//Writing input to server
		outToServer.writeBytes(sentence + '\n');

		//Retrieving info from the server
		String serverOutput = inFromServer.readLine();

		//Putting retrieved info into the proper format
		modifiedSentence = correctDataFromServer(serverOutput);

		//Printing retrieved info
		System.out.println("FROM SERVER:\n" + modifiedSentence);
		clientSocket.close();
	}

	//checks if the user's sentence is in form server/port
	private static boolean checkInput(String input) {
		int slash = input.indexOf('/');
		if(slash == -1) return false;
		else if(slash == 0) return false;
		else {
            for(int i = 0; i < input.substring(0, slash).length(); i++){
                if (!Character.isLetter(input.charAt(i)))
                    if(input.charAt(i) != '.')
                        return false;
            }
            for(int i = slash + 1; i < input.length(); i++){
                if (!Character.isDigit(input.charAt(i))) return false;
            }
		    return true;
        }
	}

	//Reads through the server's output and makes the entered string correct (creates new lines from '-_-_')
	private static String correctDataFromServer(String oldSentence) {
		String editedSentence = "";
		int tempIndex = 0;

		for(int i = 0; i < oldSentence.length(); i++) {
			if(oldSentence.charAt(i) == '-') {
				if (i + 4 < oldSentence.length()) {
					if(oldSentence.charAt(i+1) == '_' && oldSentence.charAt(i+2) == '-' && oldSentence.charAt(i+3) == '_') {
						editedSentence += oldSentence.substring(tempIndex, i) + '\n';
						tempIndex = i + 4;
						i = i + 3;
					}
				}
			}
		}

		return editedSentence;
	}
}