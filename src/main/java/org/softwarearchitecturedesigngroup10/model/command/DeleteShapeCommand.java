package org.softwarearchitecturedesigngroup10.model.command;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class DeleteShapeCommand implements Command {
    private final Pane canvas;
    private final ArrayList<Shape> shapesToDelete;
    private int originalIndex;


    public DeleteShapeCommand(Pane canvas, ArrayList<Shape> shapesToDelete) {
        this.canvas = canvas;
        this.shapesToDelete = shapesToDelete;
    }

    @Override
    public void execute() {
        // Removes shapes from canvas
        for(Shape shape : shapesToDelete) canvas.getChildren().remove(shape);
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
