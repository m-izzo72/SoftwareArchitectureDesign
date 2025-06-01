package org.softwarearchitecturedesigngroup10.model.commands.groups;// package org.softwarearchitecturedesigngroup10.model.commands;
// Importa CanvasModel, Command, ShapeData, GroupedShapeData (se necessario per lo stato)
import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.commands.Command;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.composite.GroupedShapeData;

import java.util.List; // Per previousState
import java.util.ArrayList; // Per previousState
import java.util.Map;

public class UngroupShapesCommand implements Command {
    private final CanvasModel receiver;
    private GroupedShapeData groupThatWasUngrouped; // Il gruppo prima di essere separato
    private String originalGroupId;
    private List<String> idsOfUngroupedChildren;


    public UngroupShapesCommand(CanvasModel receiver) {
        this.receiver = receiver;
        // Salva lo stato del gruppo SELEZIONATO prima della separazione
        for (Map.Entry<String, ShapeData> entry : receiver.getSelectedShapes().entrySet()) {
            if (entry.getValue() instanceof GroupedShapeData) {
                this.originalGroupId = entry.getKey();
                this.groupThatWasUngrouped = (GroupedShapeData) entry.getValue().clone(); // Salva un clone del gruppo
                break; // Supponiamo di separare un solo gruppo alla volta
            }
        }
    }

    @Override
    public void execute() {
        if (groupThatWasUngrouped == null) return; // Nessun gruppo da separare

        // Salva gli ID dei figli che verranno creati (o meglio, le loro rappresentazioni)
        // L'operazione di ungroup corrente in CanvasModel crea nuovi UUID per i figli.
        // Per l'undo, dobbiamo sapere quali forme sono state create.
        // Potremmo modificare ungroupSelectedShapes per restituire gli ID dei nuovi figli.
        // Oppure, prima dell'execute, calcoliamo la differenza di forme.

        // Temporaneamente, per l'undo, assumiamo che l'undo di groupSelectedShapes sia il "ri-raggruppamento".
        receiver.ungroupSelectedShapes(); // Esegue l'operazione

        // Dopo l'execute, dobbiamo trovare gli ID dei figli creati per poterli rimuovere nell'undo.
        // Questo è complesso. L'ideale sarebbe che `ungroupSelectedShapes` non solo creasse
        // i figli ma tracciasse anche quali sono stati creati da quale gruppo per l'undo.
        // Una semplificazione: l'undo di `ungroup` rimuoverà tutti i figli attuali (che sono appena stati creati)
        // e ri-aggiungerà il `groupThatWasUngrouped`.

        // Identificare i figli creati dall'operazione di ungroup.
        // Questo è un modo euristico:
        idsOfUngroupedChildren = new ArrayList<>();
        List<ShapeData> childrenData = groupThatWasUngrouped.getChildren();
        int numChildren = childrenData.size();
        // Prendiamo gli ultimi 'numChildren' ID aggiunti, assumendo che siano quelli creati.
        // Questo è fragile. CanvasModel.ungroupSelectedShapes dovrebbe idealmente restituire questi ID.
        List<String> allShapeIds = new ArrayList<>(receiver.getShapes().keySet());
        if (allShapeIds.size() >= numChildren) {
            idsOfUngroupedChildren.addAll(allShapeIds.subList(allShapeIds.size() - numChildren, allShapeIds.size()));
        }

    }

    @Override
    public void undo() {
        if (groupThatWasUngrouped == null || originalGroupId == null) return;

        // 1. Rimuovi i figli che sono stati creati dall'operazione di ungroup.
        if (idsOfUngroupedChildren != null) {
            for (String childId : idsOfUngroupedChildren) {
                receiver.getShapes().remove(childId);
                receiver.getSelectedShapes().remove(childId); // Rimuovi anche dalla selezione
            }
        }

        // 2. Ri-aggiungi il gruppo originale.
        receiver.addShapeByKeepingKeys(originalGroupId, groupThatWasUngrouped.clone()); // Aggiungi un clone per mantenere lo stato originale

        // 3. Seleziona il gruppo ripristinato.
        receiver.deselectAllShapes(); // Deseleziona tutto
        ShapeData restoredGroup = receiver.getShapes().get(originalGroupId);
        if (restoredGroup != null) {
            restoredGroup.setSelected(true);
            receiver.getSelectedShapes().put(originalGroupId, restoredGroup);
        }

        receiver.notifyObservers();
    }

    @Override
    public boolean isUndoable() {
        return groupThatWasUngrouped != null; // È annullabile solo se un gruppo è stato effettivamente identificato
    }
}