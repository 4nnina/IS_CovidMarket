package main.controller;

import com.sun.glass.ui.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.model.DatiConsegna;
import main.model.FasciaOraria;
import main.model.MetodoPagamento;
import main.model.Utente;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ResourceBundle;

public class PopupConsegna extends Popup<DatiConsegna> implements Initializable
{
    @FXML private DatePicker datePicked;
    @FXML private ChoiceBox<String> orarioChoiceBox;
    @FXML private ChoiceBox<String> pagamentoChoiceBox;

    @FXML private Button confermaButton;

    private Utente currentUser;

    public PopupConsegna(Utente currentUser)
    {
        super("../resources/fxml/consegna.fxml");
        this.currentUser = currentUser;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        // Disabilita giorni passati, vicini e festivi
        datePicked.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                LocalDate today = LocalDate.now();
                setDisable(empty
                        || date.compareTo(today) <= 0
                        || date.getDayOfWeek() == DayOfWeek.SATURDAY
                        || date.getDayOfWeek() == DayOfWeek.SUNDAY);
            }
        });
    }

    @Override
    protected void showEvent()
    {
        // Metodo di pagamento normale (esclude 'Nessuno')
        for(int i = 1; i < MetodoPagamento.values().length; ++i) {
            MetodoPagamento metodoPagamento = MetodoPagamento.values()[i];
            pagamentoChoiceBox.getItems().add(metodoPagamento.name());
        }

        // Setta il metodo predefinito
        MetodoPagamento metodoPredefinito = currentUser.getMetodoPagamento();
        if (metodoPredefinito != MetodoPagamento.Nessuno) {
            int i = metodoPredefinito.ordinal() + 1;
            pagamentoChoiceBox.getSelectionModel().select(i);
        }
        else
            pagamentoChoiceBox.getSelectionModel().select(0);

        // Tempo di consegna preferito
        for(FasciaOraria fasciaOraria : FasciaOraria.values())
            orarioChoiceBox.getItems().add(fasciaOraria.name());
        orarioChoiceBox.getSelectionModel().select(0);
    }

    // Ritorna risultato
    @FXML
    private void confermaButtonPress(ActionEvent event) {

        // Una data deve essere selezionata
        LocalDate localDate = datePicked.getValue();
        if (localDate != null)
        {
            MetodoPagamento metodoPagamento = MetodoPagamento.values()
                    [pagamentoChoiceBox.getSelectionModel().getSelectedIndex() + 1];

            FasciaOraria fasciaOraria = FasciaOraria.values()
                    [orarioChoiceBox.getSelectionModel().getSelectedIndex()];

            DatiConsegna datiConsegna = new DatiConsegna(localDate, metodoPagamento, fasciaOraria);
            close(datiConsegna);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selezionare data di consegna");
            alert.showAndWait();
        }
    }

    @Override
    protected Popup<DatiConsegna> self() {
        return this;
    }
}
