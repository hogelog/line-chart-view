package org.hogel.android.linechartview;

import org.junit.Test;
import org.robolectric.annotation.Config;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

@Config(emulateSdk = 18)
public class LineChartViewTest extends ViewTestBase {
    private List<LineChartView.Point> points;

    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void createSuccess() {
        LineChartView lineChartView = new LineChartView(activity);

        lineChartView.setManualMinX(0);
        lineChartView.setManualMaxX(1000);
        assertThat(lineChartView.getXGridUnit()).isGreaterThan(0);

        lineChartView.setManualMinY(0);
        lineChartView.setManualMaxY(1000);
        assertThat(lineChartView.getXGridUnit()).isGreaterThan(0);
    }

    @Test
    public void createFailure() {
        try {
            new LineChartView(null);
            assertThat(true).isFalse();
        } catch (Exception e) {
        }
    }
}
