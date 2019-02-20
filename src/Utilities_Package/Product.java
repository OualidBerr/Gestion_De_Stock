package Utilities_Package;

import javafx.beans.property.*;

import java.math.BigDecimal;

public class Product {

    public Product(){

    }

    private IntegerProperty  id ;
    private DoubleProperty  quantite ;
    private IntegerProperty  Nbr_pcs ;
    private IntegerProperty  alert ;
    private IntegerProperty  Nbr_pcs_crt ;
    private DoubleProperty   prix_achat  ;
    private DoubleProperty   prix_ventt  ;
    private StringProperty   fournisseur  ;
    private StringProperty   code_bare ;
    private StringProperty   date_entre ;
    private StringProperty   designiation ;
    private StringProperty   ref;
    private StringProperty   expiration ;
    private DoubleProperty   value  ;

    public double getValue() {
        return value.get();
    }

    public DoubleProperty valueProperty() {
        return value;
    }

    public void setValue(double value) {
        this.value.set(value);
    }

    // Constructor
    public Product(    Integer ID,        String REF,        String DESIGNATION,  Integer  NBR_PCS_CRT,
                       Double  QUANTITY ,Integer NBR_PCS,    String CODE_BARE,    String   DATE_ENTRE
            ,Integer ALERT,   String EXPIRATION,  Double PRIX_ACHAT,   Double   PRIX_VENT
            ,String FOURNISSEUR, double VALUE  )
    {

        double V = NBR_PCS*PRIX_ACHAT;

        this.id             = new SimpleIntegerProperty(ID);
        this.ref            = new SimpleStringProperty(REF);
        this.designiation   = new SimpleStringProperty(DESIGNATION);
        this.Nbr_pcs_crt    = new SimpleIntegerProperty(NBR_PCS_CRT);
        this.quantite       = new SimpleDoubleProperty(QUANTITY);
        this.Nbr_pcs        = new SimpleIntegerProperty(NBR_PCS);
        this.code_bare      = new SimpleStringProperty(CODE_BARE);
        this.date_entre     = new SimpleStringProperty(DATE_ENTRE);
        this.alert          = new SimpleIntegerProperty(ALERT);
        this.expiration     = new SimpleStringProperty(EXPIRATION);
        this.prix_achat     = new SimpleDoubleProperty(PRIX_ACHAT);
        this.prix_ventt     = new SimpleDoubleProperty(PRIX_VENT);
        this.fournisseur    = new SimpleStringProperty(FOURNISSEUR);
        this.value          = new SimpleDoubleProperty(V);
    }



    // Constructor Stock.
    public Product(    Integer ID,        String REF,        String DESIGNATION,  Integer  NBR_PCS_CRT,
                       Double QUANTITY ,Integer NBR_PCS, Double   PRIX_VENT , double VALUE )
    {

        double V = NBR_PCS*PRIX_VENT;

        this.id             = new SimpleIntegerProperty(ID);
        this.ref            = new SimpleStringProperty(REF);
        this.designiation   = new SimpleStringProperty(DESIGNATION);
        this.Nbr_pcs_crt    = new SimpleIntegerProperty(NBR_PCS_CRT);
        this.quantite       = new SimpleDoubleProperty(QUANTITY);
        this.Nbr_pcs        = new SimpleIntegerProperty(NBR_PCS);
        this.prix_ventt     = new SimpleDoubleProperty(PRIX_VENT);
        this.value          = new SimpleDoubleProperty(V);

    }

    // Constructor Bon Client
    public Product(double Prix, int NBR_PCS_CRT, double QUAN, int NBR_PCS, String DES){

        this.designiation   = new SimpleStringProperty(DES);
        this.Nbr_pcs_crt    = new SimpleIntegerProperty(NBR_PCS_CRT);
        this.quantite       = new SimpleDoubleProperty(QUAN);
        this.Nbr_pcs        = new SimpleIntegerProperty(NBR_PCS);
        this.prix_ventt     = new SimpleDoubleProperty(Prix);


    }




    public int getId() {
        return id.get();
    }
    public IntegerProperty idProperty() {
        return id;
    }
    public double getQuantite() {
        return quantite.get();
    }
    public DoubleProperty quantiteProperty() {
        return quantite;
    }
    public int getNbr_pcs() {
        return Nbr_pcs.get();
    }
    public IntegerProperty nbr_pcsProperty() {
        return Nbr_pcs;
    }
    public int getAlert() {
        return alert.get();
    }
    public IntegerProperty alertProperty() {
        return alert;
    }
    public int getNbr_pcs_crt() {
        return Nbr_pcs_crt.get();
    }
    public IntegerProperty nbr_pcs_crtProperty() {
        return Nbr_pcs_crt;
    }
    public double getPrix_achat() {
        return prix_achat.get();
    }
    public DoubleProperty prix_achatProperty() {
        return prix_achat;
    }
    public double getPrix_ventt() {
        return prix_ventt.get();
    }
    public DoubleProperty prix_venttProperty() {
        return prix_ventt;
    }
    public String getFournisseur() {
        return fournisseur.get();
    }
    public StringProperty fournisseurProperty() {
        return fournisseur;
    }
    public String getCode_bare() {
        return code_bare.get();
    }
    public StringProperty code_bareProperty() {
        return code_bare;
    }
    public String getDate_entre() {
        return date_entre.get();
    }
    public StringProperty date_entreProperty() {
        return date_entre;
    }
    public String getDesigniation() {
        return designiation.get();
    }
    public StringProperty designiationProperty() {
        return designiation;
    }
    public String getRef() {
        return ref.get();
    }
    public StringProperty refProperty() {
        return ref;
    }
    public String getExpiration() {
        return expiration.get();
    }
    public StringProperty expirationProperty() {
        return expiration;
    }
    public void setId(int id) {
        this.id.set(id);
    }
    public void setQuantite(double quantite) {
        this.quantite.set(quantite);
    }
    public void setNbr_pcs(int nbr_pcs) {
        this.Nbr_pcs.set(nbr_pcs);
    }
    public void setAlert(int alert) {
        this.alert.set(alert);
    }
    public void setNbr_pcs_crt(int nbr_pcs_crt) {
        this.Nbr_pcs_crt.set(nbr_pcs_crt);
    }
    public void setPrix_achat(double prix_achat) {
        this.prix_achat.set(prix_achat);
    }
    public void setPrix_ventt(double prix_ventt) {
        this.prix_ventt.set(prix_ventt);
    }
    public void setFournisseur(String fournisseur) {
        this.fournisseur.set(fournisseur);
    }
    public void setCode_bare(String code_bare) {
        this.code_bare.set(code_bare);
    }
    public void setDate_entre(String date_entre) {
        this.date_entre.set(date_entre);
    }
    public void setDesigniation(String designiation) {
        this.designiation.set(designiation);
    }
    public void setRef(String ref) {
        this.ref.set(ref);
    }
    public void setExpiration(String expiration) {
        this.expiration.set(expiration);
    }

}