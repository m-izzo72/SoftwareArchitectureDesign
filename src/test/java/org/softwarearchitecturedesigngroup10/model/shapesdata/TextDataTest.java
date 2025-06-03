// src/test/java/org/softwarearchitecturedesigngroup10/model/shapesdata/TextDataTest.java
package org.softwarearchitecturedesigngroup10.model.shapesdata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TextDataTest {

    private TextData textData;

    @BeforeEach
    void setUp() {
        textData = new TextData();
        textData.setText("Sample");
        textData.setFontFamily("Arial");
        textData.setFontSize(12);
    }

    @Test
    void testDefaultConstructorValues() {
        TextData defaultTextData = new TextData();
        assertEquals(16, defaultTextData.getFontSize()); // Default from constructor
        assertEquals("System", defaultTextData.getFontFamily()); // Default from constructor
        assertEquals("TD", defaultTextData.getType());
        assertNull(defaultTextData.getText());
    }

    @Test
    void testSetGetText() {
        String newText = "Hello World";
        textData.setText(newText);
        assertEquals(newText, textData.getText());
    }

    @Test
    void testSetGetFontSize() {
        double newSize = 24.5;
        textData.setFontSize(newSize);
        assertEquals(newSize, textData.getFontSize());
    }

    @Test
    void testSetGetFontFamily() {
        String newFamily = "Times New Roman";
        textData.setFontFamily(newFamily);
        assertEquals(newFamily, textData.getFontFamily());
    }

    @Test
    void testSetStrokeWidth_DoesNothing() {
        double initialStrokeWidth = textData.getStrokeWidth();
        textData.setStrokeWidth(5.0);
        assertEquals(initialStrokeWidth, textData.getStrokeWidth());
    }

    @Test
    void testSetStrokeColor_DoesNothing() {
        String initialStrokeColor = textData.getStrokeColor();
        textData.setStrokeColor("red");
        assertEquals(initialStrokeColor, textData.getStrokeColor());
    }


    @Test
    void testGetWidth_NonEmptyText() {
        textData.setText("Test");
        textData.setFontFamily("Monospaced"); // Use a common font for more predictable results
        textData.setFontSize(12);
        assertTrue(textData.getWidth() > 0);
    }

    @Test
    void testGetHeight_NonEmptyText() {
        textData.setText("Test");
        textData.setFontFamily("Monospaced");
        textData.setFontSize(12);
        assertTrue(textData.getHeight() > 0);
    }

    @Test
    void testGetWidth_EmptyOrNullText_ReturnsZero() {
        textData.setText("");
        assertEquals(0, textData.getWidth());
        textData.setText(null);
        assertEquals(0, textData.getWidth());
    }

    @Test
    void testGetHeight_EmptyOrNullText_ReturnsZero() {
        textData.setText("");
        assertEquals(0, textData.getHeight());
        textData.setText(null);
        assertEquals(0, textData.getHeight());
    }

    @Test
    void testResize_IncreasesFontSize() {
        textData.setText("Test");
        textData.setFontSize(10);
        double initialHeight = textData.getHeight();
        assertTrue(initialHeight > 0);

        textData.resize(textData.getWidth() * 2, initialHeight * 2); // Double the height
        assertTrue(textData.getFontSize() > 10); // Font size should have increased
        assertEquals(2 * initialHeight, textData.getHeight(), 2.0); // Height should be approx double (allowing for minor floating point inaccuracies)
    }

    @Test
    void testResize_DecreasesFontSize() {
        textData.setText("Test");
        textData.setFontSize(20);
        double initialHeight = textData.getHeight();
        assertTrue(initialHeight > 0);

        textData.resize(textData.getWidth() * 0.5, initialHeight * 0.5); // Halve the height
        assertTrue(textData.getFontSize() < 20 && textData.getFontSize() >=5 ); // Font size should have decreased but not below 5
        assertEquals(0.5 * initialHeight, textData.getHeight(), 2.0);
    }

    @Test
    void testResize_MaintainsMinimumFontSize() {
        textData.setText("Test");
        textData.setFontSize(10);
        textData.resize(1, 1); // Try to make it very small
        assertEquals(5, textData.getFontSize()); // Should clamp at 5
    }

    @Test
    void testResize_WithZeroInitialOrNewHeight() {
        textData.setFontSize(12);

        // Initial height is zero (empty text)
        TextData emptyText = new TextData();
        emptyText.setFontSize(12);
        emptyText.setText("");
        double originalFontSizeEmpty = emptyText.getFontSize();
        emptyText.resize(100, 20); // newHeight is 20
        assertEquals(20, emptyText.getFontSize(), 0.01); // Should set font size to newHeight (or min 5)

        // New height is zero
        textData.setText("Hi");
        textData.setFontSize(12);
        double originalFontSize = textData.getFontSize();
        textData.resize(100, 0);
        assertEquals(originalFontSize, textData.getFontSize()); // Font size should not change
    }


    @Test
    void testClone() {
        textData.setX(5);
        textData.setY(15);
        textData.setFillColor("blue");
        textData.setRotationAngle(10);
        textData.setSelected(true);

        TextData cloned = (TextData) textData.clone();
        assertNotSame(textData, cloned);
        assertEquals(textData.getText(), cloned.getText());
        assertEquals(textData.getFontFamily(), cloned.getFontFamily());
        assertEquals(textData.getFontSize(), cloned.getFontSize());
        assertEquals(textData.getX(), cloned.getX());
        assertEquals(textData.getY(), cloned.getY());
        assertEquals(textData.getFillColor(), cloned.getFillColor());
        assertEquals(textData.getStrokeColor(), cloned.getStrokeColor());
        assertEquals(textData.getStrokeWidth(), cloned.getStrokeWidth());
        assertEquals(textData.getRotationAngle(), cloned.getRotationAngle());
        assertEquals(textData.isSelected(), cloned.isSelected());
        assertEquals(textData.getType(), cloned.getType());

        // Modify original, clone should be unaffected
        textData.setText("Changed");
        textData.setFontSize(30);
        assertEquals("Sample", cloned.getText());
        assertEquals(12, cloned.getFontSize());
    }
}