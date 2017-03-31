package inventorysystem.data;

import inventorysystem.Events.ProductEvent;
import inventorysystem.Interface.ProductListener;
import inventorysystem.Log;
import inventorysystem.gui.Supplier;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Epic
 */
public class Product implements ProductListener {

    private int productId;
    private String productName;
    private int productQuantity;
    private float productPrice;
    private String shippingWeight;
    private String description;
    private String productManufactureDate;
    private String productExpiryDate;
    private String productBatch;
    private float costPrice;
    private String supplierID; // who sulppied the product to us
    private int employeeID; // who dealt with the product received
    private DBConnectionClass connectionClass;
    private Product thisProduct;
    private String eventType;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public enum Constants {
        ADD, UPDATE, DELETE
    }

    public Product() {
        this.connectionClass = new DBConnectionClass();

    }

    public Product(int productID, String productName, int productQuantity,
            float productPrice, String shippingWeight,
            String description,
            String productManufactureDate,
            String productExpiryDate,
            String productBatch, float costPrice, String supplierId, int employeeId, String type) {
        this.productId = productID;
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        this.shippingWeight = shippingWeight;
        this.description = description;
        this.productManufactureDate = productManufactureDate;
        this.productExpiryDate = productExpiryDate;
        this.productBatch = productBatch;
        this.costPrice = costPrice;
        this.supplierID = supplierId;
        this.employeeID = employeeId;
        this.eventType = type;
        this.connectionClass = new DBConnectionClass();
    }
    //<editor-fold defaultstate="collapsed" desc="Getters and Setters for product class">

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public Product getThisProduct() {
        return thisProduct;
    }

    /**
     * @return the costPrice
     */
    public float getCostPrice() {
        return costPrice;
    }

    /**
     * @param costPrice the costPrice to set
     */
    public void setCostPrice(float costPrice) {
        this.costPrice = costPrice;
    }

    public void setThisProduct(Product thisProduct) {
        this.setCostPrice(thisProduct.getCostPrice());
        this.setProductName(thisProduct.getProductName());
        this.setDescription(thisProduct.getDescription());
        this.setEmployeeID(thisProduct.getEmployeeID());
        this.setProductBatch(thisProduct.getProductBatch());
        this.setProductExpiryDate(thisProduct.getProductExpiryDate());
        this.setProductId(thisProduct.getProductId());
        this.setProductManufactureDate(thisProduct.getProductManufactureDate());
        this.setProductPrice(thisProduct.getProductPrice());
        this.setProductQuantity(thisProduct.getProductQuantity());
        this.setSupplierID(thisProduct.getSupplierID());
    }

    /**
     * @return the productId
     */
    public int getProductId() {
        return productId;
    }

    /**
     * @param productId the productId to set
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * @return the productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName the productName to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * @return the productQuantity
     */
    public int getProductQuantity() {
        return productQuantity;
    }

    /**
     * @param productQuantity the productQuantity to set
     */
    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    /**
     * @return the productPrice
     */
    public float getProductPrice() {
        return productPrice;
    }

    /**
     * @param productPrice the productPrice to set
     */
    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    /**
     * @return the shippingWeight
     */
    public String getShippingWeight() {
        return shippingWeight;
    }

