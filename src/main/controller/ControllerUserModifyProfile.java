package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import main.model.MetodoPagamento;
import main.model.Utente;
import main.utils.Validator;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerUserModifyProfile extends Controller<SectionUser> implements Initializable
{
    @FXML private Pane gatePane;

    @FXML private TextField numberField;
    @FXML private TextField emailField;
    @FXML private TextField nameField;
    @FXML private TextField surnameField;
    @FXML private TextField addressField;

    @FXML private Label capLabel;

    @FXML private ComboBox<String> cittaCombobox;
    @FXML private ComboBox<MetodoPagamento> paymentComboBox;

    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;

    private Utente currentUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        paymentComboBox.getItems().addAll(MetodoPagamento.values());
    }

    @Override
    public void onSwapPane(Object data)
    {
        this.currentUser = (Utente) data;

        nameField.setText(currentUser.getNome());
        surnameField.setText(currentUser.getCognome());
        addressField.setText(currentUser.getIndirizzo());
        //cittaCombobox.getSelectionModel().select(...);
        capLabel.setText(String.valueOf(currentUser.getCAP()));
        numberField.setText(currentUser.getTelefono());
        emailField.setText(currentUser.getEmail());
        paymentComboBox.getSelectionModel().select(currentUser.getMetodoPagamento());
    }

    @FXML
    void modifyButtonPress(ActionEvent event)
    {
        if (validateUserData())
        {
            currentUser.setPagamento(paymentComboBox.getSelectionModel().getSelectedItem());
            currentUser.setIndirizzo(addressField.getText(), "arzignano", capLabel.getText());
            currentUser.setTelefono(numberField.getText());
            currentUser.setEmail(emailField.getText());

            if(!passwordField.getText().isEmpty())
                currentUser.setPassword(passwordField.getText());

            // Torna al profilo
            dashboard.swap(SectionUser.Profilo, currentUser);
        }
    }

    private boolean validateUserData()
    {
        boolean result = true;

        if (!Validator.isTelephoneNumber(numberField.getText()))
        {
            numberField.setStyle("-fx-control-inner-background:red");
            result = false;
        }
        if (!Validator.isEmail(emailField.getText()))
        {
            emailField.setStyle("-fx-control-inner-background:red");
            result = false;
        }

        if (!Validator.isAddressFormat(addressField.getText()))
        {
            addressField.setStyle("-fx-control-inner-background:red");
            result = false;
        }
        if(!confirmPasswordField.getText().equals(passwordField.getText()))
        {
            confirmPasswordField.setStyle("-fx-control-inner-background:red");
            result = false;
        }

        return result;
    }

    @Override
    public Pane getGatePane() {
        return gatePane;
    }
}
