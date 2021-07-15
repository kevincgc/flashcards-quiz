package Flashcard;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JRadioButton;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import javax.swing.JTextPane;
import java.awt.GridLayout;
import javax.swing.JToolBar;
import javax.swing.ButtonGroup;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.UIManager;

import com.google.gson.Gson;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class Flashcard {

	public JFrame frame;
	private JTextField textField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField tf1;
	private JTextField tf10;
	private JTextField tf2;
	private JTextField tf3;
	private JTextField tf4;
	private JTextField tf5;
	private JTextField tf6;
	private JTextField tf7;
	private JTextField tf8;
	private JTextField tf9;
	private List<Card> cards;
	private int i;
	private String filename;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Flashcard window = new Flashcard(null);
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 * @param list 
	 */
	public Flashcard() {
		i = 0;
		filename = "test";
		initialize();
		reloadCards();
	}
	
	private void reloadCards() {
		cards = CJson.readFile(filename);
		readCard();
	}
	
	private void readCard() {
		tf1.setText(cards.get(i).type.toString());
		tf2.setText(cards.get(i).en.toString());
		tf3.setText(cards.get(i).jpDict.toString());
		tf4.setText(cards.get(i).jpMasu.toString());
		tf5.setText(cards.get(i).jpTe.toString());
		tf6.setText(cards.get(i).jpSimpNeg.toString());
		tf7.setText(cards.get(i).jpParticle.toString());
	}
	
	private void writeCard() {
		Gson gson = new Gson();
		cards.get(i).type = gson.fromJson(tf1.getText(), CardType.class);
		cards.get(i).en = tf2.getText();
		cards.get(i).jpDict = tf3.getText();
		cards.get(i).jpMasu = tf4.getText();
		cards.get(i).jpTe = tf5.getText();
		cards.get(i).jpSimpNeg = tf6.getText();
		cards.get(i).jpParticle = tf7.getText();
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 704, 512);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.WEST);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton prevButton = new JButton("Prev Card");
		prevButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (i == 0) {
					i = cards.size() - 1;
				} else {
					i--;
				}
				readCard();
			}
		});
		panel_1.add(prevButton);
		
		JButton flipButton = new JButton("Flip Card");
		panel_1.add(flipButton);
		
		JButton nextButton = new JButton("Next Card");
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (i == cards.size() - 1) {
					i = 0;
				} else {
					i++;
				}
				readCard();
			}
		});
		panel_1.add(nextButton);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		textField = new JTextField();
		textField.setText("1");
		panel_2.add(textField);
		textField.setColumns(10);
		
		JButton jumpButton = new JButton("Jump");
		panel_2.add(jumpButton);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		
		JLabel countLabel = new JLabel("Card: 000/123");
		panel_3.add(countLabel);
		
		JButton shuffleButton = new JButton("Shuffle");
		shuffleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel_3.add(shuffleButton);
		
		JPanel panel_6 = new JPanel();
		panel.add(panel_6);
		
		JButton btnNewButton = new JButton("Move Current Card To Deck...");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel_6.add(btnNewButton);
		
		JPanel panel_7 = new JPanel();
		panel.add(panel_7);
		
		JButton btnNewButton_2 = new JButton("Add Card");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tf1.getText().equals("") || tf1.getText().equals("verb")) {
					System.out.println("k");
				}
			}
		});
		panel_7.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Delete Card");
		panel_7.add(btnNewButton_3);
		
		JPanel panel_4 = new JPanel();
		panel.add(panel_4);
		
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writeCard();
				CJson.writeFile(cards, filename);
			}
		});
		panel_4.add(saveButton);
		
		JButton reloadButton = new JButton("Reload");
		reloadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reloadCards();
			}
		});
		panel_4.add(reloadButton);
		
		JToolBar toolBar = new JToolBar();
		frame.getContentPane().add(toolBar, BorderLayout.NORTH);
		
		JButton switchButton = new JButton("Swap View");
		switchButton.setBackground(UIManager.getColor("Button.background"));
		toolBar.add(switchButton);
		
		JRadioButton engRadio = new JRadioButton("English");
		buttonGroup.add(engRadio);
		engRadio.setSelected(true);
		toolBar.add(engRadio);
		
		JRadioButton kanaRadio = new JRadioButton("Kana");
		buttonGroup.add(kanaRadio);
		toolBar.add(kanaRadio);
		
		JLabel lblNewLabel = new JLabel("Deck:");
		toolBar.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		toolBar.add(lblNewLabel_1);
		
		JPanel panel_5 = new JPanel();
		frame.getContentPane().add(panel_5, BorderLayout.CENTER);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.Y_AXIS));
		
		tf1 = new JTextField();
		panel_5.add(tf1);
		tf1.setColumns(10);
		
		tf2 = new JTextField();
		panel_5.add(tf2);
		tf2.setColumns(10);
		
		tf3 = new JTextField();
		panel_5.add(tf3);
		tf3.setColumns(10);
		
		tf4 = new JTextField();
		panel_5.add(tf4);
		tf4.setColumns(10);
		
		tf5 = new JTextField();
		panel_5.add(tf5);
		tf5.setColumns(10);
		
		tf6 = new JTextField();
		panel_5.add(tf6);
		tf6.setColumns(10);
		
		tf7 = new JTextField();
		panel_5.add(tf7);
		tf7.setColumns(10);
		
		tf8 = new JTextField();
		panel_5.add(tf8);
		tf8.setColumns(10);
		
		tf9 = new JTextField();
		panel_5.add(tf9);
		tf9.setColumns(10);
		
		tf10 = new JTextField();
		panel_5.add(tf10);
		tf10.setColumns(10);
	}

}
