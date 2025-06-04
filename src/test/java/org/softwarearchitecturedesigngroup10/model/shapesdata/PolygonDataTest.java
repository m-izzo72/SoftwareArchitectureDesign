// src/test/java/org/softwarearchitecturedesigngroup10/model/shapesdata/PolygonDataTest.java
package org.softwarearchitecturedesigngroup10.model.shapesdata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class PolygonDataTest {

    private PolygonData polygon;
    private ArrayList<Double> initialPoints;

    @BeforeEach
    void setUp() {
        polygon = new PolygonData();
        initialPoints = new ArrayList<>(Arrays.asList(10.0, 20.0, 30.0, 20.0, 20.0, 40.0));
        polygon.setPoints(new ArrayList<>(initialPoints)); // Use a copy for setup
        polygon.setX(10.0); // Assuming this sets the reference X for the polygon's local coords
        polygon.setY(20.0); // Assuming this sets the reference Y
    }

    @Test
    void testGetSetPoints() {
        ArrayList<Double> newPoints = new ArrayList<>(Arrays.asList(0.0, 0.0, 5.0, 0.0, 2.5, 5.0));
        polygon.setPoints(newPoints);
        assertEquals(newPoints, polygon.getPoints());
    }

    @Test
    void testDefaultType() {
        assertEquals("PD", polygon.getType());
    }

    @Test
    void testSetX_UpdatesPointsCorrectly() {
        double initialRefX = polygon.getX(); // Should be 10.0
        ArrayList<Double> pointsBefore = new ArrayList<>(polygon.getPoints());

        double newAbsoluteReferenceX = 15.0;
        double deltaXToApply = newAbsoluteReferenceX - initialRefX; // 15.0 - 10.0 = 5.0
        polygon.setX(newAbsoluteReferenceX);

        assertEquals(newAbsoluteReferenceX, polygon.getX());
        ArrayList<Double> pointsAfter = polygon.getPoints();
        for (int i = 0; i < pointsAfter.size(); i++) {
            if (i % 2 == 0) { // X coordinate
                assertEquals(pointsBefore.get(i) + deltaXToApply, pointsAfter.get(i), 0.001);
            } else { // Y coordinate should remain unchanged
                assertEquals(pointsBefore.get(i), pointsAfter.get(i), 0.001);
            }
        }
    }

    @Test
    void testSetY_UpdatesPointsCorrectly() {
        double initialRefY = polygon.getY(); // Should be 20.0
        ArrayList<Double> pointsBefore = new ArrayList<>(polygon.getPoints());

        double newAbsoluteReferenceY = 25.0;
        double deltaYToApply = newAbsoluteReferenceY - initialRefY; // 25.0 - 20.0 = 5.0
        polygon.setY(newAbsoluteReferenceY);

        assertEquals(newAbsoluteReferenceY, polygon.getY());
        ArrayList<Double> pointsAfter = polygon.getPoints();
        for (int i = 0; i < pointsAfter.size(); i++) {
            if (i % 2 == 1) { // Y coordinate
                assertEquals(pointsBefore.get(i) + deltaYToApply, pointsAfter.get(i), 0.001);
            } else { // X coordinate should remain unchanged
                assertEquals(pointsBefore.get(i), pointsAfter.get(i), 0.001);
            }
        }
    }


    @Test
    void testGetWidthHeight_ReturnsZero() {
        assertEquals(0, polygon.getWidth());
        assertEquals(0, polygon.getHeight());
    }

    @Test
    void testResize_DoesNothing() {
        ArrayList<Double> originalPoints = new ArrayList<>(polygon.getPoints());
        polygon.resize(100, 200);
        assertEquals(originalPoints, polygon.getPoints());
    }

    @Test
    void testClone() {
        polygon.setFillColor("red");
        polygon.setStrokeColor("blue");
        polygon.setStrokeWidth(2.0);
        polygon.setRotationAngle(45.0);
        polygon.setSelected(true);

        PolygonData clonedPolygon = (PolygonData) polygon.clone();

        assertNotSame(polygon, clonedPolygon);
        assertEquals(polygon.getPoints(), clonedPolygon.getPoints());
        assertNotSame(polygon.getPoints(), clonedPolygon.getPoints()); // Ensure deep copy of points list
        assertEquals(polygon.getFillColor(), clonedPolygon.getFillColor());
        assertEquals(polygon.getStrokeColor(), clonedPolygon.getStrokeColor());
        assertEquals(polygon.getStrokeWidth(), clonedPolygon.getStrokeWidth());
        assertEquals(polygon.getRotationAngle(), clonedPolygon.getRotationAngle());
        assertEquals(polygon.isSelected(), clonedPolygon.isSelected());
        assertEquals(polygon.getType(), clonedPolygon.getType());
        assertEquals(polygon.getX(), clonedPolygon.getX());
        assertEquals(polygon.getY(), clonedPolygon.getY());

        // Modify original and check clone is unaffected
        ArrayList<Double> newPoints = new ArrayList<>(Arrays.asList(1.0, 1.0));
        polygon.setPoints(newPoints);
        polygon.setFillColor("green");

        assertNotEquals(newPoints, clonedPolygon.getPoints());
        assertNotEquals("green", clonedPolygon.getFillColor());
    }
}