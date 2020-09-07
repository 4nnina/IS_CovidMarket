package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;
import main.model.Carrello;
import main.model.Persona;
import main.model.Prodotto;
import main.model.Utente;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCarrello extends Controller implements Initializable
{
    @FXML private ImageView covidMarketImageView;
    @FXML private ImageView carrelloImageView;
    @FXML private Label nomeutenteLabel;
    @FXML private Label costoTotaleLabel;
    @FXML private ListView<Pair<Prodotto, Integer>> carrelloListView;
    @FXML private Button confermaOrdinaButton;
    @FXML private DatePicker datePicker;

    //Contiene gli items del carrello corrente
    ObservableList<Pair<Prodotto, Integer>> bundles;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carrelloImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, this::carrelloButtonHandler);
    }

    @Override
    public void onSwap(Persona target)
    {
        Utente user = (Utente)target;

        bundles = FXCollections.observableArrayList(user.getCarrello().getProdotti());
        carrelloListView.setItems(bundles);
        carrelloListView.setCellFactory(__list -> new CarrelloBundleCell());

        int totalCost = 0;
        for (Pair<Prodotto, Integer> pair : bundles)
            totalCost += pair.getValue() * pair.getKey().getPrezzo();
        costoTotaleLabel.setText(String.valueOf(totalCost) + " EUR");
    }

    // Passa alla schermata principale
    private void carrelloButtonHandler(MouseEvent e) {
        stageManager.swap(Stages.HomeUtente);
    }
}
