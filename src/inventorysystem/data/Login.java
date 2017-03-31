 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysystem.data;

import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JPanel;
import inventorysystem.Interface.Switchable;

/**
 *Login Class to login user into system
 * @author Epic
 */
public class Login {
    private DBConnectionClass connectionClass;
    public Login(){
        connectionClass = new DBConnectionClass();
    }
    /**
     * if user credential is a valid one then return an
     * employee object for that user.
     * @param username
     * @param password
     * 
     * @return Employee object
     */
    public  Employee isUser(String username, String password){
        
        Employee employee = null;
        try {
            Statement s = this.connectionClass.getConnection().createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM employees WHERE username = '"+username+"' AND password = '"+password+"'");
            if(rs.first()){
                if(rs.getString("employee_type").toLowerCase().equals("admin")){
                    employee = new AdminstrativeEmployee(rs.getString("first_name").concat(" ").concat(rs.getString("last_name")),
                        rs.getString("username"), 
                        rs.getInt("employee_id"), 
                        rs.getString("gender"), 
                        rs.getString("birth_date"), 
                        rs.getString("address"));
                }else if(rs.getString("employee_type").toLowerCase().equals("sales")){
                    employee =  new SalesEmployee(rs.getString("first_name").concat(" ").concat(rs.getString("last_name")),
                        rs.getString("username"), 
                        rs.getInt("employee_id"), 
                        rs.getString("gender"), 
                        rs.getString("birth_date"), 
                        rs.getString("address"));
                }
                
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employee;
    }
    
}
