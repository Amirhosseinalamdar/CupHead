package View.Component;

import Model.DataBase;
import View.Transitions.BallShootTransition;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Ball extends ImageView {
    private double speed = 7;
    private boolean isPhase3;

    public void setPhase3(boolean phase3) {
        isPhase3 = phase3;
    }

    public boolean isPhase3() {
        return isPhase3;
    }

    private static ArrayList<Ball> balls = new ArrayList<>();
    private BallShootTransition ballShootTransition;
    private double damage;

    public static ArrayList<Ball> getBalls() {
        return balls;
    }

    public BallShootTransition getBallShootTransition() {
        return ballShootTransition;
    }

    public Ball(double x, double y,String url) {
        super(url);
        damage = 1 * DataBase.getInstance().getLoggedInUser().getWeakness();
        balls.add(this);
        ballShootTransition = new BallShootTransition(this);
        setX(x);
        setY(y);
        isPhase3 = false;
    }

    public double getDamage() {
        return damage;
    }

    public double getSpeed() {
        return speed;
    }
    public boolean hasCollisionWithPlane(){
        return Plane.getInstance().getBoundsInParent().intersects(this.getLayoutBounds()) && !Plane.getInstance().isMissile();
    }
}
