package main.controller;

import main.model.Persona;
import main.utils.StageManager;

public interface IController
{
    void setStageManager(StageManager stageManager);
    void onSwap(Persona persona);
}
