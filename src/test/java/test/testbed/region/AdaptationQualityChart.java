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

	public class AdaptationQualityChart extends ApplicationFrame {

		public final JFreeChart chart;
		
		// 8
		private static double[] R = {
			0.13936761278233764,
			1.1192434992575975,
			0.9433021728140272,
			1.6520507364952652,
			2.167038459454471,
			2.179136033583859,
			0.8985099509621193,
			1.5290967824616075,
			0.5575825756373265,
			1.7075398669186113,
			1.1618742561617081,
			0.02546579725210998,
			0.7008879582903969,
			-0.666574581191454,
			0.4451199209785186,
			3.786766842782469,
			1.116613430041972,
			0.9169492933817662,
			1.8904968717024304,
			1.203820587939943,
			1.7646243817297544,
			1.3688774726499322,
			3.8721366734334337,
			1.1826955174651526,
			0.6456356040239097,
			2.3815476682772556,
			0.9595336505453567,
			0.0071491250271765155,
			1.7263483642222661,
			-0.04495346538183513,
			1.064941620446071
		};
		
		private static double[] C = {
			-0.14543791072523538,
			0.9812823586595862,
			0.7692581672766998,
			1.5314625622186764,
			1.933675831132618,
			2.031033159533979,
			0.7202808696724716,
			1.4405301629106584,
			0.3677707939087509,
			1.5983039140063358,
			1.0516665862445205,
			-0.13206582677184525,
			0.5000031832913576,
			-0.8334961916391412,
			0.15830333824663634,
			3.619668140271355,
			0.858414777401848,
			0.7617735489070292,
			1.7110000732002213,
			0.9124528573359365,
			1.5199975776832035,
			1.3001554284993257,
			3.586670350908671,
			0.881991951832519,
			0.34355743069319744,
			2.1483280605995474,
			0.8026060290514248,
			-0.08004486375244635,
			1.507220464018587,
			-0.2826339222387241,
			0.8679694397289481
		};
		
		private static double[] FD = {
			-1.5176797321531728,
			0.5973434607547071,
			0.6551694370674129,
			1.4884119346079945,
			2.0887868046336706,
			2.112352155656304,
			0.47208532520017543,
			1.0312339880963706,
			0.08398583218519914,
			1.4684023305541423,
			1.0015048329043137,
			-0.18784458660019251,
			0.30245752029814554,
			-0.6515391360420374,
			-0.2678816568546579,
			2.771976585908404,
			0.6686495359631616,
			0.31366370179481345,
			1.2836590920993773,
			1.0506773043526154,
			0.5175775048578791,
			0.36719332162744656,
			3.872136673433434,
			0.5140960975323166,
			-0.34264582532709426,
			2.342269284620991,
			0.11071647200139147,
			-0.6565898968089077,
			1.715925949961588,
			-1.0446719810173781,
			0.10641212419430698
		};
		
		
		private static double[] SD = {
			0.17207461169763813,
			1.1507029149612047,
			0.9643552282973731,
			1.6536132315139525,
			2.1700184987857636,
			2.1897015651643863,
			0.9274942496101607,
			1.5309567033099902,
			0.5443408711350073,
			1.7061164916571279,
			1.1794546231908114,
			0.034129583532956566,
			0.7202479027826382,
			-0.6564249923903622,
			0.4626283638071653,
			3.8126255086871668,
			1.112296690460518,
			0.9123509527660673,
			1.9048236273318306,
			1.1988542992201545,
			1.757773705679487,
			1.3749741165076528,
			3.892527125042345,
			1.2217783724810098,
			0.6769019619485946,
			2.4165709264308464,
			0.9535049987767371,
			0.011346970797075112,
			1.7536294400582702,
			-0.04387813769289084,
			1.0531332671249198
		};
		
		
		private static double[] FD1 = {
			-1.5801406268391616,
			0.2589699869450257,
			0.3423306792650749,
			1.4626619179067561,
			1.657284761044374,
			1.7267331601225264,
			-0.03584982929229985,
			0.7854258788147865,
			-0.3261140559697304,
			0.5260204631785186,
			0.4177885937116782,
			-0.729740445207589,
			-0.6182090839292541,
			-1.6473306954209426,
			-0.5182637075775364,
			2.0108222166994154,
			0.010566650611139372,
			-0.3929590580795496,
			1.0842635845659083,
			0.4707193849657402,
			0.10835217035894651,
			-0.519851704722795,
			3.128110184848011,
			0.02269108966491895,
			-0.4967376297815654,
			1.904983759558223,
			-0.8519232243231649,
			-1.2614157663667585,
			1.5250610351283889,
			-1.6222104955200942,
			-0.06903292258884944
		};
		
		
		/*// 6
		
			private static double[] R = {
			0.19165009889271242,
0.9383771418025801,
0.0838540479036916,
0.4084630295239963,
0.38009772386370444,
1.9581932641904864,
-0.2007197824803113,
0.10861891577457772,
-0.4150314261791654,
0.8299421351852326,
0.15632228319482627,
-0.2887809047061005,
-0.34616480910716746,
0.7394990570234705,
0.2621817747558093,
-0.8159939357735707,
1.147937477939214
		};
		
		private static double[] C = {
			0.1061492192030737,
0.8534677758557063,
-0.07990492186167446,
0.2839971170441087,
0.29138008819959504,
1.762063383985328,
-0.2810214489363198,
0.01394759718112007,
-0.4691057562497162,
0.6545919700920879,
0.06106491415652607,
-0.44905438774468226,
-0.5095199113785114,
0.6177496312610984,
0.1402732690577383,
-0.998864708367265,
1.006871177685871
		};
		
		private static double[] FD = {
			-0.149633994485204,
0.227521558599713,
-0.22065316803169216,
-0.2563883538719077,
-0.4518495511429913,
1.2599332511819463,
-0.18239847277346058,
-0.29035147296158564,
-0.8000586168258552,
0.7140841931027853,
-0.3972581889282534,
-0.7096220259231409,
-1.0439895048129595,
0.20246918597905242,
0.22040584099879948,
-1.3610350215469953,
0.3865262291992807
		};
		
		
		private static double[] SD = {
			0.20616456433782582,
0.9406912559236629,
0.09923818230590233,
0.42157747012785696,
0.3860122329079784,
1.9986679688336573,
-0.1958084347130146,
0.12696638075927572,
-0.4157718185068891,
0.8236071247942937,
0.19148234347067214,
-0.3119328390078185,
-0.36075919869932016,
0.7452222223465413,
0.24289090885182293,
-0.8014648839690661,
1.1582852758241915
		};
		
		private static double[] FD1 = {
			-0.49087055494593346,
-0.3465567985727057,
-0.9924095084122624,
-0.8448442958453433,
-0.7536629669996366,
0.5499832462721024,
-0.7904415397229321,
-0.8175263000524815,
-1.614072826503102,
0.020140382338307172,
-1.038018248164493,
-0.8794797826894197,
-1.6163087127075288,
0.02034416199679659,
-0.35293259760293083,
-2.0117343521687636,
-0.1305889382436769
		};
		 
		 
		 */
		
		
		
		/*// 4
		
			private static double[] R = {
			-0.4916227601438026,
0.6536167749142338,
0.4568892654933907,
-0.05511915129974887,
-0.059944317507094724,
0.6757442379135696,
0.03502802804065985,
-0.6333971900780612,
0.2868946913203106,
0.8693089719808205,
-0.13871121402146197
		};
		
		private static double[] C = {
			-0.4734202819162073,
0.6560645134685134,
0.45564991486379747,
-0.060128270151028396,
-0.055258989295993155,
0.6849773252945606,
0.03502802804065985,
-0.681693076475101,
0.347060695523084,
0.8574959094741808,
-0.1178577546199995
		};
		
		private static double[] FD = {
			-1.117116070114196,
0.038085991776498435,
0.6679471501779903,
-0.8974461881743978,
-0.9684216190143575,
0.6787494714054682,
-0.21778245118243156,
-0.9238890891105063,
-0.8225544864468989,
0.43264923316339976,
-0.7734129483841399
		};
		
		
		private static double[] SD = {
			-0.4821503954624532,
0.6660715235973084,
0.4624793675477711,
-0.05993215940963492,
-0.05369721322562615,
0.6857055834064207,
0.03444597160096907,
-0.6535390457601034,
0.2861953273128121,
0.8490631449077541,
-0.14722845031057188
		};
		
				private static double[] FD1 = {
			-1.3352617362819967,
-0.1430461191545773,
0.3478658494028489,
-1.4018679201901265,
-1.849384603896466,
0.43077770185333075,
-0.6165683736093447,
-1.286421186895696,
-1.1497898989635635,
-0.15028681362178287,
-1.0128471991222199
		};
		 
		 */
		 
		
		
		
		
		/* 4
		private static double[] R = { -80.95307776382292,
			-82.1147536561549,
			50.109370551840684,
			-89.30203938789765,
			174.28690247611635,
			-171.67887204282889,
			-97.31119700166448,
			-109.80271807233913,
			165.69601500444628,
			-133.67797233986732,
			-72.03932687510076,
			273.3270255222726,
			-31.32739564483434,
			-66.34598228175037,
			-107.22843152981575,
			29.177156621311582,
			-10.018689142990937};
		private static double[] C = {-80.49366352428139,
			-82.1147536561549,
			49.74115308388089,
			-90.19738096096665,
			174.28690247611635,
			-169.10166268493788,
			-97.31119700166448,
			-109.80271807233913,
			165.69601500444628,
			-134.78482701687867,
			-70.45688429198441,
			273.2457953470718,
			-31.32739564483434,
			-65.88029830852315,
			-107.67458486786813,
			31.16386512595823,
			-10.018689142990937}; 
		private static double[] FD = {-620.3030995742505,
			-1194.3153148316533,
			106.67465550961411,
			-349.5856866271495,
			-386.86009367734437,
			-866.7979329312841,
			-306.8449910038788,
			-389.72634362919894,
			17.930352657063338,
			-1302.004612716587,
			-775.7896255098092,
			473.03479421662047,
			-113.35110043061711,
			-312.90828299485094,
			-1650.63952950693,
			52.99619260520496,
			-672.1472633630242};
		
		
		// 6
		/*private static double[] R = {-83.30597682379106,
	  -78.42985128188725,
-169.55865860571672,
522.3344871825967,
-30.27503417249997,
51.90974540847331,
60.91526444441797,
-67.3562449789732,
-81.35571665812203,
412.83394031458585,
124.1352998348643,
-118.85439060170735,
17.34572816963262,
129.18463501235834,
110.1932836540008,
-38.59905724078198,
-22.50358614857028,
35.326115336778415,
25.93319233488106,
34.155105357348475,
59.64424689142782,
193.61596976856427,
461.4551628446293,
-71.03109631011945,
51.58821130963566,
-70.17957452981993,
97.2574910841199,
73.10375878021759,
180.89308875188127,
-81.0798984915517,
-11.94767868931085,
129.58245817159371,
66.64565093681334,
-85.68484436536423,
384.62959952264185,
54.2434194617911};
		private static double[] C = {-92.0065481657536,
	  -97.96341320095586,
-177.17081478672836,
521.8162552979621,
-38.18150668074708,
48.01961994106706,
46.603829925256576,
-82.86198024720302,
-85.5262554462769,
406.77149350715194,
116.33358286181958,
-130.06376555491758,
6.1304528544074515,
125.93631911954031,
104.52063848522765,
-43.38962891212417,
-27.655695363526835,
29.42795739467048,
13.195723994904455,
27.07276256150709,
54.91316617451494,
189.1867532668348,
454.81012965864574,
-73.48283271602362,
50.14504860513627,
-77.9743112788588,
87.73118046023885,
63.65497676648416,
174.1183598902738,
-85.28958120925613,
-18.112168302199734,
122.57183802629119,
53.961804672888825,
-94.43450589980023,
379.8165239638209,
48.898345775275786}; 
		private static double[] FD = {-127.18755133197793,
	  -75.17388786664704,
-170.2653884569598,
261.8575842453924,
-410.6812268634134,
-124.62227836188968,
22.34518307176839,
-65.37725724415229,
-89.31584658940257,
413.923040445064,
77.00983347750332,
-168.27659577998068,
10.800782162160358,
60.24695124268459,
99.2138147873485,
-110.71652905410293,
-40.337904374459455,
-9.54337689435876,
-102.37090033171656,
-23.262433759911175,
-85.33592197090128,
161.17603839497377,
322.9221941431587,
-188.0038008310431,
43.13574639717374,
-347.83936545666455,
-86.71137102584835,
-118.62111815329277,
23.64194933322552,
-96.96583964385135,
-179.49400447929787,
28.483991724776654,
55.34339128346651,
-219.88037136861573,
232.66626991675963,
-11.266807956227073,
125.4646793402479,
-39.465151542675585,
-13.370196893693816,
192.7676985794567,
-378.50905092447005,
432.14953157285015,
359.90697524295626,
191.0455750893579,
6.089089262056518,
346.5992021044668,
109.81649499118842};*/
		
		
		/*// 8
		private static double[] R = {179.10870029161907,
	   -69.65382100684536,
	   699.2990013538125,
	   -207.4047191265804,
	   147.91159015116077,
	   207.0185524924359, 
	   -108.96985422899452,
	   517.5882209029604,
	   150.80897527170418,
	   489.49967175647754,
	   191.77511285262756,
	   6.608325527051997,
	   111.16966411682994,
	   512.3336728211001,
	   -125.67820716181359,
	   207.53045217337183,
	   -206.74915052782893,
	   -53.82893436222501,
	   135.01113637466855,
-23.235271405630602,
-1.1941046619371312,
208.06655746244695,
-350.61226136993747,
455.66522596120393,
376.56216294206877,
204.78167628186722,
23.25442597186136,
363.0528164424315,
143.68860225374223};
		private static double[] C = {153.3522336196725,
	   -81.07508593394098,
	   668.2406456355304,
	   -221.10756085389104,
	   138.67586958312478,
	   175.08521715586744,
	   -133.41109696118747,
	   496.60905616653696,
	   107.8517403020923,
	   480.023963405653,
	   178.45250766444525,
	   -11.941318179806402,
	   91.40870020200043,
	   500.63969982541727,
	   -151.95076017046807,
	   178.76217775526658,
	   -216.93914088502018,
	    -79.26250799906083}; 
		private static double[] FD = {47.25601251406897,
	   -158.5047706477936,
	   640.327410602568,
	   -216.19040023874788,
	   45.0965231805163,
	   41.72295934856714,
	   -277.95697725536564,
	   355.710195875535,
	   152.25880721215847,
	   443.2027409982897,
	   125.34736275417863,
	   -54.31348952585901,
	   88.30890874937839,
	   359.4508619757233,
	   -289.27987682554493,
	   77.4791163529007,
	   -225.0674685517235,
	   -142.01078160017568,
	   27.086823923106536,
-46.407863764421656,
-186.71528396171678,
118.33077268296435,
-367.6778244441019,
402.6935699398206,
268.7672350969639,
80.37723357315144,
-135.86068850744908,
301.72742836908094,
138.74707868991308};*/
		
		public AdaptationQualityChart(String title) {
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
			XYSeries series1 = new XYSeries("sensitivity-aware");
			XYSeries series2 = new XYSeries("cloud-level");
			XYSeries series3 = new XYSeries("VM-level");
			XYSeries series4 = new XYSeries("PM-level");
			XYSeries series5 = new XYSeries("service-level");
			//XYSeries series3 = new XYSeries("S-ARMAX Predicated"+domain);
			
			

			XYSeriesCollection xyDataset = new XYSeriesCollection();
			xyDataset.addSeries(series1);
			xyDataset.addSeries(series2);
			xyDataset.addSeries(series3);
			xyDataset.addSeries(series4);
			xyDataset.addSeries(series5);

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

		i = 1;
		for (double d : FD) {
			series3.add(i , d);
			i++;
		}
		
		
		i = 1;
		for (double d : SD) {
			series4.add(i , d);
			i++;
		}
		
		
		i = 1;
		for (double d : FD1) {
			series5.add(i , d);
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

			final AdaptationQualityChart demo = new AdaptationQualityChart("");
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
