package main.controller;

import main.utils.Dashboard;

public abstract class Controller2<T extends Enum<T>> implements IController2<T>
{
    protected Dashboard<T> dashboard;

    @Override
    public void setDashboard(Dashboard<T> dashboard) {
        this.dashboard = dashboard;
    }
}
