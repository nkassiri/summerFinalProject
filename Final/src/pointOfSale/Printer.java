package pointOfSale;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class Printer implements Printable 
{
	private String printData;


	public Printer(String printDataIn)
	{
		this.printData = printDataIn;
	}
	int[] pageBreaks;  // array of page break line positions.

	/* Synthesise some sample lines of text */
	String[] textLines;
	private void initTextLines() {
		textLines = printData.split("\\n");
	}
	@Override
	public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException
	{

		//    // Adding the "Imageable" to the x and y puts the margins on the page.
		//    // To make it safe for printing.
		//    Graphics2D g2d = (Graphics2D)g;
		//    int x = (int) pf.getImageableX();
		//    int y = (int) pf.getImageableY();        
		//    g2d.translate(x, y); 
		//
		//    // Calculate the line height
		//    Font font = new Font("Serif", Font.PLAIN, 10);
		//    FontMetrics metrics = g.getFontMetrics(font);
		//    int lineHeight = metrics.getHeight();
		//    
		//    double pageHeight = pf.getImageableHeight();
		//    
		//    int linesPerPage = ((int)pageHeight)/lineHeight);
		//    int numBreaks = (textLines.length-1)/linesPerPage;
		//    int[] pageBreaks = new int[numBreaks];
		//    for (int b=0; b < numBreaks; b++) {
		//        pageBreaks[b] = (b+1)*linesPerPage; 
		//    }
		//    
		//    
		//
		//    BufferedReader br = new BufferedReader(new StringReader(printData));
		//
		//    // Draw the page:
		//    try
		//    {
		//        String line;
		//        // Just a safety net in case no margin was added.
		//        x += 50;
		//        y += 50;
		//        while ((line = br.readLine()) != null)
		//        {
		//            y += lineHeight;
		//            g2d.drawString(line, x, y);
		//        }
		//    }
		//    catch (IOException e)
		//    {
		//        // 
		//    }
		//
		//    return PAGE_EXISTS;
		//}

		Font font = new Font("Serif", Font.PLAIN, 10);
		FontMetrics metrics = g.getFontMetrics(font);
		int lineHeight = metrics.getHeight();

		if (pageBreaks == null) {
			initTextLines();
			int linesPerPage = (int)(pf.getImageableHeight()/lineHeight);
			int numBreaks = (textLines.length-1)/linesPerPage;
			pageBreaks = new int[numBreaks];
			for (int b=0; b<numBreaks; b++) {
				pageBreaks[b] = (b+1)*linesPerPage; 
			}
		}

		if (pageIndex > pageBreaks.length) {
			return NO_SUCH_PAGE;
		}

		/* User (0,0) is typically outside the imageable area, so we must
		 * translate by the X and Y values in the PageFormat to avoid clipping
		 * Since we are drawing text we
		 */
		Graphics2D g2d = (Graphics2D)g;
		g2d.translate(pf.getImageableX(), pf.getImageableY());

		/* Draw each line that is on this page.
		 * Increment 'y' position by lineHeight for each line.
		 */
		int y = 0; 
		int start = (pageIndex == 0) ? 0 : pageBreaks[pageIndex-1];
		int end   = (pageIndex == pageBreaks.length)
				? textLines.length : pageBreaks[pageIndex];
		for (int line=start; line<end; line++) {
			y += lineHeight;
			g.drawString(textLines[line], 0, y);
		}

		/* tell the caller that this page is part of the printed document */
		return PAGE_EXISTS;

	}
}