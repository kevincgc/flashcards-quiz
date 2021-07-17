package Unused;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Flashcard.CardDeck;

import javax.swing.JList;

public class DeckView {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeckView window = new DeckView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DeckView() {
		initialize();
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

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JList contentList = new JList();
		frame.getContentPane().add(new JScrollPane(contentList), BorderLayout.CENTER);
		contentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		

		JList filesList = new JList(generateFilesModel());
		frame.getContentPane().add(new JScrollPane(filesList), BorderLayout.WEST);
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
		        	
		        }
		    }
		});
				
	}

}
