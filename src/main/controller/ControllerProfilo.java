package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import main.model.Utente;

public class ControllerProfilo extends Controller{

    @FXML
    private ImageView covidMarketImageView;

    @FXML
    private ImageView carrelloImageView;

    @FXML
    private Label nomeutenteLabel;

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

    private Utente utente;

    /*
    // Costruttore
    
    public void setUser(Utente utente) {
        this.utente = utente;
    }
*/
    @FXML
    private void initialize(){

        //nomeutenteLabel.setText(utente.getNome());

        //handler
        covidMarketImageView.setOnMouseClicked(this::loginHandler);
        //TODO

    }

    private void loginHandler(MouseEvent mouseEvent) {
        stageManager.swap(Stages.HomeUtente);
    }

}
