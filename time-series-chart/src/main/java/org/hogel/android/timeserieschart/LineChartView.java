package org.hogel.android.timeserieschart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.view.View;

import java.util.Date;
import java.util.List;

public class LineChartView extends View {

    private static final long HALF_DAY = 12 * 60 * 60 * 1000;

    private static final long A_DAY = 24 * 60 * 60 * 1000;

    public static class Point {
        private Date date;
        private long value;

        public Point() {
        }

        public Point(Date date, long value) {
            this.date = new Date(date.getTime());
            this.value = value;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public long getValue() {
            return value;
        }

        public void setValue(long value) {
            this.value = value;
        }
    }

    private final List<Point> points;

    private final Paint paint = new Paint();

    private final ShapeDrawable chartDrawable;

    private final LineChartStyle lineChartStyle;

    private Long manualXGridUnit;

    private Long manualYGridUnit = null;

    public LineChartView(Context context, List<Point> points) {
        this(context, points, new LineChartStyle());
    }

    public LineChartView(Context context, List<Point> points, LineChartStyle lineChartStyle) {
        super(context);
        this.points = points;
        this.lineChartStyle = lineChartStyle;
        paint.setAntiAlias(true);

//        verticalLabelDrawable = new ShapeDrawable();
//        horizontalLabelDrawable = new ShapeDrawable();

        chartDrawable = new ShapeDrawable();
        updateDrawables();
    }

    public void updateDrawables() {
        drawLineChart(chartDrawable);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        chartDrawable.draw(canvas);
        canvas.save();
        Rect labelsArea = drawLabels(canvas);
        canvas.clipRect(labelsArea, Region.Op.REPLACE);
        canvas.restore();
    }

    private Rect drawLabels(Canvas canvas) {
//        canvasWidth = canvas.getWidth();
//        canvasHeight = canvas.getHeight();

//        drawYLabels(canvas);
        Rect labelsArea = new Rect();
//        labelsArea.set(50, 0, canvasWidth, canvasHeight - 50);
        return labelsArea;
    }

    private void drawYLabels(Canvas canvas) {
    }

    private void drawLineChart(ShapeDrawable chartDrawable) {
        Shape chartShape = new Shape() {
            @Override
            public void draw(Canvas canvas, Paint paint) {
                canvas.drawColor(lineChartStyle.getBackgroundColor());

                long minX = getMinX();
                long maxX = getMaxX();
                long xrange = maxX - minX;

                long minY = getMinY();
                long maxY = getMaxY();
                long yrange = maxY - minY;

                drawXGrid(canvas, minX, xrange);
                drawYGrid(canvas, minY, yrange);

                drawLines(canvas, minX, xrange, minY, yrange);

                if (lineChartStyle.isDrawPoint()) {
                    drawPoints(canvas, minX, xrange, minY, yrange);
                }
            }
        };
        chartDrawable.setBounds(0, 0, getWidth(), getHeight());
        chartDrawable.setShape(chartShape);
    }

    protected long getMinX() {
        return getMinDateTime() - HALF_DAY;
    }

    private long getMinDateTime() {
        return points.get(0).getDate().getTime();
    }

    protected long getMaxX() {
        return getMaxDateTime() + HALF_DAY;
    }

    private long getMaxDateTime() {
        return points.get(points.size() - 1).getDate().getTime();
    }

    protected float getXCoordinate(int canvasWidth, Point point, long minX, long xrange) {
        return getXCoordinate(canvasWidth, point.getDate().getTime(), minX, xrange);
    }

    protected float getXCoordinate(int canvasWidth, long time, long minX, long xrange) {
        return canvasWidth * (time - minX) * 1.0f / xrange;
    }

    protected long getMinY() {
        return 0;
    }

    protected long getMaxY() {
        long maxValue = getMaxValue();
        long step = getYStep(maxValue);
        return (maxValue / step + 1) * step;
    }

