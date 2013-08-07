package pointOfSale;

/**
 * 
 * @author Stephen Collins, Vanessa Harris, Kolter Bradshaw, Cristhian Ramirez
 * (Date: 4/24/2013) 
 * Purpose: Tracks restaurant menu categories.  Each category object acts as a header for a number
 * of Item objects.  Class was created to help present the user with an organized menu.  Intended to be used
 * in an array which interacts with a multi-dimensional array of Item objects.
 *
 */
public class Category implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;  //Added to satisfy compiler
	
	private String categoryName;
	private boolean isActive;
	
	/**
	 * Initializes a category object with a system provided name and boolean value representing 
	 * whether the category is currently active.
	 * @param newName Category name
	 * @param activeState True if category is active, false if it is inactive
	 */
	Category(String newName, boolean activeState)
	{
		categoryName = newName;
		isActive = activeState;
	}
	
	/**
	 * Returns the category name.
	 * @return Category name
	 */
	public String getCategoryName()
	{
		return categoryName;
	}
	/**
	 * Returns whether the category is active
	 * @return True if the category is active, or false otherwise
	 */
	public boolean isActive()
	{
		return isActive;
	}
	/**
	 * Sets the category name to a new String value
	 * @param newName String representing the new category name
	 */
	public void setCategoryName(String newName)
	{
		categoryName = newName;
	}
	/**
	 * Changes an existing category object from inactive to active and sets its name.
	 * @param newName
	 */
	public void addCategory(String newName)
	{
		categoryName = newName;
		isActive = true;
	}
	/**
	 * Changes an existing category object from active to inactive and sets its name to "Empty"
	 */
	public void deleteCategory()
	{
		categoryName = "Empty";
		isActive = false;
	}
}
