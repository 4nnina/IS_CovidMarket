package main.controller;

import javafx.fxml.Initializable;
import main.model.Persona;
import main.utils.StageManager;

public abstract class Controller implements IController
{
    protected StageManager stageManager;

    @Override
    public void setStageManager(StageManager stageManager) {
        this.stageManager = stageManager;
    }
    public void onSwap(Persona target) { }
}
