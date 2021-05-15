package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class ConnectDatabase {

	public static Connection connectDB() {
		
		 final String dirverString="com.mysql.cj.jdbc.Driver";
		 final String url = "jdbc:mysql://121.36.4.61:3306/bupt2018dbs24db?serverTimezone=UTC";
		 final String user="bupt2018dbs24";
		 final String pass="bupt2018dbs24@123";
		try {
			Class.forName(dirverString);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "set error", "Warning", JOptionPane.WARNING_MESSAGE);
		}

		Connection con = null;

		try {
			con = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "connect error", "Warning", JOptionPane.WARNING_MESSAGE);
		}

		return con;
	}
}
