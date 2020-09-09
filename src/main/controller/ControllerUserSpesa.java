package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import main.model.Carrello;
import main.model.DatiConsegna;
import main.model.Spesa;
import main.model.Utente;
import main.storage.Database;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerUserSpesa extends Controller2<SectionUser> implements Initializable
{
    @FXML private Pane gatePane;

    @FXML private ListView<Spesa> speseListView;
    @FXML private ListView<Carrello.Coppia> elementiListView;

    @FXML private Label statoLabel;
    @FXML private Label pagamentoLabel;
    @FXML private Label dataLabel;
    @FXML private Label orarioLabel;
    @FXML private Label costoLabel;

    private ObservableList<Spesa> spese;
    private ObservableList<Carrello.Coppia> elementi;
    private Utente currentUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        spese = FXCollections.observableArrayList();
        elementi = FXCollections.observableArrayList();

        elementiListView.setCellFactory(factory -> new CarrelloBundleCell());
        speseListView.setCellFactory(factory-> new SpesaCell());

        // Quando modifichiamo la spesa selezionata allora cambiano gli elementi
        speseListView.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1)
                -> changeSpesaHandle(t1.intValue()));
    }

    @Override
    public void onSwapPane(Object data)
    {
        Database database = Database.getInstance();
        this.currentUser = (Utente) data;

        // Carica spese che ha questo utente
        spese.clear();
        for(Spesa spesa : database.getSpese()) {
            if (spesa.getUtente() == currentUser)
                spese.add(spesa);
        }

        speseListView.setItems(spese);
        speseListView.getSelectionModel().select(0);

        // Aggiunge elementi di questa spesa (la prima)
        if(!spese.isEmpty())
        {
            updateSpesa(0);
            updateElements(0);
        }

        elementiListView.setItems(elementi);
    }

    private void updateSpesa(int spesaIndex)
    {
        Spesa spesa = spese.get(spesaIndex);
        if (spesa != null)
        {
            statoLabel.setText(spesa.getStatoConsegna().name());
            costoLabel.setText(String.valueOf(spesa.getCarrello().getPunti()));

            DatiConsegna datiConsegna = spesa.getDatiConsegna();
            pagamentoLabel.setText(datiConsegna.metodoPagamento.name());
            dataLabel.setText(datiConsegna.date.toString());
            orarioLabel.setText(datiConsegna.fasciaOraria.name());
        }
    }

    private void updateElements(int selectedSpesa)
    {
        elementi.clear();

        // TODO: Risolvi IndexOutOfBoundsException che avviene qui
        Carrello carrello = spese.get(selectedSpesa).getCarrello();
        elementi.addAll(carrello.getProdotti());

        elementiListView.setItems(elementi);
    }

    private void changeSpesaHandle(int spesaIndex)
    {
        updateElements(spesaIndex);
        updateSpesa(spesaIndex);
    }

    @Override
    public Pane getGatePane() {
        return null;
    }
}
