package org.softwarearchitecturedesigngroup10.model.commands;

import org.softwarearchitecturedesigngroup10.model.CanvasModel;

public class ChangeShapeFillColorCommand implements Command {

    private final CanvasModel receiver;
    private final String newFillColor;
    private final String newStrokeColor;

    public ChangeShapeFillColorCommand(CanvasModel canvasModel, String newFillColor, String newStrokeColor) {
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
