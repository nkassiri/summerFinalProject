package pointOfSale;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;

/**
 * 
 * @author Stephen Collins, Vanessa Harris, Kolter Bradshaw, Cristhian Ramirez
 * (Date: 4/24/2013) 
 * Purpose: JPanel containing only the "Back" button in the administrator screen used to return to the
 * transaction screen.
 *
 */
public class AdminButtonPanel extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;  //Added to satisfy compiler
	private static final Color DARK_CHAMPAGNE = new Color(194, 178, 128);
	private MenuButton exitButton = new MenuButton("Back","Back",this);
	
	/**
	 * Arranges the "Back" button on its own JPanel
	 */
	AdminButtonPanel()
	{
		setLayout(new GridLayout(4,2));
		setBackground(DARK_CHAMPAGNE);
		setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, DARK_CHAMPAGNE));
		
		Tools.addBlankSpace(this, 7);
		add(exitButton);
		exitButton.setFont(new Font(Font.SERIF,Font.PLAIN,36));
	}
	/**
	 * Listens for the "Back" button to be clicked and returns the user to the transaction screen if it is
	 */
	public void actionPerformed(ActionEvent event)
	{
		if(event.getActionCommand().equals("Back"))
			SystemInit.setTransactionScreen();
	}
}
