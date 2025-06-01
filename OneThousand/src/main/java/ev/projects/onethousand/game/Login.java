package ev.projects.onethousand.game;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import ev.projects.onethousand.utils.SMTPClient;

public class Login extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JTextField nameField;
	private JTextField ipField;
	private JTextField portField;
	private JTextField emailField;
	private JPasswordField passwordField;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				new Login();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public Login() {
		setFont(new Font("Times New Roman", Font.PLAIN, 11));
		getContentPane().setLayout(null);
		
		JLabel nameLabel = new JLabel("Name");
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		nameLabel.setBounds(47, 34, 284, 14);
		getContentPane().add(nameLabel);
		
		nameField = new JTextField();
		nameField.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		nameField.setBounds(47, 59, 284, 20);
		getContentPane().add(nameField);
		nameField.setColumns(10);
		
		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				String ip = ipField.getText();
				String portString = portField.getText();
				if(name.length() > 0 && ip.length() > 0 && portString.length() > 0) {
					int port = Integer.parseInt(portString);
					dispose();
					Main.IP = ip;
					Main.port = port;
					String email = emailField.getText();
					String password = String.valueOf(passwordField.getPassword());
					if(email.length() > 0 && password.length() > 0) SMTPClient.setEmail(email, password);
					new Main(name);
				}
			}
		});
		sendButton.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		sendButton.setBounds(47, 379, 284, 23);
		getContentPane().add(sendButton);
		
		JLabel ipLabel = new JLabel("Server IP");
		ipLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ipLabel.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		ipLabel.setBounds(163, 90, 46, 14);
		getContentPane().add(ipLabel);
		
		ipField = new JTextField();
		ipField.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		ipField.setBounds(47, 115, 284, 20);
		getContentPane().add(ipField);
		ipField.setColumns(10);
		
		JLabel portLabel = new JLabel("Port");
		portLabel.setHorizontalAlignment(SwingConstants.CENTER);
		portLabel.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		portLabel.setBounds(42, 144, 289, 14);
		getContentPane().add(portLabel);
		
		portField = new JTextField();
		portField.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		portField.setBounds(47, 169, 284, 20);
		getContentPane().add(portField);
		portField.setColumns(10);
		
		JLabel emailLabel = new JLabel("<html>Email (optional - this will be used if you want to save your game by sending pictures to your email)</html>");
		emailLabel.setHorizontalAlignment(SwingConstants.CENTER);
		emailLabel.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		emailLabel.setBounds(64, 200, 247, 44);
		getContentPane().add(emailLabel);
		
		emailField = new JTextField();
		emailField.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		emailField.setBounds(47, 255, 284, 20);
		getContentPane().add(emailField);
		emailField.setColumns(10);
		
		JLabel passwordLabel = new JLabel("<html>Email password (optional. However it is needed if email is entered)</html>");
		passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		passwordLabel.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		passwordLabel.setBounds(102, 289, 172, 36);
		getContentPane().add(passwordLabel);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		passwordField.setBounds(47, 336, 284, 20);
		getContentPane().add(passwordField);
		Dimension size = new Dimension(400, 500);
		setPreferredSize(size);
		setResizable(false);
		setTitle("Thousand - Login");
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
