package View.Transitions;

import View.Component.Background;
import javafx.animation.Transition;
import javafx.util.Duration;

public class BackgroundTransition extends Transition {
    private final Background background;

    public BackgroundTransition(Background background) {
        this.background = background;
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double frac) {
        background.move();
    }
}
