package org.softwarearchitecturedesigngroup10.model.factories;

import org.softwarearchitecturedesigngroup10.model.shapesdata.PolygonData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

import java.util.ArrayList;

public class PolygonDataFactory implements ShapeDataFactory {

    @Override
    public ShapeData createShapeData(ArrayList<Double> points, String fillColor, String strokeColor, double strokeWidth, double rotationAngle) {
        PolygonData polygonData = new PolygonData();
        polygonData.setPoints(points);

        polygonData.setFillColor(fillColor);
        polygonData.setStrokeColor(strokeColor);
        polygonData.setStrokeWidth(strokeWidth);
        polygonData.setRotationAngle(rotationAngle);

        return polygonData;
    }


}
