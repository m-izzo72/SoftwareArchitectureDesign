package org.softwarearchitecturedesigngroup10.controller.states;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import org.softwarearchitecturedesigngroup10.controller.Controller;
import javafx.scene.shape.Shape;
import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.commands.selection.DeselectAllShapeCommand;
import org.softwarearchitecturedesigngroup10.model.commands.selection.SelectShapeCommand;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.composite.GroupedShapesData;
import org.softwarearchitecturedesigngroup10.view.CanvasView;

public class SelectionState implements State {

    private boolean isPressOnSelectedShapes = false;
    private double pressX, pressY;
    private String clickedShapeId = null;
    private static final double DRAG_THRESHOLD = 3.0;

    @Override
    public void handleMousePressed(MouseEvent event, Controller context) {
        Object target = event.getTarget();
        System.out.println("SelectionState - Clicked on: " + target);
        if (target instanceof Shape) {
            System.out.println("  ID: " + ((Shape) target).getId());
            System.out.println("  UserData: " + ((Shape) target).getUserData());
        }
        CanvasModel model = context.getCanvasModel();
        pressX = event.getX();
        pressY = event.getY();
        isPressOnSelectedShapes = false;
        clickedShapeId = null;

        if (target instanceof Rectangle handle && handle.getId() != null && handle.getId().equals(CanvasView.RESIZE_HANDLE_ID)) {
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

        Node current = (Node) target;
        String rootShapeId = null;
        ShapeData rootShapeData = null;

        while (current != null) {
            if (current.getId() != null && model.getShapes().containsKey(current.getId())) {
                rootShapeId = current.getId();
                rootShapeData = model.getShapes().get(rootShapeId);
                break;
            }
            current = current.getParent();
            if (current == context.getCanvas()) break;
        }
        System.out.println("SelectionState - Identified rootShapeId: " + rootShapeId);

        if (rootShapeId != null && rootShapeData != null) {
            clickedShapeId = rootShapeId;
            isPressOnSelectedShapes = rootShapeData.isSelected();

            if (!isPressOnSelectedShapes) {
                if (!event.isShiftDown()) {
                    new DeselectAllShapeCommand(model).execute();
                }
                new SelectShapeCommand(model, clickedShapeId).execute();
                isPressOnSelectedShapes = true;
            } else {
                if (event.isShiftDown()) {
                    new SelectShapeCommand(model, clickedShapeId).execute();
                    isPressOnSelectedShapes = model.getShapes().get(clickedShapeId).isSelected();

                }
            }

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
                if (context.getCanvasModel().getSelectedShapes().get(clickedShapeId) instanceof GroupedShapesData gd)
                    gd.getChildren().forEach(child -> {

                    });

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