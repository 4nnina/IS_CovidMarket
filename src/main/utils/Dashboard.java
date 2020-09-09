package main.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;
import main.controller.IController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

// Manager dell'interfaccia, questa è descritta da una enumerazione
// dei pannelli e dal controller principale
public class Dashboard<T extends Enum<T>>
{
    // Nodo e Controller di uno stage
    public static class StageData<T extends Enum<T>>
    {
        public Pane pane;
        public IController<T> controller;

        // Indica se questa schermata può essere raggiunta tramite menu principale
        // Utile per schermate secondarie dipendenti da altre, tipo "modifica profilo"
        public boolean menuLink = true;

        public StageData(Pane pane, IController<T> controller)
        {
            this.pane = pane;
            this.controller = controller;
        }
    }

    // Controller dedicato al controllo della parte esterna della UI
    private StageData<T> dashboardStageData;

    // Nodo dove vengono inseriti i pannelli dinamici
    private Pane gatePane;

    // Stage principale
    private Stage primaryStage;

    // Mappa che associa ad ogni elemento dell'enum il suo pannello dinamico
    private HashMap<T, StageData<T>> stageDataHashMap;

    // Costruisce la dashboard dal builder
    public Dashboard(Object data, Builder<T> builder)
    {
        this.primaryStage = builder.primaryStage;
        this.dashboardStageData = builder.dashboardStageData;
        this.stageDataHashMap = builder.stageDataHashMap;
        this.gatePane = builder.gatePane;

        // Collega tutti i pannelli
        dashboardStageData.controller.setDashboard(this);
        for(StageData<T> stageData : stageDataHashMap.values())
            stageData.controller.setDashboard(this);

        // Controller principale
        primaryStage.setScene(new Scene(dashboardStageData.pane));
        primaryStage.setTitle("Covid Market");

        dashboardStageData.controller.onSwapPane(data);
        primaryStage.show();
    }

    // Passa ad un nuovo pannello dinamico fornendo anche un puntatore
    // a dati aggiuntivi utili
    public IController<T> swap(T target, Object data)
    {
        StageData<T> stageData = stageDataHashMap.get(target);
        if (stageData != null)
        {
            // Cambia visuale
            gatePane.getChildren().clear();
            gatePane.getChildren().add(stageData.pane);
            stageData.controller.onSwapPane(data);

            primaryStage.setTitle("Covid Market - " + target.name());
            return stageData.controller;
        }

        AlertPopup.warning("La sezione " + target.name() + " non è stata caricata!");
        return null;
    }

    // Ottiene lista degli stage caricati
    public ArrayList<Pair<T, StageData<T>>> getStages()
    {
        var result = new ArrayList<Pair<T, StageData<T>>>();
        for(var entry : stageDataHashMap.entrySet())
            result.add(new Pair<>(entry.getKey(), entry.getValue()));

        return result;
    }

    // Ottiene stage primario
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    // Costruisce una nuova Dashboard customizzata
    public static class Builder<T extends Enum<T>>
    {
        // Nodo dove vengono inseriti i pannelli dinamici
        private Pane gatePane;

        // Stage principale
        private Stage primaryStage;

        // Controller dedicato al controllo della parte esterna della UI
        private StageData<T> dashboardStageData;

        // Mappa che associa ad ogni elemento dell'enum il suo pannello dinamico
        private HashMap<T, StageData<T>> stageDataHashMap = new HashMap<>();

        // Costruisce la dashboard da un file fxml, questo deve avere un pannello chiamato
        // 'gatePanel' dove verranno inseriti i pannelli dinamici
        public Builder(Stage primaryStage)
        {
            this.primaryStage = primaryStage;
        }

        // Indica file fxml principale
        public Builder<T> controllerDashboard(String filename)
        {
            StageData<T> stageData = loadFXML(filename);
            if (stageData != null) {
                this.dashboardStageData = stageData;

                // Trova gatePane
                IController<T> controller = dashboardStageData.controller;
                this.gatePane = controller.getGatePane();
            }

            return this;
        }

        // Indica il file fxml di ogni sezione
        public Builder<T> controllerSection(T section, boolean menuLink, String filename)
        {
            StageData<T> stageData = loadFXML(filename);
            if (stageData != null)
            {
                stageData.menuLink = menuLink;
                stageDataHashMap.put(section, stageData);
            }

            return this;
        }

        // Carica file FXML e ritorna il suo bundle, con Nodo e Controller
        private StageData<T> loadFXML(String filename)
        {
            try
            {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(filename));
                Pane pane = fxmlLoader.load();

                IController<T> controller = fxmlLoader.getController();
                assert controller instanceof IController;

                return new StageData<>(pane, controller);
            }
            catch (IOException e)
            {
                System.out.println("Errore caricamento file fxml da " + filename);
                e.printStackTrace();
            }

            return null;
        }

        // Finalizza la costruizione
        public Dashboard<T> launch(Object data) {
            return new Dashboard<>(data, this);
        }
    }
}
