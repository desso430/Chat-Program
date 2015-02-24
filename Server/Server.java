package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	private static final int PORT = 1234;
	private static ServerSocket serverSocket = null;
	private static String nickname = null;
	private static Socket connection = null;
	private static Scanner socketIn = null;
	private static String answer = null;
	private static String computerName = null;
	private static String response = null;
	private static PrintWriter socketOut = null;

	public static void main(String args[]) {
		  Scanner keyboardIn = new Scanner(System.in);
		  
		    while(true){
		      try {
		        WaitinForConnection();
		        setingNickname(keyboardIn);		    
		        setStreams();

		        socketOut.println(nickname + ": " + " hello ");
		        do{	  
		          receiveMessage();
		          sendMessage(keyboardIn);				  	        
		        } while(!response.equalsIgnoreCase("exit"));
		        System.out.println("Closing the connection with " + computerName);
		      } catch(IOException e) {
		        e.printStackTrace();
		      }
		      finally{
		        try{
		          closeTheSockets();
		        } catch(IOException e){
		          System.err.println(" Can not close the socket! ");
		        }
		     }
		 }
	 }

	private static void closeTheSockets() throws IOException {
		if (socketIn!=null) socketIn.close();
		if (socketOut!=null) socketOut.close();
	    if (connection!=null) connection.close();
		if (serverSocket!=null) serverSocket.close();
	}

	private static void receiveMessage() {
		socketOut.flush();
		answer = socketIn.nextLine();
	    System.out.println(answer);
	}

	private static void sendMessage(Scanner keyboardIn) {
		System.out.print("Me: ");
	    response = keyboardIn.nextLine();
		socketOut.println(nickname + ": " +response.toLowerCase());
	}

	private static void setStreams() throws IOException {
		socketOut = new PrintWriter(connection.getOutputStream(), true);
		socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
	}

	private static void setingNickname(Scanner keyboardIn) {
		System.out.println(" Please enter your nickname!" );
		nickname = keyboardIn.nextLine();
	}

	private static void WaitinForConnection() throws IOException {
		serverSocket = new ServerSocket(PORT);
		System.out.println(" Waiting for someone to connect...");
		connection = serverSocket.accept();
		computerName =  connection.getInetAddress().getHostName();
		System.out.println(" Connected with:" + computerName);
	}
}
