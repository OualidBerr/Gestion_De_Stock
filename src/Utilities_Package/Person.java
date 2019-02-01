package Utilities_Package;

import javafx.beans.property.*;


public class Person {

       private  IntegerProperty id ;
       private  StringProperty name ;
       private  StringProperty address;
       private  StringProperty telephone ;
       private  DoubleProperty sold  ;

    public Person(Integer id, String Name,String Address,String Telephone) {
        this.id      = new SimpleIntegerProperty(id);
        this.name    = new SimpleStringProperty(Name);
        this.address = new SimpleStringProperty(Address);
        this.telephone = new SimpleStringProperty(Telephone);

    }




    public Person(Integer id, String Name,String Address,String Telephone, double sold) {
        this.id      = new SimpleIntegerProperty(id);
        this.name    = new SimpleStringProperty(Name);
        this.address = new SimpleStringProperty(Address);
        this.telephone = new SimpleStringProperty(Telephone);
        this.sold = new SimpleDoubleProperty(sold);
    }



    public Person(){}



    // setters
    public void setFournisseurId(int value) {
        id.set(value);
    }
    public void setFournisseurName(String value) {
        name.set(value);
    }

    public void setFournisseurAdress(String value) {
        address.set(value);
    }
    public void setFournisseurTelephone(String value) {
        telephone.set(value);
    }

    public void setFournisseurSold(double value) {
        sold.set(value);
    }


    //Getters
    public int getFournisseurId() {
        return id.get();
    }

    public String getFournisseurName() {
        return name.get();
    }

    public String getFournisseurAdress() {
        return address.get();
    }
    public String getFournisseurTelephone() {
        return telephone.get();
    }
    public double getFournisseurSold() {
        return sold.get();
    }

   // Properties

    public IntegerProperty idProperty() {
        return id;
    }
    public StringProperty nameProperty() {
        return name;
    }
    public StringProperty adressProperty() {
        return address;
    }
    public StringProperty telephoneProperty() {
        return telephone;
    }
    public StringProperty soldProperty() {
        return address;
    }





}
