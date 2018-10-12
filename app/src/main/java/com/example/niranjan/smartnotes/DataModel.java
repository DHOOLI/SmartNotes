package com.example.niranjan.smartnotes;

import android.widget.Toast;

/**
 * Created by niranjan on 10/4/18.
 */

public class DataModel {

 public    String notes_title;
   public String subject_name;
    public  String id_;
  public  String price;
  public   String link;
public DataModel(){}
    public DataModel(String notes_title, String subject_name, String id_, String price,String link) {
        this.notes_title= notes_title;
        this.subject_name = subject_name;
        this.id_ = id_;
        this.price=price;
        this.link=link;

    }

    public String getNotes_title() {
        return notes_title;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public String getPrice() {
        return price;
    }

    public String getId_() {
        return id_;
    }
    public String getLink(){
        return link;
    }
public DataModel object(){
        return this;
}


}