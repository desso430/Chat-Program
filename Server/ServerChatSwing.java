package Server;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ServerChatSwing extends JFrame {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5414027196690424525L;
	private JPanel contentPane = new JPanel();
	private static JTextArea chatArea;
	private JScrollPane chatAreaScrollPane;
	private JTextArea inputMessageArea;
	private JScrollPane inputMessageAreaScrollPane;
	private JButton sendButton;
	private static String nickname = "user_2";

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
		chatArea = new JTextArea();
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
				if(!getMessage().equals("")) {
					writeInChatArea(getTimeStamp() + nickname + " : " + getMessage() + "\n");
					inputMessageArea.setText("");
				}
			}
		});
	}
	
	private static void writeInChatArea(String message) {
		chatArea.append("\n " + message);
	}
	
	private static void writeMessageInChatArea(Message message) {
		String data = message.getData().getDayOfMonth() + " " +  message.getData().getMonth() + " ";
		String time = message.getTime().getHour() + ":" + message.getTime().getMinute() + " ";
		writeInChatArea(data + " " + time + message.getFrom() + " : " + message.getContent());
	}
	
	private String getTimeStamp() {
		String data = LocalDate.now().getDayOfMonth() + " " +  LocalDate.now().getMonth() + " ";
		String time = LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + " ";
		
	  return "(" + data + " " + time + ") ";
	}
	
	public String getMessage() {
		return inputMessageArea.getText();
	}
}
