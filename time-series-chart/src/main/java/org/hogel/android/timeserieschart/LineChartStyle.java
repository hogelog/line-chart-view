package org.hogel.android.timeserieschart;

import android.graphics.Color;

public class LineChartStyle {
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
}
