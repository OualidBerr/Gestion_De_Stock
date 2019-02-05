package Utilities_Package;

import javafx.beans.property.*;



public class Reglement {

    private IntegerProperty id ;
    private StringProperty name ;
    private StringProperty date ;
    private  StringProperty mode;
    private  DoubleProperty amount ;
    private DoubleProperty old_sold;
    private DoubleProperty sold;
    private StringProperty note ;
    private IntegerProperty fournisseurID;

    // Constructor
    public Reglement(int ID, String NAME, String DATE,String MODE, double AMOUNT, double OLD_SOLD, double SOLD,String NOTE,Integer fournisseurID){

        this.id       = new SimpleIntegerProperty(ID);
        this.name     = new SimpleStringProperty(NAME);
        this.date     = new SimpleStringProperty(DATE);
        this.mode     = new SimpleStringProperty(MODE);
        this.note     = new SimpleStringProperty(NOTE);
        this.amount   = new SimpleDoubleProperty(AMOUNT);
        this.old_sold = new SimpleDoubleProperty(OLD_SOLD);
        this.sold     = new SimpleDoubleProperty(SOLD);
        this.fournisseurID       = new SimpleIntegerProperty(fournisseurID);

    }
    public Reglement(int ID, String DATE, String MODE, double AMOUNT, double OLD_SOLD, double SOLD,String NOTE){

        this.id       = new SimpleIntegerProperty(ID);
        this.date     = new SimpleStringProperty(DATE);
        this.mode     = new SimpleStringProperty(MODE);
        this.note     = new SimpleStringProperty(NOTE);
        this.amount   = new SimpleDoubleProperty(AMOUNT);
        this.old_sold = new SimpleDoubleProperty(OLD_SOLD);
        this.sold     = new SimpleDoubleProperty(SOLD);

    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setNote(String note) {
        this.note.set(note);
    }

    public String getNote() {
        return note.get();
    }

    public StringProperty noteProperty() {
        return note;
    }


    // Getters
    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public String getMode() {
        return mode.get();
    }

    public StringProperty modeProperty() {
        return mode;
    }

    public double getAmount() {
        return amount.get();
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public double getOld_sold() {
        return old_sold.get();
    }

    public DoubleProperty old_soldProperty() {
        return old_sold;
    }

    public double getSold() {
        return sold.get();
    }

    public DoubleProperty soldProperty() {
        return sold;
    }

    // Setters
    public void setId(int id) {
        this.id.set(id);
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public void setMode(String mode) {
        this.mode.set(mode);
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public void setOld_sold(double old_sold) {
        this.old_sold.set(old_sold);
    }

    public void setSold(double sold) {
        this.sold.set(sold);
    }

    public void setFournisseurID(int fournisseurID) {
        this.fournisseurID.set(fournisseurID);
    }

    public int getFournisseurID() {
        return fournisseurID.get();
    }

    public IntegerProperty fournisseurIDProperty() {
        return fournisseurID;
    }












}
