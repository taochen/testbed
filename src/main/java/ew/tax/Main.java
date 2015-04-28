package ew.tax;

import java.util.*;

import org.apache.commons.math3.distribution.NormalDistribution;

public class Main {
	
	private static Random r = new Random();
	private static double max = 10;
	// P from 65% to 85%
	private static double[] p = new double[]{
		/*0.01,
		0.02,
		0.03,
		0.04,
		0.05,
		0.06,
		0.07,
		0.08,
		0.09,
		0.1,
		0.09,
		0.08,
		0.07,
		0.06,
		0.05,
		0.04,
		0.03,
		0.02,
		0.01,*/
		0.1,
		0.2,
		0.3,
		0.3,
		0.2,
		0.1
		
	};
	
	private static double[] pre = new double[]{
		/*0.67,
		0.68,
		0.69,
		0.70,
		0.71,
		0.72,
		0.73,
		0.74,
		0.75,
		0.76,
		0.77,
		0.78,
		0.79,
		0.80,
		0.71,
		0.82,
		0.83,
		0.84,
		0.85,*/
		0.65,
		0.70,
		0.75,
		0.80,
		0.85,
		0.90
		
	};
	private static List<Item> nontaxable = new ArrayList<Item>();
	private static List<Item> taxable = new ArrayList<Item>();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		double t = 0;
		for (double p1 : p) {
			t += p1;
		}
		
		for (int i = 0; i < p.length; i++) {
			p[i] = p[i]/t;
		}
		
		
		nontaxable.add(new Nontaxable("herb", 7, 0.7,new int[]{1,2,3,4,5,6,7,10,14,15,20,21,25,28,30,35}));
		nontaxable.add(new Nontaxable("powder", 8, 0.3,new int[]{1,2,3,4,5,6,7,10,14,15,20,21,25,28,30,35}));
		
		
		taxable.add(new Taxable("Massage-15", 13, 0.1,new int[]{1}));
		taxable.add(new Taxable("Massage-20", 17, 0.1,new int[]{1}));
		taxable.add(new Taxable("Massage-30", 25, 0.1,new int[]{1}));
		taxable.add(new Taxable("Massage-45", 35, 0.1,new int[]{1}));
		taxable.add(new Taxable("Massage-60", 45, 0.1,new int[]{1}));
		taxable.add(new Taxable("Massage-90", 65, 0.1,new int[]{1}));
		taxable.add(new Taxable("Massage-strong", 1, 0.1,new int[]{10,15,20,30,45,60,90}));
		
		taxable.add(new Taxable("P1", 9, 0.1,new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20}));
		taxable.add(new Taxable("P2", 12, 0.1,new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20}));
		taxable.add(new Taxable("P3", 8, 0.1,new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,2010}));
		taxable.add(new Taxable("P4", 18, 0.1,new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20}));
		taxable.add(new Taxable("P5", 16, 0.1,new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20}));
		taxable.add(new Taxable("P6", 5, 0.1,new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20}));
		taxable.add(new Taxable("P7", 8, 0.1,new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20}));
		taxable.add(new Taxable("P8", 8, 0.1,new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20}));
		taxable.add(new Taxable("P9", 8, 0.1,new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20}));
		taxable.add(new Taxable("P10", 8, 0.1,new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20}));
		
		
		taxable.add(new Taxable("Consultation", 10, 0.1,new int[]{1}, true));
		taxable.add(new Taxable("Test1", 70, 0.05,new int[]{1}, true));
		taxable.add(new Taxable("Test2", 58, 0.05,new int[]{1}, true));
		taxable.add(new Taxable("Test3", 25, 0.05,new int[]{1}, true));
		taxable.add(new Taxable("Test4", 940, 0.002,new int[]{1}, true));
		taxable.add(new Taxable("Test4", 180, 0.05,new int[]{1}, true));
		
		taxable.add(new Taxable("Acupuncture", 35, 0.1,new int[]{1}, true));
		
		Cash cash = new Cash(740);
		List<Card> cards = new ArrayList<Card>();
		cards.add(new Card(145));
		cards.add(new Card(120));
		cards.add(new Card(105));
		cards.add(new Card(105));
		cards.add(new Card(105));
		cards.add(new Card(120));
		cards.add(new Card(112.5));
		cards.add(new Card(84));
		cards.add(new Card(50));
		
		double total = 0;
		for (Card c : cards) {
			total += c.getRemaining();
		}
		
		total += cash.getRemaining();
		System.out.print("Total: " +total + "\n");
		do{
			
		} while (max < run(cards, cash, total));
		
		//System.out.print(r.nextInt(1) + "\n");
	}
	
	
	public static double run(List<Card> cards, Cash cash,double total){
		
		Collections.shuffle(cards);
		double percentage = pre[Util.doProbabilisticSelection(p)];
		
		double nonTax = Math.round(total*percentage);
		nonTax += (r.nextDouble() > 0.5)? 0.5 : 0; 
		
		double tax = total - nonTax;
		
		double oringalTax = 0;
		double oringalnonTax = 0;
		
		System.out.print("tax: " +(oringalTax=tax) + "\n");
		System.out.print("nonTax: " +(oringalnonTax=nonTax) + "\n");
		double cardRemian = 0;
		for (Card c : cards) {
		   tax = c.fit(taxable, tax);
			
			
			cardRemian += c.getRemaining();
		}
		
		System.out.print("cardRemian: " +cardRemian + "\n");
		
		System.out.print("taxable quto : " +tax + "\n");
		
		double taxCard = Math.round(tax*r.nextDouble());
		taxCard = (cardRemian > taxCard)? taxCard : cardRemian;
		double taxCash = tax - taxCard;
		
		System.out.print("taxCard: " +taxCard + "\n");
		System.out.print("taxCash: " +taxCash + "\n");
		
		for (Card c : cards) {
			if (!c.isCompleted()) {
				taxCard = c.breakdown(taxable, taxCard);
			}
			
		}
		
		// Add the remaing to cash.
		taxCash += taxCard;
		
		System.out.print("taxCash with left taxCard: " +taxCash + "\n");
		taxCash = cash.breakdown(taxable, taxCash) ;
		
		System.out.print("Final remaining taxCash: " +taxCash + "\n");
		
		for (Card c : cards) {
			if (!c.isCompleted()) {
				c.cleanUpAmount(nontaxable);
			}
			
		}
		
		cash.breakdown(nontaxable);
		
		System.out.print("***********************\n");
		System.out.print("*********CARD**************\n");
		double re = 0;
		double finalTax = 0;
		double finalNontax = 0;
		for (Card c : cards) {
			System.out.print(c.toString() + "\n");
			//re += c.getRemaining();
			double[] d = c.getQuota();
			finalTax += d[0];
			finalNontax += d[1];
		}
		
		System.out.print("*********CASH**************\n");
		System.out.print(cash.toString() + "\n");
		//re += cash.getRemaining();
		
		double[] d = cash.getQuota();
		finalTax += d[0];
		finalNontax += d[1];
		
		System.out.print("Mail charge: " + (re = (total - finalTax- finalNontax)) + "\n");
		System.out.print("Tax: " + oringalTax + " final: " + finalTax + " P: " + finalTax/total  + "\n");
		System.out.print("Nontax: " + oringalnonTax + " final: " + finalNontax + " P: " + finalNontax/total  + "\n");
		return re;
	}

}
