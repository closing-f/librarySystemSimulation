package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.Semaphore;

import javax.swing.JOptionPane;

import redislock.RedisTool;


public class UpdateBookFly {
	
	public UpdateBookFly() {
	}
	
	//borrow book
	public static void BorrowBook(String uesrID,int bookid, String bookname) {
		
		String lockKey="book";
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

		String sqlStr = "insert into bookfly (userID,bookid,bookname,state,borrowtime,returntime) values (?,?,?,?,now(),null)";

		try {
			preSql = con.prepareStatement(sqlStr);
			preSql.setString(1, uesrID);
			preSql.setInt(2, bookid);
			preSql.setString(3, bookname);
			preSql.setString(4, "Be lent");
			preSql.executeUpdate();
			con.close();
			ChangeBorrowState(bookid,lockKey,requestId);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "errorSqlbookfly", "Warning", JOptionPane.WARNING_MESSAGE);
		}
		
	}
	
	//change book state when book is borrowed
	private static void ChangeBorrowState(int bookid,String lockKey,String requestId) {
		Connection con = ConnectDatabase.connectDB();

		PreparedStatement preSql;

		String sqlStr = "update booktable2 set state=? where bookid = ?";

		try {
			preSql = con.prepareStatement(sqlStr);
			preSql.setString(1, "Be lent");
			preSql.setInt(2, bookid);
			
			int ok = preSql.executeUpdate();
			con.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "errorSqlbooktable", "Warning", JOptionPane.WARNING_MESSAGE);
		}
		RedisTool.releaseDistributedLock(RedisTool.GetJedis(), lockKey, requestId);
	}
	
	//return book
	public static void ReturnBook(String uesrID, int bookid) {
		String lockKey="book";
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
		String sqlStr = "select now()";
		ResultSet rs;
		Date date=null;
		try {
			preSql = con.prepareStatement(sqlStr);
			rs = preSql.executeQuery();
			while(rs.next()) {
				date = rs.getDate(1);
			}
			sqlStr = "update bookfly set returntime =  ? where bookid = ? and userID = ? and state = ?";
			preSql = con.prepareStatement(sqlStr);
			preSql.setDate(1, date);
			preSql.setInt(2, bookid);
			preSql.setString(3, uesrID);
			preSql.setString(4, "Be lent");
			int ok = preSql.executeUpdate();
			
			sqlStr = "update bookfly set state = ? where bookid = ? and userID = ? and state = ?";
			preSql = con.prepareStatement(sqlStr);
			preSql.setString(1, "Exist");
			preSql.setInt(2, bookid);
			preSql.setString(3, uesrID);
			preSql.setString(4, "Be lent");
			ok = preSql.executeUpdate();
			
			con.close();
			ChangeReturnState(bookid,lockKey,requestId);
		} catch (SQLException e) {
		}
	}

	//change book state when book is returned
	private static void ChangeReturnState(int bookid,String lockKey,String requestId) {

		Connection con = ConnectDatabase.connectDB();

		PreparedStatement preSql;

		String sqlStr = "update booktable2 set state=? where bookid = ?";

		try {
			preSql = con.prepareStatement(sqlStr);
			preSql.setString(1, "Exist");
			preSql.setInt(2, bookid);
			
			int ok = preSql.executeUpdate();
			con.close();
		} catch (SQLException e) {
		}
		RedisTool.releaseDistributedLock(RedisTool.GetJedis(), lockKey, requestId);
	}

	//change bookfly by userID ,bookId
	public static boolean ChangeBookfly(String uesrID,int bookid) {
		
		Connection con = ConnectDatabase.connectDB();
		PreparedStatement preSql;
		ResultSet rs;
		String sqlStr = "select * from bookfly where bookid = ? and state = ?";
		try {
			preSql = con.prepareStatement(sqlStr);
			preSql.setInt(1, bookid);
			preSql.setString(2, "Be lent");
			rs = preSql.executeQuery();
			while (rs.next()) {
				String user2 = rs.getString(2);
				if(user2.equals(uesrID)) {
					return true;
				}else {
					return false;
				}
			}
			con.close();
			return false;
		} catch (SQLException e) {
			return false;
		}
		
	}
	
	//purchase book
	public static void PurchaseBook(String uesrID,int bookid, String bookname) {
		Connection con = ConnectDatabase.connectDB();

		PreparedStatement preSql;

		String sqlStr = "insert into bookfly(userID,bookid,bookname,state,borrowtime,returntime) values (?,?,?,?,now(),null)";

		try {
			preSql = con.prepareStatement(sqlStr);
			preSql.setString(1, uesrID);
			preSql.setInt(2, bookid);
			preSql.setString(3, bookname);
			preSql.setString(4, "Be sold");
			int ok = preSql.executeUpdate();
			con.close();
			ChangePurchaseState(bookid);
		} catch (SQLException e) {
		}
	}

	//change book state when book is purchased
	private static void ChangePurchaseState(int bookid) {
		Connection con = ConnectDatabase.connectDB();

		PreparedStatement preSql;

		String sqlStr = "update booktable2 set state=? where bookid = ?";

		try {
			preSql = con.prepareStatement(sqlStr);
			preSql.setString(1, "Be sold");
			preSql.setInt(2, bookid);			
			int ok = preSql.executeUpdate();
			con.close();
		} catch (SQLException e) {
		}
	}
	
	
}
