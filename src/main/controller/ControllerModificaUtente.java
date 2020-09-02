package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import main.model.Utente;

public class ControllerModificaUtente extends Controller{

    @FXML
    private ImageView covidMarketImageView;

    @FXML
    private ImageView carrelloImageView;

    @FXML
    private Label nomeUtenteLabel;

    @FXML
    private TextField indirizzoTextField;

    @FXML
    private TextField telefonoLabel;

    @FXML
    private Label nomeLabel;

    @FXML
    private Label cognomeLabel;

    @FXML
    private Label capLabel;

    @FXML
    private ComboBox<?> cittaCombobox;

    @FXML
    private TextField mailTextField;

    @FXML
    private PasswordField pswTextField;

    @FXML
    private PasswordField controllopswTextField;

    @FXML
    private ComboBox<?> pagamentoComboBox;

    @FXML
    private Button modificaButton;

    private Utente utente;

    // Costruttore
    public void setUser(Utente utente) {
        this.utente = utente;
    }

    @FXML
    private void initialize(){

        //nomeUtenteLabel.setText(utente.getNome());

        //handler
        covidMarketImageView.setOnMouseClicked(this::loginHandler);
        //TODO

    }

    private void loginHandler(MouseEvent mouseEvent) {
        stageManager.swap(Stages.HomeUtente);
    }

}
