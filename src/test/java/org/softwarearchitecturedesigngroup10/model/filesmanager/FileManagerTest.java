package org.softwarearchitecturedesigngroup10.model.filesmanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.softwarearchitecturedesigngroup10.model.shapesdata.LineData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

class FileManagerTest {

    public FileManager fileManager;
    private File testFile;
    @BeforeEach
    void setup() throws IOException {
        fileManager = new FileManager();
        testFile = File.createTempFile("test", ".tmp");
    }

    @Test
    void testSaveAndLoad() throws IOException, ClassNotFoundException {
        LinkedHashMap<String, ShapeData> shapeToSave = new LinkedHashMap<>();
        LineData lineData = new LineData();
        lineData.setX(1);
        lineData.setY(2);
        lineData.setEndX(3);
        lineData.setEndY(4);
        shapeToSave.put("line1", lineData);

        // Saving on temp file
        fileManager.save(shapeToSave, testFile);
        //File loading
        LinkedHashMap<String, ShapeData> shapeToLoad = fileManager.load(testFile);

        assertNotNull(shapeToLoad);
        assertEquals(1, shapeToLoad.size());
        assertTrue(shapeToLoad.containsKey("line1"));
        ShapeData loadedShapeData = shapeToLoad.get("line1");
        assertTrue(loadedShapeData instanceof LineData);

        LineData loadedLine = (LineData) loadedShapeData;
        assertEquals(1, loadedLine.getX());
        assertEquals(2, loadedLine.getY());
        assertEquals(3, loadedLine.getEndX());
        assertEquals(4, loadedLine.getEndY());
    }

    @Test
    void testSaveWithNullFileThrows() {
        LinkedHashMap<String, ShapeData> shapes = new LinkedHashMap<>();
        assertThrows(IllegalArgumentException.class, () -> fileManager.save(shapes, null));
    }

    @Test
    void testLoadWithNullFileThrows() {
        assertThrows(IllegalArgumentException.class, () -> fileManager.load(null));
    }

    @Test
    void testLoadWithNonexistentFileThrows() {
        File nonExistentFile = new File("file_che_non_esiste.tmp");
        assertThrows(IOException.class, () -> fileManager.load(nonExistentFile));
    }
}