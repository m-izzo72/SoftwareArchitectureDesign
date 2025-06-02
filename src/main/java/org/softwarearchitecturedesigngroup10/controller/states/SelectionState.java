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

        Node current = (Node) target;
        String rootShapeId = null;
        ShapeData rootShapeData = null;

        while (current != null) {
            if (current.getId() != null && model.getShapes().containsKey(current.getId())) {
                // Abbiamo trovato un Node il cui ID è una chiave nella mappa 'shapes' del modello.
                // Questo è il nostro ShapeData (o GroupedShapeData) di interesse.
                rootShapeId = current.getId();
                rootShapeData = model.getShapes().get(rootShapeId);
                break;
            }
            current = current.getParent(); // Risali l'albero della scena
            if (current == context.getCanvas()) break; // Non andare oltre il canvas
        }
        System.out.println("SelectionState - Identified rootShapeId: " + rootShapeId); // Log importante

        if (rootShapeId != null && rootShapeData != null) {
            clickedShapeId = rootShapeId; // ID della ShapeData (potrebbe essere un gruppo)
            isPressOnSelectedShapes = rootShapeData.isSelected();

            if (!isPressOnSelectedShapes) { // Se si clicca su una forma/gruppo non selezionato
                if (!event.isShiftDown()) {
                    // Deseleziona tutto SOLO se non si preme Shift
                    new DeselectAllShapeCommand(model).execute(); // Questo notificherà gli observer e aggiornerà selectedShapes
                }
                // Seleziona la forma/gruppo cliccato
                new SelectShapeCommand(model, clickedShapeId).execute(); // Questo aggiornerà lo stato di selezione e notificherà
                isPressOnSelectedShapes = true; // Ora è considerato "premuto su una forma selezionata" per il drag
            } else { // Se si clicca su una forma/gruppo GIÀ selezionato
                if (event.isShiftDown()) {
                    // Deseleziona la forma/gruppo se Shift è premuto (toggle)
                    new SelectShapeCommand(model, clickedShapeId).execute();
                    // isPressOnSelectedShapes diventerà false se viene deselezionato
                    isPressOnSelectedShapes = model.getShapes().get(clickedShapeId).isSelected();

                }
                // Altrimenti (già selezionato, no Shift), non fare nulla qui,
                // aspetta il drag o il release. isPressOnSelectedShapes rimane true.
            }

        } else if (target == context.getCanvas()) { // Clic sul canvas vuoto
            new DeselectAllShapeCommand(model).execute();
        }
        // Altri casi potrebbero non fare nulla o consumare l'evento

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