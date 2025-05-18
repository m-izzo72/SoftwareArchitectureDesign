package org.softwarearchitecturedesigngroup10.model.filesmanager;

import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

import java.io.*;
import java.util.LinkedHashMap;

public class FileManager implements FileManagerInterface {
    @Override
    public void save(LinkedHashMap<String, ShapeData> shapesToSave, File file) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException();
        }
        try (FileOutputStream fileOut = new FileOutputStream(file);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(shapesToSave);
            System.out.println();
        }
    }

    public LinkedHashMap<String, ShapeData> load(File file) throws IOException, ClassNotFoundException {
        if (file == null) {
            throw new IllegalArgumentException();
        }
        if (!file.exists() || !file.canRead()) {
            throw new IOException();
        }

        LinkedHashMap<String, ShapeData> loadedShapes;
        try (FileInputStream fileIn = new FileInputStream(file);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {

            Object obj = objectIn.readObject();
            if (obj instanceof LinkedHashMap) {
                loadedShapes = (LinkedHashMap<String, ShapeData>) obj;
            } else {
                throw new IOException();
            }
        }
        return loadedShapes;
    }
}
