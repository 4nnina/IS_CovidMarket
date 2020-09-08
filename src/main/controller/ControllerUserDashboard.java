package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import main.Program;
import main.utils.Dashboard;

public class ControllerUserDashboard extends Controller2<Program.SectionUtente>
{
    @FXML private Button profileBtn;
    @FXML private Button homeBtn;

    @FXML private Pane gatePane;

    @Override
    public void onSwapPane(Object data) {

    }

    @Override
    public Pane getGatePane() {
        return gatePane;
    }

    @FXML
    void homeBtnHandle(ActionEvent event) {
        dashboard.swap(Program.SectionUtente.Home, null);
    }

    @FXML
    void profileBtnHandle(ActionEvent event) {
        dashboard.swap(Program.SectionUtente.Profilo, null);
    }
}
