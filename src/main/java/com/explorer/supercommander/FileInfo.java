package com.explorer.supercommander;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.ImageView;

public class FileInfo {
    //private SimpleIntegerProperty id;
//    private ImageView image;
    private SimpleStringProperty name;
    private SimpleStringProperty size;
    private SimpleStringProperty date;
    private SimpleStringProperty type;


    /**
     *
     * @param name
     * @param size
     * @param type
     * @param date
     */
    public FileInfo(/*ImageView image, */String name, String size, String type, String date){
        super();
//        this.image = image;
        this.name = new SimpleStringProperty(name);
        this.size = new SimpleStringProperty(size);
        this.type = new SimpleStringProperty(type);
        this.date = new SimpleStringProperty(date);
    }

    public String getDate(){return date.get();}
    public String getName(){return name.get();}
    public String getSize(){return size.get();}
    public String getType(){return type.get();}
//    public void setImage(ImageView value) {image = value;}
//    public ImageView getImage() {return image;}
}
