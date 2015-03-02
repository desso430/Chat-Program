package Server;

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
	private static String nickname = null;
	private static Socket connection = null;
	private static ObjectInputStream socketIn = null;
	private static ObjectOutputStream socketOut = null;
	private static String computerName = null;
	private static String response = null;


	public static void main(String args[]) {
		  Scanner keyboardIn = new Scanner(System.in);
		  
		    while(true){
		      try {
		        WaitingForConnection();
		        setNickname(keyboardIn);		    
		        setStreams();

		        socketOut.writeObject(new Message(nickname, "client", "hello", LocalDate.now(), LocalTime.now()));
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
		Message answer = null;
		try {
			answer = (Message) socketIn.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		System.out.println(answer.getFrom() + ":  " + answer.getContent());
	}

	private static void sendMessage(Scanner keyboardIn) {
		try {
			socketOut.flush();
			System.out.print("Me: ");
			response = keyboardIn.nextLine();
			socketOut.writeObject(new Message(nickname, "client", response.toLowerCase(), LocalDate.now(), LocalTime.now()));
		} catch (IOException e) {
			e.printStackTrace();
	    }
	}

	private static void setStreams() throws IOException {
	    socketOut = new ObjectOutputStream(connection.getOutputStream());
	    socketIn =  new ObjectInputStream(connection.getInputStream());	
	}

	private static void setNickname(Scanner keyboardIn) {
		System.out.println(" Please enter your nickname!" );
		nickname = keyboardIn.nextLine();
	}

	private static void WaitingForConnection() throws IOException {
		serverSocket = new ServerSocket(PORT);
		System.out.println(" Waiting for someone to connect...");
		connection = serverSocket.accept();
		computerName = InetAddress.getLocalHost().getHostName();
		System.out.println(" Connected with:" + computerName);
	}
}
