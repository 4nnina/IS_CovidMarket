package main.controller;

import javafx.scene.layout.Pane;
import main.utils.Dashboard;

public interface IController<T extends Enum<T>>
{
    void setDashboard(Dashboard<T> stageManager);

    // Evento invocato al cambio di pannello
    void onSwapPane(Object data);

    // Ritorna il nodo principale usato per i collegamenti
    Pane getGatePane();
}
