package Client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;

public class ConnectionFrame extends JFrame {
	private static final long serialVersionUID = 4560177553030479503L;
	private JPanel contentPane = new JPanel();
	private JPanel panel = new JPanel();
	private JTextField IPField = new JTextField();
	private JTextField portField = new JTextField();
	private JTextField nicknameField = new JTextField();
	private JButton ConnectButton = new JButton("Connect");
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConnectionFrame frame = new ConnectionFrame(" Connect ");
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
	public ConnectionFrame(String title) {
		super(title);
		buildFrame();
	}

	private void buildFrame() {
		setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 373, 313);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		setHintLabel(panel);
		setIP(panel);
		setPort(panel);
		setNicknameField(panel);
		setConnectButton(panel);
	}

	private void setHintLabel(JPanel panel) {
		JLabel hintLabel = new JLabel("Enter IP address, port and your nickname to connect.");
		hintLabel.setFont(new Font("Sitka Small", Font.PLAIN, 11));
		hintLabel.setBounds(25, 22, 312, 28);
		panel.add(hintLabel);
	}

	private void setIP(JPanel panel) {
		JLabel IPLabel = new JLabel("IP Address:");
		IPLabel.setBounds(42, 61, 76, 14);
		IPField.setBounds(42, 77, 263, 20);
		panel.add(IPLabel);
		panel.add(IPField);
		IPField.setColumns(10);
	}

	private void setPort(JPanel panel) {
		JLabel portLabel = new JLabel("Port:");
		portLabel.setBounds(42, 108, 76, 14);
		portField.setBounds(42, 123, 263, 20);
		panel.add(portLabel);
		panel.add(portField);
		portField.setColumns(10);
	}

	private void setNicknameField(JPanel panel) {
		JLabel NIcknameLabel = new JLabel("Nickname:");
		NIcknameLabel.setBounds(42, 154, 76, 14);
		nicknameField.setBounds(42, 169, 263, 20);
		panel.add(NIcknameLabel);
		panel.add(nicknameField);
		nicknameField.setColumns(10);
	}
	
	private void setConnectButton(JPanel panel) {
		ConnectButton.setBounds(126, 214, 89, 23);
		panel.add(ConnectButton);
	}
}
