package org.hogel.android.timeserieschartdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import org.hogel.android.timeserieschart.LineChartStyle;
import org.hogel.android.timeserieschart.LineChartView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewGroup chartContainer = (ViewGroup) findViewById(R.id.chart_container);

        LineChartStyle lineChartStyle = new LineChartStyle();
        lineChartStyle.setDrawPointCenter(false);
        LineChartView chartView = new LineChartView(this, generatePoints(), lineChartStyle);
        chartContainer.addView(chartView);
    }

    private List<LineChartView.Point> generatePoints() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        List<LineChartView.Point> points = new ArrayList<>();
        try {
            points.add(new LineChartView.Point(dateFormat.parse("2014/07/01"), 100));
            points.add(new LineChartView.Point(dateFormat.parse("2014/07/02"), 200));
            points.add(new LineChartView.Point(dateFormat.parse("2014/07/03"), 400));
            points.add(new LineChartView.Point(dateFormat.parse("2014/07/05"), 1100));
            points.add(new LineChartView.Point(dateFormat.parse("2014/07/06"), 700));
            points.add(new LineChartView.Point(dateFormat.parse("2014/07/08"), 1700));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return points;
    }
}
