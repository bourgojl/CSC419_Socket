/* Jacob Bourgoine
 * CSC419
 * Server for a client to connect to and determine if the
 * string that the user sent is a palindrome or not.
 * 4/20/2019
 */

import java.net.*;
import java.io.*;

public class PalindromeCheckerServer {

	// variables
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private static int port;
	private String str = "-1";

	// Main method
	public static void main(String[] args) throws Exception {

		// Check for an argument. Set port to argument if there is one, else it is 1200
		if(args.length > 1) {
			System.out.println("Error. Too many arguments.");
			System.exit(0);
		}
		else if(args.length == 1) {
			// Don't use ports below 1024
			if(Integer.parseInt(args[0]) < 1024) {
				System.out.println("Error. Reserved port number. Please use port 1024 or above");
				System.exit(1);
			}
			port = Integer.parseInt(args[0]);
		}
		else
			port = 1200;
		PalindromeCheckerServer server=new PalindromeCheckerServer();
		// start server
		server.start();
		}
	// Method starts the server
	public void start() {
		try{
       		serverSocket = new ServerSocket(port);
        	clientSocket = serverSocket.accept();
        	while(true) {
        		out = new PrintWriter(clientSocket.getOutputStream(), true);
        		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        		// Reads input from client. If null, then stops server
        		str = in.readLine();
        		if(str.trim().isEmpty())
        			stop();
        		// Trims the input string
        		String temp = trimStr(str);
        		// Checks input string to see if it is a palindrome
        		boolean isPalindrome = checkPalindrome(temp);
        		// Outputs if it is a palindrome to client
        		if(isPalindrome)
        			out.println("y");
        		else
        			out.println("n");
        	}
        }catch(Exception e){
			e.printStackTrace();
		}
    }
		// Closes server
        public void stop() throws Exception{
	        in.close();
	        out.close();
	        clientSocket.close();
	        serverSocket.close();
	        System.out.println("Connection to client ended");
	    }

        // Method removes spaces and characters that are not numbers or letters
	    public String trimStr(String str) {
			String temp = str.toLowerCase();
			String returnStr = "";

			for(int i = 0; i<temp.length();i++) {
				if((temp.charAt(i) >= '0' && temp.charAt(i) <= '9') || (temp.charAt(i) >= 'a' && temp.charAt(i) <= 'z')) {
					returnStr +=temp.charAt(i);
				}
			}
			return returnStr;
		}

	    // Checks string to see if it is a palindrome
		public static boolean checkPalindrome(String str){
				int j = str.length() - 1;
				for(int i = 0; i < str.length();i++) {
					if(str.charAt(i) != str.charAt(j))
						return false;
					else
						j--;
				}
				return true;
		}
}
