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
import java.util.concurrent.Semaphore;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import database.UpdateBookTable;
import database.UpdateBookFly;
import database.ConnectDatabase;
import database.GetBookTable;
import database.GetUser;
import database.UpdateUser;
import redislock.RedisTool;
import window.CardLay;
/*
 * Book borrow or return
 * 
 * search by bookid(because bookid is the primary key in booktable)
 * 
 * show bookid,book name,book's author,book state;
 */

public class BookPurchase {
	
	public static Semaphore bookPurchaseSemaphore=new Semaphore(1);
	
	public static Semaphore GetBookFlySema()
	{
		return bookPurchaseSemaphore;	
	}
	// layered panel
	public JLayeredPane layerPanel = new JLayeredPane();
	// Label
	private JLabel titleLabel = new JLabel("Book Purchase");
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
	private JButton purchaseButton = new JButton("Purchase");
	private JButton returnButton = new JButton("Return");
	// panel
	private JPanel labelPanel = new JPanel();
	// fontTitle
	private Font fontTitle = new Font("Bauhaus 93", Font.BOLD, 50);
	private Font fontNormal = new Font("方正粗黑宋简体", Font.BOLD, 30);
	private Font fontText = new Font("宋体", Font.BOLD, 25);
	
	// userID who borrow or return book
	private String userID;
	private CardLayout layout;
	private JPanel jPanel;
	// Receive the latest records in real time
	private DefaultTableModel model = new DefaultTableModel();
	int id;
	
	public BookPurchase(CardLayout layout,JPanel jPanel) {
		this.layout=layout;
		this.jPanel=jPanel;
		Setbackground();
		
		SetFontColorBound();
		
		SetLabelPanel();
		
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
	public void setBalance(double balance)
	{
		
	}
	private void AddEvent() {
		
		//return to main interface
		returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {               
            	layout.show(jPanel,"Main Interface");
            }
        });
		
