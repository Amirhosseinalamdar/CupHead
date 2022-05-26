package View;

import Application.App;
import Controller.GameController;
import Model.DataBase;
import Model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sun.applet.Main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;


public class FirstMenuController {

    public TextField username;
    public TextField password;



    public void enterMainMenu(MouseEvent mouseEvent) throws IOException {
        DataBase.getInstance().addUser("guestskjd;lfksdj;lkfj","jshvvcjijkm,e",true);
        App.runMainMenu();
    }

    public void login(MouseEvent mouseEvent) throws IOException {
        if(DataBase.getInstance().getUserByName(username.getText()) == null ||
                !DataBase.getInstance().getUserByName(username.getText()).getPassword().equals(password.getText())) {
            password.setStyle("-fx-border-color: red;");
            username.setStyle("-fx-border-color: red;");
        }else{
            DataBase.getInstance().getUserByName(username.getText()).setLoggedIn(true);
            App.runMainMenu();
        }
    }

    public void register(MouseEvent mouseEvent) throws IOException {
        DataBase.getInstance().addUser(username.getText(),password.getText(),true);
        App.runMainMenu();
    }
}
