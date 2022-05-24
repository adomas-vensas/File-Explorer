package com.explorer.supercommander;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.TilePane;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerTilesView implements Initializable {
    @FXML private TilePane tilePane;
    public static FileExplorerFx Fx3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Fx3 = new ClassTilesView();
        Fx3.tilePane = tilePane;
        Fx3.CreateTiles();
    }

}
