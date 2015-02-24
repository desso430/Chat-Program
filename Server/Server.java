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

	public static void main(String args[]) {
		  Scanner keyboardIn = new Scanner(System.in);
		  ServerSocket serverSocket = null;
		  String nickname = null;
		  Socket connection = null;
		  Scanner socketIn = null;
	      String answer = null;
	      String name = null;
	      String response = null;
		  PrintWriter socketOut = null;

		    while(true){
		      try {
		        serverSocket = new ServerSocket(PORT);
		        System.out.println(" Waiting for someone to connect...");
		        connection = serverSocket.accept();
		        System.out.println(" Connected with:" + connection.getInetAddress().getHostName());

		        System.out.println(" Please enter your nickname!" );
			    nickname = keyboardIn.nextLine();
			    
		        socketOut = new PrintWriter(connection.getOutputStream(), true);
		        socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));

		        name =  connection.getInetAddress().getHostName();
		        System.out.println("start chating");
		        do{	        			        
				  response = keyboardIn.nextLine();
				  socketOut.println(nickname + ": " +response.toLowerCase());
				  
		          socketOut.flush();
		          answer = socketIn.nextLine();
		          System.out.println(answer);
		        }
		        while(!response.equalsIgnoreCase("exit"));
		        System.out.println("Closing the connection with " + name);
		      }
		      catch(IOException e) {
		        e.printStackTrace();
		      }
		      finally{
		        try{
		          if (socketIn!=null) socketIn.close();
		          if (socketOut!=null) socketOut.close();
		          if (connection!=null) connection.close();
		          if (serverSocket!=null) serverSocket.close();
		        }
		        catch(IOException e){
		          System.err.println(" Can not close the socket! ");
		        }
		      }
		  }
	 }
}
