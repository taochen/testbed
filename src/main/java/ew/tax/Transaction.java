package ew.tax;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public abstract class Transaction {

	protected Random r = new Random();

	protected double amount;
	
	protected double remaining;
	
	protected List<Breakdown> breakdowns = new ArrayList<Breakdown>();
	
	protected Set<Item> items = new HashSet<Item>();
	
	public Transaction(double remaining) {
		super();
		this.remaining = remaining;
	}

	public double getRemaining(){
		return remaining;
	}
	
	public double fit(List<Item> items, double total){
		//System.out.print("total: " +total+ " remaining: " +remaining +  "\n");
		for (Item item : items) {
			
			int i = item.isFit(remaining);
			
			if (i != -1 && total > remaining) {
				breakdowns.add(new Breakdown(item, i));
				double value = total - remaining;
				remaining = 0;

				System.out.print("fit remaining: " +(total-value)+ "\n");
				return value;
			}
		}
		
		
		return total;
	}
	
	public double breakdown(List<Item> items, double total){
		double figure = total;
		List<Item> sub = new ArrayList<Item>();
		sub.addAll(items);
		
		
		
		while (sub.size() != 0 && remaining != 0) {
			Item item = selectItem(sub);
			Breakdown b = item.randomSelect(figure, remaining);
			if (b == null ) {
				sub.remove(item);
				continue;
			}
			
			if (item.isUnique() && items.contains(item)) {
				continue;
			}
			
			items.add(item);
			breakdowns.add(b);
			figure -= b.getTotal();
			remaining -= b.getTotal();
		}
		
		return figure;
		
	}
	
	public double breakdown(List<Item> items){
		List<Item> sub = new ArrayList<Item>();
		sub.addAll(items);
		
		
		
		while (sub.size() != 0 && remaining != 0) {
			
			Item item = selectItem(sub);
			Breakdown b = item.randomSelect(remaining, remaining);
			if (b == null ) {
				sub.remove(item);
				continue;
			}
			
			if (item.isUnique() && items.contains(item)) {
				continue;
			}
			
			items.add(item);
			
			breakdowns.add(b);
			remaining -= b.getTotal();
		}
		
		return remaining;
		
	}
	
	public boolean isCompleted(){
		return remaining == 0;
	}
	
	protected Item selectItem(List<Item> sub) {
		double[] p = new double[sub.size()];
		
		double total = 0;
		for (Item item : sub) {
			total += item.getWeight();
		}
		
		
		for (int i = 0; i < sub.size(); i++) {
			p [i] = sub.get(i).getWeight()/total;
		}
	
		return sub.get(Util.doProbabilisticSelection(p));
	}
	
	
	public double[] getQuota(){
		double[] d = new double[]{0,0};
		for (Breakdown b : breakdowns) {
			if (b.isTaxable()) {
				d[0] += b.getTotal();
			} else {
				d[1] += b.getTotal();; 
			}
		}
		
		return d;
	}
	
	public String toString(){
		String result = "================\n";
		for (Breakdown b : breakdowns) {
			result += b.toString() + "\n";
		}
		return result + "Remaining: " + remaining + "\n================\n";
	}
}
