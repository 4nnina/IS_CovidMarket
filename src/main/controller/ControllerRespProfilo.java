package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import main.model.*;

import java.util.EnumSet;

public class ControllerRespProfilo extends Controller {

    @FXML private ImageView covidMarketImageView;

    @FXML private ChoiceBox<String> sezioneChoicebox;

    @FXML private Button speseButton;

    @FXML private Label nomeutenteLabel;
    @FXML private Label nomeLabel;
    @FXML private Label cognomeLabel;
    @FXML private Label dataaNascitaLabel;
    @FXML private Label matricolaLabel;
    @FXML private Label usernameLabel;
    @FXML private Label mailLabel;
    @FXML private Label telefonoLabel;

    @FXML private VBox repartiVBox;

    @FXML
    private void initialize()
    {
        covidMarketImageView.setOnMouseClicked(this::homeHandler);

        sezioneChoicebox.getItems().setAll("Logout");
        sezioneChoicebox.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> {
            switch (t1.intValue()) {
                case 1: stageManager.swap(Stages.Login);       break;
            }
        });

        for(Reparto rep : Reparto.values())
            if(!rep.equals(Reparto.Tutto)) {
                repartiVBox.getChildren().add(new CheckBox(rep.toString()));
            }

    }

    private void homeHandler(MouseEvent mouseEvent) { stageManager.swap(Stages.HomeResponsabile); }

    @FXML
    void speseButtonHandler(ActionEvent event) { stageManager.swap(Stages.SpesaResponsabile); }


    private Responsabile currentResp;

    @Override
    public void onSwap(Persona target)
    {
        this.currentResp = (Responsabile) target;
        nomeutenteLabel.setText(currentResp.getNome());

        nomeLabel.setText(currentResp.getNome());
        cognomeLabel.setText(currentResp.getCognome());
        usernameLabel.setText(currentResp.getUsername());
        dataaNascitaLabel.setText(currentResp.getDataDiNascita().toString());
        matricolaLabel.setText(currentResp.getMatricola());
        mailLabel.setText(currentResp.getEmail());
        telefonoLabel.setText(currentResp.getTelefono());

        repartiVBox.getChildren().clear();
        // Popola Reparti possibili
        EnumSet<Reparto> reparti = currentResp.getRepartiGestiti();
        repartiVBox.setPrefHeight(Reparto.values().length * 20);
        for(Reparto rep : Reparto.values()) {
            if(!rep.equals(Reparto.Tutto)){
                CheckBox check = new CheckBox(rep.toString());
                if(reparti.contains(rep))
                    check.setSelected(true);
                check.setDisable(true);
                repartiVBox.getChildren().add(check);
            }
        }

        sezioneChoicebox.getSelectionModel().select(null);
    }

}
