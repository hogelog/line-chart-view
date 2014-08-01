package org.hogel.android.linechartview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class LineChartView extends View {

    private static final long DEFAULT_MAX_X = 1000;

    private static final long DEFAULT_MAX_Y = 1000;

    public static class Point {
        private long x;

        private long y;

        public Point() {
        }

        public Point(long x, long y) {
            this.x = x;
            this.y = y;
        }

        public long getX() {
            return x;
        }

        public void setX(long x) {
            this.x = x;
        }

        public long getY() {
            return y;
        }

        public void setY(long y) {
            this.y = y;
        }
    }

    protected final List<Point> points;

    protected final Paint paint = new Paint();

    protected final Paint labelPaint = new Paint();

    protected final Paint framePaint = new Paint();

    protected final ShapeDrawable chartDrawable;

    protected final ShapeDrawable yLabelDrawable;

    protected final ShapeDrawable xLabelDrawable;

    protected LineChartStyle lineChartStyle;

    protected Long manualXGridUnit = null;

    protected Long manualYGridUnit = null;

    protected long yLabelWidth = 0;

    protected long xLabelHeight = 0;

    protected long chartTopMargin = 0;

    protected long chartRightMargin = 0;

    protected List<Long> manualXLabels = null;

    protected List<Long> manualYLabels = null;

    protected Long manualMinX = null;

    protected Long manualMaxX = null;

    protected Long manualMinY = null;

    protected Long manualMaxY = null;

    public LineChartView(Context context) {
        this(context, new ArrayList<Point>());
    }

    public LineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);

        points = new ArrayList<>();
        lineChartStyle = new LineChartStyle();

        paint.setAntiAlias(true);

        yLabelDrawable = new ShapeDrawable();
        xLabelDrawable = new ShapeDrawable();

        chartDrawable = new ShapeDrawable();

        updateIfEditMode();
        updateDrawables();
    }

    public LineChartView(Context context, List<Point> points) {
        this(context, points, new LineChartStyle());
    }

    public LineChartView(Context context, LineChartStyle lineChartStyle) {
        this(context, new ArrayList<Point>(), lineChartStyle);
    }

    public LineChartView(Context context, List<Point> points, LineChartStyle lineChartStyle) {
        super(context);
        this.points = points;
        this.lineChartStyle = lineChartStyle;
        paint.setAntiAlias(true);

        yLabelDrawable = new ShapeDrawable();
        xLabelDrawable = new ShapeDrawable();

        chartDrawable = new ShapeDrawable();
        updateDrawables();
    }

    public void updateDrawables() {
        drawXLabels(xLabelDrawable);
        drawYLabels(yLabelDrawable);
        drawLineChart(chartDrawable);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        yLabelDrawable.draw(canvas);
        xLabelDrawable.draw(canvas);
        chartDrawable.draw(canvas);
    }

    protected void drawYLabels(ShapeDrawable labelDrawable) {
        Shape labelsShape = new Shape() {
            @Override
            public void draw(Canvas canvas, Paint paint) {
                labelPaint.setTextAlign(Paint.Align.RIGHT);
                labelPaint.setTextSize(lineChartStyle.getLabelTextSize());
                labelPaint.setColor(lineChartStyle.getLabelTextColor());

                long minY = getMinY();
                long maxY = getMaxY();
                long yrange = maxY - minY;

                float height = getHeight();

                float left = getYLabelWidth();
                List<Long> yLabels = getYLabels();
                for (long y : yLabels) {
                    String label = formatYLabel(y);
                    float yCoordinate = getYCoordinate(height, y, minY, yrange);
                    canvas.drawText(label, left, yCoordinate, labelPaint);
                }
            }
        };
        measureYLabel();
        labelDrawable.setBounds(0, 0, getWidth(), getHeight());
        labelDrawable.setShape(labelsShape);
    }

    protected void measureYLabel() {
        labelPaint.setTextAlign(Paint.Align.RIGHT);
        labelPaint.setTextSize(lineChartStyle.getLabelTextSize());

        long minY = getMinY();
        long maxY = getMaxY();
        long yGridUnit = getYGridUnit();

        yLabelWidth = 0;
        chartTopMargin = 0;

        long y = minY;
        Rect textBounds = new Rect();

        while (y <= maxY) {
            String label = formatYLabel(y);
            labelPaint.getTextBounds(label, 0, label.length(), textBounds);
            if (textBounds.width() > yLabelWidth) {
                yLabelWidth = textBounds.width();
            }
            chartTopMargin = textBounds.height();
            y += yGridUnit;
        }
    }

    protected String formatYLabel(long y) {
        if (lineChartStyle.getYLabelFormatter() != null) {
            return lineChartStyle.getYLabelFormatter().format(y);
        }
        return String.format("%,d", y);
    }

    protected void drawXLabels(ShapeDrawable labelDrawable) {
        Shape labelsShape = new Shape() {
            @Override
            public void draw(Canvas canvas, Paint paint) {
                labelPaint.setTextAlign(Paint.Align.CENTER);
                labelPaint.setTextSize(lineChartStyle.getLabelTextSize());
                labelPaint.setColor(lineChartStyle.getLabelTextColor());

                long minX = getMinX();
                long maxX = getMaxX();
                long xrange = maxX - minX;

                float width = getWidth();
                float height = getHeight();

                Rect textBounds = new Rect();
                List<Long> xLabels = getXLabels();
                for (long x : xLabels) {
                    String label = formatXLabel(x);
                    labelPaint.getTextBounds(label, 0, label.length(), textBounds);
                    float xCoordinate = getXCoordinate(width, x, minX, xrange);
                    float labelHeight = height - lineChartStyle.getXLabelMargin();
                    canvas.drawText(label, xCoordinate, labelHeight, labelPaint);
                }
            }
        };
        measureXLabel();
        labelDrawable.setBounds(0, 0, getWidth(), getHeight());
        labelDrawable.setShape(labelsShape);
    }

    protected void measureXLabel() {
        labelPaint.setTextAlign(Paint.Align.CENTER);
        labelPaint.setTextSize(lineChartStyle.getLabelTextSize());

        long minX = getRawMinX();
        long maxX = getMaxX();
        long xGridUnit = getXGridUnit();

        xLabelHeight = 0;
        chartRightMargin = 0;

        long x = minX;
        Rect textBounds = new Rect();

        while (x <= maxX) {
            String label = formatXLabel(x);
            labelPaint.getTextBounds(label, 0, label.length(), textBounds);
            int height = (int) (textBounds.height() + lineChartStyle.getXLabelMargin() * 2);
            if (height > xLabelHeight) {
                xLabelHeight = height;
            }
            chartRightMargin = (long) (textBounds.width() + lineChartStyle.getXLabelMargin());
            x += xGridUnit;
        }
    }

    protected String formatXLabel(long x) {
        if (lineChartStyle.getXLabelFormatter() != null) {
            return lineChartStyle.getXLabelFormatter().format(x);
        }
        return String.format("%,d", x);
    }

    protected void drawLineChart(ShapeDrawable chartDrawable) {
        Shape chartShape = new Shape() {
            @Override
            public void draw(Canvas canvas, Paint paint) {
                long minX = getMinX();
                long maxX = getMaxX();
                long xrange = maxX - minX;

                long minY = getMinY();
                long maxY = getMaxY();
                long yrange = maxY - minY;

                float width = getWidth();
                float height = getHeight();
                float left = getChartLeftMargin();
                float top = getChartTopMargin();
                float right = width - getChartRightMargin();
                float bottom = height - getChartBottomMargin();

                drawChartFrame(canvas, left, top, right, bottom);

                drawXGrid(canvas, minX, xrange);
                drawYGrid(canvas, minY, yrange);

                drawChartFrameBorder(canvas, left, top, right, bottom);

                drawLines(canvas, minX, xrange, minY, yrange);

                if (lineChartStyle.isDrawPoint()) {
                    drawPoints(canvas, minX, xrange, minY, yrange);
                }
            }
        };
        chartDrawable.setBounds(0, 0, getWidth(), getHeight());
        chartDrawable.setShape(chartShape);
    }

    protected void drawChartFrame(Canvas canvas, float left, float top, float right, float bottom) {
        canvas.save();
        canvas.clipRect(left, top, right, bottom);
        canvas.drawColor(lineChartStyle.getBackgroundColor());
        canvas.restore();
    }

    protected void drawChartFrameBorder(Canvas canvas, float left, float top, float right, float bottom) {
        framePaint.setColor(lineChartStyle.getFrameBorderColor());
        framePaint.setStrokeWidth(lineChartStyle.getFrameBorderWidth());

        LineChartStyle.Border border = lineChartStyle.getFrameBorder();
        if (border.left()) {
            canvas.drawLine(left, top, left, bottom, framePaint);
        }
        if (border.top()) {
            canvas.drawLine(left, top, right, top, framePaint);
        }
        if (border.right()) {
            canvas.drawLine(right, top, right, bottom, framePaint);
        }
        if (border.bottom()) {
            canvas.drawLine(left, bottom, right, bottom, framePaint);
        }
    }

    public float getChartLeftMargin() {
        return getYLabelWidth() + lineChartStyle.getYLabelMargin();
    }

    public float getChartTopMargin() {
        return chartTopMargin;
    }

    public float getChartRightMargin() {
        return chartRightMargin;
    }

    public float getChartBottomMargin() {
        return getXLabelHeight() + lineChartStyle.getXLabelMargin();
    }

    public void clearManualMinX() {
        manualMinX = null;
        updateDrawables();
    }

    public void setManualMinX(long minX) {
        manualMinX = minX;
        updateDrawables();
    }

    public long getMinX() {
        if (manualMinX != null) {
            return manualMinX;
        }
        return getRawMinX();
    }

    public long getRawMinX() {
        if (points.isEmpty()) {
            return 0;
        }
        return points.get(0).getX();
    }

    public void clearManualMaxX(){
        manualMaxX = null;
        updateDrawables();
    }

    public void setManualMaxX(long maxX) {
        manualMaxX = maxX;
        updateDrawables();
    }

    public long getMaxX() {
        if (manualMaxX != null) {
            return manualMaxX;
        }
        long rawMaxX = getRawMaxX();
        long step = getUnit(getAbsMaxX());
        return (long) ((Math.floor(1.0 * rawMaxX / step) + 1) * step);
    }

    public long getRawMaxX() {
        if (points.isEmpty()) {
            return DEFAULT_MAX_X;
        }
        return points.get(points.size() - 1).getX();
    }

    protected long getAbsMaxX() {
        if (points.isEmpty()) {
            return DEFAULT_MAX_X;
        }
        long absMaxX = Long.MIN_VALUE;
        for (Point point : points) {
            long x = Math.abs(point.getX());
            if (x > absMaxX) {
                absMaxX = x;
            }
        }
        return absMaxX;
    }

    protected float getXCoordinate(float width, Point point, long minX, long xrange) {
        return getXCoordinate(width, point.getX(), minX, xrange);
    }

    protected float getXCoordinate(float width, long x, long minX, long xrange) {
        return getXCoordinate(width, x, minX, xrange, true);
    }

    protected float getXCoordinate(float width, long x, long minX, long xrange, boolean inChartArea) {
        if (inChartArea) {
            float left = getChartLeftMargin();
            float right = getChartRightMargin();
            float margin = left + right;
            return (width - margin) * (x - minX) * 1.0f / (xrange) + left;
        } else {
            return width * (x - minX) * 1.0f / xrange;
        }
    }

    protected long getAbsMaxY() {
        if (points.isEmpty()) {
            return DEFAULT_MAX_Y;
        }
        long absMaxY = Long.MIN_VALUE;
        for (Point point : points) {
            long y = Math.abs(point.getY());
            if (y > absMaxY) {
                absMaxY = y;
            }
        }
        return absMaxY;
    }

    public void clearManualMinY() {
        manualMinY = null;
        updateDrawables();
    }

    public void setManualMinY(long minY) {
        manualMinY = minY;
        updateDrawables();
    }

    public long getMinY() {
        if (manualMinY != null) {
            return manualMinY;
        }
        long rawMinY = getRawMinY();
        long step = getUnit(getAbsMaxY());
        return (long) ((Math.ceil(1.0 * rawMinY / step) - 1) * step);
    }

    public long getRawMinY() {
        if (points.isEmpty()) {
            return 0;
        }
        long minY = Long.MAX_VALUE;
        for (Point point : points) {
            long y = point.getY();
            if (y < minY) {
                minY = y;
            }
        }
        return minY;
    }

    public void clearManualMaxY() {
        manualMaxY = null;
        updateDrawables();
    }

    public void setManualMaxY(long maxY) {
        manualMaxY = maxY;
        updateDrawables();
    }

    public long getMaxY() {
        if (manualMaxY != null) {
            return manualMaxY;
        }
        long rawMaxY = getRawMaxY();
        long step = getUnit(getAbsMaxY());
        return (long) ((Math.floor(1.0 * rawMaxY / step) + 1) * step);
    }

    public long getRawMaxY() {
        if (points.isEmpty()) {
            return DEFAULT_MAX_Y;
        }
        long maxY = Long.MIN_VALUE;
        for (Point point : points) {
            long y = point.getY();
            if (y > maxY) {
                maxY = y;
            }
        }
        return maxY;
    }

    protected long getUnit(long maxValue) {
        int digits = (int) Math.log10(maxValue);
        long unit = (long) Math.pow(10, digits);
        return unit >= 10 ? unit / 2 : unit;
    }

    protected float getYCoordinate(float height, Point point, long minY, long yrange) {
        return getYCoordinate(height, point.getY(), minY, yrange);
    }

    protected float getYCoordinate(float height, long y, long minY, long yrange) {
        return getYCoordinate(height, y, minY, yrange, true);
    }

    protected float getYCoordinate(float height, long y, long minY, long yrange, boolean inChartArea) {
        if (inChartArea) {
            float top = getChartTopMargin();
            float bottom = getChartBottomMargin();
            float margin = top + bottom;
            return (height - margin) * (1.0f - (y - minY) * 1.0f / (yrange)) + top;
        } else {
            return height * (1.0f - (y - minY) * 1.0f / yrange);
        }
    }

    protected void drawXGrid(Canvas canvas, long minX, long xrange) {
        long maxX = getMaxX();
        long xGridUnit = getXGridUnit();

        float width = getWidth();
        float height = getHeight();

        float top = getChartTopMargin();
        float bottom = height - getChartBottomMargin();

        paint.setColor(lineChartStyle.getGridColor());
        paint.setStrokeWidth(lineChartStyle.getGridWidth());

        long x = calcMinGridValue(minX, xGridUnit);

        while (x <= maxX) {
            float xCoordinate = getXCoordinate(width, x, minX, xrange);
            canvas.drawLine(xCoordinate, bottom, xCoordinate, top, paint);
            x += xGridUnit;
        }
    }

    protected void drawYGrid(Canvas canvas, long minY, long yrange) {
        long yGridUnit = getYGridUnit();
        long maxY = getMaxY();

        float width = getWidth();
        float height = getHeight();

        float left = getChartLeftMargin();
        float right = width - getChartRightMargin();

        paint.setColor(lineChartStyle.getGridColor());
        paint.setStrokeWidth(lineChartStyle.getGridWidth());

        long y = calcMinGridValue(minY, yGridUnit);
        while (y <= maxY) {
            float yCoordinate = getYCoordinate(height, y, minY, yrange);
            canvas.drawLine(left, yCoordinate, right, yCoordinate, paint);
            y += yGridUnit;
        }
    }

    protected void drawLines(Canvas canvas, long minX, long xrange, long minY, long yrange) {
        Point prevPoint = null;
        float px = 0.0f, py = 0.0f;

        float width = getWidth();
        float height = getHeight();

        paint.setColor(lineChartStyle.getLineColor());
        paint.setStrokeWidth(lineChartStyle.getLineWidth());
        for (Point point : points) {
            float x = getXCoordinate(width, point, minX, xrange);
            float y = getYCoordinate(height, point, minY, yrange);
            if (prevPoint != null) {
                canvas.drawLine(px, py, x, y, paint);
            }
            prevPoint = point;
            px = x;
            py = y;
        }
    }

    protected void drawPoints(Canvas canvas, long minX, long xrange, long minY, long yrange) {
        float width = getWidth();
        float height = getHeight();

        for (Point point : points) {
            float x = getXCoordinate(width, point, minX, xrange);
            float y = getYCoordinate(height, point, minY, yrange);

            paint.setColor(lineChartStyle.getLineColor());
            canvas.drawCircle(x, y, lineChartStyle.getPointSize(), paint);

            if (lineChartStyle.isDrawPointCenter()) {
                paint.setColor(lineChartStyle.getBackgroundColor());
                canvas.drawCircle(x, y, lineChartStyle.getPointCenterSize(), paint);
            }
        }
    }

    public void clearManualXGridUnit() {
        manualXGridUnit = null;
        updateDrawables();
    }

    public void setManualXGridUnit(long xGridUnit) {
        manualXGridUnit = xGridUnit;
        updateDrawables();
    }

    public long getXGridUnit() {
        if (manualXGridUnit != null) {
            return manualXGridUnit;
        }
        return getUnit(getAbsMaxX());
    }

    public void clearManualYGridUnit() {
        manualYGridUnit = null;
        updateDrawables();
    }

    public void setManualYGridUnit(long yGridUnit) {
        manualYGridUnit = yGridUnit;
        updateDrawables();
    }

    public long getYGridUnit() {
        if (manualYGridUnit != null) {
            return manualYGridUnit;
        }
        return getUnit(getAbsMaxY());
    }

    public List<Long> getXLabels() {
        if (manualXLabels != null) {
            return manualXLabels;
        }

        long minX = getMinX();
        long maxX = getMaxX();
        long xGridUnit = getXGridUnit();
        long x = calcMinGridValue(minX, xGridUnit);
        List<Long> xLabels = new ArrayList<>();
        while (x <= maxX) {
            xLabels.add(x);
            x += xGridUnit;
        }
        return xLabels;
    }

    public void clearManualXLabels() {
        manualXLabels = null;
        updateDrawables();
    }

    public void setManualXLabels(List<Long> labels) {
        manualXLabels = labels;
        updateDrawables();
    }

    public List<Long> getYLabels() {
        if (manualYLabels != null) {
            return manualYLabels;
        }

        long minY = getMinY();
        long maxY = getMaxY();
        long yGridUnit = getYGridUnit();
        long y = calcMinGridValue(minY, yGridUnit);
        List<Long> yLabels = new ArrayList<>();
        while (y <= maxY) {
            yLabels.add(y);
            y += yGridUnit;
        }
        return yLabels;
    }

    public void clearManualYLabels() {
        manualYLabels = null;
        updateDrawables();
    }

    public void setManualYLabels(List<Long> labels) {
        manualYLabels = labels;
        updateDrawables();
    }

    protected long calcMinGridValue(long min, long gridUnit) {
        return (long) (Math.ceil(1.0 * min / gridUnit) * gridUnit);
    }

    public float getYLabelWidth() {
        if (lineChartStyle.getYLabelWidth() != LineChartStyle.AUTO_WIDTH) {
            return lineChartStyle.getYLabelWidth();
        }
        return yLabelWidth;
    }

    public float getXLabelHeight() {
        if (lineChartStyle.getXLabelHeight() != LineChartStyle.AUTO_HEIGHT) {
            return lineChartStyle.getXLabelHeight();
        }
        return xLabelHeight;
    }

    public LineChartStyle getStyle() {
        return lineChartStyle;
    }

    public void setStyle(LineChartStyle lineChartStyle) {
        this.lineChartStyle = lineChartStyle;
        updateDrawables();
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points.clear();
        this.points.addAll(points);
        updateDrawables();
    }

    private void updateIfEditMode() {
        if (!isInEditMode()) {
            return;
        }
        List<Point> points = new ArrayList<>();
        points.add(new LineChartView.Point(-17, -100));
        points.add(new LineChartView.Point(4, 200));
        points.add(new LineChartView.Point(5, 400));
        points.add(new LineChartView.Point(6, 1100));
        points.add(new LineChartView.Point(7, 700));
        setPoints(points);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        updateDrawables();
    }
}
