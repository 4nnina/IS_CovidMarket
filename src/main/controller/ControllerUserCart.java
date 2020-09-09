package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import main.model.*;
import main.storage.Database;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ControllerUserCart extends Controller2<SectionUser> implements Initializable
{
    @FXML private Pane gatePane;

    @FXML private Spinner<Integer> quantitySpinner;
    @FXML private ListView<Carrello.Coppia> cartListView;

    @FXML private Button confirmButton;
    @FXML private Button deleteButton;

    @FXML private Label totalCostLabel;
    @FXML private Label cartPointsLabel;
    @FXML private Label pointsLabel;

    //Contiene gli items del carrello corrente
    private ObservableList<Carrello.Coppia> elements;
    private boolean invalidChange = false;
    private Utente currentUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));
        quantitySpinner.valueProperty().addListener((observableValue, oldValue, newValue)
                -> onQuantityChangeHandler(oldValue, newValue));

        elements = FXCollections.observableArrayList();
        cartListView.getSelectionModel().selectedIndexProperty().addListener((observableValue, oldValue, newValue)
                -> onSelectedItemChangeHandler());
    }

    @Override
    public void onSwapPane(Object data)
    {
        this.currentUser = (Utente) data;
        elements.setAll(currentUser.getCarrello().getProdotti());

        cartListView.setItems(elements);
        cartListView.setCellFactory(__list -> new CellCarrello());

        updateInfoSpesa();
    }

    private void updateInfoSpesa()
    {
        CartaFedelta cartaFedelta = currentUser.getCartaFedelta();
        if(!elements.isEmpty())
        {
            int totalCost = 0;
            for (Carrello.Coppia pair : elements)
                totalCost += pair.quantita * pair.prodotto.getPrezzo();

            totalCostLabel.setText(totalCost + " €");
            cartPointsLabel.setText(totalCost + " p");

            if (cartaFedelta != null)
                pointsLabel.setText(cartaFedelta.punti + totalCost + " p");
        }
        else {
            totalCostLabel.setText("0 €");
            cartPointsLabel.setText("0 p");

            if (cartaFedelta != null)
                pointsLabel.setText(cartaFedelta.punti + " p");
        }
    }

    private void onQuantityChangeHandler(Integer oldValue, Integer newValue)
    {
        Carrello.Coppia bundle = cartListView.getSelectionModel().getSelectedItem();
        if (bundle != null && !invalidChange)
        {
            bundle.quantita = newValue;
            ObservableList<Carrello.Coppia> notZeros = elements.filtered(coppia -> coppia.quantita != 0);
            cartListView.setItems(notZeros);

            if (bundle.quantita == 0) {
                currentUser.getCarrello().removeProdotto(bundle.prodotto);
            }
        }

        // Aggiorna saldo punti etc
        updateInfoSpesa();
    }

    private void onSelectedItemChangeHandler()
    {
        Carrello.Coppia bundle = cartListView.getSelectionModel().getSelectedItem();
        if (bundle != null)
        {
            invalidChange = true;
            quantitySpinner.getValueFactory().setValue(bundle.quantita);
            invalidChange = false;
        }
    }

    @FXML
    void confirmButtonPress(ActionEvent event)
    {
        // Non ha senso se non ci sono elementi
        if (elements.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Nessun elemento nel carrello");
            alert.showAndWait();
            return;
        }

        PopupConsegna popupConsegna = new PopupConsegna(currentUser);
        Optional<DatiConsegna> datiConsegna = popupConsegna.show();
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
            try { elements.clear(); }
            catch (IndexOutOfBoundsException e) { /* Errore stupido di JavaFX */ };
            updateInfoSpesa();
        }
    }

    @FXML
    void deleteButtonPress(ActionEvent event) {
        onQuantityChangeHandler(0, 0);
    }

    @Override
    public Pane getGatePane() {
        return gatePane;
    }
}
