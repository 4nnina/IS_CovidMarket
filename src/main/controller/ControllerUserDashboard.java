package main.controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import main.Program;
import main.model.Utente;
import main.utils.Dashboard;

public class ControllerUserDashboard extends Controller2<SectionUser>
{
    @FXML private Pane gatePane;

    @FXML private Label usernameLabel;
    @FXML private ChoiceBox<SectionUser> sectionChoiceBox;

    @Override
    public void onSwapPane(Object data)
    {
        Utente user = (Utente) data;
        usernameLabel.setText(user.getNome());

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
