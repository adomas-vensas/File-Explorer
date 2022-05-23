package com.explorer.supercommander;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;


public class ClassTreeView extends FileExplorerFx {
    /**
     *
     */

    ClassTreeView(){};

    /**
     *
     * @param dir
     * @return
     */
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
                    A[pos] =new TreeItem<>(file.getName()/*, new ImageView(new Image(ClassLoader.getSystemResourceAsStream("img/folderOpen.png")))*/);
                    ++pos;
                } else {
                    A[pos] = new TreeItem<>(file.getName()/*, new ImageView(new Image(ClassLoader.getSystemResourceAsStream("img/folderOpen.png"))))*/);
                    try{
                        A[pos].getChildren().addAll(TreeCreate(file));
                        pos++;
                    }catch(Exception x){
                        System.out.println("Exception x in treecreate at "+file.getAbsolutePath()+" "+x.getMessage());
                    }
                }
            }
//            break;

//            if(!fl[i].isFile()&& !fl[i].isHidden() && fl[i].isDirectory() && n==0){
//                A[pos] =new TreeItem<>(fl[i].getName()/*, new ImageView(new Image(ClassLoader.getSystemResourceAsStream("img/folderOpen.png")))*/);
//                ++pos;
//            }
//            else if(!fl[i].isFile()&& !fl[i].isHidden() && fl[i].isDirectory() && n>0){
//                A[pos] = new TreeItem<>(fl[i].getName()/*, new ImageView(new Image(ClassLoader.getSystemResourceAsStream("img/folderOpen.png"))))*/);
//                try{
//                    A[pos].getChildren().addAll(TreeCreate(fl[i]));
//                    pos++;
//                }catch(Exception x){
//                    System.out.println("Exception x in treecreate at "+fl[i].getAbsolutePath()+" "+x.getMessage());
//                }
//            }

        }
        /**Modify here*/
        //FileComparator ov
        /***/
        return A;
    }

    /**
     *
     * @param item
     * @param s
     * @return
     */
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
        //return null;
    }

    @Override
    public void CreateTreeView(TreeView<String> treeview){
        File[] sysroots = File.listRoots();
        TreeItem<String> ThisPc = new TreeItem<>("This PC"/*, new ImageView(new Image(ClassLoader.getSystemResourceAsStream("img/pc.png")))*/);
        TreeItem<String>[] drives = new TreeItem[sysroots.length];
        for(int i=0; i<sysroots.length;++i){
            drives[i] = new TreeItem<>(sysroots[i].getAbsolutePath()/*, new ImageView(new Image(ClassLoader.getSystemResourceAsStream("img/thumb_Hard_Drive.png")))*/);
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
    /****************/

}
