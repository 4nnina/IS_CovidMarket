package main.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Pane;
import main.model.Prodotto;
import main.model.Utente;

import java.io.IOException;

public class ProdottoCatalogoCell extends ListCell<Prodotto>
{
    @FXML private Pane pane;
    @FXML private Label nomeLabel;
    @FXML private Label prezzoLabel;

    // Carica stile fxml
    public ProdottoCatalogoCell()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/fxml/prodottoCatalogo.fxml"));
            loader.setController(this);
            loader.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void updateItem(Prodotto prodotto, boolean empty)
    {
        super.updateItem(prodotto, empty);
        if(empty)
        {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);

        } else {
            // Setta informazioni sull'item
            nomeLabel.setText(prodotto.nome);
            prezzoLabel.setText(String.valueOf(prodotto.prezzo));

            setPrefWidth(495);
            setPrefHeight(180);

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            setGraphic(pane);
        }
    }

}
