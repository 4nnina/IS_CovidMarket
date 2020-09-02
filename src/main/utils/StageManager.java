package main.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.controller.*;

import java.io.IOException;
import java.util.ArrayList;

public class StageManager
{
    private static final String PATH_FXML = "../resources/fxml/";

    private static class StageBundle
    {
        public Scene scene;
        public IController controller;
        public String title;

        public StageBundle(Scene scene, IController controller, String title)
        {
            this.controller = controller;
            this.scene = scene;
            this.title = title;
        }
    }

    private StageBundle[] stageBundles = new StageBundle[Stages.values().length];
    private Stage primaryStage;

    public StageManager(Stage primaryStage)
    {
        this.primaryStage = primaryStage;

        initStage(Stages.Login, "Covid Market - Login", PATH_FXML + "login_page.fxml");
        initStage(Stages.Registrazione, "Covid Market - Registrazione", PATH_FXML + "registrazioneUtente.fxml");
        initStage(Stages.HomeResponsabile, "Covid Market - Home", PATH_FXML + "homeResponsanili.fxml");
        initStage(Stages.HomeUtente, "Covid Market - Home", PATH_FXML + "home.fxml");
        initStage(Stages.ModificaUtente, "Covid Market - Modifica", PATH_FXML + "modificaUtente.fxml");
        initStage(Stages.Profilo, "Covid Market - Profilo", PATH_FXML + "profilo.fxml");
        initStage(Stages.Tessera, "Covid Market - Carta Fedeltà", PATH_FXML + "saldoPunti_tessera.fxml");
    }

    public void initStage(Stages target, String title, String filename)
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(filename));
            Scene scene = new Scene(fxmlLoader.load());
            IController controller = fxmlLoader.getController();
            controller.setStageManager(this);

            stageBundles[target.ordinal()] = new StageBundle(scene, controller, title);
        }
        catch (IOException e)
        {
            System.out.println("Errore caricamento file fxml da " + filename);
            e.printStackTrace();
        }
    }

    public IController swap(Stages target)
    {
        StageBundle bundle = stageBundles[target.ordinal()];
        primaryStage.setScene(bundle.scene);
        primaryStage.setTitle(bundle.title);
        primaryStage.show();

        return bundle.controller;
    }
}
