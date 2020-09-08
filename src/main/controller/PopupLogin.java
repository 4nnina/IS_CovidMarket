package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.model.Utente;

public class PopupLogin extends Popup<Utente>
{
    @FXML private Button loginButton;
    @FXML private Button registerButton;

    @FXML private TextField nomeUtenteTextField;
    @FXML private PasswordField passwordField;

    @FXML private CheckBox adminCheckBox;

    public PopupLogin() {
        super("../resources/fxml/popup_login.fxml");
    }

    @Override
    protected Popup<Utente> self() {
        return this;
    }

    @FXML
    void loginButtonPress(ActionEvent event)
    {
        // TODO: Valida l'utente
        close(new Utente.Builder().build());
    }

    @FXML
    void registerButtonPress(ActionEvent event)
    {
        // TODO: Crea l'utente
        close(null);
    }
}
