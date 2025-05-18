package org.softwarearchitecturedesigngroup10.model.filesmanager;

import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

public interface FileManagerInterface {

    void save(LinkedHashMap<String, ShapeData> shapesToSave, File file) throws IOException;

    LinkedHashMap<String, ShapeData> load(File file) throws IOException, ClassNotFoundException;
}
