package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.controller.Stages;
import main.model.*;
import main.storage.BinaryDeserializer;
import main.storage.BinarySerializer;
import main.storage.Database;
import main.storage.ISerializer;
import main.utils.StageManager;

import java.util.Calendar;
import java.util.EnumSet;

public class Program extends Application
{
    private static final String PATH_DATABASE = "database.bin";

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Database database = Database.getInstance();

        /*
        try(BinaryDeserializer ser = new BinaryDeserializer(PATH_DATABASE)) {
            database.load(ser);
        }

         */

        Utente sis = new Utente.Builder()
                .setNominativo("sis", "sis")
                .setIndirizzo("sis sis 29", "sis", 36071)
                .setEmail("sis@gmail.com")
                .setTelefono("3779927407")
                .setCartaFedelta(null)
                .setMetodoPagamento(MetodoPagamento.PayPal)
                .setPassword("sis")
                .build();

        database.getUtenti().add(sis);

        database.getProdotti().add(new Prodotto.Builder()
                .setNome("Banana")
                .setMarca("dwad")
                .addAttributo(Attributo.SenzaLatte)
                .addAttributo(Attributo.Vegetariano)
                .setImage(null)
                .setPrezzo(5)
                .setReparto(Reparto.Alimentari)
                .setQuantitaDisponibile(4)
                .setQuantitaPerConfezione(4)
                .build()
        );

        StageManager stageManager = new StageManager(primaryStage);
        stageManager.swap(Stages.Login);
    }

    @Override
    public void stop() throws Exception
    {
        super.stop();

        Database database = Database.getInstance();
        try(BinarySerializer ser = new BinarySerializer(PATH_DATABASE)) {
            database.save(ser);
        }
    }

    public static void main(String[] args)
    {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        launch(args);
    }
}
