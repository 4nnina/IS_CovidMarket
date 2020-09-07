package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;
import main.model.Carrello;
import main.model.Prodotto;

import java.io.IOException;

public class CarrelloBundleCell extends ListCell<Pair<Prodotto, Integer>>
{
    @FXML private AnchorPane pane;

    @FXML private Label nomeLabel;
    @FXML private Label marcaLabel;
    @FXML private Label prezzoLabel;
    @FXML private Label quantitaLabel;

    @FXML private ImageView prodottoImageView;

    // Carica stile fxml
    public CarrelloBundleCell()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/fxml/prodottoBundle.fxml"));
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
    protected void updateItem(Pair<Prodotto, Integer> bundle, boolean empty)
    {
        super.updateItem(bundle, empty);
        if(empty)
        {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);

        } else {

            // Setta informazioni sull'item
            nomeLabel.setText(bundle.getKey().getNome());
            marcaLabel.setText(bundle.getKey().getMarca());
            quantitaLabel.setText(String.valueOf(bundle.getValue()));

            int prezzoTotale = bundle.getKey().getPrezzo() * bundle.getValue();
            prezzoLabel.setText(String.valueOf(prezzoTotale));

            prodottoImageView.setPreserveRatio(true);
            prodottoImageView.setImage(bundle.getKey().getImage());

            setPrefWidth(515);
            setPrefHeight(96);

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            setGraphic(pane);
        }
    }
}
