package View.Component;


import Application.App;
import Controller.GameController;
import Model.DataBase;
import View.Transitions.BallShootTransition;
import View.Transitions.Boss1Transition;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class Boss1 extends ImageView{
    private static Boss1 boss1;
    private double health = 1000;
    private double speed = 2;
    private double damage;
    private Boss1Transition boss1Transition;


    public double getDamage() {
        return damage;
    }

    private Boss1(){
        super("/images/boss1/1.png");
        boss1Transition = new Boss1Transition();
        boss1Transition.play();
        damage = DataBase.getInstance().getLoggedInUser().getWeakness();
    }

    public Boss1Transition getBoss1Transition() {
        return boss1Transition;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
        GameController.getInstance().getLabel().setText(String.valueOf((int) health/10));
        if(health <=0 && this.getBoss1Transition().getBossPhase() == 1){
            GameController.getInstance().end();
        }else if(health<=0 && this.getBoss1Transition().getBossPhase() == 3){
            GameController.getInstance().end();
        }

    }

    public static Boss1 getInstance(){
        if(boss1 == null) boss1 = new Boss1();
        return boss1;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
    public boolean hasCollisionWithPlane(){
        return Plane.getInstance().getBoundsInParent().intersects(this.getLayoutBounds())&& !Plane.getInstance().isMissile();
    }
}
