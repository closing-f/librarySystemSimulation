package admin;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import database.GetUser;
import database.UpdateUser;

public class Recharge extends JFrame{
	// panel
	private JPanel rechargePanel = new JPanel();
	// label
	private JLabel rechargeLabel = new JLabel("Recharge amount:");
	private JLabel userIDLabel = new JLabel("userID:");
	// textfield
	private JTextField userIDField = new JTextField(22);
	private JTextField rechargeField = new JTextField(22);
	// font
	private Font fontNormal = new Font("方正粗黑宋简体", Font.BOLD, 22);
	private Font fontText = new Font("方正粗黑宋简体", Font.BOLD, 18);
	// okButton
	private JButton okButton = new JButton("OK");
	
	public Recharge(String user) {
		setSize(400, 450);
		setTitle("Recharge");
		
		// set background image
		Icon i = new ImageIcon("img\\ssubop.jpg");
		JLabel Label = new JLabel(i);
		Label.setBounds(0, 0, 400, 450);
		
		//set font
		rechargeLabel.setFont(fontNormal);
		userIDLabel.setFont(fontNormal);
		userIDField.setFont(fontNormal);
		rechargeField.setFont(fontNormal);
		okButton.setFont(fontText);
		
		//set bound
		userIDLabel.setBounds(50, 150, 100, 30);
		userIDField.setBounds(150, 150, 185, 28);
		okButton.setBackground(Color.cyan);
		okButton.setOpaque(false);
		rechargeLabel.setBounds(50, 225, 100, 30);
		rechargeField.setBounds(150, 225, 185, 28);
		okButton.setBounds(150, 300, 100, 35);
		
		//add button event
		AddEvent();
		
		//add to panel
		rechargePanel.add(rechargeLabel);
		rechargePanel.add(userIDField);
		rechargePanel.add(userIDLabel);
		rechargePanel.add(rechargeField);
		rechargePanel.add(okButton);
		
		//set panel
		rechargePanel.setLayout(null);
		rechargePanel.setBounds(0, 0, 600, 400);
		rechargePanel.setOpaque(false);
		add(rechargePanel);
		add(Label);
		
		//set layout
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(null);
		setVisible(true);
	}
	
	private void AddEvent() {
		//execute recharge event
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					double balance = Double.parseDouble(rechargeField.getText().trim());
					if(balance<0)
					{
						JOptionPane.showMessageDialog(null, "Incorrect input", "Warning", JOptionPane.WARNING_MESSAGE);
						rechargeField.setText("");
						return;
					}
					String userID =userIDField.getText().trim();
					if(GetUser.IfUserExist(userID))
					{	UpdateUser.ModifyBalance(userID, balance,1);
						balance=GetUser.GetBalance(userID);
						JOptionPane.showMessageDialog(null, "Now,your balance is"+balance, "Congratulation", JOptionPane.WARNING_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(null, "User does not exist", "Warning", JOptionPane.WARNING_MESSAGE);
					}
				}
				catch (Exception e1) 
				{
					JOptionPane.showMessageDialog(null, "Incorrect input", "Warning", JOptionPane.WARNING_MESSAGE);
					rechargeField.setText("");
				}
			}
		});
	}

}
