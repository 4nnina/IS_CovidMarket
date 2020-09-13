package main.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import main.storage.Database;
import main.model.Responsabile;
import main.model.Utente;
import main.utils.StageManager;

import javax.xml.crypto.Data;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class ControllerLoginPage extends Controller {

    @FXML private Button loginButton;
    @FXML private TextField nomeUtenteTextField;
    @FXML private PasswordField pswPasswordField;
    @FXML private Button registratiButton;
    @FXML private CheckBox personaleCheckBox;
    @FXML private Label titleLabel;

    @FXML
    private void initialize()
    {
        //handler
        loginButton.setOnAction(this::loginButtonHandler);
        registratiButton.setOnAction((this::registratiButtonHandler));
        personaleCheckBox.setOnAction(this::personaleHandler);
    }

    private void personaleHandler(ActionEvent actionEvent) {
        if(personaleCheckBox.isSelected())
            titleLabel.setText("Username");
        else
            titleLabel.setText("Email");
    }

    private void registratiButtonHandler(ActionEvent actionEvent) {
        System.out.println("registrati");

        stageManager.swap(Stages.Registrazione);

        //TODO: implemetare registrati, cambio
    }

    private void loginButtonHandler(ActionEvent actionEvent)
    {
        Database database = Database.getInstance();
        System.out.println("login");

        if(nomeUtenteTextField.getText().isEmpty()){
            if(pswPasswordField.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.WARNING, "Inserire nome utente e password");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Campo email/nome utente vuoto");
                alert.showAndWait();
            }
        }
        else if(pswPasswordField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Inserire la password");
            alert.showAndWait();
        }
        else if(personaleCheckBox.isSelected())
        {

            boolean anyFound = false;
            for (Responsabile user : database.getResponsabili()) {
                switch (user.validLogin(nomeUtenteTextField.getText(), pswPasswordField.getText()))
                {
                    // Cambia schermata
                    case Success: {
                        titleLabel.setText("Email");
                        nomeUtenteTextField.clear();
                        pswPasswordField.clear();
                        personaleCheckBox.setSelected(false);
                        stageManager.setTargetUser(user);
                        stageManager.swap(Stages.HomeResponsabile);
                        anyFound = true;
                    } break;

                    case WrongPassword:
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Password errata per " + nomeUtenteTextField.getText());
                        alert.showAndWait();
                        anyFound = true;
                        break;

                    case Failure:
                        break;
                }
            }

            if (!anyFound) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Non esistono responsabili con username " + nomeUtenteTextField.getText());
                alert.showAndWait();
            }

        }
        else {

            boolean anyFound = false;
            for (Utente user : database.getUtenti()) {
                switch (user.validLogin(nomeUtenteTextField.getText(), pswPasswordField.getText()))
                {
                    // Cambia schermata
                    case Success: {
                        nomeUtenteTextField.clear();
                        pswPasswordField.clear();
                        personaleCheckBox.setSelected(false);
                        stageManager.setTargetUser(user);
                        stageManager.swap(Stages.HomeUtente);
                        anyFound = true;
                    } break;

                    case WrongPassword:
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Password errata per " + nomeUtenteTextField.getText());
                        alert.showAndWait();
                        anyFound = true;
                        break;

                    case Failure:
                        break;
                }
            }

            if (!anyFound) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Non esistono utenti con email " + nomeUtenteTextField.getText());
                alert.showAndWait();
            }
        }
    }
}
