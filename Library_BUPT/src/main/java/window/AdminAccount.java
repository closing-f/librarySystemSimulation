package window;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import admin.*;
/*
 * 
 *Account Management
 * 
 * Modify Password
 * 
 * Bookfly Record
 * 
 * Exit
 */

public class AdminAccount {
	
	// panel
	public JPanel adminPanel = new JPanel();
	// label
	private JLabel titleLabel = new JLabel("Account Management");
	// button
	private JButton modifyPassButton = new JButton("Modify Password");
	private JButton borrowRecordButton = new JButton("Bookfly Record");
	private JButton exitButton = new JButton("Exit");
	private JButton accountInforButton = new JButton("Account Information");
	private JButton rechargeButton = new JButton("Recharge");
	private JButton returnButton = new JButton("Return");
	// font
	private Font fontTitle = new Font("Bauhaus 93", Font.BOLD, 60);
	private Font fontNormal = new Font("方正粗黑宋简体", Font.BOLD, 25);
	private String userID;
	private JFrame frame;
	private JLabel Label;
	private CardLayout cardLayout;
	private JPanel windowPanel;
	public AdminAccount(String userID,CardLayout cardLayout,JPanel windowPanel) {
		
		this.cardLayout=cardLayout;
		this.windowPanel=windowPanel;
		this.userID=userID;
		
		// set background picture
		Icon i = new ImageIcon("img\\accountM.jpg");
		Label = new JLabel(i);
		Label.setBounds(0, 0, 1200, 800);
		
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

	//add Event
	private void AddEvent() {
		
		

		// modify password
		modifyPassButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ModifyPassword password = new ModifyPassword(userID);
				password.setFrame(frame);
			}
		});

		// bookfly record
		borrowRecordButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new BookflyAdmin();
			}
		});

		// exit
		exitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
				new Land();
			}
		});

		// accountInformation
		accountInforButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new GetUserInfor();
			}
		});

		

		// set password
		rechargeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new Recharge(userID);
			}
		});
		
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			cardLayout.show(windowPanel, "Main Interface");
			}
		});
	}
	
	private void SetFontColorBound()
	{
		//set font
		titleLabel.setFont(fontTitle);
		returnButton.setFont(fontNormal);	
		modifyPassButton.setFont(fontNormal);
		borrowRecordButton.setFont(fontNormal);
		exitButton.setFont(fontNormal);
		accountInforButton.setFont(fontNormal);
		rechargeButton.setFont(fontNormal);
		
		//set bound
		titleLabel.setBounds(200, 100, 800, 70);
		returnButton.setBounds(0, 0, 140, 40);
		modifyPassButton.setBounds(150, 250, 300, 50);
		borrowRecordButton.setBounds(150, 350, 300, 50);
		exitButton.setBounds(150, 450, 300, 50);
		accountInforButton.setBounds(500, 250, 300, 50);
		rechargeButton.setBounds(500, 350, 300, 50);
		
		//set color
		returnButton.setForeground(Color.CYAN);
		returnButton.setBackground(Color.black);
		titleLabel.setForeground(Color.orange);
		modifyPassButton.setForeground(Color.orange);
		borrowRecordButton.setForeground(Color.orange);
		rechargeButton.setForeground(Color.orange);
		exitButton.setForeground(Color.orange);
		accountInforButton.setForeground(Color.orange);
		modifyPassButton.setBackground(Color.cyan);
		borrowRecordButton.setBackground(Color.cyan);
		exitButton.setBackground(Color.cyan);
		accountInforButton.setBackground(Color.cyan);
		rechargeButton.setBackground(Color.cyan);

	}
	private void SetTransparent()
	{
		returnButton.setOpaque(false);
		modifyPassButton.setOpaque(false);
		borrowRecordButton.setOpaque(false);
		exitButton.setOpaque(false);
		accountInforButton.setOpaque(false);
		rechargeButton.setOpaque(false);

	}
	private void AddToPanel()
	{
		adminPanel.setLayout(null);
		adminPanel.add(returnButton);		
		adminPanel.add(modifyPassButton);
		adminPanel.add(borrowRecordButton);
		adminPanel.add(exitButton);
		adminPanel.add(accountInforButton);
		adminPanel.add(rechargeButton);
		adminPanel.add(titleLabel);
		adminPanel.add(Label);
	}
}
