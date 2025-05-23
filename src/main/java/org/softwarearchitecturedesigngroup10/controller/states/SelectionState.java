package org.softwarearchitecturedesigngroup10.controller.states;

import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;
import org.softwarearchitecturedesigngroup10.controller.Controller;
import org.softwarearchitecturedesigngroup10.model.commands.DeselectAllShapeCommand;
import org.softwarearchitecturedesigngroup10.model.commands.SelectShapeCommand;



public class SelectionState implements State {
    @Override
    public void handleMousePressed(MouseEvent event, Controller context) {
        Object target = event.getTarget();
        if (target instanceof Shape shape) {
            SelectShapeCommand command = new SelectShapeCommand(context.getCanvasModel(), shape.getId());
            command.execute();
        } else if (target == context.getCanvas()) {
            DeselectAllShapeCommand command = new DeselectAllShapeCommand(context.getCanvasModel());
            command.execute();
        }
        event.consume();
    }

    @Override
    public void handleMouseReleased(MouseEvent event, Controller context) {
        // May be used later for moving shapes
        event.consume();
    }

    @Override
    public void handleMouseDragged(MouseEvent event, Controller context) {
        // Same
        event.consume();
    }

    @Override
    public void enterState(Controller context) {
        context.setFactory(null);
    }

    @Override
    public void exitState(Controller context) {

    }
}
