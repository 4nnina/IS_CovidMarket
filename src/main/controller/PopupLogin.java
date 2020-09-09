package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.model.Persona;
import main.model.Responsabile;
import main.model.Utente;
import main.storage.Database;
import main.utils.AlertPopup;

import java.util.Optional;

public class PopupLogin extends Popup<Persona>
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
    protected Popup<Persona> self() {
        return this;
    }

    @FXML
    void loginButtonPress(ActionEvent event)
    {
        Database database = Database.getInstance();

        String username = nomeUtenteTextField.getText();
        String password = passwordField.getText();

        // Se amministratore
        if (adminCheckBox.isSelected())
        {
            for(Responsabile resp : database.getResponsabili())
                switch (resp.validLogin(username, password))
                {
                    case Success:
                        close(resp);
                        break;

                    case WrongPassword:
                        AlertPopup.warning("Password sbagliata per " + username);
                        break;

                    case Failure:
                        AlertPopup.warning("Responsabile " + username + " non esiste");
                        break;
                }

        }
        // Se utente
        else
        {
            for(Utente user : database.getUtenti())
                switch (user.validLogin(username, password))
                {
                    case Success:
                        close(user);
                        break;

                    case WrongPassword:
                        AlertPopup.warning("Password sbagliata per " + username);
                        break;

                    case Failure:
                        AlertPopup.warning("Utente " + username + " non esiste");
                        break;
                }
        }
    }

    @FXML
    void registerButtonPress(ActionEvent event)
    {
        PopupRegister popupRegister = new PopupRegister();
        Optional<Utente> optUtente = popupRegister.show();
        if (optUtente.isPresent())
        {
            // Aggiunge al database
            Database database = Database.getInstance();
            if(!database.getUtenti().add(optUtente.get()))
            {
                System.out.println("UTENTE ESISTE GIA");
            }

            close(optUtente.get());
        }
    }
}
