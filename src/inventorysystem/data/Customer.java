package inventorysystem.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
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
    private Date customerBirthDate;

    private int customerId;
    private String customerTelephone;
    private String customerAddress;
    private String paymentMode;
    private DBConnectionClass bConnectionClass;
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
    public Customer(String customerFirstName, String customerLastName, String customerGender, Date customerBirthDate, int customerId, String customerTelephone, String customerAddress, String paymentMode) {
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

    public Boolean addCustomer() throws SQLException {
        boolean isSaved = false;
        if (null != this.bConnectionClass.getConnection()) {
            Statement s = this.bConnectionClass.getConnection().createStatement();
            if(s.execute("INSERT INTO customers "
                    + "(first_name,last_name,gender,birth_date,address,phone) "
                    + "VALUES('"+getCustomerFirstName()+"','"+getCustomerLastName()+"','"+getCustomerGender()+"'"
                    + "'"+getCustomerBirthDate().toString()+"','"+getCustomerAddress()+"','"+getCustomerTelephone()+"')"))
                    isSaved = true;
        }
        return isSaved;
    }
    /**
     * Delete Customer From database
     * @param customerId
     * @return boolean true on delete success
     * @throws SQLException 
     */
    public Boolean deleteCustomer(int customerId) throws SQLException {
        boolean isDeleted = false;
        if (null != this.bConnectionClass.getConnection()) {
            Statement s = this.bConnectionClass.getConnection().createStatement();
            if(s.execute("DELETE FROM customers WHERE customer_id='"+customerId+"'"))
                    isDeleted = true;
        }
        return isDeleted;
    }
    /**
     * Update customer information
     * @param customerId
     * @return boolean true on success 
     * @throws SQLException 
     */
    public Boolean updateCustomer(int customerId) throws SQLException {
        boolean isDeleted = false;
        if (null != this.bConnectionClass.getConnection()) {
            Statement s = this.bConnectionClass.getConnection().createStatement();
            if(s.execute("UPDATE  customers SET  "
                    + "first_name = '"+getCustomerFirstName()+"',last_name = '"+getCustomerLastName()+"',gender = '"+getCustomerGender()+"',birth_date = '"+getCustomerBirthDate().toString()+"',address = '"+getCustomerAddress()+"',phone = '"+getCustomerTelephone()+"' "
                    + "WHERE customer_id = '"+customerId+"'"))
                    isDeleted = true;
        }
        return isDeleted;
    }
    /**
     * Get Customer Object
     * @param customerId the id of customer to retrieve
     * @param allCustomers boolean she retrieve all customers
     * @return A Customer Object
     * @throws SQLException 
     */
public Customer getCustomer(int customerId,boolean allCustomers) throws SQLException{
    Customer customer = null;
    String sql = "";
    if(allCustomers){// if shud select all customers
        sql = "SELECT * FROM customers";
    }else {
        sql = "SELECT * FROM customers where customer_id = '"+customerId+"'";
    }
    if(this.bConnectionClass.getConnection() != null && !sql.isEmpty()){ 
    //if there is a connection to db and sql query is not empty
        Statement s = this.bConnectionClass.getConnection().createStatement();
        ResultSet resultSet = s.executeQuery(sql);
        
        while (resultSet.next()) {
            customer = new Customer(
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("gender"),
                    new Date(resultSet.getString("birth_date")),
                    resultSet.getInt("customer_id"),
                    resultSet.getString("phone"),
                    resultSet.getString("address"),
                    "No Payment Method Selected."
            );
            
        }
    }
    return customer;
}

    /**
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
     * @return the customerBirthDate
     */
    public Date getCustomerBirthDate() {
        return customerBirthDate;
    }

    /**
     * @param customerBirthDate the customerBirthDate to set
     */
    public void setCustomerBirthDate(Date customerBirthDate) {
        this.customerBirthDate = customerBirthDate;
    }
}
