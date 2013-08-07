package pointOfSale;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * 
 * @author Stephen Collins, Vanessa Harris, Kolter Bradshaw, Cristhian Ramirez
 * (Date: 4/24/2013) 
 * Purpose: Creates a panel which processes, saves and loads the receipts 
 * created through TransactionGUI. 
 *
 */
public class ReceiptPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private static final String RECEIPT_PATH = "Files/Receipts/";
	private static final String RECEIPT_LIST_FILE = RECEIPT_PATH + "ReceiptList";
	private static final String TAX_FILE = "Files/Tax/SalesTax";
	private static final Color DARK_CHAMPAGNE = new Color(194, 178, 128);
	private static final  Color PALE_GOLDENROD = new Color(238,232,170);
	
	private static DefaultListModel<String> listModel = new DefaultListModel<String>();
	private static JList<String> receiptList = new JList<String>(listModel);
	private static long subtotalAmount = 0;
	private static long taxAmount = 0;
	private static long totalAmount = 0;
	private static String newReceipt = "";
	private static double salesTax=0;
	
	/**
	 * Constructs the ReceiptPanel.  Sets the initial conditions of the panel and the receiptList JList object.
	 * Adds a JScrollPane containing receiptList to this JPanel.
	 */
	ReceiptPanel()
	{
		setBorder(BorderFactory.createMatteBorder(10,10,10,10,DARK_CHAMPAGNE));
		setLayout(new GridLayout(1,1));
		setBackground(DARK_CHAMPAGNE);
		
		readTax();
		
		receiptList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		receiptList.setBackground(PALE_GOLDENROD);
		receiptList.setFont(new Font(Font.SERIF,Font.PLAIN,16));

		add(new JScrollPane(receiptList, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
	}
	/**
	 * Static method called by the ItemPanel class to add an item to the receiptList.
	 * Also adjusts the subtotals/totals to match the new receiptList elements.
	 * @param itemPrice (String) Price of user selected item
	 * @param itemName (String) Name of user selected item
	 */
	public static void addItem(String itemPrice, String itemName)
	{
		subtotalAmount = subtotalAmount + Integer.parseInt(itemPrice);
		updateTotals();
		
		if(listModel.getSize() > 1)
			for(int count=0; count < 4; count++)
				listModel.removeElementAt(listModel.getSize()-1);
		listModel.addElement(Tools.toMoney(itemPrice) + manualTab(itemPrice) + itemName);
		listModel.addElement(" ");
		listModel.addElement(Tools.toMoney(subtotalAmount) + manualTab(Tools.toMoney(subtotalAmount)) + "Subtotal");
		listModel.addElement(Tools.toMoney(taxAmount) + manualTab(Tools.toMoney(taxAmount)) + "Tax");
		listModel.addElement(Tools.toMoney(totalAmount) + manualTab(Tools.toMoney(totalAmount)) + "Total");
	}
	/**
	 * Called by the Delete button in the CheckOutPanel class to remove an item from the receiptList.
	 * Also adjusts the subtotals/totals to match the new receiptList elements.
	 */
	public static void deleteItem()
	{
		if(receiptList.getSelectedIndex() < listModel.getSize()-4 && receiptList.getSelectedIndex() > -1)
		{
			String itemPrice = receiptList.getSelectedValue().substring(0,
																receiptList.getSelectedValue().indexOf(" "));
			
			subtotalAmount = subtotalAmount - Tools.toAmount(itemPrice);
			updateTotals();
			
			listModel.removeElementAt(receiptList.getSelectedIndex());
			if(listModel.getSize() == 4)
				clearReceipt();
			else
			{
				for(int count=0; count < 4; count++)
					listModel.removeElementAt(listModel.getSize()-1);
				listModel.addElement(" ");
				listModel.addElement(Tools.toMoney(subtotalAmount) + manualTab(Tools.toMoney(subtotalAmount)) + "Subtotal");
				listModel.addElement(Tools.toMoney(taxAmount) + manualTab(Tools.toMoney(taxAmount)) + "Tax");
				listModel.addElement(Tools.toMoney(totalAmount) + manualTab(Tools.toMoney(totalAmount)) + "Total");
			}
		}
	}
	/**
	 * Removes all elements from the receiptList and resets the total price to zero.
	 */
	public static void clearReceipt()
	{
		listModel.removeAllElements();
		subtotalAmount = 0;
		taxAmount = 0;
		totalAmount = 0;
	}
	/**
	 * Saves the receiptList to text file before clearing it
	 */
	public static void saveReceipt()
	{
		PrintWriter listWriter = null;
		PrintWriter contentWriter = null;
		newReceipt = getTimeStamp();
		
		try
		{
			listWriter = new PrintWriter(new FileOutputStream(RECEIPT_LIST_FILE, true));
			contentWriter = new PrintWriter(RECEIPT_PATH + newReceipt);
		}
		catch(FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null,"File Not Found");
		}
		
		listWriter.println(newReceipt);
		for(int count=0; count < listModel.getSize(); count++)
			contentWriter.println(listModel.elementAt(count));
		
		listWriter.close();
		contentWriter.close();
		clearReceipt();
	}
	/**
	 * Loads items of the selected receipt from text file back into receipt list
	 * @param receiptFile Text file to be loaded
	 */
	public static void loadReceipt(String receiptFile)
	{
		Scanner inputStream = null;
		try
		{
			inputStream = new Scanner(new File(RECEIPT_PATH + receiptFile));
		}
		catch(FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null,"File Not Found");
		}
		clearReceipt();
		while(inputStream.hasNextLine())
			listModel.addElement(inputStream.nextLine());
		inputStream.close();
	}
	/**
	 * Updates the total price to include sales tax
	 */
	private static void updateTotals()
	{
		taxAmount = Math.round(subtotalAmount * salesTax / 100.0);
		totalAmount = subtotalAmount + taxAmount;
	}
	/**
	 * JLists do not recognize the tab character, so this inserts a manual tab that, while not perfect,
	 * gets the job done.
	 * @param entry First character, which the "tab" will follow
	 * @return A variable number of blank spaces to act as a tab
	 */
	private static String manualTab(String entry)
	{
		String tab = "";
		for(int count=0; count < 15 - entry.length(); count++)
			tab += " ";
		return tab;
	}
	/**
	 * Method to retrieve the date and time of checkout for each receipt
	 * @return returns the current time in String format
	 */
	private static String getTimeStamp()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
	/**
	 * Private helper method which reads the local sales tax amount 
	 * from a text file and sets a double value equal to it.
	 */
	private static void readTax()
	{

		Scanner inputStream = null;
		try
		{
			inputStream = new Scanner(new File(TAX_FILE));
		}
		catch(FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null,"File Not Found");
		}
		
		salesTax = Double.parseDouble(inputStream.next());
		inputStream.close();
	}
}
