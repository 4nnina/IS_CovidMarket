package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import main.model.*;
import main.storage.Database;

import java.util.EnumSet;

public class ControllerHome extends Controller
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

    // Contiene gli elementi attuali da visualizzare del database
    private ObservableList<Prodotto> prodottoObservableList;

    private Utente currentUser;

    @Override
    public void onSwap(Persona target)
    {
        this.currentUser = (Utente)target;

        Database database = Database.getInstance();

        prodottoObservableList.clear();
        prodottoObservableList.setAll(database.getProdotti());

        itemListView.getItems().setAll(prodottoObservableList);
        itemListView.setCellFactory(__list -> new ProdottoCatalogoCell());
    }

    @FXML
    private void initialize()
    {
        prodottoObservableList = FXCollections.observableArrayList();
        quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100));

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

        filtraButton.addEventHandler(ActionEvent.ACTION, this::filtraButtonHandler);
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
            if(!currentUser.carrelloCorrente.containsKey(prodotto))
                currentUser.carrelloCorrente.put(prodotto, 0);

            Integer oldValue = currentUser.carrelloCorrente.get(prodotto);
            Integer buyValue = quantitySpinner.getValue().intValue();
            currentUser.carrelloCorrente.replace(prodotto, oldValue + buyValue);
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

        //Ordina in base al filtro
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
}
