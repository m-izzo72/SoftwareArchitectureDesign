package org.softwarearchitecturedesigngroup10.controller.states;

import javafx.scene.input.MouseEvent;
import org.softwarearchitecturedesigngroup10.controller.Controller;

public class IdleState implements State {

        @Override
        public void handleMousePressed(MouseEvent event, Controller context) {
            event.consume();
        }

        @Override
        public void handleMouseReleased(MouseEvent event, Controller context) {
            event.consume();
        }

        @Override
        public void handleMouseDragged(MouseEvent event, Controller context) {
            event.consume();
        }

        @Override
        public void enterState(Controller context) {
            context.setFactory(null);
            context.getSelectToolButton().setSelected(false);
            context.getLineButton().setSelected(false);
            context.getRectangleButton().setSelected(false);
            context.getEllipseButton().setSelected(false);

        }

        @Override
        public void exitState(Controller context) {
        }
}
