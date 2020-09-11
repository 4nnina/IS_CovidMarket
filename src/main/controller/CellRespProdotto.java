package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import main.model.Prodotto;

import java.io.IOException;

public class CellRespProdotto extends ListCell<Prodotto>
{
    @FXML
    private Pane pane;

    @FXML private Label nomeLabel;
    @FXML private Label prezzoLabel;
    @FXML private Label marcaLabel;
    @FXML private Label quantitaLabel;

    @FXML private ImageView prodottoImageView;

    private int width;

    // Carica stile fxml
    public CellRespProdotto()
    {
        this.width = width;
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/fxml/prodottoCatalogo.fxml"));
            loader.setController(this);
            loader.load();
        }
        catch (IOException e)
        {
            System.out.println("Errore caricamento fxml della cella");
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
            nomeLabel.setText(prodotto.getNome());
            prezzoLabel.setText(String.valueOf(prodotto.prezzo));
            marcaLabel.setText(prodotto.getMarca());
            quantitaLabel.setText(String.valueOf(prodotto.getQuantitaDisponibile()));
            prodottoImageView.setImage(prodotto.getImage());

            setMaxWidth(393);
            setHeight(100);

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            setGraphic(pane);
        }
    }
}
