package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	private static final String HOST = "localhost";
	private static final int PORT = 1234;  
	private static Scanner keyboardIn = new Scanner(System.in);
	private static String nickname = null;
	private static Socket connection = null;
	private static Scanner socketIn = null;
	private static PrintWriter socketOut = null;
	private static String response = null;

	public static void main(String args[]){
		
	     try{
	       connectWithServer();         
	       setNickname();
	       setTheStreams();
	       
	       do{	    	   
		     receiveMessage();
		     sendMessage();
	       } while (!response.equalsIgnoreCase("exit"));    
	     } catch(IOException e) {
	       e.printStackTrace();
	     }
	     finally{
	       try{
	         closeTheSockets();
	       } catch(IOException e){
	         System.err.println(" Can not close the connection");
	       }
	     }
	}

	private static void connectWithServer() throws UnknownHostException, IOException {
		System.out.println("Connecting with server");
	     try{
	         connection = new Socket(HOST, PORT);
	       } catch(ConnectException e){
	         System.err.println(" Can not connect with the server!");
	         return;
	       }
	}

	private static void closeTheSockets() throws IOException {
	    System.out.println(" Closing the connection...");
		if(socketIn!=null) socketIn.close();
	    if(socketOut!=null) socketOut.close();
		if(connection!=null) connection.close();
	}

	private static void sendMessage() {
		socketOut.flush();
		System.out.print("Me: ");
		response = keyboardIn.nextLine();
		socketOut.println(nickname + ": " + response.toLowerCase());
	}

	private static void receiveMessage() {
		String answer = socketIn.nextLine();
		System.out.println(answer);
	}

	private static void setNickname() {
		System.out.println(" Please enter your nickname!" );
	    nickname = keyboardIn.nextLine();
	}

	private static void setTheStreams() throws IOException {
		socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
	    socketOut = new PrintWriter(connection.getOutputStream(), true);
	}
}
