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

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ControllerCarrello extends Controller implements Initializable
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
    @FXML private ComboBox<String> nomeutenteCombobox;

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

        saldoPuntiLabel.setText("");

       //nomeutenteCombobox.setPromptText(currentUser.getNome());
        //nomeutenteCombobox.getItems().addAll("Profilo","Modifica Profilo", "Tessera Fedeltà");

    }

    private void updateInfoSpesa()
    {
        int totalCost = 0;
        for (Carrello.Coppia pair : bundles)
            totalCost += pair.quantita * pair.prodotto.getPrezzo();

        costoTotaleLabel.setText(totalCost + " €");
        puntiSpesaLabel.setText(totalCost + " p");

        CartaFedelta cartaFedelta = currentUser.getCartaFedelta();
        if(cartaFedelta != null) {
            saldoPuntiLabel.setText(cartaFedelta.punti + totalCost + " p");
        }
    }

    @Override
    public void onSwap(Persona target)
    {
        this.currentUser = (Utente)target;
        usernameLabel.setText(currentUser.getNome());

        bundles.setAll(currentUser.getCarrello().getProdotti());

        carrelloListView.setItems(bundles);
        carrelloListView.setCellFactory(__list -> new CarrelloBundleCell());

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
        PopupCarrello popupCarrello = new PopupCarrello();
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

            // Aggiorna la vista
            bundles.clear();
            updateInfoSpesa();
        }
    }
}
