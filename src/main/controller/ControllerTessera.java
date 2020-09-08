package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import main.model.Persona;
import main.model.Utente;

public class ControllerTessera extends Controller{

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
    private Label tesseraLabel;

    @FXML
    private Label dataEmissioneLabel;

    @FXML private ChoiceBox sezioneChoicebox;

    @FXML
    private Label puntiLabel;

    private Utente utente;

    /*// Costruttore
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

        // Cambia schermata in base alla scelta
        sezioneChoicebox.getItems().setAll("Profilo", "Tessera Fedelta", "Storico Spese", "Logout");
        sezioneChoicebox.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> {
            switch (t1.intValue()) {
                case 0: stageManager.swap(Stages.Profilo);     break;
                case 1: stageManager.swap(Stages.Tessera);     break;
                case 2: stageManager.swap(Stages.SpesaUtente); break;
                case 3: stageManager.swap(Stages.Login);       break;
            }
        });
    }

    @Override
    public void onSwap(Persona target)
    {
        // Deseleziona menu
        sezioneChoicebox.getSelectionModel().select(null);
    }

    private void loginHandler(MouseEvent mouseEvent) {
        stageManager.swap(Stages.HomeUtente);
    }

}
