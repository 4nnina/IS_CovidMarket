package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.Database;
import main.model.Responsabile;
import main.model.Utente;

import java.util.ArrayList;

public class ControllerLoginPage {

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
    private void initialize(){
        utenti = new ArrayList<>(Database.getInstance().getUtenti());
        responsabili = new ArrayList<>(Database.getInstance().getResponsabili());

        //handler
        loginButton.setOnAction(this::loginButtonHandler);
        registratiButton.setOnAction((this::registratiButtonHandler));

    }

    private void registratiButtonHandler(ActionEvent actionEvent) {
        System.out.println("registrati");

        //TODO: implemetare registrati, cambio
    }

    private void loginButtonHandler(ActionEvent actionEvent) {
        System.out.println("login");
        
        if(personaleCheckBox.isSelected()){
            //TODO: implemetare login personale, cambio
        }
        else{
            //TODO: implemetare login utente, cambio
    }
        }


}
