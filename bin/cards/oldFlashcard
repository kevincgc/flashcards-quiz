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
import javax.swing.text.BadLocationException;

import com.google.gson.Gson;

import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.Box;
import java.awt.Component;

public class Flashcard {

	public JFrame frame;
	private JTextField jumpNum;
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
	private JLabel countLabel;
	private Boolean isShow;

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
		isShow = false;
		setVisibility();
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
		updateFields();
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
	
	private void updateFields() {
		countLabel.setText("Card: " + (i + 1) + " / " + cards.size());
	}
	
	private void setVisibility() {
		tf3.setVisible(isShow);
		tf4.setVisible(isShow);
		tf5.setVisible(isShow);
		tf6.setVisible(isShow);
		tf7.setVisible(isShow);
		tf8.setVisible(isShow);
		tf9.setVisible(isShow);
		tf10.setVisible(isShow);
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
				isShow = false;
				setVisibility();
			}
		});
		panel_1.add(prevButton);
		
		JButton flipButton = new JButton("Flip Card");
		flipButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isShow = true;
				setVisibility();
				frame.revalidate();
			}
		});
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
				isShow = false;
				setVisibility();
			}
		});
		panel_1.add(nextButton);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		jumpNum = new JTextField();
		jumpNum.setText("1");
		panel_2.add(jumpNum);
		jumpNum.setColumns(10);
		jumpNum.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (jumpNum.getText() != null && jumpNum.getText().matches("[-+]?\\d*\\.?\\d+")) {
					if (Integer.valueOf(jumpNum.getText()) < 1) {
						jumpNum.setText("1");
					} else if (Integer.valueOf(jumpNum.getText()) > cards.size()) {
						jumpNum.setText(String.valueOf(cards.size()));
					}				
				} else {
					jumpNum.setText("1");
				}
			}

			@Override
			public void focusGained(FocusEvent e) {}
		});
		
		JButton jumpButton = new JButton("Jump");
		jumpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				i = Integer.valueOf(jumpNum.getText()) - 1;
				reloadCards();
			}
		});
		panel_2.add(jumpButton);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		
		countLabel = new JLabel();
		panel_3.add(countLabel);
		
		JButton shuffleButton = new JButton("Shuffle");
		shuffleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Collections.shuffle(cards);
				i = 0;
				readCard();
				isShow = false;
				setVisibility();
			}
		});
		panel_3.add(shuffleButton);
		
		JPanel panel_6 = new JPanel();
		panel.add(panel_6);
		
		Box verticalBox = Box.createVerticalBox();
		panel_6.add(verticalBox);
		
		JButton btnNewButton_1 = new JButton("Move Current Card To Deck...");
		verticalBox.add(btnNewButton_1);
		
		JButton btnNewButton_4 = new JButton("Copy Current Card To Deck...");
		verticalBox.add(btnNewButton_4);
		
		JPanel panel_7 = new JPanel();
		panel.add(panel_7);
		
		JButton btnNewButton_2 = new JButton("Add Card");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Gson gson = new Gson();
				CardType newCardType = gson.fromJson(tf1.getText(), CardType.class);
				String f2 = tf2.getText();
				String f3 = tf3.getText();
				String f4 = tf4.getText();
				String f5 = tf5.getText();
				String f6 = tf6.getText();
				String f7 = tf7.getText();
				Card newCard = new Card(newCardType, f2, f3, f4, f5, f6, f7);
				cards.add(newCard);
				CJson.writeFile(cards, filename);
				i = cards.size() - 1;
				updateFields();
			}
		});
		panel_7.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Delete Card");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cards.remove(i);
				CJson.writeFile(cards, filename);
				if (i == cards.size()) {
					i--;
				}
				reloadCards();
			}
		});
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
		
		JButton autoButton = new JButton("Autocomplete 「う」");
		autoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!tf3.getText().isBlank()) {
					String base = "";
					try {
						base = tf3.getText(0, tf3.getText().length() - 1);
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					switch(tf3.getText().charAt(tf3.getText().length() - 1)) {
					case 'う':
						tf4.setText(base + "います");
						break;
					case 'つ':
						tf4.setText(base + "ちます");
						break;
					case 'る':
						tf4.setText(base + "ります");
						break;
					case 'く':
						tf4.setText(base + "きます");
						break;
					case 'ぐ':
						tf4.setText(base + "ぎます");
						break;
					case 'む':
						tf4.setText(base + "みます");
						break;
					case 'ぶ':
						tf4.setText(base + "びます");
						break;
					case 'ぬ':
						tf4.setText(base + "にます");
						break;
					case 'す':
						tf4.setText(base + "します");
						break;
					default:
						tf4.setText("ERROR");
					}
					switch(tf3.getText().charAt(tf3.getText().length() - 1)) {
					case 'う':
						tf5.setText(base + "って");
						break;
					case 'つ':
						tf5.setText(base + "って");
						break;
					case 'る':
						tf5.setText(base + "って");
						break;
					case 'く':
						tf5.setText(base + "いて");
						break;
					case 'ぐ':
						tf5.setText(base + "いで");
						break;
					case 'む':
						tf5.setText(base + "んで");
						break;
					case 'ぶ':
						tf5.setText(base + "んで");
						break;
					case 'ぬ':
						tf5.setText(base + "んで");
						break;
					case 'す':
						tf5.setText(base + "して");
						break;
					default:
						tf5.setText("ERROR");
					}
					switch(tf3.getText().charAt(tf3.getText().length() - 1)) {
					case 'う':
						tf6.setText(base + "わない");
						break;
					case 'つ':
						tf6.setText(base + "たない");
						break;
					case 'る':
						tf6.setText(base + "らない");
						break;
					case 'く':
						tf6.setText(base + "かない");
						break;
					case 'ぐ':
						tf6.setText(base + "がない");
						break;
					case 'む':
						tf6.setText(base + "まない");
						break;
					case 'ぶ':
						tf6.setText(base + "ばない");
						break;
					case 'ぬ':
						tf6.setText(base + "なない");
						break;
					case 'す':
						tf6.setText(base + "さない");
						break;
					default:
						tf6.setText("ERROR");
					}
				}
			}
		});
		toolBar.add(autoButton);
		
		JButton btnAutocompleteru = new JButton("Autocomplete 「る」");
		btnAutocompleteru.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tf3.getText().equals("る")) {
					String base = "";
					try {
						base = tf3.getText(0, tf3.getText().length() - 1);
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					tf4.setText(base + "ます");
					tf5.setText(base + "て");
					tf6.setText(base + "ない");
				}
			}
		});
		toolBar.add(btnAutocompleteru);
		
		JPanel panel_5 = new JPanel();
		frame.getContentPane().add(panel_5, BorderLayout.CENTER);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.Y_AXIS));
		
		tf1 = new JTextField();
		tf1.setFont(new Font("Yu Mincho", Font.PLAIN, 20));
		panel_5.add(tf1);
		tf1.setColumns(10);
		
		tf2 = new JTextField();
		tf2.setFont(new Font("Yu Mincho", Font.PLAIN, 20));
		panel_5.add(tf2);
		tf2.setColumns(10);
		
		tf3 = new JTextField();
		tf3.setFont(new Font("Yu Mincho", Font.PLAIN, 20));
		panel_5.add(tf3);
		tf3.setColumns(10);
		
		tf4 = new JTextField();
		tf4.setFont(new Font("Yu Mincho", Font.PLAIN, 20));
		panel_5.add(tf4);
		tf4.setColumns(10);
		
		tf5 = new JTextField();
		tf5.setFont(new Font("Yu Mincho", Font.PLAIN, 20));
		panel_5.add(tf5);
		tf5.setColumns(10);
		
		tf6 = new JTextField();
		tf6.setFont(new Font("Yu Mincho", Font.PLAIN, 20));
		panel_5.add(tf6);
		tf6.setColumns(10);
		
		tf7 = new JTextField();
		tf7.setFont(new Font("Yu Mincho", Font.PLAIN, 20));
		panel_5.add(tf7);
		tf7.setColumns(10);
		
		tf8 = new JTextField();
		tf8.setFont(new Font("Yu Mincho", Font.PLAIN, 20));
		panel_5.add(tf8);
		tf8.setColumns(10);
		
		tf9 = new JTextField();
		tf9.setFont(new Font("Yu Mincho", Font.PLAIN, 20));
		panel_5.add(tf9);
		tf9.setColumns(10);
		
		tf10 = new JTextField();
		tf10.setFont(new Font("Yu Mincho", Font.PLAIN, 20));
		panel_5.add(tf10);
		tf10.setColumns(10);
	}

}
