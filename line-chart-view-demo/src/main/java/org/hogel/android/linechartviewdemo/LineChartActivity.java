package org.hogel.android.linechartviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import org.hogel.android.linechartview.LineChartView;

import java.util.ArrayList;
import java.util.List;

public class LineChartActivity extends Activity {
    private static final double MAX_Y = 1000;
    private LineChartView chartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chart);

        chartView  = (LineChartView) findViewById(R.id.chart_view);

        View nextButton = findViewById(R.id.next_data);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextChartData();
            }
        });
    }

    private void nextChartData() {
        List<LineChartView.Point> points = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int y = (int) (Math.random() * MAX_Y);
            points.add(new LineChartView.Point(i, y));
        }
        chartView.setPoints(points);
    }
}
