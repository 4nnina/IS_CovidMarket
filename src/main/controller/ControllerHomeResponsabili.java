package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitMenuButton;
import main.model.Responsabile;
import main.model.Utente;

public class ControllerHomeResponsabili extends Controller
{

    @FXML
    private Button catalogoButton;

    @FXML
    private Button inscerisciProdButton;

    @FXML
    private Button speseButton;

    @FXML
    private ComboBox<?> repartoComboBox;

    @FXML
    private ComboBox<?> tipoComboBox;

    @FXML
    private ComboBox<?> marcaComboBox;

    @FXML
    private ComboBox<?> allergeniComboBox;

    @FXML
    private ComboBox<?> altroComboBox;

    @FXML
    private SplitMenuButton ordinaSpliMenuButton;

    @FXML
    private Button filtraButton;

    private Responsabile responsabile;

    // Costruttore
    public void setResponsabile(Responsabile responsabile) {
        this.responsabile = responsabile;
    }

    @FXML
    private void initialize(){

        //handler
        //TODO

    }
}
