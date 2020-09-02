package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.storage.BinaryDeserializer;
import main.storage.BinarySerializer;
import main.storage.Database;
import main.utils.StageManager;

public class Program extends Application
{
    private static final String PATH_DATABASE = "database.bin";

    @Override
    public void start(Stage stage) throws Exception
    {

        /*
        Utente filippo = new Utente("Filippo", "Ziche", "Via Tagliamento 29",
                "Arzignano", "36071", "filippo.ziche@gmail.com",
                36071, "ciscocisco".hashCode(), new CartaFedelta("3056817689",
                Calendar.getInstance().getTime(), 0), MetodoPagamento.PayPal);
         */

        StageManager loginPage = new StageManager();
        loginPage.setStageLogin(stage);

        Database database = Database.getInstance();
        database.load(new BinaryDeserializer(PATH_DATABASE));

        // FAI COSE

        database.save(new BinarySerializer(PATH_DATABASE));
    }

    public static void main(String[] args)
    {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        launch(args);
    }
}
