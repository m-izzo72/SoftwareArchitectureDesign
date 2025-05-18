package org.softwarearchitecturedesigngroup10.model.filesmanager;

import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

import java.io.File;
import java.util.HashMap;

public interface FileManagerInterface {
    public void save(HashMap<String, ShapeData> shapesToSave);

    public void load(File file);
}
