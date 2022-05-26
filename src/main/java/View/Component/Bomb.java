package View.Component;

import Controller.GameController;
import Model.DataBase;
import View.Transitions.BombTransition;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Bomb extends ImageView {
    private double bombSpeed = 5;
    private static ArrayList<Bomb> bombs = new ArrayList<>();
    private BombTransition bombTransition;
    private double damage;

    public Bomb(){
        super("/images/bomb.png");
        this.setX(Plane.getInstance().getX() + Plane.getInstance().getLayoutBounds().getWidth()/2);
        this.setY(Plane.getInstance().getY() + Plane.getInstance().getLayoutBounds().getHeight()/2);
        bombTransition = new BombTransition(this);
        bombs.add(this);
        damage = 2 * DataBase.getInstance().getLoggedInUser().getPower();
        GameController.getInstance().getBombShots().stop();
        GameController.getInstance().getBombShots().play();
    }

    public static ArrayList<Bomb> getBombs() {
        return bombs;
    }

    public BombTransition getBombTransition() {
        return bombTransition;
    }
    public boolean hasCollision(ImageView imageView){
        return imageView.getBoundsInParent().intersects(this.getLayoutBounds());
    }

    public double getDamage() {
        return damage;
    }

    public void moveDown() {
        this.setY(this.getY() + this.bombSpeed);
        this.setX(this.getX() + 0.5);
    }
}
