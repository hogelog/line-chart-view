package org.hogel.android.linechartview;

import android.content.Context;
import android.text.format.DateFormat;

import java.util.ArrayList;
import java.util.List;

public class DateLineChartView extends LineChartView {

    private static final long HALF_DAY = 12 * 60 * 60 * 1000;

    private static final long A_DAY = 24 * 60 * 60 * 1000;

    public DateLineChartView(Context context) {
        this(context, new ArrayList<Point>());
    }

    public DateLineChartView(Context context, List<Point> points) {
        this(context, points, new LineChartStyle());
    }

    public DateLineChartView(Context context, List<Point> points, LineChartStyle lineChartStyle) {
        super(context, points, lineChartStyle);
    }

    public DateLineChartView(Context context, LineChartStyle lineChartStyle) {
        super(context, new ArrayList<Point>(), lineChartStyle);
    }

    @Override
    protected String formatXLabel(long x) {
        if (lineChartStyle.getXLabelFormatter() != null) {
            return lineChartStyle.getXLabelFormatter().format(x);
        }
        return DateFormat.format("yyyy/M/d", x).toString();
    }

    @Override
    public long getMaxX() {
        if (manualMaxX != null) {
            return manualMaxX;
        }
        return getRawMaxX() + HALF_DAY;
    }

    @Override
    public long getRawMinX() {
        if (points.isEmpty()) {
            return (System.currentTimeMillis() / A_DAY - 7) * A_DAY;
        }
        return super.getRawMinX();
    }

    @Override
    protected long getRawMaxX() {
        if (points.isEmpty()) {
            return (System.currentTimeMillis() / A_DAY) * A_DAY;
        }
        return super.getRawMaxX();
    }

    @Override
    public long getMinY() {
        if (manualMinY != null) {
            return manualMinY;
        }
        return 0;
    }

    @Override
    public long getXGridUnit() {
        if (manualXGridUnit != null) {
            return manualXGridUnit;
        }
        return A_DAY;
    }

    @Override
    protected long calcMinGridValue(long min, long gridUnit) {
        return min;
    }
}
