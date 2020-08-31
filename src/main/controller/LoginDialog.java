package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import main.model.Barile;
import main.Database;
import main.model.Responsabile;
import main.model.Utente;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginDialog implements Initializable
{
    private Barile<Utente> utenti;
    private Barile<Responsabile> responsabili;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        utenti = Database.getInstance().utenti;
        responsabili = Database.getInstance().responsabili;
    }

    @FXML
    private void loginButton(ActionEvent event)
    {
        System.out.println("CIAO BESTIE");
    }
}
