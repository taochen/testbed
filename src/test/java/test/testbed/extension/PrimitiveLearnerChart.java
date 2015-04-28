package test.testbed.extension;


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
import java.text.NumberFormat;
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
import org.jfree.chart.labels.AbstractCategoryItemLabelGenerator;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
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

public class PrimitiveLearnerChart extends ApplicationFrame {

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
	
	
	private static final double[] multiLearner = {
		//Response Time
		0.13513850011049,//0.1332705588295,
		0.44852892887753,//0.46273578256140585,
		0.174151074555033, //**changed**//
		//Response Time
		//Throughput
		0.13749640581783523,//0.15902328616988,
		0.15023180965501,//0.1776187193100084,
		0.22067810658429263,//0.25015761468501 ,
		//Throughput
		//Reliability
		0.003179098199383  ,
		0.00029892571180987527 ,
		0.003759358411916 ,
		//Reliability
		//Availability
		0.00614311587613 ,//0.00694311587613 ,
		 0.0005492699925621985 ,
		 0.00678119045303//0.00668119045303
		//Availability
	};
	
	private static final double[] singlemR = {
		//Response Time
		
		0.15899255515898,//0.15717347550183,
		0.514440994753,//0.4987437192934045,
		0.20213213603554,//0.19094117737647,
		//Response Time
		//Throughput
		0.16594191926552,
		0.17035149254107,//0.17035149254107132,
		0.24400271002011, //0.24959528240479
		//Throughput
		//Reliability
		0.012033992311753897,//0.00455663129242  ,
		0.000386707535092797 ,
		0.005919485690782387 ,
		//Reliability
		//Availability
		0.007781534516114,//0.007681534516114 ,
		0.0006518317752993737 ,
		0.006435407748839 
		//Availability
	
	};
	
	
	private static final double[] singlemRMR = {
		//Response Time
		0.21839711145664,//0.14375863052363 ,
		0.56469713073524,//0.5980593776040601,
		0.20011000041687,//0.19717591628919,
		//Response Time
		//Throughput
		0.30178503212418,//0.40077718063406 ,
		0.17309507903612,//0.18947344837093374,
		0.30793688422562,//0.346607396561 ,
		//Throughput
		//Reliability
		0.0044766977314984555, //0.00147661516181  ,
		0.0004653036552800337,//0.00019575147821180195,
		0.005678975971889838,//0.00467054183173, 
		//Reliability
		//Availability
		0.00649678924678, //0.00529678924678  ,
		0.00033172360469009245,
		0.01317523921295, //0.01017523921295
		//Availability
	};
	

	private static final double[] statics = {
		//Response Time
		0.30728087696668,//0.35630884147583,
		0.53455814649336,//0.5321013389173661,
		0.21087623089024, //0.20648637623987,
		//Response Time
		//Throughput
		0.18558778162663,//0.2012926715414 ,
		0.17872691463439,// 0.1739620089829071,
		0.2581345194691351,  //0.24804624740853
		//Throughput
		//Reliability
		0.0036306033440770785, //0.003008652464441 ,
		0.000589549775932099,//0.00024230645988842077,
		0.003696647569133, 
		//Reliability
		//Availability
		0.0068385415591, //0.0066385415591  ,
		0.00048303179847618375 ,
		0.00826037227755,// 0.00716037227755 
		//Availability
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
	public PrimitiveLearnerChart(final String title) {
		super(title);
		final CategoryDataset dataset = createDataset();
		chart = createChart(dataset);
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(200, 150));
		setContentPane(chartPanel);
	}

	public static CategoryDataset createDataset(int[] index) {


		
		
		   // row keys...
     final String series1 = "hybrid multi-learners";
     final String series2 = "single-mR";
     final String series3 = "single-mRMR";
     final String series4 = "fixed";
   

     // column keys...
     /*final String category1 = "Response Time";
     final String category2 = "Throughput";
     final String category3 = "Availability";*/
     
     
     final String category1 = "ANN";
     final String category2 = "ARMAX";
     final String category3 = "RT";
     
     // create the dataset...
     final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

     
     dataset.addValue(multiLearner[index[0]]*100, series1, category1);
     dataset.addValue(singlemR[index[0]]*100, series2, category1);
     dataset.addValue(singlemRMR[index[0]]*100, series3, category1);
     dataset.addValue(statics[index[0]]*100, series4, category1);
     
     dataset.addValue(multiLearner[index[1]]*100, series1, category2);
     dataset.addValue(singlemR[index[1]]*100, series2, category2);
     dataset.addValue(singlemRMR[index[1]]*100, series3, category2);
     dataset.addValue(statics[index[1]]*100, series4, category2);
     
     dataset.addValue(multiLearner[index[2]]*100, series1, category3);
     dataset.addValue(singlemR[index[2]]*100, series2, category3);
     dataset.addValue(singlemRMR[index[2]]*100, series3, category3);
     dataset.addValue(statics[index[2]]*100, series4, category3);
	
		return dataset;

	}
	
