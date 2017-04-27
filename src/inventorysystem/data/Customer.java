package inventorysystem.data;

import inventorysystem.Interface.InventoryQueryStrategy;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

/**
 * This Class Handle all the functions that can be applied to a customer registered in this shop
 * @author Israel Edet
 * @version 1.0
 */
public class Customer {

    private String customerFirstName;
    private String customerLastName;
    private String customerGender;
    private String customerBirthDate;

    private int customerId;
    private String customerTelephone;
    private String customerAddress;
    private String paymentMode;
    private final DBConnectionClass bConnectionClass;
    private static final Logger LOG = Logger.getLogger(Customer.class.getName());

    public Customer() {
       this.bConnectionClass = new DBConnectionClass();
     }
/**
 * Default Constructor
 * @param customerFirstName
 * @param customerLastName
 * @param customerGender
 * @param customerBirthDate
 * @param customerId
 * @param customerTelephone
 * @param customerAddress
 * @param paymentMode 
 */
    public Customer(String customerFirstName, String customerLastName, String customerGender, String customerBirthDate, int customerId, String customerTelephone, String customerAddress, String paymentMode) {
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerGender = customerGender;
        this.customerBirthDate = customerBirthDate;
        this.customerId = customerId;
        this.customerTelephone = customerTelephone;
        this.customerAddress = customerAddress;
        this.paymentMode = paymentMode;
        bConnectionClass = new DBConnectionClass();
    }

    /**
     * @return the customerFirstName
     * cc 2 = 1, cc= 1
     */
    public String getCustomerFirstName() {
        return customerFirstName;
    }

    /**
     * @param customerFirstName the customerFirstName to set
     */
    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    /**
     * @return the customerId
     * cc2 = 1, cc= 1
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * cc2 = 1, cc = 1
     * @return the customerTelephone
     */
    public String getCustomerTelephone() {
        return customerTelephone;
    }

    /**
     * @param customerTelephone the customerTelephone to set
     */
    public void setCustomerTelephone(String customerTelephone) {
        this.customerTelephone = customerTelephone;
    }

    /**
     * cc2 = 1, cc = 1
     * @return the customerAddress
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * @param customerAddress the customerAddress to set
     */
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    /**
     * cc2 = 1, cc = 1
     * @return the paymentMode
     */
    public String getPaymentMode() {
        return paymentMode;
    }

    /**
     * @param paymentMode the paymentMode to set
     */
    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }
/**
 * cc2 = 2, cc = 2
 * Register a new customer to system
 * @return boolean
 * @throws SQLException 
 */
    public Boolean addCustomer() throws SQLException {
       Statement s = this.bConnectionClass.getConnection().createStatement();
        s.execute("INSERT INTO customers "
                    + "(first_name,last_name,gender,birth_date,address,phone) "
                    + "VALUES('"+getCustomerFirstName()+"','"+getCustomerLastName()+"','"+getCustomerGender()+"',"
                    + "'"+getCustomerBirthDate().toString()+"','"+getCustomerAddress()+"','"+getCustomerTelephone()+"')");
        return s.getUpdateCount() == 1;
    }
    /**
     * Delete Customer From database
     * @param customerId
     * @return boolean true on delete success
     * @throws SQLException 
     * cc2 = 2, cc = 2
     */
    public Boolean deleteCustomer(int customerId) throws SQLException {
        Statement s = this.bConnectionClass.getConnection().createStatement();
        s.execute("DELETE FROM customers WHERE customer_id='"+customerId+"'");
        return s.getUpdateCount() == 1;
    }
    /**
     * cc2 = 2, cc= 2
     * Update customer information
     * @param customerId
     * @return boolean true on success 
     * @throws SQLException 
     */
    public Boolean updateCustomer(int customerId) throws SQLException {
        Statement s = this.bConnectionClass.getConnection().createStatement();
        s.execute("UPDATE  customers SET  "
                    + "first_name = '"+getCustomerFirstName()+"',last_name = '"+getCustomerLastName()+"',gender = '"+getCustomerGender()+"',birth_date = '"+getCustomerBirthDate().toString()+"',address = '"+getCustomerAddress()+"',phone = '"+getCustomerTelephone()+"' "
                    + "WHERE customer_id = '"+customerId+"'");
             
        return s.getUpdateCount() == 1;
    }
    /**
     * CC 2 = 1, cc = 1
     * Get Customer Object
     * can be refractored to use a stategy
     * @param customerId the id of customer to retrieve
     * @param strategy
     * 
     * @return A Customer Object
     * @throws SQLException 
     */
public Customer getCustomer(int customerId,InventoryQueryStrategy strategy) throws SQLException{
    Customer customer = null;
    //if there is a connection to db and sql query is not empty
        Statement s = this.bConnectionClass.getConnection().createStatement();
        ResultSet resultSet = s.executeQuery(strategy.run());
        
        while (resultSet.next()) {
            customer = new Customer(
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("gender"),
                    resultSet.getString("birth_date"),
                    resultSet.getInt("customer_id"),
                    resultSet.getString("phone"),
                    resultSet.getString("address"),
                    "No Payment Method Selected."
            );
            
        
    }
    return customer;
}

    /**cc2 = 1, cc = 1
     * @return the customerLastName
     */
    public String getCustomerLastName() {
        return customerLastName;
    }

    /**
     * @param customerLastName the customerLastName to set
     */
    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    /**
     * @return the customerGender
     * cc 2 = 1, cc =1 
     */
    public String getCustomerGender() {
        return customerGender;
    }

    /**
     * @param customerGender the customerGender to set
     */
    public void setCustomerGender(String customerGender) {
        this.customerGender = customerGender;
    }

    /**
     * cc 2 =1 , cc = 1
     * @return the customerBirthDate
     */
    public String getCustomerBirthDate() {
        return customerBirthDate;
    }

    /**
     * @param customerBirthDate the customerBirthDate to set
     */
    public void setCustomerBirthDate(String customerBirthDate) {
        this.customerBirthDate = customerBirthDate;
    }
}
