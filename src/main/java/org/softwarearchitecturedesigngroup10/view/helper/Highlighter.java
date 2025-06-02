package org.softwarearchitecturedesigngroup10.view.helper;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.effect.Glow;
import javafx.util.Duration;

import java.util.Map;
import java.util.WeakHashMap;

public class Highlighter {

    private record AnimationContext(Timeline timeline, Glow effect) {
    }

    private static final Map<Node, AnimationContext> activeAnimatedHighlights = new WeakHashMap<>();
    private static final Map<Node, Boolean> selectedShapes = new WeakHashMap<>();

    private static final double MIN_GLOW_LEVEL = 0.1;
    private static final double MAX_GLOW_LEVEL = 0.7;
    private static final double PULSE_DURATION_SECONDS = 1.5;

    public static void highlightShape(Node shape) {
        if (shape == null) return;

        if (activeAnimatedHighlights.containsKey(shape)) {
            return;
        }

        Glow animatedEffect = new Glow();
        animatedEffect.setLevel(MIN_GLOW_LEVEL);

        Timeline timeline = new Timeline();

        KeyValue kvGlowMin = new KeyValue(animatedEffect.levelProperty(), MIN_GLOW_LEVEL);
        KeyValue kvGlowMax = new KeyValue(animatedEffect.levelProperty(), MAX_GLOW_LEVEL);

        KeyFrame kfStart = new KeyFrame(Duration.ZERO, kvGlowMin);
        KeyFrame kfEnd = new KeyFrame(Duration.seconds(PULSE_DURATION_SECONDS), kvGlowMax);

        timeline.getKeyFrames().addAll(kfStart, kfEnd);
        timeline.setAutoReverse(true);
        timeline.setCycleCount(Animation.INDEFINITE);

        shape.setEffect(animatedEffect);
        timeline.play();

        activeAnimatedHighlights.put(shape, new AnimationContext(timeline, animatedEffect));
        selectedShapes.put(shape, true);
    }

    public static void unhighlightShape(Node shape) {
        if (shape == null) return;

        if (activeAnimatedHighlights.containsKey(shape)) {
            AnimationContext context = activeAnimatedHighlights.get(shape);
            context.timeline.stop();
            activeAnimatedHighlights.remove(shape);
            selectedShapes.remove(shape);
        }

        shape.setEffect(null);
    }

//    public static boolean isShapeSelected(Shape shape) {
//        return selectedShapes.getOrDefault(shape, false);
//    }

//    public static boolean isHighlighted(Shape shape) {
//        return activeAnimatedHighlights.containsKey(shape);
//    }
}