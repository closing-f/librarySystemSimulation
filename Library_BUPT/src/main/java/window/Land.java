package window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import database.GetUser;

//Set the login GUI
public class Land {

	// BackLabel
	private JLabel titleText = new JLabel("BUPT library");
	private JLabel accountText = new JLabel("UserID: ");
	private JLabel passwordText = new JLabel("Password: ");

	// Font
	private Font fontTitle = new Font("Bauhaus 93", Font.BOLD, 40);
	private Font fontNomal = new Font("方正粗黑宋简体", Font.BOLD, 25);
	private Font fontText = new Font("宋体", Font.BOLD, 25);

	// Field
	private JTextField accountfield = new JTextField(10);
	private JPasswordField passwordfield = new JPasswordField(10);

	// Button
	private JButton loginButton = new JButton("Log in");
	private JButton registerButton = new JButton("Register");

	// Panel
	private JPanel titlePanel = new JPanel();
	private JPanel accountPanel = new JPanel();
	private JPanel passwordPanel = new JPanel();
	private JPanel loginPanel = new JPanel();
	private JPanel registerPanel = new JPanel();

	private JFrame frame = new JFrame("Log in");
	// Component size
	private Dimension dimenSmall = new Dimension(50, 30);
	private Dimension dimenBig = new Dimension(150, 50);

	public String user;
	private String password;

	public Land() {

		frame.setTitle("Log in");
		// set size
		frame.setSize(800, 500);
		// In the middle place
		frame.setLocationRelativeTo(null);
		// Layout empty
		frame.setLayout(null);

		// Change window icon
		Toolkit t = Toolkit.getDefaultToolkit();
		Image windowIcon = t.getImage("img\\book.png");
		frame.setIconImage(windowIcon);

		// Add component to Panel
		SetPanel();

		// Set Transparent
		SetTransparent();

		// Add event
		AddEvent();

		// Add background image
		Icon i = new ImageIcon("img\\land.jpg");
		JLabel BackLabel = new JLabel(i);
		BackLabel.setBounds(0, 0, 800, 500);
		frame.add(BackLabel);

		// Can not change the size of a frame
		frame.setResizable(false);
		// Setting window closed
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// Setting window visible
		frame.setVisible(true);
	}

	private void SetPanel() {
		// add font and Icon
		ImageIcon image = new ImageIcon("img\\book.png");
		titleText.setFont(fontTitle);
		titleText.setIcon(image);
		accountText.setFont(fontNomal);
		passwordText.setFont(fontNomal);
		loginButton.setFont(fontNomal);
		registerButton.setFont(fontNomal);
		accountfield.setFont(fontText);
		passwordfield.setFont(fontText);

		// set size
		accountfield.setPreferredSize(dimenSmall);
		accountfield.setForeground(Color.cyan);
		passwordfield.setPreferredSize(dimenSmall);
		passwordfield.setForeground(Color.cyan);
		loginButton.setPreferredSize(dimenBig);
		registerButton.setPreferredSize(dimenBig);
		titleText.setForeground(Color.orange);
		accountText.setForeground(Color.orange);
		passwordText.setForeground(Color.orange);
		loginButton.setForeground(Color.CYAN);
		registerButton.setForeground(Color.CYAN);
		loginButton.setBackground(Color.orange);
		registerButton.setBackground(Color.orange);

		// add conponent to panel
		titlePanel.add(titleText);
		accountPanel.add(accountText);
		accountPanel.add(accountfield);
		passwordPanel.add(passwordText);
		passwordPanel.add(passwordfield);
		loginPanel.add(loginButton);
		registerPanel.add(registerButton);

		// set Bounds
		titlePanel.setBounds(50, 60, 550, 80);
		accountPanel.setBounds(20, 160, 550, 80);
		passwordPanel.setBounds(0, 250, 550, 60);
		loginPanel.setBounds(100, 325, 200, 80);
		registerPanel.setBounds(275, 325, 300, 80);

		frame.add(titlePanel);
		frame.add(accountPanel);
		frame.add(passwordPanel);
		frame.add(loginPanel);
		frame.add(registerPanel);

	}

	private void SetTransparent() {
		// set transparent
		titleText.setOpaque(false);
		accountText.setOpaque(false);
		passwordText.setOpaque(false);
		accountfield.setOpaque(false);
		passwordfield.setOpaque(false);
		loginButton.setOpaque(false);
		registerButton.setOpaque(false);
		titlePanel.setOpaque(false);
		accountPanel.setOpaque(false);
		passwordPanel.setOpaque(false);
		loginPanel.setOpaque(false);
		registerPanel.setOpaque(false);

	}

	private void AddEvent() {

		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				user = accountfield.getText().trim();
				password = passwordfield.getText().trim();
				if (GetUser.TestUserPass(user, password)) {
					frame.dispose();
					new CardLay(user);
				} else {
					accountfield.setText("");
					passwordfield.setText("");
				}

			}
		});

		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new Register();
			}
		});
	}
}
