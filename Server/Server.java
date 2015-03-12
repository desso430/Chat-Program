package Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
	private static final int PORT = 1234;

	public static void main(String args[])  {
		ServerSocket serverSocket = null;
		Socket connection = null;
		
		try {						
			serverSocket = new ServerSocket(PORT);				
			while(true) {
				System.out.println(" Waiting for someone to connect...");
				connection = serverSocket.accept();
				String userName = InetAddress.getLocalHost().getHostName();
				System.out.println(" Connected with:" + userName);
				new Thread(new ClientThred(userName, connection)).start();
			}
		 } catch (IOException e) {
			e.printStackTrace();
		 }    	
		finally {
		    try{
		       if (serverSocket!=null) serverSocket.close();
		    } catch(IOException e){
		      System.err.println(" Can not close the socket! ");
		    }
	     }
     }
}
