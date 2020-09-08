package main.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.controller.Controller;
import main.controller.IController;
import main.controller.IController2;

import java.io.IOException;
import java.util.HashMap;

// Manager dell'interfaccia, questa è descritta da una enumerazione
// dei pannelli e dal controller principale
public class Dashboard<T extends Enum<T>>
{
    // Nodo e Controller di uno stage
    private static class StageData<T extends Enum<T>>
    {
        public Pane pane;
        public IController2<T> controller;

        public StageData(Pane pane, IController2<T> controller)
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
        dashboardStageData.controller.onSwapPane(null);
        primaryStage.show();
    }

    // Passa ad un nuovo pannello dinamico fornendo anche un puntatore
    // a dati aggiuntivi utili
    public IController2<T> swap(T target, Object data)
    {
        StageData stageData = stageDataHashMap.get(target);
        if (stageData != null)
        {
            // Cambia visuale
            gatePane.getChildren().clear();
            gatePane.getChildren().add(stageData.pane);
            stageData.controller.onSwapPane(data);

            return stageData.controller;
        }

        AlertPopup.warning("La sezione " + target.name() + " non è stata caricata!");
        return null;
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
                IController2<T> controller = dashboardStageData.controller;
                this.gatePane = controller.getGatePane();
            }

            return this;
        }

        // Indica il file fxml di ogni sezione
        public Builder<T> controllerSection(T section, String filename)
        {
            StageData<T> stageData = loadFXML(filename);
            if (stageData != null)
                stageDataHashMap.put(section, stageData);

            return this;
        }

        // Carica file FXML e ritorna il suo bundle, con Nodo e Controller
        private StageData<T> loadFXML(String filename)
        {
            try
            {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(filename));
                Pane pane = fxmlLoader.load();

                IController2<T> controller = fxmlLoader.getController();
                assert controller instanceof IController2;

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
