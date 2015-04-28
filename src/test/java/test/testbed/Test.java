package test.testbed;

import java.util.PriorityQueue;
import java.util.Queue;



public class Test {

	protected static Queue<Ant> solutions = new PriorityQueue<Ant>();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		solutions.add(new BasicAnt(3));
		solutions.add(new BasicAnt(1));
		solutions.add(new BasicAnt(7));
		
		
		System.out.print(solutions.peek().value + "\n");
		solutions.remove(solutions.peek());
		System.out.print(solutions.peek().value + "\n");
		
		System.out.print(Math.random() + "\n");

	}
	
	public static abstract class Ant implements Comparable<Ant> {
		public int value = 0;
		public int compareTo(Ant another) { 	
			// Means better
			return this.value < another.value? -1 : 1;
		}
	}
	
	public static class BasicAnt extends Ant {
		public BasicAnt (int value) {
			super.value = value;
		}
	}

}
