package main.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import main.Program;

public class ControllerUserHome extends Controller2<Program.SectionUtente>
{
    @FXML private Pane gatePane;

    @Override
    public void onSwapPane(Object data) {

    }

    @Override
    public Pane getGatePane() {
        return gatePane;
    }
}
