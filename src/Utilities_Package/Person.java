package Utilities_Package;

import javafx.beans.property.*;


public class Person {

    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty addresse;
    private final StringProperty telephone;
    private final DoubleProperty sold;
    // Fournisseur Constructor
    public Person(Integer id, String Name,String Address,String Telephone,Double Sold) {
        this.id      = new SimpleIntegerProperty(id);
        this.name    = new SimpleStringProperty(Name);
        this.addresse = new SimpleStringProperty(Address);
        this.telephone = new SimpleStringProperty(Telephone);
        this.sold      =   new SimpleDoubleProperty(Sold);


    }
    // Getters
    public int getid() {
        return id.get();
    }
    public IntegerProperty idProperty() {
        return id;
    }
    public String getname() {
        return name.get();
    }
    public StringProperty nameProperty() {
        return name;
    }
    public String getAddresse() {
        return addresse.get();
    }
    public StringProperty addresseProperty() {
        return addresse;
    }
    public String getTelephone() {
        return telephone.get();
    }
    public StringProperty telephoneProperty() {
        return telephone;
    }
    public double getSold() {
        return sold.get();
    }
    public DoubleProperty soldProperty() {
        return sold;
    }
    // setters
    public void setid(int id) {
        this.id.set(id);
    }
    public void setname(String name) {
        this.name.set(name);
    }
    public void setAddresse(String addresse) {
        this.addresse.set(addresse);
    }
    public void setTelephone(String telephone) {
        this.telephone.set(telephone);
    }
    public void setSold(double sold) {
        this.sold.set(sold);
    }

}
