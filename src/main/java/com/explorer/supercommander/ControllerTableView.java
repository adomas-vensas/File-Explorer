package com.explorer.supercommander;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;


import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.ResourceBundle;

import static com.explorer.supercommander.FileExplorerFx.CurrDirStr;

public class ControllerTableView implements Initializable {
    /**
     *
     */

//    @FXML private TableColumn<FileInfo, ImageView> image;
    @FXML private TextArea textArea;
    @FXML private Button saveBtn = new Button();
    @FXML private TableView<FileInfo> tableview = new TableView<>();
    @FXML private TableColumn<FileInfo, String> date = new TableColumn<>();
    @FXML private TableColumn<FileInfo, String> name = new TableColumn<>();
    @FXML private TableColumn<FileInfo, String> size = new TableColumn<>();
    @FXML private TableColumn<FileInfo, String> type = new TableColumn<>();
    private Desktop desktop;
    public static FileExplorerFx Fx2;

    /**
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Fx2 = new ClassTableView();

        Fx2.setValues(tableview,/*image,*/date, name, size, type);
        if (Fx2.CurrDirFile == null) {
            Fx2.CurrDirFile = new File("./");
            CurrDirStr = Fx2.CurrDirFile.getAbsolutePath();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        File[] files;
        ObservableList<FileInfo> list;
        if (Fx2.CurrDirFile == null) {
            Fx2.CurrDirFile = new File("./");
        }
        files = Fx2.CurrDirFile.listFiles();

        LinkedList<FileInfo> arr = new LinkedList<>();
        for (File file: files) {
            String s1 = null;   //name
            String s2 = null;   //size
            String s3 = null;   //type
            String s4 = null;   //date
//                ImageView img = null;
            try {
                if (Fx2.IsDrive(file)) {
                    s1 = file.getAbsolutePath();
                } else {
                    String temp = file.getName();
                    if(temp.lastIndexOf(".") != -1 && !temp.startsWith(".")){
                        s3 = temp.substring(temp.lastIndexOf("."));
                        s1 = temp.substring(0, temp.lastIndexOf("."));
                    } else {
                        s1 = temp;
                    }

                    if(file.isDirectory()){
                        s3 = "<DIR>";
                    } else if(file.isHidden()){
                        s3 = "";
                    }
//                    img = new ImageView(Fx2.getIconImageFX(fl[i]));
                }
                s2 = Fx2.calculateSize(file);
                s4 = sdf.format(file.lastModified());
            } catch (Exception x) {
                System.out.println("Exception detected in tableview strings: " + x.getMessage());
            }
            arr.add(new FileInfo(/*img,*/s1, s2, s3, s4));
        }

        list = FXCollections.observableList(arr);

//      image.setCellValueFactory(new PropertyValueFactory<>("image"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableview.setItems(list);

        // Create context menu
        ContextMenu contextMenu = new ContextMenu();
        MenuItem edit = new MenuItem("Edit file");
        MenuItem delete = new MenuItem("Delete file");

        contextMenu.getItems().addAll(edit, delete);

        tableview.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.isSecondaryButtonDown()) {
                    contextMenu.show(tableview, event.getScreenX(), event.getScreenY());
                } else {
                    contextMenu.hide();
                }

            }
        });

        edit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileInfo selectedItem = tableview.getSelectionModel().getSelectedItem();
                String fileName = selectedItem.getName();
                String fileExtension = selectedItem.getType();
                if(fileExtension == null || !fileExtension.equals(".txt")){
                    return;
                }

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("EditText.fxml"));
                    Parent root = loader.load();
                    ControllerTableView contr = loader.getController();

                    String content = Files.readString(Path.of(CurrDirStr + "\\" + fileName + fileExtension));
                    contr.setTextArea(content);
                    contr.setSaveBtn(fileName, fileExtension);

                    Stage stage = new Stage();
                    stage.setTitle("Text Editor");
                    stage.setScene(new Scene(root));

                    stage.show();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

            }

        });

        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileInfo item = tableview.getSelectionModel().getSelectedItem();
                String pathName = replaceSlashes(CurrDirStr);

                try{
                    Files.deleteIfExists(Paths.get(pathName + item.getName() + item.getType()));
                    tableview.getItems().remove(item);
                    System.out.println("Deleted file: " + item.getName() + item.getType());
                } catch(IOException e){
                    System.out.println("File could not be deleted: " + e.getMessage());
                }

            }
        });

    }

    /**
     *
     * @param mouseEvent
     */
    @FXML
    private void handleTableMouseClicked(MouseEvent mouseEvent){

        if(mouseEvent.getClickCount() == 2){
            FileInfo selectedItem = tableview.getSelectionModel().getSelectedItem();
            String fileName = selectedItem.getName();
            String fileExtension = selectedItem.getType();

            if(fileExtension.equals("<DIR>")){
                fileExtension = "";
            }
            String path = CurrDirStr+ "\\" + fileName + fileExtension;

            System.out.println(path);
            File file = new File(path);
            if(file.isDirectory() ){
                try{
                    Fx2.CurrDirFile = file;
                    CurrDirStr = Fx2.CurrDirFile.getAbsolutePath();
                    Fx2.setLabelTxt();
                    tableview.getItems().clear();
                    Fx2.CreateTableView();
                } catch(Exception x){
                    System.out.println(x.getMessage());
                }
            } else if(file.isFile()){
                desktop = Desktop.getDesktop();
                try{
                    desktop.open(file);
                } catch(IOException x){
                    System.out.println(x.getMessage());
                }
            }
        }

    }

    /**
     *
     * @param text
     */
    public void setTextArea(String text){
        textArea.setText(text);
    }

    /**
     *
     * @param fileName
     * @param fileExtension
     */
    public void setSaveBtn(String fileName, String fileExtension){
        saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String text = textArea.getText();
                String pathName = replaceSlashes(CurrDirStr);

                try {
                    FileWriter writer = new FileWriter(pathName + fileName + fileExtension, false);
                    writer.write(text);
                    System.out.println("Saved");
                    writer.close();
                } catch (IOException e) {
                    System.out.println("Error while editing");
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /**
     *
     * @param pathName
     * @return
     */
    public String replaceSlashes(String pathName){
        if(!pathName.endsWith(".")){
            pathName += "\\";
        } else {
            pathName = pathName.substring(0, pathName.length() - 1);
        }

        return pathName;
    }
}
