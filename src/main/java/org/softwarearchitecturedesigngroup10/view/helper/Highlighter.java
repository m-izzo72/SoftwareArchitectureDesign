package org.softwarearchitecturedesigngroup10.view.helper;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.effect.Glow; // Modificato: Usiamo Glow invece di DropShadow
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

public class Highlighter {

    private static class AnimationContext {
        final Timeline timeline;
        final Glow effect;

        AnimationContext(Timeline timeline, Glow effect) {
            this.timeline = timeline;
            this.effect = effect;
        }
    }

    private static final Map<Shape, AnimationContext> activeAnimatedHighlights = new HashMap<>();

    private static final double MIN_GLOW_LEVEL = 0.1;
    private static final double MAX_GLOW_LEVEL = 0.7;
    private static final double PULSE_DURATION_SECONDS = 1.5;

    public static void highlightShape(Shape shape) {
        if (shape == null) return;

        if (activeAnimatedHighlights.containsKey(shape)) {
            unhighlightShape(shape);
        } else {
            shape.setEffect(null);

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
        }
    }

    public static void unhighlightShape(Shape shape) {
        if (shape == null) return;


        if (activeAnimatedHighlights.containsKey(shape)) {
            AnimationContext context = activeAnimatedHighlights.get(shape);
            context.timeline.stop();
            activeAnimatedHighlights.remove(shape);
        }

        shape.setEffect(null);
    }
}