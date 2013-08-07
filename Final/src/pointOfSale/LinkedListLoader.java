package pointOfSale;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class LinkedListLoader {

	public static ReceiptQueue a= new ReceiptQueue();



	public static void fileNames(String x, String z){
		File rep = new File("Files/Receipts/");
		File[] list = rep.listFiles();
		ArrayList<String> filenames= new ArrayList<String>();


		for ( int i = 0; i < list.length; i++) {
			filenames.add(list[i].getName());
		} 

		int yearStart = Integer.parseInt(x.substring(0,4));
		int monthStart = Integer.parseInt(x.substring(5,7));
		int yearEnd = Integer.parseInt(z.substring(0,4));
		int monthEnd = Integer.parseInt(z.substring(5,7));

		int startDay=Integer.parseInt(x.substring(8, 10));
		int endDay=Integer.parseInt(z.substring(8, 10)); 

		String day=x.substring(0,8);
		String month=x.substring(0,5);
		String year=x.substring(0,4);
		int k = yearStart;
		int o = monthStart;
		int counter=1;

		for (;k <= yearEnd; k++) {
			year = yearStart+"-";
			month = year;
			for (int begm = 1; begm <= 12; begm++){

				if (monthStart >12 || (yearStart == yearEnd && monthStart > monthEnd))
					break;
				month = String.format("%s%02d-",month,monthStart);
				day = month;
				for (int beg=startDay; beg<=31; beg++){
					day= String.format("%s%02d",day,beg);

					if 	(yearStart == yearEnd && monthStart == monthEnd && beg > endDay)
						break;

					for ( int i = 0; i < filenames.size(); i++) {
						if (filenames.get(i).startsWith(day)){
							loadReceipt(filenames.get(i));
						}
					}

					day=day.substring(0,8);
				}
				startDay=1;
				monthStart++;
				month = month.substring(0,5);
			}
			monthStart = 1;
			yearStart++;
			year = year.substring(0,4);
		}



	}

	public static void loadReceipt(String receiptFile)
	{
		Scanner inputStream = null;
		try
		{
			inputStream = new Scanner(new File("Files/Receipts/" + receiptFile));
		}
		catch(FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null,"File Not Found");
		}
		while(inputStream.hasNextLine())
			a.enqueue(inputStream.nextLine());
		inputStream.close();
	}


	public static void main (String[] args){
		LinkedListLoader.fileNames("2013-07-26", "2013-07-26");
		a.print();

	}

}