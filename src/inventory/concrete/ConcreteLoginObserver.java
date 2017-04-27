package inventory.concrete;

import inventorysystem.Events.LoginEvent;
import inventorysystem.Interface.InventoryObserver;
import inventorysystem.Interface.LoginObserver;
import inventorysystem.data.AdminstrativeEmployee;
import inventorysystem.data.DBConnectionClass;
import inventorysystem.data.Employee;
import inventorysystem.data.SalesEmployee;
import java.sql.ResultSet;
import java.sql.Statement;
import libs.Property;
import java.util.EventObject;

/**
 *ConcreteLoginObserver Class to login user into system
 * @author Epic
 */
public class ConcreteLoginObserver implements  LoginObserver{
    private final DBConnectionClass connectionClass;
    private final ConcreteInventoryObservable observable;
    
    public ConcreteLoginObserver(ConcreteInventoryObservable observable){
        connectionClass = new DBConnectionClass();
        this.observable = observable;
        this.observable.add((InventoryObserver) this);
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
                        rs.getString("address"),
                        rs.getString("employee_type")
                    );
                }else if(rs.getString("employee_type").toLowerCase().equals("sales")){
                    employee =  new SalesEmployee(rs.getString("first_name").concat(" ").concat(rs.getString("last_name")),
                        rs.getString("username"), 
                        rs.getInt("employee_id"), 
                        rs.getString("gender"), 
                        rs.getString("birth_date"), 
                        rs.getString("address"),
                        rs.getString("employee_type")    
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employee;
    }
    @Override
    public void update(EventObject event) {
        if(event instanceof LoginEvent){
            LoginEvent loginEvent = (LoginEvent) event;
            Property configFile = new Property();
            configFile.writeProperty("EMPLOYEE_ID", String.valueOf(loginEvent.getEmployee().getEmployeeId()));
            configFile.writeProperty("EMPLOYEE_NAME", loginEvent.getEmployee().getFullname());
            configFile.writeProperty("EMPLOYEE_USERNAME", loginEvent.getEmployee().getUsername());
            configFile.writeProperty("EMPLOYEE_TYPE", loginEvent.getEmployee().getEmployeeType());
            /**after writing login details to the config.prop 
             *   Fire an event to a mainInventory(A listener that receives default event  object
             *  with just source the propagated event
             *   telling it to change screen
             * 
            */
            observable.setEvent(new EventObject(this.getClass()));
            observable.notifyObservers();
        }   
    }
}
