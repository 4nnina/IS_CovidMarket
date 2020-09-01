package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.model.*;
import main.utils.StageManager;

import java.util.Calendar;
import java.util.Date;

public class Program extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {

        /*
        Utente filippo = new Utente("Filippo", "Ziche", "Via Tagliamento 29",
                "Arzignano", "36071", "filippo.ziche@gmail.com",
                36071, "ciscocisco".hashCode(), new CartaFedelta("3056817689",
                Calendar.getInstance().getTime(), 0), MetodoPagamento.PayPal);


        Database db = Database.getInstance();
        db.getUtenti().add(filippo);
        db.save("database.bin");
         */

        Database db1 = Database.getInstance()
                .load("database.bin");

        StageManager loginPage = new StageManager();
        loginPage.setStageLogin(stage);

        //Database.getInstance()
        //      .save("database.bin");
    }

    public static void main(String[] args)
    {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        launch(args);
    }
}
