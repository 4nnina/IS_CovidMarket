package main.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import main.Program;
import main.model.Utente;
import main.utils.Dashboard;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerUserDashboard extends Controller<SectionUser> implements Initializable
{
    @FXML private Pane gatePane;

    @FXML private Label usernameLabel;

    @FXML private ChoiceBox<SectionUser> sectionChoiceBox;
    @FXML private ImageView carrelloImageView;
    @FXML private ImageView homeImageView;

    private Utente currentUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        // Porta al carrello quando cliccato
        carrelloImageView.setOnMouseClicked(
                mouseEvent -> dashboard.swap(SectionUser.Carrello, currentUser));

        // Porta lla home quando cliccata
        homeImageView.setOnMouseClicked(
                mouseEvent -> dashboard.swap(SectionUser.Home, currentUser));
    }

    @Override
    public void onSwapPane(Object data)
    {
        this.currentUser = (Utente) data;
        usernameLabel.setText(currentUser.getNome());

        // Possibili selezioni visibili nel menu
        for(Pair<SectionUser, Dashboard.StageData<SectionUser>> entry : dashboard.getStages())
            if (entry.getValue().menuLink)
                sectionChoiceBox.getItems().add(entry.getKey());
        sectionChoiceBox.getItems().add(SectionUser.Logout);

        sectionChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, sectionUser, newValue) ->
                {
                    // Cambia schermata
                    if(newValue != null && newValue != SectionUser.Logout) {
                        dashboard.swap(newValue, data);
                        Platform.runLater(() -> sectionChoiceBox.getSelectionModel().clearSelection());
                    }

                    // Logout
                    if(newValue == SectionUser.Logout)
                        Program.login(dashboard.getPrimaryStage());
                }
        );

        // Setta come prima cosa la home
        dashboard.swap(SectionUser.Home, data);
    }

    @Override
    public Pane getGatePane() {
        return gatePane;
    }
}
