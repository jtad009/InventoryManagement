
package inventorysystem.data;

 
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Epic
 */
public class DBConnectionClass {
    private Connection db_conection;
    private Statement stmt;
    public DBConnectionClass() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            db_conection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory_system", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
         
        
    }
    public Connection getConnection(){
         return db_conection;
     }
  
}
