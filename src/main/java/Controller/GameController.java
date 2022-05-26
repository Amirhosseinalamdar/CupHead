package Controller;


import Application.App;
import Model.DataBase;
import Model.Quote;
import View.Component.*;
import com.sun.media.jfxmedia.events.BufferListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Random;

public class GameController {
    private static GameController gameController;
    private int counter;
    private boolean canMissile;
    private boolean missileShot;
    private boolean isFinished;
    private ProgressBar missileProgressBar = new ProgressBar();
    private ProgressBar bossHealthBar = new ProgressBar();
    private ProgressBar healthBar = new ProgressBar();
    private MediaPlayer mediaPlayer;
    private MediaPlayer menuMediaPlayer;
    private ColorAdjust colorAdjust;
    private MediaPlayer bossShots;
    private MediaPlayer bulletShots;
    private MediaPlayer bombShots;

    private boolean ispause;
    private Label label = new Label("100");
    private Label scoreLabel = new Label("0");
    private int paneAddCounter;

    public Label getLabel() {
        return label;
    }

    public static void setGameController(GameController gameController) {
        GameController.gameController = gameController;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public void setBossHealthBar(ProgressBar bossHealthBar) {
        this.bossHealthBar = bossHealthBar;
    }

    public void setHealthBar(ProgressBar healthBar) {
        this.healthBar = healthBar;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public void setMenuMediaPlayer(MediaPlayer menuMediaPlayer) {
        this.menuMediaPlayer = menuMediaPlayer;
    }

    public void setBossShots(MediaPlayer bossShots) {
        this.bossShots = bossShots;
    }

    public void setBulletShots(MediaPlayer bulletShots) {
        this.bulletShots = bulletShots;
    }

    public void setBombShots(MediaPlayer bombShots) {
        this.bombShots = bombShots;
    }

    public void setIspause(boolean ispause) {
        this.ispause = ispause;
    }



    public ColorAdjust getColorAdjust() {
        return colorAdjust;
    }

    public ProgressBar getHealthBar() {
        return healthBar;
    }

    public Label getScoreLabel() {
        return scoreLabel;
    }

    private boolean isDevilMode;

    public void setDevilMode(boolean devilMode) {
        isDevilMode = devilMode;
    }

    public boolean isDevilMode() {
        return isDevilMode;
    }

    public MediaPlayer getBossShots() {
        return bossShots;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public MediaPlayer getMenuMediaPlayer() {
        return menuMediaPlayer;
    }

    public MediaPlayer getBulletShots() {
        return bulletShots;
    }

    public MediaPlayer getBombShots() {
        return bombShots;
    }

    public void setColorAdjust(ColorAdjust colorAdjust) {
        this.colorAdjust = colorAdjust;
    }

    private GameController() {
        canMissile = false;
        missileShot = false;
        Media media = new Media(this.getClass().getResource("/music/Anathema - Angelica.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        media = new Media(this.getClass().getResource("/music/Nirvana - Something In The Way.mp3").toExternalForm());
        menuMediaPlayer = new MediaPlayer(media);
        media = new Media(this.getClass().getResource("/music/spit3.wav").toExternalForm());
        bossShots = new MediaPlayer(media);
        media = new Media(this.getClass().getResource("/music/Damage Crackle 1.wav").toExternalForm());
        bulletShots = new MediaPlayer(media);
        media = new Media(this.getClass().getResource("/music/Card 1.wav").toExternalForm());
        bombShots = new MediaPlayer(media);
        colorAdjust = new ColorAdjust();
        ispause = false;
        isFinished = false;
        scoreLabel.setLayoutY(850);
        scoreLabel.setLayoutX(220);
        scoreLabel.setStyle("-fx-font-size: 50;");
        scoreLabel.setStyle("-fx-font-weight: bold;");
    }

    public static GameController getInstance() {
        if (gameController == null)
            gameController = new GameController();
        return gameController;
    }
    public void showScore(){
        scoreLabel.setText(String.valueOf(DataBase.getInstance().getLoggedInUser().getScore()));
    }
    public boolean isMissileShot() {
        return missileShot;
    }

    public void setMissileShot(boolean missileShot) {
        this.missileShot = missileShot;
    }

    public int getCounter() {
        return counter;
    }

    public boolean isCanMissile() {
        return canMissile;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void setCanMissile(boolean canMissile) {
        this.canMissile = canMissile;
    }

    public ProgressBar getMissileProgressBar() {
        return missileProgressBar;
    }

    public void setMissileProgressBar(ProgressBar missileProgressBar) {
        this.missileProgressBar = missileProgressBar;
    }

    public ProgressBar getBossHealthBar() {
        return bossHealthBar;
    }

    public void updatePlayer() {
        healthBar.setProgress(Plane.getInstance().getHealth() / DataBase.getInstance().getLoggedInUser().getHealth());
    }

    public boolean isIspause() {
        return ispause;
    }

    public void pause() {
        ispause = true;
        GameController.getInstance().getMediaPlayer().pause();
        Boss1.getInstance().getBoss1Transition().pause();
        for(MiniBoss key : MiniBoss.getMiniBosses()){
            key.getMiniBossTransition().pause();
        }
        for(Bullet key:Bullet.getBullets())
            key.getBulletTransition().pause();
        for(Bomb key:Bomb.getBombs())
            key.getBombTransition().pause();
        for(Ball key : Ball.getBalls())
            key.getBallShootTransition().pause();
    }

    public void continueGame() {
        GameController.gameController.getMediaPlayer().play();
        ispause = false;
        Boss1.getInstance().getBoss1Transition().play();
        for(MiniBoss key : MiniBoss.getMiniBosses()){
            key.getMiniBossTransition().play();
        }
        for(Bullet key:Bullet.getBullets())
            key.getBulletTransition().play();
        for(Bomb key:Bomb.getBombs())
            key.getBombTransition().play();
        for(Ball key : Ball.getBalls())
            key.getBallShootTransition().play();
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public void end() {
        isFinished = true;
        ispause = true;
        ImageView resultView = new ImageView("/images/resu.png");
        ImageView head;
        if(Boss1.getInstance().getBoss1Transition().getBossPhase() == 1) {
            head = new ImageView("/images/boss1Head.png");
        }else if(Boss1.getInstance().getBoss1Transition().getBossPhase() == 2){
            head = new ImageView("/images/boss2Head.png");
        }else{
            head = new ImageView("/images/boss3Head.png");
        }
        head.setX(0);
        head.setY(100);
        setQuoteText();
        ((Pane)(App.getStage().getScene().getRoot())).getChildren().add(head);
        resultView.setX(800 - resultView.getLayoutBounds().getWidth()/2);
        resultView.setY(450 - resultView.getLayoutBounds().getHeight()/2);
        ((Pane)(App.getStage().getScene().getRoot())).getChildren().add(resultView);
        Button restart = new Button("restart");
        Button mainMenu = new Button("main menu");
        restart.setLayoutX(900);
        restart.setLayoutY(500);
        mainMenu.setLayoutX(900);
        mainMenu.setLayoutY(540);
        restart.setPrefHeight(20);
        restart.setPrefWidth(150);
        mainMenu.setPrefWidth(150);
        mainMenu.setPrefHeight(20);
        restart.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    for(int i=0;i<8;i++) {
                        int lastIndex = ((Pane) (App.getStage().getScene().getRoot())).getChildren().size() - 1;
                        ((Pane) (App.getStage().getScene().getRoot())).getChildren().remove(lastIndex);
                    }
                    App.runGame(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        mainMenu.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    for(int i=0;i<8;i++) {
                        int lastIndex = ((Pane) (App.getStage().getScene().getRoot())).getChildren().size() - 1;
                        ((Pane) (App.getStage().getScene().getRoot())).getChildren().remove(lastIndex);
                    }
                    App.runMainMenu();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        ((Pane)(App.getStage().getScene().getRoot())).getChildren().add(restart);
        ((Pane)(App.getStage().getScene().getRoot())).getChildren().add(mainMenu);
        ProgressBar progress = new ProgressBar();
        progress.setPrefWidth(bossHealthBar.getPrefWidth());
        progress.setPrefHeight(bossHealthBar.getPrefHeight());
        progress.setLayoutX(800 - progress.getPrefWidth());
        progress.setLayoutY(450 - progress.getPrefHeight());
        progress.setProgress(1 - bossHealthBar.getProgress());
        progress.setStyle("-fx-accent: green;");
        ((Pane)(App.getStage().getScene().getRoot())).getChildren().add(progress);
        Text text = new Text(String.valueOf(DataBase.getInstance().getLoggedInUser().getScore()));
        text.setX(820);
        text.setY(400);
        text.setStyle("-fx-font-size: 40;");
        text.setStyle("-fx-font-weight: bold;");
        ((Pane)(App.getStage().getScene().getRoot())).getChildren().add(text);
    }
    private void setQuoteText(){
        ImageView imageView = new ImageView("/images/White-blank.jpg");
        ((Pane)(App.getStage().getScene().getRoot())).getChildren().add(imageView);
        Text text = new Text();
        Random random = new Random();
        int key = random.nextInt(7);
        if(key == 0){
            text.setText(Quote.PARENT.getQuote());
        }else if(key == 1){
            text.setText(Quote.SIBLINGS.getQuote());
        }else if(key == 2){
            text.setText(Quote.SANDWICH.getQuote());
        }else if(key == 3){
            text.setText(Quote.EAT.getQuote());
        }else if(key == 4){
            text.setText(Quote.KILL.getQuote());
        }else if(key == 5){
            text.setText(Quote.TWO.getQuote());
        }else{
            text.setText(Quote.LAST.getQuote());
        }
        text.setX(0);
        text.setY(350);
        text.setStyle("-fx-font-size: 20;");
        ((Pane)(App.getStage().getScene().getRoot())).getChildren().add(text);
    }
}
