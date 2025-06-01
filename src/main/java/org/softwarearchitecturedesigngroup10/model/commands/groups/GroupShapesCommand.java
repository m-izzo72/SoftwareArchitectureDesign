package org.softwarearchitecturedesigngroup10.model.commands.groups;// package org.softwarearchitecturedesigngroup10.model.commands;
// Importa CanvasModel, Command, ShapeData, GroupedShapeData (se necessario per lo stato)
import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.commands.Command;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.composite.GroupedShapeData;

import java.util.LinkedHashMap; // Per previousState
import java.util.Map; // Per previousState
import java.util.List; // Per previousState
import java.util.ArrayList; // Per previousState


public class GroupShapesCommand implements Command {
    private final CanvasModel receiver;
    private LinkedHashMap<String, ShapeData> shapesBeforeGroup; // Contiene le forme che sono state raggruppate
    private String createdGroupId; // ID del gruppo creato, per l'undo

    public GroupShapesCommand(CanvasModel receiver) {
        this.receiver = receiver;
        this.shapesBeforeGroup = new LinkedHashMap<>();
        // Salva lo stato delle forme SELEZIONATE prima del raggruppamento
        for (Map.Entry<String, ShapeData> entry : receiver.getSelectedShapes().entrySet()) {
            shapesBeforeGroup.put(entry.getKey(), entry.getValue().clone());
        }
    }

    @Override
    public void execute() {
        receiver.groupSelectedShapes();
        // Dopo l'esecuzione, identifica l'ID del gruppo appena creato
        // Questo è un po' euristico; potresti dover modificare groupSelectedShapes per restituire l'ID
        // o trovarlo cercando l'unico ShapeData di tipo "Group" selezionato.
        for (Map.Entry<String, ShapeData> entry : receiver.getSelectedShapes().entrySet()) {
            if (entry.getValue() instanceof GroupedShapeData) {
                createdGroupId = entry.getKey();
                break;
            }
        }
    }

    @Override
    public void undo() {
        if (createdGroupId != null) {
            // Simula la selezione del gruppo per l'operazione di ungroup
            receiver.deselectAllShapes();
            ShapeData groupToUngroup = receiver.getShapes().get(createdGroupId);
            if (groupToUngroup != null) {
                groupToUngroup.setSelected(true); // Seleziona il gruppo
                receiver.getSelectedShapes().put(createdGroupId, groupToUngroup); // Aggiungilo ai selezionati
                receiver.ungroupSelectedShapes(); // Ora dovrebbe operare sul gruppo corretto
            }

            // Ripristina le forme originali (se l'ungroup non lo fa esattamente come erano)
            // L'ungroup corrente ricrea i figli con nuovi ID. Se vuoi ripristinare gli ID esatti,
            // dovrai modificare l'undo o l'operazione di ungroup.
            // Per ora, l'ungroup dovrebbe bastare a riportare i figli.
            // Potrebbe essere necessario riselezionare le forme originali.
            List<String> originalIds = new ArrayList<>(shapesBeforeGroup.keySet());
            // Dovresti trovare le forme "equivalenti" dopo l'ungroup e riselezionarle.
            // Questo è complesso perché l'ungroup crea nuovi ID.
            // Una soluzione più robusta per l'undo di group:
            // 1. Rimuovi il 'createdGroupId' da receiver.shapes
            // 2. Riaggiungi 'shapesBeforeGroup' a receiver.shapes
            // 3. Ripristina lo stato di selezione come era in 'shapesBeforeGroup'
            receiver.getShapes().remove(createdGroupId);
            for(Map.Entry<String, ShapeData> entry : shapesBeforeGroup.entrySet()){
                ShapeData originalShape = entry.getValue().clone(); // Clona per sicurezza
                receiver.addShapeByKeepingKeys(entry.getKey(), originalShape);
                if(originalShape.isSelected()){ // Ripristina la selezione
                    receiver.getSelectedShapes().put(entry.getKey(), originalShape);
                }
            }
            receiver.notifyObservers();

        }
    }

    @Override
    public boolean isUndoable() {
        return true;
    }
}
