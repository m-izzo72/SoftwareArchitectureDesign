package org.softwarearchitecturedesigngroup10.model.commands.selection.groups;
import org.softwarearchitecturedesigngroup10.model.CanvasModel;
import org.softwarearchitecturedesigngroup10.model.commands.Command;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.composite.GroupedShapesData;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class UngroupShapesCommand implements Command {
    private final CanvasModel receiver;
    private GroupedShapesData groupThatWasUngrouped;
    private String originalGroupId;
    private List<String> idsOfUngroupedChildren;


    public UngroupShapesCommand(CanvasModel receiver) {
        this.receiver = receiver;
        for (Map.Entry<String, ShapeData> entry : receiver.getSelectedShapes().entrySet()) {
            if (entry.getValue() instanceof GroupedShapesData) {
                this.originalGroupId = entry.getKey();
                this.groupThatWasUngrouped = (GroupedShapesData) entry.getValue().clone();
                break;
            }
        }
    }

    @Override
    public void execute() {
        if (groupThatWasUngrouped == null) return;
        receiver.ungroupSelectedShapes();

        idsOfUngroupedChildren = new ArrayList<>();
        List<ShapeData> childrenData = groupThatWasUngrouped.getChildren();
        int numChildren = childrenData.size();
        List<String> allShapeIds = new ArrayList<>(receiver.getShapes().keySet());
        if (allShapeIds.size() >= numChildren) {
            idsOfUngroupedChildren.addAll(allShapeIds.subList(allShapeIds.size() - numChildren, allShapeIds.size()));
        }

    }

    @Override
    public void undo() {
        if (groupThatWasUngrouped == null || originalGroupId == null) return;

        if (idsOfUngroupedChildren != null) {
            for (String childId : idsOfUngroupedChildren) {
                receiver.getShapes().remove(childId);
                receiver.getSelectedShapes().remove(childId);
            }
        }

        receiver.addShapeByKeepingKeys(originalGroupId, groupThatWasUngrouped.clone());

        receiver.deselectAllShapes();
        ShapeData restoredGroup = receiver.getShapes().get(originalGroupId);
        if (restoredGroup != null) {
            restoredGroup.setSelected(true);
            receiver.getSelectedShapes().put(originalGroupId, restoredGroup);
        }

        receiver.notifyObservers();
    }

    @Override
    public boolean isUndoable() {
        return true;
    }
}
