package main.controller;

import main.utils.StageManager;

public abstract class Controller implements IController
{
    protected StageManager stageManager;

    @Override
    public void setStageManager(StageManager stageManager) {
        this.stageManager = stageManager;
    }
}