	/**
	 * Creates a sample dataset.
	 * 
	 * @return The dataset.
	 */
	private CategoryDataset createDataset() {


		
		
		   // row keys...
        final String series1 = "hybrid";
        final String series2 = "single-mR";
        final String series3 = "single-mRMR";
        final String series4 = "fixed";
      

        // column keys...
        /*final String category1 = "Response Time";
        final String category2 = "Throughput";
        final String category3 = "Availability";*/
        
        
        final String category1 = "ANN";
        final String category2 = "ARMAX";
        final String category3 = "RT";
        
        // create the dataset...
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        int i1 = 0;
        int i2 = 1;
        int i3 = 2;
        
        dataset.addValue(multiLearner[i1]*100, series1, category1);
        dataset.addValue(singlemR[i1]*100, series2, category1);
        dataset.addValue(singlemRMR[i1]*100, series3, category1);
        dataset.addValue(statics[i1]*100, series4, category1);
        
        dataset.addValue(multiLearner[i2]*100, series1, category2);
        dataset.addValue(singlemR[i2]*100, series2, category2);
        dataset.addValue(singlemRMR[i2]*100, series3, category2);
        dataset.addValue(statics[i2]*100, series4, category2);
        
        dataset.addValue(multiLearner[i3]*100, series1, category3);
        dataset.addValue(singlemR[i3]*100, series2, category3);
        dataset.addValue(singlemRMR[i3]*100, series3, category3);
        dataset.addValue(statics[i3]*100, series4, category3);
	
		return dataset;

	}
	
	private void createThroughputData (XYSeries series1, XYSeries series2){
		int i = 1;
		
	}
	
	private void createData (XYSeries series1, XYSeries series2,
			XYSeries series3, XYSeries series4,
			XYSeries series5, XYSeries series6){
	
		
	
		
	
		
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
	
	
	 public static CategoryPlot createPlot(final CategoryDataset dataset) {
	        
	      

	        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

	        // set the background color for the chart...
	        //chart.setBackgroundPaint(Color.white);
		 
		 
	        final NumberAxis rangeAxis2 = new NumberAxis("SMAPE(%)");
	        rangeAxis2.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	        final BarRenderer renderer2 = new BarRenderer();
	      
	        // get a reference to the plot for further customisation...
	        final CategoryPlot plot = new CategoryPlot(dataset, null, rangeAxis2, renderer2);
	        BarRenderer renderer = (BarRenderer) plot.getRenderer();

	        CategoryItemRenderer catRenderer = ((CategoryItemRenderer)renderer);
	        catRenderer.setBaseItemLabelGenerator(new NumberLabelGenerator("", NumberFormat.getInstance()));


			
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
	        rangeAxis.setRange(0, 1.5);//0.55, 1.2
	        CategoryAxis domain = (CategoryAxis) plot.getDomainAxis();
	        rangeAxis.setTickUnit(new NumberTickUnit(0.1));//0.1, 0.2
	    	rangeAxis.setLabelFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
			
	    	//domain.setLabelFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
	        
	        renderer.setDrawBarOutline(false);
	        renderer.setShadowVisible(false);
	        renderer.setItemLabelFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 8));
	        renderer.setItemLabelsVisible(true);
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
	        //domainAxis.setCategoryLabelPositions(
	          //  CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
	        //);
	        // OPTIONAL CUSTOMISATION COMPLETED.
	        
	        return plot;
	        
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
            PlotOrientation.HORIZONTAL, // orientation
            true,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

        // set the background color for the chart...
        chart.setBackgroundPaint(Color.white);
      
        // get a reference to the plot for further customisation...
        final CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        CategoryItemRenderer catRenderer = ((CategoryItemRenderer)renderer);
        catRenderer.setBaseItemLabelGenerator(new NumberLabelGenerator("", NumberFormat.getInstance()));


		LegendTitle lt = chart.getLegend();
		// lt.setItemFont(new Font("Dialog", Font.PLAIN, 9));
		//lt.setBackgroundPaint(new Color(200, 200, 255, 100));
		lt.setFrame(new BlockBorder(Color.white));
		lt.setPosition(RectangleEdge.BOTTOM);
		lt.setItemFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));


        plot.setBackgroundPaint(Color.white);
        //plot.setDomainGridlinePaint(Color.BLACK);
        //plot.setRangeGridlinePaint(Color.BLACK);
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
        rangeAxis.setRange(0, 70);//0.55, 1.2
        CategoryAxis domain = (CategoryAxis) plot.getDomainAxis();
        rangeAxis.setTickUnit(new NumberTickUnit(10));//0.1, 0.2
    	rangeAxis.setLabelFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
		domain.setLabelFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
        
		
		 domain.setCategoryMargin(0.13);
        renderer.setDrawBarOutline(false);
        renderer.setShadowVisible(false);
        
    //   renderer.setItemMargin(0.8);
       /* ItemLabelPosition position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE1, TextAnchor.TOP_CENTER, TextAnchor.TOP_CENTER,1.35);
        renderer.setPositiveItemLabelPosition(position);
        position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE1, TextAnchor.TOP_CENTER, TextAnchor.TOP_CENTER, 0.55); 
        renderer.setPositiveItemLabelPositionFallback(position);
        renderer.setNegativeItemLabelPositionFallback(position);
     
        renderer.setItemLabelAnchorOffset(20);*/
        renderer.setItemLabelFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
        renderer.setItemLabelsVisible(true);
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
        //domainAxis.setCategoryLabelPositions(
          //  CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        //);
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

		final PrimitiveLearnerChart demo = new PrimitiveLearnerChart(title);
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);
		
		/*try {
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
		}*/
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

	   static class NumberLabelGenerator extends AbstractCategoryItemLabelGenerator implements CategoryItemLabelGenerator {
	        public NumberLabelGenerator(String labelFormat,
	                NumberFormat formatter, NumberFormat percentFormatter) {
	            super(labelFormat, formatter, percentFormatter);
	        }

	        protected NumberLabelGenerator(String labelFormat,  NumberFormat formatter) {
	            super(labelFormat, formatter);
	        }

	        private NumberFormat formatter = NumberFormat.getInstance();

	        public String generateLabel(CategoryDataset dataset, int series, int category) {
	            Number b = dataset.getValue(series, category);
	            formatter.setMaximumFractionDigits(2);
	            return formatter.format(b);
	        }

	    }
}

