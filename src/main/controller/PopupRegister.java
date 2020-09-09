package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import main.model.MetodoPagamento;
import main.model.Utente;
import main.storage.Database;
import main.utils.Validator;

import java.net.URL;
import java.util.ResourceBundle;

public class PopupRegister extends Popup<Utente>
{
    @FXML private TextField nomeTextField;
    @FXML private TextField cognomeTextField;
    @FXML private TextField indirizzoTextField;
    @FXML private TextField telefonoTextField;
    @FXML private TextField mailTextField;

    @FXML private Label capLabel;
    @FXML private Label tesseraFedeltàLabel;

    @FXML private PasswordField pswPasswordField;
    @FXML private PasswordField confermaPswPasswordField;

    @FXML private ImageView covidMarketImageView;

    @FXML private RadioButton siRadioButton;
    @FXML private RadioButton noRadioButton;

    @FXML private ChoiceBox<?> cittaChoiceBox;
    @FXML private ChoiceBox<?> pagamentoChoiceBox;

    public PopupRegister() {
        super("../resources/fxml/popup_registrazione.fxml");
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
    private void salvaButtonPress(ActionEvent actionEvent)
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

            close(user);
        }
    }

    @Override
    protected Popup<Utente> self() {
        return this;
    }
}
