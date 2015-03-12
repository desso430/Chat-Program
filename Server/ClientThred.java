package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Message.Message;

public class ClientThred implements Runnable {
	
	private Socket connection = null;
	private ObjectInputStream socketIn = null;
	private ObjectOutputStream socketOut = null;
	private String frameName;
	private ServerChatSwing frame;
	
	public ClientThred(String frameName, Socket connection) {
		this.connection = connection;
		this.frameName = frameName;
		setStreams();
	}
	
	@Override
	public void run() {	
		frame = new ServerChatSwing(frameName, socketOut);
		frame.setVisible(true);

		sendFirstMessage();

		while(true) {
		  try {
			receiveMessage();
		  } catch (ClassNotFoundException | IOException e) {
			frame.printProgramMessage(e.getMessage());
			closeTheSockets();
//			new SaveChatHistory(frame.getChatHistory()).saveHistory();
			break;
		  }
	   }					
	}
	
	private void sendFirstMessage() {
		Message firstMessage = new Message("deso", "client", "hello");
		try {
			socketOut.writeObject(firstMessage);
			frame.writeMessageInChatArea(firstMessage);
		} catch (IOException e) {
			frame.printError(e.getMessage());
		}	
	}
	
	private void receiveMessage() throws ClassNotFoundException, IOException {
		Message answer = (Message) socketIn.readObject();
		frame.writeMessageInChatArea(answer);				
	}
	
	private void setStreams() {
	    try {
			socketOut = new ObjectOutputStream(connection.getOutputStream());
			socketIn =  new ObjectInputStream(connection.getInputStream());	
		} catch (IOException e) {
			frame.printError(e.getMessage()); 
		}   
	}
	
	private void closeTheSockets()  {
	  try {
		if (socketIn!=null) socketIn.close();
		if (socketOut!=null) socketOut.close();
		if (connection!=null) connection.close();
	   } catch (IOException e) {
		   frame.printError(e.getMessage());
      }
	}
}
