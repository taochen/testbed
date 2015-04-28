package test.testbed.region;

import test.testbed.ContinousAccurcyChart;
import test.testbed.ContinousAccurcyChart_1;

public class Output {

	private static double[] a = {
		-1.3352617362819967,
		-0.1430461191545773,
		0.3478658494028489,
		-1.4018679201901265,
		-1.849384603896466,
		0.43077770185333075,
		-0.6165683736093447,
		-1.286421186895696,
		-1.1497898989635635,
		-0.15028681362178287,
		-1.0128471991222199
	};
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		for (double d : ContinousAccurcyChart.armaAvActual) {
			System.out.print(d * 1+"\n");
		}
		
		for (double d : ContinousAccurcyChart_1.armaAvActual) {
			System.out.print(d * 1+"\n");
		}
		
		//System.out.print("===");
	}

}
