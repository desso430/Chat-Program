package Client;
import Message.Message;
import Server.SaveChatHistory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;

public class Client {
	private static final String HOST = "localhost";
	private static final int PORT = 1234;  

	public static void main(String args[]){
		 ObjectInputStream socketIn = null;
		 ObjectOutputStream socketOut = null;
		 Socket connection = null;
         
	       try {	   
	    	  System.out.println(" Connecting with server... ");
	   	       try{
	   	         connection = new Socket(HOST, PORT);
	   	        } catch(ConnectException e){
	   	         ClientChatSwing.printError(" Can not connect with the server! " + e.getMessage());
	   	         return;
	   	        }
	    	
	    	// set the streams
	    	socketOut = new ObjectOutputStream(connection.getOutputStream());
	  	    socketIn =  new ObjectInputStream(connection.getInputStream());	
  	    
	  	  // start the frame
	         ClientChatSwing.main(socketOut);
	         
		    while(true) {   		
		    	try {
					receiveMessage(socketIn);
				} catch (ClassNotFoundException e) {
					 ClientChatSwing.printProgramMessage(e.getMessage());
//					 new SaveChatHistory(ClientChatSwing.getChatHistory()).saveHistory();
					 break;
				}
		    }  		       
		} catch (IOException e) {
			ClientChatSwing.printError(e.getMessage());
		}
	     finally{
	       try{
	   		if(socketIn!=null) socketIn.close();
	   	    if(socketOut!=null) socketOut.close();
	   		if(connection!=null) connection.close();
	       } catch(IOException e){
	    	  ClientChatSwing.printError(" Can not close the connection! \n" + e.getMessage());
	       }
	     }
	}

	private static void receiveMessage(ObjectInputStream socketIn) throws ClassNotFoundException, IOException {
		Message answer = (Message) socketIn.readObject();
		ClientChatSwing.writeMessageInChatArea(answer);
	}
}
