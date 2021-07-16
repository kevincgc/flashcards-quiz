package Flashcard;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
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

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		DefaultListModel<String> listModel = new DefaultListModel<>();
		File dir = new File("src/cards/");
		for (File file : dir.listFiles()) {
			if (file.getName().endsWith((".json"))) {
				listModel.addElement(file.getName());
			}
		}


		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JList list = new JList(listModel);
		frame.getContentPane().add(new JScrollPane(list), BorderLayout.WEST);
	}

}
