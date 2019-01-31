package Utilities_Package;



import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Db_Connection {

    public   static String USERNAME  = "root";
    public  static String PASSWORD  = "gamadev";
    public  static String DB_Chema  = "demo";
    public  static String Server  = "localhost";
    public  static String   URL    = "jdbc:mysql://"+Server+":3306/"+DB_Chema;

    public Connection connect() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            return conn;

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Db_Connection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }




}
