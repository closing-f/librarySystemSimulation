package book;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import database.UpdateBookFly;
import redislock.RedisTool;
import database.ConnectDatabase;
import database.GetBookTable;
import window.CardLay;
/*
 * Book borrow or return
 * 
 * search by bookid(because bookid is the primary key in booktable)
 * 
 * show bookid,book name,book's author,book state;
 */
public class BorrowingReturning {
	
	// layered panel
	public JLayeredPane layerPanel = new JLayeredPane();
	// Label
	private JLabel titleLabel = new JLabel("Book Borrow Or Return");
	private JLabel signLabel = new JLabel("Input BookId:");
	private JLabel bookNameLabel = new JLabel("BookName:");
	private JLabel getBookNameLabel = new JLabel();
	private JLabel AuthorLabel = new JLabel("Author:");
	private JLabel getAuthorLabel = new JLabel();
	private JLabel stateLabel = new JLabel("State:");
	private JLabel getStateLabel = new JLabel();
	private JLabel bookIdLabel = new JLabel("BookId:");
	private JLabel getBookIdLabel = new JLabel();

	// text inputField
	private JTextField inputField = new JTextField(20);
	// button
	private JButton searchButton = new JButton("Search");
	private JButton givebackButton = new JButton("Giveback");
	private JButton borrowButton = new JButton("Borrow");
	private JButton returnButton = new JButton("Return");
	// panel
	private JPanel labelPanel = new JPanel();
	// fontTitle
	private Font fontTitle = new Font("Bauhaus 93", Font.BOLD, 50);
	private Font fontNormal = new Font("方正粗黑宋简体", Font.BOLD, 30);
	private Font fontText = new Font("宋体", Font.BOLD, 25);
	// userID who borrow or return book
	private String userID;
	private CardLayout cardLayout;
	private JPanel windowPanel;
	// Receive the latest records in real time
	private DefaultTableModel model = new DefaultTableModel();
	//save selected item
	int bookId;
	
	public BorrowingReturning(CardLayout cardLayout,JPanel windowPanel) {
		
		this.cardLayout=cardLayout;
		this.windowPanel=windowPanel;
		//set background
		Setbackground();
		
		//set font color bound of components
		SetFontColorBound();
		
		//set labelpanel
		SetLabelPanel();
		
		//set components transparent
		SetTransparent();

		AddEvent();

		AddToLayerPanel();
		
	}

	public void setUser(String userID) {
		this.userID = userID;
	}

	public void setModel(DefaultTableModel model) {
		this.model = model;
	}

