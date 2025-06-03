// src/test/java/org/softwarearchitecturedesigngroup10/model/factories/RectangleDataFactoryTest.java
package org.softwarearchitecturedesigngroup10.model.factories;

import org.junit.jupiter.api.Test;
import org.softwarearchitecturedesigngroup10.model.shapesdata.RectangleData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class RectangleDataFactoryTest {

    @Test
    void testCreateShapeData() {
        double p1X = 3.0;
        double p1Y = 5.0;
        double p2X = 8.0;
        double p2Y = 10.0;
        String fillColor = "#112233";
        String strokeColor = "#445566";
        double strokeWidth = 4.0;
        double rotationAngle = 0.0; // RectangleDataFactory non imposta rotationAngle, ShapeData lo defaulta a 0

        RectangleDataFactory factory = new RectangleDataFactory();
        ArrayList<Double> points = new ArrayList<>(Arrays.asList(p1X, p1Y, p2X, p2Y));

        ShapeData shapeData = factory.createShapeData(points, fillColor, strokeColor, strokeWidth, rotationAngle);

        assertTrue(shapeData instanceof RectangleData);
        RectangleData rectangleData = (RectangleData) shapeData;

        double expectedX = Math.min(p1X, p2X);
        double expectedY = Math.min(p1Y, p2Y);
        double expectedWidth = Math.abs(p2X - p1X);
        double expectedHeight = Math.abs(p2Y - p1Y);

        assertEquals(expectedX, rectangleData.getX(), 0.0001);
        assertEquals(expectedY, rectangleData.getY(), 0.0001);
        assertEquals(expectedWidth, rectangleData.getWidth(), 0.0001);
        assertEquals(expectedHeight, rectangleData.getHeight(), 0.0001);

        assertEquals(fillColor, rectangleData.getFillColor());
        assertEquals(strokeColor, rectangleData.getStrokeColor());
        assertEquals(strokeWidth, rectangleData.getStrokeWidth(), 0.0001);
        assertEquals(rotationAngle, rectangleData.getRotationAngle(), 0.0001); // Verifichiamo che rimanga 0 o il valore passato se la factory lo usasse
        assertEquals("RD", rectangleData.getType());
    }
}