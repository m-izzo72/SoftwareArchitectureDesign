package org.softwarearchitecturedesigngroup10.controller.states;

import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import org.softwarearchitecturedesigngroup10.controller.Controller;
import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.commands.shapeediting.ResizeShapeCommand;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

public class ResizingState implements State {

    private CanvasModel model;
    private String resizingShapeId;
    private double initialPressX;
    private double initialPressY;
    private double initialWidth;
    private double initialHeight;
    private boolean isInitialized = false;

    public boolean initializeResize(CanvasModel model, String shapeId, double pressX, double pressY) {
        this.model = model;
        this.resizingShapeId = shapeId;
        ShapeData shape = model.getShapes().get(shapeId);

        if (shape == null) {
            return false;
        }

        this.initialPressX = pressX;
        this.initialPressY = pressY;
        this.initialWidth = shape.getWidth();
        this.initialHeight = shape.getHeight();
        this.isInitialized = true;
        return true;
    }

    @Override
    public void handleMousePressed(MouseEvent event, Controller context) {
        event.consume();
    }

    @Override
    public void handleMouseDragged(MouseEvent event, Controller context) {
        if (!isInitialized) return;

        double currentX = event.getX();
        double currentY = event.getY();
        double deltaX = currentX - initialPressX;
        double deltaY = currentY - initialPressY;

        double newWidth = initialWidth + deltaX;
        double newHeight = initialHeight + deltaY;

        if (newWidth > 10 && newHeight > 10) {
            model.resizeShape(resizingShapeId, newWidth, newHeight);
        }
        event.consume();
    }

    @Override
    public void handleMouseReleased(MouseEvent event, Controller context) {
        if (!isInitialized) {
            context.setCurrentState(context.getSelectionState());
            event.consume();
            return;
        }

        ShapeData finalShape = model.getShapes().get(resizingShapeId);
        double finalWidth = finalShape.getWidth();
        double finalHeight = finalShape.getHeight();

        if (Math.abs(finalWidth - initialWidth) > 0.1 || Math.abs(finalHeight - initialHeight) > 0.1) {
            ResizeShapeCommand resizeCommand = new ResizeShapeCommand(
                    model,
                    resizingShapeId,
                    initialWidth,
                    initialHeight,
                    finalWidth,
                    finalHeight
            );
            context.getCommandManager().executeCommand(resizeCommand);
        }


        context.setCurrentState(context.getSelectionState());
        event.consume();
    }

    @Override
    public void enterState(Controller context) {
        context.getCanvas().setCursor(Cursor.SE_RESIZE);
        isInitialized = false;
    }

    @Override
    public void exitState(Controller context) {
        context.getCanvas().setCursor(Cursor.DEFAULT);
        this.model = null;
        this.resizingShapeId = null;
        isInitialized = false;
        context.getCanvasView().hideResizeHandle();
    }
}