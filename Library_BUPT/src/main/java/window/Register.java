package window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import database.UpdateUser;

// Set Register GUI
public class Register {

	// Label
	private JLabel registerLabel = new JLabel("    Register:");
	private JLabel userIDLabel = new JLabel("     userID:");
	private JLabel userNameLabel = new JLabel("     userName:");
	private JLabel passwordLabel = new JLabel("    Password:");
	private JLabel passEqualLabel = new JLabel("Confirm Pass:");

	// String
	private String userID;
	private String userName;
	private String password;
	private String passwordEqual;

	// Font
	private Font fontTitle = new Font("Bauhaus 93", Font.BOLD, 40);
	private Font fontLabel = new Font("方正粗黑宋简体", Font.BOLD, 25);
	private Font fontField = new Font("宋体", Font.BOLD, 20);

	// userIDField
	private JTextField userIDField = new JTextField(18);
	private JTextField userNameField = new JTextField(18);
	private JPasswordField passwordField = new JPasswordField(18);
	private JPasswordField passEuqalField = new JPasswordField(18);

	// button
	private JButton registerButton = new JButton("Register");
	private JButton returnButton = new JButton("Return");

	// Conponment size
	private Dimension dimension = new Dimension(100, 40);

	// Set panel
	private JPanel returnPanel = new JPanel();
	private JPanel userIDPanel = new JPanel();
	private JPanel userNamePanel = new JPanel();
	private JPanel passwordPanel = new JPanel();
	private JPanel passEqualPanel = new JPanel();
	private JPanel registerPanel = new JPanel();

	// Frame
	private JFrame registerFrame = new JFrame("Register");

	public Register() {

		registerFrame.setSize(500, 700);
		registerFrame.setLayout(null);
		registerFrame.setLocationRelativeTo(null);

		// Change window icon
		Toolkit t = Toolkit.getDefaultToolkit();
		Image image = t.getImage("img\\top.jpg");
		registerFrame.setIconImage(image);

		SetFontColorBound();

		SetTransparent();

		addEvent();
		// Change background
		Icon i = new ImageIcon("img\\register1.jpg");
		JLabel Label = new JLabel(i);
		Label.setBounds(0, 0, 500, 700);

		registerFrame.add(Label);
		registerFrame.setResizable(false);
		registerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		registerFrame.setVisible(true);
	}

	private void SetFontColorBound()
	{
		// Set Font and Color
		registerLabel.setFont(fontTitle);
		userIDLabel.setFont(fontLabel);
		userNameLabel.setFont(fontLabel);
		passwordLabel.setFont(fontLabel);
		passEqualLabel.setFont(fontLabel);	
		registerButton.setFont(fontLabel);
		userIDField.setFont(fontField);
		userNameField.setFont(fontField);
		passwordField.setFont(fontField);
		passEuqalField.setFont(fontField);
		returnButton.setFont(fontLabel);
		
		//set color
		userIDField.setForeground(Color.cyan);
		userNameField.setForeground(Color.cyan);
		passwordField.setForeground(Color.cyan);
		passEuqalField.setForeground(Color.cyan);
		registerButton.setForeground(Color.orange);
		registerButton.setBackground(Color.cyan);		
		returnButton.setForeground(Color.orange);
		returnButton.setBackground(Color.cyan);
		registerLabel.setForeground(Color.orange);
		userIDLabel.setForeground(Color.orange);
		userNameLabel.setForeground(Color.orange);
		passEqualLabel.setForeground(Color.orange);
		passwordLabel.setForeground(Color.orange);
		// Set size of button
		registerLabel.setBounds(80, 60,400, 60);
		registerButton.setBounds(200, 520, 150, 50);
		returnButton.setBounds(0, 0, 140, 50);
		
		

		// Add to panel
		returnPanel.add(returnButton);
		returnPanel.add(registerLabel);
		userIDPanel.add(userIDLabel);
		userIDPanel.add(userIDField);
		userNamePanel.add(userNameLabel);
		userNamePanel.add(userNameField);
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordField);
		passEqualPanel.add(passEqualLabel);
		passEqualPanel.add(passEuqalField);
		registerPanel.add(registerButton);
		returnPanel.setLayout(null);

		// Set panel Location
		returnPanel.setBounds(0, 0, 500, 200);
		userIDPanel.setBounds(10, 120, 450, 50);
		userNamePanel.setBounds(0, 200, 450, 50);
		passwordPanel.setBounds(10, 280, 450, 50);
		passEqualPanel.setBounds(10, 360, 450, 50);
		registerPanel.setBounds(60, 440, 450, 60);
		// Add to frame
		registerFrame.add(returnPanel);
		registerFrame.add(userIDPanel);
		registerFrame.add(userNamePanel);
		registerFrame.add(passwordPanel);
		registerFrame.add(passEqualPanel);
		registerFrame.add(registerPanel);

	}

	private void SetTransparent() {
		// Label Transparent
		registerLabel.setOpaque(false);
		userIDLabel.setOpaque(false);
		userNameLabel.setOpaque(false);
		passwordLabel.setOpaque(false);
		passEqualLabel.setOpaque(false);
		
		// button Transparent
		registerButton.setOpaque(false);
		returnButton.setOpaque(false);
		// Field Transparent
		userIDField.setOpaque(false);
		userNameField.setOpaque(false);
		passwordField.setOpaque(false);
		passEuqalField.setOpaque(false);
		
		// Panel Transparent
		returnPanel.setOpaque(false);
		userIDPanel.setOpaque(false);
		userNamePanel.setOpaque(false);
		passwordPanel.setOpaque(false);
		passEqualPanel.setOpaque(false);
		passEqualPanel.setOpaque(false);
		registerPanel.setOpaque(false);
	}

	private void addEvent() {
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userID = userIDField.getText().trim();
				userName=userNameField.getText().trim();
				password = passwordField.getText().trim();
				passwordEqual = passEuqalField.getText().trim();
				if (userID.length() == 0) {
					JOptionPane.showMessageDialog(null, "The userID cannot be empty", "Warning",
							JOptionPane.WARNING_MESSAGE);
					empty();
				} else if (!(password.equals(passwordEqual))) {
					JOptionPane.showMessageDialog(null, "The password is not the same twice", "Warning",
							JOptionPane.WARNING_MESSAGE);
					empty();
				} else {
					if (UpdateUser.AddUser(userID, userName, password)) {
						JOptionPane.showMessageDialog(null, "Registered successfully");
						registerFrame.dispose();
						new Land();
					} else {
						empty();
					}

				}
			}
		});

		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registerFrame.dispose();
				new Land();
			}
		});
	}

	private void empty() {
		userIDField.setText("");
		userNameField.setText("");
		passwordField.setText("");
		passEuqalField.setText("");
	}
}
