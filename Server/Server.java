package Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class Server implements Runnable {
	
	private static final int PORT = 1234;
	private static ServerSocket serverSocket = null;
	private static Socket connection = null;
	
	@Override
	public void run() {
		try {						
			serverSocket = new ServerSocket(PORT);
			ServerFrame.writeMessageInStatusArea(" Server is started....");
			while(true) {
				ServerFrame.writeMessageInStatusArea(" Waiting for someone to connect...");
				connection = serverSocket.accept();
				String userName = InetAddress.getLocalHost().getHostName();
				ServerFrame.writeMessageInStatusArea(" Start chat with:" + userName);
				new Thread(new ClientThred(userName, connection)).start();
			}
		 } catch (IOException e) {
			 ServerFrame.writeMessageInStatusArea(" Error: " + e.getMessage());
		 }   	
	}
	
	public static ServerSocket getServerSocket() {
		return serverSocket;
	}
}
