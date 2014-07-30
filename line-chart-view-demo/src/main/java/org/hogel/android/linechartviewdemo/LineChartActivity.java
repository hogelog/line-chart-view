package org.hogel.android.linechartviewdemo;

import android.app.Activity;
import android.os.Bundle;
import org.hogel.android.linechartview.LineChartView;

import java.util.ArrayList;
import java.util.List;

public class LineChartActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chart);

        LineChartView chartView  = (LineChartView) findViewById(R.id.chart_view);
        chartView.setPoints(generatePoints());
    }

    private List<LineChartView.Point> generatePoints() {
        List<LineChartView.Point> points = new ArrayList<>();
        points.add(new LineChartView.Point(-17, -100));
        points.add(new LineChartView.Point(4, 200));
        points.add(new LineChartView.Point(5, 400));
        points.add(new LineChartView.Point(6, 1100));
        points.add(new LineChartView.Point(7, 700));
        return points;
    }
}
