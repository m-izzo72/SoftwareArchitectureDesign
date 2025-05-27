// src/main/java/org/softwarearchitecturedesigngroup10/model/commands/MoveShapesCommand.java
package org.softwarearchitecturedesigngroup10.model.commands.selection;

import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.commands.Command;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData; // Assicurati che sia importato

import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class MoveShapesCommand implements Command {

    private final CanvasModel receiver;
    private final ArrayList<String> shapeIds;
    private final double dx, dy;
    //private final LinkedHashMap<String, ShapeData> previousState;

    public MoveShapesCommand(CanvasModel receiver, List<String> shapeIdsToMove, double dx, double dy) {
        this.receiver = receiver;
        this.shapeIds = new ArrayList<>(shapeIdsToMove); // Crea una copia per sicurezza
        this.dx = dx;
        this.dy = dy;
        //getPreviousState();
    }

    //private void getPreviousState() {
    //    receiver.getShapes().forEach((key, value) -> previousState.put(key, value.clone()));
    //}

    @Override
    public void execute() {
        System.out.println(shapeIds);
        // Move operation is managed by the controller, which updates the shape every time it changes its position.
        // A MoveShapesCommand is created when the shape has stopped moving, so there's no need to execute the command
        // as it is only necessary to undo the move

        // A previous state (like in other commands) can't be saved since the "shapes" map is updated every time a shape has been moved
    }

    @Override
    public void undo() {
        //receiver.clear();
        //previousState.forEach((key, value) -> receiver.addShape(value));
        //System.out.println("Trying to undo the move of shapes: " + shapeIds + " with delta: dx=" + dx + ", dy=" + dy + "");
        this.receiver.moveShapeDataByIDs(this.shapeIds, -this.dx, -this.dy);
//        System.out.println("MoveShapesCommand undo: dx=" + (-dx) + ", dy=" + (-dy) + " for shapes: " + shapeIds);
    }

    @Override
    public boolean isUndoable() {
        return true;
    }
}