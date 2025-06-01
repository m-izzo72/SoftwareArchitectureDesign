// src/main/java/org/softwarearchitecturedesigngroup10/controller/states/MovingState.java
package org.softwarearchitecturedesigngroup10.controller.states;

import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import org.softwarearchitecturedesigngroup10.controller.Controller;
import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.commands.selection.MoveShapesCommand;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

import java.util.ArrayList;
import java.util.List;

public class MovingState implements State {

    private CanvasModel model;
    private String primaryDraggedShapeId;
    private double dragOffsetX;
    private double dragOffsetY;

    private double initialPressX_forUndo;
    private double initialPressY_forUndo;
    private List<String> selectedShapeIds_atDragStart;

    public boolean initializeMove(CanvasModel model, String primaryDraggedShapeId, double pressX, double pressY) {
        this.model = model;
        this.primaryDraggedShapeId = primaryDraggedShapeId;

        ShapeData primaryShape = model.getShapes().get(this.primaryDraggedShapeId);
        if (primaryShape == null) {
            return false;
        }

        this.dragOffsetX = pressX - primaryShape.getX();
        this.dragOffsetY = pressY - primaryShape.getY();

        this.initialPressX_forUndo = pressX;
        this.initialPressY_forUndo = pressY;
        this.selectedShapeIds_atDragStart = new ArrayList<>(model.getSelectedShapes().keySet());

        System.out.println("MovingState Initialized. Offset: (" + dragOffsetX + ", " + dragOffsetY + ")");
        return true;
    }

    @Override
    public void handleMousePressed(MouseEvent event, Controller context) {
        event.consume();
    }

    @Override
    public void handleMouseDragged(MouseEvent event, Controller context) {
        if (primaryDraggedShapeId == null || model == null) return;

        double currentMouseX = event.getX();
        double currentMouseY = event.getY();

        double targetX = currentMouseX - dragOffsetX;
        double targetY = currentMouseY - dragOffsetY;

        ShapeData primaryShape = model.getShapes().get(primaryDraggedShapeId);
        if (primaryShape == null) return;

        double currentX = primaryShape.getX();
        double currentY = primaryShape.getY();

        double dx = targetX - currentX;
        double dy = targetY - currentY;

        if (Math.abs(dx) > 0 || Math.abs(dy) > 0) {
            model.moveSelectedShapes(dx, dy);
        }
        event.consume();
    }

    @Override
    public void handleMouseReleased(MouseEvent event, Controller context) {
        if (model == null || selectedShapeIds_atDragStart == null || selectedShapeIds_atDragStart.isEmpty()) {
            context.setCurrentState(context.getSelectionState());
            event.consume();
            return;
        }

        double finalMouseX = event.getX();
        double finalMouseY = event.getY();

        double totalDx = finalMouseX - initialPressX_forUndo;
        double totalDy = finalMouseY - initialPressY_forUndo;

        System.out.println("Drag finished. Total dx=" + totalDx + ", dy=" + totalDy);

        if (Math.abs(totalDx) > 0.1 || Math.abs(totalDy) > 0.1) {
            MoveShapesCommand moveCommand = new MoveShapesCommand(
                    model,
                    selectedShapeIds_atDragStart,
                    totalDx,
                    totalDy
            );
            context.getCommandManager().executeCommand(moveCommand);
        }

        context.setCurrentState(context.getSelectionState());
        event.consume();
    }

    @Override
    public void enterState(Controller context) {
        System.out.println("Entering Moving State");
        context.getCanvas().setCursor(Cursor.MOVE);
    }

    @Override
    public void exitState(Controller context) {
        System.out.println("Exiting Moving State");
        context.getCanvas().setCursor(Cursor.DEFAULT);
        this.primaryDraggedShapeId = null;
        this.model = null;
        this.dragOffsetX = 0;
        this.dragOffsetY = 0;
    }
}