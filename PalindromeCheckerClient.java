/* Jacob Bourgoine and Jacob Richardson
 * CSC419
 * Client for user to enter a string, send it to a server,and
 * have the server determine if string is a palindrome or not.
 * 4/20/2019
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class PalindromeCheckerClient {

	// variables
    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;
    private static int port = 1200;
    private static String address = "localhost";

    // Main method
    public static void main(String[] args) throws Exception {

    	// Checks number of arguments.
    	if(args.length > 2) {
    		System.out.println("Error. Too many arguments.");
    		System.exit(0);
    	}
    	// If 2 arguments, then the port is set to the first and address to the second
    	else if(args.length == 2) {
    		port = Integer.parseInt(args[0]);
    		address = args[1];
    	}
    	// If one argument, port is set to argument and address is kept as "localhost"
    	else if(args.length == 1) {
    		port = Integer.parseInt(args[0]);
    	}

	    PalindromeCheckerClient client = new PalindromeCheckerClient();
	    Scanner sc = new Scanner(System.in);
	    String str = "-1";
	    // connect to server
	    client.startConnection(address, port);
	    while(true) {
	    	// Get string to send to server
	    	System.out.println("Enter a string to check for a palindrome: ");
	    	str = sc.nextLine();
	    	// If user presses Enter without any string entered, stop connection
	    	if(str.trim().isEmpty())
	    		stopConnection();
	    	// Get response from server
	    	String response = client.sendString(str);
	    	// Tell user the response
	    	if(response.trim().equals("y"))
	    		System.out.println("\nThe string:\n" + str + "\nis a palindrome.\n");
	    	else
	    		System.out.println("\nThe string:\n" + str + "\nis not a palindrome.\n");
	    }
	}
    // Method connects to the server
    public void startConnection(String ip, int port) throws Exception{
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    // Method sends String to server to check and gets a response back
    public String sendString(String s) throws Exception{
        out.println(s);
        String resp = in.readLine();
        return resp;
    }

    // Method ends connection to the server and exits
    public static void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        System.out.println("Have a nice day!");
        System.exit(1);
    }
}