package pointOfSale;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;

/**
 * 
 * @author Stephen Collins, Vanessa Harris, Kolter Bradshaw, Cristhian Ramirez
 *         (Date: 4/24/2013) Purpose: A grid of MenuButtons which represent
 *         either the restaurants food categories or the items in any particular
 *         category. These buttons are used to interact with the Item and
 *         Category arrays which contain the restaurant's menu data. Those
 *         arrays are read from a serialized binary file save in the system.
 *         This class is a component of the TransactionGUI class.
 * 
 */
public class SearchPanel extends JPanel implements ActionListener,
		MouseListener {
	private static final long serialVersionUID = 1L;
	private static final Color DARK_CHAMPAGNE = new Color(194, 178, 128);
	private static final String CATEGORY_ARRAY = "Files/Menu/CategoryArray";
	private static final String ITEM_ARRAY = "Files/Menu/MenuItemArray";
	
	private MenuButton pAll;
	private MenuButton cat;
	private MenuButton back;
	private int size = MenuEditor.size;
	private MenuButton[] button = new MenuButton[size];
	private Category[] category = new Category[size];
	private Item[][] item = new Item[size][size];
	private double totalprice;
	JPanel searchResult = new JPanel();
	JTextArea text = new JTextArea();
	JScrollPane textScroll = new JScrollPane(text);
	JFrame search;
	private Printer p;
	private PrinterJob job;
	private MenuButton print;

	/**
	 * Initializes the screen with an array of size MenuButton objects,
	 * organized into 8 rows and 4 columns. Defaults the objects to represent
	 * categories initially.
	 */
	public SearchPanel(String fromEntered, String toEntered) {
	/*	p = new Printer("Message", search);
		job = PrinterJob.getPrinterJob();*/
		LinkedListLoader.fileNames(fromEntered, toEntered);
		LinkedListLoader.a.setCurrentToFront();
		totalprice = 0;
		setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10,
				DARK_CHAMPAGNE));
		setBackground(DARK_CHAMPAGNE);
		setLayout(new GridLayout(8, 4));
		
		addMouseListener(this);
		textScroll.setPreferredSize(new Dimension(500, 500));
		textScroll
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		textScroll
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		searchResult.setPreferredSize(new Dimension(600, 650));
		searchResult.setBackground(DARK_CHAMPAGNE);
		text.setEditable(false);
		text.setBackground(DARK_CHAMPAGNE);
		text.setFont(new Font("Ariel", Font.BOLD, 12));
		search = new JFrame("Your search results");
		search.setSize(new Dimension(600, 600));
		searchResult.add(textScroll);
		
		cat = new MenuButton("All");

		print = new MenuButton("Print", "print", new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printToPrinter();
			}
		});
		print.setBounds(300,575,100,50);
		searchResult.add(print);
		print.setVisible(true);
		search.add(searchResult);
		back = new MenuButton("Back", "back", new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayCategories();
				cat.setVisible(false);
				back.setVisible(false);
				pAll.setVisible(true);

			}
		});

		back.setVisible(false);
		cat.setVisible(false);

		

		initializeArrays();
		initializeButtons();
		displayCategories();
		
		pAll = new MenuButton("Print All", "$", new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int j = 0; j<size; j++) {	
					for (int i = 0; i < size; i++) {
						if (item[j][i].getName().equals("Empty"))
							break;
						searchLinkedListItem(item[j][i].getPrice(),
								item[j][i].getName(), j, i);

						}
					}
						search.setVisible(true);
			}
		});
		this.add(pAll);
	}
	
	private void printToPrinter()
	{
	    String printData = text.getText();
	    PrinterJob job = PrinterJob.getPrinterJob();
	    job.setPrintable(new Printer(printData));
	    boolean doPrint = job.printDialog();
	    if (doPrint)
	    { 
	        try 
	        {
	            job.print();
	        }
	        catch (PrinterException e)
	        {
	            e.printStackTrace();
	        }
	    }
	}

	/**
	 * MenuButton ActionListener. If a button is clicked when categories are
	 * being displayed, changes the buttons to display all items in that
	 * category. If an item button is clicked, that item's date is added to the
	 * receipt panel.
	 */

	public void ShowGUI() {
		JFrame test = new JFrame();
		test.add(this);
		test.setVisible(true);
		test.setSize(new Dimension(400, 400));
		test.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}

	public void actionPerformed(ActionEvent event) {
		text.setText("");
		totalprice = 0;
		
		if (event.getActionCommand().charAt(0) == '#') {
			int category = Integer.parseInt(event.getActionCommand().substring(
					1));
			for (int i = 0; i < size; i++) {
				if (item[category][i].getName().equals("Empty"))
					break;
				searchLinkedListItem(item[category][i].getPrice(),
						item[category][i].getName(), category, i);

			}
			search.setVisible(true);
			
			

		} else {
			int index = event.getActionCommand().indexOf(".");
			pAll.setVisible(false);
			if (index == -1)
				displayItems(Integer.parseInt(event.getActionCommand()));
			else {
				int catNumber = Integer.parseInt(event.getActionCommand()
						.substring(0, index));
				int itemNumber = Integer.parseInt(event.getActionCommand()
						.substring(index + 1));
				searchLinkedListItem(item[catNumber][itemNumber].getPrice(),
						item[catNumber][itemNumber].getName(), catNumber,
						itemNumber);
				search.setVisible(true);
				
			}

		}

	}

	public void searchLinkedListItem(String Price, String Name, int catNumber,
			int itemNumber) {
		item[catNumber][itemNumber].clearTotalCount();
		double itemPrice = 0;
		int before = 0;
		int after = 0;
		String report = "";
		before = Integer.parseInt(Price);
		after = before % 100;
		before /= 100;
		itemPrice += before;
		itemPrice += ((double) after / 100);
		while (LinkedListLoader.a.getCurrent() != null) {
			String x = LinkedListLoader.a.getCurrentString();
			if (x.contains(Name)) {
				totalprice += itemPrice;
				item[catNumber][itemNumber].setTotalCount();

			}
		}

		report = String.format("%s # Sold: %d \n\tTotal Price: %.2f\n\n", Name,
				item[catNumber][itemNumber].getTotalCount(), totalprice);
		text.setText(text.getText() + report);
		LinkedListLoader.a.setCurrentToFront();

	}

	/**
	 * This MouseListener is assigned to the ItemPanel as a whole and to each
	 * button. If the user clicks the right mouse button, then the buttons
	 * change to display categories, allowing the user to quickly browse the
	 * restaurant menu.
	 */
	public void mousePressed(MouseEvent event) {
		if (event.getButton() == MouseEvent.BUTTON3)
			displayCategories();
	}

	public void mouseClicked(MouseEvent event) {
	}

	public void mouseReleased(MouseEvent event) {
	}

	public void mouseEntered(MouseEvent event) {
	}

	public void mouseExited(MouseEvent event) {
	}

	/**
	 * Changes the buttons to display food categories. Called by this class and
	 * the TransactionGUI class.
	 */
	public void displayCategories() {
		int count = 0;
		for (; category[count].isActive() && count < size; count++) {
			button[count].setText(category[count].getCategoryName());
			button[count].setActionCommand(String.valueOf(count));
			button[count].setVisible(true);
		}
		for (; count < size; count++)
			button[count].setVisible(false);

		Tools.update(this);
	}

	/**
	 * Private helper method which changes the buttons to display individual
	 * menu items associated with the same category
	 * 
	 * @param category
	 *            Category selected by the user
	 */
	private void displayItems(int category) {
		int count = 0;
		for (; item[category][count].isActive() && count < size; count++) {
			button[count].setText(item[category][count].getName());
			button[count].setActionCommand(String.valueOf(category) + "."
					+ String.valueOf(count));
			button[count].setVisible(true);
		}
		for (; count < size; count++)
			button[count].setVisible(false);

		cat.setVisible(true);
		back.setVisible(true);

		cat.setActionCommand("#" + String.valueOf(category));
		cat.addActionListener(this);

		Tools.update(this);
	}

	/**
	 * Private helper method which initializes the MenuButton array, and adds a
	 * MouseListener to each button
	 */
	private void initializeButtons() {
		for (int count = 0; count < size; count++) {
			button[count] = new MenuButton("Empty", "null", this);
			button[count].addMouseListener(this);
			button[count].setVisible(false);
			add(button[count]);
		}
		add(cat);
		add(back);
		
	}

	/**
	 * Private helper method which reads the Item and Category arrays from the
	 * system in their respective binary files.
	 */
	private void initializeArrays() {
		ObjectInputStream readCategories = null;
		ObjectInputStream readItems = null;

		try {
			readCategories = new ObjectInputStream(new FileInputStream(
					CATEGORY_ARRAY));
			readItems = new ObjectInputStream(new FileInputStream(ITEM_ARRAY));

			category = (Category[]) readCategories.readObject();
			item = (Item[][]) readItems.readObject();

			readCategories.close();
			readItems.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"ERROR: Arrays Not Loaded Correctly");
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null,
					"ERROR: Local Array Class Not Found");
		}
	}

}
