package org.softwarearchitecturedesigngroup10.model.factories;

import org.softwarearchitecturedesigngroup10.model.shapesdata.*;

public class RectangleDataFactory implements ShapeDataFactory{

    @Override
    public ShapeData createShapeData(double startX, double startY, double endX, double endY, String fillColor, String strokeColor, double strokeWidth, double rotationAngle){
        RectangleData rectangleData = new RectangleData();

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
