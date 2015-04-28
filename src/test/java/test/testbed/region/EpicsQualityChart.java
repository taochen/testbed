package test.testbed.region;

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

public class EpicsQualityChart extends ApplicationFrame {

	public final JFreeChart chart;
	
	// 8
	private static double[] R = {
		20.275999865880955,
		12.743165443605116,
		9.10413645725606,
		14.76310025603081,
		16.804765225430696,
		15.292229630683408,
		19.949197585542624,
		5.284477550129238,
		4.919214417713439,
		9.629944426447285,
		14.52455201704348,
		12.315676873362339,
		5.853533259000092,
		15.64255179656915,
		10.396242331317362,
		13.952723430615169,
		11.419441616858997,
		18.023222009942092,
		4.110282406540942,
		11.041822643519156
	};
	
	private static double[] C = {
		-3.9670281761101878,
		-18.440409874608605,
		-36.038763436031246,
		-4.096216115773303,
		-15.588820383781309,
		4.788247443258229,
		2.3291873221445134,
		-12.46322691829384,
		-17.643535623274634,
		-27.944927034453233,
		-11.579290510520558,
		-21.704905865300876,
		-33.103651780515186,
		-5.83213922432523,
		7.734218390970363,
		-12.045912745195391,
		-22.03616263292255,
		-6.1434886514066225,
		-41.44974181867098,
		-8.877544950397658
	};
	
	
	
	public EpicsQualityChart(String title) {
		super(title);
		final XYDataset dataset1 = createDataset();
		//final XYDataset dataset1 = createErrorDataset();
		
		chart = createChart(dataset1);
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(780, 225));
		setContentPane(chartPanel);
		
		
		//calculateAll();
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
	
	
	public static XYDataset createDataset() {


		/*
		 * Throughput
		 * Response Time
		 * Reliability
		 */
		//String domain = " Reliability";
		// create the dataset...
		XYSeries series1 = new XYSeries("self-aware");
		XYSeries series2 = new XYSeries("non-self-aware");
		//XYSeries series3 = new XYSeries("S-ARMAX Predicated"+domain);
		
		

		XYSeriesCollection xyDataset = new XYSeriesCollection();
		xyDataset.addSeries(series1);
		xyDataset.addSeries(series2);

	int i = 1;
	for (double d : R) {
		series1.add(i , d);
		i++;
	}

	i = 1;
	for (double d : C) {
		series2.add(i , d);
		i++;
	}

	
		//xyDataset.addSeries(series3);
		//createThroughputData(series1, series2);
		//createResponseTimeData(series1, series2);
		//createReliabilityData(series1, series2);
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
		//createResponsetimeErrorData(series1);
		//createAvailabilityData(series1, series2, series3);
		return xyDataset;

	}
	
	
	
	
	private JFreeChart createChart(final XYDataset dataset) {

		
		// create the chart...
		final JFreeChart chart = ChartFactory.createXYLineChart(
				"", // chart title
				"", // domain
																	// axis
																	// label
				"Global Benefits Score",//"Reliability (%)",//"Throughput (req/min)",//"Response Time (ms)",//"Average time (ms) taken for each service to complete", // range
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
		XYTitleAnnotation ta = new XYTitleAnnotation(0.35, 1.02, lt,
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
		//rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setLabelFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
		domain.setLabelFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 10));
		domain.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		//domain.setRange(0, 625);
		//domain.setTickLabelFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 15));
		//rangeAxis.setTickUnit(new NumberTickUnit(0.1));
		//rangeAxis.setAutoRangeIncludesZero(true);
		//rangeAxis.setRange(90, 100);
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
		renderer.setSeriesShape(1, ShapeUtilities.createDiamond(2));
		renderer.setSeriesShape(2, ShapeUtilities.createDiagonalCross(2, 0));
		renderer.setSeriesShape(3, ShapeUtilities.createUpTriangle(2));
		renderer.setSeriesShape(4, new Ellipse2D.Double(-3, -3, 3, 3));
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

		final EpicsQualityChart demo = new EpicsQualityChart("");
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

