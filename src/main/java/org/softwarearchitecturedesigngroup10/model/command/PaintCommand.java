package org.softwarearchitecturedesigngroup10.model.command;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import org.softwarearchitecturedesigngroup10.model.CanvasModel;

import java.util.List;

public class PaintCommand implements Command {
    private Pane canvas;
    //private List<Shape> shapes;
    private Shape shapeToPaint;

    public PaintCommand(Pane canvas, Shape shapeToPaint) {
        this.canvas = canvas;
        this.shapeToPaint = shapeToPaint;
    }

    @Override
    public void execute() {
        // Aggiungi la forma sia alla lista che al canvas
        //canvas..add(shapeToAdd);
        canvas.getChildren().add(shapeToPaint);
    }

    @Override
    public void undo() {
        // Rimuovi la forma sia dalla lista che dal canvas
        //shapes.remove(shapeToPaint);
        canvas.getChildren().remove(shapeToPaint);
    }
}


