package org.softwarearchitecturedesigngroup10.model.factories;

import org.junit.jupiter.api.Test;
import org.softwarearchitecturedesigngroup10.model.shapesdata.EllipseData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class EllipseDataFactoryTest {

    @Test
    void testCreateShapeData() {
        double startX = 2.0;
        double startY = 4.0;
        double endX = 6.0;  // width = 4, radiusX = 2
        double endY = 10.0; // height = 6, radiusY = 3
        String fillColor = "black";
        String strokeColor = "red";
        double strokeWidth = 3.0;
        double rotationAngle = 30.0;

        EllipseDataFactory factory = new EllipseDataFactory();
        ArrayList<Double> points = new ArrayList<>(Arrays.asList(startX, startY, endX, endY));
        ShapeData shapeData = factory.createShapeData(points, fillColor, strokeColor, strokeWidth, rotationAngle);

        assertTrue(shapeData instanceof EllipseData);

        EllipseData ellipse = (EllipseData) shapeData;

        assertEquals((startX + endX) / 2, ellipse.getCenterX(), 0.0001);
        assertEquals((startY + endY) / 2, ellipse.getCenterY(), 0.0001);
        assertEquals(Math.abs(endX - startX) / 2, ellipse.getRadiusX(), 0.0001);
        assertEquals(Math.abs(endY - startY) / 2, ellipse.getRadiusY(), 0.0001);
        assertEquals(ellipse.getCenterX() - ellipse.getRadiusX(), ellipse.getX(), 0.0001);
        assertEquals(ellipse.getCenterY() - ellipse.getRadiusY(), ellipse.getY(), 0.0001);
        assertEquals(fillColor, ellipse.getFillColor());
        assertEquals(strokeColor, ellipse.getStrokeColor());
        assertEquals(strokeWidth, ellipse.getStrokeWidth());
        assertEquals(rotationAngle, ellipse.getRotationAngle());
    }
}