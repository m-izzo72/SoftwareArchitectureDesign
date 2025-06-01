package org.softwarearchitecturedesigngroup10.controller.adapters;

import javafx.scene.shape.Shape;
import org.softwarearchitecturedesigngroup10.model.shapesdata.*;

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

    public Shape convert(ShapeData data) {
        ShapeAdapterInterface adapter = adapters.get(data.getClass()); // Gets the adapter for the given data type by checking its class
        if (adapter != null) {
            return adapter.toFXShape(data);
        } else {
            throw new IllegalArgumentException("No suitable adapter found");
        }
    }
}
