package View.Component;


import Application.App;
import Controller.GameController;
import Model.DataBase;
import View.Transitions.MiniBossTransition;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class MiniBoss extends ImageView {
    private static ArrayList<MiniBoss> miniBosses = new ArrayList<>();
    private double speed = 1.3;
    private double health = 2;
    private double damage;
    private MiniBossTransition miniBossTransition;

    public void move(){
        this.setX(this.getX() - speed);
        if(this.getX() + this.getLayoutBounds().getWidth() < 0) {
            deleteMiniBoss();
        }else if(hasCollisionWithPlane() && !Plane.getInstance().isBlinked()){
            deleteMiniBoss();
            Plane.getInstance().setHealth(Plane.getInstance().getHealth() - damage);
            GameController.getInstance().updatePlayer();
            Plane.getInstance().setBlinked(true);
            Plane.getInstance().setTime0(System.currentTimeMillis());
        }
    }
    private void deleteMiniBoss(){
        miniBosses.remove(this);
        ((Pane) (App.getStage().getScene().getRoot())).getChildren().remove(this);
        this.getMiniBossTransition().stop();
    }
    public boolean hasCollisionWithPlane(){
        return Plane.getInstance().getBoundsInParent().intersects(this.getLayoutBounds()) && !Plane.getInstance().isMissile();
    }
    public MiniBoss() {
        super("/images/miniBoss/1.png");
        damage = DataBase.getInstance().getLoggedInUser().getWeakness();
        miniBossTransition = new MiniBossTransition(this);
        miniBosses.add(this);
    }

    public static ArrayList<MiniBoss> getMiniBosses() {
        return miniBosses;
    }

    public MiniBossTransition getMiniBossTransition() {
        return miniBossTransition;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }
    public void setImage(String url){
        this.setImage(new Image(url));
    }
}
