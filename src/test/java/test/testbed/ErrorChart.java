package test.testbed;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.CategoryAnnotation;
import org.jfree.chart.annotations.CategoryTextAnnotation;
import org.jfree.chart.annotations.XYTitleAnnotation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.TextAnchor;

public class ErrorChart extends ApplicationFrame {

	static List<Record> recoder = new LinkedList<Record>();
	public final JFreeChart chart;
	// Corresponding to column number
	public static int numberOfRound = 0;

	static int currentRound = 0;
	// Number of lines
	static final int division = 3;

	static int numberOfCompletedNode = 0;

	public static boolean executable = true;

	public static final Object mutualLock = new Byte[0];
	
	private static final String title = "";//"Performance comparison";
	
	
	private static final double[] armaThroughputActual = {
		 0.3706734526813062,
		 0.23307204268024478,
		 0.1410883468254055,
		 0.13533008342687142
	
	};
	
	private static final double[] throughputActual = {
		//0.18108434455769956,
		0.1628297948276293,
		0.13081995231254948,
		0.1470248954355147,
		0.12843026555440898
	
	};
	
	
	private static final double[] responseActual = {
		//0.1891392835662221,
		0.13855981914885723,
		0.1299288059590181,
		0.12165163293482649,
		0.11564676174082333
	};
	

	private static final double[] armaResponseActual = {
		//0.4866647656335968,
		0.4372819485603318,
		0.32850301612800514,
		0.29028257363358184,
		0.2730952654394793
	};
	


	private static final double[] avActual = {
		0.007727233172623654,
		0.007435354967979867,
		0.004813402266464209,
		0.0035619580607944242
		
		
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
		final CategoryDataset dataset = createDataset();
		chart = createChart(dataset);
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(380, 160));
		setContentPane(chartPanel);
	}

	/**
	 * Creates a sample dataset.
	 * 
	 * @return The dataset.
	 */
	private CategoryDataset createDataset() {


		   // row keys...
        final String series1 = "S-ANN-R";
        final String series2 = "S-ARMAX-R";
        final String series3 = "S-ANN-T";
        final String series4 = "S-ARMAX-T";
        final String series5 = "S-ANN-A";
        final String series6 = "S-ARMAX-A";
      

        // column keys...
        /*final String category1 = "Response Time";
        final String category2 = "Throughput";
        final String category3 = "Availability";*/
        
        
        final String category1 = "40%";
        final String category2 = "60%";
        final String category3 = "80%";
        
        final String category4 = "100%";
        // create the dataset...
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        
        dataset.addValue(responseActual[0]*100, series1, category1);
        dataset.addValue(armaResponseActual[0]*100, series2, category1);
        dataset.addValue(throughputActual[0]*100, series3, category1);
        dataset.addValue(armaThroughputActual[0]*100, series4, category1);
        dataset.addValue(avActual[0]*100, series5, category1);
        dataset.addValue(armaAvActual[0]*100, series6, category1);
        
        dataset.addValue(responseActual[1]*100, series1, category2);
        dataset.addValue(armaResponseActual[1]*100, series2, category2);
        dataset.addValue(throughputActual[1]*100, series3, category2);
        dataset.addValue(armaThroughputActual[1]*100, series4, category2);
        dataset.addValue(avActual[1]*100, series5, category2);
        dataset.addValue(armaAvActual[1]*100, series6, category2);
        
        dataset.addValue(responseActual[2]*100, series1, category3);
        dataset.addValue(armaResponseActual[2]*100, series2, category3);
        dataset.addValue(throughputActual[2]*100, series3, category3);
        dataset.addValue(armaThroughputActual[2]*100, series4, category3);
        dataset.addValue(avActual[2]*100, series5, category3);
        dataset.addValue(armaAvActual[2]*100, series6, category3);
        
        dataset.addValue(responseActual[3]*100, series1, category4);
        dataset.addValue(armaResponseActual[3]*100, series2, category4);
        dataset.addValue(throughputActual[3]*100, series3, category4);
        dataset.addValue(armaThroughputActual[3]*100, series4, category4);
        dataset.addValue(avActual[3]*100, series5, category4);
        dataset.addValue(armaAvActual[3]*100, series6, category4);
	
		return dataset;

	}
	
	private void createThroughputData (XYSeries series1, XYSeries series2){
		int i = 1;
		
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
    private JFreeChart createChart(final CategoryDataset dataset) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createBarChart(
            "",         // chart title
            "",               // domain axis label
            "SMAPE (%)",                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

        // set the background color for the chart...
        chart.setBackgroundPaint(Color.white);
      
        // get a reference to the plot for further customisation...
        final CategoryPlot plot = chart.getCategoryPlot();
       

		LegendTitle lt = chart.getLegend();
		// lt.setItemFont(new Font("Dialog", Font.PLAIN, 9));
		//lt.setBackgroundPaint(new Color(200, 200, 255, 100));
		lt.setFrame(new BlockBorder(Color.white));
		lt.setPosition(RectangleEdge.RIGHT);
		lt.setItemFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));

		
        plot.setBackgroundPaint(Color.white);
        //plot.setDomainGridlinePaint(Color.white);
        //plot.setRangeGridlinePaint(Color.white);
        ((BarRenderer) plot.getRenderer()).setBarPainter(new StandardBarPainter());

        
        final IntervalMarker target = new IntervalMarker(4.5, 18.5);
      
        target.setLabel("Target Range");
        target.setLabelFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
        target.setLabelAnchor(RectangleAnchor.LEFT);
        target.setLabelTextAnchor(TextAnchor.CENTER_LEFT);
        target.setPaint(new Color(222, 222, 255, 128));
       // plot.addRangeMarker(target, Layer.BACKGROUND);
      
        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        CategoryAxis domain = (CategoryAxis) plot.getDomainAxis();
        rangeAxis.setTickUnit(new NumberTickUnit(10));
    	rangeAxis.setLabelFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
		domain.setLabelFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
        
        // disable bar outlines...
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        renderer.setShadowVisible(false);
        // set up gradient paints for series...
        final GradientPaint gp0 = new GradientPaint(
                0.0f, 0.0f, Color.blue, 
                0.0f, 0.0f, Color.blue
            );
            
            
            final GradientPaint gp1 = new GradientPaint(
                0.0f, 0.0f, Color.red, 
                0.0f, 0.0f, Color.red
            );
            final GradientPaint gp2 = new GradientPaint(
                0.0f, 0.0f, Color.magenta, 
                0.0f, 0.0f, Color.magenta
            );
            
            final GradientPaint gp3 = new GradientPaint(
                    0.0f, 0.0f, Color.orange, 
                    0.0f, 0.0f, Color.orange
                );
            
            final GradientPaint gp4 = new GradientPaint(
                    0.0f, 0.0f, Color.cyan, 
                    0.0f, 0.0f, Color.cyan
                );
        renderer.setSeriesPaint(0, gp0);
        renderer.setSeriesPaint(1, gp1);
        renderer.setSeriesPaint(2, gp2);
        renderer.setSeriesPaint(3, gp3);

        renderer.setSeriesPaint(4, gp4);


        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );
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
		
		try {
			Thread.sleep((long)2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		File file = new File("/Users/tao/research/chart.png");
		try {
			ChartUtilities.saveChartAsPNG(file, demo.chart, 380, 170);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