		// when tik searchButton
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					id = Integer.parseInt(inputField.getText().trim());
					Connection con = ConnectDatabase.connectDB();
					PreparedStatement preSql;
					ResultSet rs;
					String sqlStr = "select * from booktable2 where bookid = ?";
					try {
						preSql = con.prepareStatement(sqlStr);
						preSql.setInt(1, id);
						rs = preSql.executeQuery();
						boolean flag = false;
						while (rs.next()) {
							flag = true;
							getBookIdLabel.setText(rs.getString(1));
							getBookNameLabel.setText(rs.getString(3));
							getAuthorLabel.setText(rs.getString(4));
							getStateLabel.setText(rs.getString(6));
						}
						if (!flag) {
							JOptionPane.showMessageDialog(null, "Books don't exist", "Warning", JOptionPane.WARNING_MESSAGE);
							empty();
						}
						con.close();
					} catch (SQLException e1) {
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Incorrect Input", "Warning", JOptionPane.WARNING_MESSAGE);
					inputField.setText("");
				}

			}
		});

		// when purchase book
		purchaseButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String requestId=UUID.randomUUID().toString();
				String lockKey="book";
				try {
				while(!RedisTool.tryGetDistributedLock(RedisTool.GetJedis(), lockKey, requestId, 1000));
				}catch (Exception e1) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, "Redis does not start", "Warning", JOptionPane.WARNING_MESSAGE);
				}
				searchButton.doClick();
				if (getStateLabel.getText().equals("Exist")) {
					id = Integer.parseInt(getBookIdLabel.getText().trim());
					if(WhetherCanPurchase(id,userID))
					{
						UpdateBookFly.PurchaseBook(userID, id, getBookNameLabel.getText().trim());
						JOptionPane.showMessageDialog(null, "Purchase succeeds", "Congratulation", JOptionPane.WARNING_MESSAGE);
						empty();
						model.setRowCount(0);
						GetBookTable.GetBookTable(model);
					}
					else
					{
						JOptionPane.showMessageDialog(null, "The balance is insufficient, please recharge it in time", "Warning", JOptionPane.WARNING_MESSAGE);
					}
				} else if (getStateLabel.getText().equals("Be lent")) {
					JOptionPane.showMessageDialog(null, "The book has been loaned out", "Warning", JOptionPane.WARNING_MESSAGE);
					empty();
				}else if (getStateLabel.getText().equals("Be sold")) {
					JOptionPane.showMessageDialog(null, "The book has been sold", "Warning", JOptionPane.WARNING_MESSAGE);
					empty();
				} 
				else {
					JOptionPane.showMessageDialog(null, "Error!", "Warning", JOptionPane.WARNING_MESSAGE);
				}
			
				try {
					RedisTool.releaseDistributedLock(RedisTool.GetJedis(), lockKey, requestId);
				}catch (Exception e1) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, "Redis does not start", "Warning", JOptionPane.WARNING_MESSAGE);
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
		
		searchButton.setFont(fontNormal);
		searchButton.setBounds(780, 160, 140, 40);
		searchButton.setForeground(Color.black);
		searchButton.setBackground(Color.black);
		
	}
	private void Setbackground()
	{
		// set background
		Icon i = new ImageIcon("img\\bookpurchase.jpg");
		JLabel Label = new JLabel(i);
		Label.setBounds(0, 0, 1200, 800);
		layerPanel.add(Label, new Integer(0), 0);
	}
	private void SetLabelPanel()
	{
		
		

		// set font
		bookNameLabel.setFont(fontNormal);
		getBookNameLabel.setFont(fontNormal);
		AuthorLabel.setFont(fontNormal);
		getAuthorLabel.setFont(fontNormal);
		stateLabel.setFont(fontNormal);
		getStateLabel.setFont(fontNormal);
		bookIdLabel.setFont(fontNormal);
		getBookIdLabel.setFont(fontNormal);
		givebackButton.setFont(fontText);
		purchaseButton.setFont(fontText);
		
		//set color
		bookNameLabel.setForeground(Color.black);
		getBookNameLabel.setForeground(Color.black);
		AuthorLabel.setForeground(Color.black);
		getAuthorLabel.setForeground(Color.black);
		stateLabel.setForeground(Color.black);
		getStateLabel.setForeground(Color.black);
		bookIdLabel.setForeground(Color.black);
		getBookIdLabel.setForeground(Color.black);
		givebackButton.setForeground(Color.black);
		purchaseButton.setForeground(Color.black);
		givebackButton.setBackground(Color.black);
		givebackButton.setOpaque(false);
		purchaseButton.setForeground(Color.orange);
		purchaseButton.setBackground(Color.black);
		purchaseButton.setOpaque(false);
		//set bound
		bookIdLabel.setBounds(20, 20, 200, 50);
		getBookIdLabel.setBounds(200, 20, 400, 50);
		bookNameLabel.setBounds(20, 90, 200, 50);
		getBookNameLabel.setBounds(200, 90, 400, 50);
		AuthorLabel.setBounds(20, 160, 200, 50);
		getAuthorLabel.setBounds(200, 160, 400, 50);
		stateLabel.setBounds(20, 230, 200, 50);
		getStateLabel.setBounds(200, 230, 400, 50);
		givebackButton.setBounds(60, 310, 160, 50);
		purchaseButton.setBounds(100, 310, 220, 50);
		
		//add to label panel
		labelPanel.add(bookNameLabel);
		labelPanel.add(getBookNameLabel);
		labelPanel.add(AuthorLabel);
		labelPanel.add(getAuthorLabel);
		labelPanel.add(stateLabel);
		labelPanel.add(getStateLabel);
		labelPanel.add(bookIdLabel);
		labelPanel.add(getBookIdLabel);
		labelPanel.add(purchaseButton);
		
		//set panel
		labelPanel.setOpaque(false);
		labelPanel.setBounds(270, 220, 620, 450);
		labelPanel.setBackground(Color.white);
		labelPanel.setLayout(null);
		
	}
	private void SetTransparent()
	{
		returnButton.setOpaque(false);
		inputField.setOpaque(false);		
		searchButton.setOpaque(false);
	}
	private void AddToLayerPanel()
	{
		// add to layerpanel
		layerPanel.add(titleLabel, new Integer(100), 1);
		layerPanel.add(signLabel, new Integer(100), 2);
		layerPanel.add(inputField, new Integer(100), 3);
		layerPanel.add(searchButton, new Integer(100), 4);
		layerPanel.add(labelPanel, new Integer(100), 5);
		layerPanel.add(returnButton,new Integer(100),6);
	}
	//judge whether the user can afford the price,if he can,then purchase else 
	private boolean WhetherCanPurchase(int bookid,String userID)
	{
		
		double balance=GetUser.GetBalance(userID);
		int vipLevel=GetUser.GetvipLevel(userID);
		String category=GetBookTable.GetCategory(bookid);
		double price=GetBookTable.GetPrice(bookid);		
		// get purchaseDiscount from StrategyFactory which is created in singleton pattern and all strategy is packaging
		pricingStrategyFactory purchaseStrategy=pricingStrategyFactory.getInstance();
		PricingStrategy strategy=purchaseStrategy.getStrategy(category);
		//get price after discount
		double priceAfterDiscount=strategy.GetPreferentialPrice(price,vipLevel);
		
		if(priceAfterDiscount<balance)
		{
			//modify the balance of user
			UpdateUser.ModifyBalance(userID,priceAfterDiscount,-1);
			return true;
		}
		else
		return false;
	}
}
