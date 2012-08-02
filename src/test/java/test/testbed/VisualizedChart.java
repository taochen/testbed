package test.testbed;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class VisualizedChart extends ApplicationFrame {

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

	/**
	 * Creates a new demo.
	 * 
	 * @param title
	 *            the frame title.
	 */
	public VisualizedChart(final String title) {
		super(title);
		final CategoryDataset dataset = createDataset();
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
	private CategoryDataset createDataset() {

		int i = 0;

		// create the dataset...
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		if (i==0) {

		dataset.addValue(9.40, "S1", "1");
		dataset.addValue(0, "S2", "1");
		dataset.addValue(12.0, "SLA of S1", "1");
		dataset.addValue(40.0, "SLA of S2", "1");
		dataset.addValue(9.40, "web-service", "1");
		
		dataset.addValue(14.22, "S1", "2");
		dataset.addValue(47.85, "S2", "2");
		dataset.addValue(12.0, "SLA of S1", "2");
		dataset.addValue(40.0, "SLA of S2", "2");
		dataset.addValue(24.31, "web-service", "2");
		
		dataset.addValue(13.12, "S1", "3");
		dataset.addValue(46.70, "S2", "3");
		dataset.addValue(12.0, "SLA of S1", "3");
		dataset.addValue(40.0, "SLA of S2", "3");
		dataset.addValue(23.19, "web-service", "3");
		
		dataset.addValue(12.58, "S1", "4");
		dataset.addValue(41.42, "S2", "4");
		dataset.addValue(12.0, "SLA of S1", "4");
		dataset.addValue(40.0, "SLA of S2", "4");
		dataset.addValue(21.23, "web-service", "4");
		
		
		dataset.addValue(9.13, "S1", "5");
		dataset.addValue(37.42, "S2", "5");
		dataset.addValue(12.0, "SLA of S1", "5");
		dataset.addValue(40.0, "SLA of S2", "5");
		dataset.addValue(17.61, "web-service", "5");
		
		dataset.addValue(5.91, "S1", "6");
		dataset.addValue(23.13, "S2", "6");
		dataset.addValue(12.0, "SLA of S1", "6");
		dataset.addValue(40.0, "SLA of S2", "6");
		dataset.addValue(11.08, "web-service", "6");
		} else if (i==1) {
		dataset.addValue(1, "cpu", "1");
		dataset.addValue(1, "cpu", "2");
		dataset.addValue(1, "cpu", "3");
		dataset.addValue(2, "cpu", "4");
		dataset.addValue(2, "cpu", "5");
		dataset.addValue(4, "cpu", "6");
		}else if (i==2) {
			dataset.addValue(128, "memory", "1");
			dataset.addValue(128, "memory", "2");
			dataset.addValue(512, "memory", "3");
			dataset.addValue(512, "memory", "4");
			dataset.addValue(2048, "memory", "5");
			dataset.addValue(2048, "memory", "6");
		}
		
		return dataset;

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
		final JFreeChart chart = ChartFactory.createLineChart(
				title, // chart title
				"Epoch", // domain
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
	    Shape[] arrayOfShape = new Shape[4];
	    int[] arrayOfInt1 = { -3, 3, -3 };
	    int[] arrayOfInt2 = { -3, 0, 3 };
	    arrayOfShape[0] = new Polygon(arrayOfInt1, arrayOfInt2, 3);
	    arrayOfShape[1] = new Rectangle2D.Double(-2.0D, -3.0D, 3.0D, 8.0D);
	    arrayOfInt1 = new int[] { -3, 3, 3 };
	    arrayOfInt2 = new int[] { 0, -3, 3 };
	    arrayOfShape[2] = new Polygon(arrayOfInt1, arrayOfInt2, 3);
	    arrayOfShape[3] = new Rectangle2D.Double(-6.0D, -6.0D, 6.0D, 6.0D);
	    DefaultDrawingSupplier localDefaultDrawingSupplier = new DefaultDrawingSupplier(DefaultDrawingSupplier.DEFAULT_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_PAINT_SEQUENCE, DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE, arrayOfShape);
	  


		// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
		// final StandardLegend legend = (StandardLegend) chart.getLegend();
		// legend.setDisplaySeriesShapes(true);
		// legend.setShapeScaleX(1.5);
		// legend.setShapeScaleY(1.5);
		// legend.setDisplaySeriesLines(true);

		chart.setBackgroundPaint(Color.white);

		final CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.white);
		plot.setRangeGridlinePaint(Color.black);
	    LineAndShapeRenderer localLineAndShapeRenderer = (LineAndShapeRenderer)plot.getRenderer();
	    localLineAndShapeRenderer.setBaseShapesVisible(true);
	    //localLineAndShapeRenderer.setBaseItemLabelsVisible(true);
	    localLineAndShapeRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		// customise the range axis...
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
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
		final LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot
				.getRenderer();
		// renderer.setDrawShapes(true);
		renderer.setSeriesPaint(0, Color.red);
		renderer.setSeriesPaint(1, Color.blue);
		//renderer.setSeriesPaint(2, Color.orange);
		renderer.setSeriesPaint(2, Color.magenta);
		renderer.setSeriesPaint(3, Color.green);
		renderer.setSeriesPaint(4, Color.orange);
		renderer.setSeriesStroke(0, new BasicStroke(2.0f,
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

		final VisualizedChart demo = new VisualizedChart(title);
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);

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
