package com.explorer.supercommander;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.explorer.supercommander.ControllerTableView.Fx2;
import static com.explorer.supercommander.ControllerTilesView.Fx3;

public class Controller implements Initializable {
    /**
     *
     */

    @FXML private Button btn;
    @FXML private Pane secPane;
    @FXML private TreeView<String> treeview;
    @FXML private Label label;
    private int count;
    public static ClassTreeView Fx1;
    /**
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        count = 0;
        Fx1 = new ClassTreeView();

        Fx1.CurrDirFile = new File("./");
        Fx1.CurrDirStr = Fx1.CurrDirFile.getAbsolutePath();
        Fx1.lbl = label;
        Fx2.lbl = label;
        label.setText(Fx1.CurrDirStr);
        try{
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("Scene2.fxml"));
            secPane.getChildren().add(newLoadedPane);
        }catch(NullPointerException x){
            x.printStackTrace();
        }
        catch(IOException x){
            x.printStackTrace();
        }
        Fx1.CreateTreeView(treeview);

    }


    /**
     *
     * @param mouseEvent
     */
    @FXML
    private void handleMouseClicked(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount() == 1){
            try{
                TreeItem<String> item = treeview.getSelectionModel().getSelectedItem();
                Fx1.CurrDirName = item.getValue();
                System.out.println("Selected Text : " + item.getValue());
                Fx1.CurrDirFile = new File(Fx1.FindAbsolutePath(item,item.getValue()));
                Fx1.CurrDirStr = Fx1.CurrDirFile.getAbsolutePath();
                label.setText(Fx1.CurrDirStr);
                Fx2.tableview.getItems().clear();
                Fx2.CreateTableView();
                Fx3.CreateTiles();
            }catch(Exception x){
                System.out.println(x.getMessage());
            }
        }
    }

    /**
     *
     * @param event
     * @throws IOException
     */
//    @FXML private void loadFxml (ActionEvent event) throws IOException {
//        count = ( count+1 ) % 2;
//        String setScene = (count == 0) ? "Scene2.fxml" : "Scene3.fxml";
//        Pane newLoadedPane = FXMLLoader.load(getClass().getResource(setScene));
//
//        secPane.getChildren().clear();
//        secPane.getChildren().add(newLoadedPane);
//    }
}
