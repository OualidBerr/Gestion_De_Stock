package Utilities_Package;

import javafx.beans.property.*;

public class Bon_Fournisseur_Global {

    private IntegerProperty id ;
    private StringProperty n_bon ;
    private StringProperty date ;
    private DoubleProperty valeur ;
    private  IntegerProperty bonID;


    public int getBonID() {
        return bonID.get();
    }

    public IntegerProperty bonIDProperty() {
        return bonID;
    }

    public void setBonID(int bonID) {
        this.bonID.set(bonID);
    }



    // Constractor
    public Bon_Fournisseur_Global(int ID,String N_BON,double VALEUR,String DATE,int BONID){

        this.id       = new SimpleIntegerProperty(ID);
        this.n_bon     = new SimpleStringProperty(N_BON);
        this.date     = new SimpleStringProperty(DATE);
        this.valeur     = new SimpleDoubleProperty(VALEUR);
        this.bonID       = new SimpleIntegerProperty(BONID);

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

    public String getN_bon() {
        return n_bon.get();
    }

    public StringProperty n_bonProperty() {
        return n_bon;
    }

    public void setN_bon(String n_bon) {
        this.n_bon.set(n_bon);
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

    public double getValeur() {
        return valeur.get();
    }

    public DoubleProperty valeurProperty() {
        return valeur;
    }

    public void setValeur(double valeur) {
        this.valeur.set(valeur);
    }






}
