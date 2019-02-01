package Utilities_Package;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {

    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty username;
    private final StringProperty password;
    private final StringProperty role;
    private final StringProperty date;



    public User(Integer id,String name,String username,String password,String role, String date){

        this.id     = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.role  = new SimpleStringProperty(role);
        this.date  = new SimpleStringProperty(date);

    }


   //Getters
    public int getid() {
       return id.get();
   }

    public String getUsername() {
        return username.get();
    }

    public String getNname() {
        return name.get();
    }

    public String getPassword() {
        return password.get();
    }

    public String getRole() {
        return role.get();
    }

    public String getDate() {
        return date.get();
    }



    //Setters
    public void setid(int value) {
        id.set(value);
    }
    public void setNname(String value) {
        name.set(value);
    }

    public void setUserName(String value) {
        username.set(value);
    }

    public void setPassword(String value) {
        password.set(value);
    }

    public void setRole(String value) {
        role.set(value);
    }

    public void setDate(String value) {
        date.set(value);
    }

    // properties
    public IntegerProperty idProperty() {
    return id;
    }
    public StringProperty usernameProperty() {
        return username;
    }
    public StringProperty nameProperty() {
        return name;
    }
    public StringProperty roleProperty() {
        return role;
    }
    public StringProperty passwordProperty() {
        return password;

    }
    public StringProperty dateProperty() {
        return date;

    }

}
