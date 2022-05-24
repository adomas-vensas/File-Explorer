package com.explorer.supercommander;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;

import java.io.File;


public interface FileExplorer {
    /**this class will contain all the method's name. as other derived classes
     * will be created, they will implement necessary methods accordingly...*/
    Image getIconImageFX(File f);
    TreeItem<String>[] TreeCreate(File dir);
    String calculateSize(File f);
    String FindAbsolutePath(TreeItem<String> item, String s);
    boolean IsDrive(File f);
    int FilesHiddensCount(File dir);
    void CreateTreeView(TreeView<String> treeview);
    void CreateTableView(TableView<FileInfo> tableview, TableColumn<FileInfo, String> date,
                         TableColumn<FileInfo, String> name, TableColumn<FileInfo, String> size, TableColumn<FileInfo, String> type);
    void CreateTableView();
    void CreateTilesView();
    void setLabelTxt();
    void Initiate();
    void setValues(TableView<FileInfo> tableview, TableColumn<FileInfo, String> date,
                   TableColumn<FileInfo, String> name,TableColumn<FileInfo, String> size, TableColumn<FileInfo, String> type);
    void CreateTiles();
    /**********************/
    public int NumOfDirectChilds(File f);

}