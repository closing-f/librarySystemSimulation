package admin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import database.ConnectDatabase;
import database.GetBookTable;
import database.GetBookFly;

/*
 * get bookflyrecord by userID or bookId
 */
public class BookflyAdmin extends JFrame {
	
	private static final long serialVersionUID = 2L;
	// panel
	private JPanel bookflyPanel = new JPanel();
	// layer pane
	private JLayeredPane layerPanel = new JLayeredPane();
	// label
	private JLabel titleLabel = new JLabel("BookFly Record");
	private JLabel selectLabel = new JLabel("Select method");
	// bookfly Field
	private JTextField bookflyField = new JTextField(20);
	// drop-down selectBox
	private JComboBox<String> selectBox = new JComboBox<String>();
	// searchButton
	private JButton searchButton = new JButton("Search");
	// fontTitle
	private Font fontTitle = new Font("Bauhaus 93", Font.BOLD, 40);
	private Font fontNormal = new Font("方正粗黑宋简体", Font.BOLD, 20);
	private Font fontText = new Font("方正粗黑宋简体", Font.BOLD, 18);
	//table which shows bookfly
	public DefaultTableModel model = new DefaultTableModel();
	//back ground image
	JLabel Label;
	//save the selected item
	private String selectStr;
	private int selectIndex;

	public BookflyAdmin() {
		
		//set background
		SetBackground();
		
		//set font,color,bound of the components
		SetFontColorBound();
		
		//add click event to button
		AddEvent();
		
		//set the table 
		SetModelHead();
		
		// add button to panel and set lay out
		AddToPanel();		
		
		
	}

	private void AddEvent() {

		//Gets the value of the drop-down list
		selectStr = "by userID";
		selectBox.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getStateChange() == ItemEvent.SELECTED) {
					selectStr = (String) e.getItem();
				}
			}
		});

		// search button
		searchButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				model.setRowCount(0);
				if (selectStr.equals("by userID")) {
					try {
						String user = bookflyField.getText().trim();
						GetBookFly.UserBookfly(model, user);
					} catch (Exception e1) {
					}
				} else if (selectStr.equals("by bookId")) {
					try {
						selectIndex = Integer.parseInt(bookflyField.getText().trim());
						GetBookFly.IdBookfly(model, selectIndex);
					} catch (Exception e1) {
					}
				}
				bookflyField.setText("");
			}
		});
	}
	private void SetBackground()
	{
		// set background image
		Icon i = new ImageIcon("img\\bookflyrecord.jpg");
		Label = new JLabel(i);
		Label.setBounds(0, 0, 800, 600);
		setLayout(null);
		setSize(800, 600);
		setLocationRelativeTo(null);	
		
	}
	private void SetFontColorBound()
	{
		titleLabel.setFont(fontTitle);
		titleLabel.setBounds(200, 30, 400, 100);

		selectLabel.setFont(fontText);
		selectLabel.setBounds(95, 100, 250, 30);	
		
		// drop-down box
		selectBox.addItem("by userID");
		selectBox.addItem("by bookId");
		selectBox.setFont(fontText);
		selectBox.setBounds(95, 135, 160, 30);
		// selectBox.setBackground(Color.cyan);
		selectBox.setOpaque(false);

		// set textbox 
		bookflyField.setFont(fontNormal);
		bookflyField.setBackground(Color.cyan);
		bookflyField.setBounds(325, 135, 200, 30);
		bookflyField.setOpaque(false);

		//set search button 
		searchButton.setFont(fontNormal);
		searchButton.setBounds(590, 135, 120, 35);
		searchButton.setBackground(Color.cyan);
		searchButton.setOpaque(false);
	}
	private void AddToPanel()
	{
		bookflyPanel.add(titleLabel);
		bookflyPanel.add(selectLabel);
		bookflyPanel.add(selectBox);
		bookflyPanel.add(bookflyField);
		bookflyPanel.add(searchButton);
		
		bookflyPanel.setBounds(0, 0, 850, 250);
		bookflyPanel.setOpaque(false);
		bookflyPanel.setLayout(null);
		

		setContentPane(layerPanel);
		add(bookflyPanel);
		add(Label);
		setVisible(true);
			
	}
	private void SetModelHead()
	{
		// form
		model.addColumn("UserID", new Vector<Integer>());
		model.addColumn("BookId", new Vector<Integer>());
		model.addColumn("BookName", new Vector<Integer>());
		model.addColumn("BorrowTime", new Vector<Integer>());
		model.addColumn("ReturnTime", new Vector<Integer>());
		model.addColumn("State", new Vector<Integer>());
		
		JTable jTable;
		JScrollPane pane;
		jTable = new JTable(model);
		pane = new JScrollPane(jTable);
		pane.setBounds(80, 180, 690, 340);
		layerPanel.add(pane);
		//  record of all borrowed books
		GetBookFly.GetBookfly(model);
		
		JTableHeader head = jTable.getTableHeader();
		// set size of header
		head.setPreferredSize(new Dimension(head.getWidth(), 30));
		// set font of header
		head.setFont(new Font("����", Font.BOLD, 20));
		// head.setForeground(Color.cyan);
		head.setBackground(Color.cyan);
		// set form width
		jTable.setRowHeight(30);
		// set font in form
		jTable.setFont(new Font("����", Font.ROMAN_BASELINE, 17));
		// set content central
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		jTable.setDefaultRenderer(Object.class, renderer);
		setTitle("bookfly record");
		setResizable(false);
		
	}
}
