package com.explorer.supercommander;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.TilePane;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

public abstract class FileExplorerFx implements FileExplorer{

    static File CurrDirFile;
    static String CurrDirStr;
    static Label lbl;
    static String CurrDirName;
    static TilePane tilePane;
    SimpleDateFormat sdf;

    TableView<com.explorer.supercommander.FileInfo> tableview;
    TableColumn<com.explorer.supercommander.FileInfo, String> date, name, size, type;

    FileExplorerFx(){}
    public void setLabelTxt(){lbl.setText(CurrDirStr);}
    public String calculateSize(File f){
        long sizeInByte=0;
        if(IsDrive(f)){
            return formatSize(f.getTotalSpace());
        }

        try {
            sizeInByte = Files.size(Paths.get(f.toURI()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return formatSize(sizeInByte);
    }
    public boolean IsDrive(File file){
        File[] roots = File.listRoots();
        for(File root: roots) {
            if(file.equals(root)) {
                return true;
            }
        }
        return false;
    }
    public int FilesHiddensCount(File dir){
        int count = 0;
        File[] files = dir.listFiles();

        for(File file: files){
            try{
                if(file.isHidden() || file.isFile()){
                    ++count;
                }
            } catch(Exception x){
                System.out.println("Exception at prototype1, file explorer CountDir: "+x.getMessage());
            }

        }
        return count;
    }
    /*******************************************/
    public int NumOfDirectChilds(File file){
        if(file.listFiles() == null){
            return -1;
        }
        return file.listFiles().length;
    }

    public static String formatSize(long v) {
        if (v < 1024){
            return v + " B";
        }
        int z = (63 - Long.numberOfLeadingZeros(v)) / 10;
        return String.format("%.1f %sB", (double)v / (1L << (z*10)), " KMGTPE".charAt(z));
    }

    public static String replaceSlashes(String pathName){
        if(!pathName.endsWith(".")){
            pathName += "\\";
        } else {
            pathName = pathName.substring(0, pathName.length() - 1);
        }

        return pathName;
    }

    public Image getIconImageFX(File f){
        return null;
    }
}