    private long getMaxValue() {
        long maxValue = Long.MIN_VALUE;
        for (Point point : points) {
            long value = point.getValue();
            if (value > maxValue) {
                maxValue = value;
            }
        }
        return maxValue;
    }

    private long getYStep(long maxValue) {
        int digits = String.valueOf(maxValue).length();
        return (long) Math.pow(10, digits - 1);
    }

    protected float getYCoordinate(int canvasHeight, Point point, long minY, long yrange) {
        return canvasHeight * (1.0f - (point.getValue() - minY) * 1.0f / yrange);
    }

    protected float getYCoordinate(int canvasHeight, long value, long minY, long yrange) {
        return canvasHeight * (1.0f - (value - minY) * 1.0f / yrange);
    }

    private void drawXGrid(Canvas canvas, long minX, long xrange) {
        long minDateTime = getMinDateTime();
        long maxDateTime = getMaxDateTime();
        long gridDatetime = minDateTime;
        long xGridUnit = getXGridUnit();

        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        paint.setColor(lineChartStyle.getGridColor());
        paint.setStrokeWidth(lineChartStyle.getGridWidth());

        while (gridDatetime <= maxDateTime) {
            float x = getXCoordinate(canvasWidth, gridDatetime, minX, xrange);
            canvas.drawLine(x, 0.0f, x, canvasHeight, paint);
            gridDatetime += xGridUnit;
        }
    }

    private void drawYGrid(Canvas canvas, long minY, long yrange) {
        long maxValue = getMaxValue();
        long yGridUnit = getYGridUnit(maxValue);
        long maxY = getMaxY();
        long gridValue = 0;

        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        paint.setColor(lineChartStyle.getGridColor());
        paint.setStrokeWidth(lineChartStyle.getGridWidth());
        while (gridValue < maxY) {
            float y = getYCoordinate(canvasHeight, gridValue, minY, yrange);
            canvas.drawLine(0.0f, y, canvasWidth, y, paint);
            gridValue += yGridUnit;
        }
    }

    private void drawLines(Canvas canvas, long minX, long xrange, long minY, long yrange) {
        Point prevPoint = null;
        float px = 0.0f, py = 0.0f;

        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        paint.setColor(lineChartStyle.getLineColor());
        paint.setStrokeWidth(lineChartStyle.getLineWidth());
        for (Point point : points) {
            float x = getXCoordinate(canvasWidth, point, minX, xrange);
            float y = getYCoordinate(canvasHeight, point, minY, yrange);
            if (prevPoint != null) {
                canvas.drawLine(px, py, x, y, paint);
            }
            prevPoint = point;
            px = x;
            py = y;
        }
    }

    private void drawPoints(Canvas canvas, long minX, long xrange, long minY, long yrange) {
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        for (Point point : points) {
            float x = getXCoordinate(canvasWidth, point, minX, xrange);
            float y = getYCoordinate(canvasHeight, point, minY, yrange);

            paint.setColor(lineChartStyle.getLineColor());
            canvas.drawCircle(x, y, lineChartStyle.getPointSize(), paint);

            if (lineChartStyle.isDrawPointCenter()) {
                paint.setColor(lineChartStyle.getBackgroundColor());
                canvas.drawCircle(x, y, lineChartStyle.getPointCenterSize(), paint);
            }
        }
    }

    public void setXGridUnit(long xGridUnit) {
        manualXGridUnit = xGridUnit;
        updateDrawables();
    }

    public long getXGridUnit() {
        if (manualXGridUnit != null) {
            return manualXGridUnit;
        }
        return A_DAY;
    }

    public void setYGridUnit(long yGridUnit) {
        manualYGridUnit = yGridUnit;
        updateDrawables();
    }

    public long getYGridUnit(long maxValue) {
        if (manualYGridUnit != null) {
            return manualYGridUnit;
        }
        final long yStep = getYStep(maxValue);
        return yStep >= 10 ? yStep / 2 : yStep;
    }
}
