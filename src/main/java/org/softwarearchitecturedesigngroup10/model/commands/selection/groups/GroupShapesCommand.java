package org.softwarearchitecturedesigngroup10.model.commands.selection.groups;
import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.commands.Command;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.composite.GroupedShapesData;

import java.util.LinkedHashMap;
import java.util.Map;


public class GroupShapesCommand implements Command {
    private final CanvasModel receiver;
    private final LinkedHashMap<String, ShapeData> shapesBeforeGroup;
    private String createdGroupId;

    public GroupShapesCommand(CanvasModel receiver) {
        this.receiver = receiver;
        this.shapesBeforeGroup = new LinkedHashMap<>();
        for (Map.Entry<String, ShapeData> entry : receiver.getSelectedShapes().entrySet()) {
            shapesBeforeGroup.put(entry.getKey(), entry.getValue().clone());
        }
    }

    @Override
    public void execute() {
        receiver.groupSelectedShapes();
        for (Map.Entry<String, ShapeData> entry : receiver.getSelectedShapes().entrySet()) {
            if (entry.getValue() instanceof GroupedShapesData) {
                createdGroupId = entry.getKey();
                break;
            }
        }
    }

    @Override
    public void undo() {
        if (createdGroupId != null) {
            receiver.deselectAllShapes();
            ShapeData groupToUngroup = receiver.getShapes().get(createdGroupId);
            if (groupToUngroup != null) {
                groupToUngroup.setSelected(true);
                receiver.getSelectedShapes().put(createdGroupId, groupToUngroup);
                receiver.ungroupSelectedShapes();
            }

            //List<String> originalIds = new ArrayList<>(shapesBeforeGroup.keySet());
            receiver.getShapes().remove(createdGroupId);
            for(Map.Entry<String, ShapeData> entry : shapesBeforeGroup.entrySet()){
                ShapeData originalShape = entry.getValue().clone();
                receiver.addShapeByKeepingKeys(entry.getKey(), originalShape);
                if(originalShape.isSelected()){
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