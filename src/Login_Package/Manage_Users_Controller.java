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
import java.util.ResourceBundle;


public class Manage_Users_Controller implements Initializable {
    @FXML
    public TableView<User> usertableView ;
    @FXML
    public TableColumn<User,Integer> Idcolumn ;
    @FXML
    public TableColumn<User,String> usernamecolumn ;
    public TableColumn<User,String> passwordcolumn ;
    public TableColumn<User,String> rolecolumn ;
    public TableColumn<User,String> datecolumn ;

    @FXML
    private ComboBox camboBox;
    @FXML
    private TextField  usernametxt, passtxt;
    @FXML
    private DatePicker date_txt;

    public ObservableList<User> data;

    Db_Connection conn = new Db_Connection();

    PreparedStatement  preparesStatemnt = null;
    ResultSet resultSet = null;

    Utility utility = new Utility();


    public void loadData() throws SQLException{

        try{

            Connection cnn = conn.connect();
            data = FXCollections.observableArrayList();

            ResultSet rs = cnn.createStatement().executeQuery("SELECT * FROM demo.users");

            while(rs.next()){

                    data.add(new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
               }
        }
        catch(SQLException eX){
            System.out.println("error ! Not Connected to Db****");
        }

        Idcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernamecolumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordcolumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        rolecolumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        datecolumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        usertableView.setItems(null);
        usertableView.setItems(data);


    }













    @FXML
    public void add_New_User() throws SQLException {

       int max_id = 0;
        max_id  = utility.getMax_ID("users","id") +1;

       String userName = usernametxt.getText();
       String passWord = passtxt.getText();
       String role = camboBox.getValue().toString();
       String date = date_txt.getValue().toString();

        String query = "INSERT INTO demo.users (id,username,password,role,date) VALUES (?,?,?,?,?)";

        preparesStatemnt = conn.connect().prepareStatement(query);
        preparesStatemnt.setInt(1, max_id);
        preparesStatemnt.setString(2, userName);
        preparesStatemnt.setString(3, passWord);
        preparesStatemnt.setString(4, role);
        preparesStatemnt.setString(5, date);
        preparesStatemnt.execute();
        preparesStatemnt.close();

        usernametxt.clear();
        passtxt.clear();

        utility.showAlert("New User added successfully");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
