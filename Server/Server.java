package Server;

import Message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;


public class Server {
	private static final int PORT = 1234;
	private static ServerSocket serverSocket = null;
	private static Socket connection = null;
	private static ObjectInputStream socketIn = null;
	private static ObjectOutputStream socketOut = null;
	private static String computerName = null;

	public static void main(String args[])  { 
		try {
			WaitingForConnection();
			setStreams();
			
			ServerChatSwing.main(args);
			socketOut.writeObject(new Message("deso", "client", "hello"));
					      	
		    while(true) {
		       receiveMessage();
		    }
		 } catch (IOException e) {
			e.printStackTrace();
		 }    	
		finally {
		    try{
		       closeTheSockets();
		    } catch(IOException e){
		      System.err.println(" Can not close the socket! ");
		    }
	     }
     }
	
	static ObjectOutputStream getOutput() {
		return socketOut;
	}

	private static void closeTheSockets() throws IOException {
		if (socketIn!=null) socketIn.close();
		if (socketOut!=null) socketOut.close();
	    if (connection!=null) connection.close();
		if (serverSocket!=null) serverSocket.close();
	}

	private static void receiveMessage() {
		Message answer = null;
		try {
			answer = (Message) socketIn.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		ServerChatSwing.writeMessageInChatArea(answer);
	}

	private static void setStreams() throws IOException {
	    socketOut = new ObjectOutputStream(connection.getOutputStream());
	    socketIn =  new ObjectInputStream(connection.getInputStream());	
	}

	private static void WaitingForConnection() throws IOException {
		serverSocket = new ServerSocket(PORT);
		System.out.println(" Waiting for someone to connect...");
		connection = serverSocket.accept();
		computerName = InetAddress.getLocalHost().getHostName();
		System.out.println(" Connected with:" + computerName);
	}
}
