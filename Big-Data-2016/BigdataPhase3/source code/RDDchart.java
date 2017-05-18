import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.XYBarDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.VerticalAlignment;

import java.awt.*;
import java.io.*;
import java.util.DoubleSummaryStatistics;
import java.util.StringTokenizer;

import static java.lang.Integer.*;

/**
 * Created by hadoop on 12/3/16.
 */
public class chart {

    public static void main(String[] args) throws Exception {
        Chart1(); // function for 4th query
        Chart2(); // function for 5th query

    }

    public static void Chart1() throws IOException {
        InputStream in = new FileInputStream(new File("outFile1/part-00000"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int count=0;


        //////////////////////read result file and create dataset ////////////////////////////////
        while ((line = reader.readLine()) != null && count <=20) {
            String datavalue[] = line.split("/");
            //System.out.println(datavalue[0] + ", " + datavalue[1]);
            String category = datavalue[0];
            String value = datavalue[1];
            //System.out.println(category + ", " + value);
            dataset.setValue(Double.parseDouble(value), "Number of tweet", category);
            count++;
        }

        ////////////////////////create XYAreaChart ////////////////////////////////////////////////
        JFreeChart chart = ChartFactory.createAreaChart("top 20 places tweet about #AMA", "place", "number of tweet", dataset, PlotOrientation.VERTICAL, true, true, false);
        chart.setBackgroundPaint(Color.white);
        final TextTitle subtitle = new TextTitle("SELECT user.location, COUNT(user.location) from tweet where array_contains (entities.hashtags.text,'AMAs')=true GROUP BY user.location ORDER BY COUNT(user.location) DESC");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitle.setPosition(RectangleEdge.TOP);
        subtitle.setVerticalAlignment(VerticalAlignment.BOTTOM);
        chart.addSubtitle(subtitle);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setForegroundAlpha(0.5f);

        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.white);

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);

        int width = 700; /* Width of the image */
        int height = 600; /* Height of the image */

        //////////////save as a file//////////////////////////////
        File xyAreaChart = new File("XYAreaChart.jpeg");
        ChartUtilities.saveChartAsJPEG(xyAreaChart, chart, width, height);


    }

    public static void Chart2() throws IOException {
        InputStream in = new FileInputStream(new File("outFile2/part-00000"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        DefaultPieDataset dataset = new DefaultPieDataset();
        int count=0;

        //////////////////////read result file and create dataset ////////////////////////////////
        while ((line = reader.readLine()) != null && count <10) {
            String datavalue[] = line.split(",");
            //System.out.println(datavalue[0] + ", " + datavalue[1]);
            String category = datavalue[0];
            String value = datavalue[1];
            //System.out.println(category + ", " + value);
            dataset.setValue(category, Double.parseDouble(value));
            count++;
        }

        ////////////////////////create PieChart ////////////////////////////////////////////////
        JFreeChart chart = ChartFactory.createPieChart("top 10 pictures which contains #AMA", dataset,false,true,false);
        chart.setBackgroundPaint(Color.white);
        final TextTitle subtitle = new TextTitle("select entities.media.media_url, count (entities.media.media_url)" +
                " where text like %DNCE% GROUP BY entities.media.media_url ORDER BY COUNT(entities.media.media_url) DESC");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitle.setPosition(RectangleEdge.TOP);
        subtitle.setVerticalAlignment(VerticalAlignment.BOTTOM);
        chart.addSubtitle(subtitle);

        PiePlot plot=(PiePlot)chart.getPlot();
        plot.setLabelGenerator(null);

        int width = 1000; /* Width of the image */
        int height = 600; /* Height of the image */

        /////////////////////save as a file////////////////////////////////////////////////////
        File pieChart = new File("PieChart2.jpeg");
        ChartUtilities.saveChartAsJPEG(pieChart, chart, width, height);
    }
}
