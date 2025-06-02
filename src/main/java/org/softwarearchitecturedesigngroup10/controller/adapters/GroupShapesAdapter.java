package org.softwarearchitecturedesigngroup10.controller.adapters;

import javafx.scene.Group;
import javafx.scene.shape.Shape;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.composite.GroupedShapesData;

public class GroupShapesAdapter implements ShapeAdapterInterface{
    ShapeConverter converter;

    public GroupShapesAdapter(ShapeConverter converter) {
        this.converter = converter;
    }

    @Override
    public Group toFXShape(ShapeData data) {
        Group group = new Group();
        ((GroupedShapesData) data).getChildren().forEach(shape -> {
            group.getChildren().add(converter.convert(shape));
        });

        return group;
    }
}
