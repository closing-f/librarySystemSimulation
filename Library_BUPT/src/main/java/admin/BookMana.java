package admin;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 * ͼ��������
 * 
 * @author K.X
 * 
 */
import admin.*;
import window.CardLay;
/* 
 * Book Management:
 *
 * add book
 * 
 * modify book
 * 
 * delete book
 * 
 */
public class BookMana {
	
	// panel
	public JPanel bookManaPanel = new JPanel();
	// title label
	private JLabel titleLabel = new JLabel("Book Management");
	// addBookButton
	private JButton addBookButton = new JButton("Add Book");
	private JButton modifyBookButton = new JButton("Modify Book");
	private JButton deleteBookButton = new JButton("Delete Book");
	private JButton returnButton = new JButton("Return");
	// font
	private Font fontTitle = new Font("Bauhaus 93", Font.BOLD, 60);
	private Font fontNormal = new Font("方正粗黑宋简体", Font.BOLD, 25);
	// update the search table	
	public DefaultTableModel modelTable = new DefaultTableModel();
	//set background
	private JLabel Label;
	
	//identify the special card
	private CardLayout cardLayout;
	private JPanel cardPanel;
	
	public BookMana(CardLayout cardLayout,JPanel cardPanel) {
		
		this.cardLayout=cardLayout;
		this.cardPanel=cardPanel;
		
		//set background
		SetBackground();
		
		//set font,color,bound of the components
		SetFontColorBound();
		
		//add click event to button
		AddEvent();
		// add button to panel and set lay out
		AddToPanel();
	}

	public void setModel(DefaultTableModel modelTable) {
		this.modelTable = modelTable;
	}
	
	private void AddEvent() {
		// add book
		addBookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddBook addBook = new AddBook();
				addBook.setModel(modelTable);
			}
		});

		// modify book
		modifyBookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ModifyBook modifyBook = new ModifyBook();
				modifyBook.setModel(modelTable);
			}
		});

		// delete book
		deleteBookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteBook deleteBook = new DeleteBook();
				deleteBook.setModel(modelTable);
			}
		});
		
		//return main interface
		returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {       
            	cardLayout.show(cardPanel,"Main Interface");
            }
        });
	}
	
	private void SetBackground()
	{
		// set background image
		Icon i = new ImageIcon("img\\bookM.jpg");
		Label = new JLabel(i);
		Label.setBounds(0, 0, 1200, 800);
		
	}
	private void SetFontColorBound()
	{
		// set font
		titleLabel.setFont(fontTitle);
		returnButton.setFont(fontNormal);		  
		addBookButton.setFont(fontNormal);
		modifyBookButton.setFont(fontNormal);
		deleteBookButton.setFont(fontNormal);
		
		//set bound
		titleLabel.setBounds(300, 50, 800, 70);
		returnButton.setBounds(0, 0, 150,40); 
		addBookButton.setBounds(150, 200, 250, 50);
		modifyBookButton.setBounds(150, 300, 250, 50);
		deleteBookButton.setBounds(150, 400, 250, 50);
		
		//set color
		titleLabel.setForeground(Color.orange);
		addBookButton.setForeground(Color.orange);
		modifyBookButton.setForeground(Color.orange);
		deleteBookButton.setForeground(Color.orange);
		returnButton.setForeground(Color.orange);
		addBookButton.setBackground(Color.orange);
		modifyBookButton.setBackground(Color.orange);
		deleteBookButton.setBackground(Color.orange);
		returnButton.setBackground(Color.orange);
		
		//set transparent
		returnButton.setOpaque(false);
		addBookButton.setOpaque(false);
		modifyBookButton.setOpaque(false);
		deleteBookButton.setOpaque(false);			
	}
	private void AddToPanel()
	{
		bookManaPanel.setLayout(null);
		bookManaPanel.add(titleLabel);
		bookManaPanel.add(returnButton);
		bookManaPanel.add(addBookButton);
		bookManaPanel.add(modifyBookButton);
		bookManaPanel.add(deleteBookButton);
		bookManaPanel.add(Label);
		
	}
}
