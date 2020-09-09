package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import main.model.Responsabile;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerRespDashboard extends Controller<SectionResp> implements Initializable
{
    @FXML private Pane gatePane;

    @FXML private ImageView homeImageView;
    @FXML private Button speseButton;
    @FXML private Label usernameLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
    }

    @Override
    public void onSwapPane(Object data)
    {
        Responsabile responsabile = (Responsabile) data;
        usernameLabel.setText(responsabile.getNome());

        // Setta come prima cosa la home
        dashboard.swap(SectionResp.Home, data);
    }

    @FXML
    void speseButtonPress(ActionEvent event)
    {

    }

    @Override
    public Pane getGatePane() {
        return gatePane;
    }
}
