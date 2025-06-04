package org.softwarearchitecturedesigngroup10.controller.adapters;

import javafx.scene.Node;
import org.softwarearchitecturedesigngroup10.model.shapesdata.*;
import org.softwarearchitecturedesigngroup10.model.shapesdata.composite.GroupedShapesData;

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
        adapters.put(GroupedShapesData.class, new GroupedShapesAdapter(this));
    }

    public Node convert(ShapeData data) {
        ShapeAdapterInterface adapter = adapters.get(data.getClass()); // Gets the adapter for the given data type by checking its class
        if (adapter != null) {
            return adapter.toFXShape(data);
        } else {
            throw new IllegalArgumentException("No suitable adapter found");
        }
    }
}
