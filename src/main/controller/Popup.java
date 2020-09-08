package main.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Optional;

public abstract class Popup<T> implements IPopup<T>
{
    private Stage dialog;
    private Optional<T> result;

    public Popup(String filename)
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(filename));
        loader.setController(self());

        dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initStyle(StageStyle.UNDECORATED);

        try { dialog.setScene(new Scene(loader.load())); }
        catch (Exception e)
        {
            System.err.println("Impossibile caricare FXML");
            e.printStackTrace();
        }

        result = Optional.empty();
    }

    // Setta il risultato di questo popup
    protected void close(T value)
    {
        result = Optional.of(value);
        dialog.close();
    }

    // Ritorna 'this' nella classe derivata
    protected abstract Popup<T> self();

    // Evento quando visualiziamo
    protected void showEvent() { }

    @Override
    public Optional<T> show()
    {
        showEvent();
        dialog.showAndWait();
        return result;
    }
}
