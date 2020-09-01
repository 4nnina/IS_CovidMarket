package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import main.Database;
import main.model.Responsabile;
import main.model.Utente;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginDialog implements Initializable
{
    private ArrayList<Utente> utenti;
    private ArrayList<Responsabile> responsabili;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        utenti = new ArrayList<>(Database.getInstance().getUtenti());
        responsabili = new ArrayList<>(Database.getInstance().getResponsabili());
    }

    @FXML
    private void loginButton(ActionEvent event)
    {
        System.out.println("CIAO BESTIE");
    }
}
