package main;

import javafx.application.Application;
import javafx.stage.Stage;
import main.controller.Stages;
import main.model.*;
import main.storage.BinaryDeserializer;
import main.storage.BinarySerializer;
import main.storage.Database;
import main.utils.StageManager;

import java.time.LocalDate;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.function.Predicate;

public class Program extends Application
{
    private static final String PATH_DATABASE = "src/main/resources/database.bin";

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Database database = Database.getInstance();

        try(BinaryDeserializer ser = new BinaryDeserializer(PATH_DATABASE)) {
            database.load(ser);
        }


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
