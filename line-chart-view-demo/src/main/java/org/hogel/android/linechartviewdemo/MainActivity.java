package org.hogel.android.linechartviewdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View lineChartButton = findViewById(R.id.line_chart_button);
        lineChartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LineChartActivity.class));
            }
        });
        View dateLineChartButton = findViewById(R.id.date_line_chart_button);
        dateLineChartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DateLineChartActivity.class));
            }
        });
    }
}
