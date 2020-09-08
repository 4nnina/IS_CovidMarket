package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import main.model.Persona;
import main.model.Utente;

import javax.swing.*;

public class ControllerProfilo extends Controller {

    @FXML
    private ImageView covidMarketImageView;

    @FXML
    private ImageView carrelloImageView;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label nomeLabel;

    @FXML
    private Label cognomeLabel;

    @FXML
    private Label capLabel;

    @FXML
    private Label indirizzoLabel;

    @FXML
    private Label cittaLabel;

    @FXML
    private Label mailLabel;

    @FXML
    private Label pagamentoLabel;

    @FXML
    private Label telefonoLabel;

    @FXML
    private Label tesseraLabel;

    @FXML
    private Label puntiLabel;

    @FXML private ChoiceBox sezioneChoicebox;

    private Utente currentUser;


    @FXML
    private void initialize() {

        //handler
        covidMarketImageView.setOnMouseClicked(this::homeHandler);
        //TODO

        // Cambia schermata in base alla scelta
        sezioneChoicebox.getItems().setAll("Profilo", "Tessera Fedelta", "Storico Spese", "Logout");
        sezioneChoicebox.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> {
            switch (t1.intValue()) {
                case 0:
                    stageManager.swap(Stages.Profilo);
                    break;
                case 1:
                    stageManager.swap(Stages.Tessera);
                    break;
                case 2:
                    stageManager.swap(Stages.SpesaUtente);
                    break;
                case 3:
                    stageManager.swap(Stages.Login);
                    break;
            }
        });

    }

    @Override
    public void onSwap(Persona target)
    {
        this.currentUser = (Utente)target;
        usernameLabel.setText(currentUser.getNome());

        nomeLabel.setText(currentUser.getNome());
        cognomeLabel.setText(currentUser.getCognome());
        indirizzoLabel.setText(currentUser.getIndirizzo());
        cittaLabel.setText(currentUser.getCitta());
        capLabel.setText(String.valueOf(currentUser.getCAP()));
        telefonoLabel.setText(currentUser.getTelefono());
        mailLabel.setText(currentUser.getEmail());
        pagamentoLabel.setText(currentUser.getMetodoPagamento().toString());
        tesseraLabel.setText(currentUser.getCartaFedelta().ID);
        puntiLabel.setText(String.valueOf(currentUser.getCartaFedelta().punti));

        // Deseleziona menu
        sezioneChoicebox.getSelectionModel().select(null);
    }

    private void homeHandler(MouseEvent mouseEvent) {
        stageManager.swap(Stages.HomeUtente);
    }

    @FXML
    private void modificaButtonHandler(ActionEvent event) {
        stageManager.swap(Stages.ModificaUtente);
    }
}
