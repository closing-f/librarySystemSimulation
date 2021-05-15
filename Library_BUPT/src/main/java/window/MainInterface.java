package window;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;

import database.GetUser;

/*Main Interface :
 * 
 * Book Search
 * 
 * Book borrow
 * 
 * Account Management
 * 
 * if(admin)Book Management
 */

public class MainInterface {

	public JLayeredPane layerPanel = new JLayeredPane();

	// Label
	private JLabel titleLabel = new JLabel("Welcome to the BUPT library");

	// Button
	private JButton returnButton;
	private JButton searchButton;
	private JButton accountButton;
	private JButton purchaseButton;
	private JButton bookManaButton;
	private JButton newUserButton;
	// font
	private Font fontTitle = new Font("Bauhaus 93", Font.BOLD, 50);
	private Font fontNormal = new Font("方正粗黑宋简体", Font.BOLD, 30);
	private Font fontText= new Font("方正粗黑宋简体", Font.BOLD, 25);
	private CardLayout cardLayout;
	private JPanel cardPanel;
	public MainInterface(String user,CardLayout layout,JPanel jPanel) {

		this.cardLayout=layout;
		this.cardPanel=jPanel;
		
		int wetherAdmin = 0;
		if (GetUser.IfAdmin(user)) {
			wetherAdmin = 1;
		}
		// set background picture
		SetBackground();

		// set button
		SetButton(wetherAdmin);

		// set color, font, bounds of buttons
		SetFontCorlorBound(wetherAdmin);

		// make buttons transparent
		SetTransparent(wetherAdmin);

		// add button event
		AddEvent(wetherAdmin);

		// add button to panel
		AddToPanel(wetherAdmin);
	}

	private void SetButton(int wetherAdmin) {
		searchButton = new JButton("Book Search");
		returnButton = new JButton("Book Return or Borrow");
		purchaseButton = new JButton("Book purchase");
		accountButton = new JButton("Account Management");
		newUserButton=new JButton("Add New User");
		if (wetherAdmin == 1) {
			bookManaButton = new JButton("Book Management");
		}

	}

	private void SetFontCorlorBound(int wetherAdmin) {
		
		titleLabel.setFont(fontTitle);
		titleLabel.setBounds(200, 50, 1000, 200);
		titleLabel.setForeground(Color.black);
		
		
		searchButton.setFont(fontNormal);
		searchButton.setBackground(Color.cyan);
		searchButton.setBounds(200, 200, 350, 50);
		searchButton.setForeground(Color.orange);

		returnButton.setFont(fontText);
		returnButton.setBackground(Color.cyan);
		returnButton.setBounds(200, 300, 350, 50);
		returnButton.setForeground(Color.orange);

		purchaseButton.setFont(fontNormal);
		purchaseButton.setBackground(Color.cyan);
		purchaseButton.setBounds(200, 400, 350, 50);
		purchaseButton.setForeground(Color.orange);
		
		newUserButton.setFont(fontNormal);
		newUserButton.setBackground(Color.cyan);
		newUserButton.setBounds(600, 200, 350, 50);
		newUserButton.setForeground(Color.orange);
		
		accountButton.setFont(fontNormal);
		accountButton.setBackground(Color.cyan);
		accountButton.setBounds(600, 300, 350, 50);
		accountButton.setForeground(Color.orange);
		
		
		if (wetherAdmin == 1) {
			bookManaButton.setFont(fontNormal);
			bookManaButton.setBackground(Color.cyan);
			bookManaButton.setBounds(600, 400, 350, 50);
			bookManaButton.setForeground(Color.orange);
		}

	}

	private void SetTransparent(int wetherAdmin) {
		searchButton.setOpaque(false);
		accountButton.setOpaque(false);
		returnButton.setOpaque(false);
		purchaseButton.setOpaque(false);
		newUserButton.setOpaque(false);
		if (wetherAdmin == 1) {
			bookManaButton.setOpaque(false);
		}
	}

	private void AddEvent(int wetherAdmin) {
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "Book Search");
			}
		});

		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "Book Borrow And Return");
			}
		});
		
		purchaseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(cardPanel, "Book Purchase");
			}
		});
		newUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.cachedThreadPool.execute(() -> 
				{
		        	new Land();            
		        });
			}
		});
		accountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (wetherAdmin == 1)
					cardLayout.show(cardPanel, "Admin Management");
				else
					cardLayout.show(cardPanel, "Account Management");

			}
		});

		if (wetherAdmin == 1) {
			bookManaButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cardLayout.show(cardPanel, "Book Management");
				}
			});
		}

	}

	private void AddToPanel(int wetherAdmin) {

		layerPanel.add(titleLabel, new Integer(100), 1);
		layerPanel.add(searchButton, new Integer(100), 2);
		layerPanel.add(accountButton, new Integer(100), 2);
		layerPanel.add(returnButton, new Integer(100), 2);
		layerPanel.add(purchaseButton, new Integer(100), 2);
		layerPanel.add(newUserButton,new Integer(100), 2);
		if (wetherAdmin == 1) {
			layerPanel.add(bookManaButton, new Integer(100), 2);
		}
	}

	private void SetBackground() {
		Icon i = new ImageIcon("img\\Main1.jpg");
		JLabel Label = new JLabel(i);
		Label.setBounds(0, 0, 1200, 800);
		layerPanel.add(Label, new Integer(0), 0);
	}
}
