package org.softwarearchitecturedesigngroup10.view.helper;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class Highlighter {

    public static void highlightShape(Shape shape) {
        if(shape.getEffect() == null) {
            DropShadow highlightEffect = new DropShadow();

            highlightEffect.setColor(Color.GOLD);
            highlightEffect.setRadius(15);
            highlightEffect.setSpread(0.3);

            highlightEffect.setOffsetX(0);
            highlightEffect.setOffsetY(0);

            shape.setEffect(highlightEffect);
        } else {
            shape.setEffect(null);
        }
    }

    public static void unhighlightShape(Shape shape) {
        shape.setEffect(null);
    }
}
