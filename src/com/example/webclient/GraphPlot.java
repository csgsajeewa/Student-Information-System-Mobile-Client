package com.example.webclient;



import android.os.Bundle;
import android.app.Activity;
import android.view.WindowManager;
import com.androidplot.xy.*;
import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * A straightforward example of using AndroidPlot to plot some data.
 */
public class GraphPlot extends Activity
{
 
    private XYPlot plot;
 
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
 
        // fun little snippet that prevents users from taking screenshots
        // on ICS+ devices :-)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                                 WindowManager.LayoutParams.FLAG_SECURE);
 
        setContentView(R.layout.xy_plot);
      
        // initialize our XYPlot reference:
        plot = (XYPlot) findViewById(R.id.mySimpleXYPlot);
        plot.setDomainLabel("Years");
        plot.setRangeLabel("marks");
     // get rid of the decimal place on the display:
       plot.setDomainValueFormat(new DecimalFormat("#"));
       
       
      plot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1);
       plot.setRangeStep(XYStepMode.INCREMENT_BY_VAL, 0.1);
      plot.setDomainLeftMax(2004);
      plot.setDomainRightMin(2011);
      plot.setRangeTopMin(4.2);
        // Create a couple arrays of y-values to plot:
        Number[] series1Numbers = {3.5467, 2.3456, 3.9809, 3.4500, 3.6778, 3.5434};
        Number[] series2Numbers = {2005, 2006, 2007, 2008, 2009, 2010};
 
        // Turn the above arrays into XYSeries':
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(series2Numbers),          // SimpleXYSeries takes a List so turn our array into a List
                Arrays.asList(series1Numbers), // Y_VALS_ONLY means use the element index as the x value
                "GPA");                             // Set the display title of the series
 
        // same as above
        XYSeries series2 = new SimpleXYSeries(Arrays.asList(series2Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series2");
 
        // Create a formatter to use for drawing a series using LineAndPointRenderer
        // and configure it from xml:
        LineAndPointFormatter series1Format = new LineAndPointFormatter();
        series1Format.setPointLabelFormatter(new PointLabelFormatter());
        series1Format.configure(getApplicationContext(),
                R.layout.line_point_formatter_with_plf1);
 
        // add a new series' to the xyplot:
        plot.addSeries(series1, series1Format);
 
        // same as above:
        LineAndPointFormatter series2Format = new LineAndPointFormatter();
        series2Format.setPointLabelFormatter(new PointLabelFormatter());
        series2Format.configure(getApplicationContext(),
                R.layout.line_point_formatter_with_plf2);
       // plot.addSeries(series2, series2Format);
 
        // reduce the number of range labels
        plot.setTicksPerRangeLabel(3);
        plot.getGraphWidget().setDomainLabelOrientation(-45);
 
    }
}
