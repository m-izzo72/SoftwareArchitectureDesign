package org.softwarearchitecturedesigngroup10.model.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.shapesdata.LineData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

import static org.junit.jupiter.api.Assertions.*;

class DeleteShapeCommandTest {

    private CanvasModel canvasModel;
    private ShapeData shape;

    @BeforeEach
    void setup() {
        canvasModel = new CanvasModel();
        shape = new LineData();
        shape.setSelected(true);
        canvasModel.addShape(shape);
    }

    @Test
    void testConstructorThrowsOnNullReceiver() {
        assertThrows(IllegalArgumentException.class, () -> {
            DeleteShapeCommand command = new DeleteShapeCommand(null);
            command.execute();
        });
    }

    @Test
    void testExecuteRemovesSelectedShape() {
        assertEquals(1, canvasModel.getShapes().size());
        DeleteShapeCommand command = new DeleteShapeCommand(canvasModel);
        command.execute();
        assertEquals(0, canvasModel.getShapes().size());
    }
}
