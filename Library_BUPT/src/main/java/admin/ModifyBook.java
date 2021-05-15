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

public class ModifyBook extends JFrame{
	// panel
	private JPanel modifyBookPanel = new JPanel();
	// label
	private JLabel titleLabel = new JLabel("Modify Book");
	private JLabel categoryLabel = new JLabel("Category:");
	private JLabel booknameLabel = new JLabel("BookName:");
	private JLabel authorLabel = new JLabel("Author:");
	private JLabel priceLabel = new JLabel("Price:");
	private JLabel stateLabel = new JLabel("State:");
	private JLabel bookIdLabel = new JLabel("BookId");
	// tex bookNameField
	private JTextField bookNameField = new JTextField(20);
	private JTextField authorField = new JTextField(20);
	private JTextField priceField = new JTextField(20);
	private JTextField bookIdField = new JTextField(20);
	// comboBox
	private JComboBox<String> box = new JComboBox<String>();
	private JComboBox<String> box1 = new JComboBox<String>();
	// okButton
	private JButton okButton = new JButton("OK");
	// font
	private Font font = new Font("Bauhaus 93", Font.BOLD, 40);
	private Font font2 = new Font("方正粗黑宋简体", Font.BOLD, 25);
	private Font font3 = new Font("方正粗黑宋简体", Font.BOLD, 20);
	
	private JLabel Label;
	// update search table when modift
	public DefaultTableModel model = new DefaultTableModel();
		
	private String selectStr;
	private String stateStr;

	public ModifyBook() {
		
		// change background
		SetBackground();
		
		SetFontColorBound();
		
		//add button event
		AddEvent();
		
		AddToPanel();		
		
		
		
	}
	
	public void setModel(DefaultTableModel model) {
		this.model = model;
	}
	
	private void AddEvent() {
		//Gets the value of the drop-down list
		selectStr = "Select category";
		box.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange()==ItemEvent.SELECTED) {
					selectStr=(String)e.getItem();
				}
			}
		});
		
		stateStr = "Exist";
		box1.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange()==ItemEvent.SELECTED) {
					stateStr=(String)e.getItem();
				}
			}
		});
				
		okButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					String bookname = bookNameField.getText().trim();
					String author = authorField.getText().trim();
					String priceStr = priceField.getText().trim();
					double price=Double.valueOf(priceStr.toString());
					int bookid = Integer.parseInt(bookIdField.getText().trim());
					UpdateBookTable.modifybook(bookid, selectStr, bookname, author, price, stateStr);
					model.setRowCount(0);
					GetBookTable.GetBookTable(model);
					JOptionPane.showMessageDialog(null, "Successfully modified");
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, "Incorrect input", "Warning", JOptionPane.WARNING_MESSAGE);
				}
				
			}
		});
		
	}
	private void SetBackground()
	{
		Icon i = new ImageIcon("img\\subop.jpg");
		Label = new JLabel(i);
		Label.setBounds(0, 0, 580, 650);
		
	}
	private void SetFontColorBound()
	{
		titleLabel.setFont(font);
		titleLabel.setBounds(155, 80, 400, 100);

		bookIdLabel.setFont(font2);
		bookIdLabel.setBounds(75, 200, 250, 30);
		
		categoryLabel.setFont(font2);
		categoryLabel.setBounds(75, 250, 250, 30);

		booknameLabel.setFont(font2);
		booknameLabel.setBounds(75, 300, 250, 30);
		
		authorLabel.setFont(font2);
		authorLabel.setBounds(75, 350, 250, 30);
		
		priceLabel.setFont(font2);
		priceLabel.setBounds(75, 400, 250, 30);
		
		stateLabel.setFont(font2);
		stateLabel.setBounds(75, 450, 250, 30);
		
		// drop-down box
		box.addItem("Select category");
		GetBookTable.GetCategory(box);
		box.setFont(font3);
		box.setBounds(245, 250, 200, 30);
		box.setOpaque(false);
		
		box1.addItem("Exist");
		box1.addItem("Be lent");
		box1.setFont(font3);
		box1.setBounds(245, 450, 200, 30);
		box1.setOpaque(false);

		// text field
		bookIdField.setFont(font3);
		bookIdField.setBackground(Color.cyan);
		bookIdField.setBounds(245, 200, 200, 30);
		bookIdField.setOpaque(false);
		
		bookNameField.setFont(font3);
		bookNameField.setBackground(Color.cyan);
		bookNameField.setBounds(245, 300, 200, 30);
		bookNameField.setOpaque(false);
		
		authorField.setFont(font3);
		authorField.setBackground(Color.cyan);
		authorField.setBounds(245, 350, 200, 30);
		authorField.setOpaque(false);
		
		priceField.setFont(font3);
		priceField.setBackground(Color.cyan);
		priceField.setBounds(245, 400, 200, 30);
		priceField.setOpaque(false);

		// ok buton
		okButton.setFont(font2);
		okButton.setBounds(120, 510, 325, 35);
		okButton.setForeground(Color.orange);
		okButton.setBackground(Color.cyan);
		okButton.setOpaque(false);

	}
	private void AddToPanel()
	{
		//add commponent to panel
		modifyBookPanel.add(titleLabel);
		modifyBookPanel.add(categoryLabel);
		modifyBookPanel.add(booknameLabel);
		modifyBookPanel.add(authorLabel);
		modifyBookPanel.add(priceLabel);
		modifyBookPanel.add(stateLabel);
		modifyBookPanel.add(bookIdLabel);
		modifyBookPanel.add(box);
		modifyBookPanel.add(box1);
		modifyBookPanel.add(bookNameField);
		modifyBookPanel.add(authorField);
		modifyBookPanel.add(priceField);
		modifyBookPanel.add(bookIdField);
		modifyBookPanel.add(okButton);
		
		// set panel
		modifyBookPanel.setBounds(0, 0, 850, 650);
		modifyBookPanel.setOpaque(false);
		modifyBookPanel.setLayout(null);
		add(modifyBookPanel);
		add(Label);
		
		//set layout
		setLayout(null);
		setSize(580, 650);
		setLocationRelativeTo(null);
		
		setTitle("Modify book");		
		
		//can not change size
		setResizable(false);
		
		setVisible(true);
		
	}

}