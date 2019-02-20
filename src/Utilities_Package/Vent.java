package Utilities_Package;

import javafx.beans.property.*;

public class Vent {

    private IntegerProperty     id;
    private StringProperty      ref      ;
    private StringProperty      des      ;
    private IntegerProperty     nbr_pcs  ;
    private DoubleProperty      prix_vent ;
    private DoubleProperty      value;
    private IntegerProperty     clientID ;
    private IntegerProperty     ventID ;
    private StringProperty      date_vent;

    // Constructor
    public Vent(  int ID,      String REF,   String DES,  int NBR_PCS, double PRIX,
                  double VALUE,int CLIENTID, int VENTID,  String DATE     )
    {

        this.id                  = new SimpleIntegerProperty(ID);
        this.ref                 = new SimpleStringProperty(REF);
        this.des                 = new SimpleStringProperty(DES);
        this.nbr_pcs             = new SimpleIntegerProperty(NBR_PCS);
        this.prix_vent           = new SimpleDoubleProperty(PRIX);
        this.value               = new SimpleDoubleProperty(VALUE);
        this.clientID            = new SimpleIntegerProperty(CLIENTID);
        this.ventID              = new SimpleIntegerProperty(VENTID);
        this.date_vent                = new SimpleStringProperty(DATE);

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

    public String getRef() {
        return ref.get();
    }

    public StringProperty refProperty() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref.set(ref);
    }

    public String getDes() {
        return des.get();
    }

    public StringProperty desProperty() {
        return des;
    }

    public void setDes(String des) {
        this.des.set(des);
    }

    public int getNbr_pcs() {
        return nbr_pcs.get();
    }

    public IntegerProperty nbr_pcsProperty() {
        return nbr_pcs;
    }

    public void setNbr_pcs(int nbr_pcs) {
        this.nbr_pcs.set(nbr_pcs);
    }

    public double getPrix_vent() {
        return prix_vent.get();
    }

    public DoubleProperty prix_ventProperty() {
        return prix_vent;
    }

    public void setPrix_vent(double prix_vent) {
        this.prix_vent.set(prix_vent);
    }

    public double getValue() {
        return value.get();
    }

    public DoubleProperty valueProperty() {
        return value;
    }

    public void setValue(double value) {
        this.value.set(value);
    }

    public int getClientID() {
        return clientID.get();
    }

    public IntegerProperty clientIDProperty() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID.set(clientID);
    }

    public int getVentID() {
        return ventID.get();
    }

    public IntegerProperty ventIDProperty() {
        return ventID;
    }

    public void setVentID(int ventID) {
        this.ventID.set(ventID);
    }

    public String getDate_vent() {
        return date_vent.get();
    }

    public StringProperty date_ventProperty() {
        return date_vent;
    }

    public void setDate_vent(String date_vent) {
        this.date_vent.set(date_vent);
    }




















}
