package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import main.Program;
import main.model.CartaFedelta;
import main.model.Utente;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerUserProfile extends Controller2<SectionUser>
{
    @FXML private Pane gatePanel;

    @FXML private Label nomeLabel;
    @FXML private Label cognomeLabel;
    @FXML private Label capLabel;
    @FXML private Label indirizzoLabel;
    @FXML private Label cittaLabel;
    @FXML private Label mailLabel;
    @FXML private Label pagamentoLabel;
    @FXML private Label telefonoLabel;
    @FXML private Label tesseraLabel;
    @FXML private Label puntiLabel;

    @FXML private Button modificaButton;

    private Utente currentUser;

    @Override
    public void onSwapPane(Object data)
    {
        this.currentUser = (Utente) data;

        nomeLabel.setText(currentUser.getNome());
        cognomeLabel.setText(currentUser.getCognome());
        indirizzoLabel.setText(currentUser.getIndirizzo());
        cittaLabel.setText(currentUser.getCitta());
        capLabel.setText(String.valueOf(currentUser.getCAP()));
        telefonoLabel.setText(currentUser.getTelefono());
        mailLabel.setText(currentUser.getEmail());
        pagamentoLabel.setText(currentUser.getMetodoPagamento().toString());

        CartaFedelta cartaFedelta = currentUser.getCartaFedelta();
        if (cartaFedelta != null)
        {
            tesseraLabel.setText(currentUser.getCartaFedelta().ID);
            puntiLabel.setText(String.valueOf(currentUser.getCartaFedelta().punti));
        }
    }

    @FXML
    void modificaButtonPress(ActionEvent event) {
        dashboard.swap(SectionUser.ModificaProfilo, currentUser);
    }

    @Override
    public Pane getGatePane() {
        return gatePanel;
    }
}
