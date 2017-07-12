package hotelpossystem.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author lingyanjiang
 */
public class DatabaseConnection {
    private final String url = "jdbc:mysql://mydatabase.cwhndt08kykb.us-west-2.rds.amazonaws.com:3306/hotel";
    private final String user = "lynne"; 
    private final String pwd = "abcd1234";
    private Connection conn;
    
    /**
     * Initiate connection to database
     */
    public DatabaseConnection() throws ClassNotFoundException {
        try{
            this.conn = DriverManager.getConnection(url, user, pwd);
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This method is to get connection
     * @return Connection
     */
    public Connection getConnection() {
        return this.conn;
    }
    
    /**
     * This method close connection
     */
    public void close() {
        try {
            this.conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
