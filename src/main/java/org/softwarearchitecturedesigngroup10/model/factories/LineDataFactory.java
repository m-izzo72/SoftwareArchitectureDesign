package org.softwarearchitecturedesigngroup10.model.factories;

import org.softwarearchitecturedesigngroup10.model.shapesdata.LineData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

import java.util.ArrayList;

public class LineDataFactory implements ShapeDataFactory{

    @Override
    public ShapeData createShapeData(ArrayList<Double> points, String fillColor, String strokeColor, double strokeWidth, double rotationAngle){
        LineData lineData = new LineData();

        double startX = points.get(0);
        double startY = points.get(1);
        double endX = points.get(2);
        double endY = points.get(3);

        lineData.setX(startX);
        lineData.setY(startY);
        lineData.setEndX(endX);
        lineData.setEndY(endY);

        lineData.setFillColor(fillColor);
        lineData.setStrokeColor(strokeColor);
        lineData.setStrokeWidth(strokeWidth);
        lineData.setRotationAngle(rotationAngle);

        return lineData;
    }
}
