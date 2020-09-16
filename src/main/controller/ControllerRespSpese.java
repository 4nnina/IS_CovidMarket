package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import main.model.*;
import main.storage.Database;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerRespSpese extends Controller implements Initializable
{
    @FXML private Button consegnaButton;
    @FXML private Button preparaButton;

    @FXML private ListView<Spesa> speseListView;
    @FXML private ListView<Carrello.Coppia> elementiListView;

    @FXML private Label statoLabel;
    @FXML private Label pagamentoLabel;
    @FXML private Label dataLabel;
    @FXML private Label orarioLabel;
    @FXML private Label costoLabel;
    @FXML private Label nomeutenteLabel;
    @FXML private Label utenteLabel;

    @FXML private ImageView covidMarketImageView;

    @FXML private ChoiceBox<String> statoChoiceBox;
    @FXML private ChoiceBox<String> sezioneChoicebox;
    @FXML private TextField utenteTextField;

    private ObservableList<Spesa> spese;
    private ObservableList<Carrello.Coppia> elementi;
    private Responsabile currentresponsabile;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        spese = FXCollections.observableArrayList();
        elementi = FXCollections.observableArrayList();

        elementiListView.setCellFactory(factory
                -> new CellUserProdottoCarrello(elementiListView.widthProperty().intValue()));
        speseListView.setCellFactory(factory
                -> new CellSpesa(speseListView.widthProperty().intValue()));

        // Quando modifichiamo la spesa selezionata allora cambiano gli elementi
        speseListView.getSelectionModel().selectedItemProperty().addListener((observableValue, number, t1)
                -> changeSpesaHandle(t1));

        // Carica possibili stati spesa
        statoChoiceBox.getItems().add("---");
        for(StatoConsegna statoConsegna : StatoConsegna.values())
            statoChoiceBox.getItems().add(statoConsegna.name());

        covidMarketImageView.setOnMouseClicked(this::homeHandler);

        sezioneChoicebox.getItems().setAll("Profilo","Logout");
        sezioneChoicebox.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> {
            switch (t1.intValue()) {
                case 0: stageManager.swap(Stages.ProfiloResponsabile);       break;
                case 1: stageManager.swap(Stages.Login);       break;
            }
        });
    }

    private void homeHandler(MouseEvent mouseEvent) { stageManager.swap(Stages.HomeResponsabile); }

    @Override
    public void onSwap(Persona target)
    {
        Database database = Database.getInstance();
        this.currentresponsabile = (Responsabile)target;
        nomeutenteLabel.setText(currentresponsabile.getNome());

        // Carica tutte le spese
        spese.clear();
        spese.addAll(database.getSpese());

        speseListView.setItems(spese);
        speseListView.getSelectionModel().select(0);
        speseListView.refresh();

        // Aggiunge elementi di questa spesa (la prima)
        if(!spese.isEmpty())
        {
            Spesa spesa = speseListView.getSelectionModel().getSelectedItem();
            updateElements(spesa);
            updateSpesa(spesa);
        }

        elementiListView.setItems(elementi);
        elementiListView.refresh();

        sezioneChoicebox.getSelectionModel().select(null);
    }

    private void updateSpesa(Spesa spesa)
    {
        if (spesa != null)
        {
            statoLabel.setText(spesa.getStatoConsegna().name());
            costoLabel.setText(String.format("%.2f €", spesa.getCarrello().getCostoTot()));
            utenteLabel.setText(spesa.getUtente().getEmail());

            DatiConsegna datiConsegna = spesa.getDatiConsegna();
            pagamentoLabel.setText(datiConsegna.metodoPagamento.name());
            dataLabel.setText(datiConsegna.date.toString());
            orarioLabel.setText(datiConsegna.fasciaOraria.name());
        }
    }

    private void updateElements(Spesa spesa)
    {

        // Aggiorna la vista
        try {  elementi.clear(); }
        catch (IndexOutOfBoundsException e) { /* Errore stupido di JavaFX */ };

        if (spesa != null)
        {
            Carrello carrello = spesa.getCarrello();
            for (Carrello.Coppia coppia : carrello.getProdotti()) {
                elementi.add(coppia);
            }
        }

        elementiListView.setItems(elementi);
    }

    private void updateButtonState(Spesa spesa)
    {
        // Attiviamo o disattiviamo i bottoni in base allo stato della spesa
        if (spesa != null)
        {
            StatoConsegna statoConsegna = spesa.getStatoConsegna();
            switch (statoConsegna)
            {
                case Confermata:
                    preparaButton.setDisable(false);
                    consegnaButton.setDisable(true);
                    break;
                case Preparazione:
                    preparaButton.setDisable(true);
                    consegnaButton.setDisable(false);
                    break;
                case Consegnata:
                    preparaButton.setDisable(true);
                    consegnaButton.setDisable(true);
                    break;
            }
        }
    }

    private void changeSpesaHandle(Spesa spesa)
    {
        updateElements(spesa);
        updateSpesa(spesa);
        updateButtonState(spesa);
    }

    @FXML
    void filtraButtonHandler(ActionEvent event)
    {
        FilteredList<Spesa> speseFiltrate = spese.filtered(spesa ->
        {
            // Controlla se il reparto va bene
            if(!statoChoiceBox.getSelectionModel().isEmpty()) {
                String statoConsegna = statoChoiceBox.getSelectionModel().getSelectedItem();
                if (!statoConsegna.equals("---") && !spesa.getStatoConsegna().name().equals(statoConsegna))
                    return false;
            }

            // Controlla l'utente
            String mail = utenteTextField.getText().toLowerCase();
            boolean subset = spesa.getUtente().getEmail().toLowerCase().contains(mail);
            if (!mail.isEmpty() && !subset)
                return false;

            return true;
        });

        speseListView.setItems(speseFiltrate);
        speseListView.getSelectionModel().select(0);
        speseListView.refresh();
    }

    @FXML
    void preparaButtonHandler(ActionEvent event) {
        Spesa spesa = speseListView.getSelectionModel().getSelectedItem();
        if (spesa != null)
        {
            spesa.setStatoConsegna(StatoConsegna.Preparazione);
            updateButtonState(spesa);
            updateSpesa(spesa);
        }
    }

    @FXML
    void consegnaButtonHandler(ActionEvent event) {
        Spesa spesa = speseListView.getSelectionModel().getSelectedItem();
        if (spesa != null)
        {
            spesa.setStatoConsegna(StatoConsegna.Consegnata);
            updateButtonState(spesa);
            updateSpesa(spesa);
        }
    }

    @FXML
    void homeButtonHandler(ActionEvent event) {
        stageManager.swap(Stages.HomeResponsabile);
    }
}
