package com.explorer.supercommander;

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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;


import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.ResourceBundle;

import static com.explorer.supercommander.FileExplorerFx.CurrDirStr;

public class ControllerTableView implements Initializable {
    @FXML private TextArea textArea;
    @FXML private Button saveBtn = new Button();
    @FXML private Button loadBtn = new Button();
    @FXML private TableView<FileInfo> tableview = new TableView<>();
    @FXML private TableColumn<FileInfo, String> date = new TableColumn<>();
    @FXML private TableColumn<FileInfo, String> name = new TableColumn<>();
    @FXML private TableColumn<FileInfo, String> size = new TableColumn<>();
    @FXML private TableColumn<FileInfo, String> type = new TableColumn<>();
    Desktop desktop;
    public static FileExplorerFx Fx2;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Fx2 = new ClassTableView();

        Fx2.setValues(tableview,/*image,*/date, name, size, type);
        if (FileExplorerFx.CurrDirFile == null) {
            FileExplorerFx.CurrDirFile = new File("./");
            CurrDirStr = FileExplorerFx.CurrDirFile.getAbsolutePath();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        File[] files;
        ObservableList<FileInfo> list;
        if (FileExplorerFx.CurrDirFile == null) {
            FileExplorerFx.CurrDirFile = new File("./");
        }
        files = FileExplorerFx.CurrDirFile.listFiles();

        //USAGE OF COLLECTION
        LinkedList<FileInfo> arr = new LinkedList<>();
        for (File file: files) {
            String s1 = null;   //name
            String s2 = null;   //size
            String s3 = null;   //type
            String s4 = null;   //date
            try {
                BasicFileAttributes basicFileAttributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
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

                    if(basicFileAttributes.isDirectory()){
                        s3 = "<DIR>";
                    } else if(file.isHidden()){
                        s3 = "";
                    }
                }
                s2 = Fx2.calculateSize(file);
                s4 = sdf.format(basicFileAttributes.lastModifiedTime().toMillis());
            } catch (Exception x) {
                System.out.println("Exception detected in tableview strings: " + x.getMessage());
            }
            arr.add(new FileInfo(s1, s2, s3, s4));
        }

        list = FXCollections.observableList(arr);

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

        tableview.setOnMousePressed(event -> {
            if(event.isSecondaryButtonDown()) {
                contextMenu.show(tableview, event.getScreenX(), event.getScreenY());
            } else {
                contextMenu.hide();
            }

        });

        edit.setOnAction(new EventHandler<>() {
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
                    contr.setSaveBtn(fileName, fileExtension, content);
                    contr.setLoadBtn(contr);

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

        delete.setOnAction(actionEvent -> {
            FileInfo item = tableview.getSelectionModel().getSelectedItem();
            String pathName = FileExplorerFx.replaceSlashes(CurrDirStr);

            try{
                Files.deleteIfExists(Paths.get(pathName + item.getName() + item.getType()));
                tableview.getItems().remove(item);
                System.out.println("Deleted file: " + item.getName() + item.getType());
            } catch(IOException e){
                System.out.println("File could not be deleted: " + e.getMessage());
            }

        });

    }

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
                    FileExplorerFx.CurrDirFile = file;
                    CurrDirStr = FileExplorerFx.CurrDirFile.getAbsolutePath();
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

    public void setTextArea(String text){
        textArea.setText(text);
    }

    public void setSaveBtn(String fileName, String fileExtension, String content){
        saveBtn.setOnAction(event -> {
            String text = textArea.getText();
            String pathName = FileExplorerFx.replaceSlashes(CurrDirStr);

            try {
                FileWriter writer = new FileWriter(pathName + fileName + fileExtension, false);
                writer.write(text);
                writer.close();
            } catch (IOException e) {
                System.out.println("Error while editing");
                throw new RuntimeException(e);
            }

            //Save previous state to binary file
            ObjectOutputStream output;
            try {
                output = new ObjectOutputStream(new FileOutputStream("savefile.ser"));
            } catch (IOException e1) {
                throw new RuntimeException(e1);
            }

            try {
                output.writeObject(content);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                output.close();
            } catch (IOException e1) {
                throw new RuntimeException(e1);
            }

            System.out.println("Saved succesfully");

        });
    }

    public final void setLoadBtn(ControllerTableView contr){
        loadBtn.setOnAction(actionEvent -> {
            ObjectInputStream input;
            try {
                input = new ObjectInputStream(new FileInputStream("savefile.ser"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            try{
                String text = (String) input.readObject();
                contr.setTextArea(text);
            } catch(ClassNotFoundException | IOException e){
                e.printStackTrace();
            }


            try {
                input.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println("Load success");
        });
    }



}
