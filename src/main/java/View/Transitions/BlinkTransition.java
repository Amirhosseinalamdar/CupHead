package View.Transitions;

import View.Component.Plane;
import javafx.animation.Transition;
import javafx.util.Duration;

public class BlinkTransition extends Transition {
    private boolean isPlaying;

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public BlinkTransition() {
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double frac) {
        if(frac < 1.0/8 || (frac<3.0/8 && frac>2.0/8) || (frac<5.0/8 && frac>4.0/8) || (frac<7.0/8 && frac>6.0/8)){
            Plane.getInstance().setOpacity(1);
        }else{
            Plane.getInstance().setOpacity(0.2);
        }
    }
}
