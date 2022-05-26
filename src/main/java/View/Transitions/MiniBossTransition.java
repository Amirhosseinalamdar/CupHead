package View.Transitions;

import View.Component.MiniBoss;
import javafx.animation.Transition;
import javafx.util.Duration;

public class MiniBossTransition extends Transition {
    private MiniBoss miniBoss;

    public MiniBossTransition(MiniBoss miniBoss) {
        this.miniBoss = miniBoss;
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double frac) {
        int frame = (int) Math.floor(3 * frac + 1);
        miniBoss.setImage("/images/miniBoss/"+frame+".png");
        miniBoss.move();

    }


}