	private void AddEvent() {
		
		//return to main interface
		returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {               
            	cardLayout.show(windowPanel,"Main Interface");
            }
        });
		
		// when tik searchButton
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				try {
					bookId = Integer.parseInt(inputField.getText().trim());
					Connection con = ConnectDatabase.connectDB();
					PreparedStatement preSql;
					ResultSet rs;
					String sqlStr = "select * from booktable2 where bookid = ?";
					try {
						preSql = con.prepareStatement(sqlStr);
						preSql.setInt(1, bookId);
						rs = preSql.executeQuery();
						boolean flag = false;
						while (rs.next()) 
						{
							flag = true;
							getBookIdLabel.setText(rs.getString(1));
							getBookNameLabel.setText(rs.getString(3));
							getAuthorLabel.setText(rs.getString(4));
							getStateLabel.setText(rs.getString(6));
						}
						if (!flag) 
						{
							JOptionPane.showMessageDialog(null, "Books don't exist", "Warning", JOptionPane.WARNING_MESSAGE);
							empty();
						}
						con.close();
					} catch (SQLException e1) 
					{
					}
				} 
				catch (Exception e1) 
				{
					JOptionPane.showMessageDialog(null, "Incorrect input", "Warning", JOptionPane.WARNING_MESSAGE);
					inputField.setText("");
				}

			}
		});

		// when borrow book
		borrowButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String requestId=UUID.randomUUID().toString();
				String lockKey="book";
				try {
				while(!RedisTool.tryGetDistributedLock(RedisTool.GetJedis(), lockKey, requestId, 1000));
				}catch (Exception e1) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, "Redis does not start", "Warning", JOptionPane.WARNING_MESSAGE);
				}
				searchButton.doClick();
				if (getStateLabel.getText().equals("Exist")) 
				{
					bookId = Integer.parseInt(getBookIdLabel.getText().trim());
					UpdateBookFly.BorrowBook(userID, bookId, getBookNameLabel.getText().trim());
					JOptionPane.showMessageDialog(null, "Borrowed successfully!", "congratulation", JOptionPane.WARNING_MESSAGE);
					empty();
					model.setRowCount(0);
					GetBookTable.GetBookTable(model);
				} 
				else if (getStateLabel.getText().equals("Be lent")) 
				{
					JOptionPane.showMessageDialog(null, "The book has been loaned out.", "Warning", JOptionPane.WARNING_MESSAGE);
					empty();
				} else 
				{
					JOptionPane.showMessageDialog(null, "error", "Warning", JOptionPane.WARNING_MESSAGE);
				}
				try {
					RedisTool.releaseDistributedLock(RedisTool.GetJedis(), lockKey, requestId);
				}catch (Exception e1) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, "Redis does not start", "Warning", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		// giveback book
		givebackButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (getStateLabel.getText().equals("Be lent")) 
				{
					if(UpdateBookFly.ChangeBookfly(userID, bookId)) 
					{
						bookId = Integer.parseInt(getBookIdLabel.getText().trim());
						UpdateBookFly.ReturnBook(userID, bookId);
						JOptionPane.showMessageDialog(null, "Return the book success");
						empty();
						model.setRowCount(0);
						GetBookTable.GetBookTable(model);
					}
					else 
					{
						JOptionPane.showMessageDialog(null, "You didn't borrow this book!", "congratulation ", JOptionPane.WARNING_MESSAGE);
					}
				} 
				else if (getStateLabel.getText().equals("Exist")) 
				{
					JOptionPane.showMessageDialog(null, "This book is in the library.", "Warning", JOptionPane.WARNING_MESSAGE);
					empty();
				} 
				else 
				{
					JOptionPane.showMessageDialog(null, "mistake ", "Warning", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}
	
	public void empty() {
		getBookNameLabel.setText("");
		getAuthorLabel.setText("");
		getStateLabel.setText("");
		getBookIdLabel.setText("");
	}
	private void SetFontColorBound()
	{
		returnButton.setFont(fontText);
		returnButton.setBounds(0, 0, 120,40);
		returnButton.setForeground(Color.cyan);
		returnButton.setBackground(Color.black);
		
		titleLabel.setFont(fontTitle);
		titleLabel.setBounds(400, 35, 800, 100);
		titleLabel.setForeground(Color.orange);

		signLabel.setFont(fontNormal);
		signLabel.setBounds(260, 164, 250, 30);
		signLabel.setForeground(Color.black);

		inputField.setFont(fontNormal);
		inputField.setBackground(Color.black);
		inputField.setBounds(480, 164, 250, 30);
		inputField.setForeground(Color.black);
		
		bookIdLabel.setForeground(Color.orange);
		bookNameLabel.setForeground(Color.orange);
		AuthorLabel.setForeground(Color.orange);
		stateLabel.setForeground(Color.orange);
		searchButton.setForeground(Color.orange);
		signLabel.setForeground(Color.orange);
		givebackButton.setForeground(Color.cyan);
		borrowButton.setForeground(Color.cyan);
		
		searchButton.setFont(fontNormal);
		searchButton.setBounds(780, 160, 140, 40);
		searchButton.setForeground(Color.black);
		searchButton.setBackground(Color.black);
		
	}
	private void Setbackground()
	{
		Icon i = new ImageIcon("img\\bookfly.jpg");
		JLabel Label = new JLabel(i);
		Label.setBounds(0, 0, 1200, 800);
		layerPanel.add(Label, new Integer(0), 0);
	}
	private void SetLabelPanel()
	{
		labelPanel.setBounds(270, 220, 620, 450);
		labelPanel.setBackground(Color.white);
		labelPanel.setLayout(null);

		//  button in labelPanel 
		bookNameLabel.setFont(fontNormal);
		getBookNameLabel.setFont(fontNormal);
		AuthorLabel.setFont(fontNormal);
		getAuthorLabel.setFont(fontNormal);
		stateLabel.setFont(fontNormal);
		getStateLabel.setFont(fontNormal);
		bookIdLabel.setFont(fontNormal);
		getBookIdLabel.setFont(fontNormal);
		givebackButton.setFont(fontText);
		borrowButton.setFont(fontText);
		
		//set color
		bookNameLabel.setForeground(Color.orange);
		getBookNameLabel.setForeground(Color.cyan);
		AuthorLabel.setForeground(Color.orange);
		getAuthorLabel.setForeground(Color.cyan);
		stateLabel.setForeground(Color.orange);
		getStateLabel.setForeground(Color.cyan);
		bookIdLabel.setForeground(Color.orange);
		getBookIdLabel.setForeground(Color.cyan);
		givebackButton.setForeground(Color.cyan);
		borrowButton.setForeground(Color.cyan);
		inputField.setForeground(Color.cyan);
		searchButton.setForeground(Color.cyan);
		givebackButton.setForeground(Color.cyan);
		givebackButton.setBackground(Color.orange);
		givebackButton.setOpaque(false);
		borrowButton.setForeground(Color.cyan);
		borrowButton.setBackground(Color.orange);
		borrowButton.setOpaque(false);
		
		//set bound
		bookIdLabel.setBounds(30, 20, 200, 50);
		getBookIdLabel.setBounds(230, 20, 400, 50);
		bookNameLabel.setBounds(30, 90, 200, 50);
		getBookNameLabel.setBounds(230, 90, 400, 50);
		AuthorLabel.setBounds(30, 160, 200, 50);
		getAuthorLabel.setBounds(230, 160, 400, 50);
		stateLabel.setBounds(30, 230, 200, 50);
		getStateLabel.setBounds(230, 230, 400, 50);
		givebackButton.setBounds(30, 310, 160, 50);
		borrowButton.setBounds(460, 310, 120, 50);
	
		//add label to labelpanel
		labelPanel.add(bookNameLabel);
		labelPanel.add(getBookNameLabel);
		labelPanel.add(AuthorLabel);
		labelPanel.add(getAuthorLabel);
		labelPanel.add(stateLabel);
		labelPanel.add(getStateLabel);
		labelPanel.add(bookIdLabel);
		labelPanel.add(getBookIdLabel);
		labelPanel.add(givebackButton);
		labelPanel.add(borrowButton);
		labelPanel.setOpaque(false);
		
	}
	private void SetTransparent()
	{
		returnButton.setOpaque(false);
        		
		inputField.setOpaque(false);
		
		searchButton.setOpaque(false);
	}
	private void AddToLayerPanel()
	{
		layerPanel.add(titleLabel, new Integer(100), 1);
		layerPanel.add(signLabel, new Integer(100), 2);
		layerPanel.add(inputField, new Integer(100), 3);
		layerPanel.add(searchButton, new Integer(100), 4);
		layerPanel.add(labelPanel, new Integer(100), 5);
		layerPanel.add(returnButton,new Integer(100),6);
	}
}
