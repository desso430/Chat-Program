package Server;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFrame extends JFrame {
	private static final long serialVersionUID = -2556780849697422434L;
	private JPanel contentPane = new JPanel();
	private static JTextArea statusArea = new JTextArea();
	private JButton startButton = new JButton("Start server");
	private JButton stopButton = new JButton("Stop server\r\n");
	private boolean isStarted = false;
	private Thread server;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerFrame frame = new ServerFrame(" Server ");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ServerFrame(String title) {
		super(title);
		buildFrame();	
	
	}

	private void buildFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);	
		setResizable(false);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		setStatusArea();
		setStartButton();	
		setStopButton();
	}

	private void setStartButton() {
		startButton.setBounds(66, 227, 114, 23);
		contentPane.add(startButton);
		
		// add behaviour
		startButton.addActionListener(new ActionListener() {
							
		   @Override
		   public void actionPerformed(ActionEvent e) {
			   if(!isStarted) {
				 server =  new Thread(new Server());
				 server.start();
			     isStarted = true;
			  }
		   }
		});
	}

	private void setStopButton() {
		stopButton.setBounds(273, 227, 107, 23);
		contentPane.add(stopButton);
		
		// add behaviour
		stopButton.addActionListener(new ActionListener() {
									
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isStarted) {
				try{
				   Server.getServerSocket().close();
			     } catch(IOException e1){
			    	 writeMessageInStatusArea("Error: Can not close the socket! ");
				 }
				   server.stop();
				   writeMessageInStatusArea(" Server was stoped!");
				   isStarted = false;
			   }				 
			}
		});
	}

	private void setStatusArea() {
		JLabel statusLabel = new JLabel("Server status:");
		statusLabel.setBounds(23, 23, 107, 14);
		contentPane.add(statusLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(23, 38, 384, 165);
		contentPane.add(scrollPane);
		
		
		statusArea.setEditable(false);
		statusArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		scrollPane.setViewportView(statusArea);
		statusArea.setLineWrap(true);
		statusArea.setForeground(Color.CYAN);
	}
	
	static void writeMessageInStatusArea(String message) {
		if(message != null) {
			statusArea.append(message + "\n");
		}
	}
}
