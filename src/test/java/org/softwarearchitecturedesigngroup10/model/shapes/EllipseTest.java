package org.softwarearchitecturedesigngroup10.model.shapes;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EllipseTest {

    @Test
    /* This test case asserts that default coords and radius upon instancing a new Ellipse are equal to (0, 0), 0 and 0 */
    void testDefaultEllipseCoordinatesAndRadii() {
        Ellipse ellipse = new Ellipse();
        assertEquals(0.0, ellipse.getX(), "Default X should be 0.0");
        assertEquals(0.0, ellipse.getY(), "Default Y should be 0.0");
        assertEquals(0.0, ellipse.getRadiusX(), "Default X radius should be 0.0");
        assertEquals(0.0, ellipse.getRadiusY(), "Default Y radius should be 0.0");
    }

    @Test
    /* This test case asserts that setters and getters work properly */
    void testSetAndGetRadii() {
        Ellipse ellipse = new Ellipse();
        double expectedRadiusX = 50.0;
        double expectedRadiusY = 30.0;
        ellipse.setRadiusX(expectedRadiusX);
        ellipse.setRadiusY(expectedRadiusY);
        assertEquals(expectedRadiusX, ellipse.getRadiusX(), "X radius should be set correctly");
        assertEquals(expectedRadiusY, ellipse.getRadiusY(), "Y radius should be set correctly");
    }
}