package Utilities_Package;

import javafx.beans.property.*;

public class Bon_Command_Client {

    private IntegerProperty id ;
    private StringProperty  ref ;
    private StringProperty  des ;
    private IntegerProperty nbr_pcs_crt ;
    private IntegerProperty quantite ;
    private IntegerProperty nbr_pcs ;
    private DoubleProperty  prix_vent ;
    private DoubleProperty  value;
    private IntegerProperty clientID ;
    private IntegerProperty bonID ;
    private StringProperty  date ;


    // Constructor
    public Bon_Command_Client(int ID, String REF, String DES, int NBR_PCS_CRT,
                              int QUANTIT, int NBR_PCS, double PRIX_vent, double VALUE,
                              int CLIENTID, int BON_ID, String DATE)
    {
        this.id                  = new SimpleIntegerProperty(ID);
        this.ref                 = new SimpleStringProperty(REF);
        this.des                 = new SimpleStringProperty(DES);
        this.nbr_pcs_crt         = new SimpleIntegerProperty(NBR_PCS_CRT);
        this.quantite            = new SimpleIntegerProperty(QUANTIT);
        this.nbr_pcs             = new SimpleIntegerProperty(NBR_PCS);
        this.prix_vent           = new SimpleDoubleProperty(PRIX_vent);
        this.value               = new SimpleDoubleProperty(VALUE);
        this.clientID            = new SimpleIntegerProperty(CLIENTID);
        this.bonID               = new SimpleIntegerProperty(BON_ID);
        this.date                = new SimpleStringProperty(DATE);

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

    public int getNbr_pcs_crt() {
        return nbr_pcs_crt.get();
    }

    public IntegerProperty nbr_pcs_crtProperty() {
        return nbr_pcs_crt;
    }

    public void setNbr_pcs_crt(int nbr_pcs_crt) {
        this.nbr_pcs_crt.set(nbr_pcs_crt);
    }

    public int getQuantite() {
        return quantite.get();
    }

    public IntegerProperty quantiteProperty() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite.set(quantite);
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

    public int getBonID() {
        return bonID.get();
    }

    public IntegerProperty bonIDProperty() {
        return bonID;
    }

    public void setBonID(int bonID) {
        this.bonID.set(bonID);
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













    }
