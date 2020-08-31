package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.model.*;

public class Program extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        Database.getInstance().load();
        var user = Database.getInstance().utenti;

        // Carica tutti i dati dalla memoria secondaria
        //Database.getInstance().load();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/fxml/login_page.fxml"));
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Covid Market");
        stage.show();

        // Salva tutti i dati dalla memoria secondaria
        //Database.getInstance().save();
    }

    public static void main(String[] args)
    {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        launch(args);
    }
}
