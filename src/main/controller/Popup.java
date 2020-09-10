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
        dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        //dialog.initStyle(StageStyle.UNDECORATED);

        result = Optional.empty();

        FXMLLoader loader = new FXMLLoader(getClass().getResource(filename));
        loader.setController(self());

        try { dialog.setScene(new Scene(loader.load())); }
        catch (Exception e)
        {
            System.err.println("Impossibile caricare FXML");
            e.printStackTrace();
        }
    }

    public abstract Object self();

    // Setta il risultato di questo popup
    protected void close(T value)
    {
        result = Optional.of(value);
        dialog.close();
    }

    // Invocato quando viene mostrato il popup
    protected void onShow() { }

    @Override
    public Optional<T> show()
    {
        onShow();
        dialog.showAndWait();
        return result;
    }
}
