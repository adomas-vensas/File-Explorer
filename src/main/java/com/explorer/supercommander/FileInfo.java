package com.explorer.supercommander;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.ImageView;

public class FileInfo {
    private SimpleStringProperty name;
    private SimpleStringProperty size;
    private SimpleStringProperty date;
    private SimpleStringProperty type;

    public FileInfo(String name, String size, String type, String date){
        super();
        this.name = new SimpleStringProperty(name);
        this.size = new SimpleStringProperty(size);
        this.type = new SimpleStringProperty(type);
        this.date = new SimpleStringProperty(date);
    }

    public String getDate(){return date.get();}
    public String getName(){return name.get();}
    public String getSize(){return size.get();}
    public String getType(){return type.get();}
}
