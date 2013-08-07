package pointOfSale;

public class ReceiptQueue 
{
	protected ReceiptNode front;   
	protected ReceiptNode current;
	protected ReceiptNode rear;
	private int count;

	public ReceiptQueue()
	{
		front = null;
		rear = null;
		current = null;
	}

	public void enqueue(String element)
	{ 
		ReceiptNode newNode = new ReceiptNode(element);
		if(rear == null) {
			front = newNode;
		}
		else {
			rear.setLink(newNode);
		}
		rear = newNode;
		count++;
	}     

	public int getCount (){
		return count;
	}

	public String getfront()  {
		return front.getData();
	}

	private void Print(ReceiptNode listRef)
	{
		if (listRef != null)
		{
			Print(listRef.getLink());
			System.out.println(" " + listRef.getData());
		}
	}

	public void print()
	{
		Print(front);
	}

	public void setCurrentToFront() {
		current = front;
	}

	public void removeCurrent() {
		rear.setData("Gone");
	}

	public String getCurrentString(){
		String x = current.getData();
		current = current.getLink();
		return x;
	}
	public ReceiptNode getCurrent() {
		return current;
	}




	public ReceiptNode dequeue()
	{      
		ReceiptNode element = null;
		if (!isEmpty()) {
			element = front;
			front = front.getLink();
			if(front == null) {
				rear = null;
			}
			count--;
		}
		return element;
	}


	public boolean isEmpty()
	{              
		return (front == null);
	}
}