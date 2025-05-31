package org.softwarearchitecturedesigngroup10.model.factories;

import org.softwarearchitecturedesigngroup10.model.shapesdata.*;

import java.util.ArrayList;

public class RectangleDataFactory implements ShapeDataFactory{

    @Override
    public ShapeData createShapeData(ArrayList<Double> points, String fillColor, String strokeColor, double strokeWidth, double rotationAngle){
        RectangleData rectangleData = new RectangleData();

        double startX = points.get(0);
        double startY = points.get(1);
        double endX = points.get(2);
        double endY = points.get(3);

        double x = Math.min(startX, endX);
        double y = Math.min(startY, endY);
        double width = Math.abs(endX - startX);
        double height = Math.abs(endY - startY);

        rectangleData.setX(x);
        rectangleData.setY(y);
        rectangleData.setWidth(width);
        rectangleData.setHeight(height);

        rectangleData.setFillColor(fillColor);
        rectangleData.setStrokeColor(strokeColor);
        rectangleData.setStrokeWidth(strokeWidth);

        return rectangleData;
    }

}
