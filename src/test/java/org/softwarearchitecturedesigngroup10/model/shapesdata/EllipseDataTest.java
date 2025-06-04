// src/test/java/org/softwarearchitecturedesigngroup10/model/shapesdata/EllipseDataTest.java
package org.softwarearchitecturedesigngroup10.model.shapesdata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EllipseDataTest {

    private EllipseData ellipse;

    @BeforeEach
    void setUp() {
        ellipse = new EllipseData();
        ellipse.setX(5.0); // top-left x
        ellipse.setY(10.0); // top-left y
        ellipse.setCenterX(15.0); // centerX = x + radiusX
        ellipse.setCenterY(20.0); // centerY = y + radiusY
        ellipse.setRadiusX(10.0); // width = 20
        ellipse.setRadiusY(10.0); // height = 20
    }

    @Test
    void testDefaultType() {
        assertEquals("ED", new EllipseData().getType());
    }

    @Test
    void testGetSetCenterX() {
        ellipse.setCenterX(10.5);
        assertEquals(10.5, ellipse.getCenterX());
    }

    @Test
    void testGetSetCenterY() {
        ellipse.setCenterY(-3.2);
        assertEquals(-3.2, ellipse.getCenterY());
    }

    @Test
    void testGetSetRadiusX() {
        ellipse.setRadiusX(15.0);
        assertEquals(15.0, ellipse.getRadiusX());
    }

    @Test
    void testGetSetRadiusY() {
        ellipse.setRadiusY(25.5);
        assertEquals(25.5, ellipse.getRadiusY());
    }

    @Test
    void testGetWidth() {
        assertEquals(20.0, ellipse.getWidth(), 0.001); // 2 * radiusX
        ellipse.setRadiusX(7.5);
        assertEquals(15.0, ellipse.getWidth(), 0.001);
    }

    @Test
    void testGetHeight() {
        assertEquals(20.0, ellipse.getHeight(), 0.001); // 2 * radiusY
        ellipse.setRadiusY(12.0);
        assertEquals(24.0, ellipse.getHeight(), 0.001);
    }

    @Test
    void testResize() {
        ellipse.setX(10); // Initial top-left X
        ellipse.setY(10); // Initial top-left Y

        double newWidth = 30.0;
        double newHeight = 40.0;
        ellipse.resize(newWidth, newHeight);

        assertEquals(newWidth / 2.0, ellipse.getRadiusX(), 0.001);
        assertEquals(newHeight / 2.0, ellipse.getRadiusY(), 0.001);
        assertEquals(10 + (newWidth / 2.0), ellipse.getCenterX(), 0.001); // X + newRadiusX
        assertEquals(10 + (newHeight / 2.0), ellipse.getCenterY(), 0.001); // Y + newRadiusY

        // Verify getWidth and getHeight reflect resize
        assertEquals(newWidth, ellipse.getWidth(), 0.001);
        assertEquals(newHeight, ellipse.getHeight(), 0.001);
    }


    @Test
    void testClone() {
        ellipse.setFillColor("yellow");
        ellipse.setStrokeColor("orange");
        ellipse.setStrokeWidth(1.5);
        ellipse.setRotationAngle(45);
        ellipse.setSelected(false);

        EllipseData clonedEllipse = (EllipseData) ellipse.clone();

        assertNotSame(ellipse, clonedEllipse);
        assertEquals(ellipse.getX(), clonedEllipse.getX());
        assertEquals(ellipse.getY(), clonedEllipse.getY());
        assertEquals(ellipse.getCenterX(), clonedEllipse.getCenterX());
        assertEquals(ellipse.getCenterY(), clonedEllipse.getCenterY());
        assertEquals(ellipse.getRadiusX(), clonedEllipse.getRadiusX());
        assertEquals(ellipse.getRadiusY(), clonedEllipse.getRadiusY());
        assertEquals(ellipse.getFillColor(), clonedEllipse.getFillColor());
        assertEquals(ellipse.getStrokeColor(), clonedEllipse.getStrokeColor());
        assertEquals(ellipse.getStrokeWidth(), clonedEllipse.getStrokeWidth());
        assertEquals(ellipse.getRotationAngle(), clonedEllipse.getRotationAngle());
        assertEquals(ellipse.isSelected(), clonedEllipse.isSelected());
        assertEquals(ellipse.getType(), clonedEllipse.getType());

        // Modify original, clone should be unaffected
        ellipse.setCenterX(100.0);
        ellipse.setFillColor("black");

        assertEquals(15.0, clonedEllipse.getCenterX(), 0.001); // Original centerX for clone
        assertEquals("yellow", clonedEllipse.getFillColor());
    }
}