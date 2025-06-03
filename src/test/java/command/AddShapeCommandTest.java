package command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.commands.AddShapeCommand;
import org.softwarearchitecturedesigngroup10.model.shapesdata.LineData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.RectangleData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AddShapeCommandTest {

    private CanvasModel canvasModel;
    private ShapeData shapeToAdd;
    private String existingShapeKey;
    private ShapeData existingShape;


    @BeforeEach
    void setUp() {
        canvasModel = new CanvasModel();
        shapeToAdd = new LineData();
        ((LineData)shapeToAdd).setX(10);

        existingShape = new RectangleData();
        ((RectangleData)existingShape).setX(5);
        ((RectangleData)existingShape).setWidth(100);
        canvasModel.addShape(existingShape);

        Optional<Map.Entry<String, ShapeData>> entryOpt = canvasModel.getShapes().entrySet().stream()
                .filter(entry -> entry.getValue() == existingShape)
                .findFirst();
        assertTrue(entryOpt.isPresent());
        existingShapeKey = entryOpt.get().getKey();
    }

    @Test
    void constructor_NullReceiver_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new AddShapeCommand(null, shapeToAdd));
    }

    @Test
    void constructor_NullShapeToAdd_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new AddShapeCommand(canvasModel, null));
    }

    @Test
    void execute_AddsShapeToReceiver() {
        canvasModel.clear();
        assertTrue(canvasModel.getShapes().isEmpty());

        AddShapeCommand command = new AddShapeCommand(canvasModel, shapeToAdd);
        command.execute();

        assertEquals(1, canvasModel.getShapes().size());
        ShapeData addedShapeInModel = canvasModel.getShapes().values().iterator().next();
        assertEquals(shapeToAdd.getX(), addedShapeInModel.getX());
        assertTrue(addedShapeInModel instanceof LineData);
    }

    @Test
    void undo_RestoresPreviousState_EmptyInitially() {
        canvasModel.clear();
        AddShapeCommand command = new AddShapeCommand(canvasModel, shapeToAdd);
        command.execute();

        assertEquals(1, canvasModel.getShapes().size());
        command.undo();

        assertTrue(canvasModel.getShapes().isEmpty());
    }

    @Test
    void undo_RestoresPreviousState_WithExistingShapes() {
        assertEquals(1, canvasModel.getShapes().size());

        AddShapeCommand command = new AddShapeCommand(canvasModel, shapeToAdd);
        command.execute();

        assertEquals(2, canvasModel.getShapes().size());
        command.undo();

        assertEquals(1, canvasModel.getShapes().size());
        assertTrue(canvasModel.getShapes().containsKey(existingShapeKey));

        ShapeData restoredExistingShape = canvasModel.getShapes().get(existingShapeKey);
        assertNotNull(restoredExistingShape);
        assertTrue(restoredExistingShape instanceof RectangleData);
        assertEquals(((RectangleData)existingShape).getX(), ((RectangleData)restoredExistingShape).getX());

        final ShapeData finalShapeToAdd = shapeToAdd;
        boolean shapeToAddPresent = canvasModel.getShapes().values().stream()
                .anyMatch(s -> s.getClass() == finalShapeToAdd.getClass() && s.getX() == finalShapeToAdd.getX());
    }


    @Test
    void isUndoable_ReturnsTrue() {
        AddShapeCommand command = new AddShapeCommand(canvasModel, shapeToAdd);
        assertTrue(command.isUndoable());
    }
}