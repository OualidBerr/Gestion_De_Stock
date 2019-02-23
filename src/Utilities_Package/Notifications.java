package Utilities_Package;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Notifications {

    IntegerProperty id;
    StringProperty note;
    StringProperty date;
    StringProperty satuts;

    // Constructor
    public Notifications(int ID,String NOTE,String DATE,String STATUS)
       {
        this.id        = new SimpleIntegerProperty(ID);
        this.note      = new SimpleStringProperty(NOTE);
        this.date   = new SimpleStringProperty(DATE);
        this.satuts = new SimpleStringProperty(STATUS);
       }


    public Notifications(String NOTE)
    {
        this.note      = new SimpleStringProperty(NOTE);

    }




    public int getId() {
        return id.get();
    }
    public IntegerProperty idProperty() {
        return id;
    }
    public void setId(int id) {
        this.id.set(id);
    }
    public String getNote() {
        return note.get();
    }
    public StringProperty noteProperty() {
        return note;
    }
    public void setNote(String note) {
        this.note.set(note);
    }
    public String getDate() {
        return date.get();
    }
    public StringProperty dateProperty() {
        return date;
    }
    public void setDate(String date) {
        this.date.set(date);
    }
    public String getSatuts() {
        return satuts.get();
    }
    public StringProperty satutsProperty() {
        return satuts;
    }
    public void setSatuts(String satuts) {
        this.satuts.set(satuts);
    }

}
