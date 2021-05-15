package admin;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import database.UpdateBookTable;
import database.GetBookTable;

public class DeleteBook extends JFrame {
	// panel
	private JPanel deletebookPanel = new JPanel();
	// label
	private JLabel bookIdLabel = new JLabel("BookId:");
	private JLabel titleLabel = new JLabel("Delete Book");
	// field
	private JTextField field = new JTextField(22);
	// font
	private Font fontNormal = new Font("方正粗黑宋简体", Font.BOLD, 24);
	private Font fontText = new Font("方正粗黑宋简体", Font.BOLD, 18);
	private Font fontTitle = new Font("Bauhaus 93", Font.BOLD,30);
	// okButton
	private JButton okButton = new JButton("OK");

	// update search table
	public DefaultTableModel modelTable = new DefaultTableModel();

	public DeleteBook() {
		
		setSize(400, 450);
		setTitle("delete book");
		// set background
		Icon i = new ImageIcon("img\\ssubop.jpg");
		JLabel Label = new JLabel(i);
		Label.setBounds(0, 0, 400, 450);
		
		//set font
		bookIdLabel.setFont(fontNormal);
		field.setFont(fontNormal);
		okButton.setFont(fontNormal);
		okButton.setForeground(Color.orange);
		okButton.setBackground(Color.black);
		titleLabel.setFont(fontTitle);
		
		//set bound
		titleLabel.setBounds(100, 110, 300, 60);
		bookIdLabel.setBounds(20, 210, 100, 30);
		field.setBounds(140, 210, 185, 28);
		okButton.setBounds(47, 300, 280, 35);
		
		//set transparent
		okButton.setOpaque(false);
		deletebookPanel.setOpaque(false);
		
		AddEvent();
		
		//add to panel
		deletebookPanel.add(titleLabel);
		deletebookPanel.add(bookIdLabel);
		deletebookPanel.add(field);
		deletebookPanel.add(okButton);
		deletebookPanel.setLayout(null);
		deletebookPanel.setBounds(0, 0, 600, 400);
		add(deletebookPanel);
		add(Label);
		
		//set layout
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(null);
		setVisible(true);
	}

	public void setModel(DefaultTableModel modelTable) {
		this.modelTable = modelTable;
	}

	private void AddEvent() {

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int bookid = Integer.parseInt(field.getText().trim());
					UpdateBookTable.deletebook(bookid);
					modelTable.setRowCount(0);
					GetBookTable.GetBookTable(modelTable);
					JOptionPane.showMessageDialog(null, "Successfully delete");
				} catch (Exception e1) 
				{
					JOptionPane.showMessageDialog(null, "Incorrect input", "Warning", JOptionPane.WARNING_MESSAGE);
				}

			}
		});
	}

}
