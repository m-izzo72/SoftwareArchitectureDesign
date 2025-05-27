package org.softwarearchitecturedesigngroup10.model.commands.shapeediting;

import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.commands.Command;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

import java.util.LinkedHashMap;

public class EditShapesFillColourCommand implements Command {

    private final CanvasModel receiver;
    private final String newFillColor;
    //private final String newStrokeColor;
    private final LinkedHashMap<String, ShapeData> previousState;

    public EditShapesFillColourCommand(CanvasModel canvasModel, String newFillColor) {
        this.receiver = canvasModel;
        this.newFillColor = newFillColor;
        //this.newStrokeColor = newStrokeColor;
        this.previousState = new LinkedHashMap<>();
        getPreviousState();
    }

    private void getPreviousState() {
        receiver.getShapes().forEach((key, value) -> previousState.put(key, value.clone()));
    }

    @Override
    public void execute() {
        receiver.editShapesFillColour(newFillColor);
    }

    @Override
    public void undo() {
        receiver.clear();
        previousState.forEach(receiver::addShapeByKeepingKeys);

    }

    @Override
    public boolean isUndoable() {
        return true;
    }
}
