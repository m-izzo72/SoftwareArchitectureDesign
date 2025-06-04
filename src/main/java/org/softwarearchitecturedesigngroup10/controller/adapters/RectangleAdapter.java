package org.softwarearchitecturedesigngroup10.controller.adapters;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import org.softwarearchitecturedesigngroup10.model.shapesdata.RectangleData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

public class RectangleAdapter implements ShapeAdapterInterface {
    @Override
    public Shape toFXShape(ShapeData data) {
        if (!(data instanceof RectangleData rd)) {
            throw new IllegalArgumentException("Invalid data type for RectangleAdapter");
        }
        Rectangle rect = new Rectangle(rd.getX(), rd.getY(), rd.getWidth(), rd.getHeight());
        Color fillColor = rd.getFillColor() != null ? Color.valueOf(rd.getFillColor()) : Color.TRANSPARENT;
        Color strokeColor = rd.getStrokeColor() != null ? Color.valueOf(rd.getStrokeColor()) : Color.BLACK;
        rect.setFill(fillColor);
        rect.setStroke(strokeColor);
        rect.setStrokeWidth(rd.getStrokeWidth());

//        if(rd.isXFlipped()) rect.setScaleX(-1);
//        if(rd.isYFlipped()) rect.setScaleY(-1);

        System.out.println("XFlipped? " + rd.isXFlipped() + "\nYFlipped? " + rd.isYFlipped());
        System.out.println("Angle " + rd.getRotationAngle());

        rect.setRotate(0);
        if(rd.isYFlipped() && rd.isXFlipped()) {
            rect.setRotate(rd.getRotationAngle() + 180);
        } else if(rd.isYFlipped() && !rd.isXFlipped()) {
            rect.setRotate(-rd.getRotationAngle());
        } else if(rd.isXFlipped() && !rd.isYFlipped()) {
            rect.setRotate(-rd.getRotationAngle() + 180);
        } else {
            rect.setRotate(rd.getRotationAngle());
        }


        return rect;
    }
}
