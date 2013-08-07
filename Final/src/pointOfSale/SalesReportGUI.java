package pointOfSale;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.*;

public class SalesReportGUI extends JFrame implements ActionListener {

	private JButton[] buttons;
	private JTextField display;
	private JPanel scrollPanel;
	private JPanel displayPanel;
	private Container contentPane;
	private JLabel prompt;
	private String toEntered;
	private String fromEntered;
	private JButton request;
	private JButton restart;
	private JButton change;
	private JComboBox yearList;
	private JComboBox monthList;
	private JComboBox dayList;
	private String[] yearStrings = {"", "1999", "2000", "2001", "2002", "2003", "2004","2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014"};
	private String[] monthStrings = {"", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
	private String[] dayStrings = {""};
	private int count = 0;
	private Calendar myCal;

	public SalesReportGUI ( )
	{
		setSize (800,350);
		setTitle ("Sales Report Date Period");

		contentPane = getContentPane ( );
		contentPane.setLayout (new BorderLayout ( ));

		display = new JTextField (10);
		displayPanel= new JPanel();
		displayPanel.setLayout(new FlowLayout());
		displayPanel.setSize(450, 100);
		prompt= new JLabel ("From: yyyy-mm-dd");
		change= new JButton("Click to enter end period");
		change.addActionListener(this);

		request= new JButton ("Generate requested sales report");
		request.addActionListener(this);
		request.setVisible(false);

		restart= new JButton ("Restart");
		restart.addActionListener(this);

		displayPanel.add(prompt);
		displayPanel.add(display);
		displayPanel.add(change);
		displayPanel.add(request);
		displayPanel.add(restart);
		contentPane.add (displayPanel,BorderLayout.NORTH);


		scrollPanel = new JPanel ( );
		yearList = new JComboBox(yearStrings);
		yearList.addActionListener(this);
		monthList = new JComboBox(monthStrings);
		monthList.addActionListener(this);
		dayList = new JComboBox(dayStrings);
		dayList.addActionListener(this);
		contentPane.add (scrollPanel, BorderLayout.CENTER);
		scrollPanel.add(new JLabel("Select year: "));
		scrollPanel.add(yearList);
		scrollPanel.add(new JLabel("Select month: "));
		scrollPanel.add(monthList);
		monthList.setVisible(false);
		scrollPanel.add(new JLabel("Select day: "));
		scrollPanel.add(dayList);
		dayList.setVisible(false);




	}


	public static void main(String[]args){
		SalesReportGUI a= new SalesReportGUI();
		a.setVisible(true);
	}

	public void restart() {
		prompt.setText("From: yyyy-mm-dd");
		display.setText("");
		change.setVisible(true);
		request.setVisible(false);

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		String which="";
		which = e.getActionCommand() + Integer.toString(count);
		if(which.equalsIgnoreCase("comboBoxChanged0")) {
			display.setText((String)yearList.getSelectedItem() + "-");
			monthList.setVisible(true);
			count++;
		}
		if(which.equalsIgnoreCase("comboBoxChanged1")) {
			display.setText(display.getText() + (String)monthList.getSelectedItem() + "-");
			String[] yearMonth = display.getText().split("-");
			int daysInMonth = 31;
			if(yearMonth[1] == "01") {
				myCal = new GregorianCalendar(Integer.parseInt(yearMonth[0]), Calendar.JANUARY, 1);
				daysInMonth = myCal.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
			if(yearMonth[1] == "02") {
				myCal = new GregorianCalendar(Integer.parseInt(yearMonth[0]), Calendar.FEBRUARY, 1);
				daysInMonth = myCal.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
			if(yearMonth[1] == "03") {
				myCal = new GregorianCalendar(Integer.parseInt(yearMonth[0]), Calendar.MARCH, 1);
				daysInMonth = myCal.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
			if(yearMonth[1] == "04") {
				myCal = new GregorianCalendar(Integer.parseInt(yearMonth[0]), Calendar.APRIL, 1);
				daysInMonth = myCal.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
			if(yearMonth[1] == "05") {
				myCal = new GregorianCalendar(Integer.parseInt(yearMonth[0]), Calendar.MAY, 1);
				daysInMonth = myCal.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
			if(yearMonth[1] == "06") {
				myCal = new GregorianCalendar(Integer.parseInt(yearMonth[0]), Calendar.JUNE, 1);
				daysInMonth = myCal.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
			if(yearMonth[1] == "07") {
				myCal = new GregorianCalendar(Integer.parseInt(yearMonth[0]), Calendar.JULY, 1);
				daysInMonth = myCal.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
			if(yearMonth[1] == "08") {
				myCal = new GregorianCalendar(Integer.parseInt(yearMonth[0]), Calendar.AUGUST, 1);
				daysInMonth = myCal.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
			if(yearMonth[1] == "09") {
				myCal = new GregorianCalendar(Integer.parseInt(yearMonth[0]), Calendar.SEPTEMBER, 1);
				daysInMonth = myCal.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
			if(yearMonth[1] == "10") {
				myCal = new GregorianCalendar(Integer.parseInt(yearMonth[0]), Calendar.OCTOBER, 1);
				daysInMonth = myCal.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
			if(yearMonth[1] == "11") {
				myCal = new GregorianCalendar(Integer.parseInt(yearMonth[0]), Calendar.NOVEMBER, 1);
				daysInMonth = myCal.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
			if(yearMonth[1] == "12") {
				myCal = new GregorianCalendar(Integer.parseInt(yearMonth[0]), Calendar.DECEMBER, 1);
				daysInMonth = myCal.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
			for(int i=1;i<=daysInMonth;i++) {
				if(i<10) {
					dayList.addItem("0" + Integer.toString(i));
				}
				else {
					dayList.addItem(Integer.toString(i));
				}
			}
			dayList.setVisible(true);
			count++;
		}
		if(which.equalsIgnoreCase("comboBoxChanged2")) {

			display.setText(display.getText() + (String)dayList.getSelectedItem());
		}
		
		which = which.substring(0, which.length()-1);

		if (which.equalsIgnoreCase("Generate requested sales report")){
			toEntered=display.getText();
			if (!toEntered.substring(0,4).matches("[0-9]+") || toEntered.charAt(4)!='-'||
					!toEntered.substring(5,7).matches("[0-9]+") || toEntered.charAt(7)!='-' || !toEntered.substring(8).matches("[0-9]+")) {
				JOptionPane.showMessageDialog(null, "Incorrect format, try again.");
				restart();
				return;
			}
			else {
				which="";
				display.setText("Calculating....");
				SearchPanel a= new SearchPanel(fromEntered,toEntered);
				which="";
				a.ShowGUI();	
			}
			/*JFrame searchPanel = new JFrame();
			searchPanel.add(a);
			searchPanel.setVisible(true);
			searchPanel.setSize(new Dimension(400,400));*/
		}

		if (which.equalsIgnoreCase("Click to enter end period")){
			fromEntered= display.getText();
			if (!fromEntered.substring(0,4).matches("[0-9]+") || fromEntered.charAt(4)!='-'||
					!fromEntered.substring(5,7).matches("[0-9]+") || fromEntered.charAt(7)!='-' || !fromEntered.substring(8).matches("[0-9]+")) {
				JOptionPane.showMessageDialog(null, "Incorrect format, try again.");
				restart();
				return;
			}
			else{	 
				prompt.setText("To: yyyy-mm-dd");
				display.setText("");
				which="";
				request.setVisible(true);
				change.setVisible(false);
				monthList.setVisible(false);
				dayList.setVisible(false);
				dayList.setSelectedIndex(0);
				count = 0;
			}
		}

		if (which.equalsIgnoreCase("Restart")){
			prompt.setText("From: yyyy-mm-dd");
			display.setText("");
			which="";
			change.setVisible(true);
			request.setVisible(false);
			monthList.setVisible(false);
			dayList.setVisible(false);
			dayList.setSelectedIndex(0);
			count = 0;
		}
	}
}
