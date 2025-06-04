package command;// src/test/java/org/softwarearchitecturedesigngroup10/model/commands/CommandManagerTest.java


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.commands.Command;
import org.softwarearchitecturedesigngroup10.model.commands.CommandManager;
import org.softwarearchitecturedesigngroup10.model.shapesdata.LineData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;
import org.softwarearchitecturedesigngroup10.model.commands.AddShapeCommand;

import static org.junit.jupiter.api.Assertions.*;

class CommandManagerTest {

    private CommandManager commandManager;
    private CanvasModel mockCanvasModel; // Using a real model for some tests might be simpler

    @BeforeEach
    void setUp() {
        commandManager = new CommandManager();
        mockCanvasModel = new CanvasModel(); // Use a real one to simplify AddShapeCommand
    }

    @Test
    void testExecuteCommand_UndoableCommand_IsAddedToStackAndExecuted() {
        ShapeData shape = new LineData();
        Command command = new AddShapeCommand(mockCanvasModel, shape); // AddShapeCommand is undoable

        assertTrue(commandManager.isUndoStackEmpty());
        commandManager.executeCommand(command);

        assertFalse(commandManager.isUndoStackEmpty());
        assertEquals(1, mockCanvasModel.getShapes().size()); // Check if execute had an effect
    }

    @Test
    void testUndo_WhenStackIsNotEmpty_PopsAndUndoesCommand() {
        ShapeData shape = new LineData();
        Command command = new AddShapeCommand(mockCanvasModel, shape);
        commandManager.executeCommand(command); // Adds shape, mockCanvasModel has 1 shape

        assertFalse(commandManager.isUndoStackEmpty());
        assertEquals(1, mockCanvasModel.getShapes().size());

        commandManager.undo();

        assertTrue(commandManager.isUndoStackEmpty());
        assertEquals(0, mockCanvasModel.getShapes().size()); // Check if undo had an effect
    }

    @Test
    void testUndo_WhenStackIsEmpty_DoesNothing() {
        assertTrue(commandManager.isUndoStackEmpty());
        commandManager.undo(); // Should not throw an error
        assertTrue(commandManager.isUndoStackEmpty());
    }

    @Test
    void testIsUndoStackEmpty() {
        assertTrue(commandManager.isUndoStackEmpty());
        ShapeData shape = new LineData();
        Command command = new AddShapeCommand(mockCanvasModel, shape);
        commandManager.executeCommand(command);
        assertFalse(commandManager.isUndoStackEmpty());
    }

    @Test
    void testClear_EmptiesUndoStack() {
        ShapeData shape = new LineData();
        Command command1 = new AddShapeCommand(mockCanvasModel, shape);
        Command command2 = new AddShapeCommand(mockCanvasModel, new LineData());
        commandManager.executeCommand(command1);
        commandManager.executeCommand(command2);

        assertFalse(commandManager.isUndoStackEmpty());
        commandManager.clear();
        assertTrue(commandManager.isUndoStackEmpty());
    }
}