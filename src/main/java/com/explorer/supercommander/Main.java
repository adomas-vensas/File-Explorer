package com.explorer.supercommander;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    /**
     *
     * @param primaryStage
     * @throws Exception
     */

    @Override
    public void start(Stage primaryStage) throws Exception{
        SceneSingleton sceneSingleton = new SceneSingleton(primaryStage);
        sceneSingleton.go();
    }

    /**
     *
     * @param args
     */

    public static void main(String[] args) {
        launch(args);
    }
}
