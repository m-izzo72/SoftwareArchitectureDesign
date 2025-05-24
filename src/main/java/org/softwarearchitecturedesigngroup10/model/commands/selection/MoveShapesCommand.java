// src/main/java/org/softwarearchitecturedesigngroup10/model/commands/MoveShapesCommand.java
package org.softwarearchitecturedesigngroup10.model.commands.selection;

import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.commands.Command;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData; // Assicurati che sia importato

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * Comando per spostare una o più forme e supportare l'annullamento.
 */
public class MoveShapesCommand implements Command { // Implementa Command (con undo)

    private final CanvasModel receiver;
    private final List<String> shapeIds; // ID delle forme che sono state spostate
    private final double dx; // Spostamento orizzontale totale
    private final double dy; // Spostamento verticale totale

    /**
     * Costruttore per MoveShapesCommand.
     * @param receiver Il CanvasModel su cui operare.
     * @param shapeIdsToMove Una mappa delle forme selezionate al momento dell'inizio del drag.
     * Potrebbe essere più semplice passare solo gli ID.
     * @param dx Lo spostamento orizzontale totale.
     * @param dy Lo spostamento verticale totale.
     */
    public MoveShapesCommand(CanvasModel receiver, List<String> shapeIdsToMove, double dx, double dy) {
        if (receiver == null) {
            throw new IllegalArgumentException("CanvasModel (receiver) non può essere nullo.");
        }
        if (shapeIdsToMove == null || shapeIdsToMove.isEmpty()) {
            throw new IllegalArgumentException("La lista degli ID delle forme non può essere nulla o vuota.");
        }
        this.receiver = receiver;
        this.shapeIds = new ArrayList<>(shapeIdsToMove); // Crea una copia per sicurezza
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void execute() {
        // Il movimento è già avvenuto aggiornando direttamente il modello
        // durante il drag per avere un feedback visivo immediato.
        // Quindi, execute() potrebbe non fare nulla o riapplicare il delta se necessario,
        // ma per il nostro flusso attuale, il modello è già aggiornato.
        // Questo comando serve principalmente per l'UNDO.
        // Tuttavia, se il modello NON fosse già aggiornato, qui si farebbe:
        // this.receiver.moveShapesByIds(this.shapeIds, this.dx, this.dy);

        // Poiché il modello è già aggiornato dal drag, execute() è formalmente vuoto.
        // L'importante è che il CommandManager lo registri.
        System.out.println("MoveShapesCommand execute: dx=" + dx + ", dy=" + dy + " for shapes: " + shapeIds);
        // Se vuoi essere sicuro che il modello sia nello stato finale corretto,
        // potresti riapplicare qui lo spostamento, ma assicurati che il modello
        // gestisca questo senza effetti collaterali se lo stato è già quello.
        // Per ora, assumiamo che lo stato del modello sia già corretto dopo il drag.
    }

//    @Override
//    public void undo() {
        // Per annullare, sposta le forme del delta opposto
//        this.receiver.moveShapesByIds(this.shapeIds, -this.dx, -this.dy);
//        System.out.println("MoveShapesCommand undo: dx=" + (-dx) + ", dy=" + (-dy) + " for shapes: " + shapeIds);
//    }
}