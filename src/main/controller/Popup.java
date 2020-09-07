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

    public Popup()
    {
        dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initStyle(StageStyle.UNDECORATED);

        result = Optional.empty();
    }

    protected void loadFXML(Object controller, String filename)
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(filename));
        loader.setController(this);

        try { dialog.setScene(new Scene(loader.load())); }
        catch (Exception e)
        {
            System.err.println("Impossibile caricare FXML");
            e.printStackTrace();
        }
    }

    // Setta il risultato di questo popup
    protected void returnResult(T value)
    {
        result = Optional.of(value);
        dialog.close();
    }

    @Override
    public Optional<T> show()
    {
        dialog.showAndWait();
        return result;
    }
}
