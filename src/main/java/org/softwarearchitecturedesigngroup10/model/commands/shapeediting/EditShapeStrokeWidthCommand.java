package org.softwarearchitecturedesigngroup10.model.commands.shapeediting;

import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.commands.Command;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

import java.util.LinkedHashMap;

public class EditShapeStrokeWidthCommand implements Command {

    private final CanvasModel receiver;
    private final double newStrokeWidth;
    private final LinkedHashMap<String, ShapeData> previousState;

    public EditShapeStrokeWidthCommand(CanvasModel receiver, double newStrokeWidth) {
        this.receiver = receiver;
        this.newStrokeWidth = newStrokeWidth;
        this.previousState = new LinkedHashMap<>();
        getPreviousState();
    }

    private void getPreviousState() {
        receiver.getShapes().forEach((key, value) -> previousState.put(key, value.clone()));
    }

    @Override
    public void execute() {
        receiver.editShapesStrokeWidth(newStrokeWidth);
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
