package org.softwarearchitecturedesigngroup10.model;

import org.softwarearchitecturedesigngroup10.model.observers.ModelObserver;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

import java.io.File;
import java.io.IOException;

public interface CanvasModelInterface {
    void addObserver(ModelObserver observer);

    void notifyObservers();

    void clear();

    void addShape(ShapeData shapeToAdd);

    void deleteShapes();

    void save(File file) throws IOException;

    void load(File file) throws IOException, ClassNotFoundException;
}
