package com.explorer.supercommander;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;


public class ClassTreeView extends FileExplorerFx {
    ClassTreeView(){}

    @Override
    public TreeItem<String>[] TreeCreate(File dir){
        TreeItem<String>[] A;
        File[] files = dir.listFiles();
        int n= files.length-FilesHiddensCount(dir);
        A = new TreeItem[n];

        int pos = 0;
        for(File file: files){

            if(!file.isFile()&& !file.isHidden() && file.isDirectory()) {
                if(n == 0){
                    A[pos] =new TreeItem<>(file.getName());
                    ++pos;
                } else {
                    A[pos] = new TreeItem<>(file.getName());
                    try{
                        A[pos].getChildren().addAll(TreeCreate(file));
                        pos++;
                    }catch(Exception x){
                        System.out.println("Exception x in treecreate at "+file.getAbsolutePath()+" "+x.getMessage());
                    }
                }
            }
//            break;

        }
        return A;
    }

    @Override
    public String FindAbsolutePath(TreeItem<String> item, String s){
        if((item.getParent()==null) || (item.getParent().getValue().equals("This PC"))){
            return s;
        }
        else{
            String dir = item.getParent().getValue();
            dir = dir+"\\"+s;
            return FindAbsolutePath(item.getParent(), dir);
        }
    }

    @Override
    public void CreateTreeView(TreeView<String> treeview){
        File[] sysroots = File.listRoots();
        TreeItem<String> ThisPc = new TreeItem<>("This PC");
        TreeItem<String>[] drives = new TreeItem[sysroots.length];
        for(int i=0; i<sysroots.length;++i){
            drives[i] = new TreeItem<>(sysroots[i].getAbsolutePath());
            try{
                drives[i].getChildren().addAll(TreeCreate(sysroots[i]));
            }catch(NullPointerException x){
                System.out.println("Exception x detected: "+x.fillInStackTrace()+drives[i].toString());
            }
            finally {
                ThisPc.getChildren().add(drives[i]);
            }
        }
        treeview.setRoot(ThisPc);
    }

    @Override
    public void CreateTableView(TableView<FileInfo> tableview, TableColumn<FileInfo, String> date, TableColumn<FileInfo, String> name, TableColumn<FileInfo, String> size, TableColumn<FileInfo, String> type) {

    }

    @Override
    public void CreateTableView() {

    }

    @Override
    public void CreateTiles() { }

    @Override
    public void Initiate() {
    }

    @Override
    public void setValues(TableView<FileInfo> tableview, TableColumn<FileInfo, String> date, TableColumn<FileInfo, String> name, TableColumn<FileInfo, String> size, TableColumn<FileInfo, String> type) {

    }

    @Override
    public void CreateTilesView() {}

}
