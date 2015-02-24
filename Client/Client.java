package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	 public static void main(String args[]){
	     Socket connection = null;
	     Scanner socketIn = null;
	     PrintWriter socketOut = null;
	     String response = null;
	     Scanner keyboardIn = new Scanner(System.in);
	     int port = 1234;
	     String host = "localhost";

	     try{
	       System.out.println("Connecting with server");
	       try{
	         connection = new Socket(host, port);
	       }
	       catch(ConnectException e){
	         System.err.println(" Can not connect with the server!");
	         return;
	       }
	       System.out.println("Connected with the server successfully!");

	       socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
	       socketOut = new PrintWriter(connection.getOutputStream(), true);
	       do{	    	   
		     String answer = socketIn.nextLine();
		     System.out.println("Server : " + answer);
		         
	         socketOut.flush();
	         System.out.print("Write message to server: ");
	         response = keyboardIn.nextLine();
	         socketOut.println(response.toLowerCase());
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
