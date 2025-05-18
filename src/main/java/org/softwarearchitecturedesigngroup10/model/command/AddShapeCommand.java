package org.softwarearchitecturedesigngroup10.model.command;

import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

public class AddShapeCommand implements Command {
    private CanvasModel receiver; // Il CanvasModel che eseguirà l'azione
    private ShapeData shapeToAdd;      // La forma da aggiungere

    public AddShapeCommand(CanvasModel receiver, ShapeData shapeToAdd) {
        if (receiver == null) {
            throw new IllegalArgumentException("Il receiver (CanvasModelInterface) non può essere nullo.");
        }
        if (shapeToAdd == null) {
            throw new IllegalArgumentException("ShapeData da aggiungere non può essere nullo.");
        }
        this.receiver = receiver;
        this.shapeToAdd = shapeToAdd;
    }

    @Override
    public void execute() {
        receiver.addShape(shapeToAdd);
        // Il CanvasModel, dopo aver aggiunto la forma, dovrebbe chiamare notifyObservers()
        // per aggiornare la vista tramite il pattern Observer.
        //System.out.println("AddShapeCommand: Eseguito - Aggiunta forma ID: " + shapeToAdd.getId());
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


