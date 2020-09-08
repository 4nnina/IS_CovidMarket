package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.stage.Stage;
import main.model.MetodoPagamento;
import main.model.Utente;
import main.storage.Database;
import main.utils.StageManager;
import main.utils.Validator;

import java.awt.*;
import java.util.ArrayList;

public class ControllerRegistrazione extends Controller
{

    @FXML
    private TextField nomeTextField;

    @FXML
    private TextField cognomeTextField;

    @FXML
    private TextField indirizzoTextField;

    @FXML
    private TextField telefonoTextField;

    @FXML
    private Label capLabel;

    @FXML
    private TextField mailTextField;

    @FXML
    private PasswordField pswPasswordField;

    @FXML
    private PasswordField confermaPswPasswordField;

    @FXML
    private ImageView covidMarketImageView;

    @FXML
    private RadioButton siRadioButton;

    @FXML
    private RadioButton noRadioButton;

    @FXML
    private Label tesseraFedeltàLabel;

    @FXML
    private Button salvaButton;

    @FXML
    private ChoiceBox<?> cittaChoiceBox;

    @FXML
    private ChoiceBox<?> pagamentoChoiceBox;

    @FXML
    private void initialize(){
        //handler
        salvaButton.setOnAction(this::salvaButtonHandler);
        covidMarketImageView.setOnMouseClicked(this::loginHandler);
        //TODO
    }

    private void loginHandler(MouseEvent mouseEvent) {
        stageManager.swap(Stages.Login);
    }

    // Controlla se i parametri inseriti vanno bene
    private boolean validateUserData()
    {
        boolean result = true;

        if (!Validator.isAlphanumeric(nomeTextField.getText()))
        {
            nomeTextField.setStyle("-fx-control-inner-background:red");
            result = false;
        }
        if (!Validator.isAlphanumeric(cognomeTextField.getText()))
        {
            cognomeTextField.setStyle("-fx-control-inner-background:red");
            result = false;
        }
        if (!Validator.isTelephoneNumber(telefonoTextField.getText()))
        {
            telefonoTextField.setStyle("-fx-control-inner-background:red");
            result = false;
        }
        if (!Validator.isEmail(mailTextField.getText()))
        {
            mailTextField.setStyle("-fx-control-inner-background:red");
            result = false;
        }
        if(pswPasswordField.getText().isEmpty())
        {
            pswPasswordField.setStyle("-fx-control-inner-background:red");
            result = false;
        }
        if(confermaPswPasswordField.getText().isEmpty() ||
                !confermaPswPasswordField.getText().equals(pswPasswordField.getText()))
        {
            confermaPswPasswordField.setStyle("-fx-control-inner-background:red");
            result = false;
        }
        if (!Validator.isAddressFormat(indirizzoTextField.getText()))
        {
            indirizzoTextField.setStyle("-fx-control-inner-background:red");
            result = false;
        }

        /*
        int tmp = 0;
        try{tmp = Integer.valueOf(capLabel.getText()); } catch (NumberFormatException e)
        {
            capLabel.setStyle("-fx-control-inner-background:red");
            result = false;
        }
        */

        return result;
    }

    @FXML
    private void salvaButtonHandler(ActionEvent actionEvent)
    {
        //TODO
        System.out.println("premuto salva");

        if (validateUserData())
        {
            Utente user = new Utente.Builder()
                    .setNominativo(nomeTextField.getText(), cognomeTextField.getText())
                    .setIndirizzo(indirizzoTextField.getText(), "arzignano", "0")
                    .setTelefono(telefonoTextField.getText())
                    .setEmail(mailTextField.getText())
                    .setPassword(pswPasswordField.getText())
                    .setCartaFedelta(null)
                    .setMetodoPagamento(MetodoPagamento.Nessuno)
                    .build();

            Database database = Database.getInstance();
            if(database.getUtenti().add(user))
            {
                // Ha inserito con successo
                stageManager.setTargetUser(user);
                stageManager.swap(Stages.HomeUtente);
            }
            else {
                System.out.println("UTENTE ESISTE GIA");
            }
        }
    }
}
