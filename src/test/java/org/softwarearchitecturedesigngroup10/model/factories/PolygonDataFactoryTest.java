package org.softwarearchitecturedesigngroup10.model.factories;

import org.junit.jupiter.api.Test;
import org.softwarearchitecturedesigngroup10.model.shapesdata.PolygonData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PolygonDataFactoryTest {

    @Test
    void testCreateShapeData() {
        PolygonDataFactory factory = new PolygonDataFactory();
        ArrayList<Double> points = new ArrayList<>(Arrays.asList(10.0, 20.0, 30.0, 40.0, 50.0, 60.0));
        String fillColor = "blue";
        String strokeColor = "green";
        double strokeWidth = 2.5;
        double rotationAngle = 45.0;

        ShapeData shapeData = factory.createShapeData(points, fillColor, strokeColor, strokeWidth, rotationAngle);

        assertTrue(shapeData instanceof PolygonData);
        PolygonData polygonData = (PolygonData) shapeData;

        assertEquals(points, polygonData.getPoints());
        assertEquals(fillColor, polygonData.getFillColor());
        assertEquals(strokeColor, polygonData.getStrokeColor());
        assertEquals(strokeWidth, polygonData.getStrokeWidth());
        assertEquals(rotationAngle, polygonData.getRotationAngle());
        assertEquals("PD", polygonData.getType());
    }

    @Test
    void testCreateShapeDataEmptyPoints() {
        PolygonDataFactory factory = new PolygonDataFactory();
        ArrayList<Double> points = new ArrayList<>();
        String fillColor = "red";
        String strokeColor = "black";
        double strokeWidth = 1.0;
        double rotationAngle = 0;

        ShapeData shapeData = factory.createShapeData(points, fillColor, strokeColor, strokeWidth, rotationAngle);
        assertTrue(shapeData instanceof PolygonData);
        PolygonData polygonData = (PolygonData) shapeData;
        assertTrue(polygonData.getPoints().isEmpty());
        assertEquals(rotationAngle, polygonData.getRotationAngle());
    }
}