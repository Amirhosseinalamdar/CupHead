package View.Transitions;

import View.Component.Plane;
import javafx.animation.Transition;
import javafx.util.Duration;

import java.io.IOException;

public class PlaneTransition extends Transition {
    private boolean flag=false;
    private boolean explosion = false;




    public PlaneTransition() {
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double frac) {

            if (!flag) {
                int frame = (int) Math.floor(14 * frac + 1);
                if (frame < 10)
                    Plane.getInstance().setImage("/images/missle/mm_shmup_super_intro_000" + frame + ".png");
                else
                    Plane.getInstance().setImage("/images/missle/mm_shmup_super_intro_00" + frame + ".png");
                if (frac == 1) flag = true;
            } else if (flag && !explosion) {
                try {
                    Plane.getInstance().moveAsMissile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

    }

    public boolean isExplosion() {
        return explosion;
    }

    public void setExplosion(boolean explosion) {
        this.explosion = explosion;
    }
}
