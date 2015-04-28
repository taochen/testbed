package ew.tax;

public class Breakdown {

	private Item item;
	private int amount;
	
	public Breakdown(Item item, int amount) {
		super();
		this.item = item;
		this.amount = amount;
	}
	
	public double getTotal(){
		return item.getPrice() * amount;
	}
	
	
	public String toString(){
		return item.toString() + ", Amount: " + amount;
	}
	
	public boolean isTaxable(){
		return item instanceof Taxable;
	}
	
}
