package test.testbed;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class ErrorChart extends ApplicationFrame {

	static List<Record> recoder = new LinkedList<Record>();

	// Corresponding to column number
	public static int numberOfRound = 0;

	static int currentRound = 0;
	// Number of lines
	static final int division = 3;

	static int numberOfCompletedNode = 0;

	public static boolean executable = true;

	public static final Object mutualLock = new Byte[0];
	
	private static final String title = "";//"Performance comparison";
	
	private static final double[] throughputIdeal = {
		0.4515516942474389,
		0.43500267927501973,
		0.38888888888888895,
		0.4822855791962175,
		0.5106382978723405,
		0.39835843183609143,
		0.4515516942474389,
		0.46453900709219864,
		0.4633569739952719,
		0.32269503546099293,
		0.14184869976359338,
		0.04255319148936171,
		0.00236414499605989,
		0.08510638297872342,
		0.14539007092198583,
		0.1914957446808511,
		0.20095232466509064,
		0.21040189125295508,
		0.2293144208037825,
		0.22222962962962964,
		0.2529550827423168,
		0.23405035460992907,
		0.21278014231680065,
		0.23877068557919623,
		0.2376044922537607,
		0.0933806146572104,
		0.04964869987392015,
		0.19385988967691098,
		0.37234042553191493,
		0.5614844365642239,
		0.6855791962174941,
		0.693853427895981,
		0.7104255713159969,
		0.6832379038613081,
		0.6501398739164697,
		0.6513219464144997,
		0.6796916863672183,
		0.6725768321513003,
		0.6855791962174941,
		0.3557919621749409,
		0.2186761229314421,
		0.004728447607040015,
		0.10520094562647754,
		0.2505993695823483,
		0.38888888888888895,
		0.4326529560441613,
		0.4113475177304965,
		0.38300425617024114,
		0.4479905437352246,
		0.5094732466509063,
		0.4113475177304965,
		0.47164692671394803,
		0.42907801418439717,
		0.4550827423167849,
		0.2210475571315997,
		0.13474728162330551,
		0.00945657998423956,
		0.0059101654846335705,
		0.09929408983451536,
		0.12766382978723406,
		0.2080447596532703,
		0.21276595744680854,
		0.24350693459416864,
		0.2588738770685579,
		0.2505993695823483,
		0.22222962962962964,
		0.23049645390070922,
		0.23286828211189914,
		0.21513002364066194,
		0.2021411352009606,
		0.08392434988179669,
		0.04018912529550828,
		0.18322123719464148,
		0.39952718676122934,
		0.5756693065405832,
		0.6725992513790386,
		0.6820558313632783,
		0.706879353821907,
		0.6725768321513003,
		0.622952206461781,
		0.6288625689519307,
		0.7009689913317573,
		0.6666666666666667,
		0.621770133963751,
		0.3333444444444445,
		0.22459377462568952,
		0.13712498060416042,
		0.5189298266351458,
		0.6844199763593382,
		0.7718676122931443,
		0.815630023640662,
		0.8002630811662726,
		0.624134278959811,
		0.7919621749408985,
		0.7943791979827224,
		0.8096926713947991,
		0.6087876294064019,
		0.8912826635145784,
		0.5035460992907802,
		0.2505993695823483,
		0.04964704491725768,
		0.05437352245862885,
		0.1749408983451537,
		0.28250591016548465,
		0.2943360520094563,
		0.2967001970055162,
		0.3132492119779354,
		0.2895981087470449,
		0.3534396769109535,
		0.34989345941686373,
		0.36404192520070294,
		0.3239202564858535,
		0.31678486997635935,
		0.1761288022064618,
		0.12056737588652483,
		0.02245937746256895,
		0.008274783312320025,
		0.23049645390070922,
		0.3605200945626478,
		0.5851258865248228,
		0.5898541765169425,
		0.5732860520094563,
		0.5508457840819543,
		0.5898541765169425,
		0.6146572104018913,
		0.622952206461781,
		0.5260047281323877,
		0.5189298266351458,
		0.5744872340425532,
		0.20449172576832153,
		0.16312056737588654,
		0.00709243498817967,
		0.12174940898345155,
		0.15721564223798268,
		0.23401915205632157,
		0.21517305681758625,
		0.2529635145784082,
		0.23641449960598898,
		0.24114278959810875,
		0.24703668297606007,
		0.22460126133440067,
		0.2115839243498818,
		0.24941729708431837,
		0.20567375886524825,
		0.08156300236406619,
		0.06264984239558707,
		0.13239211977935383,
		0.4231678486997636,
		0.5969665103888018,
		0.8014451536643027,
		0.7659829787234044,
		0.7612293144208039,
		0.4905600866824272,
		0.8132387706855793,
		0.5496453900709221,
		0.7624113475177305,
		0.6761454688731284,
		0.7045152088258472,
		0.5401891252955083,
		0.2517814420803782,
		0.06501182033096928,
		0.06264775413711585,
		0.11465721040189125,
		0.20567375886524825,
		0.2080447596532703,
		0.18085106382978725,
		0.1997436196844361,
		0.20924078153673603,
		0.2186761229314421,
		0.2434988179669031,
		0.23996071710007882,
		0.22577584712371945,
		0.2293144208037825,
		0.13239211977935383,
		0.06855791962174941,
		0.011820330969267141,
		0.050827423167848704,
		0.37708112687155243,
		0.5815602836879433,
		0.8428457859548826,
		0.7151538613081168,
		0.7978989361702128,
		0.6004728132387708,
		0.5851063829787235,
		0.8321513002364067,
		0.8085375886524824,
		0.6867612293144209,
		0.537842986603625,
		0.664324743892829,
		0.3037926319936958,
		0.132387706855792,
		0.010638297872340427,
		0.10638297872340427,
		0.17375886524822695,
		0.23404255319148937,
		0.23167848699763596,
		0.23995271867612294,
		0.21513002364066194,
		0.22104018912529552,
		0.21394799054373523,
		0.21986548463356975,
		0.23050413711583925,
		0.25650118203309696,
		0.21513002364066194,
		0.06264775413711585,
		0.04137391656160013,
		0.00236390860519937,
		0.30734909445760095,
		0.5721230890464933,
		0.7966903073286052,
		0.7115839243498818,
		0.41610338941952124,
		0.5839438140267927,
		0.48463356973995275,
		1.0,
		0.7908065011820332
	
	};
	
	
	private static final double[] armaThroughputActual = {
		 0.3706734526813062,
		 0.23307204268024478,
		 0.1410883468254055,
		 0.13533008342687142
	
	};
	
	private static final double[] throughputActual = {
		//0.18108434455769956,
		 0.1993445048460364,
		 0.19280655841874364,
		 0.15059005771591938,
		 0.12873880398308452
	
	};
	
	
	private static final double[] responseActual = {
		//0.1891392835662221,
		0.19827729657299695,
		0.15178553619135673,
		0.1615528921894703,
		0.1255875254493435
	};
	

	private static final double[] armaResponseActual = {
		//0.4866647656335968,
		0.4372819485603318,
		0.32850301612800514,
		0.29028257363358184,
		0.2730952654394793
	};
	


	private static final double[] avActual = {
		0.007639337731958022,
		0.0072436704434898115,
		0.004258117216039143,
		0.004793907976199847
		
		
	};
	
	private static final double[] armaAvActual = {
		0.012902744035490809,
		0.010065935548177899,
		0.006715924799074933,
		0.00519315638503653
		 
	};
	
	
	private static double maxOfThroughput = 28.199060031332287;
	private static double maxOfResponse = 0.1761915641476274;
	private static double maxOfAv = 1.0;
	
	
	
	/**
	 * Max of edu.rice.rubis.servlets.SearchItemsByCategory's Availability.rtf=1.0
Max of edu.rice.rubis.servlets.SearchItemsByCategory's Response Time.rtf=0.1761915641476274
Max of edu.rice.rubis.servlets.SearchItemsByCategory's Throughput.rtf=28.199060031332287

Response Time:
Prediction RMAPE: 0.20471427118277635


Throughput:
Prediction RMAPE: 0.15320254706329603
Prediction RS: 0.9999946698839458

Availability:
Prediction RMAPE: 0.010236337390917246
Prediction RS: 0.9999996056410796

	 */

	
	
	/**
	 * Creates a new demo.
	 * 
	 * @param title
	 *            the frame title.
	 */
	public ErrorChart(final String title) {
		super(title);
		final XYDataset dataset = createDataset();
		final JFreeChart chart = createChart(dataset);
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(900, 570));
		setContentPane(chartPanel);
	}

	/**
	 * Creates a sample dataset.
	 * 
	 * @return The dataset.
	 */
	private XYDataset createDataset() {


		// create the dataset...
		XYSeries series1 = new XYSeries("ANN Response Time Error");
		XYSeries series2 = new XYSeries("ARMIA Response Time Error");
		XYSeries series3 = new XYSeries("ANN Throughput Error");
		XYSeries series4 = new XYSeries("ARMIA Throughput Error");
		XYSeries series5 = new XYSeries("ANN Availability Error");
		XYSeries series6 = new XYSeries("ARMIA Availability Error");
		

		XYSeriesCollection xyDataset = new XYSeriesCollection();
		xyDataset.addSeries(series1);
		xyDataset.addSeries(series2);
		xyDataset.addSeries(series3);
		xyDataset.addSeries(series4);
		xyDataset.addSeries(series5);
		xyDataset.addSeries(series6);
		//createThroughputData(series1, series2);
		//createResponsetData(series1, series2);
		//createAvailabilityData(series1, series2);
		this.createData(series1, series2, series3, series4, series5, series6);
		return xyDataset;

	}
	
	private void createThroughputData (XYSeries series1, XYSeries series2){
		int i = 1;
		for (double d : throughputIdeal) {
			series1.add(i, maxOfThroughput*d);
			i++;
		}
		i=1;
		for (double d : throughputActual) {
			series2.add(i, maxOfThroughput*d);;
			i++;
		}
	}
	
	private void createData (XYSeries series1, XYSeries series2,
			XYSeries series3, XYSeries series4,
			XYSeries series5, XYSeries series6){
		int i = 40;
		for (double d : responseActual) {
			series1.add(i, d);
			i += 20;
		}
	
		i=40;
		for (double d : armaResponseActual) {
			series2.add(i, d);
			i += 20;
		}
		
		i=40;
		for (double d : throughputActual) {
			series3.add(i, d);
			i += 20;
		}
		
		
		i=40;
		for (double d : armaThroughputActual) {
			series4.add(i, d);
			i += 20;
		}
		
		
		i=40;
		for (double d : avActual) {
			series5.add(i, d);
			i += 20;
		}
		
		
		i=40;
		for (double d : armaAvActual) {
			series6.add(i, d);
			i += 20;
		}
		
		
	
		
	}
	
	private void createAvailabilityData (XYSeries series1, XYSeries series2){
		int i = 1;
	
	}
	
	private double calculate(double[] ideal, double[] actual, List<Double> ampes){
		double MAPE = 0.0;
		double total = 0.0;
		for (int j = 0; j < ideal.length; j++) {
			MAPE = 0.0;
			  if ((actual[j] > 0 && ideal[j] < 0) ||
					   (actual[j] < 0 && ideal[j] > 0)) {
				   MAPE = Math.abs((ideal[j] + actual[j]) / 
						   (ideal[j] - actual[j]));
			   } else {
				   MAPE = Math.abs((ideal[j] - actual[j]) / 
						   (ideal[j]+ actual[j]));
			   }
			  total += MAPE;
			  ampes.add(MAPE);  
		}
		
		
		System.out.print("££££££££££££££££££Ideal \n\n\n");
		for (double d : ampes) {
			System.out.print(d + ",\n");
		}
		
		Collections.sort(ampes);
		
		return total;
	}

	/**
	 * Creates a sample chart.
	 * 
	 * @param dataset
	 *            a dataset.
	 * 
	 * @return The chart.
	 */
	private JFreeChart createChart(final XYDataset dataset) {

		
		// create the chart...
		final JFreeChart chart = ChartFactory.createXYLineChart(
				title, // chart title
				"Interval", // domain
																	// axis
																	// label
				"Mean Resp Time (s)",//"Average time (ms) taken for each service to complete", // range
																		// axis
																		// label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips
				false // urls
				);
	    /*Shape[] arrayOfShape = new Shape[4];
	    int[] arrayOfInt1 = { -3, 3, -3 };
	    int[] arrayOfInt2 = { -3, 0, 3 };
	    arrayOfShape[0] = new Polygon(arrayOfInt1, arrayOfInt2, 3);
	    arrayOfShape[1] = new Rectangle2D.Double(-2.0D, -3.0D, 3.0D, 8.0D);
	    arrayOfInt1 = new int[] { -3, 3, 3 };
	    arrayOfInt2 = new int[] { 0, -3, 3 };
	    arrayOfShape[2] = new Polygon(arrayOfInt1, arrayOfInt2, 3);
	    arrayOfShape[3] = new Rectangle2D.Double(-6.0D, -6.0D, 6.0D, 6.0D);
	    DefaultDrawingSupplier localDefaultDrawingSupplier = new DefaultDrawingSupplier(DefaultDrawingSupplier.DEFAULT_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE, arrayOfShape);
	  */


		// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
		// final StandardLegend legend = (StandardLegend) chart.getLegend();
		// legend.setDisplaySeriesShapes(true);
		// legend.setShapeScaleX(1.5);
		// legend.setShapeScaleY(1.5);
		// legend.setDisplaySeriesLines(true);

		chart.setBackgroundPaint(Color.white);

		final XYPlot plot = (XYPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.white);
		plot.setRangeGridlinePaint(Color.black);
		XYLineAndShapeRenderer localLineAndShapeRenderer = (XYLineAndShapeRenderer)plot.getRenderer();
	    localLineAndShapeRenderer.setBaseShapesVisible(true);
	    //localLineAndShapeRenderer.setBaseItemLabelsVisible(true);
	    localLineAndShapeRenderer.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
		// customise the range axis...
	    ValueAxis domain = (ValueAxis) plot.getDomainAxis();
	 
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		//rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setTickUnit(new NumberTickUnit(0.1));
		rangeAxis.setAutoRangeIncludesZero(true);

		// ****************************************************************************
		// * JFREECHART DEVELOPER GUIDE *
		// * The JFreeChart Developer Guide, written by David Gilbert, is
		// available *
		// * to purchase from Object Refinery Limited: *
		// * *
		// * http://www.object-refinery.com/jfreechart/guide.html *
		// * *
		// * Sales are used to provide funding for the JFreeChart project -
		// please *
		// * support us so that we can continue developing free software. *
		// ****************************************************************************

		// customise the renderer...
		final XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot
				.getRenderer();
		
		renderer.setSeriesPaint(0, Color.red);
		renderer.setSeriesPaint(1, Color.blue);
		//renderer.setSeriesPaint(2, Color.orange);
		renderer.setSeriesPaint(2, Color.magenta);
		renderer.setSeriesPaint(3, Color.green);
		renderer.setSeriesPaint(4, Color.orange);
		/*renderer.setSeriesStroke(0, new BasicStroke(2.0f,
				BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f,
				new float[] { 1.0f, 1.0f }, 10.0f));
		renderer.setSeriesStroke(2, new BasicStroke(2.0f,
				BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f,
				new float[] { 1.0f, 1.0f }, 10.0f));
		renderer.setSeriesStroke(1, new BasicStroke(2.0f,
				BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f,
				new float[] { 1.0f, 1.0f }, 10.0f));
		renderer.setSeriesStroke(3, new BasicStroke(2.0f,
				BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f,
				new float[] { 1.0f, 1.0f }, 10.0f));
		renderer.setSeriesStroke(4, new BasicStroke(2.0f,
				BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f,
				new float[] { 1.0f, 1.0f }, 10.0f));
		//renderer.setSeriesShape(1, new Ellipse2D.Double(-3, -3, 6, 6));*/
		renderer.setSeriesShape(1, null);
		renderer.setSeriesShapesFilled(0, false);
		renderer.setSeriesShapesFilled(1, false);
		renderer.setSeriesShapesFilled(2, false);
		
		// OPTIONAL CUSTOMISATION COMPLETED.

		return chart;
	}

	private static class Record {
		private String name;
		private double timeUsage;
		private String numberOfThread;

		public Record(String name, double timeUsage, String numberOfThread) {
			super();
			this.name = name;
			this.timeUsage = timeUsage;
			this.numberOfThread = numberOfThread;
		}

		public String getName() {
			return name;
		}

		public double getTimeUsage() {
			return timeUsage;
		}

		public String getNumberOfThread() {
			return numberOfThread;
		}
	}

	/**
	 * Starting point for the demonstration application.
	 * 
	 * @param args
	 *            ignored.
	 */
	public static void main(final String[] args) {

		final ErrorChart demo = new ErrorChart(title);
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);

	}
	
	
	public static void main1(final String[] args) {

		BufferedReader reader;
		List<Double> ideals = new ArrayList<Double>();
		List<Double> actuals = new ArrayList<Double>();
		try {
			reader = new BufferedReader(new FileReader("/Users/tao/research/monitor/rs"));
			String line = null;
			while((line = reader.readLine()) != null) {
				if(!line.contains("MSE")){
					actuals.add(Double.parseDouble(line.split(",")[0].split("=")[1]));
					ideals.add(Double.parseDouble(line.split(",")[1].split("=")[1]));
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//values = new LinkedList<Double>();

		System.out.print(ideals.size() + " Ideal \n\n\n");
		for (double d : ideals) {
			System.out.print(d + ",\n");
		}
		
		System.out.print(actuals.size()+" Actual \n\n\n");
		for (double d : actuals) {
			System.out.print(d + ",\n");
		}

		double MPE = 0.0;
		double yMean = 0.0;
		double RSS = 0.0;
		double Ry = 0.0;
		double MAPE = 0.0;
		int size = 0;
		
		
		for (int i = 0; i < actuals.size(); i++) {
			yMean += ideals.get(i);
		}
		yMean = yMean/ideals.size();
		
		double value = 0.0;
		for (int i = 180; i < actuals.size(); i++) {
			value =  actuals.get(i);
			System.out.print("Actual=" + value + ", Ideal=" + ideals.get(i) + "\n");
			MPE += Math.abs(ideals.get(i) - value);
			RSS += Math.pow((ideals.get(i) - value),2);
			//yMean += ideals.get(i);
			
			   if ((value > 0 && ideals.get(i) < 0) ||
					   (value < 0 && ideals.get(i) > 0)) {
				   MAPE += Math.abs((ideals.get(i) + value) / 
						   (ideals.get(i) - value));
			   } else {
				   MAPE += Math.abs((ideals.get(i) - value) / 
						   (ideals.get(i)+ value));
			   }
			   Ry += Math.pow((ideals.get(i) - yMean),2);
			   size++;
		}

		MPE = MPE/actuals.size();
		//yMean = yMean/actuals.size();
		
		
		System.out.print("Prediction RMAPE: " + (MAPE/size) + "\n");
		System.out.print("Prediction RS: " + (1-(RSS/Ry)) + "\n");
	}

	public synchronized static boolean record(String name, double timeUsage,
			String numberOfThread) {

		recoder.add(new Record(name, timeUsage, String.valueOf(Integer.parseInt(numberOfThread) * division)));
		numberOfCompletedNode++;
		if (numberOfCompletedNode % division == 0) {
			currentRound++;
			if (currentRound == numberOfRound) {
				main(null);
				return false;
			} else {
				executable = true;
				synchronized (mutualLock) {
					mutualLock.notifyAll();
				}

				return true;
			}
		}

		return false;
	}

}

