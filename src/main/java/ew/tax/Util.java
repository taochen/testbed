package ew.tax;

public class Util {

	
	public static int doProbabilisticSelection(double[] probability) {
		double p = Math.random();
		double cumulativeProbability = 0.0;
		for (int i = 0; i < probability.length;i++) {
		    cumulativeProbability += probability[i];
		    if (p <= cumulativeProbability && probability[i] != 0) {
		        return i;
		    }
		}
		
		// Select the last one if the random number is too large
		// However, this is unlikely to occur.
		return probability.length-1;
	}

}
