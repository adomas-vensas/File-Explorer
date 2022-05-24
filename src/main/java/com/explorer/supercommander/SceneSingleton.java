package com.explorer.supercommander;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public final class SceneSingleton {
    private static SceneSingleton INSTANCE;
    private Stage stage;

    public SceneSingleton(Stage stage) throws IOException {
        this.stage = stage;
    }
    private SceneSingleton() throws IOException {

    }

    public static SceneSingleton getInstance() throws IOException {
        if(INSTANCE == null){
            INSTANCE = new SceneSingleton();
        }
        return INSTANCE;
    }

    public void go() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Scene.fxml"));
        stage.setTitle("Special Commander");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
