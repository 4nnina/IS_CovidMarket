package main.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertPopup
{
    // Mostra un Popup di avvertimento
    public static void warning(String message)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.APPLY);
        alert.showAndWait();
    }

    // Mostra un Popup di errore
    public static void error(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.APPLY);
        alert.showAndWait();
    }
}
