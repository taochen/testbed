package ew.tax;

import java.util.Random;

public abstract class Item {

	protected Random r = new Random();

	protected String type;
	protected double price;
	protected double weight;
	protected int[] amounts;
	
	
	protected boolean isUnique = false;
	
	
	public boolean isUnique() {
		return isUnique;
	}

	public Item( String type, double price, double weight,
			int[] amounts) {
		super();
		this.type = type;
		this.price = price;
		this.weight = weight;
		this.amounts = amounts;
	}

	public String toString(){
		return "Item: " + type;
	}
	
	public Breakdown select(double amount){
		if(price == amount) {
			return new Breakdown(this, 1);
		}
		
		return null;
	}
	
	public Breakdown randomSelect(double figure, double remaining){
		// System.out.print("*****"+price+"\n");
		
		if (amounts[amounts.length-1]*price < figure &&
				amounts[amounts.length-1]*price < remaining) {
			return new Breakdown(this, amounts[amounts.length-1]);
		}
		
		
		 int size = 0;
		 for (int i = 0; i < amounts.length ;i++) {
			/* if (price == 3) {
			 System.out.print("******amounts[i]*price: " +amounts[i]*price + " figure " + figure + "\n");
			 }*/
			 if (amounts[i]*price > figure || amounts[i]*price > remaining){
				 size = i;
				 break;
			 }
		 }
		 
		 if (size == 0) {
			 return null;
		 }
		
		 return new Breakdown(this, amounts[r.nextInt(size)]);
	}
	
	public Breakdown cleanSelect(double remaining){
		

		if (amounts[amounts.length-1]*price < remaining) {
			return new Breakdown(this, amounts[amounts.length-1]);
		}
		
		 int size = 0;
		 for (int i = 0; i < amounts.length ;i++) {
			
			 if (amounts[i]*price > remaining){
				 size = i-1;
				 break;
			 }
		 }
		 
		
		 
		 if (size == -1) {
			 return null;
		 }
		 
		 return new Breakdown(this, amounts[size]);
	}
	
	public int isFit(double remaining) {
		
		
		
		for (int i : amounts) {
			
			if (price * i == remaining) {
				return i;
			}
			
		}
		
		return -1;
	}
	
	public double getPrice(){
		return price;
	}
	
	
	public double getWeight(){
		return weight;
	}
}
