package controller;

import javafx.application.Application;
import presentationLayer.InterfaceLoader;

public class Controller {
    public void start(){ new Thread(() -> Application.launch(InterfaceLoader.class)).start(); }
}
