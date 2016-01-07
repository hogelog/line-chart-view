package org.hogel.android.linechartview;

import org.junit.Test;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@Config(constants = BuildConfig.class)
public class LineChartViewTest extends ViewTestBase {
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
        assertThat(lineChartView.getYGridUnit()).isGreaterThan(0);
    }

    @Test
    public void createFailure() {
        try {
            new LineChartView(null);
            fail("Invalid context value null");
        } catch (Exception e) {
        }
    }
}
