package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.Semaphore;
import javax.swing.JOptionPane;

import redislock.RedisTool;

public class UpdateBookTable {
	public UpdateBookTable() {
		
	}
	
	//Add book
	public static  void AddBook(String category,String bookname,String author,double price) {
		
		Connection con = ConnectDatabase.connectDB();

		PreparedStatement preSql;

		String sqlStr = "insert into booktable2(category,bookname,author,price,state) values (?,?,?,?,?)";
		try {
			preSql = con.prepareStatement(sqlStr);
			preSql.setString(1, category);
			preSql.setString(2, bookname);
			preSql.setString(3, author);
			preSql.setDouble(4, price);
			preSql.setString(5, "Exist");
			int ok = preSql.executeUpdate();
			con.close();
		} catch (SQLException e) {
			
		}
		
	}
	
	//modify book
	public static  void modifybook(int bookid,String category,String bookname,String author,double price,String state) {
		
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

		String sqlStr = "update booktable2 set category = ? ,bookname = ? ,author = ?,price = ? ,state = ? where bookid = ?";

		try {
			preSql = con.prepareStatement(sqlStr);
			preSql.setString(1, category);
			preSql.setString(2, bookname);
			preSql.setString(3, author);
			preSql.setDouble(4, price);
			preSql.setString(5, state);
			preSql.setInt(6, bookid);
			int ok = preSql.executeUpdate();
			con.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "sqlError");
			
		}
		
		RedisTool.releaseDistributedLock(RedisTool.GetJedis(), lockKey, requestId);
	}
	
	//delete book
	public static void deletebook(int bookid) {
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

		String sqlStr = "delete from booktable2 where bookid = ?";

		try {
			preSql = con.prepareStatement(sqlStr);
			preSql.setInt(1, bookid);
			int ok = preSql.executeUpdate();
			con.close();
		} catch (SQLException e) {
		}
		RedisTool.releaseDistributedLock(RedisTool.GetJedis(), lockKey, requestId);
	}
	
	
}
