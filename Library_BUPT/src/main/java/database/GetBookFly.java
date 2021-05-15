package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class GetBookFly {
	public GetBookFly() {
	}
	//find all bookfly 
	public static void GetBookfly(DefaultTableModel model) {

		Connection con = ConnectDatabase.connectDB();
		PreparedStatement preSql;
		ResultSet rs;
		String sqlStr = "select * from bookfly";
		try {
			preSql = con.prepareStatement(sqlStr);
			rs = preSql.executeQuery();
			while (rs.next()) {
				String userID = rs.getString(2);
				int bookid = rs.getInt(3);
				String bookname = rs.getString(4);
				Date date = rs.getDate(5);
				Date date2 = rs.getDate(6);
				String status = rs.getString(7);
				model.addRow(new Vector<>(Arrays.asList(userID,bookid,bookname,date,date2,status)));
			}
			con.close();
		} catch (SQLException e) {
		}
	}
	
	//find bookfly by userID
	public static void UserBookfly(DefaultTableModel model,String userID) {

		Connection con = ConnectDatabase.connectDB();
		PreparedStatement preSql;
		ResultSet rs;
		String sqlStr = "select * from bookfly where userID = ?";
		try {
			preSql = con.prepareStatement(sqlStr);
			preSql.setString(1, userID);
			rs = preSql.executeQuery();
			while (rs.next()) {
				int bookid = rs.getInt(3);
				String bookname = rs.getString(4);
				Date date = rs.getDate(5);
				Date date2 = rs.getDate(6);
				String status = rs.getString(7);
				model.addRow(new Vector<>(Arrays.asList(userID,bookid,bookname,date,date2,status)));
			}	
			con.close();
		} catch (SQLException e) {
		}
	}
	
	//find bookfly by bookid
	public static void IdBookfly(DefaultTableModel model,int bookid) {

		Connection con = ConnectDatabase.connectDB();
		PreparedStatement preSql;
		ResultSet rs;
		String sqlStr = "select * from bookfly where bookid = ?";
		try {
			preSql = con.prepareStatement(sqlStr);
			preSql.setInt(1, bookid);
			rs = preSql.executeQuery();
			while (rs.next()) {
				String userID = rs.getString(2);	
				String bookname = rs.getString(4);
				Date date = rs.getDate(5);
				Date date2 = rs.getDate(6);
				String status = rs.getString(7);
				model.addRow(new Vector<>(Arrays.asList(userID,bookid,bookname,date,date2,status)));
			}
			con.close();
		} catch (SQLException e) {
		}
	}
}
