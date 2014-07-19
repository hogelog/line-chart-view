package org.hogel.android.timeserieschart;

import android.content.Context;
import android.text.format.DateFormat;

import java.util.List;

public class DateLineChartView extends LineChartView {

    private static final long HALF_DAY = 12 * 60 * 60 * 1000;

    private static final long A_DAY = 24 * 60 * 60 * 1000;

    public DateLineChartView(Context context, List<Point> points) {
        this(context, points, new LineChartStyle());
    }

    public DateLineChartView(Context context, List<Point> points, LineChartStyle lineChartStyle) {
        super(context, points, lineChartStyle);
    }

    @Override
    protected String formatXLabel(long x) {
        if (lineChartStyle.getXLabelFormatter() != null) {
            return lineChartStyle.getXLabelFormatter().format(x);
        }
        return DateFormat.format("yyyy/M/d", x).toString();
    }

    protected long getMinX() {
        return getRawMinX() - HALF_DAY;
    }

    protected long getMaxX() {
        return getRawMaxX() + HALF_DAY;
    }

    protected long getMinY() {
        return 0;
    }

    public long getXGridUnit() {
        if (manualXGridUnit != null) {
            return manualXGridUnit;
        }
        return A_DAY;
    }
}
