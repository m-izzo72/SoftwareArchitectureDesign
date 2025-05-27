package org.softwarearchitecturedesigngroup10.controller.states;

import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import org.softwarearchitecturedesigngroup10.controller.Controller;
import javafx.scene.shape.Shape;
import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.commands.selection.DeselectAllShapeCommand;
import org.softwarearchitecturedesigngroup10.model.commands.selection.SelectShapeCommand;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;
import org.softwarearchitecturedesigngroup10.view.CanvasView;

public class SelectionState implements State {

    private boolean isPressOnSelectedShapes = false;
    private double pressX, pressY;
    private String clickedShapeId = null;
    private static final double DRAG_THRESHOLD = 3.0;

    @Override
    public void handleMousePressed(MouseEvent event, Controller context) {
        Object target = event.getTarget();
        System.out.println("SelectionState - Clicked on: " + target); // <-- AGGIUNGI QUESTO
        if (target instanceof Shape) {
            System.out.println("  ID: " + ((Shape) target).getId()); // <-- AGGIUNGI QUESTO
            System.out.println("  UserData: " + ((Shape) target).getUserData()); // <-- AGGIUNGI QUESTO
        }
        CanvasModel model = context.getCanvasModel();
        pressX = event.getX();
        pressY = event.getY();
        isPressOnSelectedShapes = false;
        clickedShapeId = null;

        if (target instanceof Rectangle && ((Rectangle) target).getId() != null && ((Rectangle) target).getId().equals(CanvasView.RESIZE_HANDLE_ID)) {
            Rectangle handle = (Rectangle) target;
            String shapeId = (String) handle.getUserData();
            if (shapeId != null) {
                ResizingState resizingState = (ResizingState) context.getResizingState();
                boolean initOk = resizingState.initializeResize(model, shapeId, pressX, pressY);
                if (initOk) {
                    context.setCurrentState(resizingState);
                }
            }
            event.consume();
            return;
        }

        if (target instanceof Shape shape && shape.getId() != null && !shape.getId().equals(CanvasView.RESIZE_HANDLE_ID)) {
            ShapeData data = model.getShapes().get(shape.getId());
            if (data != null) {
                clickedShapeId = shape.getId();
                isPressOnSelectedShapes = data.isSelected();
            }
        } else if (target == context.getCanvas()) {
            new DeselectAllShapeCommand(model).execute();
            event.consume();
            return;
        }

        if (target instanceof Shape && !isPressOnSelectedShapes && clickedShapeId != null) {
            if (!event.isShiftDown()) {
                new DeselectAllShapeCommand(model).execute();
            }
            new SelectShapeCommand(model, clickedShapeId).execute();
            isPressOnSelectedShapes = true;

        } else if (target instanceof Shape && isPressOnSelectedShapes && clickedShapeId != null) {
            // Se clicco su una forma già selezionata, non faccio nulla qui,
            // aspetto il drag o il release
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

            if (Math.sqrt(totalDx * totalDx + totalDy * totalDy) > DRAG_THRESHOLD) {

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
            CanvasModel model = context.getCanvasModel();
            new SelectShapeCommand(model, clickedShapeId).execute();
        }

        isPressOnSelectedShapes = false;
        clickedShapeId = null;
        event.consume();
    }

    @Override
    public void enterState(Controller context) {
        context.setFactory(null);
        context.getSelectToolButton().setSelected(true);
        isPressOnSelectedShapes = false;
        clickedShapeId = null;
        context.getCanvas().setCursor(Cursor.DEFAULT);
        context.getCanvasModel().notifyObservers();
    }

    @Override
    public void exitState(Controller context) {
        context.getSelectToolButton().setSelected(false);
        context.getCanvasView().getResizeHandle().setVisible(false);
    }
}