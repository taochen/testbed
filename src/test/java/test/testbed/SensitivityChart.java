package test.testbed;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * A demo for the {@link CombinedDomainCategoryPlot} class.
 */
public class SensitivityChart extends ApplicationFrame {

	/*
	 * 
	 * 
40
Throughput.rtf: 19.956272215899197 - 0.49890680539748
Response Time.rtf: 17.078962004369124 - 0.42697405010923
Availability.rtf: 4.536339492473022 - 0.11340848731183
	 * 
	 * 19
	 * Throughput.rtf: 10.013353692321834 - 0.52701861538536
Response Time.rtf: 6.720255889938681 - 0.35369767841783
Availability.rtf: 3.3784917912905748 - 0.17781535743635



	 */
	
	private double[] cpu1 = {
			12.0,
			15.0
	};
	
	private double[] cpu2 = {
			13.0,
			20.0
	};
    /**
     * Creates a new demo instance.
     *
     * @param title  the frame title.
     */
    public SensitivityChart(final String title) {

        super(title);

        // add the chart to a panel...
        final CategoryDataset dataset = createDataset1();
		final JFreeChart chart = createBarChart(dataset);
		
        //final XYDataset dataset = createDataset2();
		//final JFreeChart chart = createLineChart(dataset);
		
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(900, 570));
		setContentPane(chartPanel);
    }

    /**
     * Creates a dataset.
     *
     * @return A dataset.
     */
    public CategoryDataset createDataset1() {

        final DefaultCategoryDataset result = new DefaultCategoryDataset();

        // row keys...
        final String series1 = "Response Time";
        final String series2 = "Throughput";
        final String series3 = "Availability";

        // column keys...
        final String type1 = "C1";
        final String type2 = "C2";

        result.addValue(0.55697405010923, series1, type1);
        result.addValue(0.49890680539748, series2, type1);
        result.addValue(0.11340848731183, series3, type1);
        
        result.addValue(0.35369767841783, series1, type2);
        result.addValue(0.52701861538536, series2, type2);
        result.addValue(0.17781535743635, series3, type2);

        return result;

    }

    /**
     * Creates a dataset.
     *
     * @return A dataset.
     */
    public XYDataset createDataset2() {

    	
    	XYSeries series1 = new XYSeries("C1");
		XYSeries series2 = new XYSeries("C2");
		
		

		XYSeriesCollection xyDataset = new XYSeriesCollection();
		xyDataset.addSeries(series1);
		xyDataset.addSeries(series2);

     
		int i = 1;
		for (double d : cpu1) {
			series1.add(i, d);
			i++;
		}
		i=1;
		for (double d : cpu2) {
			series2.add(i, d);;
			i++;
		}

        return xyDataset;
        
    }

    // ****************************************************************************
    // * JFREECHART DEVELOPER GUIDE                                               *
    // * The JFreeChart Developer Guide, written by David Gilbert, is available   *
    // * to purchase from Object Refinery Limited:                                *
    // *                                                                          *
    // * http://www.object-refinery.com/jfreechart/guide.html                     *
    // *                                                                          *
    // * Sales are used to provide funding for the JFreeChart project - please    * 
    // * support us so that we can continue developing free software.             *
    // ****************************************************************************
    
    /**
     * Creates a chart.
     *
     * @return A chart.
     */
       private JFreeChart createBarChart(final CategoryDataset dataset) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createBarChart(
            "",         // chart title
            "Response Time QoS",               // domain axis label
            "Overall symmetric uncertaincy value",                  // range axis label
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
        plot.setBackgroundPaint(Color.white);
        //plot.setDomainGridlinePaint(Color.white);
        //plot.setRangeGridlinePaint(Color.white);

        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        //rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setTickUnit(new NumberTickUnit(0.1));
        // disable bar outlines...
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        
        // set up gradient paints for series...
        final GradientPaint gp0 = new GradientPaint(
            0.0f, 0.0f, Color.blue, 
            0.0f, 0.0f, Color.blue
        );
        
        
        final GradientPaint gp1 = new GradientPaint(
            0.0f, 0.0f, Color.green, 
            0.0f, 0.0f, Color.green
        );
        final GradientPaint gp2 = new GradientPaint(
            0.0f, 0.0f, Color.red, 
            0.0f, 0.0f, Color.red
        );
        
        final GradientPaint gp3 = new GradientPaint(
                0.0f, 0.0f, Color.cyan, 
                0.0f, 0.0f, Color.cyan
            );
        
        final GradientPaint gp4 = new GradientPaint(
                0.0f, 0.0f, Color.orange, 
                0.0f, 0.0f, Color.orange
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
       
       
       private JFreeChart createLineChart(final XYDataset dataset) {

   		
   		// create the chart...
   		final JFreeChart chart = ChartFactory.createXYLineChart(
   				"", // chart title
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
   		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
   		//rangeAxis.setTickUnit(new NumberTickUnit(20));
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
   		renderer.setSeriesPaint(2, Color.orange);
   		renderer.setSeriesPaint(3, Color.magenta);
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
   		
   		renderer.setSeriesShapesVisible(0, false);
   		renderer.setSeriesShapesVisible(1, false);
   		renderer.setSeriesShapesVisible(2, false);
   		// OPTIONAL CUSTOMISATION COMPLETED.

   		return chart;
   	}
   

    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(final String[] args) {

        final String title = "";
        final SensitivityChart demo = new SensitivityChart(title);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }

}
