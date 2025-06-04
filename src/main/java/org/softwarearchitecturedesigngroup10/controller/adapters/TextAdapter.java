package org.softwarearchitecturedesigngroup10.controller.adapters;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.TextData;

public class TextAdapter implements ShapeAdapterInterface {
    @Override
    public Shape toFXShape(ShapeData data) {
        if (!(data instanceof TextData td)) {
            throw new IllegalArgumentException();
        }
        Text fxText = new Text(td.getX(), td.getY(), td.getText());

        fxText.setFont(Font.font(td.getFontFamily(), td.getFontSize()));

        if (td.getFillColor() != null && !td.getFillColor().trim().isEmpty()) {
            try {
                fxText.setFill(Color.valueOf(td.getFillColor()));
            } catch (IllegalArgumentException e) {
                fxText.setFill(Color.BLACK);
            }
        } else {
            fxText.setFill(Color.BLACK);
        }

        if (td.getStrokeColor() != null && !td.getStrokeColor().trim().isEmpty() && td.getStrokeWidth() > 0) {
            try {
                fxText.setStroke(Color.valueOf(td.getStrokeColor()));
                fxText.setStrokeWidth(td.getStrokeWidth());
            } catch (IllegalArgumentException e) {
                fxText.setStroke(null);
            }
        } else {
            fxText.setStroke(null);
        }

        fxText.setRotate(td.getRotationAngle());

        fxText.setRotate(0);
        fxText.setScaleY(1.0); fxText.setScaleX(1.0);
        if(td.isYFlipped() && td.isXFlipped()) {
            fxText.setRotate(td.getRotationAngle());
            fxText.setScaleY(-1); fxText.setScaleX(-1);
        } else if(td.isYFlipped() && !td.isXFlipped()) {
            fxText.setRotate(-td.getRotationAngle());
            fxText.setScaleY(-1); fxText.setScaleX(1);
        } else if(td.isXFlipped() && !td.isYFlipped()) {
            fxText.setRotate(-td.getRotationAngle());
            fxText.setScaleY(1);
            fxText.setScaleX(-1);
        } else {
            fxText.setRotate(td.getRotationAngle());
            fxText.setScaleY(1); fxText.setScaleX(1);
        }

        return fxText;
    }
}