package Utilities_Package;

import javafx.beans.property.*;

public class Client {

    private IntegerProperty id ;
    private StringProperty name ;
    private  StringProperty address;
    private  StringProperty telephone ;
    private IntegerProperty period ;
    private DoubleProperty max_sold  ;
    private StringProperty registre ;
    private DoubleProperty sold  ;

    // Constructor
    public Client (  Integer id,       String Name,     String Address,
                     String Telephone, Integer PERIOD,  Double SOLD_MAX,
                     String REGISTRE,  Double SOLD  ){

        this.id        = new SimpleIntegerProperty(id);
        this.name      = new SimpleStringProperty(Name);
        this.address   = new SimpleStringProperty(Address);
        this.telephone = new SimpleStringProperty(Telephone);
        this.sold      = new SimpleDoubleProperty(SOLD);
        this.max_sold  = new SimpleDoubleProperty(SOLD_MAX);
        this.period    = new SimpleIntegerProperty(PERIOD);
        this.registre  = new SimpleStringProperty(REGISTRE);

    }

    public String getRegistre() {
        return registre.get();
    }
    public StringProperty registreProperty() {
        return registre;
    }
    public void setRegistre(String registre) {
        this.registre.set(registre);
    }
    public int getId() {
        return id.get();
    }
    public IntegerProperty idProperty() {
        return id;
    }
    public String getName() {
        return name.get();
    }
    public StringProperty nameProperty() {
        return name;
    }
    public String getAddress() {
        return address.get();
    }
    public StringProperty addressProperty() {
        return address;
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
    public double getMax_sold() {
        return max_sold.get();
    }
    public DoubleProperty max_soldProperty() {
        return max_sold;
    }
    public int getPeriod() {
        return period.get();
    }
    public IntegerProperty periodProperty() {
        return period;
    }
    public void setId(int id) {
        this.id.set(id);
    }
    public void setName(String name) {
        this.name.set(name);
    }
    public void setAddress(String address) {
        this.address.set(address);
    }
    public void setTelephone(String telephone) {
        this.telephone.set(telephone);
    }
    public void setSold(double sold) {
        this.sold.set(sold);
    }
    public void setMax_sold(double max_sold) {
        this.max_sold.set(max_sold);
    }
    public void setPeriod(int period) {
        this.period.set(period);
    }

}
