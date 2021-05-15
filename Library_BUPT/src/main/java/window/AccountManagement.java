package window;

import admin.*;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import book.BookFlyRecord;
/*
 * Account Management :
 *
 * modify password
 * 
 * bookfly
 * 
 * recharge
 * 
 * Exit
 * 
 */
public class AccountManagement 	{
	
	// panel
	public JPanel accountManaPanel = new JPanel();
	//  title label
	private JLabel titleLabel = new JLabel("Account Management");
	// button
	private JButton passwordButton = new JButton("Modify Password");
	private JButton bookflyButton = new JButton("BookFly");
	private JButton exitButton = new JButton("Exit");
	private JButton returnButton = new JButton("Return");
	private JButton rechargeButton = new JButton("Recharge");
	// font
	private Font fontTitle = new Font("Bauhaus 93", Font.BOLD, 60);
	private Font fontNormal = new Font("方正粗黑宋简体", Font.BOLD, 25);
	
	private String userID;
	private JFrame frame;
	private CardLayout cardLayout;
	private JPanel windowPanel;
	private JLabel Label;
	public AccountManagement(String userID,CardLayout cardLayout,JPanel windowPanel) {
		
		this.cardLayout=cardLayout;
		this.windowPanel=windowPanel;
		
		// set background picture
		SetBackground();
		
		//set font color bound of component
		SetFontColorBound();
				
		SetTransparent();

		AddEvent();
				
		AddToPanel();		
	}

	public void setUser(String userID) {
		this.userID = userID;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	//add event
	private void AddEvent() {
		// modify password
		passwordButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ModifyPassword password = new ModifyPassword(userID);
				password.setFrame(frame);
			}
		});

		// bookfly
		bookflyButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new BookFlyRecord(userID);
			}
		});

		// exit
		exitButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new Land();
			}
		});
		
		//return to main interface
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(windowPanel, "Main Interface");
			}
		});
		// set password
		rechargeButton.addActionListener(new ActionListener() {			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new Recharge(userID);
			}
		});		
	}
	private void SetBackground ()
	{
		// set background picture
		Icon i = new ImageIcon("img\\accountM.jpg");
		Label = new JLabel(i);
		Label.setBounds(0, 0, 1200, 800);
	}
	private void SetFontColorBound()
	{
		//set font
		returnButton.setFont(fontNormal);
		titleLabel.setFont(fontTitle);
		passwordButton.setFont(fontNormal);
		bookflyButton.setFont(fontNormal);
		exitButton.setFont(fontNormal);
		rechargeButton.setFont(fontNormal);
		
		//set bound
		returnButton.setBounds(0, 0, 150, 40);
		titleLabel.setBounds(200, 100, 800, 70);
		passwordButton.setBounds(150, 250, 300, 50);
		bookflyButton.setBounds(150, 350, 300, 50);
		exitButton.setBounds(150, 450, 300, 50);
		rechargeButton.setBounds(500, 250, 300, 50);
		
		//set color
		titleLabel.setForeground(Color.orange);
		passwordButton.setForeground(Color.orange);
		bookflyButton.setForeground(Color.orange);
		exitButton.setForeground(Color.orange);
		returnButton.setForeground(Color.orange);	
		passwordButton.setBackground(Color.cyan);
		rechargeButton.setForeground(Color.orange);
		bookflyButton.setBackground(Color.cyan);
		exitButton.setBackground(Color.cyan);
		returnButton.setBackground(Color.cyan);
		rechargeButton.setBackground(Color.cyan);

	}
	private void AddToPanel()
	{
		accountManaPanel.setLayout(null);
		accountManaPanel.add(returnButton);
		accountManaPanel.add(passwordButton);
		accountManaPanel.add(bookflyButton);
		accountManaPanel.add(rechargeButton);
		accountManaPanel.add(exitButton);
		accountManaPanel.add(titleLabel);
		accountManaPanel.add(Label);
	}
	private void SetTransparent()
	{
		returnButton.setOpaque(false);
		passwordButton.setOpaque(false);
		bookflyButton.setOpaque(false);
		exitButton.setOpaque(false);
		rechargeButton.setOpaque(false);
	}

}
