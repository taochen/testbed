package test.testbed.extension;

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

	public class DetailedAccuracyChart extends ApplicationFrame {

		public final JFreeChart chart;
		
		public DetailedAccuracyChart(String title) {
			super(title);
			final XYDataset dataset1 = createDataset1();
			//final XYDataset dataset1 = createErrorDataset();
			
			chart = createChart(dataset1);
			final ChartPanel chartPanel = new ChartPanel(chart);
			chartPanel.setPreferredSize(new Dimension(380, 225));
			setContentPane(chartPanel);
			
			
			calculateAll();
		}

		public static void calculateAll () {
			calculateThroughput();
			calculateResponseTime();
			calculateReliability();
		}
		
		public static void calculateReliability(){
			List<Double> ampes = new ArrayList<Double>();
			double total = calculate(getReliabilityActual(), getReliabilityPredicted(), ampes);
			System.out.print("Reliability Adaptive Average " + total/ampes.size()+ "\n");
			System.out.print("Reliability Adaptive Median " + ampes.get(ampes.size()/2) + "\n");
			System.out.print("Reliability Adaptive 90th " + ampes.get((int)(ampes.size() * 0.9)) + "\n");
			
			
		}
		
		public static void calculateThroughput(){
			List<Double> ampes = new ArrayList<Double>();
			double total = calculate(getThroughputActual(), getThroughputPredicted(), ampes);
			System.out.print("Througput Adaptive Average " + total/ampes.size()+ "\n");
			System.out.print("Througput Adaptive Median " + ampes.get(ampes.size()/2) + "\n");
			System.out.print("Througput Adaptive 90th " + ampes.get((int)(ampes.size() * 0.9)) + "\n");
			
			
		}
		
	
		
		public static void calculateResponseTime(){
			List<Double> ampes = new ArrayList<Double>();
			double total = calculate(getResponseTimeActual(), getResponseTimePredicted(), ampes);
			System.out.print("ResponseTime Adaptive Average " + total/ampes.size()+ "\n");
			System.out.print("ResponseTime Adaptive Median " + ampes.get(ampes.size()/2) + "\n");
			System.out.print("ResponseTime Adaptive 90th " + ampes.get((int)(ampes.size() * 0.9)) + "\n");
			
			
		}
		
	
		
		public static double[] getThroughputActual(){
			List<Double> list = new ArrayList<Double>();
			int k = 0;
			for (double d : Data.throughputIdeal) {
				list.add(d *Data.throughputMax[k] * 60);
				k++;
			}
		
			
			double[] array = new double[list.size()];
			for (int i = 0;i<list.size();i++) {
				array[i] = list.get(i);
			}
			
			return array;
		}
		
		public static double[] getThroughputError(){
			
			List<Double> ampes = new ArrayList<Double>();
			calculateError( getThroughputActual(), getThroughputPredicted(), ampes);
			
			double[] array = new double[ampes.size()];
			for (int i = 0;i<ampes.size();i++) {
				array[i] = ampes.get(i);
			}
			
			return array;
		}
		
		public static double[] getThroughputPredicted(){
			List<Double> list = new ArrayList<Double>();
			int k = 0;
			for (double d : Data.throughput) {
				list.add(d *Data.throughputMax[k] * 60);
				k++;
			}
		
			
			double[] array = new double[list.size()];
			for (int i = 0;i<list.size();i++) {
				array[i] = list.get(i);
			}
			
			return array;
		}
		
		public static double[] getResponsetimeError(){
			
			List<Double> ampes = new ArrayList<Double>();
			calculateError(getResponseTimeActual(), getResponseTimePredicted(), ampes);
			
			double[] array = new double[ampes.size()];
			for (int i = 0;i<ampes.size();i++) {
				array[i] = ampes.get(i);
			}
			
			return array;
		}
		
		
		public static double[] getResponseTimeActual(){
			List<Double> list = new ArrayList<Double>();
			int k = 0;
			for (double d : Data.responsetimeIdeal) {
				list.add(d *Data.responsetimeMax[k] * 1000);
				k++;
			}
		
			
			double[] array = new double[list.size()];
			for (int i = 0;i<list.size();i++) {
				array[i] = list.get(i);
			}
			
			return array;
		}
		
		public static double[] getResponseTimePredicted(){
			List<Double> list = new ArrayList<Double>();
			int k = 0;
			for (double d : Data.responsetime) {
				list.add(d * Data.responsetimeMax[k] * 1000);
				k++;
			}
		
			
			double[] array = new double[list.size()];
			for (int i = 0;i<list.size();i++) {
				array[i] = list.get(i);
			}
			
			return array;
		}
	
		
		
		public static double[] getReliabilityActual(){
			List<Double> list = new ArrayList<Double>();
			int k = 0;
			for (double d : Data.reliabilityIdeal) {
				list.add(d *Data.reliabilityMax[k] * 100);
				k++;
			}
		
			
			double[] array = new double[list.size()];
			for (int i = 0;i<list.size();i++) {
				array[i] = list.get(i);
			}
			
			return array;
		}
		
		public static double[] getReliabilityPredicted(){
			List<Double> list = new ArrayList<Double>();
			int k = 0;
			for (double d : Data.reliability) {
				list.add(d * Data.reliabilityMax[k] * 100);
				k++;
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
			double RMSD = 0.0;
			double all = 0.0;
			List<Double> data = new ArrayList<Double>();
			for (int i = 0; i < ideal.length; i++) {
				yMean += ideal[i];
			}
			yMean = yMean/ideal.length;
			for (int j = 0; j < ideal.length; j++) {
				MAPE = 0.0;
				RMSD += Math.pow((ideal[j] - actual[j]), 2);
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
				  all+=MAPE;
					//System.out.print("Measured: " + ideal[j] + " Predicated: " + actual[j] + " MAPE:" + Math.abs((ideal[j] - actual[j]) / 
							  // (ideal[j]))  + "\n");
				  standard += Math.abs(ideal[j] - yMean);
				  Pd +=  Math.abs((ideal[j] - yMean) / 
						   (yMean));
				  Ry += Math.pow((ideal[j] - yMean),2);
			//0.00029892571180985446
				
				  
				  total += MAPE;
				  ampes.add(MAPE);  
				  data.add(ideal[j]);
			}
			Collections.sort(ampes);
            RMSD = Math.sqrt(RMSD/ampes.size());
			
			Collections.sort(data);
			Collections.sort(data);
			
			double ampeStandard = 0;
			for (double d : ampes) {
				ampeStandard += Math.pow(d - (alt/ampes.size() ),2);
			}
			
			
			System.out.print("NRMSD: " + (RMSD/(Math.abs(data.get(0) - data.get(data.size()-1))))+"\n");
			System.out.print("MAPE:" + alt/ampes.size()  + "\n");
			System.out.print("SMAPE:" + all/ampes.size()  + "\n");
			System.out.print("SMAPE Standard Deviation:" + Math.sqrt(ampeStandard/ampes.size())  + "\n");
			System.out.print("SMAPE Relative Standard Deviation:" + Math.sqrt(ampeStandard/ampes.size())/(all/ampes.size() )  + "\n");
			System.out.print("Reversed SMAPE Relative Standard Deviation:" + (all/ampes.size() )/Math.sqrt(ampeStandard/ampes.size())  + "\n");
			System.out.print("Standard Deviation:" + Math.sqrt(Ry/ampes.size())  + "\n");
			System.out.print("Relative Standard Deviation:" + Math.sqrt(Ry/ampes.size())/yMean  + "\n");
			System.out.print("Relative Standard Error:" + (standard/ampes.size())/yMean  + "\n");
			System.out.print("PD:" + Pd/ampes.size()  + "\n");
			System.out.print("RS:" + (1-(RSS/Ry))  + "\n");
			System.out.print("size:" + ampes.size() + "\n");
			System.out.print("Difference: "+Math.abs(ampes.get(0) - ampes.get(ampes.size()-1))+"\n");
			System.out.print("NSMAPE: " + (total/(ampes.size()*(Math.abs(ampes.get(0) - ampes.get(ampes.size()-1)))))+"\n");
			return total;
		}
		
		private static void calculateError(double[] ideal, double[] actual, List<Double> ampes){
			double MAPE = 0.0;
			
		
			double RMSD = 0.0;
			List<Double> data = new ArrayList<Double>();
			for (int j = 0; j < ideal.length; j++) {
				  MAPE = 0.0;
				  RMSD += Math.pow((ideal[j] - actual[j]), 2);
				  if ((actual[j] > 0 && ideal[j] < 0) ||
						   (actual[j] < 0 && ideal[j] > 0)) {
					   MAPE = Math.abs((ideal[j] + actual[j]) / 
							   (ideal[j] - actual[j]));
					 
				   } else {
					   MAPE = Math.abs((ideal[j] - actual[j]) / 
							   (ideal[j]+ actual[j]));
				   }
				
				  ampes.add(MAPE);  
				  data.add(ideal[j]);
			}
			
			RMSD = Math.sqrt(RMSD/ampes.size());
			
			Collections.sort(data);
			
			
			System.out.print("NRMSD: " + (RMSD/(Math.abs(data.get(0) - data.get(data.size()-1))))+"\n");
		}
		
		
		public static XYDataset createDataset1() {


			/*
			 * Throughput
			 * Response Time
			 * Reliability
			 */
			String domain = " Reliability";
			// create the dataset...
			XYSeries series1 = new XYSeries("Actual"+domain);
			XYSeries series2 = new XYSeries("Predicated"+domain);
			//XYSeries series3 = new XYSeries("S-ARMAX Predicated"+domain);
			
			

			XYSeriesCollection xyDataset = new XYSeriesCollection();
			xyDataset.addSeries(series1);
			xyDataset.addSeries(series2);
			//xyDataset.addSeries(series3);
			//createThroughputData(series1, series2);
			//createResponseTimeData(series1, series2);
			createReliabilityData(series1, series2);
			return xyDataset;

		}
		
		
		public static XYDataset createErrorDataset() {


			// create the dataset...
			XYSeries series1 = new XYSeries("SMAPE");
			//XYSeries series2 = new XYSeries("S-ARMAX SMAPE");
			
			

			XYSeriesCollection xyDataset = new XYSeriesCollection();
			xyDataset.addSeries(series1);
			//xyDataset.addSeries(series2);
			//createThroughputErrorData(series1);
			createResponsetimeErrorData(series1);
			//createAvailabilityData(series1, series2, series3);
			return xyDataset;

		}
		
		
		private static void createReliabilityData (XYSeries series1, XYSeries series2){
			int i = 100;
			double[] throughputIdeal = getReliabilityActual();
			double[] throughputActual = getReliabilityPredicted();
			for (double d : throughputIdeal) {
				series1.add(i, d);
				i++;
			}
			i=100;
			for (double d : throughputActual) {
				series2.add(i, d);;
				i++;
			}
			
		}
		
		private static void createThroughputData (XYSeries series1, XYSeries series2){
			int i = 100;
			double[] throughputIdeal = getThroughputActual();
			double[] throughputActual = getThroughputPredicted();
			for (double d : throughputIdeal) {
				series1.add(i, d);
				i++;
			}
			i=100;
			for (double d : throughputActual) {
				series2.add(i, d);;
				i++;
			}
			
		}
		
		private static void createResponseTimeData (XYSeries series1, XYSeries series2){
			int i = 100;
			double[] throughputIdeal = getResponseTimeActual();
			double[] throughputActual = getResponseTimePredicted();
		
			for (double d : throughputIdeal) {
				series1.add(i, d);
				i++;
			}
			i=100;
			for (double d : throughputActual) {
				series2.add(i, d);;
				i++;
			}
			i=100;
			
			
			
		}
		
	
		private static void createThroughputErrorData (XYSeries series1){
			int i = 100;
			double[] throughputActual = getThroughputError();
		
			for (double d : throughputActual) {
				series1.add(i, d);;
				i++;
			}
			
			
			
		}
		
		
		private static void createResponsetimeErrorData (XYSeries series1){
			int i = 1;
			double[] throughputActual = getResponsetimeError();
			int divide = 10;
			double[] data = new double[throughputActual.length/divide];
			
		 
			double result = 0;
			for (int k = 0; k < throughputActual.length;k++) {
				result += throughputActual[k];
				if ((k+1)%divide == 0) {
					data[((k+1)/divide)-1] = result/divide;
					result=0;
				}
			}
			
			for (double d : data) {
				series1.add(i, d);;
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
					"Reliability (%)",//"Reliability (%)",//"Throughput (req/min)",//"Response Time (ms)",//"Average time (ms) taken for each service to complete", // range
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
			lt.setItemFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 8));
			XYTitleAnnotation ta = new XYTitleAnnotation(0.61, 0.98, lt,
					RectangleAnchor.TOP_LEFT);//0.61, 0.98 for throughput

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
			rangeAxis.setLabelFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			domain.setLabelFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
			//domain.setRange(0, 625);
			//domain.setTickLabelFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 15));
			//rangeAxis.setTickUnit(new NumberTickUnit(0.1));
			rangeAxis.setAutoRangeIncludesZero(true);
			rangeAxis.setRange(90, 100);
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

			final DetailedAccuracyChart demo = new DetailedAccuracyChart("");
			demo.pack();
			RefineryUtilities.centerFrameOnScreen(demo);
			demo.setVisible(true);
			
			
			/*try {
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
			}*/

		}
	}
