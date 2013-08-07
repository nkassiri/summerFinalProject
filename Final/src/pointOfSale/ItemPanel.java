package pointOfSale;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * 
 * @author Stephen Collins, Vanessa Harris, Kolter Bradshaw, Cristhian Ramirez
 * (Date: 4/24/2013) 
 * Purpose: A grid of MenuButtons which represent either the restaurants food categories or the items in any
 * particular category.  These buttons are used to interact with the Item and Category arrays which contain
 * the restaurant's menu data.  Those arrays are read from a serialized binary file save in the system.
 * This class is a component of the TransactionGUI class.
 *
 */
public class ItemPanel extends JPanel implements ActionListener, MouseListener
{
	private static final long serialVersionUID = 1L;
	private static final Color DARK_CHAMPAGNE = new Color(194, 178, 128);
	private static final String CATEGORY_ARRAY = "Files/Menu/CategoryArray";
	private static final String ITEM_ARRAY = "Files/Menu/MenuItemArray";
	private int size=MenuEditor.size;
	private MenuButton[] button = new MenuButton[size];
	private Category[] category = new Category[size];
	private Item[][] item = new Item[size][size];
	
	/**
	 * Initializes the screen with an array of size MenuButton objects, organized into 8 rows and
	 * 4 columns.  Defaults the objects to represent categories initially.
	 */
	ItemPanel()
	{
		setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, DARK_CHAMPAGNE));
		setBackground(DARK_CHAMPAGNE);
		setLayout(new GridLayout(8,4));
		
		addMouseListener(this);
		
		initializeArrays();
		initializeButtons();
		displayCategories();
	}
	/**
	 * MenuButton ActionListener. If a button is clicked when categories are being displayed, changes the
	 * buttons to display all items in that category.  If an item button is clicked, that item's date is added
	 * to the receipt panel.
	 */
	public void actionPerformed(ActionEvent event)
	{
		int index = event.getActionCommand().indexOf(".");
		
		if(index == -1)
			displayItems(Integer.parseInt(event.getActionCommand()));
		else
		{
			int catNumber = Integer.parseInt(event.getActionCommand().substring(0,index));
			int itemNumber = Integer.parseInt(event.getActionCommand().substring(index+1));
			ReceiptPanel.addItem(item[catNumber][itemNumber].getPrice(), item[catNumber][itemNumber].getName());
		}
	}
	/**
	 * This MouseListener is assigned to the ItemPanel as a whole and to each button.  If the user clicks the
	 * right mouse button, then the buttons change to display categories, allowing the user to quickly
	 * browse the restaurant menu.
	 */
	public void mousePressed(MouseEvent event)
	{
		if(event.getButton() == MouseEvent.BUTTON3)
			displayCategories();
	}
	public void mouseClicked(MouseEvent event)
	{
	}
	public void mouseReleased(MouseEvent event)
	{
	}
	public void mouseEntered(MouseEvent event)
	{
	}
	public void mouseExited(MouseEvent event)
	{
	}
	/**
	 * Changes the buttons to display food categories.  Called by this class and the TransactionGUI class.
	 */
	public void displayCategories()
	{
		int count = 0;
		for(; category[count].isActive() && count < size; count++)
		{
			button[count].setText(category[count].getCategoryName());
			button[count].setActionCommand(String.valueOf(count));
			button[count].setVisible(true);
		}
		for(; count < size; count++)
			button[count].setVisible(false);
		
		Tools.update(this);
	}
	/**
	 * Private helper method which changes the buttons to display individual menu items associated with the
	 * same category
	 * @param category Category selected by the user
	 */
	private void displayItems(int category)
	{
		int count = 0;
		for(; item[category][count].isActive() && count < size; count++)
		{
			button[count].setText(item[category][count].getName());
			button[count].setActionCommand(String.valueOf(category) + "." + String.valueOf(count));
			button[count].setVisible(true);
		}
		for(; count<size; count++)
			button[count].setVisible(false);
			
		Tools.update(this);
	}
	/**
	 * Private helper method which initializes the MenuButton array, and adds a MouseListener to each button
	 */
	private void initializeButtons()
	{
		for(int count=0; count<size; count++)
		{
			button[count] = new MenuButton("Empty","null",this);
			button[count].addMouseListener(this);
			button[count].setVisible(false);
			add(button[count]);
		}
	}
	/**
	 * Private helper method which reads the Item and Category arrays from the system in their respective binary
	 * files.
	 */
	private void initializeArrays()
	{
		ObjectInputStream readCategories = null;
		ObjectInputStream readItems = null;
		
		try
		{
			readCategories = new ObjectInputStream(new FileInputStream(CATEGORY_ARRAY));
			readItems = new ObjectInputStream(new FileInputStream(ITEM_ARRAY));
			
			category = (Category[]) readCategories.readObject();
			item = (Item[][]) readItems.readObject();

			readCategories.close();
			readItems.close();
		}
		catch(IOException e)
		{
			JOptionPane.showMessageDialog(null,"ERROR: Arrays Not Loaded Correctly");
		}
		catch(ClassNotFoundException e)
		{
			JOptionPane.showMessageDialog(null,"ERROR: Local Array Class Not Found");
		}
	}
}
