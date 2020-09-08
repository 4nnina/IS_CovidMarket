package main.controller;

import javafx.scene.layout.Pane;
import main.model.Persona;
import main.utils.Dashboard;
import main.utils.StageManager;

public interface IController2<T extends Enum<T>>
{
    void setDashboard(Dashboard<T> stageManager);

    // Evento invocato al cambio di pannello
    void onSwapPane(Object data);

    // Ritorna il nodo principale usato per i collegamenti
    Pane getGatePane();
}
