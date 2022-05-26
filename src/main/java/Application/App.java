package Application;

import Controller.GameController;
import Model.DataBase;
import View.Component.Boss1;
import View.Component.Plane;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class App extends Application {
    private static Stage stage;
    public static void main(String[] args){
        GameController.getInstance().getMenuMediaPlayer().play();
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        URL firstMenuAddress =  new URL(App.class.getResource("../fxml/firstMenu.fxml").toExternalForm());
        stageShower(firstMenuAddress);
    }

    public static void runMainMenu() throws IOException {
        GameController.getInstance().getMediaPlayer().stop();
        GameController.getInstance().getMenuMediaPlayer().play();
        URL mainMenuAddress = new URL(App.class.getResource("../fxml/mainMenu.fxml").toExternalForm());
        stageShower(mainMenuAddress);
    }
    public static void runProfileMenu() throws IOException {
        URL profileMenuAddress = new URL(App.class.getResource("../fxml/profileMenu.fxml").toExternalForm());
        stageShower(profileMenuAddress);
    }
    public static void runSetting() throws IOException {
        URL settingAddress = new URL(App.class.getResource("../fxml/settings.fxml").toExternalForm());
        stageShower(settingAddress);
    }
    public static void runRankings() throws IOException {
        URL rankingsAddress = new URL(App.class.getResource("../fxml/rankings.fxml").toExternalForm());
        stageShower(rankingsAddress);
    }



    public static void runGame(boolean isRestarted) throws IOException{
        URL gameAddress = new URL(App.class.getResource("../fxml/game.fxml").toExternalForm());
        Pane root = FXMLLoader.load(gameAddress);
        if(GameController.getInstance().isIspause()){
            GameController.getInstance().setIspause(false);
            GameController.getInstance().setFinished(false);
                Plane.getInstance().setHealth(DataBase.getInstance().getLoggedInUser().getHealth());
                Boss1.getInstance().setHealth(1000);
                Boss1.getInstance().getBoss1Transition().setBossPhase(1);
                Plane.getInstance().setBlinked(false);
                DataBase.getInstance().getLoggedInUser().setScore(-DataBase.getInstance().getLoggedInUser().getScore());
        }
    }

    private static void stageShower(URL url) throws IOException {
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public static void exit(){
        stage.close();
    }

    public static Stage getStage() {
        return stage;
    }
}
