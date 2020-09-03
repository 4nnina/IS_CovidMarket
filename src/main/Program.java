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

        try(BinaryDeserializer ser = new BinaryDeserializer(PATH_DATABASE)) {
            database.load(ser);
        }

/*
        database.getUtenti().add(new Utente("sis", "sis", "sis sis 29", "sis",
                "3779927407", "sis@gmail.com", 36071, "sis".hashCode(), null, MetodoPagamento.Nessuno));

        database.getResponsabili().add(new Responsabile("asm", "asm", "asm asm 29",
                "asm", "377992704", "asm", 0, "asm".hashCode(), Calendar.getInstance().getTime(), "asm",
                EnumSet.of(Reparto.Carne, Reparto.Alimentari), 0, "asm"));

        database.getProdotti().add(new Prodotto(Reparto.Alimentari, "banana", "oreo",
                76, 6, null, 1, EnumSet.of(Attributo.Vegetariano)));
        database.getProdotti().add(new Prodotto(Reparto.Biscotti, "elicottero", "oreo",
                76, 6, null, 1, EnumSet.of(Attributo.Vegetariano)));
 */
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
