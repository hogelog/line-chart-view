package org.hogel.android.timeserieschart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

    private int lineColor = Color.RED;
    private float lineWidth = 8.0f;

    private int pointColor = Color.RED;
    private float pointSize = 10.0f;
    private float pointCenterSize = 5.0f;

    private int gridColor = Color.GRAY;
    private float gridWidth = 2.0f;

    private int canvasWidth;
    private int canvasHeight;

    public LineChartView(Context context, List<Point> points) {
        super(context);
        this.points = points;
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        long minX = getMinX();
        long maxX = getMaxX();
        long xrange = maxX - minX;

        long minY = getMinY();
        long maxY = getMaxY();
        long yrange = maxY - minY;

        drawVerticalGrid(canvas, minX, xrange);
        drawHorizontalGrid(canvas, minY, yrange);

        drawLines(canvas, minX, xrange, minY, yrange);
        drawPoints(canvas, minX, xrange, minY, yrange);
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

    protected float getXCoordinate(Point point, long minX, long xrange) {
        return getXCoordinate(point.getDate().getTime(), minX, xrange);
    }

    protected float getXCoordinate(long time, long minX, long xrange) {
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

    protected float getYCoordinate(Point point, long minY, long yrange) {
        return canvasHeight * (1.0f - (point.getValue() - minY) * 1.0f / yrange);
    }

    protected float getYCoordinate(long value, long minY, long yrange) {
        return canvasHeight * (1.0f - (value - minY) * 1.0f / yrange);
    }

    private void drawVerticalGrid(Canvas canvas, long minX, long xrange) {
        long minDateTime = getMinDateTime();
        long maxDateTime = getMaxDateTime();
        long gridDatetime = minDateTime;

        paint.setColor(gridColor);
        paint.setStrokeWidth(gridWidth);
        while (gridDatetime <= maxDateTime) {
            float x = getXCoordinate(gridDatetime, minX, xrange);
            canvas.drawLine(x, 0.0f, x, canvasHeight, paint);
            gridDatetime += A_DAY;
        }
    }

    private void drawHorizontalGrid(Canvas canvas, long minY, long yrange) {
        long maxValue = getMaxValue();
        long ystep = getYStep(maxValue);
        long maxY = getMaxY();
        long gridValue = 0;
        long gridStep = ystep >= 10 ? ystep / 2 : ystep;

        paint.setColor(gridColor);
        paint.setStrokeWidth(gridWidth);
        while (gridValue < maxY) {
            float y = getYCoordinate(gridValue, minY, yrange);
            canvas.drawLine(0.0f, y, canvasWidth, y, paint);
            gridValue += gridStep;
        }
    }

    private void drawLines(Canvas canvas, long minX, long xrange, long minY, long yrange) {
        Point prevPoint = null;
        float px = 0.0f, py = 0.0f;

        paint.setColor(lineColor);
        paint.setStrokeWidth(lineWidth);
        for (Point point : points) {
            float x = getXCoordinate(point, minX, xrange);
            float y = getYCoordinate(point, minY, yrange);
            if (prevPoint != null) {
                canvas.drawLine(px, py, x, y, paint);
            }
            prevPoint = point;
            px = x;
            py = y;
        }
    }

    private void drawPoints(Canvas canvas, long minX, long xrange, long minY, long yrange) {
        for (Point point : points) {
            float x = getXCoordinate(point, minX, xrange);
            float y = getYCoordinate(point, minY, yrange);
            paint.setColor(pointColor);
            canvas.drawCircle(x, y, pointSize, paint);
            paint.setColor(Color.WHITE);
            canvas.drawCircle(x, y, pointCenterSize, paint);
        }
    }
}
