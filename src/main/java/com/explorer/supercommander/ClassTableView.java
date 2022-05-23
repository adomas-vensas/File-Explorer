package com.explorer.supercommander;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.LinkedList;

public class ClassTableView extends FileExplorerFx {
    /**
     *
     */
    ClassTableView(){}
    public void setValues(TableView<FileInfo> tableview/*,TableColumn<FileInfo, ImageView> image*/,TableColumn<FileInfo, String> date,
                          TableColumn<FileInfo, String> name,TableColumn<FileInfo, String> size, TableColumn<FileInfo, String> type){
        this.tableview = tableview;
        this.date = date;
        this.name = name;
        this.size = size;
        this.type = type;
//        this.image=image;
    }


    @Override
    public void CreateTableView() {
        sdf = new SimpleDateFormat("dd/MM/yy");
        File[] files;
        ObservableList<FileInfo> list;
        if(CurrDirFile==null){CurrDirFile = new File("./"); }
        if(CurrDirName.equals("This PC")){
            files = File.listRoots();
        }
        else{
            files = CurrDirFile.listFiles();
        }

        // donot delete . original
        LinkedList<FileInfo> arr = new LinkedList<>();
        for(File file: files){
            String s1 = null;   //name
            String s2 = null;   //size
            String s3 = null;   //type
            String s4 = null;   //date
//            ImageView img = null;

            try{
                if(IsDrive(file)){
//                    img = new ImageView(getIconImageFX(file));
                    s1 = file.getAbsolutePath();
                } else{
                    String temp = file.getName();
                    if(temp.lastIndexOf(".") != -1 && !temp.startsWith(".")){
                        s3 = temp.substring(temp.lastIndexOf("."));
                        s1 = temp.substring(0, temp.lastIndexOf("."));
                    } else {
                        s1 = temp;
                    }
//                    img = new ImageView(getIconImageFX(file));
                }
                s2 = calculateSize(file);
                String temp = file.getName();
                if(temp.lastIndexOf(".") != -1 && !temp.startsWith(".")) {
                    s3 = temp.substring(temp.lastIndexOf("."));
                } else if(file.isDirectory()){
                    s3 = "<DIR>";
                } else if(file.isHidden()){
                    s3 = "";
                }
                s4 = sdf.format(file.lastModified());
            }catch(Exception x){
                System.out.println("Exception detected in tableview strings: "+x.getMessage());
            }
            arr.add(new FileInfo(/*img,*/s1, s2, s3, s4));
        }

        list = FXCollections.observableList(arr);

        //id.setCellValueFactory(new PropertyValueFactory<Student, Integer>("id"));
//        image.setCellValueFactory(new PropertyValueFactory<>("image"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        size.setCellValueFactory(new PropertyValueFactory<>("size"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableview.setItems(list);
    }

    @Override
    public void CreateTiles() { }
    @Override
    public TreeItem<String>[] TreeCreate(File dir) {return null;}
    @Override
    public String FindAbsolutePath(TreeItem<String> item, String s) {return null;}
    @Override
    public void CreateTreeView(TreeView<String> treeview) {}

    @Override
    public void CreateTableView(TableView<FileInfo> tableview, TableColumn<FileInfo, String> date, TableColumn<FileInfo, String> name, TableColumn<FileInfo, String> size, TableColumn<FileInfo, String> type) {

    }

    @Override
    public void CreateTilesView() {}
    @Override
    public void Initiate() {}

}
