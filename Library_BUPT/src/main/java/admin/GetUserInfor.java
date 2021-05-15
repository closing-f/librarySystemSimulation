package admin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import database.ConnectDatabase;

/*
 * User Information
 */
public class GetUserInfor extends JFrame {

	// panel
	private JPanel userInforPanel = new JPanel();
	// layerPanel
	private JLayeredPane jLayeredPane = new JLayeredPane();
	// label
	private JLabel titleLabel = new JLabel("User Information");
	// font
	private Font font = new Font("Bauhaus 93", Font.BOLD, 40);
	// modelTable to show userID information
	public DefaultTableModel modelTable = new DefaultTableModel();
	//save background
	JLabel Label;
	
	public GetUserInfor() {
		
		SetBackground();
		
		SetModelandHead();
			
		SetPanel();	
		
		GetUserInfor(modelTable);
		
	}
	
	private void GetUserInfor(DefaultTableModel modeltable) {
		Connection con = ConnectDatabase.connectDB();
		PreparedStatement preSql;
		ResultSet rs;
		String sqlStr = "select * from usertable2";
		try {
			preSql = con.prepareStatement(sqlStr);
			rs = preSql.executeQuery();
			while (rs.next()) {
				String userID = rs.getString(1);
				String name = rs.getString(2);
				double balance = rs.getDouble(5);
				int flag = rs.getInt(4);
				int viplevel=rs.getInt(6);
				String admin;
				if (flag == 1) {
					admin = "yes";
				} else {
					admin = "no";
				}
				modeltable.addRow(new Vector<Object>(Arrays.asList(userID, name, balance,viplevel,admin)));
			}
			con.close();
		} catch (SQLException e) {
		}
	}
	private void SetBackground()
	{
		Icon i = new ImageIcon("img\\bookflyrecord.jpg");
		Label = new JLabel(i);
		Label.setBounds(0, 0, 800, 600);
		
	}
	private void SetModelandHead()
	{
		//set label
		titleLabel.setFont(font);
		titleLabel.setBounds(315, 80, 400, 100);
		
		//set UserInfor
		modelTable.addColumn("UserID", new Vector<Integer>());
		modelTable.addColumn("Name", new Vector<Integer>());
		modelTable.addColumn("Balance", new Vector<Integer>());
		modelTable.addColumn("VipLevel", new Vector<Integer>());
		modelTable.addColumn("IfAdmin", new Vector<Integer>());
		JTable jTable = new JTable(modelTable);
		JScrollPane pane = new JScrollPane(jTable);
		pane.setBounds(80, 160, 640, 350);
		jLayeredPane.add(pane);

		JTableHeader head = jTable.getTableHeader();
		// Set the size of the table header
		head.setPreferredSize(new Dimension(head.getWidth(), 30));
		// Sets the font size of the table header
		head.setFont(new Font("����", Font.BOLD, 20));
		// head.setForeground(Color.cyan);
		head.setBackground(Color.cyan);
		// Sets the row width of the table
		jTable.setRowHeight(30);
		// Sets the font size in a table row
		jTable.setFont(new Font("����", Font.ROMAN_BASELINE, 17));
		//Sets the content in the table to center
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		jTable.setDefaultRenderer(Object.class, renderer);			
	}
	private void SetPanel()
	{
		setLayout(null);
		setSize(800, 600);
		setLocationRelativeTo(null);
		userInforPanel.add(titleLabel);
		userInforPanel.setBounds(0, 0, 800, 200);
		userInforPanel.setOpaque(false);
		userInforPanel.setLayout(null);
		
		setContentPane(jLayeredPane);
		setTitle("User Information");
		setResizable(false);
		add(userInforPanel);
		add(Label);
		setVisible(true);
		
	}
}
