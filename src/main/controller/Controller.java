package main.controller;

import main.utils.Dashboard;

public abstract class Controller<T extends Enum<T>> implements IController<T>
{
    protected Dashboard<T> dashboard;

    @Override
    public void setDashboard(Dashboard<T> dashboard) {
        this.dashboard = dashboard;
    }
}
