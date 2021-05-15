package window;

import admin.BookMana;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.*;

import database.GetUser;
import book.*;
/*Main interface     
  Book purchase 
  Book borrowReturn
  Account Management				* 
 */
public class CardLay extends JFrame {

	public  CardLayout cardLayout = new CardLayout(0, 0);
	public  JPanel jPanel = new JPanel(cardLayout);
	private Font fontNormal = new Font("宋体", Font.BOLD, 20);

	public CardLay(String user) {

		// Change window icon
		Toolkit t = Toolkit.getDefaultToolkit();
		Image image = t.getImage("img\\book.png");
		setIconImage(image);

		jPanel.setFont(fontNormal);
		
		//create main interface
		MainInterface mainInter = new MainInterface(user,cardLayout,jPanel);
		jPanel.add("Main Interface", mainInter.layerPanel);
		
		//create booksearch
		BookSearch search = new BookSearch(cardLayout,jPanel);
		jPanel.add("Book Search", search.layerPanel);
		
		//create bookPurchase
		BookPurchase purchase=new BookPurchase(cardLayout,jPanel);
		purchase.setUser(user);
		purchase.setModel(search.modelTable);
		jPanel.add("Book Purchase", purchase.layerPanel);
		
		//create book borrow or return
		BorrowingReturning returning = new BorrowingReturning(cardLayout,jPanel);
		returning.setUser(user);
		returning.setModel(search.modelTable);
		jPanel.add("Book Borrow And Return", returning.layerPanel);
		
		//if user is admin,then create book management and admin account ;else create accountManagement
		if (GetUser.IfAdmin(user)) {
			AdminAccount admin = new AdminAccount(user,cardLayout,jPanel);
			admin.setUser(user);
			admin.setFrame(this);
			jPanel.add("Admin Management", admin.adminPanel);
			BookMana bookAdmin = new BookMana(cardLayout,jPanel);
			bookAdmin.setModel(search.modelTable);
			jPanel.add("Book Management", bookAdmin.bookManaPanel);

		} else {
			AccountManagement management = new AccountManagement(user,cardLayout,jPanel);
			management.setUser(user);
			management.setFrame(this);
			jPanel.add("Account Management", management.accountManaPanel);
		}

		cardLayout.show(jPanel, "Main Interface");

		setContentPane(jPanel);
		
		this.setResizable(false);
		this.setTitle("BUPT library");
		this.setSize(1200, 800);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}
}
