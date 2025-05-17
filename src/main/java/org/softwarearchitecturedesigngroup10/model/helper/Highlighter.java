package org.softwarearchitecturedesigngroup10.model.helper;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.util.List;

public class Highlighter {

    public static void highlightShape(Shape shape) {
        DropShadow highlightEffect = new DropShadow();

        highlightEffect.setColor(Color.GOLD);
        highlightEffect.setRadius(15);
        highlightEffect.setSpread(0.3);

        highlightEffect.setOffsetX(0);
        highlightEffect.setOffsetY(0);

        shape.setEffect(highlightEffect);
    }

    public static void unHighlightShape(Shape shape) {
        shape.setEffect(null);
    }

    public static void unHighlightShapes(List<Shape> shapes) {
        for (Shape shape : shapes) shape.setEffect(null);
    }
}
