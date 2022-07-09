package play;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import utils.Communicator;

public class RaiseCall extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField callField;
	
	public RaiseCall(int call) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel callLabel = new JLabel("Your call");
		callLabel.setBounds(69, 70, 66, 14);
		contentPane.add(callLabel);
		
		callField = new JTextField();
		callField.setText("" + call);
		callField.setBounds(49, 95, 86, 20);
		contentPane.add(callField);
		callField.setColumns(10);
		callField.requestFocus(true);
		
		JButton confirmButton = new JButton("Confirm");
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				int callValue = Integer.parseInt(callField.getText());
				if(call <= callValue) {
				Communicator.send("" + callValue);
				dispose();
				}
				}
				catch(Exception ex) {
					
				}
			}
		});
		confirmButton.setBounds(47, 136, 89, 23);
		contentPane.add(confirmButton);
		setLocationRelativeTo(null);
		setSize(200, 250);
		setVisible(true);
	}
}
