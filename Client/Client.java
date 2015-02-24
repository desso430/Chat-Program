package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	 private static final String HOST = "localhost";
	private static final int PORT = 1234;

	public static void main(String args[]){
	     Scanner keyboardIn = new Scanner(System.in);
	     String nickname = null;
	     Socket connection = null;
	     Scanner socketIn = null;
	     PrintWriter socketOut = null;
	     String response = null;

	     try{
	       System.out.println("Connecting with server");
	         try{
	           connection = new Socket(HOST, PORT);
	         }
	           catch(ConnectException e){
	           System.err.println(" Can not connect with the server!");
	           return;
	         }
	       System.out.println("Connected with the server successfully!");
	       System.out.println(" Please enter your nickname!" );
	       nickname = keyboardIn.nextLine();

	       socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
	       socketOut = new PrintWriter(connection.getOutputStream(), true);
	       do{	    	   
		     String answer = socketIn.nextLine();
		     System.out.println(answer);
		         
	         socketOut.flush();
	         response = keyboardIn.nextLine();
	         socketOut.println(nickname + ": " + response.toLowerCase());
	       }
	       while (!response.equalsIgnoreCase("exit"));
	       System.out.println(" Closing the connection...");
	     }
	     catch(IOException e) {
	       e.printStackTrace();
	     }
	     finally{
	       try{
	         if(socketIn!=null) socketIn.close();
	         if(socketOut!=null) socketOut.close();
	         if(connection!=null) connection.close();
	       }
	       catch(IOException e){
	         System.err.println(" Can not close the connection");
	       }
	     }
	}
}
