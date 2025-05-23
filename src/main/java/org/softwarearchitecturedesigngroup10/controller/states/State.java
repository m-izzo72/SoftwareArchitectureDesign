package org.softwarearchitecturedesigngroup10.controller.states;

import javafx.scene.input.MouseEvent;
import org.softwarearchitecturedesigngroup10.controller.Controller;

public interface State {
    void handleMousePressed(MouseEvent event, Controller context);

    void handleMouseReleased(MouseEvent event, Controller context);

    void handleMouseDragged(MouseEvent event, Controller context);

    void enterState(Controller context);

    void exitState(Controller context);
}
