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
import javafx.scene.layout.VBox;
import main.model.*;
import main.storage.Database;

import java.net.URL;
import java.util.EnumSet;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ControllerHomeResponsabili extends Controller implements Initializable
{
    private enum Ordinamento
    {
        PrezzoCrescente,
        PrezzoDecrescente,
        Marca
    }

    @FXML private Button speseButton;
    @FXML private Button filtraButton1;
    @FXML private TextField marcaTextField;
    @FXML private Label nomeutenteLabel;

    @FXML
    private VBox caratteristicheVBox;

    @FXML private ComboBox<Ordinamento> ordinaComboBox;
    @FXML private ComboBox<Reparto> repartoComboBox;
    @FXML private ListView<Prodotto> itemListView;
    @FXML private Spinner<Integer> quantitySpinner;

    @FXML private Button modificaButton;
    @FXML private Button inserisciButton;

    // Contiene gli elementi attuali da visualizzare del database
    private ObservableList<Prodotto> prodottoObservableList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        // Spinner quantita
        quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000));
        quantitySpinner.valueProperty().addListener((observableValue, integer, newValue) ->
        {
            // Cambia quantita prodotto
            Prodotto prodotto = itemListView.getSelectionModel().getSelectedItem();
            if (prodotto != null)
                prodotto.setQuantitaDisponibile(newValue);

            itemListView.refresh();
        });

        // Quando cambia l'elemento selezionato aggiorna la quantita in magazzino
        itemListView.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) ->
        {
            // Cambia quantita prodotto visualizzata
            Prodotto prodotto = itemListView.getSelectionModel().getSelectedItem();
            if (prodotto != null)
                quantitySpinner.getValueFactory().setValue(prodotto.getQuantitaDisponibile());
        });

        // Popola caratteristiche possibili
        caratteristicheVBox.setPrefHeight(Attributo.values().length * 20);
        for(Attributo attrib : Attributo.values())
            caratteristicheVBox.getChildren().add(new CheckBox(attrib.toString()));

        ordinaComboBox.getItems().addAll(Ordinamento.values());
        ordinaComboBox.getSelectionModel().select(0);
    }

    private Responsabile currentResp;

    @Override
    public void onSwap(Persona target)
    {
        this.currentResp = (Responsabile) target;
        nomeutenteLabel.setText(currentResp.getNome());

        // Carica tutti i reparti gestiti
        repartoComboBox.getItems().add(Reparto.Tutto);
        for (Reparto rep: Reparto.values())
            if (currentResp.getRepartiGestiti().contains(rep))
                repartoComboBox.getItems().add(rep);

        repartoComboBox.getSelectionModel().select(0);

        // Setta solo gli elementi che questo responsabile può modificiare
        updateAllowedProducts(currentResp);
    }

    private void updateAllowedProducts(Responsabile responsabile)
    {
        // Setta solo gli elementi che questo responsabile può modificiare
        var prodotti = Database.getInstance().getProdotti();
        prodottoObservableList = FXCollections.observableArrayList(prodotti).filtered(prodotto
                -> responsabile.getRepartiGestiti().contains(prodotto.getReparto()));

        itemListView.setItems(prodottoObservableList);
        itemListView.setCellFactory(factory -> new ProdottoCatalogoCell());
    }

    @FXML
    void filtraButtonHandler(ActionEvent event)
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

    @FXML
    void inserisciButtonHandler(ActionEvent event)
    {
        PopupProdotto popupProdotto = new PopupProdotto(currentResp,null);
        Optional<Prodotto> prodotto = popupProdotto.show();
        if (prodotto.isPresent())
        {
            Database database = Database.getInstance();
            database.getProdotti().add(prodotto.get());

            updateAllowedProducts(currentResp);
        }
    }

    @FXML
    void speseButtonHandler(ActionEvent event) {
        stageManager.swap(Stages.SpesaResponsabile);
    }

    @FXML
    void logoutButtonHandler(ActionEvent event) {
        stageManager.swap(Stages.Login);
    }
}
