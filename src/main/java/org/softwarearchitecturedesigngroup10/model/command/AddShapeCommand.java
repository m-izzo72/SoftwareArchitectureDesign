package org.softwarearchitecturedesigngroup10.model.command;

import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

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
        receiver.addShape(shapeToAdd);
        // Il CanvasModel, dopo aver aggiunto la forma, dovrebbe chiamare notifyObservers()
        // per aggiornare la vista tramite il pattern Observer.
        System.out.println("AddShapeCommand: Eseguito - Aggiunta forma ID: " + shapeToAdd.getId());
    }

    @Override
    public void undo() {
        // Assumiamo che deleteShape possa prendere l'oggetto ShapeData
        // o che CanvasModelInterface abbia un metodo deleteShapeById(String id).
        // Se deleteShape si aspetta l'oggetto esatto, questo va bene.
        // Se si basa sull'ID, potrebbe essere più robusto passare l'ID.
        // receiver.deleteShapes(shapeToAdd); o receiver.deleteShapeById(shapeToAdd.getId());
        // Il CanvasModel, dopo aver rimosso la forma, dovrebbe chiamare notifyObservers().
        // System.out.println("AddShapeCommand: Annullato - Rimossa forma ID: " + shapeToAdd.getId());
    }
}


