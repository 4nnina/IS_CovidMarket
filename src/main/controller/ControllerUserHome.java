package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.model.Attributo;
import main.model.Prodotto;
import main.model.Reparto;
import main.model.Utente;
import main.storage.Database;

import java.net.URL;
import java.util.EnumSet;
import java.util.ResourceBundle;

public class ControllerUserHome extends Controller2<SectionUser> implements Initializable
{
    private enum Ordinamento
    {
        PrezzoCrescente,
        PrezzoDecrescente,
        Marca
    }

    @FXML private Button filtraButton;
    @FXML private Button compraButton;

    @FXML private TextField marcaTextField;
    @FXML private ComboBox<Ordinamento> ordinaComboBox;
    @FXML private ComboBox<Reparto> repartoComboBox;
    @FXML private VBox caratteristicheVBox;

    @FXML private ListView<Prodotto> itemListView;
    @FXML private Spinner<Integer> quantitySpinner;
    @FXML private Label costoQuantitaLabel;

    @FXML private Pane gatePane;

    // Contiene gli elementi attuali da visualizzare del database
    private ObservableList<Prodotto> productList;

    // Utente attuale, modificato ad ogni swap
    private Utente currentUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        productList = FXCollections.observableArrayList();

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
    }

    @Override
    public void onSwapPane(Object data)
    {
        Database database = Database.getInstance();
        this.currentUser = (Utente) data;

        productList = FXCollections.observableArrayList();
        productList.setAll(database.getProdotti());

        itemListView.setItems(productList);
        itemListView.setCellFactory(__list -> new ProdottoCatalogoCell());
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

    @FXML
    private void filtraButtonHandler(ActionEvent event)
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
        FilteredList<Prodotto> prodottiFiltrati = productList.filtered(prodotto ->
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

    @FXML
    private void compraButtonHandler(ActionEvent event)
    {
        Prodotto prodotto = itemListView.getSelectionModel().getSelectedItem();
        if (prodotto != null)
        {
            Integer buyValue = quantitySpinner.getValue();
            currentUser.getCarrello().addProdotto(prodotto, buyValue);
        }
    }

    @Override
    public Pane getGatePane() {
        return gatePane;
    }
}
