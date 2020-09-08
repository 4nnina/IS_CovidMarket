package main.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import main.Program;
import main.model.Utente;
import main.utils.Dashboard;

public class ControllerUserDashboard extends Controller2<SectionUser>
{
    @FXML private Label usernameLabel;
    @FXML private ChoiceBox<SectionUser> sectionChoiceBox;

    @FXML private Pane gatePane;

    @Override
    public void onSwapPane(Object data)
    {
        Utente user = (Utente) data;
        usernameLabel.setText(user.getNome());

        // Possibili selezioni
        sectionChoiceBox.getItems().addAll(SectionUser.values());
        sectionChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> {
            switch (t1.intValue())
            {
                case 0: dashboard.swap(SectionUser.Home,      data); break;
                case 1: dashboard.swap(SectionUser.Carrello,  data); break;
            }
            sectionChoiceBox.getSelectionModel().select(null);
        });

        // Setta come prima cosa la home
        dashboard.swap(SectionUser.Home, data);
    }

    @Override
    public Pane getGatePane() {
        return gatePane;
    }
}
