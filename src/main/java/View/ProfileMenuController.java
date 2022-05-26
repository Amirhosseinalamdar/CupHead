package View;

import Application.App;
import Controller.GameController;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ProfileMenuController {

    public void exit(MouseEvent mouseEvent) throws IOException {
        App.runMainMenu();
    }
}
