package org.hogel.android.linechartview;

import android.graphics.Color;

public class LineChartStyle {

    public static class Border {
        public static final int LEFT = 1;

        public static final int TOP = 2;

        public static final int RIGHT = 4;

        public static final int BOTTOM = 8;

        public static final int ALL = LEFT | TOP | RIGHT | BOTTOM;

        private int style;

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

    private float xLabelMargin = 10f;

    private Border frameBorder = new Border(Border.ALL);

    private int frameBorderColor = Color.BLACK;

    private float frameBorderWidth = 4.0f;

    private LabelFormatter xLabelFormatter = null;

    private LabelFormatter yLabelFormatter = null;

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

    public Border getFrameBorder() {
        return frameBorder;
    }

    public void setFrameBorder(Border frameBorder) {
        this.frameBorder = frameBorder;
    }

    public int getFrameBorderColor() {
        return frameBorderColor;
    }

    public void setFrameBorderColor(int frameBorderColor) {
        this.frameBorderColor = frameBorderColor;
    }

    public float getFrameBorderWidth() {
        return frameBorderWidth;
    }

    public void setFrameBorderWidth(float frameBorderWidth) {
        this.frameBorderWidth = frameBorderWidth;
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
}
