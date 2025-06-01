package org.softwarearchitecturedesigngroup10.model.shapesdata.composite;
import org.softwarearchitecturedesigngroup10.model.shapesdata.ShapeData;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class GroupedShapeData extends ShapeData {
    private List<ShapeData> children = new ArrayList<>();

    public GroupedShapeData() {
        setType("Group");
    }

    @Override
    public void add(ShapeData shape) {
        children.add(shape);
    }

    @Override
    public void remove(ShapeData shape) {
        children.remove(shape);
    }

    @Override
    public List<ShapeData> getChildren() {
        return Collections.unmodifiableList(children);
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
        super.setFillColor(fillColor);
        for (ShapeData child : children) {
            child.setFillColor(fillColor);
        }
    }

    @Override
    public void setStrokeColor(String strokeColor) {
        super.setStrokeColor(strokeColor);
        for (ShapeData child : children) {
            child.setStrokeColor(strokeColor);
        }
    }

    @Override
    public void setStrokeWidth(double strokeWidth) {
        super.setStrokeWidth(strokeWidth);
        for (ShapeData child : children) {
            child.setStrokeWidth(strokeWidth);
        }
    }

    @Override
    public void setRotationAngle(double rotationAngle) {
        super.setRotationAngle(rotationAngle);
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
    public void resize(double newWidth, double newHeight) {
        double oldGroupWidth = getWidth();
        double oldGroupHeight = getHeight();
        if (oldGroupWidth == 0 || oldGroupHeight == 0) return;

        double scaleX = newWidth / oldGroupWidth;
        double scaleY = newHeight / oldGroupHeight;

        double groupAnchorX = getX();
        double groupAnchorY = getY();

        for (ShapeData child : children) {
            double relativeChildX = child.getX() - groupAnchorX;
            double relativeChildY = child.getY() - groupAnchorY;

            double newRelativeChildX = relativeChildX * scaleX;
            double newRelativeChildY = relativeChildY * scaleY;

            child.setX(groupAnchorX + newRelativeChildX);
            child.setY(groupAnchorY + newRelativeChildY);

            child.resize(child.getWidth() * scaleX, child.getHeight() * scaleY);
        }
    }

    @Override
    public void setXFlipped() {
        super.setXFlipped();
        double centerX = getX() + getWidth() / 2;
        for (ShapeData child : children) {
            child.setXFlipped();
            double childCurrentX = child.getX();
            double childWidth = child.getWidth();
            double newChildX = centerX + (centerX - (childCurrentX + childWidth));
            child.setX(newChildX);
        }
    }

    @Override
    public void setYFlipped() {
        super.setYFlipped();
        double centerY = getY() + getHeight() / 2;
        for (ShapeData child : children) {
            child.setYFlipped();
            double childCurrentY = child.getY();
            double childHeight = child.getHeight();
            double newChildY = centerY + (centerY - (childCurrentY + childHeight));
            child.setY(newChildY);
        }
    }


    @Override
    public ShapeData clone() {
        GroupedShapeData clonedGroup = (GroupedShapeData) super.clone();
        clonedGroup.children = new ArrayList<>();
        for (ShapeData child : this.children) {
            clonedGroup.children.add(child.clone());
        }
        return clonedGroup;
    }
}