package org.hogel.android.timeserieschart;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
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
    private final ShapeDrawable shapeDrawable;
    private final ShapeDrawable chartDrawable;
    private Shape chartShape;

    private int lineColor = Color.RED;
    private float lineWidth = 8.0f;

    private int pointColor = Color.RED;
    private float pointSize = 10.0f;
    private float pointCenterSize = 5.0f;

    private int gridColor = Color.GRAY;
    private float gridWidth = 2.0f;

    public LineChartView(Context context, List<Point> points) {
        super(context);
        this.points = points;
        paint.setAntiAlias(true);
        shapeDrawable = new ShapeDrawable();
        chartDrawable = new ShapeDrawable();
        updateDrawables();
    }

    private void updateDrawables() {
        shapeDrawable.setShape(new OvalShape());
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
        chartShape = new Shape() {
            @Override
            public void draw(Canvas canvas, Paint paint) {
                canvas.drawColor(Color.WHITE);

                long minX = getMinX();
                long maxX = getMaxX();
                long xrange = maxX - minX;

                long minY = getMinY();
                long maxY = getMaxY();
                long yrange = maxY - minY;

                drawXGrid(canvas, minX, xrange);
                drawYGrid(canvas, minY, yrange);

                drawLines(canvas, minX, xrange, minY, yrange);
                drawPoints(canvas, minX, xrange, minY, yrange);
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

        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        paint.setColor(gridColor);
        paint.setStrokeWidth(gridWidth);
        while (gridDatetime <= maxDateTime) {
            float x = getXCoordinate(canvasWidth, gridDatetime, minX, xrange);
            canvas.drawLine(x, 0.0f, x, canvasHeight, paint);
            gridDatetime += A_DAY;
        }
    }

    private void drawYGrid(Canvas canvas, long minY, long yrange) {
        long maxValue = getMaxValue();
        long ystep = getYStep(maxValue);
        long maxY = getMaxY();
        long gridValue = 0;
        long gridStep = ystep >= 10 ? ystep / 2 : ystep;

        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        paint.setColor(gridColor);
        paint.setStrokeWidth(gridWidth);
        while (gridValue < maxY) {
            float y = getYCoordinate(canvasHeight, gridValue, minY, yrange);
            canvas.drawLine(0.0f, y, canvasWidth, y, paint);
            gridValue += gridStep;
        }
    }

    private void drawLines(Canvas canvas, long minX, long xrange, long minY, long yrange) {
        Point prevPoint = null;
        float px = 0.0f, py = 0.0f;

        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        paint.setColor(lineColor);
        paint.setStrokeWidth(lineWidth);
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
            paint.setColor(pointColor);
            canvas.drawCircle(x, y, pointSize, paint);
            paint.setColor(Color.WHITE);
            canvas.drawCircle(x, y, pointCenterSize, paint);
        }
    }
}
