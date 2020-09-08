package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import main.model.Carrello;
import main.model.Spesa;

import java.io.IOException;

public class SpesaCell extends ListCell<Spesa>
{
    @FXML
    private AnchorPane pane;

    @FXML
    private Label statoLabel;

    @FXML
    private Label idLabel;

    // Carica stile fxml
    public SpesaCell()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/fxml/spesaItem.fxml"));
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
    protected void updateItem(Spesa spesa, boolean empty)
    {
        super.updateItem(spesa, empty);
        if(empty)
        {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);

        } else {

            statoLabel.setText(spesa.getStatoConsegna().name());
            idLabel.setText( Integer.toUnsignedString(spesa.hashCode()));

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            setGraphic(pane);
        }
    }
}
