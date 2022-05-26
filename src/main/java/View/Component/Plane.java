package View.Component;

import Application.App;
import Controller.GameController;
import Model.DataBase;
import View.EndMenuController;
import View.Transitions.BlinkTransition;
import View.Transitions.PlaneTransition;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class Plane extends ImageView {
    private ImageView bombIcon = new ImageView("/images/bombIcon.png");
    private static Plane plane;
    private boolean isMissile = false;
    private String weapon = "bullet";
    private int planeVSpeed = 30;
    private int planeHSpeed = 30;
    private double damage;
    private double health;
    private PlaneTransition planeTransition;
    private BlinkTransition blinkTransition;
    private boolean[] directions = new boolean[6];
    private boolean isBlinked;

    public BlinkTransition getBlinkTransition() {
        return blinkTransition;
    }

    private Long time0;
    private Long time1;

    public void setTime0(Long time0) {
        this.time0 = time0;
    }

    public void setTime1(Long time1) {
        this.time1 = time1;
    }

    public Long getTime0() {
        return time0;
    }

    public Long getTime1() {
        return time1;
    }

    public void setHealth(double health) {
        this.health = health;
        if(health <= 0){
            GameController.getInstance().end();
        }
        if(health <= DataBase.getInstance().getLoggedInUser().getHealth()/3)
            GameController.getInstance().getHealthBar().setStyle("-fx-accent: red;");
    }

    public double getHealth() {
        return health;
    }

    public void setBlinked(boolean blinked) {
        isBlinked = blinked;
        if (blinked) {
            blinkTransition.play();
            blinkTransition.setPlaying(true);
        } else {
            blinkTransition.stop();
            blinkTransition.setPlaying(false);
        }
    }

    public boolean isBlinked() {
        return isBlinked;
    }

    public double getDamage() {
        return damage;
    }

    private Plane(String url) {
        super(url);
        bombIcon.setY(900 - bombIcon.getLayoutBounds().getHeight() - 10 - 20 - 20);
        damage = 4 * DataBase.getInstance().getLoggedInUser().getPower();
        health = DataBase.getInstance().getLoggedInUser().getHealth();
        for (int i = 0; i < 4; i++) directions[i] = false;
        isBlinked = false;
        time0 = 0L;
        time1 = 0L;
        blinkTransition = new BlinkTransition();
    }

    public static Plane getInstance() {
        if (plane == null)
            plane = new Plane("/images/plane.png");
        return plane;
    }

    public void setImage(String url) {
        this.setImage(new Image(url));
    }

    public void planeHandler() {
        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                    chooseOption(event);

            }
        });
        this.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                chooseReleaseOption(event);
            }
        });
    }

    private void chooseReleaseOption(KeyEvent event) {
        if (event.getCode().getName().equals("Down")) directions[0] = false;
        else if (event.getCode().getName().equals("Up")) directions[1] = false;
        else if (event.getCode().getName().equals("Right")) directions[2] = false;
        else if (event.getCode().getName().equals("Left")) directions[3] = false;
        else if (event.getCode().getName().equals("Space")) directions[4] = false;
    }

    private void chooseOption(KeyEvent event) {
        if (!GameController.getInstance().isIspause()) {
            if (event.getCode().getName().equals("Down")) {
                directions[0] = true;
                move();
            } else if (event.getCode().getName().equals("Up")) {
                directions[1] = true;
                move();
            } else if (event.getCode().getName().equals("Right")) {
                directions[2] = true;
                move();
            } else if (event.getCode().getName().equals("Left")) {
                directions[3] = true;
                move();
            } else if (event.getCode().getName().equals("Space") && !GameController.getInstance().isFinished()) {
                directions[4] = true;
                move();
            } else if (event.getCode().getName().equals("Tab")) {
                if (this.weapon.equals("bullet")) {
                    this.weapon = "bomb";
                    ((Pane) (App.getStage().getScene().getRoot())).getChildren().add(19, bombIcon);
                } else {
                    ((Pane) (App.getStage().getScene().getRoot())).getChildren().remove(bombIcon);
                    this.weapon = "bullet";
                }
            } else if (event.getCode().getName().equals("Shift") && GameController.getInstance().isCanMissile() && !isMissile) {
                planeTransition = new PlaneTransition();
                planeTransition.play();
                this.isMissile = true;
                GameController.getInstance().setCanMissile(false);
                GameController.getInstance().setMissileShot(true);
            } else if (event.getCode().getName().equals("Esc")) {
                GameController.getInstance().pause();
            }
        }else if(event.getCode().getName().equals("Esc")){
            GameController.getInstance().continueGame();
        }
    }

    public boolean isMissile() {
        return isMissile;
    }

    private void move() {
        if (directions[0] && this.getY() + this.getLayoutBounds().getHeight() < 900)
            this.setY(this.getY() + planeVSpeed);
        if (directions[1] && this.getY() > 0)
            this.setY(this.getY() - planeVSpeed);
        if (directions[2] && this.getX() + this.getLayoutBounds().getWidth() < 1600)
            this.setX(this.getX() + planeHSpeed);
        if (directions[3] && this.getX() > 0)
            this.setX(this.getX() - planeHSpeed);
        if (directions[4] && !GameController.getInstance().isFinished()) {
            if (weapon.equals("bullet")) {
                Bullet bullet = new Bullet();
                ((Pane) (App.getStage().getScene().getRoot())).getChildren().add(bullet);
                bullet.getBulletTransition().play();
            } else {
                Bomb bomb = new Bomb();
                ((Pane) (App.getStage().getScene().getRoot())).getChildren().add(bomb);
                bomb.getBombTransition().play();
            }
        }
    }

    public void moveAsMissile() throws IOException {
        this.setX(this.getX() + 7);
        if (this.getX() + this.getLayoutBounds().getWidth() >= 1600) {
            getPlaneTransition().stop();
            setImage("/images/plane.png");
            this.setX(0);
            this.isMissile = false;
        }
        for (MiniBoss key : MiniBoss.getMiniBosses()) {
            if (hasCollision(key)) {
                getPlaneTransition().stop();
                MiniBoss.getMiniBosses().remove(key);
                ((Pane) (App.getStage().getScene().getRoot())).getChildren().remove(key);

                key.getMiniBossTransition().stop();
                setImage("/images/plane.png");
                this.setX(0);
                this.isMissile = false;
                break;
            }
        }
        if (hasCollision(Boss1.getInstance())) {
            Boss1.getInstance().setHealth(Boss1.getInstance().getHealth() - damage);
            getPlaneTransition().stop();
            setImage("/images/plane.png");
            this.setX(0);
            this.isMissile = false;
            GameController.getInstance().getBossHealthBar().setProgress(Boss1.getInstance().getHealth() / 1000);
        }
    }

    public PlaneTransition getPlaneTransition() {
        return planeTransition;
    }

    public boolean hasCollision(ImageView imageView) {
        return imageView.getBoundsInParent().intersects(this.getLayoutBounds());
    }
}
