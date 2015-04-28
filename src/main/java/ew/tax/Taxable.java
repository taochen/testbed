package ew.tax;

public class Taxable extends Item {

	public Taxable(String type, double price, double weight, int[] amounts) {
		super(type, price, weight, amounts);
		// TODO Auto-generated constructor stub
	}
	
	public Taxable(String type, double price, double weight, int[] amounts, boolean isUnique) {
		super(type, price, weight, amounts);
		this.isUnique = isUnique;
	}

}
