package org.hogel.android.linechartview;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class LineChartStyle {

    public static final float AUTO_WIDTH = -1;

    public static final float AUTO_HEIGHT = -1;

    public static class Border {
        public static final int LEFT = 1;

        public static final int TOP = 2;

        public static final int RIGHT = 4;

        public static final int BOTTOM = 8;

        public static final int ALL = LEFT | TOP | RIGHT | BOTTOM;

        private int style;

        private int color = Color.GRAY;

        private float width = 4.0f;

        public Border(int... values) {
            style = 0;
            for (int value : values) {
                style |= value;
            }
        }

        public boolean contains(int value) {
            return (style & value) > 0;
        }

        public boolean left() {
            return contains(LEFT);
        }

        public boolean top() {
            return contains(TOP);
        }

        public boolean right() {
            return contains(RIGHT);
        }

        public boolean bottom() {
            return contains(BOTTOM);
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public float getWidth() {
            return width;
        }

        public void setWidth(float width) {
            this.width = width;
        }
    }


    public interface LabelFormatter {
        String format(long value);
    }

    private int lineColor = Color.RED;

    private float lineWidth = 8.0f;

    private boolean drawPoint = true;

    private int pointColor = Color.RED;

    private float pointSize = 10.0f;

    private boolean drawPointCenter = true;

    private float pointCenterSize = 5.0f;

    private int gridColor = Color.GRAY;

    private float gridWidth = 2.0f;

    private int backgroundColor = Color.WHITE;

    private float labelTextSize = 20f;

    private int labelTextColor = Color.BLACK;

    private float yLabelMargin = 10f;

    private float yLabelWidth = AUTO_WIDTH;

    private float xLabelHeight = AUTO_HEIGHT;

    private float xLabelMargin = 10f;

    private final List<Border> borders = new ArrayList<>();

    private LabelFormatter xLabelFormatter = null;

    private LabelFormatter yLabelFormatter = null;

    public LineChartStyle() {
        borders.add(new Border(Border.ALL));
    }

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    public float getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
    }

    public int getPointColor() {
        return pointColor;
    }

    public void setPointColor(int pointColor) {
        this.pointColor = pointColor;
    }

    public float getPointSize() {
        return pointSize;
    }

    public void setPointSize(float pointSize) {
        this.pointSize = pointSize;
    }

    public float getPointCenterSize() {
        return pointCenterSize;
    }

    public void setPointCenterSize(float pointCenterSize) {
        this.pointCenterSize = pointCenterSize;
    }

    public int getGridColor() {
        return gridColor;
    }

    public void setGridColor(int gridColor) {
        this.gridColor = gridColor;
    }

    public float getGridWidth() {
        return gridWidth;
    }

    public void setGridWidth(float gridWidth) {
        this.gridWidth = gridWidth;
    }

    public boolean isDrawPoint() {
        return drawPoint;
    }

    public void setDrawPoint(boolean drawPoint) {
        this.drawPoint = drawPoint;
    }

    public boolean isDrawPointCenter() {
        return drawPointCenter;
    }

    public void setDrawPointCenter(boolean drawPointCenter) {
        this.drawPointCenter = drawPointCenter;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public float getLabelTextSize() {
        return labelTextSize;
    }

    public void setLabelTextSize(float labelTextSize) {
        this.labelTextSize = labelTextSize;
    }

    public int getLabelTextColor() {
        return labelTextColor;
    }

    public void setLabelTextColor(int labelTextColor) {
        this.labelTextColor = labelTextColor;
    }

    public float getYLabelMargin() {
        return yLabelMargin;
    }

    public void setYLabelMargin(float yLabelMargin) {
        this.yLabelMargin = yLabelMargin;
    }

    public float getXLabelMargin() {
        return xLabelMargin;
    }

    public void setXLabelMargin(float xLabelMargin) {
        this.xLabelMargin = xLabelMargin;
    }

    public List<Border> getBorders() {
        return borders;
    }

    public void addBorder(Border border) {
        borders.add(border);
    }

    public void clearBorders() {
        borders.clear();
    }

    public LabelFormatter getXLabelFormatter() {
        return xLabelFormatter;
    }

    public void setXLabelFormatter(LabelFormatter xLabelFormatter) {
        this.xLabelFormatter = xLabelFormatter;
    }

    public LabelFormatter getYLabelFormatter() {
        return yLabelFormatter;
    }

    public void setYLabelFormatter(LabelFormatter yLabelFormatter) {
        this.yLabelFormatter = yLabelFormatter;
    }

    public float getYLabelWidth() {
        return yLabelWidth;
    }

    public void setYLabelWidth(float yLabelWidth) {
        this.yLabelWidth = yLabelWidth;
    }

    public float getXLabelHeight() {
        return xLabelHeight;
    }

    public void setXLabelHeight(float xLabelHeight) {
        this.xLabelHeight = xLabelHeight;
    }
}
