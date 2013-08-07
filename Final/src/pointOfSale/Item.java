package pointOfSale;

/**
 * 
 * @author Stephen Collins, Vanessa Harris, Kolter Bradshaw, Cristhian Ramirez
 * (Date: 4/24/2013) 
 * Purpose: Each object of this class represents a single menu item.  Intended to be used in a multi-dimensional
 * array with objects of the category class acting as a label for each of the rows.  All objects of the
 * array are read from the system at program initiation.  Unused objects have their active state flag set to
 * inactive and are ignored by the system.
 *
 */
public class Item implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;  //Added to satisfy compiler
	private String itemName;
	private String itemPrice;
	private boolean isActive;
	private int totalCount;
	
	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}
	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount() {
		this.totalCount++;
	}
	/**
	 * Constructs a new Item object using saved date.
	 * @param newPrice Saved item price
	 * @param newName Saved item name
	 * @param activeState Saved item state, true if item object is active, false otherwise
	 */
	Item(String newPrice, String newName, boolean activeState)
	{
		totalCount = 0;
		itemPrice = newPrice;
		itemName = newName;
		isActive = activeState;
	}
	/**
	 * Returns the item price
	 * @return String value representing the item price
	 */
	public String getPrice()
	{
		return itemPrice;
	}
	
	public void clearTotalCount() {
		totalCount = 0;
	}
	/**
	 * Returns the item name
	 * @return String value representing the item's name
	 */
	public String getName()
	{
		return itemName;
	}
	/**
	 * Return active state of item
	 * @return True if item object is currently in use, false otherwise
	 */
	public boolean isActive()
	{
		return isActive;
	}
	/**
	 * Sets the item price to a new value
	 * @param newPrice User entered String, new item price
	 */
	public void setPrice(String newPrice)
	{
		itemPrice = newPrice;
	}
	/**
	 * Sets the item name to a new value
	 * @param newName User entered String, new item name
	 */
	public void setName(String newName)
	{
		itemName = newName;
	}
	/**
	 * Changes the active state of the item object to true, adds a new name and price
	 * @param newPrice User entered item price
	 * @param newName User entered item name
	 */
	public void addItem(String newPrice, String newName)
	{
		itemPrice = newPrice;
		itemName = newName;
		isActive = true;
	}
	/**
	 * Changes the active state of the item object to false, Changes price and name values to "Empty"
	 */
	public void deleteItem()
	{
		itemPrice = "Empty";
		itemName = "Empty";
		isActive = false;
	}
}
