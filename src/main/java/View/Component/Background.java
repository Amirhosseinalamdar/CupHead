package View.Component;

import View.Transitions.BackgroundTransition;
import javafx.scene.image.ImageView;

public class Background extends ImageView {
    private double speed = 1;
    private BackgroundTransition backgroundTransition;
    public void move() {
        if(this.getX() == -this.getLayoutBounds().getWidth())
            this.setX(this.getLayoutBounds().getWidth()-1);
        else this.setX(this.getX() - speed);
    }

    public Background(String url) {
        super(url);
        backgroundTransition = new BackgroundTransition(this);
    }

    public BackgroundTransition getBackgroundTransition() {
        return backgroundTransition;
    }
}
