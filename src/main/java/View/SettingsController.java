package View;

import Application.App;
import Controller.GameController;
import Model.DataBase;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class SettingsController {
    public Button blackButton;
    private Button isDevilMode;
    private boolean isBlack;

    public SettingsController() {
        isBlack = false;
    }

    @FXML
    private Button muteButton;

    public void back(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        App.runMainMenu();
    }

    public void setToDevilMode(javafx.scene.input.MouseEvent mouseEvent) {
        setOnHard(mouseEvent);
        DataBase.getInstance().getLoggedInUser().setDevilMode(true);
    }

    public void setOnHard(javafx.scene.input.MouseEvent mouseEvent) {
        DataBase.getInstance().getLoggedInUser().setHealth(2);
        DataBase.getInstance().getLoggedInUser().setWeakness(1.5);
        DataBase.getInstance().getLoggedInUser().setPower(0.5);
    }

    public void setOnNormal(javafx.scene.input.MouseEvent mouseEvent) {
        DataBase.getInstance().getLoggedInUser().setHealth(5);
        DataBase.getInstance().getLoggedInUser().setWeakness(1);
        DataBase.getInstance().getLoggedInUser().setPower(1);
    }

    public void setOnEasy(javafx.scene.input.MouseEvent mouseEvent) {
        DataBase.getInstance().getLoggedInUser().setHealth(10);
        DataBase.getInstance().getLoggedInUser().setWeakness(0.5);
        DataBase.getInstance().getLoggedInUser().setPower(1.5);
    }

    public void dark(javafx.scene.input.MouseEvent mouseEvent) {
        if(!isBlack) {
            GameController.getInstance().getColorAdjust().setSaturation(-1);
            GameController.getInstance().getColorAdjust().setBrightness(-0.2);
            blackButton.setText("colorful");
            isBlack = true;
        }else{
            GameController.getInstance().setColorAdjust(new ColorAdjust());
            blackButton.setText("dark");
            isBlack = false;
        }
    }

    public void mute(MouseEvent mouseEvent) {
        if(!GameController.getInstance().getMenuMediaPlayer().isMute()) {
            GameController.getInstance().getBombShots().setMute(true);
            GameController.getInstance().getBulletShots().setMute(true);
            GameController.getInstance().getBossShots().setMute(true);
            GameController.getInstance().getMediaPlayer().setMute(true);
            GameController.getInstance().getMenuMediaPlayer().setMute(true);
            muteButton.setText("unmute");
        }else{
            GameController.getInstance().getBombShots().setMute(false);
            GameController.getInstance().getBulletShots().setMute(false);
            GameController.getInstance().getBossShots().setMute(false);
            GameController.getInstance().getMediaPlayer().setMute(false);
            GameController.getInstance().getMenuMediaPlayer().setMute(false);
            muteButton.setText("mute");
        }
    }
}
