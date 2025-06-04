package clipboard;// src/test/java/org/softwarearchitecturedesigngroup10/model/commands/clipboard/ShapesClipboardTest.java

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.softwarearchitecturedesigngroup10.model.commands.clipboard.ShapesClipboard;
import org.softwarearchitecturedesigngroup10.model.shapesdata.LineData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.RectangleData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ShapesClipboardTest {

    private ShapesClipboard shapesClipboard;
    private ShapeData line;
    private ShapeData rectangle;

    @BeforeEach
    void setUp() {
        shapesClipboard = new ShapesClipboard();
        line = new LineData();
        ((LineData)line).setX(10);
        rectangle = new RectangleData();
        ((RectangleData)rectangle).setX(20);

        // Clear static clipboard before each test if it's static in the class
        ShapesClipboard.clipboard.clear();

    }

    @Test
    void constructor_InitializesEmptyClipboardAndOffset() {
        assertTrue(ShapesClipboard.clipboard.isEmpty());
        assertEquals(20, shapesClipboard.getPasteOffset()); // Initial offset after constructor
    }

    @Test
    void copyToClipboard_ClearsPreviousAndCopiesNewShapes() {
        // Add something to clipboard first
        HashMap<String, ShapeData> initialItems = new HashMap<>();
        initialItems.put("rect1", rectangle);
        shapesClipboard.copyToClipboard(initialItems);
        assertEquals(1, ShapesClipboard.clipboard.size());
        assertEquals(0, shapesClipboard.getPasteOffset()); // Offset resets on copy

        HashMap<String, ShapeData> itemsToCopy = new HashMap<>();
        itemsToCopy.put("line1", line);
        shapesClipboard.copyToClipboard(itemsToCopy);

        assertEquals(1, ShapesClipboard.clipboard.size());
        assertTrue(ShapesClipboard.clipboard.stream().anyMatch(s -> s.getX() == line.getX()));
        assertFalse(ShapesClipboard.clipboard.stream().anyMatch(s -> s.getX() == rectangle.getX()));
        assertEquals(0, shapesClipboard.getPasteOffset()); // Offset should be 0 after copy
    }

    @Test
    void copyToClipboard_CopiesClonesNotReferences() {
        HashMap<String, ShapeData> itemsToCopy = new HashMap<>();
        itemsToCopy.put("line1", line);
        shapesClipboard.copyToClipboard(itemsToCopy);

        ShapeData clipboardLine = ShapesClipboard.clipboard.get(0);
        assertNotSame(line, clipboardLine);
        assertEquals(line.getX(), clipboardLine.getX());

        // Modify original, clipboard clone should be unaffected
        ((LineData)line).setX(100);
        assertEquals(10, clipboardLine.getX());
    }

    @Test
    void getClipboard_ReturnsClonedListAndIncrementsOffset() {
        HashMap<String, ShapeData> itemsToCopy = new HashMap<>();
        itemsToCopy.put("line1", line);
        itemsToCopy.put("rect1", rectangle);
        shapesClipboard.copyToClipboard(itemsToCopy); // Offset is 0

        ArrayList<ShapeData> retrievedClipboard1 = shapesClipboard.getClipboard();
        assertEquals(2, retrievedClipboard1.size());
        assertEquals(20, shapesClipboard.getPasteOffset()); // Offset becomes 20

        // Verify cloned content
        ShapeData retrievedLine1 = retrievedClipboard1.stream().filter(s -> s instanceof LineData).findFirst().get();
        assertNotSame(ShapesClipboard.clipboard.get(0), retrievedLine1); // Compare with the actual static clipboard item
        assertEquals(line.getX(), retrievedLine1.getX());


        ArrayList<ShapeData> retrievedClipboard2 = shapesClipboard.getClipboard();
        assertEquals(2, retrievedClipboard2.size());
        assertEquals(40, shapesClipboard.getPasteOffset()); // Offset becomes 40

        // Ensure subsequent gets also return clones
        assertNotSame(retrievedClipboard1.get(0), retrievedClipboard2.get(0));
    }


    @Test
    void getPasteOffset_ReturnsCurrentOffset() {
        assertEquals(20, shapesClipboard.getPasteOffset()); // Initial from constructor
        HashMap<String, ShapeData> itemsToCopy = new HashMap<>();
        itemsToCopy.put("line1", line);
        shapesClipboard.copyToClipboard(itemsToCopy);
        assertEquals(0, shapesClipboard.getPasteOffset()); // Reset by copy

        shapesClipboard.getClipboard(); // Increments offset
        assertEquals(20, shapesClipboard.getPasteOffset());
    }

    @Test
    void copyToClipboard_WithEmptyMap_ClearsClipboard() {
        HashMap<String, ShapeData> itemsToCopy = new HashMap<>();
        itemsToCopy.put("line1", line);
        shapesClipboard.copyToClipboard(itemsToCopy);
        assertFalse(ShapesClipboard.clipboard.isEmpty());

        shapesClipboard.copyToClipboard(new HashMap<>());
        assertTrue(ShapesClipboard.clipboard.isEmpty());
        assertEquals(0, shapesClipboard.getPasteOffset());
    }
}