package Caisse_Package;

import Utilities_Package.Caisse;
import Utilities_Package.Db_Connection;
import Utilities_Package.Reglement;
import Utilities_Package.Utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

    public class Caisse_Controller implements Initializable {

        @FXML
        public TableView<Caisse> caisse_tableView;
        public TableColumn<Caisse,Integer>   Id_column    ;
        public TableColumn<Caisse, String>   name_column ;
        public TableColumn<Caisse, String>   operationType_column ;
        public TableColumn<Caisse, Date>   date_column ;
        public TableColumn<Caisse,Double>   amount_column ;
        public TableColumn<Caisse,Double>   old_soldcolumn;
        public TableColumn<Caisse,Double>   sold_column ;
        public TableColumn<Caisse,String>   note_column ;
        public ObservableList<Caisse> data;

        Db_Connection conn = new Db_Connection();
        PreparedStatement preparesStatemnt = null;
        ResultSet rs = null;
        Utility utility = new Utility();



        public void load(){
            int personType_int=1;
            try{

                data = FXCollections.observableArrayList();
                rs = conn.connect().createStatement().executeQuery("SELECT R.id,P.name,R.date,R.amount,R.old_sold,R.sold,R.note,P.PersonType,P.id FROM demo.person_reglement_table R, demo.person_table P where R.personID=P.id and R.id>0 ;");
                while(rs.next()){
                     int     id     =  rs.getInt(   1);
                    String  Name   =  rs.getString(2);
                     personType_int = rs.getInt(8);
                     String date =  rs.getString(3);
                    double amount = rs.getDouble(4);
                    double old_sold = rs.getDouble(5);
                    double sold = rs.getDouble(6);
                    String note =  rs.getString(7);
                    String OperationType=null;

                    if (personType_int==2)    {OperationType="Verssement";}
                    else if(personType_int==1){OperationType="Paiment";}

                    data.add(new Caisse(id,Name, OperationType,date,amount,old_sold,sold,note,rs.getInt(9)));
                }






            }
            catch(SQLException eX){
                eX.printStackTrace();
                System.out.println("error ! Not Connected to Db****");
            }
            finally{
                Id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
                name_column.setCellValueFactory(new PropertyValueFactory<>("personName"));
                operationType_column.setCellValueFactory(new PropertyValueFactory<>("operationType"));
                date_column.setCellValueFactory(new PropertyValueFactory<>("date"));
                amount_column.setCellValueFactory(new PropertyValueFactory<>("amount"));
                old_soldcolumn.setCellValueFactory(new PropertyValueFactory<>("oldsold"));
                sold_column.setCellValueFactory(new PropertyValueFactory<>("sold"));
                note_column.setCellValueFactory(new PropertyValueFactory<>("note"));

                caisse_tableView.setItems(data);



            }


        }













    @FXML
    public Button goHome_btn;

    @FXML
    public void goBack_To_Home_Window(Event event) throws IOException {

        new Utility().go_Home(event);
    }

    // Stock
    @FXML
    public void Open_Stock_Window(Event event) throws IOException {
        new Utility().go_Stock(event);
    }




    @FXML
    public void handlekeyPressed(KeyEvent event) throws Exception {

        switch (event.getCode()) {
            case F:
              break;

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        load();


        new Utility().Button_request_focus(goHome_btn);
    }
}
