package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import main.model.*;
import main.storage.Database;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ControllerUserCarrello extends Controller implements Initializable
{
    @FXML private ImageView covidMarketImageView;
    @FXML private ImageView carrelloImageView;
    @FXML private Label nomeutenteLabel;
    @FXML private Label costoTotaleLabel;
    @FXML private ListView<Carrello.Coppia> carrelloListView;
    @FXML private Button confermaOrdinaButton;
    @FXML private Spinner<Integer> quantitySpinner;
    @FXML private Label usernameLabel;
    @FXML private Label puntiSpesaLabel;
    @FXML private Label saldoPuntiLabel;
    @FXML private Button eliminaButton;

    @FXML private ChoiceBox sezioneChoicebox;

    //Contiene gli items del carrello corrente
    ObservableList<Carrello.Coppia> bundles;

    private Utente currentUser;
    private boolean invalidChange = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));
        quantitySpinner.valueProperty().addListener((observableValue, oldValue, newValue)
                -> onQuantityChangeHandler(oldValue, newValue));

        bundles = FXCollections.observableArrayList();
        carrelloListView.getSelectionModel().selectedIndexProperty().addListener((observableValue, oldValue, newValue)
                -> onSelectedItemChangeHandler());

        covidMarketImageView.setOnMouseClicked(this::homeButtonHandler);
        eliminaButton.setOnMouseClicked(foo -> onQuantityChangeHandler(0, 0));
        confermaOrdinaButton.setOnMouseClicked(foo -> confermaSpesaHandler());
        carrelloImageView.setOnMouseClicked(foo -> stageManager.swap(Stages.SpesaUtente));

        saldoPuntiLabel.setText("");

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
    }

    private void updateInfoSpesa()
    {
        CartaFedelta cartaFedelta = currentUser.getCartaFedelta();
        if(!bundles.isEmpty())
        {
            /*
            double totalCost = 0;
            for (Carrello.Coppia pair : bundles)
                totalCost += pair.quantita * pair.prodotto.getPrezzo();
*/
            costoTotaleLabel.setText(String.format("%.2f €", currentUser.getCarrello().getCostoTot()));
            puntiSpesaLabel.setText(String.format("%d p", currentUser.getCarrello().getPunti()));

            if (cartaFedelta != null)
                saldoPuntiLabel.setText(cartaFedelta.punti + currentUser.getCarrello().getPunti() + " p");
        }
        else {
            costoTotaleLabel.setText("0 €");
            puntiSpesaLabel.setText("0 p");

            if (cartaFedelta != null)
                saldoPuntiLabel.setText(cartaFedelta.punti + " p");
        }
    }

    @Override
    public void onSwap(Persona target)
    {
        this.currentUser = (Utente)target;
        usernameLabel.setText(currentUser.getNome());

        bundles.setAll(currentUser.getCarrello().getProdotti());

        carrelloListView.setItems(bundles);
        carrelloListView.setCellFactory(__list
                -> new CellUserProdottoCarrello(carrelloListView.widthProperty().intValue()));

        // Deseleziona menu
        sezioneChoicebox.getSelectionModel().select(null);

        updateInfoSpesa();
    }

    private void onQuantityChangeHandler(Integer oldValue, Integer newValue)
    {
        Carrello.Coppia bundle = carrelloListView.getSelectionModel().getSelectedItem();
        if (bundle != null && !invalidChange)
        {
            bundle.quantita = newValue;
            ObservableList<Carrello.Coppia> notZeros = bundles.filtered(coppia -> coppia.quantita != 0);
            carrelloListView.setItems(notZeros);

            if (bundle.quantita == 0) {
                currentUser.getCarrello().removeProdotto(bundle.prodotto);
            }
        }

        // Aggiorna saldo punti etc
        updateInfoSpesa();
    }

    private void onSelectedItemChangeHandler()
    {
        Carrello.Coppia bundle = carrelloListView.getSelectionModel().getSelectedItem();
        if (bundle != null)
        {
            invalidChange = true;
            quantitySpinner.getValueFactory().setValue(bundle.quantita);
            invalidChange = false;
        }
    }

    // Passa alla schermata principale
    private void homeButtonHandler(MouseEvent e) {
        stageManager.swap(Stages.HomeUtente);
    }

    private void confermaSpesaHandler()
    {
        // Non ha senso se non ci sono elementi
        if (bundles.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Nessun elemento nel carrello");
            alert.showAndWait();
            return;
        }

        // Blocca se non ci sono abbastanza items nel database per soddisfare la richiesta
        boolean success = true;
        for(Carrello.Coppia coppia : currentUser.getCarrello().getProdotti())
        {

            int quantitaDisponibile = coppia.prodotto.getQuantitaDisponibile();
            if (coppia.quantita > quantitaDisponibile) {
                success = false;

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Il prodotto " + coppia.prodotto.getNome()
                        + " non è più disponibile nella quantità richiesta ("+ coppia.quantita +"/"+ quantitaDisponibile + ")."
                        + "L'elemento verrà rimosso dal carrello.");

                alert.show();

                // Elimina dal carrello
                currentUser.getCarrello().removeProdotto(coppia.prodotto);
            }
        }

        if(success)
        {
            PopupConsegna popupCarrello = new PopupConsegna(currentUser);
            Optional<DatiConsegna> datiConsegna = popupCarrello.show();
            if (datiConsegna.isPresent())
            {
                // Aggiorna saldo punti
                Carrello carrello = currentUser.resetCarrello();
                CartaFedelta cartaFedelta = currentUser.getCartaFedelta();
                if (cartaFedelta != null)
                    cartaFedelta.punti += carrello.getPunti();

                // Aggiorna database
                Database database = Database.getInstance();
                database.getSpese().add(new Spesa(currentUser, carrello, datiConsegna.get()));

                // Rimuove occorrente di quell'item dal database
                for(Carrello.Coppia coppia : carrello.getProdotti()) {
                    int newQuantita = coppia.prodotto.getQuantitaDisponibile() - coppia.quantita;
                    coppia.prodotto.setQuantitaDisponibile(newQuantita);
                }

                // Aggiorna la vista
                try { bundles.clear(); }
                catch (IndexOutOfBoundsException e) { /* Errore stupido di JavaFX */ };
                updateInfoSpesa();
            }
        }
        else{
            carrelloListView.getItems().clear();
            carrelloListView.getItems().setAll(currentUser.getCarrello().getProdotti());
            carrelloListView.refresh();
            updateInfoSpesa();
        }
    }
}
