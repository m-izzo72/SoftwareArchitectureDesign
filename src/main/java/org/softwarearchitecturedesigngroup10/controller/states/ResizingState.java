package org.softwarearchitecturedesigngroup10.controller.states;

import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import org.softwarearchitecturedesigngroup10.controller.Controller;
import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.commands.shapeediting.ResizeShapeCommand;
import org.softwarearchitecturedesigngroup10.model.shapesdata.LineData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

public class ResizingState implements State {

    private CanvasModel model;
    private String resizingShapeId;
    private double initialPressX;
    private double initialPressY;
    private double initialWidth;
    private double initialHeight;
    private double initialX;
    private double initialY;
    private double aspectRatio;
    private boolean isInitialized = false;

    public boolean initializeResize(CanvasModel model, String shapeId, double pressX, double pressY) {
        this.model = model;
        this.resizingShapeId = shapeId;
        ShapeData shape = model.getShapes().get(shapeId);

        if (shape == null || shape instanceof LineData || shape.getWidth() <= 5 || shape.getHeight() <= 5) {
            this.isInitialized = false;
            return false;
        }

        this.initialPressX = pressX;
        this.initialPressY = pressY;
        this.initialX = shape.getX();
        this.initialY = shape.getY();
        this.initialWidth = shape.getWidth();
        this.initialHeight = shape.getHeight();
        // Evita divisione per zero se l'altezza è 0
        this.aspectRatio = (this.initialHeight > 0) ? (this.initialWidth / this.initialHeight) : 1.0;
        this.isInitialized = true;
        return true;
    }

    @Override
    public void handleMousePressed(MouseEvent event, Controller context) {
        event.consume();
    }

    @Override
    public void handleMouseDragged(MouseEvent event, Controller context) {
        System.out.println(isInitialized);
        if (isInitialized) return;

        double currentX = event.getX();
        double currentY = event.getY();

        double deltaX = currentX - initialPressX;
        double deltaY = currentY - initialPressY;

        double newWidth;
        double newHeight;

        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            newWidth = initialWidth + deltaX;
            newHeight = (aspectRatio > 0) ? (newWidth / aspectRatio) : initialHeight;
        } else {
            newHeight = initialHeight + deltaY;
            newWidth = newHeight * aspectRatio;
        }

        System.out.println("ResizingState: Dragged - New W:" + newWidth + " H:" + newHeight); // <-- DEBUG

        if (newWidth > 10 && newHeight > 10) {
            System.out.println("ResizingState: Calling model.resizeShape"); // <-- DEBUG
            model.resizeShape(resizingShapeId, newWidth, newHeight);
        }
        event.consume();
    }

    @Override
    public void handleMouseReleased(MouseEvent event, Controller context) {
        if (isInitialized) {
            context.setCurrentState(context.getSelectionState());
            event.consume();
            return;
        }

        ShapeData finalShape = model.getShapes().get(resizingShapeId);
        double finalWidth = finalShape.getWidth();
        double finalHeight = finalShape.getHeight();

        ResizeShapeCommand resizeCommand = new ResizeShapeCommand(
                model,
                resizingShapeId,
                initialWidth,
                initialHeight,
                finalWidth,
                finalHeight
        );
        context.getCommandManager().executeCommand(resizeCommand);

        context.setCurrentState(context.getSelectionState());
        event.consume();
    }

    @Override
    public void enterState(Controller context) {
        context.getCanvas().setCursor(Cursor.SE_RESIZE);
        isInitialized = false; // Resetta all'ingresso
    }

    @Override
    public void exitState(Controller context) {
        context.getCanvas().setCursor(Cursor.DEFAULT);
        this.model = null;
        this.resizingShapeId = null;
        isInitialized = false;
    }
}