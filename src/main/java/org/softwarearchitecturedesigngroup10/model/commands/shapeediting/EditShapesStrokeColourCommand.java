package org.softwarearchitecturedesigngroup10.model.commands.shapeediting;

import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.commands.Command;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

import java.util.LinkedHashMap;

public class EditShapesStrokeColourCommand implements Command {

    private final CanvasModel receiver;
    private final String newFillColor;
    //private final String newStrokeColor;
    private final LinkedHashMap<String, ShapeData> previousState;

    public EditShapesStrokeColourCommand(CanvasModel canvasModel, String newFillColor) {
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
        receiver.editShapesStrokeColour(newFillColor);
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