    /**
     * @param shippingWeight the shippingWeight to set
     */
    public void setShippingWeight(String shippingWeight) {
        this.shippingWeight = shippingWeight;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the productManufactureDate
     */
    public String getProductManufactureDate() {
        return productManufactureDate;
    }

    /**
     * @param productManufactureDate the productManufactureDate to set
     */
    public void setProductManufactureDate(String productManufactureDate) {
        /*
        s - a String object representing a date in in the format "yyyy-[m]m-[d]d". 
        The leading zero for mm and dd may also be omitted.
         */
        this.productManufactureDate.valueOf(productManufactureDate);
    }

    /**
     * @return the productExpiryDate
     */
    public String getProductExpiryDate() {
        return productExpiryDate;
    }

    /**
     * @param productExpiryDate the productExpiryDate to set
     */
    public void setProductExpiryDate(String productExpiryDate) {
        /*
            s - a String object representing a date in in the format 
            "yyyy-[m]m-[d]d". The leading zero for mm and dd may also be omitted.
         */
        this.productExpiryDate = productExpiryDate;
    }

    /**
     * @return the productBatch
     */
    public String getProductBatch() {
        return productBatch;
    }

    /**
     * @param productBatch the productBatch to set
     */
    public void setProductBatch(String productBatch) {
        this.productBatch = productBatch;
    }
//</editor-fold>

    /**
     * Add New Product to inventory
     *
     * @return Boolean true is product is saved to db and false if not saved
     * @throws SQLException
     */
    public Boolean addProduct() throws SQLException {
        boolean isSaved = false;
        int lastInsertID = 0;

        Statement addProductStatement = this.connectionClass.getConnection().createStatement();
        Suppler s = new Suppler();
        ArrayList<Suppler> supplierId = s.getSupplier(this.supplierID, false);

        if (!addProductStatement.execute("INSERT INTO products ("
                + "product_name,product_description,product_batch_no,"
                + "product_manufacture_date,product_expiry_date,timestamp) "
                + "VALUES('" + getProductName() + "','" + getDescription() + "',"
                + "'" + getProductBatch() + "',"
                + "'" + getProductManufactureDate() + "','" + getProductExpiryDate() + "',NOW() )", Statement.RETURN_GENERATED_KEYS)) {
            ResultSet rs = addProductStatement.getGeneratedKeys();

            if (rs.first()) {
                lastInsertID = rs.getInt(1);
                ResultSet stockingRS = this.insertStockingData(addProductStatement, supplierId, this.employeeID);
//                System.out.println(stockingRS.getIn);

                if (stockingRS.first()) {
                    int lastStockId = stockingRS.getInt(1);
                    this.addStockDetailData(addProductStatement, lastStockId, lastInsertID);
                }
            }
            isSaved = true;
        }

        return isSaved;
    }
    
    private void addStockDetailData(Statement addProductStatement, int lastStockId, int lastInsertID) {
        try {
            addProductStatement.execute("INSERT INTO stock_details "
                    + "(stocking_id,product_id,stock_quantity,cost_price,sell_price) "
                    + "VALUES('" + lastStockId + "','" + lastInsertID + "','" + getProductQuantity() + "','" + getCostPrice() + "','" + getProductPrice() + "')");
        } catch (SQLException ex) {
            Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ResultSet insertStockingData(Statement addProductStatement, ArrayList<Suppler> supplierId, int employee_id) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        java.util.Date date = new java.util.Date();
        ResultSet rs = null;
        try {
            addProductStatement.execute("INSERT INTO stocking (supplier_id,employee_id,date,timestamp) "
                    + "VALUES('" + supplierId.get(0).getSupplerId() + "','" + employee_id + "','" + dateFormat.format(date) + "',NOW() )", Statement.RETURN_GENERATED_KEYS);
            rs = addProductStatement.getGeneratedKeys();

        } catch (Exception e) {
        }

        return rs;
    }

    /**
     * Delete product from the inventory
     *
     * @param productId
     * @return Boolean true if product is deleted from inventory
     * @throws SQLException
     */
    public Boolean removeProduct(int productId) throws SQLException {
        boolean isSaved = false;
        Statement addProductStatement = this.connectionClass.getConnection().createStatement();

        if (!addProductStatement.execute("DELETE FROM products WHERE product_id = '" + productId + "'")) {
            isSaved = true;
        }

        return isSaved;
    }

    /**
     * Update product information
     *
     * @param productId
     * @return boolean true if product information is successfully updated
     * @throws SQLException
     */
    public Boolean updateProduct() throws SQLException {
        boolean isSaved = false;
        Statement addProductStatement = this.connectionClass.getConnection().createStatement();

        if (!addProductStatement.execute("UPDATE products SET "
                + "product_name = '" + getProductName() + "',product_description = '" + getDescription() + "'"
                + ",product_batch_no = '" + getProductBatch() + "',"
                + "product_manufacture_date = '" + getProductManufactureDate() + "',"
                + "product_expiry_date = '" + getProductExpiryDate() + "'"
                + " WHERE product_id = '" + this.getProductId() + "' "
        )) {
            this.updateStockDetailData(addProductStatement);
            isSaved = true;
        }

        return isSaved;
    }
    private void updateStockDetailData(Statement updateProduct)
    {
        
        try {
            updateProduct.execute("UPDATE stock_details SET stock_quantity = '"+getProductQuantity()+"',cost_price = '"+getCostPrice()+"',sell_price = '"+getProductPrice()+"' WHERE product_id  = '"+getProductId()+"'");
         } catch (Exception e) {
             Log.getLogger("Error while saving stocking details "+e.getMessage());
        }
    }
    private void insertStockingData(){
        
    }
    /**
     * Get a result set of all products avaliable to fill table model
     *
     * @return ResultSet of all products
     */
    public ResultSet getAllProducts() {
        ResultSet rs = null;
        try {
            String sql = "SELECT products.product_id as ID ,product_name as Name,stock_quantity as Quantity,sell_price as 'Sale Price',cost_price as 'Cost Price',product_manufacture_date as 'Manufacture Date',product_expiry_date as 'Expiry Date',product_batch_no as Batch,supplier_name as Supplier, product_description as Description FROM products INNER JOIN stock_details ON products.product_id = stock_details.product_id INNER JOIN stocking s ON s.stocking_id = stock_details.stocking_id LEFT JOIN suppliers ON suppliers.supplier_id = s.supplier_id";
            Statement s = this.connectionClass.getConnection().createStatement();

            rs = s.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    public Product getProduct(int productId) throws SQLException {
        Product product = null;
        String sql = "SELECT * FROM products INNER JOIN stock_details ON products.product_id = stock_details.product_id INNER JOIN stocking s ON s.stocking_id = stock_details.stocking_id LEFT JOIN suppliers ON suppliers.supplier_id = s.supplier_id";
        sql += " WHERE products.product_id = '" + productId + "'";

        Statement s = this.connectionClass.getConnection().createStatement();

        ResultSet rs = s.executeQuery(sql);
        while (rs.next()) {
            product = new Product(
                    rs.getInt("product_id"),
                    rs.getString("product_name"),
                    rs.getInt("stock_quantity"),
                    rs.getFloat("sell_price"),
                    "shipping_weight not set",
                    rs.getString("product_description"),
                    rs.getString("product_manufacture_date"),
                    rs.getString("product_expiry_date"),
                    rs.getString("product_batch_no"),
                    rs.getFloat("cost_price"),
                    rs.getString("supplier_biz_name"),
                    rs.getInt("supplier_id"),//employee id not yet set
                    null
            );
        }
        return product;
    }

    /**
     * Search for product by name
     * @param productName
     * @return Product or Null is product not found
     * @throws SQLException
     */
    public Product getProduct(String productName) throws SQLException {
        Product product = null;
        String sql = "SELECT products.product_id as ID ,product_name as Name,stock_quantity as Quantity,sell_price as 'Sale Price',cost_price as 'Cost Price',product_manufacture_date as 'Manufacture Date',product_expiry_date as 'Expiry Date',product_batch_no as Batch,supplier_name as Supplier, product_description as Description FROM products INNER JOIN stock_details ON products.product_id = stock_details.product_id INNER JOIN stocking s ON s.stocking_id = stock_details.stocking_id LEFT JOIN suppliers ON suppliers.supplier_id = s.supplier_id WHERE `product_name` = '" + productName + "' ";

        Statement s = this.connectionClass.getConnection().createStatement();

        ResultSet rs = s.executeQuery(sql);
        while (rs.next()) {
            product = new Product(
                    rs.getInt("ID"),
                    rs.getString("Name"),
                    rs.getInt("Quantity"),
                    rs.getFloat("Sale Price"),
                    "shipping_weight not set",
                    rs.getString("Description"),
                    rs.getString("Manufacture Date"),
                    rs.getString("Expiry Date"),
                    rs.getString("Batch"),
                    rs.getFloat("Cost Price"),
                    rs.getString("Supplier"),
                    0,//employee id not yet set
                    null
            );
        }
        return product;
    }

    @Override
    public void addProduct(ProductEvent productEvent) {
        
        if (productEvent.getProduct().getEventType().equals(String.valueOf(Product.Constants.ADD)) ) {
            this.setThisProduct(productEvent.getProduct());

            try {
                if (this.addProduct()) {
                    JOptionPane.showMessageDialog(null, "New Product Saved");
                } else {
                    JOptionPane.showMessageDialog(null, "Error while saving new product ");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void updateProduct(ProductEvent updateEvent) {
        
        if (updateEvent.getProduct().getEventType().equals(String.valueOf(Product.Constants.UPDATE))) {
            this.setThisProduct(updateEvent.getProduct());
            try {
                if (!this.updateProduct()) {
                    JOptionPane.showMessageDialog(null, "Product Update Saved");
                } else {
                    JOptionPane.showMessageDialog(null, "Error while saving new product ");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
