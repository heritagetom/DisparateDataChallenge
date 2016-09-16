package challenge.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class FilterSelection {

	private JFrame frame;
	String dataBase, dataLink, dataSet =null;

	/**
	 * Launch the application.
	 */
	public static void show(final String db, final String dl, final String ds) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FilterSelection window = new FilterSelection(db, dl, ds);
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
	public FilterSelection(String dataBase, String dataLink, String dataSet) {
		this.dataBase = dataBase;
		this.dataLink = dataLink;
		this.dataSet = dataSet;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		System.out.println(dataBase+ dataLink+ dataSet);
	}

}
