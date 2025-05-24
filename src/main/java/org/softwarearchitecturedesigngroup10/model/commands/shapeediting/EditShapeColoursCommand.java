package org.softwarearchitecturedesigngroup10.model.commands.shapeediting;

import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.commands.Command;

public class EditShapeColoursCommand implements Command {

    private final CanvasModel receiver;
    private final String newFillColor;
    private final String newStrokeColor;

    public EditShapeColoursCommand(CanvasModel canvasModel, String newFillColor, String newStrokeColor) {
        this.receiver = canvasModel;
        this.newFillColor = newFillColor;
        this.newStrokeColor = newStrokeColor;
    }

    @Override
    public void execute() {
        receiver.changeShapesColours(newFillColor, newStrokeColor);
    }

//    @Override
//    public void undo() {
//
//    }
}
