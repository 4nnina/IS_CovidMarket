package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import main.model.*;
import main.storage.Database;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerUserSpesa extends Controller implements Initializable {

    @FXML private ImageView covidMarketImageView;
    @FXML private ImageView carrelloImageView;
    @FXML private Label usernameLabel;
    @FXML private ListView<Spesa> speseListView;
    @FXML private ListView<Carrello.Coppia> elementiListView;
    @FXML private Label statoLabel;
    @FXML private Label pagamentoLabel;
    @FXML private Label dataLabel;
    @FXML private Label orarioLabel;
    @FXML private Label costoLabel;
    @FXML private ChoiceBox sezioneChoicebox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        spese = FXCollections.observableArrayList();
        elementi = FXCollections.observableArrayList();

        elementiListView.setCellFactory(factory
                -> new CellUserProdottoCarrello(elementiListView.widthProperty().intValue()));
        speseListView.setCellFactory(factory
                -> new CellSpesa(speseListView.widthProperty().intValue()));

        // Quando modifichiamo la spesa selezionata allora cambiano gli elementi
        speseListView.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1)
                -> changeSpesaHandle(t1.intValue()));

        covidMarketImageView.setOnMouseClicked(foo -> stageManager.swap(Stages.HomeUtente));

        // Cambia schermata in base alla scelta
        sezioneChoicebox.getItems().setAll("Profilo", "Tessera Fedelta", "Storico Spese", "Logout");
        sezioneChoicebox.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> {
            switch (t1.intValue()) {
                case 0: stageManager.swap(Stages.Profilo);     break;
                case 1: stageManager.swap(Stages.Tessera);     break;
                case 2: stageManager.swap(Stages.SpesaUtente); break;
                case 3: stageManager.swap(Stages.Login);       break;
            }
        });
        carrelloImageView.setOnMouseClicked(this::carrelloButtonHandler);

    }

    private void carrelloButtonHandler(MouseEvent mouseEvent) {
        stageManager.swap(Stages.Carrello);
    }

    private Utente currentUser;
    private ObservableList<Spesa> spese;
    private ObservableList<Carrello.Coppia> elementi;

    @Override
    public void onSwap(Persona target)
    {
        Database database = Database.getInstance();
        this.currentUser = (Utente)target;
        usernameLabel.setText(currentUser.getNome());

        // Carica spese che ha questo utente
        try {  elementi.clear(); }
        catch (IndexOutOfBoundsException e) { /* Errore stupido di JavaFX */ };

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

        // Deseleziona menu
        sezioneChoicebox.getSelectionModel().select(null);
    }

    private void updateSpesa(int spesaIndex)
    {
        Spesa spesa = spese.get(spesaIndex);
        if (spesa != null)
        {
            statoLabel.setText(spesa.getStatoConsegna().name());
            costoLabel.setText(String.format("%.2f €",spesa.getCarrello().getCostoTot()));

            DatiConsegna datiConsegna = spesa.getDatiConsegna();
            pagamentoLabel.setText(datiConsegna.metodoPagamento.name());
            dataLabel.setText(datiConsegna.date.toString());
            orarioLabel.setText(datiConsegna.fasciaOraria.name());
        }
    }

    private void updateElements(int selectedSpesa)
    {
        elementi.clear();
        Carrello carrello = spese.get(selectedSpesa).getCarrello();
        for(Carrello.Coppia coppia : carrello.getProdotti()) {
            elementi.add(coppia);
        }

        elementiListView.setItems(elementi);
    }

    private void changeSpesaHandle(int spesaIndex)
    {
        updateElements(spesaIndex);
        updateSpesa(spesaIndex);
    }
}
