package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class GetBookTable {

	GetBookTable() {
	}
	//find category of All book
	public static void GetCategory(JComboBox<String> box) {
			Connection con = ConnectDatabase.connectDB();
			PreparedStatement preSql;
			ResultSet rs;
			String sqlStr = "select * from bookcategory";
			try {
				preSql = con.prepareStatement(sqlStr);
				rs = preSql.executeQuery();
				while (rs.next()) {
					String category = rs.getString(1);
					box.addItem(category);
				}
				con.close();
			} catch (SQLException e) {
			}
		}
	// find all book
	public static void GetBookTable(DefaultTableModel model) {

		Connection con = ConnectDatabase.connectDB();
		PreparedStatement preSql;
		ResultSet rs;
		String sqlStr = "select * from booktable2";
		try {
			preSql = con.prepareStatement(sqlStr);
			rs = preSql.executeQuery();
			while (rs.next()) {
				int bookid = rs.getInt(1);
				String category = rs.getString(2);
				String bookname = rs.getString(3);
				String author = rs.getString(4);
				double price = rs.getDouble(5);
				String state = rs.getString(6);
				model.addRow(new Vector<>(Arrays.asList(bookid, category, bookname, author, price, state)));
			}
			con.close();
		} catch (SQLException e) {
		}
	}

	// find book by category
	public static void GetBookByCate(DefaultTableModel model, String CateGory) {
		Connection con = ConnectDatabase.connectDB();
		PreparedStatement preSql;
		ResultSet rs;
		CateGory = "%" + CateGory + "%";
		String sqlStr = "select * from booktable2 where category like ?";
		try {
			preSql = con.prepareStatement(sqlStr);
			preSql.setString(1, CateGory);
			rs = preSql.executeQuery();
			boolean flag = false;
			while (rs.next()) {
				flag = true;
				int bookid = rs.getInt(1);
				String category = rs.getString(2);
				String bookname = rs.getString(3);
				String author = rs.getString(4);
				double price = rs.getDouble(5);
				String state = rs.getString(6);
				model.addRow(new Vector<>(Arrays.asList(bookid, category, bookname, author, price, state)));
			}
			if (!flag) {
				JOptionPane.showMessageDialog(null, "Books don't exist", "Warning", JOptionPane.WARNING_MESSAGE);
			}
			con.close();
		} catch (SQLException e) {
		}
	}

	// find book by bookname
	public static void GetBookByName(DefaultTableModel model, String BookName) {
		Connection con = ConnectDatabase.connectDB();
		PreparedStatement preSql;
		ResultSet rs;
		BookName = "%" + BookName + "%";
		String sqlStr = "select * from booktable2 where bookname like ?";
		try {
			preSql = con.prepareStatement(sqlStr);
			preSql.setString(1, BookName);
			rs = preSql.executeQuery();
			boolean flag = false;
			while (rs.next()) {
				flag = true;
				int bookid = rs.getInt(1);
				String category = rs.getString(2);
				String bookname = rs.getString(3);
				String author = rs.getString(4);
				double price = rs.getDouble(5);
				String state = rs.getString(6);
				model.addRow(new Vector<>(Arrays.asList(bookid, category, bookname, author, price, state)));
			}
			if (!flag) {
				JOptionPane.showMessageDialog(null, "Books don't exist", "Warning", JOptionPane.WARNING_MESSAGE);
			}
			con.close();
		} catch (SQLException e) {
		}
	}

	// find book by author
	public static void GetBookByAuthor(DefaultTableModel model, String Author) {
		Connection con = ConnectDatabase.connectDB();
		PreparedStatement preSql;
		ResultSet rs;
		Author = "%" + Author + "%";
		String sqlStr = "select * from booktable where author like ?";
		try {
			preSql = con.prepareStatement(sqlStr);
			preSql.setString(1, Author);
			rs = preSql.executeQuery();
			boolean flag = false;
			while (rs.next()) {
				flag = true;
				int bookid = rs.getInt(1);
				String category = rs.getString(2);
				String bookname = rs.getString(3);
				String author = rs.getString(4);
				double price = rs.getDouble(5);
				String state = rs.getString(6);
				model.addRow(new Vector<>(Arrays.asList(bookid, category, bookname, author, price, state)));
			}
			if (!flag) {
				JOptionPane.showMessageDialog(null, "Books don't exist", "Warning", JOptionPane.WARNING_MESSAGE);
			}
			con.close();
		} catch (SQLException e) {
		}
	}

	// find book by bookid
	public static void GetBookById(DefaultTableModel model, int BookId) {
		Connection con = ConnectDatabase.connectDB();
		PreparedStatement preSql;
		ResultSet rs;
		String sqlStr = "select * from booktable where bookid = ?";
		try {
			preSql = con.prepareStatement(sqlStr);
			preSql.setInt(1, BookId);
			rs = preSql.executeQuery();
			boolean flag = false;
			while (rs.next()) {
				flag = true;
				int bookid = rs.getInt(1);
				String category = rs.getString(2);
				String bookname = rs.getString(3);
				String author = rs.getString(4);
				double price = rs.getDouble(5);
				String state = rs.getString(6);
				model.addRow(new Vector<>(Arrays.asList(bookid, category, bookname, author, price, state)));
			}
			if (!flag) {
				JOptionPane.showMessageDialog(null, "Books don't exist", "Warning", JOptionPane.WARNING_MESSAGE);
			}
			con.close();
		} catch (SQLException e) {
		}
	}
	
	//find price of book
	public static double GetPrice(int bookid)
	{
		Connection con = ConnectDatabase.connectDB();
		PreparedStatement preSql;
		ResultSet rs;
		double price=-1;
		String sqlStr = "select * from booktable2 where bookid = ?";
		try {
			preSql = con.prepareStatement(sqlStr);
			preSql.setInt(1, bookid);
			rs= preSql.executeQuery();
			while(rs.next())
			{
				price = rs.getDouble(5);
			}
			con.close();
			return price;
		} catch (SQLException e) {
		}
		return -1;
	}
	
	// find category of book
	public static String GetCategory(int bookid)
	{
		Connection con = ConnectDatabase.connectDB();

		PreparedStatement preSql;
		ResultSet rs;
		String category="";
		String sqlStr = "select * from booktable2 where bookid = ?";
		try {
			preSql = con.prepareStatement(sqlStr);
			preSql.setInt(1, bookid);
			rs= preSql.executeQuery();
			while(rs.next())
			{
				category = rs.getString(2);
			}
			con.close();
			return category;
		} catch (SQLException e) {
		}
		return null;
	}

}
