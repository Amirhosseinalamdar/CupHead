package View.Transitions;

import Application.App;
import Controller.GameController;
import Model.DataBase;
import View.Component.Boss1;
import View.Component.Bullet;
import View.Component.MiniBoss;
import javafx.animation.Transition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;

public class BulletTransition extends Transition {
    private final Bullet bullet;
    public BulletTransition(Bullet bullet) {
        this.bullet = bullet;
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double frac) {
        bullet.moveRight();
        if(bullet.getX() > 1600){
            Bullet.getBullets().remove(bullet);
            ((Pane)(App.getStage().getScene().getRoot())).getChildren().remove(bullet);
            bullet.getBulletTransition().stop();
        }
        for(MiniBoss key:MiniBoss.getMiniBosses()){
            if(bullet.hasCollision(key)){
                key.setHealth(key.getHealth() - bullet.getDamage());
                Bullet.getBullets().remove(bullet);
                ((Pane)(App.getStage().getScene().getRoot())).getChildren().remove(bullet);
                if(key.getHealth() <=0){
                    MiniBoss.getMiniBosses().remove(key);
                    ((Pane)(App.getStage().getScene().getRoot())).getChildren().remove(key);
                    key.getMiniBossTransition().stop();
                    DataBase.getInstance().getLoggedInUser().setScore(10);
                }
                bullet.getBulletTransition().stop();
                break;
            }
        }
        if(bullet.hasCollision(Boss1.getInstance())){
            Boss1.getInstance().setHealth(Boss1.getInstance().getHealth() - bullet.getDamage());
            Bullet.getBullets().remove(bullet);
            ((Pane)(App.getStage().getScene().getRoot())).getChildren().remove(bullet);
            bullet.getBulletTransition().stop();
            GameController.getInstance().getBossHealthBar().setProgress(Boss1.getInstance().getHealth() * 1.0 / 1000);
            DataBase.getInstance().getLoggedInUser().setScore(1);
        }
    }

}
