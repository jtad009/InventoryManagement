/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysystem.Interface;

import inventorysystem.data.DBConnectionClass;
import inventorysystem.data.NotExpiredItem;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Epic
 */
public abstract class InventoryItem {
    protected int id;
    protected String name;
    protected int quantity;
    protected float price;
    protected String shippingWeight;
    protected String description;
    protected String manufactureDate;
    protected String expiryDate;
    protected String batch;
    protected float costPrice;
    protected String supplierBusinessName; // who sulppied the product to us
    protected int employeeID; // who dealt with the product received
    protected DBConnectionClass connectionClass;
    protected NotExpiredItem thisProduct;
    protected String eventType;
    private final InventoryQueryStrategy strategy;
    
//    private final InventoryObserver observer;
    public InventoryItem(InventoryQueryStrategy inventoryStrategy) {
        this.strategy = inventoryStrategy;
        this.connectionClass = new DBConnectionClass();
    }
    /**
     * Get a result set of all products avaliable to fill table model
     *
     * @return ResultSet of all products
     */
    protected  ResultSet getAll(){
       ResultSet rs = null;
        try {
            Statement s = this.connectionClass.getConnection().createStatement();
            rs = s.executeQuery(this.strategy.run());
        } catch (SQLException ex) {
            Logger.getLogger(NotExpiredItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs; 
    }
    
    protected ResultSet getBelowThreshold(){
       ResultSet rs = null;
        try {
            String sql = "SELECT products.product_id as ID ,product_name as Name,stock_quantity as Quantity,sell_price as 'Sale Price',cost_price as 'Cost Price',product_manufacture_date as 'Manufacture Date',product_expiry_date as 'Expiry Date',product_batch_no as Batch,supplier_name as Supplier, product_description as Description FROM products INNER JOIN stock_details ON products.product_id = stock_details.product_id INNER JOIN stocking s ON s.stocking_id = stock_details.stocking_id LEFT JOIN suppliers ON suppliers.supplier_id = s.supplier_id WHERE stock_quantity < " + InventoryQueryStrategy.THRESHOLD + "";
            Statement s = this.connectionClass.getConnection().createStatement();

            rs = s.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(NotExpiredItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs; 
    } 
 }
