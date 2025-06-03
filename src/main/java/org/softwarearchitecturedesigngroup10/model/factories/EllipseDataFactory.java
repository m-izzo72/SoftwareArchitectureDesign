package org.softwarearchitecturedesigngroup10.model.factories;

import org.softwarearchitecturedesigngroup10.model.shapesdata.EllipseData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

import java.util.ArrayList;

public class EllipseDataFactory implements ShapeDataFactory{

    public ShapeData createShapeData(ArrayList<Double> points, String fillColor, String strokeColor, double strokeWidth, double rotationAngle){
        EllipseData ellipseData = new EllipseData();

        double startX = points.get(0);
        double startY = points.get(1);
        double endX = points.get(2);
        double endY = points.get(3);

        double centerX = (startX + endX) / 2;
        double centerY = (startY + endY) / 2;
        double radiusX = Math.abs(endX - startX) / 2;
        double radiusY = Math.abs(endY - startY) / 2;

        ellipseData.setCenterX(centerX);
        ellipseData.setCenterY(centerY);
        ellipseData.setRadiusX(radiusX);
        ellipseData.setRadiusY(radiusY);

        ellipseData.setX(centerX - radiusX);
        ellipseData.setY(centerY - radiusY);

        ellipseData.setFillColor(fillColor);
        ellipseData.setStrokeColor(strokeColor);
        ellipseData.setStrokeWidth(strokeWidth);
        ellipseData.setRotationAngle(rotationAngle);

        return ellipseData;
    }
}
