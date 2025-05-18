package org.softwarearchitecturedesigngroup10.model.command;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

import java.util.ArrayList;
import java.util.List;

public class DeleteShapeCommand implements Command {
    private CanvasModel receiver;
    private HashMap<String, ShapeData> shapesToDelete;


    public DeleteShapeCommand(CanvasModel receiver, HashMap<String, ShapeData> shapeToDelete) {
        this.receiver = receiver;
        this.shapesToDelete = shapesToDelete;
    }

    @Override
    public void execute() {
    }

    @Override
    public void undo() {
        // Aggiungi di nuovo la forma sia alla lista che al canvas
//        if (originalIndex >= 0 && originalIndex <= shapes.size()) {
//            shapes.add(originalIndex, shapeToDelete);
//        } else {
//            shapes.add(shapeToDelete);
//        }
        //canvas.getChildren().add(shapesToDelete);
    }
}
