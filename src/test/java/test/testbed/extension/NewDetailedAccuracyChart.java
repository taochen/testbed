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

	public class NewDetailedAccuracyChart extends ApplicationFrame {

		public final JFreeChart chart;
		
		public NewDetailedAccuracyChart(String title) {
			super(title);
			final XYDataset dataset1 = createDataset1();
			//final XYDataset dataset1 = createErrorDataset();
			
			chart = createChart(dataset1);
			final ChartPanel chartPanel = new ChartPanel(chart);
			chartPanel.setPreferredSize(new Dimension(400, 60));
			setContentPane(chartPanel);
			
			
		}

	
	
		
	
		
		public static double[] getThroughputActual(){
			List<Double> list = new ArrayList<Double>();
			int k = 0;
			
			
			
			for (double d : NewData.throughput[0]) {
				list.add(d*60); //req/min
				k++;
			}
		
			
			double[] array = new double[list.size()];
			for (int i = 0;i<list.size();i++) {
				array[i] = list.get(i);
			}
			
			return array;
		}
		
		
		
		public static double[] getThroughputPredicted(){
			List<Double> list = new ArrayList<Double>();
			
			
			
			int k = 0;
			for (double d : NewData.throughput[1]) {
				list.add(d *60);
				k++;
			}
		
			
			double[] array = new double[list.size()];
			for (int i = 0;i<list.size();i++) {
				array[i] = list.get(i);
			}
			
			return array;
		}
		
		
		
		
		public static double[] getResponseTimeActual(){
			List<Double> list = new ArrayList<Double>();
			
		
			int k = 0;
			for (double d : NewData.responsetime[0]) {
				list.add(d*1000);// ms
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
			for (double d : NewData.responsetime[1]) {
				list.add(d*1000);
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
			for (double d : NewData.reliability[0]) {
				list.add(d *100 );
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
			for (double d : NewData.reliability[1]) {
				list.add(d*100  );
				k++;
			}
		
			
			double[] array = new double[list.size()];
			for (int i = 0;i<list.size();i++) {
				array[i] = list.get(i);
			}
			
			return array;
		}
		
		public static double[] getAvailabilityActual(){
			List<Double> list = new ArrayList<Double>();
			int k = 0;
			for (double d : NewData.availability[0]) {
				list.add(d *100  );
				k++;
			}
		
			
			double[] array = new double[list.size()];
			for (int i = 0;i<list.size();i++) {
				array[i] = list.get(i);
			}
			
			return array;
		}
		
		public static double[] getAvailabilityPredicted(){
			List<Double> list = new ArrayList<Double>();
			int k = 0;
			for (double d : NewData.availability[1]) {
				list.add(d *100 );
				k++;
			}
		
			
			double[] array = new double[list.size()];
			for (int i = 0;i<list.size();i++) {
				array[i] = list.get(i);
			}
			
			return array;
		}
	
	
		
		
		public static XYDataset createDataset1() {


			/*
			 * Throughput
			 * Response Time
			 * Reliability
			 */
			String domain = " Reliability";
			// create the dataset...
			XYSeries series1 = new XYSeries("Actual");
			XYSeries series2 = new XYSeries("Predicated");
			XYSeries series3 = new XYSeries("Actual");
			XYSeries series4 = new XYSeries("Predicated");
			XYSeries series5 = new XYSeries("Actual");
			XYSeries series6 = new XYSeries("Predicated");
			XYSeries series7 = new XYSeries("Actual");
			XYSeries series8 = new XYSeries("Predicated");
			//XYSeries series3 = new XYSeries("S-ARMAX Predicated"+domain);
			
			

			XYSeriesCollection xyDataset = new XYSeriesCollection();
			xyDataset.addSeries(series1);
			xyDataset.addSeries(series2);
			//xyDataset.addSeries(series3);
			//xyDataset.addSeries(series4);
			/*xyDataset.addSeries(series5);
			xyDataset.addSeries(series6);
			/*xyDataset.addSeries(series7);
			xyDataset.addSeries(series8);*/
		
			createAvailabilityData(series1, series2);
			createReliabilityData(series3, series4);
			createResponseTimeData(series5, series6);
			createThroughputData(series7, series8);
			
			return xyDataset;

		}
		
	
		
		private static void createAvailabilityData (XYSeries series1, XYSeries series2){
			int i = 150;
			double[] throughputIdeal = getAvailabilityActual();
			double[] throughputActual = getAvailabilityPredicted();
			for (double d : throughputIdeal) {
				series1.add(i, d);
				i++;
			}
			i=150;
			for (double d : throughputActual) {
				series2.add(i, d);;
				i++;
			}
			
		}
		
		
		private static void createReliabilityData (XYSeries series1, XYSeries series2){
			int i = 150;
			double[] throughputIdeal = getReliabilityActual();
			double[] throughputActual = getReliabilityPredicted();
			for (double d : throughputIdeal) {
				series1.add(i, d);
				i++;
			}
			i=150;
			for (double d : throughputActual) {
				series2.add(i, d);
				i++;
			}
			
		}
		
		private static void createThroughputData (XYSeries series1, XYSeries series2){
			int i = 150;
			double[] throughputIdeal = getThroughputActual();
			double[] throughputActual = getThroughputPredicted();
			for (double d : throughputIdeal) {
				series1.add(i, d);
				i++;
			}
			i=150;
			for (double d : throughputActual) {
				series2.add(i, d);;
				i++;
			}
			
		}
		
		private static void createResponseTimeData (XYSeries series1, XYSeries series2){
			int i = 150;
			double[] throughputIdeal = getResponseTimeActual();
			double[] throughputActual = getResponseTimePredicted();
		
			for (double d : throughputIdeal) {
				
				if (d>100) {
					d = d*0.1;
				}
				
				series1.add(i, d);
				i++;
			}
			i=150;
			for (double d : throughputActual) {
				if (d>100) {
					d = d*0.1;
				}
				
				series2.add(i, d);;
				i++;
			}
			i=100;
			
			
			
		}
	
		
		
		
		private JFreeChart createChart(final XYDataset dataset) {

			
			// create the chart...
			final JFreeChart chart = ChartFactory.createXYLineChart(
					"", // chart title
					"", // domain
																		// axis
																		// label
					"%",//"Reliability (%)",//"Throughput (req/min)",//"Response Time (ms)",//"Average time (ms) taken for each service to complete", // range
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
			lt.setItemFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 15));
			XYTitleAnnotation ta = new XYTitleAnnotation(0.61, 0.58, lt,
					RectangleAnchor.TOP_LEFT);//0.61, 0.98 for throughput

			ta.setMaxWidth(0.48);
			plot.addAnnotation(ta);
			
			plot.setBackgroundPaint(Color.white);
			plot.setRangeGridlinePaint(Color.white);
			
			XYLineAndShapeRenderer localLineAndShapeRenderer = (XYLineAndShapeRenderer)plot.getRenderer();
		    localLineAndShapeRenderer.setBaseShapesVisible(true);
		    //localLineAndShapeRenderer.setBaseItemLabelsVisible(false);
		    localLineAndShapeRenderer.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
			// customise the range axis...
		    ValueAxis domain = (ValueAxis) plot.getDomainAxis();
		    
			final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
			rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			rangeAxis.setLabelFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 15));
			domain.setLabelFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 15));
			//domain.setRange(0, 625);
			//domain.setTickLabelFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 15));
			//rangeAxis.setTickUnit(new NumberTickUnit(1));
			rangeAxis.setAutoRangeIncludesZero(true);
			rangeAxis.setRange(0, 120);
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
			renderer.setSeriesShape(0, new Rectangle(-3,-3,3,3));
			renderer.setSeriesShape(1, ShapeUtilities.createDiamond(3));
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
		
			renderer.setSeriesShapesFilled(0, false);
			renderer.setSeriesShapesFilled(1, false);
			renderer.setSeriesShapesFilled(2, false);
			
			renderer.setSeriesShapesVisible(0, true);
			renderer.setSeriesShapesVisible(1, true);
			renderer.setSeriesShapesVisible(2, true);
			// OPTIONAL CUSTOMISATION COMPLETED.

			return chart;
		}
		
		public static void main(final String[] args) {

			final NewDetailedAccuracyChart demo = new NewDetailedAccuracyChart("");
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

