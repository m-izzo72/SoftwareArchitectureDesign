package org.softwarearchitecturedesigngroup10.model.shapes;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LineTest {

    @Test
    /* This test case asserts that default coords upon instancing a new Line are equal to (0, 0) */
    void testDefaultLineCoordinates() {
        Line line = new Line();
        assertEquals(0.0, line.getX(), "Default X should be 0.0");
        assertEquals(0.0, line.getY(), "Default Y should be 0.0");
        // Nota: la classe Line attualmente non ha modo di impostare o recuperare x2 e y2
        // e nel metodo draw usa valori fissi. Potresti voler aggiungere logica per questo.
    }

    @Test
    /* This test case asserts that setters and getters work properly */
    void testSetAndGetX2Y2() {
        Line line = new Line();
        line.setX2(100.5);
        line.setY2(50.2);
        assertEquals(100.5, line.getX2(), "X2 should be set correctly");
        assertEquals(50.2, line.getY2(), "Y2 should be set correctly");
    }

}
