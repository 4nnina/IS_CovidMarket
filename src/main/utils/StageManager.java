package main.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.controller.ControllerLoginPage;

public class StageManager {

    public void setStageLogin(Stage schermata){
        Parent root;

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/fxml/login_page.fxml"));

            root = fxmlLoader.load();
            ControllerLoginPage loginDialog = fxmlLoader.getController();
            schermata.setScene(new Scene(root));
            schermata.setTitle("Covid Market");
            schermata.getIcons().add(new Image(getClass().getResource("../resources/img/logo.png").toExternalForm()));
            schermata.show();

        }catch(Exception exc){
            exc.printStackTrace();
        }
    }
}
