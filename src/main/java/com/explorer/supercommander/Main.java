package com.explorer.supercommander;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        SceneSingleton sceneSingleton = new SceneSingleton(primaryStage);
        sceneSingleton.go();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
