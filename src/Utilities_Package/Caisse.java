package Utilities_Package;

import javafx.beans.property.*;

public class Caisse {

    private IntegerProperty id ;
    private StringProperty personName ;
    private StringProperty operationType ;
    private StringProperty date ;
    private DoubleProperty amount ;
    private DoubleProperty oldsold;
    private DoubleProperty sold;
    private StringProperty note ;
    private IntegerProperty personID;

    // Constructor
    public Caisse(int ID,String PERSONNAME,String OPERATIONTYPE,String DATE,
                  double AMOUNT,double OLDSOLD,double SOLD,String NOTE,int PERSONID)
    {
        this.id            = new SimpleIntegerProperty(ID);
        this.personName    = new SimpleStringProperty(PERSONNAME);
        this.operationType = new SimpleStringProperty(OPERATIONTYPE);
        this.date          = new SimpleStringProperty(DATE)    ;
        this.amount        = new SimpleDoubleProperty(AMOUNT);
        this.oldsold       = new SimpleDoubleProperty(OLDSOLD);
        this.sold          = new SimpleDoubleProperty(SOLD)   ;
        this.note          = new SimpleStringProperty(NOTE)    ;
        this.personID      = new SimpleIntegerProperty(PERSONID)  ;

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

    public String getPersonName() {
        return personName.get();
    }

    public StringProperty personNameProperty() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName.set(personName);
    }

    public String getOperationType() {
        return operationType.get();
    }

    public StringProperty operationTypeProperty() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType.set(operationType);
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

    public double getAmount() {
        return amount.get();
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public double getOldsold() {
        return oldsold.get();
    }

    public DoubleProperty oldsoldProperty() {
        return oldsold;
    }

    public void setOldsold(double oldsold) {
        this.oldsold.set(oldsold);
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
