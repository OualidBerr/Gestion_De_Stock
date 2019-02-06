package Client_Package;

import Utilities_Package.Client;
import Utilities_Package.Db_Connection;
import Utilities_Package.Fournisseur;
import Utilities_Package.Utility;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class New_Client_Controller implements Initializable {

      public static int ID;
      public static String NAME;
      public static String ADDRESS;
      public static String TELEPHONE;
      public static int PERIOD;
      public static String REGISTRE;
      public static double MAX_SOLD;

    @FXML
    public Button add_btn;
    @FXML
    public Button delete_btn,closeButton;
    @FXML
    public Button edit_btn;

    @FXML
    public  TextField nametxt;
    @FXML
    public TextField id_Client_txt;
    @FXML
    public TextField addresstxt;
    @FXML
    public TextField telephonetxt;
    @FXML
    public TextField periodtxt;
    @FXML
    public TextField sold_maxtxt;
    @FXML
    public TextField registretxt;

    public ObservableList<Fournisseur> data;
    Db_Connection conn = new Db_Connection();
    ResultSet resultSet = null;
    Utility utility = new Utility();

    public static boolean add_button_Visibility;
    public static boolean edit_button_Visibility;

    // Add Client
    @FXML
    public void add_Client() throws SQLException {

        int max_id = 0;
        max_id  = utility.getMax_ID("client_table","id") ;

        if (!nametxt.getText().isEmpty() &&
                !addresstxt.getText().isEmpty() &&
                !telephonetxt.getText().isEmpty()) {

            String Name      = nametxt.getText();
            String Address   = addresstxt.getText();
            String Telephone = telephonetxt.getText();
            String Period_String    = periodtxt.getText(); int Period = Integer.parseInt(Period_String);
            String Sold_max_String = sold_maxtxt.getText();  double Sold_max = Double.parseDouble(Sold_max_String);
            String Registre    = registretxt.getText();
            PreparedStatement preparesStatemnt = null;
            String query = "INSERT INTO demo.client_table (id,name,address,phone,period,soldmax,registre) VALUES (?,?,?,?,?,?,?)";

            preparesStatemnt = conn.connect().prepareStatement(query);
            preparesStatemnt.setInt(   1, max_id+1);
            preparesStatemnt.setString(2, Name);
            preparesStatemnt.setString(3, Address);
            preparesStatemnt.setString(4, Telephone);
            preparesStatemnt.setInt(   5, Period);
            preparesStatemnt.setDouble(   6, Sold_max);
            preparesStatemnt.setString(7, Registre);

            preparesStatemnt.execute();
            preparesStatemnt.close();
              clear();


            utility.showAlert("New User added successfully");


        }







    }
    // Edit Client
    @FXML
    public void edit_Client(){

        if (    !nametxt.getText().isEmpty() &&
                !addresstxt.getText().isEmpty() &&
                !telephonetxt.getText().isEmpty()
                           ) {
            int id             = Integer.parseInt(id_Client_txt.getText());
            String name        = nametxt.getText();
            String telephone   = telephonetxt.getText();
            String adress      = addresstxt.getText();
            int    period      = Integer.parseInt(periodtxt.getText()) ;
            double sold_max    = Double.parseDouble(sold_maxtxt.getText());
            String registre    = registretxt.getText();

            Client client = new Client(0,"","","",0,.25,"",0.25);
                 client.setId(id);
                 client.setName(name);
                 client.setAddress(adress);
                 client.setTelephone(telephone);
                 client.setMax_sold(sold_max);
                 client.setRegistre(registre);

            try{
                PreparedStatement  preparesStatemnt = null;

                String query  = "UPDATE demo.client_table SET name =?, address =?, phone =?,period=?,soldmax=?,registre=? Where id="+id;
                preparesStatemnt = conn.connect().prepareStatement(query);
                preparesStatemnt.setString(1, client.getName());
                preparesStatemnt.setString(2, client.getAddress());
                preparesStatemnt.setString(3, client.getTelephone());
                preparesStatemnt.setInt   (4, client.getPeriod());
                preparesStatemnt.setDouble(5, client.getMax_sold());
                preparesStatemnt.setString(6, client.getRegistre());

                preparesStatemnt.executeUpdate();

                clear();
                closeButtonAction();
                conn.connect().close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        else{

            utility.showAlert("Fields are not filled");
        }

        clear();
        utility.showAlert("Client has been Updated");


    }
    public  void clear(){

     nametxt.clear();
     addresstxt.clear();
     telephonetxt.clear();
     periodtxt.clear();
     sold_maxtxt.clear();
     registretxt.clear();

 }
    @FXML
    private void closeButtonAction(){
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        clear();
        stage.close();
    }
    @FXML
    public void handlekeyPressed(KeyEvent event) throws Exception {


        switch (event.getCode()) {
            case ESCAPE:
                closeButtonAction(); break;
            case ENTER:
                if (add_button_Visibility){

                    add_Client();
                }
                else{
                    edit_Client();
                }
                break;



        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        add_btn.setVisible(add_button_Visibility);
        edit_btn.setVisible(edit_button_Visibility);

        id_Client_txt.setText(ID + "");
        nametxt.setText(NAME);
        addresstxt.setText(ADDRESS);
        telephonetxt.setText(TELEPHONE);
        periodtxt.setText(PERIOD+"");
        registretxt.setText(REGISTRE);
        sold_maxtxt.setText(MAX_SOLD+"");

    }
}
