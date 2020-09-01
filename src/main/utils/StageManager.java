package main.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.controller.ControllerHome;
import main.controller.ControllerHomeResponsabili;
import main.controller.ControllerLoginPage;
import main.controller.ControllerRegistrazione;

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

    public void setStageRegistrazione(Stage window){
        Parent root;

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/fxml/registrazioneUtente.fxml"));

            root = fxmlLoader.load();
            ControllerRegistrazione controllerRegistrazione = fxmlLoader.getController();
            window.setScene(new Scene(root));
            window.setTitle("Covid Market - Registrazione");
            //schermata.getIcons().add(new Image(getClass().getResource("../resources/img/logo.png").toExternalForm()));
            window.show();

        }catch(Exception exc){
            exc.printStackTrace();
        }
    }

    public void setStageHomePersonale(Stage window) {
        Parent root;

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/fxml/homeResponsanili.fxml"));

            root = fxmlLoader.load();
            ControllerHomeResponsabili controllerHomeResponsabili = fxmlLoader.getController();
            window.setScene(new Scene(root));
            window.setTitle("Covid Market - HomePersonale");
            //schermata.getIcons().add(new Image(getClass().getResource("../resources/img/logo.png").toExternalForm()));
            window.show();

        }catch(Exception exc){
            exc.printStackTrace();
        }
    }

    public void setStageHomeUtenti(Stage window) {
        Parent root;

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../resources/fxml/home.fxml"));

            root = fxmlLoader.load();
            ControllerHome controllerHome = fxmlLoader.getController();
            window.setScene(new Scene(root));
            window.setTitle("Covid Market - Home");
            //schermata.getIcons().add(new Image(getClass().getResource("../resources/img/logo.png").toExternalForm()));
            window.show();

        }catch(Exception exc){
            exc.printStackTrace();
        }
    }
}
