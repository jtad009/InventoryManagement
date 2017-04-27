package inventorysystem.Events;

import inventorysystem.data.Employee;
import java.util.EventObject;

/**
 *
 * @author Epic
 */
public class LoginEvent extends EventObject {
    Employee _thisEmployee;
    public LoginEvent(Object source,Employee employee) {
        super(source);
        _thisEmployee = employee;
    }
   public void setEmployee(Employee employee){_thisEmployee = employee; }
    public Employee getEmployee(){return  _thisEmployee; }
}
