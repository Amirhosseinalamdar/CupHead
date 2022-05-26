package View.Component;

import Controller.GameController;
import Model.DataBase;
import View.Transitions.BulletTransition;
import javafx.scene.image.ImageView;

import javax.xml.crypto.Data;
import java.util.ArrayList;

public class Bullet extends ImageView{
    private int bulletSpeed = 7;
    private static ArrayList<Bullet> bullets = new ArrayList<>();
    private BulletTransition bulletTransition;
    private double damage;
    public Bullet(){
        super("/images/bullet.png");
        this.setX(Plane.getInstance().getX() + 10);
        this.setY(Plane.getInstance().getY() + 50);
        bulletTransition = new BulletTransition(this);
        bullets.add(this);
        damage = DataBase.getInstance().getLoggedInUser().getPower();
        GameController.getInstance().getBulletShots().stop();
        GameController.getInstance().getBulletShots().play();
    }

    public static ArrayList<Bullet> getBullets() {
        return bullets;
    }
    public void moveRight(){
        this.setX(this.getX() + bulletSpeed);
    }

    public BulletTransition getBulletTransition() {
        return bulletTransition;
    }

    public boolean hasCollision(ImageView imageView){
        return imageView.getBoundsInParent().intersects(this.getLayoutBounds());
    }

    public double getDamage() {
        return damage;
    }
}
