package admin;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


import database.UpdateBookTable;
import database.GetBookTable;

/*
 * Add Book to booktable
 * 
 * category:
 * 
 * bookname:
 * 
 * author:
 * 
 * price(yuan):
 * 
 */
public class AddBook extends JFrame{
	
	private static final long serialVersionUID = 2L;
	// panel
	private JPanel addbookPanel = new JPanel();
	// label
	private JLabel titleLabel = new JLabel("Add Book");
	private JLabel categoryLabel = new JLabel("Category");
	private JLabel booknameLabel = new JLabel("BookName:");
	private JLabel authorLabel = new JLabel("Author:");
	private JLabel priceLabel = new JLabel("Price(yuan):");
	// text field
	private JTextField booknameField = new JTextField(20);
	private JTextField AuthorField = new JTextField(20);
	private JTextField priceField = new JTextField(20);
	// drop-down box 
	private JComboBox<String> box = new JComboBox<String>();
	// okButton
	private JButton okButton = new JButton("OK");
	// fontTitle
	private Font fontTitle = new Font("Bauhaus 93", Font.BOLD, 40);
	private Font fontNormal = new Font("方正粗黑宋简体", Font.BOLD, 25);
	private Font fontText = new Font("方正粗黑宋简体", Font.BOLD, 20);
	// in order to update the table in search field
	public DefaultTableModel tableModel = new DefaultTableModel();
	//save the selected category string
	private String categoryStr;
	//set background
	JLabel Label;

	public AddBook() {
		
		//set background
		SetBackground();
		
		//set font,color,bound of the components
		SetFontColorBound();
		
		//add click event to button
		AddEvent();
		// add button to panel and set lay out
		AddToPanel();
		
	}
	
	public void setModel(DefaultTableModel tableModel) {
		this.tableModel = tableModel;
	}
	
	private void AddEvent() {		
		//get selected item
		categoryStr = "";
		box.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED) {
					categoryStr=(String)e.getItem();
				}
			}
		});
		// add book to booktable
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String bookname = booknameField.getText().trim();
					String author = AuthorField.getText().trim();
					String priceStr = priceField.getText().trim();
					double price=Double.valueOf(priceStr.toString());
					UpdateBookTable.AddBook(categoryStr, bookname, author, price);
					tableModel.setRowCount(0);
					GetBookTable.GetBookTable(tableModel);
					JOptionPane.showMessageDialog(null, "Successfully added");
				}catch (Exception e1) 
				{
					JOptionPane.showMessageDialog(null, "Incorrect input", "Warning", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
	}	
	
	private void SetBackground()
	{
		Icon i = new ImageIcon("img\\subop.jpg");
		Label= new JLabel(i);
		Label.setBounds(0, 0, 580, 650);				
	}
	
	private void SetFontColorBound()
	{
		
		titleLabel.setFont(fontTitle);
		titleLabel.setBounds(195, 80, 400, 100);

		categoryLabel.setFont(fontNormal);
		categoryLabel.setBounds(50, 200, 250, 30);

		booknameLabel.setFont(fontNormal);
		booknameLabel.setBounds(50, 260, 250, 30);
		
		authorLabel.setFont(fontNormal);
		authorLabel.setBounds(50, 320, 250, 30);
		
		priceLabel.setFont(fontNormal);
		priceLabel.setBounds(50, 380, 250, 30);
		
		//set categoryStr box
		box.addItem("please select");
		
		//get category from category table
		GetBookTable.GetCategory(box);
		
		box.setFont(fontText);
		box.setBounds(245, 200, 200, 30);
		box.setOpaque(false);

		booknameField.setFont(fontText);
		booknameField.setBackground(Color.cyan);
		booknameField.setBounds(245, 260, 200, 30);
		booknameField.setOpaque(false);
		
		AuthorField.setFont(fontText);
		AuthorField.setBackground(Color.cyan);
		AuthorField.setBounds(245, 320, 200, 30);
		AuthorField.setOpaque(false);
		
		priceField.setFont(fontText);
		priceField.setBackground(Color.cyan);
		priceField.setBounds(245, 380, 200, 30);
		priceField.setOpaque(false);

		okButton.setFont(fontNormal);
		okButton.setBounds(120, 455, 325, 35);
		okButton.setForeground(Color.orange);
		okButton.setBackground(Color.cyan);
		okButton.setOpaque(false);

	}
	private void AddToPanel()
	{
		addbookPanel.add(titleLabel);
		addbookPanel.add(categoryLabel);
		addbookPanel.add(booknameLabel);
		addbookPanel.add(authorLabel);
		addbookPanel.add(priceLabel);
		addbookPanel.add(box);
		addbookPanel.add(booknameField);
		addbookPanel.add(AuthorField);
		addbookPanel.add(priceField);
		addbookPanel.add(okButton);
		add(addbookPanel);
		add(Label);
		
		//set addbook panel
		addbookPanel.setBounds(0, 0, 850, 650);
		addbookPanel.setOpaque(false);
		addbookPanel.setLayout(null);
		
		//set layout
		setLayout(null);
		setSize(580, 650);
		setLocationRelativeTo(null);
		setTitle("AddBook");	
		setResizable(false);
		
		setVisible(true);
		
		
	}

}
