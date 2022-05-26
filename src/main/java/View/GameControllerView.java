package View;

import Application.App;
import Controller.GameController;
import Model.DataBase;
import View.Component.*;
import View.Transitions.BulletTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameControllerView {
    private Timeline timeline;
    private int miniBossCounter;
    private int missileCounter;
    private boolean flag;


    public GameControllerView() {
        flag = true;
    }

    private Random random = new Random();
    @FXML
    private Pane gamePane;

    void mainWhile(Region region, double xPosition) throws InterruptedException, IOException {
        miniBossCounter++;
        if(System.currentTimeMillis() - 2900 > Plane.getInstance().getTime0() &&
        System.currentTimeMillis() - 3100 < Plane.getInstance().getTime0() &&
        Plane.getInstance().isBlinked()){
            Plane.getInstance().setBlinked(false);
        }
        if(DataBase.getInstance().getLoggedInUser().isDevilMode()) {
            if (Boss1.getInstance().getHealth() <= 0 && Boss1.getInstance().getBoss1Transition().getBossPhase() != 3) {
                Boss1.getInstance().getBoss1Transition().setBossPhase(3);
                Boss1.getInstance().setHealth(1000);
            } else if (Boss1.getInstance().getHealth() < 500 && Boss1.getInstance().getBoss1Transition().getBossPhase() != 2) {
                Boss1.getInstance().getBoss1Transition().setBossPhase(2);
            }
        }
        if(random.nextInt(1000)>992 && !GameController.getInstance().isFinished() && !GameController.getInstance().isIspause())
            Boss1.getInstance().getBoss1Transition().setShoot(true);
        if(missileCounter!=4000 && !GameController.getInstance().isIspause()) missileCounter++;
        if(!GameController.getInstance().isCanMissile()){
            GameController.getInstance().getMissileProgressBar().setProgress(missileCounter*1.0/4000);
        }
        if(miniBossCounter % 2000 == 0 && !GameController.getInstance().isFinished() && !GameController.getInstance().isIspause()){
            spawnMiniBosses();
            miniBossCounter = 0;
        }else if(!GameController.getInstance().isCanMissile() && GameController.getInstance().isMissileShot()){
            missileCounter = 0;
            GameController.getInstance().setMissileShot(false);
        }else if(missileCounter == 4000 && !GameController.getInstance().isCanMissile()){
            GameController.getInstance().setCanMissile(true);
        }
    }
    private void spawnMiniBosses(){
        Random random = new Random();
        int y = random.nextInt(800);
        for(int i=0;i<random.nextInt(5)+3;i++) {
            MiniBoss miniBoss = new MiniBoss();
            miniBoss.setX(1600 + i*miniBoss.getLayoutBounds().getWidth());
            miniBoss.setY(y);
            ((Pane) (App.getStage().getScene().getRoot())).getChildren().add(miniBoss);
            miniBoss.getMiniBossTransition().play();
        }
    }
    public Timeline getTimeline(){
        DoubleProperty xPosition = new SimpleDoubleProperty(0);
        xPosition.addListener((observable, oldValue, newValue) -> {
            try {
                mainWhile(gamePane, xPosition.get());
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });
        timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(xPosition, 0)),
                new KeyFrame(Duration.seconds(200), new KeyValue(xPosition, -15000)));

        return timeline;
    }
    public void initialize() {
        gamePane.setEffect(GameController.getInstance().getColorAdjust());
        GameController.getInstance().getMenuMediaPlayer().stop();
        GameController.getInstance().getMediaPlayer().play();
        getTimeline().play();
        setBackground();
        setBasics();
        gamePane.getChildren().add(GameController.getInstance().getScoreLabel());
        setBoss1();
        Plane.getInstance().planeHandler();
        gamePane.getChildren().add(Plane.getInstance());
        Scene scene = new Scene(gamePane);
        App.getStage().setScene(scene);
        gamePane.getChildren().get(23).requestFocus();
        App.getStage().show();
    }
    private void setBoss1(){
        Boss1.getInstance().setX(1600 - Boss1.getInstance().getLayoutBounds().getWidth());
        Boss1.getInstance().setY(0);
        gamePane.getChildren().add(Boss1.getInstance());
        GameController.getInstance().getBossHealthBar().setStyle("-fx-accent: #571414;");
        GameController.getInstance().getBossHealthBar().setProgress(1000);
        GameController.getInstance().getBossHealthBar().setPrefHeight(30);
        GameController.getInstance().getBossHealthBar().setPrefWidth(300);
        GameController.getInstance().getBossHealthBar().setLayoutX(1600 - 300 - 10);
        GameController.getInstance().getBossHealthBar().setLayoutY(900 - 30 - 10);
        gamePane.getChildren().add(GameController.getInstance().getBossHealthBar());
        GameController.getInstance().getLabel().setLayoutX(1600 - 300 -10 - 85);
        GameController.getInstance().getLabel().setLayoutY(900 - 30 - 10 - 17);
        GameController.getInstance().getLabel().setStyle("-fx-font-weight: bold;");
        GameController.getInstance().getLabel().setStyle("-fx-font-size: 45;");
        gamePane.getChildren().add(GameController.getInstance().getLabel());
    }
    private void setBasics(){
        GameController.getInstance().getMissileProgressBar().setPrefWidth(200);
        GameController.getInstance().getMissileProgressBar().setPrefHeight(20);
        GameController.getInstance().getMissileProgressBar().setLayoutX(10);
        GameController.getInstance().getMissileProgressBar().
                setLayoutY(900 - GameController.getInstance().getMissileProgressBar().getPrefHeight() - 10 - 20);
        ImageView bulletIcon = new ImageView("/images/bulletIcon.png");
        bulletIcon.setX(0);
        bulletIcon.setY(900 - bulletIcon.getLayoutBounds().
                getHeight() - GameController.getInstance().getMissileProgressBar().getPrefHeight() - 10 - 20);
        GameController.getInstance().getHealthBar().setPrefWidth(200);
        GameController.getInstance().getHealthBar().setPrefHeight(20);
        GameController.getInstance().getHealthBar().setStyle("-fx-accent: green;");
        GameController.getInstance().getHealthBar().setProgress(1);
        GameController.getInstance().getHealthBar().setLayoutX(10);
        GameController.getInstance().getHealthBar().setLayoutY(900 -
                GameController.getInstance().getHealthBar().getPrefHeight() - 10);
        gamePane.getChildren().add(GameController.getInstance().getHealthBar());
        gamePane.getChildren().add(GameController.getInstance().getMissileProgressBar());
        gamePane.getChildren().add(bulletIcon);

    }
    private void setBackground() {
        Background[] backgrounds = new Background[16];
        for(int i=15;i>=0;i--){
            backgrounds[i] = getThisBackGround(i/2 + 1);
            if(i%2 == 1){
                backgrounds[i].setX(backgrounds[i].getLayoutBounds().getWidth());
            }
            if(i<12){
                backgrounds[i].setY(900 - backgrounds[i].getLayoutBounds().getHeight());
            }
        }

        for(int i=15;i>=0;i--){
            gamePane.getChildren().add(backgrounds[i]);
            backgrounds[i].getBackgroundTransition().play();
        }
    }
    private Background getThisBackGround(int i){
        return new Background("/images/backgrounds/birdhouse_bg_000" + i + ".png");
    }

}
