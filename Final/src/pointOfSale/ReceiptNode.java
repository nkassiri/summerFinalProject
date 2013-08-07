package pointOfSale;

public class ReceiptNode {
	private String data;
	private ReceiptNode link;

	public ReceiptNode(String data) {
		this.data = data;
		link = null;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public ReceiptNode getLink() {
		return link;
	}

	public void setLink(ReceiptNode link) {
		this.link = link;
	}

}
