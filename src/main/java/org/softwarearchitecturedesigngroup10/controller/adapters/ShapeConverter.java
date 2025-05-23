package org.softwarearchitecturedesigngroup10.controller.adapters;

import javafx.scene.shape.Shape;
import org.softwarearchitecturedesigngroup10.model.shapesdata.EllipseData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.LineData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.RectangleData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

import java.util.HashMap;
import java.util.Map;

public class ShapeConverter {
    private final Map<Class<? extends ShapeData>, ShapeAdapterInterface> adapters = new HashMap<>();

    public ShapeConverter() {
        adapters.put(LineData.class, new LineAdapter());
        adapters.put(RectangleData.class, new RectangleAdapter());
        adapters.put(EllipseData.class, new EllipseAdapter());
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
