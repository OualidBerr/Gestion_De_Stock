package Login_Package;

import Utilities_Package.Db_Connection;
import Utilities_Package.User;
import Utilities_Package.Utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Manage_Users_Controller implements Initializable {
    @FXML
    private Label showlb;

    @FXML
    public TableView<User> usertableView ;
    @FXML
    public TableColumn<User,Integer> Idcolumn ;
    @FXML
    public TableColumn<User,String> namecolumn ;
    public TableColumn<User,String> usernamecolumn ;
    public TableColumn<User,String> passwordcolumn ;
    public TableColumn<User,String> rolecolumn ;
    public TableColumn<User,String> datecolumn ;

    @FXML
    private ComboBox camboBox;
    @FXML
    private TextField  usernametxt, passtxt, nametxt;
    @FXML
    private DatePicker date_txt;

    public ObservableList<User> data;

    Db_Connection conn = new Db_Connection();

    PreparedStatement  preparesStatemnt = null;
    ResultSet resultSet = null;

    Utility utility = new Utility();

    // Add
    @FXML
    public void add_New_User() throws SQLException {

        int max_id = 0;
        max_id  = utility.getMax_ID("users","id") ;

        if (!usernametxt.getText().isEmpty() && !passtxt.getText().isEmpty() && !nametxt.getText().isEmpty()){


            String userName = usernametxt.getText();
            String Name = nametxt.getText();
            String passWord = passtxt.getText();
            String role = camboBox.getValue().toString();
            String date = date_txt.getValue().toString();

            String query = "INSERT INTO demo.users (id,name,username,password,role,date) VALUES (?,?,?,?,?,?)";

            preparesStatemnt = conn.connect().prepareStatement(query);
            preparesStatemnt.setInt(1, max_id+1);
            preparesStatemnt.setString(2, Name);
            preparesStatemnt.setString(3, userName);
            preparesStatemnt.setString(4, passWord);
            preparesStatemnt.setString(5, role);
            preparesStatemnt.setString(6, date);
            preparesStatemnt.execute();
            preparesStatemnt.close();

            usernametxt.clear();
            passtxt.clear();

            nametxt.clear();
            loadData();
            utility.showAlert("New User added successfully");
        }

        else{
            utility.showAlert("Data Can not be Dulicated");
        }
    }
    // Update
    @FXML
    public  int update_User() throws SQLException {

        if(! usertableView.getSelectionModel().isEmpty()) {
            User user = usertableView.getSelectionModel().getSelectedItem();
            int  id = user.getid();
            user.setNname(nametxt.getText());
            user.setPassword(passtxt.getText());
            user.setUserName(usernametxt.getText());
            user.setRole(camboBox.getValue().toString());
            //user.getDate( (Date) date_txt.getValue() );

            try{
                String query = "UPDATE users SET name =?, username =?, password =?, role =? Where id="+id;
                Connection cnn = conn.connect();
                preparesStatemnt = cnn.prepareStatement(query);
                preparesStatemnt.setString(1,user.getNname());
                preparesStatemnt.setString(2,user.getUsername());
                preparesStatemnt.setString(3,user.getPassword());
                preparesStatemnt.setString(4,user.getRole());
                preparesStatemnt.executeUpdate();
                loadData();
                utility.showAlert("User has been Updated");
                cnn.close();

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }


        return 0;
    }

    // Delete
    @FXML
    private void deletUser() throws SQLException{

        if(! usertableView.getSelectionModel().isEmpty()    ) {

            try{
                String query = "DELETE FROM users WHERE id =?";
                User user = (User) usertableView.getSelectionModel().getSelectedItem();
                int i = user.getid();
                String s = String.valueOf(i);
                preparesStatemnt = conn.connect().prepareStatement(query);
                preparesStatemnt.setString(1, s);
                preparesStatemnt.executeUpdate();
                preparesStatemnt.close();
                loadData();
                utility.showAlert("User has been deleted");

            }

            catch(SQLException e){
                e.printStackTrace();
            }

        }



    }



    public void loadData() throws SQLException{

        try{

            Connection cnn = conn.connect();
            data = FXCollections.observableArrayList();

            ResultSet rs = cnn.createStatement().executeQuery("SELECT * FROM demo.users");

            while(rs.next()){

                    data.add(new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)));
               }
        }
        catch(SQLException eX){
            System.out.println("error ! Not Connected to Db****");
        }

        Idcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        namecolumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        usernamecolumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordcolumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        rolecolumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        datecolumn.setCellValueFactory(new PropertyValueFactory<>("date"));


        usertableView.setItems(null);
        usertableView.setItems(data);


    }



    @FXML
    public void showOnClick()throws SQLException{

        try{
            User user = usertableView.getSelectionModel().getSelectedItem();

            String qyery = ("SELECT * FROM demo.users");
            preparesStatemnt = conn.connect().prepareStatement(qyery);
            int i = user.getid();
            String s = String.valueOf(i);

            usernametxt.setText(user.getUsername());
            nametxt.setText(user.getNname());
            passtxt.setText(user.getPassword());
            camboBox.setValue(user.getRole());

            preparesStatemnt.close();

        }

        catch(SQLException ex){

        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

       date_txt.setValue(LocalDate.now());

        /////// Value changed listener in the Table
        usertableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        try {
                            showOnClick();
                        } catch (SQLException ex) {
                            Logger.getLogger(Manage_Users_Controller.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                }
        );


        try {
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        camboBox.getItems().addAll("User","Admin");
        camboBox.setValue("User");
        camboBox.getStyleClass().add("center-aligned");
    }

}
