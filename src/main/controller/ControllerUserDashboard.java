package main.controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import main.Program;
import main.model.Utente;
import main.utils.Dashboard;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerUserDashboard extends Controller2<SectionUser> implements Initializable
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

        sectionChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, sectionUser, newValue) -> {
                    if(newValue != null) {
                        dashboard.swap(newValue, data);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                sectionChoiceBox.getSelectionModel().clearSelection();
                            }
                        });
                    }
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
