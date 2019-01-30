package Utilities_Package;



import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Db_Connection {

   private final static String USERNAME  = "root";
   private final static String PASSWORD  = "gamadev";
  private final static String DB_Chema  = "demo";
  private final static String   URL    = "jdbc:mysql://localhost:3306/"+DB_Chema;

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
