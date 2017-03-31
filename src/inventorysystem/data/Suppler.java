/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysystem.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

/**
 *
 * @author Epic
 */
public class Suppler {
    private int supplerId;
    private String supplerName;
    private String supplerAddress;
    private String supplerPhone;
    private String supplerBusinessName;

    public String getSupplerBusinessName() {
        return supplerBusinessName;
    }

    public void setSupplerBusinessName(String supplerBusinessName) {
        this.supplerBusinessName = supplerBusinessName;
    }
    private static final Logger LOG = Logger.getLogger(Suppler.class.getName());
    private DBConnectionClass connectionClass;

    public Suppler() {
        connectionClass = new DBConnectionClass();
    }
    public Suppler(int supplierId, String supplierName, String supplierAddress, String supplierPhone, String supplerBusinessName) {
        this.supplerId = supplierId;
        this.supplerName = supplierName;
        this.supplerAddress = supplierAddress;
        this.supplerPhone = supplierPhone;
        this.supplerBusinessName = supplerBusinessName;
        connectionClass = new DBConnectionClass();
    }

    /**
     * @return the supplerId
     */
    public int getSupplerId() {
        return supplerId;
    }

    /**
     * @param supplerId the supplerId to set
     */
    public void setSupplerId(int supplerId) {
        this.supplerId = supplerId;
    }

    /**
     * @return the supplerName
     */
    public String getSupplerName() {
        return supplerName;
    }

    /**
     * @param supplerName the supplerName to set
     */
    public void setSupplerName(String supplerName) {
        this.supplerName = supplerName;
    }

    /**
     * @return the supplerAddress
     */
    public String getSupplerAddress() {
        return supplerAddress;
    }

    /**
     * @param supplerAddress the supplerAddress to set
     */
    public void setSupplerAddress(String supplerAddress) {
        this.supplerAddress = supplerAddress;
    }

    /**
     * @return the supplerPhone
     */
    public String getSupplerPhone() {
        return supplerPhone;
    }

    /**
     * @param supplerPhone the supplerPhone to set
     */
    public void setSupplerPhone(String supplerPhone) {
        this.supplerPhone = supplerPhone;
    }
    /**
     * Add a new supplier to the database
     * @return Boolean true if information is saved
     * @throws SQLException 
     */
    public Boolean addSupplier() throws SQLException{
        
        DateFormat df = new SimpleDateFormat("yyyy/MM/DD");
        Date date = new Date();
        Statement statement = this.connectionClass.getConnection().createStatement();
        
        return statement.execute("INSERT INTO suppliers (supplier_name,supplier_date,supplier_phone,supplier_biz_name,supplier_address)"
                + " VALUES('"+getSupplerName()+"','"+df.format(date)+"','"+getSupplerPhone()+"','"+getSupplerBusinessName()+"','"+getSupplerAddress()+"')");
         
        
    }
    /**
     * Update supplier information
     * @param supplierId
     * @return Boolean true if information is updated
     * @throws SQLException 
     */
    public Boolean updateSupplier(int supplierId) throws SQLException{
        boolean isSaved = false;
        
        Statement statement = this.connectionClass.getConnection().createStatement();
        if(statement.execute("UPDATE suppliers SET supplier_name = '"+getSupplerName()+"',supplier_phone = '"+getSupplerPhone()+"' WHERE supplier_id = '"+supplierId+"')"))
            isSaved = true;
        return isSaved;
    }
    /**
     * Delete a supplier from database
     * @param supplierId
     * @return
     * @throws SQLException 
     */
    public  Boolean removeSupplier(int supplierId) throws SQLException{
        boolean isDeleted = false;
        Statement statement = this.connectionClass.getConnection().createStatement();
        if(statement.execute("DELETE FROM suppliers WHERE supplier_id = '"+supplierId+"'"))
            isDeleted = true;
        return  isDeleted;
    }
   /**
     * Get Suppler Object
     * @param supplierBizName  the bix name of supplier to retrieve
     * @param allSupplier  boolean should retrieve all supplier
     * @return A Suppler Object
     * @throws SQLException 
     */
public ArrayList<Suppler> getSupplier(String supplierBizName,boolean allSupplier) throws SQLException{
    ArrayList<Suppler> supplierList = new ArrayList<>();
    Suppler supplier = null;
    String sql = "";
    if(allSupplier){// if shud select all customers
        sql = "SELECT * FROM suppliers";
    }else {
        sql = "SELECT * FROM suppliers where supplier_biz_name = '"+supplierBizName+"'";
    }
    if(this.connectionClass.getConnection() != null && !sql.isEmpty()){ 
    //if there is a connection to db and sql query is not empty
        Statement s = this.connectionClass.getConnection().createStatement();
        ResultSet resultSet = s.executeQuery(sql);
        
        while (resultSet.next()) {
            supplier = new Suppler(
                    resultSet.getInt("supplier_id"),
                    resultSet.getString("supplier_name"),
                    resultSet.getString("supplier_address"),
                    resultSet.getString("supplier_phone"),
                    resultSet.getString("supplier_biz_name")
             );
            supplierList.add(supplier);
        }
    }
    return supplierList;
}
/**
 * Check if a value already exist in the database
 * @param columnToSearch
 * @param valueToSearchFor
 * @return true if no unique
 */
public boolean checkIfExist(String columnToSearch,String valueToSearchFor) throws SQLException{
    Statement s = this.connectionClass.getConnection().createStatement();
    ResultSet resultSet = s.executeQuery("select * from suppliers where `"+columnToSearch+"` = '"+valueToSearchFor+"'");
    if (resultSet.first()) {
        return true;
    }
    return false;
}
}
