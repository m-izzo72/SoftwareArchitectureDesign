package org.softwarearchitecturedesigngroup10.model;

import org.softwarearchitecturedesigngroup10.model.observer.ModelObserver;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

import java.io.File;
import java.io.IOException;

public interface CanvasModelInterface {
    public void addObserver(ModelObserver observer);

    public void removeObserver(ModelObserver observer);

    public void notifyObservers();

    public void addShape(ShapeData shapeToAdd);

    public void deleteShapes(HashMap<String, ShapeData> shapesToDelete);

    void save(File file) throws IOException;

    void load(File file) throws IOException, ClassNotFoundException;
}
