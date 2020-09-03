package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import main.model.Persona;
import main.model.Prodotto;
import main.model.Utente;
import main.storage.Database;
import main.view.ProdottoCatalogoCell;

public class ControllerHome extends Controller
{
    @FXML private ImageView carrelloImageView;
    @FXML private ComboBox<?> repartoComboBox;
    @FXML private ComboBox<?> tipoComboBox;
    @FXML private ComboBox<?> marcaComboBox;
    @FXML private ComboBox<?> allergeniComboBox;
    @FXML private ComboBox<?> altroComboBox;
    @FXML private SplitMenuButton ordinaSpliMenuButton;
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

        itemListView.setItems(prodottoObservableList);
        itemListView.setCellFactory(__list -> new ProdottoCatalogoCell());
    }

    @FXML
    private void initialize()
    {
        prodottoObservableList = FXCollections.observableArrayList();
        quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100));
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
}
