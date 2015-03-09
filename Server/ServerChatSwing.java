package Server;
import Message.Message;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.swing.border.EmptyBorder;
import javax.swing.*;


public class ServerChatSwing extends JFrame {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5414027196690424525L;
	private JPanel contentPane = new JPanel();
	private static JTextArea chatArea = new JTextArea();
	private JScrollPane chatAreaScrollPane;
	private JTextArea inputMessageArea;
	private JScrollPane inputMessageAreaScrollPane;
	private JButton sendButton;
	private static String nickname = "Deso";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerChatSwing frame = new ServerChatSwing("Server Chat");
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
	public ServerChatSwing(String ProgramName) {
		super(ProgramName);
		buildFrame();
	}
	
	private void buildFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 531, 430);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		setChatArea();
		setInputMessageArea();
		setSendButton();
	}

	private void setChatArea() {
		chatArea.setEditable(false);
		chatAreaScrollPane = new JScrollPane();
		chatAreaScrollPane.setBounds(10, 11, 483, 276);
		contentPane.add(chatAreaScrollPane);
		chatAreaScrollPane.setViewportView(chatArea);
	}

	private void setInputMessageArea() {
		inputMessageArea = new JTextArea();
		inputMessageArea.setToolTipText("Type your message here...");
		inputMessageAreaScrollPane = new JScrollPane();
		inputMessageAreaScrollPane.setBounds(10, 326, 374, 37);
		contentPane.add(inputMessageAreaScrollPane);
		inputMessageAreaScrollPane.setViewportView(inputMessageArea);
		
		inputMessageArea.addKeyListener( new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) { }
		
			@Override
			public void keyTyped(KeyEvent e) { }	

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					sendMessage(Server.getOutput());
			    }
			}
		});	
	}

	private void setSendButton() {
		sendButton = new JButton("Send");
		sendButton.setToolTipText("Send message...\r\n\r\n");
		sendButton.setBounds(404, 326, 89, 37);
		contentPane.add(sendButton);
		
		// add behaviour
		sendButton.addActionListener(new ActionListener() {
					
			@Override
		    public void actionPerformed(ActionEvent e) {
			   sendMessage(Server.getOutput());
			}
		});
	}


	static void writeMessageInChatArea(Message message) {
		if(message != null) {	
		  if(message.getContent() != null )
			chatArea.append("\n " + message.toString());
		}
	}
	
	private String getMessage() {
		return inputMessageArea.getText();
	}	
	
	private void sendMessage(ObjectOutputStream socketOut) {
		if(!getMessage().equals("")) {
		   Message message = new Message(nickname, "client",  getMessage());
		   send(message, socketOut);
		   writeMessageInChatArea(message);
		   inputMessageArea.setText("");
		} 
	}
	
	private void send(Message message, ObjectOutputStream socketOut) {		
		try {
			socketOut.flush();	
			socketOut.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
