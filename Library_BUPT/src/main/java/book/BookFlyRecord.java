package book;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import database.ConnectDatabase;
/*
 * BookFly Record
 */
public class BookFlyRecord extends JFrame {
	
	// panel
	private JPanel bookflyPanel = new JPanel();
	// layer pane
	private JLayeredPane layerPane = new JLayeredPane();
	// label
	private JLabel titleLabel = new JLabel("BookFly Record");
	// fontTitle
	private Font fontTitle = new Font("Bauhaus 93", Font.BOLD, 40);
	// form
	public DefaultTableModel model = new DefaultTableModel();
	//background picture
	JLabel Label;
	
	private String userID;

	public BookFlyRecord(String userID) {
		this.userID=userID;
		//set background
		SetBackground();
		
		//set font,color,bound of the components
		SetFontColorBound();
		
		//set form of bookfly record
		SetFormAndHeader();
		
		// add button to panel and set lay out
		AddToLayerPanel();
		
	}
	//
	private void GetBookfly(DefaultTableModel model, String userID) 
	{
		Connection con = ConnectDatabase.connectDB();
		PreparedStatement preSql;
		ResultSet rs;
		String sqlStr = "select * from bookfly where userID = ?";
		try 
		{
			
			preSql = con.prepareStatement(sqlStr);
			preSql.setString(1, userID);
			rs = preSql.executeQuery();
			boolean flag = false;
			while (rs.next()) {
				flag = true;
				int bookid = rs.getInt(3);
				String bookname = rs.getString(4);
				Date date = rs.getDate(5);
				Date date2 = rs.getDate(6);
				String state = rs.getString(7);
				model.addRow(new Vector<Object>(Arrays.asList(bookid, bookname, date, date2, state)));
			}
			if (!flag) 
			{
				JOptionPane.showMessageDialog(null, "You haven't borrowed any books yet!");
			}
			con.close();
		} 
		catch (SQLException e) 
		{
		}
	}
	private void SetFontColorBound()
	{
		titleLabel.setFont(fontTitle);
		titleLabel.setBounds(315, 80, 400, 100);	
	}
	
	// set background
	private void SetBackground()
	{
		
		Icon i = new ImageIcon("img\\bookflyrecord.jpg");
		Label= new JLabel(i);
		Label.setBounds(0, 0, 800, 600);
		setLayout(null);
		setSize(800, 600);
		setLocationRelativeTo(null);
	}
	private void SetFormAndHeader()
	{
		bookflyPanel.add(titleLabel);
		// form
		model.addColumn("BookId", new Vector<Integer>());
		model.addColumn("BookName", new Vector<Integer>());
		model.addColumn("FlyTime", new Vector<Integer>());
		model.addColumn("ReturnTime", new Vector<Integer>());
		model.addColumn("State", new Vector<Integer>());
		JTable jTable = new JTable(model);
		JScrollPane pane = new JScrollPane(jTable);
		pane.setBounds(80, 160, 640, 350);
		layerPane.add(pane);

		JTableHeader head = jTable.getTableHeader();
		// Set the size of the table header
		head.setPreferredSize(new Dimension(head.getWidth(), 30));
		// Sets the font size of the table header
		head.setFont(new Font("宋体", Font.BOLD, 20));
		head.setBackground(Color.cyan);
		// Sets the row width of the table
		jTable.setRowHeight(30);
		// Sets the font size in a table row
		jTable.setFont(new Font("宋体", Font.ROMAN_BASELINE, 17));
		//Sets the content in the table to center	
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		jTable.setDefaultRenderer(Object.class, renderer);

		GetBookfly(model, userID);
		
	}
	private void AddToLayerPanel()
	{
		bookflyPanel.setBounds(0, 0, 800, 200);
		bookflyPanel.setOpaque(false);
		bookflyPanel.setLayout(null);
		setContentPane(layerPane);
		add(bookflyPanel);
		add(Label);
		
		setTitle("bookfly record");
		setResizable(false);
		setVisible(true);
	}
}
