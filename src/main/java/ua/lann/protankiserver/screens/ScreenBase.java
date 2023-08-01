package ua.lann.protankiserver.screens;

import ua.lann.protankiserver.ClientController;

public abstract class ScreenBase {
    public final ClientController controller;

    protected ScreenBase(ClientController controller) {
        this.controller = controller;
    }

    public abstract void open();
    public abstract void close();
}
