package main.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import main.model.*;
import main.storage.Database;

import java.net.URL;
import java.util.EnumSet;
import java.util.ResourceBundle;

public class ControllerHome extends Controller implements Initializable
{
    private enum Ordinamento
    {
        PrezzoCrescente,
        PrezzoDecrescente,
        Marca
    }

    @FXML private ImageView carrelloImageView;
    @FXML private ComboBox<Reparto> repartoComboBox;
    @FXML private VBox caratteristicheVBox;
    @FXML private TextField marcaTextField;
    @FXML private ComboBox<Ordinamento> ordinaComboBox;
    @FXML private Button filtraButton;
    @FXML private Button compraButton;
    @FXML private Spinner<Integer> quantitySpinner;
    @FXML private ListView<Prodotto> itemListView;
    @FXML private Label carrelloCountLabel;
    @FXML private Label usernameLabel;
    @FXML private Label costoQuantitaLabel;

    // Contiene gli elementi attuali da visualizzare del database
    private ObservableList<Prodotto> prodottoObservableList;

    private Utente currentUser;

    @Override
    public void onSwap(Persona target)
    {
        this.currentUser = (Utente)target;
        usernameLabel.setText(currentUser.getNome());

        Database database = Database.getInstance();

        prodottoObservableList = FXCollections.observableArrayList();
        prodottoObservableList.setAll(database.getProdotti());

        itemListView.getItems().setAll(prodottoObservableList);
        itemListView.setCellFactory(__list -> new ProdottoCatalogoCell());

        // Setta numero di elementi nel carrello
        int count = currentUser.getCarrello().getProdotti().size();
        carrelloCountLabel.setText(String.valueOf(count));
    }

    // Aggiorna costoQuantitaLabel
    private void updateQuantityCostLabel(int newValue)
    {
        // Cambia costo della quantità
        Prodotto prodotto = itemListView.getSelectionModel().getSelectedItem();
        if (prodotto != null)
        {
            int cost = newValue * prodotto.getPrezzo();
            costoQuantitaLabel.setText(cost + " EUR");
        }
        else
            costoQuantitaLabel.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        prodottoObservableList = FXCollections.observableArrayList();

        // Spinner quantita
        quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100));
        quantitySpinner.valueProperty().addListener((observableValue, oldValue, newValue)
                -> updateQuantityCostLabel(newValue));

        // Quando cambia l'elemento selezionato aggiorna il costo per quantita
        itemListView.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1)
                -> updateQuantityCostLabel(quantitySpinner.getValue()));

        // Carica tutti i reparti
        repartoComboBox.getItems();
        for (Reparto rep: Reparto.values())
            repartoComboBox.getItems().add(rep);
        repartoComboBox.getSelectionModel().select(0);

        // Popola caratteristiche possibili
        caratteristicheVBox.setPrefHeight(Attributo.values().length * 20);
        for(Attributo attrib : Attributo.values())
            caratteristicheVBox.getChildren().add(new CheckBox(attrib.toString()));

        ordinaComboBox.getItems().addAll(Ordinamento.values());
        ordinaComboBox.getSelectionModel().select(0);

        // Aggiunge eventi
        filtraButton.addEventHandler(ActionEvent.ACTION, this::filtraButtonHandler);
        carrelloImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, this::carrelloButtonHandler);
    }

    /**
     * Aggiunge l'elemento selezionato al carrello attuale
     */
    @FXML
    private void onCompraButton(ActionEvent e)
    {
        Prodotto prodotto = itemListView.getSelectionModel().getSelectedItem();
        if (prodotto != null)
        {
            Integer buyValue = quantitySpinner.getValue().intValue();
            currentUser.getCarrello().addProdotto(prodotto, buyValue);

            // Setta numero di elementi nel carrello
            int count = currentUser.getCarrello().getProdotti().size();
            carrelloCountLabel.setText(String.valueOf(count));
        }
    }

    /**
     * Filtra la listView
     */
    @FXML
    private void filtraButtonHandler(ActionEvent e)
    {
        // Crea enumset del filtro per gli attributi, se niente è selezionato allora visualizza tutto
        boolean anySelected = false;
        EnumSet<Attributo> caratteristicheFilter = EnumSet.noneOf(Attributo.class);
        for (int i = 0; i < Attributo.values().length; ++i)
        {
            CheckBox checkBox = (CheckBox)caratteristicheVBox.getChildren().get(i);
            if (checkBox.isSelected()) {
                caratteristicheFilter.add(Attributo.values()[i]);
                anySelected = true;
            }
        }

        // Aggiunge solo prodotti che passano il filtro
        final boolean finalAnySelected = anySelected;
        FilteredList<Prodotto> prodottiFiltrati = prodottoObservableList.filtered(prodotto ->
        {
            // Controlla se il reparto va bene
            Reparto selectedReparto = repartoComboBox.getSelectionModel().getSelectedItem();
            if (selectedReparto != Reparto.Tutto && prodotto.getReparto() != selectedReparto)
                return false;

            // Controlla la marca
            String marca = marcaTextField.getText();
            boolean subset = prodotto.getMarca().toLowerCase().contains(marca.toLowerCase());
            if (!marca.isEmpty() && !subset)
                return false;

            // Controlla se gli attributi vanno bene
            if (finalAnySelected && !prodotto.checkAttributi(caratteristicheFilter))
                return false;

            return true;
        });

        //Riordina in base all'ordinamento
        SortedList<Prodotto> prodottiOrdinati = prodottiFiltrati.sorted((o1, o2) ->
        {
            switch (ordinaComboBox.getSelectionModel().getSelectedItem())
            {
                case PrezzoCrescente:
                    return o1.getPrezzo() - o2.getPrezzo();
                case PrezzoDecrescente:
                    return o2.getPrezzo() - o1.getPrezzo();
                case Marca:
                    return o1.getMarca().compareTo(o2.getMarca());
                default:
                    return 0;
            }
        });

        itemListView.setItems(prodottiOrdinati);
    }

    /**
     * Passa alla schermata del carrello
     */
    @FXML
    private void carrelloButtonHandler(MouseEvent e) {
        stageManager.swap(Stages.Carrello);
    }
}
