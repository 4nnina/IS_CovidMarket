package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import main.model.Persona;
import main.model.Prodotto;
import main.storage.Database;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerHomeResponsabili extends Controller implements Initializable
{
    @FXML
    private Button speseButton;

    @FXML
    private Button filtraButton1;

    @FXML
    private TextField marcaTextField;

    @FXML
    private VBox caratteristicheVBox;

    @FXML
    private ComboBox<?> ordinaComboBox;

    @FXML
    private ComboBox<?> repartoComboBox1;

    @FXML
    private ListView<Prodotto> itemListView;

    @FXML
    private Spinner<?> quantitySpinner;

    @FXML
    private Button modificaButton;

    @FXML
    private Button inserisciButton;

    // Contiene gli elementi attuali da visualizzare del database
    private ObservableList<Prodotto> prodottoObservableList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        prodottoObservableList = FXCollections.observableArrayList();
        prodottoObservableList.setAll(Database.getInstance().getProdotti());

        itemListView.setItems(prodottoObservableList);
        itemListView.setCellFactory(factory -> new CellProdotto());
    }

    @Override
    public void onSwap(Persona target)
    {

    }

    @FXML
    void filtraButtonHandler(ActionEvent event) {

    }

    @FXML
    void inserisciButtonHandler(ActionEvent event) {

    }

    @FXML
    void modificaButtonHandler(ActionEvent event) {

    }

    @FXML
    void speseButtonHandler(ActionEvent event) {

    }
}
