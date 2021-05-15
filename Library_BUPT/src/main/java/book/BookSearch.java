package book;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import database.GetBookTable;
import window.CardLay;

/*
 * Book Search
 * 
 * search by ?   search searchButton
 * 
 * book bookfield
 * 
 */
public class BookSearch {
	
	// layered panel 
	public JLayeredPane layerPanel = new JLayeredPane();
	// label
	private JLabel searchLabel = new JLabel("Book Search");
	private JLabel methodLabel = new JLabel("Search method:");
	// field recording book
	private JTextField bookfield = new JTextField(25);
	// field size
	private Dimension fieldDimen = new Dimension(220, 30);
	// method box
	private JComboBox<String> methodBox = new JComboBox<String>();
	// search searchButton
	private JButton searchButton = new JButton("Search");
	private JButton returnButton = new JButton("Return");
	// get record from database
	public DefaultTableModel modelTable = new DefaultTableModel();
	
	//set table recording book
	JTable jTable = new JTable(modelTable);
	JScrollPane pane = new JScrollPane(jTable);
	JTableHeader head = jTable.getTableHeader();
	// fontTitle
	private Font fontTitle = new Font("Bauhaus 93", Font.BOLD, 60);
	private Font fontNormal = new Font("方正粗黑宋简体", Font.BOLD, 25);
	private Font fontText = new Font("宋体", Font.BOLD, 20);
	
	// record temporary string
	private String methodStr;
	private String book;
	private int id;
	private CardLayout cardLayout;
	private JPanel windowPanel;
	public BookSearch(CardLayout cardLayout,JPanel windowPanel) {
		
		this.cardLayout=cardLayout;
		this.windowPanel=windowPanel;
		
		//set background
		SetBackground();
		
		//set font,color,bound of components
		SetFontBoundColor();
		
		//set components transparent
		SetTransparent();
		
		//set select item 
		SetBoxAndModel();
		
		//set booktable
		SetTableAndHead();

		GetBookTable.GetBookTable(modelTable);

		AddEvent();
		
		// add component to layer panel
		AddToPanel();
		
	}

	private void AddEvent() {
		
		//return to Main Interface
		returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {               
            	cardLayout.show(windowPanel,"Main Interface");
            }
        });
		
		//get search method
		methodStr = "Category";
		methodBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {				
				if(e.getStateChange()==ItemEvent.SELECTED) {
					methodStr=(String)e.getItem();
				}
			}
		});
		
		
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modelTable.setRowCount(0);
				if(methodStr.equals("Category")) {
					book=bookfield.getText().trim();
					GetBookTable.GetBookByCate(modelTable, book);
				}else if(methodStr.equals("BookName")) {
					book=bookfield.getText().trim();
					GetBookTable.GetBookByName(modelTable, book);
				}else if(methodStr.equals("Author")) {
					book=bookfield.getText().trim();
					GetBookTable.GetBookByAuthor(modelTable, book);
				}else if(methodStr.equals("BookId")) {
					try {
						id= Integer.parseInt(bookfield.getText().trim());
						GetBookTable.GetBookById(modelTable, id);
					}catch(Exception e1) {
					}
				}
				bookfield.setText("");
			}
		});
	}
	private void SetFontBoundColor()
	{
		returnButton.setFont(fontNormal);
		returnButton.setBounds(0, 0, 140,40);
		returnButton.setForeground(Color.yellow);
		returnButton.setBackground(Color.yellow);
		
		searchLabel.setFont(fontTitle);
		searchLabel.setBounds(485, 35, 800, 100);
		searchLabel.setForeground(Color.orange);

		methodLabel.setFont(fontNormal);
		methodLabel.setBounds(180, 130, 250, 30);
		methodLabel.setForeground(Color.orange);
		
		methodBox.setFont(fontText);
		methodBox.setBounds(180, 170, 200, 40);
		
		bookfield.setFont(fontText);
		bookfield.setSize(fieldDimen);
		bookfield.setBackground(Color.cyan);
		bookfield.setBounds(480, 173, 250, 35);
		bookfield.setForeground(Color.cyan);
		
		searchButton.setFont(fontNormal);
		searchButton.setBounds(850, 170, 150, 40);
		searchButton.setForeground(Color.orange);
		searchButton.setBackground(Color.cyan);
		
		pane.setBounds(180, 250, 800, 400);
	}
	private void SetBackground()
	{
		
				Icon i = new ImageIcon("img\\booksearch.jpg");
				JLabel Label = new JLabel(i);
				Label.setBounds(0, 0, 1200, 800);
				layerPanel.add(Label, new Integer(0), 0);
	}
	private void SetTransparent()
	{
		methodBox.setOpaque(false);
		bookfield.setOpaque(false);
		searchButton.setOpaque(false);
		returnButton.setOpaque(false);

		
	}
	private void SetBoxAndModel()
	{    // set methodBox
		methodBox.setSize(fieldDimen);
		methodBox.addItem("Category");
		methodBox.addItem("BookName");
		methodBox.addItem("Author");
		methodBox.addItem("BookId");
			
		// set form table
		modelTable.addColumn("BookId", new Vector<Integer>());
		modelTable.addColumn("Category", new Vector<Integer>());
		modelTable.addColumn("BookName", new Vector<Integer>());
		modelTable.addColumn("Author", new Vector<Integer>());
		modelTable.addColumn("Price", new Vector<Integer>());
		modelTable.addColumn("State", new Vector<Integer>());
	}
	private void SetTableAndHead()
	{
				head.setPreferredSize(new Dimension(head.getWidth(), 30));
				// set head font 
				head.setFont(new Font("宋体", Font.BOLD, 20));
				// head.setForeground(Color.cyan);
				head.setForeground(Color.blue);
				head.setOpaque(false);
				// set form row height
				jTable.setRowHeight(30);
				// set font size in form
				jTable.setFont(new Font("宋体", Font.ROMAN_BASELINE, 17));
				// make word central
				DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
				renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
				jTable.setDefaultRenderer(Object.class, renderer);
				
	}
	private void AddToPanel()
	{
		layerPanel.add(searchLabel, new Integer(100), 1);
		layerPanel.add(methodLabel, new Integer(100), 2);
		layerPanel.add(methodBox, new Integer(100), 3);
		layerPanel.add(bookfield, new Integer(100), 4);
		layerPanel.add(searchButton, new Integer(100), 5);
		layerPanel.add(pane, new Integer(100), 6);
		layerPanel.add(returnButton, new Integer(100), 5);
	}
}
