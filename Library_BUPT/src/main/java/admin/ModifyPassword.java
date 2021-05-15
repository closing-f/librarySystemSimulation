package admin;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import database.ConnectDatabase;
import window.Land;
/*
 * modify password
 */
public class ModifyPassword extends JFrame {
	
	//panel
	private JPanel modifyPassPanel = new JPanel();
	// label
	private JLabel titleLabel = new JLabel("Modify Password");
	private JLabel userIDLabel = new JLabel("UserID:");
	private JLabel getUserText = new JLabel();
	private JLabel oldpassLabel = new JLabel("Old Password:");
	private JLabel newpassLabel = new JLabel("New Password:");
	private JLabel confirmpassLabel = new JLabel("Confirm Pass:");
	// password odlpassField
	private JPasswordField odlpassField = new JPasswordField(22);
	private JPasswordField newpassField = new JPasswordField(22);
	private JPasswordField confirmpassField = new JPasswordField(22);
	// fontNormal
	private Font fontNormal = new Font("方正粗黑宋简体", Font.BOLD, 24);
	private Font fontText = new Font("方正粗黑宋简体", Font.BOLD, 22);
	// modify modifyButton
	private JButton modifyButton = new JButton("Modify");
	private JFrame frame = new JFrame();

	public ModifyPassword(String userID) {
		setSize(580, 650);
		// set background
		Icon i = new ImageIcon("img\\subop.jpg");
		JLabel Label = new JLabel(i);
		Label.setBounds(0, 0, 580, 650);
		
		setTitle("modify password");
		
		getUserText.setText(userID);
		
		//set font
		userIDLabel.setFont(fontNormal);
		getUserText.setFont(fontNormal);
		oldpassLabel.setFont(fontNormal);
		newpassLabel.setFont(fontNormal);
		confirmpassLabel.setFont(fontNormal);
		odlpassField.setFont(fontText);
		newpassField.setFont(fontText);
		confirmpassField.setFont(fontText);
		modifyButton.setFont(fontText);
		//set bound
		userIDLabel.setBounds(120, 170, 150, 30);
		getUserText.setBounds(260, 170, 300, 30);
		oldpassLabel.setBounds(120, 210, 200, 30);
		odlpassField.setBounds(300, 215, 140, 25);
		newpassLabel.setBounds(120, 250, 200, 30);
		newpassField.setBounds(300, 255, 140, 25);
		confirmpassLabel.setBounds(120, 290, 200, 30);
		confirmpassField.setBounds(300, 295, 140, 25);
		modifyButton.setBounds(115, 340, 285, 30);
		modifyButton.setBackground(Color.black);
		modifyButton.setOpaque(false);
		
		AddEvent();
		
		//add to panel
		modifyPassPanel.add(userIDLabel);
		modifyPassPanel.add(getUserText);
		modifyPassPanel.add(oldpassLabel);
		modifyPassPanel.add(odlpassField);
		modifyPassPanel.add(newpassLabel);
		modifyPassPanel.add(newpassField);
		modifyPassPanel.add(confirmpassLabel);
		modifyPassPanel.add(confirmpassField);
		modifyPassPanel.add(modifyButton);
		
		//set panel
		modifyPassPanel.setLayout(null);
		modifyPassPanel.setBounds(0, 0, 600, 400);
		modifyPassPanel.setOpaque(false);
		add(modifyPassPanel);
		add(Label);
		
		// set layout
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(null);
		setVisible(true);
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	
	// compare old password 
	private boolean UserCompare(String userID, String password) {
		Connection con = ConnectDatabase.connectDB();
		
		PreparedStatement preSql;
		ResultSet rs;
		String sqlStr = "select * from usertable2 where userID = ?";	
		try {
			preSql = con.prepareStatement(sqlStr);
			preSql.setString(1, userID);
			rs = preSql.executeQuery();
			while (rs.next()) {
				String s = rs.getString(3);			
				if (s.equals(password)) {
					return true;
				} else {
					return false;
				}
			}
			con.close();
			return false;
		} catch (SQLException e) {
			return false;
		}
	}

	private void AddEvent() {
		
		// modify password event
		modifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String userID = getUserText.getText().trim();
				String oldpass = odlpassField.getText().trim();
				String newpass = newpassField.getText().trim();
				String surepass = confirmpassField.getText().trim();
					if (UserCompare(userID, oldpass)) 
					{
						if (newpass.equals(surepass)) 
						{
							database.UpdateUser.UpdatePass(userID, newpass);
							JOptionPane.showMessageDialog(null, "modify successfully");
	
							JOptionPane.showMessageDialog(null, "Please log in again");
							dispose();
							frame.dispose();
							new Land();
						}
						else 
						{
							JOptionPane.showMessageDialog(null, "Make sure the passwords are equal", "Warning", JOptionPane.WARNING_MESSAGE);
							empty();
						}
					}
					else 
					{
					JOptionPane.showMessageDialog(null, "The original password is incorrect", "Warning", JOptionPane.WARNING_MESSAGE);
					empty();
					}

			}
		});
	}

	//set the field empty
	private void empty() {
		odlpassField.setText("");
		newpassField.setText("");
		confirmpassField.setText("");
	}
}
