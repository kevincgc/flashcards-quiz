package Flashcard;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JRadioButton;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
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
import javax.swing.ListSelectionModel;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;

import com.google.gson.Gson;

import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
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
	JRadioButton engRadio, kanaRadio, allRadio;
	private JTextField[] tf;
	private Card currentCard;
	private String filename;
	private JLabel countLabel;
	private CardDeck deck;
	private JTextField tfType;
	JLabel deckLabel;

	public Flashcard(String fname) {
		filename = fname;
		deck = new CardDeck(filename);
		tf = new JTextField[10];
		initialize();
		loadCard(deck.getCard());
		engRadio.setSelected(true);
		updateVisibility(false);
	}
	
	public void reinitialize(String fname) {
		filename = fname;
		deck = new CardDeck(filename);
		loadCard(deck.getCard());
		updateVisibility(false);
	}
	
	private void updateCount() {
		countLabel.setText("Card: " + (deck.getIndex() + 1) + " / " + deck.getSize());
		deckLabel.setText(filename);
	}
	
	private void loadCard(Card newCard) {
		currentCard = newCard;
		Gson gson = new Gson();
		tfType.setText(gson.toJson(currentCard.type));
		for (int i = 0; i < 10; i++) {
			tf[i].setText(currentCard.sArr[i]);
		}
		updateCount();
	}

	private Card generateCard() {
		Gson gson = new Gson();
		CardType cType = gson.fromJson(tfType.getText(), CardType.class);
		String[] sArr = new String[Card.FIELD_COUNT];
		for (int i = 0; i < Card.FIELD_COUNT; i++) {
			sArr[i] = tf[i].getText();
		}
		return new Card(cType, sArr);
	}
	
	private void updateVisibility(Boolean isShow) {
		if (currentCard.type == CardType.VERB) {
			if (allRadio.isSelected()) {
				for (int i = 0; i < Card.FIELD_COUNT; i++) {
					tf[i].setVisible(true);
				}
			} else if (engRadio.isSelected()) {
				for (int i = 2; i < Card.FIELD_COUNT; i++) {
					tf[i].setVisible(isShow);
				}
				for (int i = 0; i < 2; i++) {
					tf[i].setVisible(true);
				}
			} else if (kanaRadio.isSelected()) {
				for (int i = 0; i < 2; i++) {
					tf[i].setVisible(isShow);
				}
				for (int i = 2; i < Card.FIELD_COUNT; i++) {
					tf[i].setVisible(true);
				}
			}
		}
		frame.revalidate();
	}
	
	private DefaultListModel<String> generateFilesModel() {
		DefaultListModel<String> listModel = new DefaultListModel<>();
		File dir = new File("src/cards/");
		for (File file : dir.listFiles()) {
			if (file.getName().endsWith((".json"))) {
				listModel.addElement(file.getName().replaceFirst("[.][^.]+$", ""));
			}
		}
		return listModel;
	}
	
	private DefaultListModel<String> generateCardsModel(String str) {
		DefaultListModel<String> listModel = new DefaultListModel<>();
		CardDeck deck = new CardDeck(str);
		for (int i = 0; i < deck.getSize(); i++) {
			listModel.addElement(deck.getCard().getArr(0));
		}
		return listModel;
	}


	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent e) {
		        deck.writeFile();
		        //MainPage m = new MainPage();
		        //m.setVisible(true);
		        e.getWindow().dispose();
		    }
		});
		frame.setBounds(100, 100, 704, 512);
		CardLayout cardLayout = new CardLayout();
		JPanel rootPanel = new JPanel(cardLayout);
		frame.getContentPane().add(rootPanel);
		JPanel cardsPanel = new JPanel(new BorderLayout()); 
		rootPanel.add(cardsPanel, "cards");
		cardLayout.show(rootPanel,"cards");
		/////////////////////////////////////////////////////////////
		JPanel navPanel = new JPanel(new BorderLayout());
		JList contentList = new JList();
		navPanel.add(new JScrollPane(contentList), BorderLayout.CENTER);
		contentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JList filesList = new JList(generateFilesModel());
		navPanel.add(new JScrollPane(filesList), BorderLayout.WEST);
		filesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		filesList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
                    contentList.setModel(generateCardsModel((String)filesList.getSelectedValue()));
                }
			}
        });
		filesList.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        JList list = (JList)evt.getSource();
		        if (evt.getClickCount() == 2) {
		        	reinitialize((String)filesList.getSelectedValue());
		        	cardLayout.show(rootPanel,"cards");
		        }
		    }
		});
		rootPanel.add(navPanel, "nav");
		/////////////////////////////////////////////////////////////
		JPanel panel = new JPanel();
		cardsPanel.add(panel, BorderLayout.WEST);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton prevButton = new JButton("Prev Card");
		prevButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadCard(deck.prevCard());
				updateVisibility(false);
			}
		});
		panel_1.add(prevButton);
		
		JButton flipButton = new JButton("Flip Card");
		flipButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateVisibility(true);
			}
		});
		panel_1.add(flipButton);
		
		JButton nextButton = new JButton("Next Card");
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadCard(deck.nextCard());
				updateVisibility(false);
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
					} else if (Integer.valueOf(jumpNum.getText()) > deck.getSize()) {
						jumpNum.setText(String.valueOf(deck.getSize()));
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
				loadCard(deck.getCard(Integer.valueOf(jumpNum.getText()) - 1));
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
				deck.shuffle();
				loadCard(deck.getCard());
			}
		});
		panel_3.add(shuffleButton);
		
		JPanel panel_6 = new JPanel();
		panel.add(panel_6);
		
		Box verticalBox = Box.createVerticalBox();
		panel_6.add(verticalBox);
		
		JButton btnNewButton_1 = new JButton("Move Current Card To Deck...");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> optionsArr = new ArrayList<String>();
				File dir = new File("src/cards/");
				for (File file : dir.listFiles()) {
					if (file.getName().endsWith((".json"))) {
						optionsArr.add(file.getName().replaceFirst("[.][^.]+$", ""));
					}
				}
				String[] options = optionsArr.toArray(new String[0]);
				String newDeckFilename = (String)JOptionPane.showInputDialog(null, "Select list to move card to:", 
		                "Move Card", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				if (!filename.equals(newDeckFilename)) {
					deck.removeCard(currentCard);
					CardDeck newDeck = new CardDeck(newDeckFilename);
					newDeck.addCard(currentCard);
					newDeck.writeFile();
				}
				updateCount();
			}
		});
		verticalBox.add(btnNewButton_1);
		
		JButton btnNewButton_4 = new JButton("Copy Current Card To Deck...");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> optionsArr = new ArrayList<String>();
				File dir = new File("src/cards/");
				for (File file : dir.listFiles()) {
					if (file.getName().endsWith((".json"))) {
						optionsArr.add(file.getName().replaceFirst("[.][^.]+$", ""));
					}
				}
				String[] options = optionsArr.toArray(new String[0]);
				String newDeckFilename = (String)JOptionPane.showInputDialog(null, "Select list to copy card to:", 
		                "Copy Card", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				if (!filename.equals(newDeckFilename)) {
					CardDeck newDeck = new CardDeck(newDeckFilename);
					newDeck.addCard(currentCard);
					newDeck.writeFile();
				}
				updateCount();
			}
		});
		verticalBox.add(btnNewButton_4);
		
		JPanel panel_7 = new JPanel();
		panel.add(panel_7);
		
		JButton btnNewButton_2 = new JButton("Add Card");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deck.addCard(generateCard());
				updateCount();
			}
		});
		panel_7.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Delete Card");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deck.removeCard(generateCard());
				updateCount();
			}
		});
		panel_7.add(btnNewButton_3);
		
		JPanel panel_4 = new JPanel();
		panel.add(panel_4);
		
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deck.updateCard(generateCard());
			}
		});
		panel_4.add(saveButton);
		
		JButton reloadButton = new JButton("Reload");
		reloadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadCard(deck.getCard());
			}
		});
		panel_4.add(reloadButton);
		
		JToolBar toolBar = new JToolBar();
		cardsPanel.add(toolBar, BorderLayout.NORTH);
		//frame.getContentPane().add;
		
		JButton switchButton = new JButton("Swap View");
		switchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deck.writeFile();
				cardLayout.show(rootPanel, "nav");
			}
		});
		switchButton.setBackground(UIManager.getColor("Button.background"));
		toolBar.add(switchButton);
		
		engRadio = new JRadioButton("English");
		engRadio.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					updateVisibility(false);
				}
				
			}
		});
		buttonGroup.add(engRadio);
		toolBar.add(engRadio);
		
		kanaRadio = new JRadioButton("Kana");
		kanaRadio.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					updateVisibility(false);
				}
				
			}
		});
		buttonGroup.add(kanaRadio);
		toolBar.add(kanaRadio);
		
		allRadio = new JRadioButton("Show All");
		allRadio.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					updateVisibility(false);
				}
				
			}
		});
		buttonGroup.add(allRadio);
		toolBar.add(allRadio);
		
		JLabel lblNewLabel = new JLabel("Deck:");
		toolBar.add(lblNewLabel);
		
		deckLabel = new JLabel("New label");
		toolBar.add(deckLabel);
		
		JButton autoButton = new JButton("Autocomplete 「う」");
		autoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!tf[2].getText().isBlank()) {
					String base = "";
					try {
						base = tf[2].getText(0, tf[2].getText().length() - 1);
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
					switch(tf[2].getText().charAt(tf[2].getText().length() - 1)) {
					case 'う':
						tf[3].setText(base + "います");
						break;
					case 'つ':
						tf[3].setText(base + "ちます");
						break;
					case 'る':
						tf[3].setText(base + "ります");
						break;
					case 'く':
						tf[3].setText(base + "きます");
						break;
					case 'ぐ':
						tf[3].setText(base + "ぎます");
						break;
					case 'む':
						tf[3].setText(base + "みます");
						break;
					case 'ぶ':
						tf[3].setText(base + "びます");
						break;
					case 'ぬ':
						tf[3].setText(base + "にます");
						break;
					case 'す':
						tf[3].setText(base + "します");
						break;
					default:
						tf[3].setText("ERROR");
					}
					switch(tf[2].getText().charAt(tf[2].getText().length() - 1)) {
					case 'う':
						tf[4].setText(base + "って");
						break;
					case 'つ':
						tf[4].setText(base + "って");
						break;
					case 'る':
						tf[4].setText(base + "って");
						break;
					case 'く':
						tf[4].setText(base + "いて");
						break;
					case 'ぐ':
						tf[4].setText(base + "いで");
						break;
					case 'む':
						tf[4].setText(base + "んで");
						break;
					case 'ぶ':
						tf[4].setText(base + "んで");
						break;
					case 'ぬ':
						tf[4].setText(base + "んで");
						break;
					case 'す':
						tf[4].setText(base + "して");
						break;
					default:
						tf[4].setText("ERROR");
					}
					switch(tf[2].getText().charAt(tf[2].getText().length() - 1)) {
					case 'う':
						tf[5].setText(base + "わない");
						break;
					case 'つ':
						tf[5].setText(base + "たない");
						break;
					case 'る':
						tf[5].setText(base + "らない");
						break;
					case 'く':
						tf[5].setText(base + "かない");
						break;
					case 'ぐ':
						tf[5].setText(base + "がない");
						break;
					case 'む':
						tf[5].setText(base + "まない");
						break;
					case 'ぶ':
						tf[5].setText(base + "ばない");
						break;
					case 'ぬ':
						tf[5].setText(base + "なない");
						break;
					case 'す':
						tf[5].setText(base + "さない");
						break;
					default:
						tf[5].setText("ERROR");
					}
				}
			}
		});
		toolBar.add(autoButton);
		
		JButton btnAutocompleteru = new JButton("Autocomplete 「る」");
		btnAutocompleteru.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tf[2].getText().equals("る")) {
					String base = "";
					try {
						base = tf[2].getText(0, tf[2].getText().length() - 1);
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
					tf[3].setText(base + "ます");
					tf[4].setText(base + "て");
					tf[5].setText(base + "ない");
				}
			}
		});
		toolBar.add(btnAutocompleteru);
		
		JPanel panel_5 = new JPanel();
		cardsPanel.add(panel_5, BorderLayout.CENTER);
		//frame.getContentPane().add;
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.Y_AXIS));
		
		tfType = new JTextField();
		panel_5.add(tfType);
		tfType.setColumns(10);
		
		tf[0] = new JTextField();
		tf[0].setFont(new Font("Yu Mincho", Font.PLAIN, 20));
		panel_5.add(tf[0]);
		tf[0].setColumns(10);
		
		tf[1] = new JTextField();
		tf[1].setFont(new Font("Yu Mincho", Font.PLAIN, 20));
		panel_5.add(tf[1]);
		tf[1].setColumns(10);
		
		tf[2] = new JTextField();
		tf[2].setFont(new Font("Yu Mincho", Font.PLAIN, 20));
		panel_5.add(tf[2]);
		tf[2].setColumns(10);
		
		tf[3] = new JTextField();
		tf[3].setFont(new Font("Yu Mincho", Font.PLAIN, 20));
		panel_5.add(tf[3]);
		tf[3].setColumns(10);
		
		tf[4] = new JTextField();
		tf[4].setFont(new Font("Yu Mincho", Font.PLAIN, 20));
		panel_5.add(tf[4]);
		tf[4].setColumns(10);
		
		tf[5] = new JTextField();
		tf[5].setFont(new Font("Yu Mincho", Font.PLAIN, 20));
		panel_5.add(tf[5]);
		tf[5].setColumns(10);
		
		tf[6] = new JTextField();
		tf[6].setFont(new Font("Yu Mincho", Font.PLAIN, 20));
		panel_5.add(tf[6]);
		tf[6].setColumns(10);
		
		tf[7] = new JTextField();
		tf[7].setFont(new Font("Yu Mincho", Font.PLAIN, 20));
		panel_5.add(tf[7]);
		tf[7].setColumns(10);
		
		tf[8] = new JTextField();
		tf[8].setFont(new Font("Yu Mincho", Font.PLAIN, 20));
		panel_5.add(tf[8]);
		tf[8].setColumns(10);
		
		tf[9] = new JTextField();
		tf[9].setFont(new Font("Yu Mincho", Font.PLAIN, 20));
		panel_5.add(tf[9]);
		tf[9].setColumns(10);

	}

}
