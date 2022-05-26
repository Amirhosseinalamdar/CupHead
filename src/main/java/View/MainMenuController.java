package View;

import Application.App;
import Controller.GameController;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MainMenuController {


    public void exit(MouseEvent mouseEvent) {
        App.exit();
    }

    public void enterSetting(MouseEvent mouseEvent) throws IOException {
        App.runSetting();
    }

    public void enterProfileMenu(MouseEvent mouseEvent) throws IOException {
        App.runProfileMenu();
    }

    public void enterRankings(MouseEvent mouseEvent) throws IOException {
        App.runRankings();
    }

    public void startNewGame(MouseEvent mouseEvent) throws IOException {
        App.runGame(false);
    }
}
