package Server;

import Message.Message;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import javax.swing.*;


public class ServerChatSwing extends JFrame {
	private static final long serialVersionUID = 5414027196690424525L;
	private Queue<Message> chatHistory =  new ConcurrentLinkedQueue<Message>();
	private ObjectOutputStream socketOut = null;
	private JPanel contentPane = new JPanel();
	private JTextArea chatArea = new JTextArea();
	private JScrollPane chatAreaScrollPane = new JScrollPane();
	private JTextArea inputMessageArea = new JTextArea();
	private JScrollPane inputMessageAreaScrollPane = new JScrollPane();
	private JButton sendButton = new JButton("Send");
	private String nickname = "Deso";

	/**
	 * Create the frame.
	 */
	public ServerChatSwing(String ProgramName, ObjectOutputStream socketOut) {
		super(ProgramName);
		this.socketOut = socketOut;
		buildFrame();
	}
	
	public Queue<Message> getChatHistory() {
		return chatHistory;
	}

	private void buildFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 531, 430);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		
		setChatArea();
		setInputMessageArea();
		setSendButton();
	}

	private void setChatArea() {
		chatArea.setEditable(false);
		chatArea.setLineWrap(true);
		DefaultCaret caret = (DefaultCaret)chatArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		chatAreaScrollPane.setBounds(10, 11, 483, 276);
		contentPane.add(chatAreaScrollPane);
		chatAreaScrollPane.setViewportView(chatArea);
	}

	private void setInputMessageArea() {
		inputMessageArea.setLineWrap(true);
		inputMessageArea.setToolTipText("Type your message here...");	
		inputMessageAreaScrollPane.setBounds(10, 326, 374, 37);
		contentPane.add(inputMessageAreaScrollPane);
		inputMessageAreaScrollPane.setViewportView(inputMessageArea);
		
		inputMessageArea.addKeyListener( new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					 String message = getMessage();
					 inputMessageArea.setText("");
					 sendMessage(message); 
			    }
			}
		
			@Override
			public void keyTyped(KeyEvent e) { }	

			@Override
			public void keyReleased(KeyEvent e) { 
				 if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				     inputMessageArea.setText("");
				 }
			}
		});	
	}

	private void setSendButton() {
		sendButton.setToolTipText("Send message...\r\n\r\n");
		sendButton.setBounds(404, 326, 89, 37);
		contentPane.add(sendButton);
		
		// add behaviour
		sendButton.addActionListener(new ActionListener() {
					
			@Override
		    public void actionPerformed(ActionEvent e) {
				 String message = getMessage();
				 inputMessageArea.setText("");
				 sendMessage(message); 
			}
		});
	}
	
    void writeMessageInChatArea(Message message) {
    	if(message != null && message.getContent() != null) {	
  		  if(!message.getContent().equals("") )
  			chatArea.append(message.toString() + "\n ");
//  		chatHistory.add(message);
  		}
	}
    
    void printError(String error) {
    	if(error != null && !error.equals("")) {
    		chatArea.append("Error: " + error);
    	}
    }
    
    void printProgramMessage(String message) {
    	if(message != null && !message.equals("")) {
    		chatArea.append("Program status: " + message);
    	}
    }
	
	private String getMessage() {
		return inputMessageArea.getText();
	}	
	
	private void sendMessage(String messageToSend) {
		if(!messageToSend.equals("")) {
		   Message message = new Message(nickname, "client", messageToSend);
		   send(message);
		   writeMessageInChatArea(message);			
		} 
	}
	
	private void send(Message message) {		
		try {
			socketOut.flush();	
			socketOut.writeObject(message);
		} catch (IOException e) {
			printError(e.getMessage()); 
		}
	}
}
