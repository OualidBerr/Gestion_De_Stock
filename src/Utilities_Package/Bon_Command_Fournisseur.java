package Utilities_Package;

import javafx.beans.property.*;

public class Bon_Command_Fournisseur {

    private IntegerProperty id ;
    private StringProperty  ref ;
    private StringProperty  des ;
    private IntegerProperty nbr_pcs_crt ;
    private DoubleProperty quantite ;
    private IntegerProperty nbr_pcs ;
    private DoubleProperty  prix_vent ;
    private DoubleProperty  prix_achat ;
    private DoubleProperty  value;
    private IntegerProperty fournisseurID ;
    private StringProperty  date;
    private IntegerProperty bonID ;

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }



    public Bon_Command_Fournisseur(int ID,String REF,String DES,int NBR_PCS_CRT,double QUN,int NBR_PCS,
                   double prix_vent,double prix_achat,double value,String DATE)
       {
           this.id                  = new SimpleIntegerProperty(ID);
           this.ref                 = new SimpleStringProperty(REF);
           this.des                 = new SimpleStringProperty(DES);
           this.nbr_pcs_crt         = new SimpleIntegerProperty(NBR_PCS_CRT);
           this.quantite            = new SimpleDoubleProperty(QUN);
           this.nbr_pcs             = new SimpleIntegerProperty(NBR_PCS);
           this.prix_vent           = new SimpleDoubleProperty(prix_vent);
           this.prix_achat          = new SimpleDoubleProperty(prix_achat);
           this.value               = new SimpleDoubleProperty(value);
           this.date                = new SimpleStringProperty(DATE);

       }





    // Constructor
    public Bon_Command_Fournisseur(int ID,String REF,String DES,int NBR_PCS_CRT,
                                   double QUANTIT,int NBR_PCS,double PRIX_vent,double PRIX_Achat,double VALUE,
                                   int FOURNISSEURID,int BON_ID, String DATE)
    {
        this.id                  = new SimpleIntegerProperty(ID);
        this.ref                 = new SimpleStringProperty(REF);
        this.des                 = new SimpleStringProperty(DES);
        this.nbr_pcs_crt         = new SimpleIntegerProperty(NBR_PCS_CRT);
        this.quantite            = new SimpleDoubleProperty(QUANTIT);
        this.nbr_pcs             = new SimpleIntegerProperty(NBR_PCS);
        this.prix_vent           = new SimpleDoubleProperty(PRIX_vent);
        this.prix_achat          = new SimpleDoubleProperty(PRIX_Achat);
        this.value               = new SimpleDoubleProperty(VALUE);
        this.fournisseurID       = new SimpleIntegerProperty(FOURNISSEURID);
        this.bonID               = new SimpleIntegerProperty(BON_ID);
        this.date                = new SimpleStringProperty(DATE);

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
    public double getQuantite() {
        return quantite.get();
    }
    public DoubleProperty quantiteProperty() {
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
    public double getPrix_achat() {
        return prix_achat.get();
    }
    public DoubleProperty prix_achatProperty() {
        return prix_achat;
    }
    public void setPrix_achat(double prix_achat) {
        this.prix_achat.set(prix_achat);
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
    public int getFournisseurID() {
        return fournisseurID.get();
    }
    public IntegerProperty fournisseurIDProperty() {
        return fournisseurID;
    }
    public void setFournisseurID(int fournisseurID) {
        this.fournisseurID.set(fournisseurID);
    }

    }
