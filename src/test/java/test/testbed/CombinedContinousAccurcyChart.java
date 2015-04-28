package test.testbed;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.spi.IIORegistry;
import javax.imageio.stream.ImageOutputStream;

import org.geotoolkit.image.io.plugin.RawTiffImageReader;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTitleAnnotation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.TextAnchor;
import org.jfree.util.ShapeUtilities;

public class CombinedContinousAccurcyChart extends ApplicationFrame {

	public final JFreeChart chart;
	
	public CombinedContinousAccurcyChart(String title) {
		super(title);
		final XYDataset dataset1 = createDataset1();
		//final XYDataset dataset1 = createErrorDataset();
		
		chart = createChart(dataset1);
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(600, 255));
		setContentPane(chartPanel);
		
		
		calculateAll();
	}

	public static void calculateAll () {
		calculateThroughput();
		calculateResponseTime();
		calculateAvailability();
	}
	
	public static void calculateThroughput(){
		List<Double> ampes = new ArrayList<Double>();
		double total = calculate(getThroughputIdeal(), getThroughputANNActual(), ampes);
		System.out.print("Througput ANN Average " + total/ampes.size()+ "\n");
		System.out.print("Througput ANN Median " + ampes.get(ampes.size()/2) + "\n");
		System.out.print("Througput ANN 90th " + ampes.get((int)(ampes.size() * 0.9)) + "\n");
		
		
		ampes = new ArrayList<Double>();
		total = calculate(getThroughputIdeal(), getThroughputARMAXActual(), ampes);
		System.out.print("Througput ARMAX Average " + total/ampes.size()+ "\n");
		System.out.print("Througput ARMAX Median " + ampes.get(ampes.size()/2) + "\n");
		System.out.print("Througput ARMAX 90th " + ampes.get((int)(ampes.size() * 0.9)) + "\n");
	}
	
	public static void calculateAvailability(){
		List<Double> ampes = new ArrayList<Double>();
		double total = calculate(getAvailabilityIdeal(), getAvailabilityANNActual(), ampes);
		System.out.print("Availability ANN Average " + total/ampes.size()+ "\n");
		System.out.print("Availability ANN Median " + ampes.get(ampes.size()/2) + "\n");
		System.out.print("Availability ANN 90th " + ampes.get((int)(ampes.size() * 0.9)) + "\n");
		
		ampes = new ArrayList<Double>();
		total = calculate(getAvailabilityIdeal(), getAvailabilityARMAXActual(), ampes);
		System.out.print("Availability ARMAX Average " + total/ampes.size()+ "\n");
		System.out.print("Availability ARMAX Median " + ampes.get(ampes.size()/2) + "\n");
		System.out.print("Availability ARMAX 90th " + ampes.get((int)(ampes.size() * 0.9)) + "\n");
	}
	
	public static void calculateResponseTime(){
		List<Double> ampes = new ArrayList<Double>();
		double total = calculate(getResponseTimeIdeal(), getResponseTimeANNActual(), ampes);
		System.out.print("ResponseTime ANN Average " + total/ampes.size()+ "\n");
		System.out.print("ResponseTime ANN Median " + ampes.get(ampes.size()/2) + "\n");
		System.out.print("ResponseTime ANN 90th " + ampes.get((int)(ampes.size() * 0.9)) + "\n");
		
		
		ampes = new ArrayList<Double>();
		total = calculate(getResponseTimeIdeal(), getResponseTimeARMAXActual(), ampes);
		System.out.print("ResponseTime ARMAX Average " + total/ampes.size()+ "\n");
		System.out.print("ResponseTime ARMAX Median " + ampes.get(ampes.size()/2) + "\n");
		System.out.print("ResponseTime ARMAX 90th " + ampes.get((int)(ampes.size() * 0.9)) + "\n");
	}
	
	public static double[] getThroughputIdeal(){
		List<Double> list = new ArrayList<Double>();
		for (double d : ContinousAccurcyChart.throughputIdeal) {
			list.add(d * 28.199060031332287);
		}
		
		for (double d : ContinousAccurcyChart_1.throughputIdeal) {
			list.add(d * 60.13333333333333);
		}
		
		double[] array = new double[list.size()];
		for (int i = 0;i<list.size();i++) {
			array[i] = list.get(i);
		}
		
		return array;
	}
	
	public static double[] getThroughputANNActual(){
		List<Double> list = new ArrayList<Double>();
		for (double d : ContinousAccurcyChart.throughputActual) {
			list.add(d * 28.199060031332287);
		}
		
		for (double d : ContinousAccurcyChart_1.throughputActual) {
			list.add(d * 60.13333333333333);
		}
		
		double[] array = new double[list.size()];
		for (int i = 0;i<list.size();i++) {
			array[i] = list.get(i);
		}
		
		return array;
	}
	
	public static double[] getThroughputANNError(){
		
		List<Double> ampes = new ArrayList<Double>();
		calculateError(getThroughputIdeal(), getThroughputANNActual(), ampes);
		
		double[] array = new double[ampes.size()];
		for (int i = 0;i<ampes.size();i++) {
			array[i] = ampes.get(i);
		}
		
		return array;
	}
	
	public static double[] getThroughputARMAXActual(){
		List<Double> list = new ArrayList<Double>();
		for (double d : ContinousAccurcyChart.armaThroughputActual) {
			list.add(d * 28.199060031332287);
		}
		
		for (double d : ContinousAccurcyChart_1.armaThroughputActual) {
			list.add(d * 60.13333333333333);
		}
		
		double[] array = new double[list.size()];
		for (int i = 0;i<list.size();i++) {
			array[i] = list.get(i);
		}
		
		return array;
	}
	
	public static double[] getThroughputARMAXError(){
		
		List<Double> ampes = new ArrayList<Double>();
		calculateError(getThroughputIdeal(), getThroughputARMAXActual(), ampes);
		
		double[] array = new double[ampes.size()];
		for (int i = 0;i<ampes.size();i++) {
			array[i] = ampes.get(i);
		}
		
		return array;
	}
	
	
	public static double[] getResponseTimeIdeal(){
		List<Double> list = new ArrayList<Double>();
		for (double d : ContinousAccurcyChart.responseIdeal) {
			list.add(d * 0.1761915641476274 * 1000);
		}
		
		for (double d : ContinousAccurcyChart_1.responseIdeal) {
			list.add(d * 0.23011924119241198 * 1000);
		}
		
		double[] array = new double[list.size()];
		for (int i = 0;i<list.size();i++) {
			array[i] = list.get(i);
		}
		
		return array;
	}
	
	public static double[] getResponseTimeANNActual(){
		List<Double> list = new ArrayList<Double>();
		for (double d : ContinousAccurcyChart.responseActual) {
			list.add(d * 0.1761915641476274 * 1000);
		}
		
		for (double d : ContinousAccurcyChart_1.responseActual) {
			list.add(d * 0.23011924119241198 * 1000);
		}
		
		double[] array = new double[list.size()];
		for (int i = 0;i<list.size();i++) {
			array[i] = list.get(i);
		}
		
		return array;
	}
	
	public static double[] getResponseTimeARMAXActual(){
		List<Double> list = new ArrayList<Double>();
		for (double d : ContinousAccurcyChart.armaResponseActual) {
			list.add(d * 0.1761915641476274 * 1000);
		}
		
		for (double d : ContinousAccurcyChart_1.armaResponseActual) {
			list.add(d * 0.23011924119241198 * 1000);
		}
		
		double[] array = new double[list.size()];
		for (int i = 0;i<list.size();i++) {
			array[i] = list.get(i);
		}
		
		return array;
	}
	
	public static double[] getAvailabilityIdeal(){
		List<Double> list = new ArrayList<Double>();
		for (double d : ContinousAccurcyChart.avIdeal) {
			list.add(d * 1);
		}
		
		for (double d : ContinousAccurcyChart_1.avIdeal) {
			list.add(d * 1);
		}
		
		double[] array = new double[list.size()];
		for (int i = 0;i<list.size();i++) {
			array[i] = list.get(i);
		}
		
		return array;
	}
	
	public static double[] getAvailabilityANNActual(){
		List<Double> list = new ArrayList<Double>();
		for (double d : ContinousAccurcyChart.avActual) {
			list.add(d * 1);
		}
		
		for (double d : ContinousAccurcyChart_1.avActual) {
			list.add(d * 1);
		}
		
		double[] array = new double[list.size()];
		for (int i = 0;i<list.size();i++) {
			array[i] = list.get(i);
		}
		
		return array;
	}
	
	public static double[] getAvailabilityARMAXActual(){
		List<Double> list = new ArrayList<Double>();
		for (double d : ContinousAccurcyChart.armaAvActual) {
			list.add(d * 1);
		}
		
		for (double d : ContinousAccurcyChart_1.armaAvActual) {
			list.add(d * 1);
		}
		
		double[] array = new double[list.size()];
		for (int i = 0;i<list.size();i++) {
			array[i] = list.get(i);
		}
		
		return array;
	}
	
	private static double calculate(double[] ideal, double[] actual, List<Double> ampes){
		double MAPE = 0.0;
		double total = 0.0;
		
		double yMean = 0.0;
		double RSS = 0.0;
		double Ry = 0.0;
		double Pd = 0.0;
        double standard = 0.0;
		double alt = 0.0;
		
		for (int i = 0; i < ideal.length; i++) {
			yMean += ideal[i];
		}
		yMean = yMean/ideal.length;
		for (int j = 0; j < ideal.length; j++) {
			MAPE = 0.0;
			RSS += Math.pow((ideal[j] 
			                       - actual[j]),2);
			  if ((actual[j] > 0 && ideal[j] < 0) ||
					   (actual[j] < 0 && ideal[j] > 0)) {
				   MAPE = Math.abs((ideal[j] + actual[j]) / 
						   (ideal[j] - actual[j]));
				   alt +=  Math.abs((ideal[j] - actual[j]) / 
						   (ideal[j]));
				 
			   } else {
				   MAPE = Math.abs((ideal[j] - actual[j]) / 
						   (ideal[j]+ actual[j]));
				   alt +=  Math.abs(( actual[j]- ideal[j]) / 
						   (ideal[j]));
			   }
				System.out.print("Measured: " + ideal[j] + " Predicated: " + actual[j] + " MAPE:" + Math.abs((ideal[j] - actual[j]) / 
						   (ideal[j]))  + "\n");
			  standard += Math.abs(ideal[j] - yMean);
			  Pd +=  Math.abs((ideal[j] - yMean) / 
					   (yMean));
			  Ry += Math.pow((ideal[j] - yMean),2);
		
			
			  
			  total += MAPE;
			  ampes.add(MAPE);  
		}
		Collections.sort(ampes);
		System.out.print("MAPE:" + alt/ampes.size()  + "\n");
		System.out.print("Standard Deviation:" + Math.sqrt(Ry/ampes.size())  + "\n");
		System.out.print("Relative Standard Deviation:" + Math.sqrt(Ry/ampes.size())/yMean  + "\n");
		System.out.print("Relative Standard Error:" + (standard/ampes.size())/yMean  + "\n");
		System.out.print("PD:" + Pd/ampes.size()  + "\n");
		System.out.print("RS:" + (1-(RSS/Ry))  + "\n");
		System.out.print("size:" + ampes.size() + "\n");
		return total;
	}
	
	private static void calculateError(double[] ideal, double[] actual, List<Double> ampes){
		double MAPE = 0.0;
		
	
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
			
			  ampes.add(MAPE);  
		}
	}
	
	
	public static XYDataset createDataset1() {


		/*
		 * Throughput
		 * Response Time
		 * Availability
		 */
		String domain = " Response Time";
		// create the dataset...
		XYSeries series1 = new XYSeries("Measured"+domain);
		XYSeries series2 = new XYSeries("S-ANN Predicated"+domain);
		XYSeries series3 = new XYSeries("S-ARMAX Predicated"+domain);
		
		

		XYSeriesCollection xyDataset = new XYSeriesCollection();
		xyDataset.addSeries(series1);
		xyDataset.addSeries(series2);
		xyDataset.addSeries(series3);
		//createThroughputData(series1, series2,series3);
		createResponseTimeData(series1, series2, series3);
		//createAvailabilityData(series1, series2, series3);
		return xyDataset;

	}
	
	
	public static XYDataset createErrorDataset() {


		// create the dataset...
		XYSeries series1 = new XYSeries("S-ANN SMAPE");
		XYSeries series2 = new XYSeries("S-ARMAX SMAPE");
		
		

		XYSeriesCollection xyDataset = new XYSeriesCollection();
		xyDataset.addSeries(series1);
		xyDataset.addSeries(series2);
		//createThroughputErrorData(series1, series2);
		//createResponseTimeData(series1, series2, series3);
		//createAvailabilityData(series1, series2, series3);
		return xyDataset;

	}
	
	private static void createThroughputData (XYSeries series1, XYSeries series2,XYSeries series3){
		int i = 1;
		double[] throughputIdeal = getThroughputIdeal();
		double[] throughputActual = getThroughputANNActual();
		double[] armaThroughputActual = getThroughputARMAXActual();
		for (double d : throughputIdeal) {
			series1.add(i, d);
			i++;
		}
		i=1;
		for (double d : throughputActual) {
			series2.add(i, d);;
			i++;
		}
		i=1;
		for (double d : armaThroughputActual) {
			series3.add(i, d);
			i++;
		}
		
		
	}
	
	private static void createResponseTimeData (XYSeries series1, XYSeries series2,XYSeries series3){
		int i = 1;
		double[] throughputIdeal = getResponseTimeIdeal();
		double[] throughputActual = getResponseTimeANNActual();
		double[] armaThroughputActual = getResponseTimeARMAXActual();
		for (double d : throughputIdeal) {
			series1.add(i, d);
			i++;
		}
		i=1;
		for (double d : throughputActual) {
			series2.add(i, d);;
			i++;
		}
		i=1;
		for (double d : armaThroughputActual) {
			series3.add(i, d);
			i++;
		}
		
		
	}
	
	
	private static void createAvailabilityData (XYSeries series1, XYSeries series2,XYSeries series3){
		int i = 1;
		double[] throughputIdeal = getAvailabilityIdeal();
		double[] throughputActual = getAvailabilityANNActual();
		double[] armaThroughputActual = getAvailabilityARMAXActual();
		for (double d : throughputIdeal) {
			series1.add(i, d*100);
			i++;
		}
		i=1;
		for (double d : throughputActual) {
			series2.add(i, d*100);;
			i++;
		}
		i=1;
		for (double d : armaThroughputActual) {
			series3.add(i, d*100);
			i++;
		}
		
		
	}
	private static void createThroughputErrorData (XYSeries series1, XYSeries series2){
		int i = 1;
		double[] throughputActual = getThroughputANNError();
		double[] armaThroughputActual = getThroughputARMAXError();
	
		for (double d : throughputActual) {
			series1.add(i, d);;
			i++;
		}
		i=1;
		for (double d : armaThroughputActual) {
			series2.add(i, d);
			i++;
		}
		
		
	}
	
	private JFreeChart createChart(final XYDataset dataset) {

		
		// create the chart...
		final JFreeChart chart = ChartFactory.createXYLineChart(
				"", // chart title
				"", // domain
																	// axis
																	// label
				"Response Time (ms)",//"Availability (%)",//"Throughput (req/sec)",//"Response Time (ms)",//"Average time (ms) taken for each service to complete", // range
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

		//chart.setBackgroundPaint(Color.white);
		
		
		//chart.getLegend().setPosition(RectangleEdge.LEFT);
 
		chart.getLegend().setVisible(false);
		final XYPlot plot = (XYPlot) chart.getPlot();

		LegendTitle lt = new LegendTitle(plot);
		// lt.setItemFont(new Font("Dialog", Font.PLAIN, 9));
		//lt.setBackgroundPaint(new Color(200, 200, 255, 100));
		lt.setFrame(new BlockBorder(Color.white));
		lt.setPosition(RectangleEdge.LEFT);
		lt.setItemFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
		XYTitleAnnotation ta = new XYTitleAnnotation(0.61, 0.88, lt,
				RectangleAnchor.TOP_LEFT);

		ta.setMaxWidth(0.48);
		plot.addAnnotation(ta);
		
		plot.setBackgroundPaint(Color.white);
		plot.setRangeGridlinePaint(Color.black);
		
		XYLineAndShapeRenderer localLineAndShapeRenderer = (XYLineAndShapeRenderer)plot.getRenderer();
	    localLineAndShapeRenderer.setBaseShapesVisible(true);
	    //localLineAndShapeRenderer.setBaseItemLabelsVisible(false);
	    localLineAndShapeRenderer.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
		// customise the range axis...
	    ValueAxis domain = (ValueAxis) plot.getDomainAxis();
	    
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setLabelFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
		domain.setLabelFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 12));
		//domain.setRange(0, 625);
		//domain.setTickLabelFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 15));
		//rangeAxis.setTickUnit(new NumberTickUnit(10));
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
		
		  ItemLabelPosition position = new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.BASELINE_LEFT, TextAnchor.CENTER, 0.0); 
		//renderer.setSeriesOutlineStroke(series, stroke, notify)
		  
		
		
		renderer.setSeriesPaint(0, Color.red);
		renderer.setSeriesPaint(1, Color.blue);
		//renderer.setSeriesPaint(2, Color.orange);
		renderer.setSeriesPaint(2, Color.green);
		renderer.setSeriesPaint(3, Color.magenta);
		renderer.setSeriesPaint(4, Color.orange);
		/*renderer.setSeriesStroke(
			    0, 
			    new BasicStroke(
			        2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
			        10.5f, new float[] {6.0f, 6.0f}, 0.0f
			    ));
		renderer.setSeriesStroke(
			    1, 
			    new BasicStroke(
			        2.5f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND,
			        2.5f, new float[] {6.0f, 6.0f}, 0.0f
			    ));
		renderer.setSeriesStroke(
			    2, 
			    new BasicStroke(
			        2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
			       7.5f, new float[] {6.0f, 6.0f}, 0.0f
			    ));*/
		renderer.setSeriesShape(0, new Rectangle(-2,-2,2,2));
		renderer.setSeriesShape(1, ShapeUtilities.createDiamond(2));
		renderer.setSeriesShape(2, ShapeUtilities.createDiagonalCross(2, 0));
		//renderer.setSeriesShape(2, ShapeUtilities.createUpTriangle(2));
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
				new float[] { 1.0f, 1.0f }, 10.0f));*/
		//renderer.setSeriesShape(1, new Ellipse2D.Double(-3, -3, 6, 6));
	
		renderer.setSeriesShapesFilled(0, true);
		renderer.setSeriesShapesFilled(1, true);
		renderer.setSeriesShapesFilled(2, true);
		
		renderer.setSeriesShapesVisible(0, true);
		renderer.setSeriesShapesVisible(1, true);
		renderer.setSeriesShapesVisible(2, true);
		// OPTIONAL CUSTOMISATION COMPLETED.

		return chart;
	}
	
	public static void main(final String[] args) {

		final CombinedContinousAccurcyChart demo = new CombinedContinousAccurcyChart("");
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);
		
		
		try {
			Thread.sleep((long)2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		IIORegistry registry = IIORegistry.getDefaultInstance();   
		registry.registerServiceProvider(new RawTiffImageReader.Spi());   
	
		
		File file = new File("/Users/tao/research/chart.tif");
		try {
			//ChartUtilities.saveChartAsPNG(file, demo.chart, 900, 165);
			//ChartUtilities.saveChartAsJPEG(file, 1, demo.chart, 900, 160);
			BufferedImage chartImage = demo.chart.createBufferedImage( 1000, 455, null); 
			ImageIO.write( chartImage, "tif", file ); 
			System.out.print(ImageIO.getImageWritersByFormatName("tif").hasNext()+"***8\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
