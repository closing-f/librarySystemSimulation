package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.Semaphore;
import javax.swing.JOptionPane;

import redislock.RedisTool;

public class UpdateUser {

	//add user
	public static Boolean AddUser(String userID, String name, String password) {
		Connection con = ConnectDatabase.connectDB();

		PreparedStatement preSql;

		String sqlStr=null;
		
		if(GetUser.IfUserEmpty()) {
			sqlStr = "insert into usertable2(userID,name,password,balance,admin)values (?,?,?,?,0)";
		}else {
			sqlStr = "insert into usertable2(userID,name,password,balance,admin)values (?,?,?,?,1)";
		}

		try {
			double balance=100.0;
			preSql = con.prepareStatement(sqlStr);
			preSql.setString(1, userID);
			preSql.setString(2, name);
			preSql.setString(3, password);
			preSql.setDouble(4, balance);
			int ok = preSql.executeUpdate();
			con.close();
			
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "The userID already exists", "Warning:", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
	}
	
	//delete user
	public static void DeleteUser(String userID) {
		String lockKey="usertable";
		String requestId=UUID.randomUUID().toString();
		try {
			while(!RedisTool.tryGetDistributedLock(RedisTool.GetJedis(), lockKey, requestId, 1000));
		}catch (Exception e1) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Redis does not start", "Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}
		Connection con = ConnectDatabase.connectDB();

		PreparedStatement preSql;


		String sqlStr = "delete from usertable2 where userID = ?";
		try {
			
			preSql = con.prepareStatement(sqlStr);
			preSql.setString(1, userID);
			int ok = preSql.executeUpdate();
			con.close();
		} catch (SQLException e) {
		}
		RedisTool.releaseDistributedLock(RedisTool.GetJedis(), lockKey, requestId);
	}
	
	//change admin to common user
	public static void UpdateUser(String userID) {
		String lockKey="usertable";
		String requestId=UUID.randomUUID().toString();
		try {
			while(!RedisTool.tryGetDistributedLock(RedisTool.GetJedis(), lockKey, requestId, 1000));
		}catch (Exception e1) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Redis does not start", "Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}
		Connection con = ConnectDatabase.connectDB();
		PreparedStatement preSql;

		String sqlStr = "update usertable2  set admin = ? where userID = ?";
		try {
			preSql = con.prepareStatement(sqlStr);
			preSql.setInt(1, 0);
			preSql.setString(2, userID);
			int ok = preSql.executeUpdate();
			con.close();
		} catch (SQLException e) {
		}
		RedisTool.releaseDistributedLock(RedisTool.GetJedis(), lockKey, requestId);
	}
	
	//change to admin
	public static void ChangeToAdmin(String userID) {
		String lockKey="usertable";
		String requestId=UUID.randomUUID().toString();
		try {
			while(!RedisTool.tryGetDistributedLock(RedisTool.GetJedis(), lockKey, requestId, 1000));
		}catch (Exception e1) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Redis does not start", "Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}
		Connection con = ConnectDatabase.connectDB();
		PreparedStatement preSql;

		String sqlStr = "update usertable2  set admin = ? where userID = ?";
		try {
			preSql = con.prepareStatement(sqlStr);
			preSql.setInt(1, 1);
			preSql.setString(2, userID);
			int ok = preSql.executeUpdate();
			con.close();
		} catch (SQLException e) {
		}
		RedisTool.releaseDistributedLock(RedisTool.GetJedis(), lockKey, requestId);
	}
	
	//change password
	public static void UpdatePass(String userID,String password){
		
		String lockKey="usertable";
		String requestId=UUID.randomUUID().toString();
		try {
			while(!RedisTool.tryGetDistributedLock(RedisTool.GetJedis(), lockKey, requestId, 1000));
		}catch (Exception e1) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Redis does not start", "Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}
		Connection con = ConnectDatabase.connectDB();
		PreparedStatement preSql;

		String sqlStr = "update usertable2 set password = ? where userID = ?";
		try {
			preSql = con.prepareStatement(sqlStr);
			preSql.setString(1, password);
			preSql.setString(2, userID);
			int ok = preSql.executeUpdate();
			con.close();
		} catch (SQLException e) {
		}
		RedisTool.releaseDistributedLock(RedisTool.GetJedis(), lockKey, requestId);
	}
	
	//change balance
	public static Boolean ModifyBalance(String userID, double balance,int flag)
	{
		String lockKey="usertable";
		String requestId=UUID.randomUUID().toString();
		try {
			while(!RedisTool.tryGetDistributedLock(RedisTool.GetJedis(), lockKey, requestId, 1000));
		}catch (Exception e1) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Redis does not start", "Warning", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		Connection con = ConnectDatabase.connectDB();
		PreparedStatement preSql;

		String sqlStr = "update usertable2 set balance = balance + ? where userID = ?";
		try {
			preSql = con.prepareStatement(sqlStr);
			preSql.setDouble(1, balance*flag);
			preSql.setString(2, userID);
			int ok = preSql.executeUpdate();
			con.close();
			RedisTool.releaseDistributedLock(RedisTool.GetJedis(), lockKey, requestId);
			if(balance*flag>500)
			{
				SetvipLevel(userID,balance);
			}
			return true;
		} catch (SQLException e) {
		}
		RedisTool.releaseDistributedLock(RedisTool.GetJedis(), lockKey, requestId);
		return false;
		
		
	}
	//set viplevel of user
	public static void SetvipLevel(String userID,double balance)
	{
		int vipLevel=(int) (balance/500);
		
		Connection con = ConnectDatabase.connectDB();
		PreparedStatement preSql;
		String sqlStr = "update usertable2 set vipLevel = vipLevel + ? where userID = ?";
		try 
		{
			preSql = con.prepareStatement(sqlStr);
			preSql.setInt(1, vipLevel);
			preSql.setString(2, userID);
			int ok = preSql.executeUpdate();
			con.close();
			return;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Update vipLevel Error", "Warning", JOptionPane.WARNING_MESSAGE);
		}
		return;
	}
	
}
