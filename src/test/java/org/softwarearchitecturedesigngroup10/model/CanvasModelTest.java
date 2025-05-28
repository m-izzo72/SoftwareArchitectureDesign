// Contenuto suggerito per: src/test/java/org/softwarearchitecturedesigngroup10/model/CanvasModelTest.java
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
    void testSelectShape() {
        ShapeData shape = new LineData();
        model.addShape(shape);
        String shapeId = model.getShapes().entrySet().iterator().next().getKey();
        boolean initiallySelected = shape.isSelected();
        model.selectShape(shapeId);
        assertEquals(!initiallySelected, model.getShapes().get(shapeId).isSelected());
    }

    @Test
    void testDeselectAllShapes() {
        ShapeData shape1 = new LineData() ;
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
        File tempFile = File.createTempFile("test", ".tmp");
        tempFile.deleteOnExit();

        model.save(tempFile);
        model.clear();
        assertEquals(0, model.getShapes().size());

        model.load(tempFile);
        assertEquals(1, model.getShapes().size());
    }

    @Test
    void testAddObserverNullOrDuplicateIgnored() {
        ModelObserver observer = () -> {};
        model.addObserver(null);
        model.addObserver(observer);
        model.addObserver(observer);
        model.notifyObservers();
    }

    @Test
    void testBringToFront() {
        ShapeData shapeToMove = new LineData();
        shapeToMove.setSelected(true);
        model.addShape(shapeToMove);

        ShapeData otherShape = new LineData();
        model.addShape(otherShape);

        List<String> initialKeys = new ArrayList<>(model.getShapes().keySet());
        String keyForShapeToMove = initialKeys.get(0);

        model.bringToFront();

        List<String> newOrderKeys = new ArrayList<>(model.getShapes().keySet());
        assertEquals(keyForShapeToMove, newOrderKeys.get(newOrderKeys.size() - 1));
        assertEquals(initialKeys.get(1), newOrderKeys.get(0));
        assertEquals(2, newOrderKeys.size());
    }

    @Test
    void testSendToBack() {
        ShapeData otherShape = new LineData();
        model.addShape(otherShape);

        ShapeData shapeToMove = new LineData();
        shapeToMove.setSelected(true);
        model.addShape(shapeToMove);

        List<String> initialKeys = new ArrayList<>(model.getShapes().keySet());
        String keyForShapeToMove = initialKeys.get(1);

        model.sendToBack();

        List<String> newOrderKeys = new ArrayList<>(model.getShapes().keySet());
        assertEquals(keyForShapeToMove, newOrderKeys.get(0));
        assertEquals(initialKeys.get(0), newOrderKeys.get(1));
        assertEquals(2, newOrderKeys.size());
    }

    @Test
    void testAddShapeByKeepingKeys() {
        ShapeData shape = new LineData();
        String key = "test-key";
        model.addShapeByKeepingKeys(key, shape);
        assertTrue(model.getShapes().containsKey(key));
    }

    @Test
    void testMoveShapeDataByIDs() {
        LineData shape = new LineData();
        shape.setX(10);
        shape.setY(10);
        shape.setEndX(20);
        shape.setEndY(20);

        model.addShape(shape);
        String id = model.getShapes().entrySet().iterator().next().getKey();

        ArrayList<String> ids = new ArrayList<>();
        ids.add(id);
        model.moveShapeDataByIDs(ids, 5, 5);

        LineData movedShape = (LineData) model.getShapes().get(id);
        assertEquals(15, movedShape.getX());
        assertEquals(15, movedShape.getY());
        assertEquals(25, movedShape.getEndX());
        assertEquals(25, movedShape.getEndY());
    }

    @Test
    void testResizeShape() {
        RectangleData shape = new RectangleData();
        model.addShape(shape);
        String id = model.getShapes().entrySet().iterator().next().getKey();
        model.resizeShape(id, 100, 50);
        assertEquals(100, shape.getWidth());
        assertEquals(50, shape.getHeight());
    }

    @Test
    void testEditShapesFillColour() {
        ShapeData shape = new RectangleData();
        shape.setSelected(true);
        model.addShape(shape);
        model.editShapesFillColour("red");
        assertEquals("red", shape.getFillColor());
    }

    @Test
    void testEditShapesStrokeColour() {
        ShapeData shape = new RectangleData();
        shape.setSelected(true);
        model.addShape(shape);
        model.editShapesStrokeColour("blue");
        assertEquals("blue", shape.getStrokeColor());
    }

    @Test
    void testEditShapesStrokeWidth() {
        ShapeData shape = new RectangleData();
        shape.setSelected(true);
        model.addShape(shape);
        model.editShapesStrokeWidth(5.0);
        assertEquals(5.0, shape.getStrokeWidth());
    }
}