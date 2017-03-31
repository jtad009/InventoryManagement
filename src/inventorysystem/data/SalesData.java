/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysystem.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Epic
 */
public class SalesData {

    private final Vector cartItems;
    private final DBConnectionClass bConnectionClass;
    private final String employee;
    private Customer customer;
    private Product product;
/**
 * SaleData Constructor
 * @param cartItems
 * @param employee
 * @param customer 
 */
    public SalesData(Vector cartItems, String employee, Customer customer) {
        this.cartItems = cartItems;
        this.employee = employee;
        this.customer = customer;

        bConnectionClass = new DBConnectionClass();
        product = new Product();
        parseCartItems();
    }
/**
 * parse cart table and get items needed
 */
    private void parseCartItems() {
        // if cart not empty them scan thru the data and save to cart table
        if (cartItems.size() > 0) {
            int last_insert_id = insertSale();
            for (int i = 0; i < cartItems.size(); i++) {
                double sale_qty = Double.parseDouble(((Vector) cartItems.elementAt(i)).elementAt(2).toString());
                double sale_price = Double.parseDouble(((Vector) cartItems.elementAt(i)).elementAt(3).toString());
                String productName = (String) ((Vector) cartItems.elementAt(i)).elementAt(1);
                try {
                    product = product.getProduct(productName);
                } catch (SQLException ex) {
                    Logger.getLogger(SalesData.class.getName()).log(Level.SEVERE, null, ex);
                }
                insertCartItems(last_insert_id, sale_qty, sale_price);
            }
        }

    }

    /**
     * insert cart details
     *
     * @param sale_id sale record ID
     * @param sale_qty item quantity
     * @param sale_price item cost* quantity
     */
    private void insertCartItems(int sale_id, double sale_qty, double sale_price) {

        if (null != this.bConnectionClass.getConnection()) {

            try {
                Statement s = this.bConnectionClass.getConnection().createStatement();
                s.execute("INSERT INTO sale_details (sale_id,product_id,sale_quantity,sale_price) VALUES('" + sale_id + "','" + product.getProductId() + "','" + sale_qty + "','" + sale_price + "')");
            } catch (SQLException ex) {
                Logger.getLogger(SalesData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * create sale record in the db
     *
     * @return int last insert id
     */
    private int insertSale() {
        int last_insert_id = 0;
        if (null == this.customer) {
            this.customer = new Customer();
        }
        try {
            Statement s = this.bConnectionClass.getConnection().createStatement();
            s.execute("INSERT INTO sales (customer_id,employee_id,sale_date) VALUES('" + customer.getCustomerId() + "','" + employee + "',NOW())", Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = s.getGeneratedKeys();
            if (resultSet.first()) {
                last_insert_id = resultSet.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SalesData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return last_insert_id;
    }
}
