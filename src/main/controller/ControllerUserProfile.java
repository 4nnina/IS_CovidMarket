package main.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import main.Program;

public class ControllerUserProfile extends Controller2<Program.SectionUtente>
{
    @FXML private Pane gatePanel;

    @Override
    public void onSwapPane(Object data) {

    }

    @Override
    public Pane getGatePane() {
        return gatePanel;
    }
}
