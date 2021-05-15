package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class GetUser {
	
	//judege whether userID and password is right	
	public static Boolean TestUserPass(String userID, String password) {

		Connection con = ConnectDatabase.connectDB();

   		PreparedStatement preSql;

		ResultSet rs;

		String sqlStr = "select * from usertable2 where userID = ?";
		try {
			preSql = con.prepareStatement(sqlStr);
			preSql.setString(1, userID);
			rs = preSql.executeQuery();
			boolean flag = false;
			while(rs.next()) {
				flag = true;
				String password2 = rs.getString(3);
				if (!(password.equals(password2))) {
					JOptionPane.showMessageDialog(null, "wrong password", "Warning", JOptionPane.WARNING_MESSAGE);
					return false;
				}
			}
			if(!flag) {
				JOptionPane.showMessageDialog(null, "The user name does not exist", "Warning", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			con.close();
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "The user name does not exist", "Warning", JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}
	
	//judege whether user is admin
	public static boolean IfAdmin(String userID) {
		Connection con = ConnectDatabase.connectDB();
		PreparedStatement preSql;
		ResultSet rs;
		String sqlStr = "select * from usertable2 where userID = ?";
		try {
			 preSql = con.prepareStatement(sqlStr);
			 preSql.setString(1, userID);
			rs = preSql.executeQuery();
			while(rs.next()) {
				int admin = rs.getInt(4);
				if (admin==1) {
					return true;
				}else {
					return false;
				}
			}
			con.close();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	//get viplevel of user
	public static int GetvipLevel(String userID)
	{
		Connection con = ConnectDatabase.connectDB();
		PreparedStatement preSql;
		ResultSet rs;
		int vipLevel=0;
		String sqlStr = "select * from usertable2 where userID = ?";
		try {
			preSql = con.prepareStatement(sqlStr);
			preSql.setString(1, userID);
			rs = preSql.executeQuery();
			if(rs.next()) {
				vipLevel= rs.getInt(6);
			}
			con.close();
			return vipLevel;
		} catch (SQLException e) {
		}
		return -1;
	}
	
	//judge whether there is a user in table
	public static boolean IfUserEmpty() {
		Connection con = ConnectDatabase.connectDB();

		PreparedStatement preSql;

		ResultSet rs;

		String sqlStr = "select * from usertable2";
		try {
			preSql = con.prepareStatement(sqlStr);
			rs = preSql.executeQuery();
			boolean flag = false;
			while(rs.next()) {
				flag = true;
				return true;
			}
			if(!flag) {
				return false;
			}
			con.close();
			return false;
		} catch (SQLException e) {
			return false;
		}
	}
	
	//jude whether userID is exist
	public static boolean IfUserExist(String userID) {
		Connection con = ConnectDatabase.connectDB();

		PreparedStatement preSql;

		ResultSet rs;

		String sqlStr = "select * from usertable2 where userID = ?";
		try {
			preSql = con.prepareStatement(sqlStr);
			preSql.setString(1, userID);
			rs = preSql.executeQuery();
			boolean flag = false;
			while(rs.next()) {
				flag = true;
				return true;
			}
			if(!flag) {
				return false;
			}
			con.close();
			return false;
		} catch (SQLException e) {
			return false;
		}
	}
	
	//get balance of user
	public static double GetBalance(String userID)
	{
		Connection con = ConnectDatabase.connectDB();
		PreparedStatement preSql;
		ResultSet rs;
		double balance=-1;
		String sqlStr = "select * from usertable2 where userID = ?";
		try {
			preSql = con.prepareStatement(sqlStr);
			preSql.setString(1, userID);
			rs = preSql.executeQuery();
			if(rs.next()) {
				balance= rs.getDouble(5);
			}
			con.close();
			return balance;
		} catch (SQLException e) {
		}
		return -1;
		
	}
}
