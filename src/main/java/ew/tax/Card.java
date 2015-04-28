package ew.tax;

import java.util.List;

public class Card extends Transaction {

	public Card(double remaining) {
		super(remaining);
		// TODO Auto-generated constructor stub
	}

	public boolean cleanUpAmount(List<Item> items){
		Item item = selectItem(items);
		// System.out.print(remaining+"***********************\n");
		Breakdown b = item.cleanSelect(remaining);
		if (b == null) {
			return false;
		}
		
		breakdowns.add(b);
		remaining -= b.getTotal();
		return true;
	}
}
