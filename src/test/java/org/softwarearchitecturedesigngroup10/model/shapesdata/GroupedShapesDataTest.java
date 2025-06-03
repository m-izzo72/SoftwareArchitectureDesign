package org.softwarearchitecturedesigngroup10.model.shapesdata;// src/test/java/org/softwarearchitecturedesigngroup10/model/shapesdata/composite/GroupedShapesDataTest.java

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.softwarearchitecturedesigngroup10.model.shapesdata.*;
import org.softwarearchitecturedesigngroup10.model.shapesdata.composite.GroupedShapesData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GroupedShapesDataTest {

    private GroupedShapesData group;
    private ShapeData rect1;
    private ShapeData ellipse1;
    private LineData line1;

    @BeforeEach
    void setUp() {
        group = new GroupedShapesData();

        rect1 = new RectangleData();
        rect1.setX(10); rect1.setY(10); ((RectangleData)rect1).setWidth(20); ((RectangleData)rect1).setHeight(30); // Bounds: (10,10) to (30,40)
        rect1.setFillColor("red");
        rect1.setStrokeColor("darkred");
        rect1.setStrokeWidth(1);
        rect1.setRotationAngle(0);


        ellipse1 = new EllipseData();
        ((EllipseData)ellipse1).setCenterX(50); ((EllipseData)ellipse1).setCenterY(50); // X=50-15=35, Y=50-10=40
        ((EllipseData)ellipse1).setRadiusX(15); ((EllipseData)ellipse1).setRadiusY(10); // Bounds: (35,40) to (65,60)
        ellipse1.setX(35);
        ellipse1.setY(40);
        ellipse1.setFillColor("blue");
        ellipse1.setStrokeColor("darkblue");
        ellipse1.setStrokeWidth(2);
        ellipse1.setRotationAngle(10);

        line1 = new LineData(); // Lines don't have fill, only stroke
        line1.setX(0); line1.setY(0); line1.setEndX(5); line1.setEndY(5);
        line1.setStrokeColor("green");
        line1.setStrokeWidth(3);
        line1.setRotationAngle(20);


        group.add(rect1);
        group.add(ellipse1);
    }

    @Test
    void testDefaultType() {
        assertEquals("GD", group.getType());
    }

    @Test
    void testAddAndGetChildren() {
        assertEquals(2, group.getChildren().size());
        assertTrue(group.getChildren().contains(rect1));
        assertTrue(group.getChildren().contains(ellipse1));

        group.add(line1);
        assertEquals(3, group.getChildren().size());
        assertTrue(group.getChildren().contains(line1));
    }

    @Test
    void testRemoveChild() {
        group.remove(rect1);
        assertEquals(1, group.getChildren().size());
        assertFalse(group.getChildren().contains(rect1));
        assertTrue(group.getChildren().contains(ellipse1));
    }

    @Test
    void testGetX_CalculatesCorrectly() {
        // rect1 X is 10, ellipse1 X is 35. Min should be 10.
        assertEquals(10, group.getX(), 0.001);
        group.add(line1); // line1 X is 0
        assertEquals(0, group.getX(), 0.001);
    }

    @Test
    void testGetY_CalculatesCorrectly() {
        // rect1 Y is 10, ellipse1 Y is 40. Min should be 10.
        assertEquals(10, group.getY(), 0.001);
        group.add(line1); // line1 Y is 0
        assertEquals(0, group.getY(), 0.001);
    }

    @Test
    void testGetWidth_CalculatesCorrectly() {
        // rect1: x=10, width=20 -> maxX=30
        // ellipse1: x=35, radiusX=15 (width=30) -> maxX=65
        // Overall: minX=10, maxX=65. Width = 65 - 10 = 55.
        assertEquals(55, group.getWidth(), 0.001);

        group.add(line1); // line1: x=0, endX=5
        // Overall: minX=0, maxX=65. Width = 65 - 0 = 65
        assertEquals(65, group.getWidth(), 0.001);
    }

    @Test
    void testGetHeight_CalculatesCorrectly() {
        // rect1: y=10, height=30 -> maxY=40
        // ellipse1: y=40, radiusY=10 (height=20) -> maxY=60
        // Overall: minY=10, maxY=60. Height = 60 - 10 = 50.
        assertEquals(50, group.getHeight(), 0.001);

        group.add(line1); // line1: y=0, endY=5
        // Overall: minY=0, maxY=60. Height = 60 - 0 = 60
        assertEquals(60, group.getHeight(), 0.001);
    }

    @Test
    void testSetX_MovesGroupAndChildren() {
        double originalRectX = rect1.getX();
        double originalEllipseX = ellipse1.getX();
        double groupInitialX = group.getX(); // Should be 10

        double deltaX = 5.0;
        group.setX(groupInitialX + deltaX); // Move group by 5

        assertEquals(groupInitialX + deltaX, group.getX(), 0.001);
        assertEquals(originalRectX + deltaX, rect1.getX(), 0.001);
        assertEquals(originalEllipseX + deltaX, ellipse1.getX(), 0.001);

        // For EllipseData, also check if centerX is updated if setX updates it
        if (ellipse1 instanceof EllipseData) {
            assertEquals(50 + deltaX, ((EllipseData)ellipse1).getCenterX(), 0.001);
        }
    }

    @Test
    void testSetY_MovesGroupAndChildren() {
        double originalRectY = rect1.getY();
        double originalEllipseY = ellipse1.getY();
        double groupInitialY = group.getY(); // Should be 10

        double deltaY = -5.0;
        group.setY(groupInitialY + deltaY); // Move group by -5

        assertEquals(groupInitialY + deltaY, group.getY(), 0.001);
        assertEquals(originalRectY + deltaY, rect1.getY(), 0.001);
        assertEquals(originalEllipseY + deltaY, ellipse1.getY(), 0.001);

        if (ellipse1 instanceof EllipseData) {
            assertEquals(50 + deltaY, ((EllipseData)ellipse1).getCenterY(), 0.001);
        }
    }


    @Test
    void testSetFillColor_AppliesToChildren() {
        String newColor = "green";
        group.setFillColor(newColor);
        assertEquals(newColor, rect1.getFillColor());
        assertEquals(newColor, ellipse1.getFillColor());
        // line1 has no fill color, so it should remain as it was (or null)
        group.add(line1);
        group.setFillColor(newColor); // Apply again
        assertEquals(newColor, line1.getFillColor()); // LineData's setFillColor should store it
    }

    @Test
    void testSetStrokeColor_AppliesToChildren() {
        String newColor = "yellow";
        group.setStrokeColor(newColor);
        assertEquals(newColor, rect1.getStrokeColor());
        assertEquals(newColor, ellipse1.getStrokeColor());
    }

    @Test
    void testSetStrokeWidth_AppliesToChildren() {
        double newWidth = 5.0;
        group.setStrokeWidth(newWidth);
        assertEquals(newWidth, rect1.getStrokeWidth());
        assertEquals(newWidth, ellipse1.getStrokeWidth());
    }

    @Test
    void testSetRotationAngle_AppliesToChildren() {
        double newAngle = 90.0;
        group.setRotationAngle(newAngle);
        assertEquals(newAngle, rect1.getRotationAngle());
        assertEquals(newAngle, ellipse1.getRotationAngle());
    }

    @Test
    void testSetSelected_AppliesToChildrenAndGroup() {
        group.setSelected(true);
        assertTrue(group.isSelected());
        assertTrue(rect1.isSelected());
        assertTrue(ellipse1.isSelected());

        group.setSelected(false);
        assertFalse(group.isSelected());
        assertFalse(rect1.isSelected());
        assertFalse(ellipse1.isSelected());
    }

    @Test
    void testGetProperties_ReturnsFromFirstChild() {
        assertEquals(rect1.getFillColor(), group.getFillColor());
        assertEquals(rect1.getStrokeColor(), group.getStrokeColor());
        assertEquals(rect1.getStrokeWidth(), group.getStrokeWidth());
        assertEquals(rect1.getRotationAngle(), group.getRotationAngle());
    }

    @Test
    void testFlipY_AppliesToChildren() {
        boolean rectInitialFlip = rect1.isYFlipped();
        boolean ellipseInitialFlip = ellipse1.isYFlipped();

        group.setYFlipped();

        assertEquals(!rectInitialFlip, rect1.isYFlipped());
        assertEquals(!ellipseInitialFlip, ellipse1.isYFlipped());

        group.setYFlipped(); // Flip back

        assertEquals(rectInitialFlip, rect1.isYFlipped());
        assertEquals(ellipseInitialFlip, ellipse1.isYFlipped());
    }

    @Test
    void testFlipX_AppliesToChildren() {
        boolean rectInitialFlip = rect1.isXFlipped();
        boolean ellipseInitialFlip = ellipse1.isXFlipped();

        group.setXFlipped();

        assertEquals(!rectInitialFlip, rect1.isXFlipped());
        assertEquals(!ellipseInitialFlip, ellipse1.isXFlipped());

        group.setXFlipped(); // Flip back

        assertEquals(rectInitialFlip, rect1.isXFlipped());
        assertEquals(ellipseInitialFlip, ellipse1.isXFlipped());
    }

    @Test
    void testClone() {
        group.add(line1);
        group.setX(100); // This will internally adjust children's X based on the group's reported X
        group.setY(120);
        group.setFillColor("purple");
        group.setRotationAngle(33);
        group.setSelected(true);

        GroupedShapesData clonedGroup = (GroupedShapesData) group.clone();

        assertNotSame(group, clonedGroup);
        assertEquals(group.getChildren().size(), clonedGroup.getChildren().size());
        assertEquals(group.getX(), clonedGroup.getX(), 0.001);
        assertEquals(group.getY(), clonedGroup.getY(), 0.001);
        assertEquals(group.getWidth(), clonedGroup.getWidth(), 0.001);
        assertEquals(group.getHeight(), clonedGroup.getHeight(), 0.001);
        assertEquals(group.getFillColor(), clonedGroup.getFillColor());
        assertEquals(group.getRotationAngle(), clonedGroup.getRotationAngle());
        assertEquals(group.isSelected(), clonedGroup.isSelected());

        // Check children are cloned and not the same instances
        for (int i = 0; i < group.getChildren().size(); i++) {
            ShapeData originalChild = group.getChildren().get(i);
            ShapeData clonedChild = clonedGroup.getChildren().get(i);
            assertNotSame(originalChild, clonedChild);
            assertEquals(originalChild.getClass(), clonedChild.getClass());
            assertEquals(originalChild.getX(), clonedChild.getX(), 0.001);
            assertEquals(originalChild.getY(), clonedChild.getY(), 0.001);
            assertEquals(originalChild.getFillColor(), clonedChild.getFillColor());
            // Check selection state propagates correctly during clone
            assertEquals(originalChild.isSelected(), clonedChild.isSelected());
        }

        // Modify original group, cloned should be unaffected
        group.remove(line1);
        group.setFillColor("black");
        ((RectangleData)rect1).setWidth(500);


        assertEquals(3, clonedGroup.getChildren().size()); // Clone had 3 children
        assertEquals("purple", clonedGroup.getFillColor());
        ShapeData clonedRect1 = clonedGroup.getChildren().stream().filter(s -> s instanceof RectangleData).findFirst().get();
        assertEquals(20, ((RectangleData)clonedRect1).getWidth(), 0.001); // Original width for rect1 in clone

    }

    @Test
    void testGroupWithNoChildren() {
        GroupedShapesData emptyGroup = new GroupedShapesData();
        assertEquals(0, emptyGroup.getX()); // Default from ShapeData
        assertEquals(0, emptyGroup.getY()); // Default from ShapeData
        assertEquals(0, emptyGroup.getWidth());
        assertEquals(0, emptyGroup.getHeight());

        emptyGroup.setX(10);
        assertEquals(10, emptyGroup.getX()); // Should update its own X

        emptyGroup.setFillColor("test"); // Should not throw error

    }
}