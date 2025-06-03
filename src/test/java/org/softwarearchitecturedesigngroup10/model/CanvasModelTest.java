// src/test/java/org/softwarearchitecturedesigngroup10/model/CanvasModelTest.java
package org.softwarearchitecturedesigngroup10.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.softwarearchitecturedesigngroup10.model.shapesdata.LineData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.RectangleData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;
import org.softwarearchitecturedesigngroup10.model.observers.ModelObserver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class CanvasModelTest {

    private CanvasModel model;

    @BeforeEach
    void setUp() {
        model = new CanvasModel();
    }

    @Test
    void testAddShape() {
        ShapeData shape = new LineData();
        model.addShape(shape);
        assertEquals(1, model.getShapes().size());
        assertTrue(model.getShapes().containsValue(shape));
    }

    @Test
    void testClear() {
        ShapeData shape = new LineData();
        model.addShape(shape);
        assertFalse(model.getShapes().isEmpty());
        model.clear();
        assertTrue(model.getShapes().isEmpty());
    }

    @Test
    void testToggleShapeSelection() {
        ShapeData shape = new LineData();
        model.addShape(shape);
        String shapeId = model.getShapes().entrySet().iterator().next().getKey();
        boolean initiallySelected = shape.isSelected();
        model.toggleShapeSelection(shapeId);
        assertEquals(!initiallySelected, model.getShapes().get(shapeId).isSelected());
    }

    @Test
    void testDeselectAllShapes() {
        ShapeData shape1 = new LineData();
        ShapeData shape2 = new LineData();
        shape1.setSelected(true);
        shape2.setSelected(true);
        model.addShape(shape1);
        model.addShape(shape2);
        model.deselectAllShapes();
        model.getShapes().values().forEach(shape -> assertFalse(shape.isSelected()));
    }

    @Test
    void testDeleteShapes() {
        ShapeData selectedShape = new LineData();
        selectedShape.setSelected(true);
        ShapeData unselectedShape = new LineData();
        unselectedShape.setSelected(false);

        model.addShape(selectedShape);
        model.addShape(unselectedShape);
        model.deleteShapes();

        assertEquals(1, model.getShapes().size());
        assertFalse(model.getShapes().containsValue(selectedShape));
    }

    @Test
    void testAddObserverAndNotifyObservers() {
        CanvasModel canvasModel = new CanvasModel();
        AtomicBoolean updated = new AtomicBoolean(false);

        canvasModel.addObserver(() -> updated.set(true));
        canvasModel.notifyObservers();

        assertTrue(updated.get());
    }

    @Test
    void testSaveAndLoad() throws IOException, ClassNotFoundException {
        LineData shape = new LineData();
        shape.setX(1);
        shape.setY(2);
        shape.setEndX(3);
        shape.setEndY(4);

        model.addShape(shape);
        File tempFile = File.createTempFile("testCanvasModel", ".canvas");
        tempFile.deleteOnExit();

        model.save(tempFile);
        model.clear();
        assertEquals(0, model.getShapes().size());

        model.load(tempFile);
        assertEquals(1, model.getShapes().size());
        assertTrue(model.getShapes().values().iterator().next() instanceof LineData);
        LineData loadedShape = (LineData) model.getShapes().values().iterator().next();
        assertEquals(1, loadedShape.getX());
        assertEquals(2, loadedShape.getY());
        assertEquals(3, loadedShape.getEndX());
        assertEquals(4, loadedShape.getEndY());
    }

    @Test
    void testAddObserverNullOrDuplicateIgnored() {
        final int[] updateCount = {0};
        ModelObserver observer = () -> updateCount[0]++;
        model.addObserver(null);
        model.addObserver(observer);
        model.addObserver(observer); // Adding duplicate
        model.notifyObservers();
        assertEquals(1, updateCount[0]); // Should only be called once
    }

    @Test
    void testBringToFront() {
        ShapeData shapeToMove = new LineData();
        shapeToMove.setSelected(true);
        model.addShape(shapeToMove); // ID will be generated, let's get it
        String keyForShapeToMove = model.getShapes().entrySet().stream()
                .filter(entry -> entry.getValue().equals(shapeToMove))
                .findFirst().get().getKey();

        ShapeData otherShape = new RectangleData();
        model.addShape(otherShape);

        model.bringToFront();

        List<String> newOrderKeys = new ArrayList<>(model.getShapes().keySet());
        assertEquals(keyForShapeToMove, newOrderKeys.get(newOrderKeys.size() - 1));
        assertEquals(2, newOrderKeys.size());
    }

    @Test
    void testSendToBack() {
        ShapeData otherShape = new RectangleData();
        model.addShape(otherShape);

        ShapeData shapeToMove = new LineData();
        shapeToMove.setSelected(true);
        model.addShape(shapeToMove); // ID will be generated
        String keyForShapeToMove = model.getShapes().entrySet().stream()
                .filter(entry -> entry.getValue().equals(shapeToMove))
                .findFirst().get().getKey();
        model.sendToBack();

        List<String> newOrderKeys = new ArrayList<>(model.getShapes().keySet());
        assertEquals(keyForShapeToMove, newOrderKeys.get(0));
        assertEquals(2, newOrderKeys.size());
    }

    @Test
    void testAddShapeByKeepingKeys() {
        ShapeData shape = new LineData();
        String key = "custom-test-key";
        model.addShapeByKeepingKeys(key, shape);
        assertTrue(model.getShapes().containsKey(key));
        assertEquals(shape, model.getShapes().get(key));
    }

    @Test
    void testMoveShapeDataByIDs() {
        LineData line = new LineData();
        line.setX(10); line.setY(10); line.setEndX(20); line.setEndY(20);
        model.addShape(line);
        String id = model.getShapes().keySet().iterator().next();

        RectangleData rect = new RectangleData();
        rect.setX(30); rect.setY(30); rect.setWidth(5); rect.setHeight(5);
        model.addShape(rect);
        String id2 = model.getShapes().keySet().stream().filter(k -> !k.equals(id)).findFirst().get();


        ArrayList<String> idsToMove = new ArrayList<>(List.of(id));
        model.moveShapeDataByIDs(idsToMove, 5, -5);

        LineData movedLine = (LineData) model.getShapes().get(id);
        assertEquals(15, movedLine.getX());
        assertEquals(5, movedLine.getY());
        assertEquals(25, movedLine.getEndX());
        assertEquals(15, movedLine.getEndY());

        RectangleData unMovedRect = (RectangleData) model.getShapes().get(id2);
        assertEquals(30, unMovedRect.getX());
        assertEquals(30, unMovedRect.getY());
    }


    @Test
    void testResizeShape() {
        RectangleData shape = new RectangleData();
        shape.setWidth(10); shape.setHeight(10);
        model.addShape(shape);
        String id = model.getShapes().keySet().iterator().next();

        model.resizeShape(id, 100, 50);

        RectangleData resizedShape = (RectangleData) model.getShapes().get(id);
        assertEquals(100, resizedShape.getWidth());
        assertEquals(50, resizedShape.getHeight());
    }

    @Test
    void testEditShapesFillColour() {
        ShapeData shape = new RectangleData();
        shape.setSelected(true);
        model.addShape(shape);
        model.editShapesFillColour("red");
        assertEquals("red", model.getShapes().values().iterator().next().getFillColor());
    }

    @Test
    void testEditShapesStrokeColour() {
        ShapeData shape = new RectangleData();
        shape.setSelected(true);
        model.addShape(shape);
        model.editShapesStrokeColour("blue");
        assertEquals("blue", model.getShapes().values().iterator().next().getStrokeColor());
    }

    @Test
    void testEditShapesStrokeWidth() {
        ShapeData shape = new RectangleData();
        shape.setSelected(true);
        model.addShape(shape);
        model.editShapesStrokeWidth(5.0);
        assertEquals(5.0, model.getShapes().values().iterator().next().getStrokeWidth());
    }

    @Test
    void testRotateShape() {
        ShapeData shape = new RectangleData();
        shape.setSelected(true);
        model.addShape(shape);
        model.rotateShape(90.0);
        assertEquals(90.0, model.getShapes().values().iterator().next().getRotationAngle());
    }

    @Test
    void testYFlip() {
        ShapeData shape = new RectangleData();
        shape.setSelected(true);
        assertFalse(shape.isYFlipped());
        model.addShape(shape);
        model.yFlip();
        assertTrue(model.getShapes().values().iterator().next().isYFlipped());
        model.yFlip();
        assertFalse(model.getShapes().values().iterator().next().isYFlipped());
    }

    @Test
    void testXFlip() {
        ShapeData shape = new RectangleData();
        shape.setSelected(true);
        assertFalse(shape.isXFlipped());
        model.addShape(shape);
        model.xFlip();
        assertTrue(model.getShapes().values().iterator().next().isXFlipped());
        model.xFlip();
        assertFalse(model.getShapes().values().iterator().next().isXFlipped());
    }
}