package org.softwarearchitecturedesigngroup10.model.factories;

import javafx.scene.paint.Color;
import org.softwarearchitecturedesigngroup10.model.shapes.Line;

public class LineFactory extends Line {

    public LineFactory(double x1, double y1, double x2, double y2, Color borderColor, boolean isSelected) {
        super(x1, y1, x2, y2, borderColor, isSelected);
    }
}
