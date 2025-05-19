package org.softwarearchitecturedesigngroup10.view.helper;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

public class Highlighter {

    // Static nested class that keeps information about a shape animation
    private static class AnimationContext {
        final Timeline timeline;
        final DropShadow effect;
        final DoubleProperty hueProperty;

        AnimationContext(Timeline timeline, DropShadow effect, DoubleProperty hueProperty) {
            this.timeline = timeline;
            this.effect = effect;
            this.hueProperty = hueProperty;
        }
    }

    private static final Map<Shape, AnimationContext> activeAnimatedHighlights = new HashMap<>();

    public static void highlightShape(Shape shape) {
        if (shape == null) return;

        if (activeAnimatedHighlights.containsKey(shape)) {
            unhighlightShape(shape);
        } else {

            shape.setEffect(null); // Removes any previous effect just to be sure

            DropShadow animatedEffect = new DropShadow();
            animatedEffect.setOffsetX(0);
            animatedEffect.setOffsetY(0);
            animatedEffect.setRadius(2);
            animatedEffect.setSpread(1);

            // Creates DoubleProperty (in order to add a listener) and a Timeline
            DoubleProperty hue = new SimpleDoubleProperty(0);
            Timeline timeline = new Timeline();

            KeyValue kvHueStart = new KeyValue(hue, 0.0);
            KeyValue kvHueEnd = new KeyValue(hue, 360.0); // 360 completes the hue cycle
            KeyFrame kfHue = new KeyFrame(Duration.seconds(10), kvHueStart, kvHueEnd);

            timeline.getKeyFrames().add(kfHue);
            timeline.setCycleCount(Animation.INDEFINITE);

            hue.addListener((obs, oldVal, newVal) -> {
                // Checks if the animation on the shape is the same that is being animated
                if (shape.getEffect() == animatedEffect) {
                    animatedEffect.setColor(Color.hsb(newVal.doubleValue() % 360, 1.0, 1.0));
                }
            });

            // Sets start hue
            animatedEffect.setColor(Color.hsb(hue.get() % 360, 1.0, 1.0));

            shape.setEffect(animatedEffect);
            timeline.play();

            activeAnimatedHighlights.put(shape, new AnimationContext(timeline, animatedEffect, hue));
        }
    }

    public static void unhighlightShape(Shape shape) {
        if (shape == null) return;

        if (activeAnimatedHighlights.containsKey(shape)) {
            AnimationContext context = activeAnimatedHighlights.get(shape);
            context.timeline.stop(); // Stops animation
            activeAnimatedHighlights.remove(shape);
        }
        // Removes any effect on the shape
        shape.setEffect(null);
    }
}