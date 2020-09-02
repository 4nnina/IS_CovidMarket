package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.storage.Database;
import main.model.Responsabile;
import main.model.Utente;
import main.utils.StageManager;

import java.util.ArrayList;

public class ControllerLoginPage extends Controller {

    @FXML
    private Button loginButton;

    @FXML
    private TextField nomeUtenteTextField;

    @FXML
    private PasswordField pswPasswordField;

    @FXML
    private Button registratiButton;

    @FXML
    private CheckBox personaleCheckBox;


    private ArrayList<Utente> utenti;
    private ArrayList<Responsabile> responsabili;

    @FXML
    private void initialize()
    {
        utenti = new ArrayList<>(Database.getInstance().getUtenti());
        responsabili = new ArrayList<>(Database.getInstance().getResponsabili());

        //handler
        loginButton.setOnAction(this::loginButtonHandler);
        registratiButton.setOnAction((this::registratiButtonHandler));
    }

    private void registratiButtonHandler(ActionEvent actionEvent) {
        System.out.println("registrati");

        stageManager.swap(Stages.Registrazione);

        //TODO: implemetare registrati, cambio
    }

    private void loginButtonHandler(ActionEvent actionEvent) {
        System.out.println("login");


        if(personaleCheckBox.isSelected()){
            //TODO: implemetare login personale, cambio
            stageManager.swap(Stages.HomeResponsabile);
        }
        else{
            //TODO: implemetare login utente, cambio
            ControllerHome controllerHome = (ControllerHome) stageManager.swap(Stages.HomeUtente);
            //controllerHome.setUser(...);
        }
    }


}
