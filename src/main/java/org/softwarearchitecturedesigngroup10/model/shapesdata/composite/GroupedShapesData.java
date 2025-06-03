package org.softwarearchitecturedesigngroup10.model.shapesdata.composite;

import org.softwarearchitecturedesigngroup10.model.shapesdata.EllipseData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.LineData;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

import java.util.ArrayList;
import java.util.List;

public class GroupedShapesData extends ShapeData {

    private List<ShapeData> children;

    public GroupedShapesData() {
        setType("GD");
        children = new ArrayList<>();
    }

    @Override
    public void resize(double newWidth, double newHeight) {

    }

    @Override
    public List<ShapeData> getChildren() {
        return children;
    }

    public void add(ShapeData data) {
        children.add(data);
    }

    public void remove(ShapeData data) {
        children.remove(data);
    }

    @Override
    public void setX(double newAbsoluteX) {
        if (children.isEmpty()) {
            super.setX(newAbsoluteX);
            return;
        }

        double currentGroupX = getX();
        double deltaX = newAbsoluteX - currentGroupX;

        super.setX(newAbsoluteX);
        for (ShapeData child : children) {
            child.setX(child.getX() + deltaX);
            if (child instanceof LineData ld) ld.setEndX(ld.getEndX() + deltaX);
            if (child instanceof EllipseData ed) {
                ed.setCenterX(ed.getCenterX() + deltaX);
                ed.setX(ed.getCenterX() - ed.getRadiusX());
            }
        }
    }

    @Override
    public void setY(double newAbsoluteY) {
        if (children.isEmpty()) {
            super.setY(newAbsoluteY);
            return;
        }
        double currentGroupY = getY();
        double deltaY = newAbsoluteY - currentGroupY;

        super.setY(newAbsoluteY);
        for (ShapeData child : children) {
            child.setY(child.getY() + deltaY);
            if (child instanceof LineData ld) ld.setEndY(ld.getEndY() + deltaY);
            if (child instanceof EllipseData ed) {
                ed.setCenterY(ed.getCenterY() + deltaY);
                ed.setY(ed.getCenterY() - ed.getRadiusY());
            }
        }
    }

    @Override
    public double getX() {
        if (children.isEmpty()) {
            return super.getX();
        }
        double minX = Double.MAX_VALUE;
        for (ShapeData child : children) {
            minX = Math.min(minX, child.getX());
        }
        return minX;
    }

    @Override
    public double getY() {
        if (children.isEmpty()) {
            return super.getY();
        }
        double minY = Double.MAX_VALUE;
        for (ShapeData child : children) {
            minY = Math.min(minY, child.getY());
        }
        return minY;
    }

    @Override
    public double getWidth() {
        if (children.isEmpty()) return 0;
        double minX = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        for (ShapeData child : children) {
            minX = Math.min(minX, child.getX());
            maxX = Math.max(maxX, child.getX() + child.getWidth());
        }
        return maxX - minX;
    }

    @Override
    public double getHeight() {
        if (children.isEmpty()) return 0;
        double minY = Double.MAX_VALUE;
        double maxY = Double.MIN_VALUE;
        for (ShapeData child : children) {
            minY = Math.min(minY, child.getY());
            maxY = Math.max(maxY, child.getY() + child.getHeight());
        }
        return maxY - minY;
    }

    @Override
    public void setFillColor(String fillColor) {
        for (ShapeData child : children) {
            child.setFillColor(fillColor);
        }
    }

    @Override
    public void setStrokeColor(String strokeColor) {
        for (ShapeData child : children) {
            child.setStrokeColor(strokeColor);
        }
    }

    @Override
    public void setStrokeWidth(double strokeWidth) {
        for (ShapeData child : children) {
            child.setStrokeWidth(strokeWidth);
        }
    }

    @Override
    public void setRotationAngle(double rotationAngle) {
        for (ShapeData child : children) {
            child.setRotationAngle(rotationAngle);
        }
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        for (ShapeData child : children) {
            child.setSelected(selected);
        }
    }

    @Override
    public double getRotationAngle() {
        return getChildren().get(0).getRotationAngle();
    }

    @Override
    public String getFillColor() {
        return getChildren().get(0).getFillColor();
    }

    @Override
    public String getStrokeColor() {
        return getChildren().get(0).getStrokeColor();
    }

    @Override
    public double getStrokeWidth() {
        return getChildren().get(0).getStrokeWidth();
    }

    @Override
    public ShapeData clone() {
        GroupedShapesData clonedGroup = (GroupedShapesData) super.clone();
        clonedGroup.children = new ArrayList<>();
        for (ShapeData child : this.children) {
            clonedGroup.children.add(child.clone());
        }
        return clonedGroup;
    }
}
