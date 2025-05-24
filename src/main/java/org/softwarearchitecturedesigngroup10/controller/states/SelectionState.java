package org.softwarearchitecturedesigngroup10.controller.states;

import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import org.softwarearchitecturedesigngroup10.controller.Controller;
import javafx.scene.shape.Shape;
import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.commands.selection.DeselectAllShapeCommand;
import org.softwarearchitecturedesigngroup10.model.commands.selection.SelectShapeCommand;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

public class SelectionState implements State {

    private boolean isPressOnSelectedShapes = false;
    private double pressX, pressY;
    private String clickedShapeId = null;
    private static final double DRAG_THRESHOLD = 3.0;

    @Override
    public void handleMousePressed(MouseEvent event, Controller context) {
        Object target = event.getTarget();
        CanvasModel model = context.getCanvasModel();
        pressX = event.getX();
        pressY = event.getY();
        isPressOnSelectedShapes = false;
        clickedShapeId = null;

        if (target instanceof Shape shape ) {
            ShapeData data = model.getShapes().get(shape.getId());
            if (data != null) {
                clickedShapeId = shape.getId();
                isPressOnSelectedShapes = data.isSelected();
            }
        } else if (target == context.getCanvas()) {
            new DeselectAllShapeCommand(model).execute();
        }

        if (target instanceof Shape && !isPressOnSelectedShapes && clickedShapeId != null) {
            if (!event.isShiftDown()) {
                new DeselectAllShapeCommand(model).execute();
            }
            new SelectShapeCommand(model, clickedShapeId).execute();

            isPressOnSelectedShapes = true; //
            clickedShapeId = null; //
            isPressOnSelectedShapes = false; //

        } else if (target == context.getCanvas()) {
            new DeselectAllShapeCommand(model).execute();
        }

        event.consume();
    }


    @Override
    public void handleMouseDragged(MouseEvent event, Controller context) {

        if (isPressOnSelectedShapes && clickedShapeId != null) {
            double currentMouseX = event.getX();
            double currentMouseY = event.getY();
            double totalDx = currentMouseX - pressX;
            double totalDy = currentMouseY - pressY;

            // Checks if the shape has been dragged at least DRAG_THRESHOLD pixels
            if (Math.sqrt(totalDx * totalDx + totalDy * totalDy) > DRAG_THRESHOLD) {

                System.out.println("Drag detected. Transitioning to MovingState.");
                MovingState movingState = (MovingState) context.getMovingState();
                boolean initOk = movingState.initializeMove(context.getCanvasModel(), clickedShapeId, pressX, pressY);

                if (initOk) {
                    context.setCurrentState(movingState);
                    movingState.handleMouseDragged(event, context);
                }

                isPressOnSelectedShapes = false;
                clickedShapeId = null;
            }
        }
        event.consume();
    }

    @Override
    public void handleMouseReleased(MouseEvent event, Controller context) {
        if (isPressOnSelectedShapes && clickedShapeId != null) {
            System.out.println("Click (no drag) on selected shape: " + clickedShapeId + ". Deselecting.");
            CanvasModel model = context.getCanvasModel();
            new SelectShapeCommand(model, clickedShapeId).execute();
        }

        isPressOnSelectedShapes = false;
        clickedShapeId = null;
        event.consume();
    }

    @Override
    public void enterState(Controller context) {
        System.out.println("Entering Selection State");
        context.setFactory(null);
        context.getSelectToolButton().setSelected(true);
        isPressOnSelectedShapes = false;
        clickedShapeId = null;
        context.getCanvas().setCursor(Cursor.DEFAULT);
        //context.getCanvasView().setHandCursorToShapes();
    }

    @Override
    public void exitState(Controller context) {
        System.out.println("Exiting Selection State");
        context.getSelectToolButton().setSelected(false);
        //context.getCanvasView().removeHandCursorToShapes();
    }
}