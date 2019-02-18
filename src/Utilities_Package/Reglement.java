package Utilities_Package;

import javafx.beans.property.*;



public class Reglement {

    private IntegerProperty id ;
    private  DoubleProperty amount ;
    private DoubleProperty oldsold;
    private DoubleProperty sold;
    private  StringProperty mode;
    private StringProperty date ;
    private StringProperty note ;
    private IntegerProperty personID;

    public double getOldsold() {
        return oldsold.get();
    }

    public DoubleProperty oldsoldProperty() {
        return oldsold;
    }

    public void setOldsold(double oldsold) {
        this.oldsold.set(oldsold);
    }



    // Constractor
    public Reglement(int ID,double AMOUNT,double OLDSOLD,double SOLD,
                     String MODE,String DATE,String NOTE,int PERSONID){

               this.id        = new SimpleIntegerProperty(ID)          ;
               this.amount    = new SimpleDoubleProperty(AMOUNT)       ;
               this.sold      = new SimpleDoubleProperty(SOLD)         ;
               this.oldsold   = new SimpleDoubleProperty(OLDSOLD)      ;
               this.mode      = new SimpleStringProperty(MODE)         ;
               this.date      = new SimpleStringProperty(DATE)         ;
               this.note      = new SimpleStringProperty(NOTE)         ;
               this.personID  = new SimpleIntegerProperty(PERSONID)    ;

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

    public double getAmount() {
        return amount.get();
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }


    public double getSold() {
        return sold.get();
    }

    public DoubleProperty soldProperty() {
        return sold;
    }

    public void setSold(double sold) {
        this.sold.set(sold);
    }

    public String getMode() {
        return mode.get();
    }

    public StringProperty modeProperty() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode.set(mode);
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

    public String getNote() {
        return note.get();
    }

    public StringProperty noteProperty() {
        return note;
    }

    public void setNote(String note) {
        this.note.set(note);
    }

    public int getPersonID() {
        return personID.get();
    }

    public IntegerProperty personIDProperty() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID.set(personID);
    }










}
