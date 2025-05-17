package org.softwarearchitecturedesigngroup10.model.command;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

public class DeleteShapeCommand implements Command {
    private Pane canvas;
    private Shape shapeToDelete;
    private int originalIndex; // Indice originale per ripristinare la posizione


    public DeleteShapeCommand(Pane canvas, Shape shapeToDelete) {
        this.canvas = canvas;
        this.shapeToDelete = shapeToDelete;
    }

    @Override
    public void execute() {
        // Rimuovi la forma sia dalla lista che dal canvas
        //shapes.remove(shapeToDelete);
        canvas.getChildren().remove(shapeToDelete);
    }

    @Override
    public void undo() {
        // Aggiungi di nuovo la forma sia alla lista che al canvas
//        if (originalIndex >= 0 && originalIndex <= shapes.size()) {
//            shapes.add(originalIndex, shapeToDelete);
//        } else {
//            shapes.add(shapeToDelete);
//        }
        canvas.getChildren().add(shapeToDelete);
    }
}
