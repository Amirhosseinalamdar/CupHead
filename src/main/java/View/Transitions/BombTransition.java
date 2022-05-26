package View.Transitions;

import Application.App;
import Controller.GameController;
import Model.DataBase;
import View.Component.Bomb;
import View.Component.Boss1;
import View.Component.Bullet;
import View.Component.MiniBoss;
import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;

public class BombTransition extends Transition {
    private final Bomb bomb;
    public BombTransition(Bomb bomb) {
        this.bomb = bomb;
        this.setCycleDuration(Duration.millis(1000));
        this.setCycleCount(-1);
    }

    @Override
    protected void interpolate(double frac) {
        bomb.moveDown();
        if(bomb.getY() > 900){
            Bomb.getBombs().remove(bomb);
            ((Pane)(App.getStage().getScene().getRoot())).getChildren().remove(bomb);
            bomb.getBombTransition().stop();
        }
        for(MiniBoss key:MiniBoss.getMiniBosses()){
            if(bomb.hasCollision(key)){
                key.setHealth(key.getHealth() - bomb.getDamage());
                Bomb.getBombs().remove(bomb);
                ((Pane)(App.getStage().getScene().getRoot())).getChildren().remove(bomb);
                if(key.getHealth() <=0){
                    MiniBoss.getMiniBosses().remove(key);
                    ((Pane)(App.getStage().getScene().getRoot())).getChildren().remove(key);
                    key.getMiniBossTransition().stop();
                    DataBase.getInstance().getLoggedInUser().setScore(15);
                }
                bomb.getBombTransition().stop();
                break;
            }
        }
        if(bomb.hasCollision(Boss1.getInstance())){
            Boss1.getInstance().setHealth(Boss1.getInstance().getHealth() - bomb.getDamage());
            Bomb.getBombs().remove(bomb);
            ((Pane)(App.getStage().getScene().getRoot())).getChildren().remove(bomb);
            bomb.getBombTransition().stop();
            GameController.getInstance().getBossHealthBar().setProgress(Boss1.getInstance().getHealth() / 1000);
            DataBase.getInstance().getLoggedInUser().setScore(3);
        }
    }
}
