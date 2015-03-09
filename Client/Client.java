package Client;
import Message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private static final String HOST = "192.168.1.105";
	private static final int PORT = 1234;  
	private static ObjectInputStream socketIn = null;
	private static ObjectOutputStream socketOut = null;
	private static Socket connection = null;

	public static void main(String args[]){
			             
	       try {
	    	connectWithServer();
			setTheStreams();
			
		    ClientChatSwing.main(args);
		       
		    do{	    	   
			  receiveMessage();
		    } while (true);   
		       
		} catch (IOException e) {
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

	static ObjectOutputStream getOutput() {
		return socketOut;
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

	private static void receiveMessage() {
		Message answer = null;
		try {
			answer = (Message) socketIn.readObject();		
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}	
		ClientChatSwing.writeMessageInChatArea(answer);
	}

	private static void setTheStreams() throws IOException {
	    socketOut = new ObjectOutputStream(connection.getOutputStream());
	    socketIn =  new ObjectInputStream(connection.getInputStream());	
	}
}
