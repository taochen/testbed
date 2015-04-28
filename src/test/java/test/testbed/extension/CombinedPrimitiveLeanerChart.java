package test.testbed.extension;
import java.awt.Font;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
public class CombinedPrimitiveLeanerChart extends ApplicationFrame{

	private static final double[] multiLearner = {
		//Response Time
		0.1332705588295,
		0.46273578256140585,
		0.174151074555033
		//Response Time
		//Throughput
		/*0.15902328616988,
		0.1776187193100084,
		0.25015761468501 
		//Throughput*/
		//Reliability
		/*0.003179098199383  ,
		0.00029892571180987527 ,
		0.003759358411916 */
		//Reliability
		//Availability
		/* 0.00694311587613 ,
		 0.0005492699925621985 ,
		 0.00668119045303*/
		//Availability
	};
	
	private static final double[] singlemR = {
		//Response Time
		
		0.15717347550183,
		0.4987437192934045,
		0.19094117737647
		//Response Time
		//Throughput
		/*0.16594191926552,
		0.17035149254107132,
		0.25959528240479 //0.24959528240479
		//Throughput
		//Reliability
		/*0.00455663129242  ,
		0.000386707535092797 ,
		0.0046799804412 */
		//Reliability
		//Availability
		/*0.007681534516114 ,
		0.0006518317752993737 ,
		0.006435407748839 */
		//Availability
	
	};
	
	
	private static final double[] singlemRMR = {
		//Response Time
		0.14375863052363 ,
		0.5980593776040601,
		0.19717591628919
		//Response Time
		//Throughput
		/*0.40077718063406 ,
		0.18947344837093374,
		0.346607396561 */
		//Throughput
		//Reliability
		/*0.00147661516181  ,
		0.00019575147821180195,
		0.00467054183173 */
		//Reliability
		//Availability
		/*0.00529678924678  ,
		0.00033172360469009245,
		0.01017523921295*/ 
		//Availability
	};
	

	private static final double[] statics = {
		//Response Time
		0.35630884147583,
		0.5321013389173661,
		0.20648637623987
		//Response Time
		//Throughput
		/*0.2012926715414 ,
		0.1739620089829071,
		0.25804624740853 //0.24804624740853
		//Throughput
		//Reliability
		/*0.003008652464441 ,
		0.00024230645988842077,
		0.003696647569133 */
		//Reliability
		//Availability
		/*0.0066385415591  ,
		0.00048303179847618375 ,
		0.00716037227755 */
		//Availability
	};
	
	
	/**
     * Creates a new demo instance.
     *
     * @param title  the frame title.
     */
    public CombinedPrimitiveLeanerChart(final String title) {

        super(title);

        // add the chart to a panel...
        final ChartPanel chartPanel = new ChartPanel(createChart());
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);

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
    private JFreeChart createChart() {
    	
        final CategoryPlot subplot1 = PrimitiveLearnerChart.createPlot(PrimitiveLearnerChart.createDataset(new int[]{0,1,2}) );
        subplot1.setDomainGridlinesVisible(true);
        
        final CategoryPlot subplot2 = PrimitiveLearnerChart.createPlot(PrimitiveLearnerChart.createDataset(new int[]{3,4,5}) );
        subplot2.setDomainGridlinesVisible(true);

        subplot1.setOrientation(PlotOrientation.VERTICAL);
        
        final CategoryAxis domainAxis = new CategoryAxis("Category");
        final CombinedDomainCategoryPlot plot = new CombinedDomainCategoryPlot(domainAxis);
        plot.add(subplot1, 2);
        plot.add(subplot2, 1);
      
        plot.setOrientation(PlotOrientation.HORIZONTAL);
        final JFreeChart result = new JFreeChart(
            "Combined Domain Category Plot Demo",
            new Font("SansSerif", Font.BOLD, 12),
            plot,
            true
        );
  //      result.getLegend().setAnchor(Legend.SOUTH);
        return result;

    }

    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(final String[] args) {

        final String title = "Combined Category Plot Demo 1";
        final CombinedPrimitiveLeanerChart demo = new CombinedPrimitiveLeanerChart(title);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }

	
}
