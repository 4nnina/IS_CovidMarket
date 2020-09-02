package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.image.ImageView;
import main.model.Utente;

public class ControllerHome extends Controller
{

    @FXML
    private ImageView carrelloImageView;

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

    private Utente utente;

    // Costruttore
    public void setUser(Utente utente) {
        this.utente = utente;
    }

    @FXML
    private void initialize(){

        //handler
        //TODO

    }

}
