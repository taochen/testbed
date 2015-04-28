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
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RefineryUtilities;

public class PerformanceChart extends ApplicationFrame {

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
	
	
	private static final double[] ann = {
		334
	
	};
	
	private static final double[] arma = {
		112
	
	};
	
	
	private static final double[] annf = {
		5479
	};
	

	private static final double[] armaf = {
		113
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
	public PerformanceChart(final String title) {
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
        final String series1 = "S-ANN with matrix change";
        final String series2 = "S-ARMAX with matrix change";
        final String series3 = "S-ANN without matrix change";
        final String series4 = "S-ARMAX without matrix change";
      

        // column keys...
        /*final String category1 = "Response Time";
        final String category2 = "Throughput";
        final String category3 = "Availability";*/
        
        
        final String category1 = "";
        // create the dataset...
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        
        dataset.addValue(annf[0]/1000, series1, category1);
        dataset.addValue(armaf[0]/1000, series2, category1);
        dataset.addValue(ann[0]/1000, series3, category1);
        dataset.addValue(arma[0]/1000, series4, category1);
	
		return dataset;

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
        BarRenderer.setDefaultBarPainter(new StandardBarPainter());
        // create the chart...
        final JFreeChart chart = ChartFactory.createBarChart(
            "",         // chart title
            "",               // domain axis label
            "Training Time (s)",                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

        // set the background color for the chart...
        chart.setBackgroundPaint(Color.white);
    	LegendTitle lt = chart.getLegend();
		// lt.setItemFont(new Font("Dialog", Font.PLAIN, 9));
		//lt.setBackgroundPaint(new Color(200, 200, 255, 100));
		lt.setFrame(new BlockBorder(Color.white));
		lt.setPosition(RectangleEdge.RIGHT);
		lt.setItemFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
        // get a reference to the plot for further customisation...
        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white);
        //plot.setDomainGridlinePaint(Color.white);
        //plot.setRangeGridlinePaint(Color.white);
        ((BarRenderer) plot.getRenderer()).setBarPainter(new StandardBarPainter());

        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        //rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setTickUnit(new NumberTickUnit(1));
        CategoryAxis domain = (CategoryAxis) plot.getDomainAxis();
        rangeAxis.setLabelFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
		domain.setLabelFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
        // disable bar outlines...
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
  
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
        renderer.setShadowVisible(false);
     
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

		final PerformanceChart demo = new PerformanceChart(title);
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
		} catch (IOException e) {
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

