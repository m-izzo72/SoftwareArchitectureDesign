package org.softwarearchitecturedesigngroup10.controller.adapters;

import javafx.scene.Node;
import javafx.scene.shape.Shape;
import org.softwarearchitecturedesigngroup10.model.shapesdata.*;
import org.softwarearchitecturedesigngroup10.model.shapesdata.composite.GroupedShapeData;

import java.util.HashMap;
import java.util.Map;

public class ShapeConverter {
    private final Map<Class<? extends ShapeData>, ShapeAdapterInterface> adapters = new HashMap<>();

    public ShapeConverter() {
        adapters.put(LineData.class, new LineAdapter());
        adapters.put(RectangleData.class, new RectangleAdapter());
        adapters.put(EllipseData.class, new EllipseAdapter());
        adapters.put(PolygonData.class, new PolygonAdapter());
        adapters.put(TextData.class, new TextAdapter());
    }

    public Node convert(ShapeData data) {
        if (data instanceof GroupedShapeData groupData) {
            javafx.scene.Group fxGroup = new javafx.scene.Group();
            for (ShapeData childData : groupData.getChildren()) {
                Node fxChildNode = convert(childData);
                if (fxChildNode != null) {
                    //fxChildNode.setMouseTransparent(true);
                    fxGroup.getChildren().add(fxChildNode);
                }
            }
            return fxGroup;
        } else {
            ShapeAdapterInterface adapter = adapters.get(data.getClass());
            if (adapter != null) {
                return adapter.toFXShape(data);
            } else {
                throw new IllegalArgumentException("No suitable adapter found for " + data.getClass().getName());
            }
        }
    }
}
