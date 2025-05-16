package org.softwarearchitecturedesigngroup10.model.shapes;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RectangleTest {

    @Test
    void testDefaultRectangleCoordinatesAndDimensions() {
        Rectangle rectangle = new Rectangle();
        assertEquals(0.0, rectangle.getX(), "Default X should be 0.0");
        assertEquals(0.0, rectangle.getY(), "Default Y should be 0.0");
        assertEquals(0.0, rectangle.getWidth(), "Default width should be 0.0");
        assertEquals(0.0, rectangle.getHeight(), "Default height should be 0.0");
    }

    @Test
    void testSetAndGetDimensions() {
        Rectangle rectangle = new Rectangle();
        double expectedWidth = 100.0;
        double expectedHeight = 75.0;
        rectangle.setWidth(expectedWidth);
        rectangle.setHeight(expectedHeight);
        assertEquals(expectedWidth, rectangle.getWidth(), "Width should be set correctly");
        assertEquals(expectedHeight, rectangle.getHeight(), "Height should be set correctly");
    }


}
