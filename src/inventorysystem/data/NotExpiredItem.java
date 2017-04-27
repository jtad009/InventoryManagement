package inventorysystem.data;

import inventory.concrete.ConcreteInventoryObservable;
import inventorysystem.Events.ProductEvent;
import inventorysystem.Interface.InventoryObserver;
import inventorysystem.Interface.InventoryItem;
import libs.Log;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import inventorysystem.Interface.ProductObserver;
import java.util.EventObject;
import inventorysystem.Interface.InventoryQueryStrategy;

/**
 *
 * @author Epic
 */
public class NotExpiredItem extends InventoryItem
        implements ProductObserver {

    public enum Constants {
        ADD, UPDATE, DELETE, INVENTORY_UPDATE,
    }
    private final ConcreteInventoryObservable observable;

    public NotExpiredItem() {
        super(null);
        this.observable = null;
    }

    public NotExpiredItem(InventoryQueryStrategy inventoryStrategy, ConcreteInventoryObservable observable) {
        super(inventoryStrategy);
        this.observable = observable;
        observable.add((InventoryObserver) NotExpiredItem.this);
    }

    /**
     * Add New NotExpiredItem to inventory
     *
     * @return Boolean true is product is saved to db and false if not saved
     * @throws SQLException
     */
    public Boolean save() throws SQLException {
        boolean isSaved = false;
        int lastInsertID = 0;
        try (Statement addProductStatement = this.connectionClass.getConnection().createStatement()) {
            Suppler s = new Suppler();
            ArrayList<Suppler> supplierObject = s.getSupplier(this.supplierBusinessName, false);
            if (!addProductStatement.execute("INSERT INTO products ("
                    + "product_name,product_description,product_batch_no,"
                    + "product_manufacture_date,product_expiry_date,timestamp) "
                    + "VALUES('" + getName() + "','" + getDescription() + "',"
                    + "'" + getBatch() + "',"
                    + "'" + getManufactureDate() + "','" + getExpiryDate() + "',NOW() )", Statement.RETURN_GENERATED_KEYS)) {
                ResultSet rs = addProductStatement.getGeneratedKeys();
                if (rs.first()) {
                    lastInsertID = rs.getInt(1);
                    int lastInsertStockId = this.insertStockingData(supplierObject, this.employeeID);
                    if (lastInsertStockId > 0) {
                        this.addStockDetailData(addProductStatement, lastInsertStockId, lastInsertID);
                        isSaved = true;
                    }
                }

            }
        }
        return isSaved;
    }

    private void addStockDetailData(Statement addProductStatement, int lastStockId, int lastInsertID) {
        try {
            addProductStatement.execute("INSERT INTO stock_details "
                    + "(stocking_id,product_id,stock_quantity,cost_price,sell_price) "
                    + "VALUES('" + lastStockId + "','" + lastInsertID + "','" + getQuantity() + "','" + getCostPrice() + "','" + getPrice() + "')");
        } catch (SQLException ex) {
            Logger.getLogger(NotExpiredItem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int insertStockingData(ArrayList<Suppler> supplierId, int employee_id) throws SQLException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        java.util.Date date = new java.util.Date();
        int result = 0;
        try (Statement addProductStatement = this.connectionClass.getConnection().createStatement()) {
            addProductStatement.execute("INSERT INTO stocking (supplier_id,employee_id,date,timestamp) "
                    + "VALUES('" + supplierId.get(0).getSupplerId() + "','" + employee_id + "','" + dateFormat.format(date) + "',NOW() )", Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = addProductStatement.getGeneratedKeys();
            if (rs.first()) {
                result = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw e;
        }
        return result;
    }

    /**
     * Delete product from the inventory
     *
     * @param productId
     * @return Boolean true if product is deleted from inventory 1 is returned
     * else 0
     * @throws SQLException
     */
    public Boolean remove(int productId) throws SQLException {
        try (Statement addProductStatement = this.connectionClass.getConnection().createStatement()) {
            addProductStatement.execute("DELETE FROM products WHERE product_id = '" + productId + "'");
            return addProductStatement.getUpdateCount() == 1;
        }
    }

    /**
     * Update product information
     *
     * @return boolean true if product information is successfully updated
     * @throws SQLException
     */
    public Boolean doUpdate() throws SQLException {
        Statement addProductStatement = this.connectionClass.getConnection().createStatement();
        if (!addProductStatement.execute("UPDATE products SET "
                + "product_name = '" + getName() + "',product_description = '" + getDescription() + "'"
                + ",product_batch_no = '" + getBatch() + "',"
                + "product_manufacture_date = '" + getManufactureDate() + "',"
                + "product_expiry_date = '" + getExpiryDate() + "'"
                + " WHERE product_id = '" + this.getId() + "' "
        )) {
            this.updateStockDetailData(addProductStatement);
        }
        return addProductStatement.getUpdateCount() != -1;// if update count != -1 then return true
    }

    private void updateStockDetailData(Statement updateProduct) {
        try {
            updateProduct.execute("UPDATE stock_details SET stock_quantity = '" + getQuantity() + "',cost_price = '" + getCostPrice() + "',sell_price = '" + getPrice() + "' WHERE product_id  = '" + getId() + "'");
        } catch (Exception e) {
            Log.getLogger("Error while saving stocking details " + e.getMessage());
        }
    }

    /**
     * Update stock details after sales is made
     *
     * @return boolean true on success if result !== -1 then return true
     */
    private boolean updateStockDetailData() {
        int result = -1;
        try {
            Statement s = this.connectionClass.getConnection().createStatement();

            s.execute("UPDATE stock_details SET "
                    + "stock_quantity = (stock_quantity - " + getQuantity() + ") "
                    + "WHERE product_id  = '" + getId() + "' ");
            result = s.getUpdateCount();
        } catch (Exception e) {
            Log.getLogger("Error while saving stocking details " + e.getMessage());
        }
        return result != -1;
    }

    /**
     * Get a result set of all products avaliable to fill table model
     *
     * @return ResultSet of all products
     */
    @Override
    public ResultSet getAll() {
        return super.getAll();
    }

    @Override
    public void add(ProductEvent productEvent) {
        this.setThisProduct(productEvent.getProduct());
        try {
            if (this.save()) {
                JOptionPane.showMessageDialog(null, "New Product Saved");
            } else {
                JOptionPane.showMessageDialog(null, "Error while saving new product ");
            }
        } catch (SQLException ex) {
            Logger.getLogger(NotExpiredItem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updated(ProductEvent updateEvent) {
        this.setThisProduct(updateEvent.getProduct());
        try {
            if (this.doUpdate()) {
                JOptionPane.showMessageDialog(null, "Product Update Saved");
            } else {
                JOptionPane.showMessageDialog(null, "Error while saving new product ");
            }
        } catch (SQLException ex) {
            Logger.getLogger(NotExpiredItem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(EventObject object) {
        if (object instanceof ProductEvent) {
            ProductEvent productEvent = (ProductEvent) object;
            if (productEvent.getProduct().getEventType().equals(String.valueOf(NotExpiredItem.Constants.ADD))) {
                this.add(productEvent);
            } else if (productEvent.getProduct().getEventType().equals(String.valueOf(NotExpiredItem.Constants.UPDATE))) {
                this.updated(productEvent);
            } else if (productEvent.getProduct().getEventType().equals(String.valueOf(NotExpiredItem.Constants.INVENTORY_UPDATE))) {
                this.setThisProduct(productEvent.getProduct());
                System.out.println("sdsdsd" + productEvent.getProduct().getQuantity());
                this.updateStockDetailData();
            }
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters for product class">
    /**
     *
     * @return
     */
    public String getEventType() {
        return eventType;
    }

    /**
     *
     * @param eventType
     */
    public void setEventType(String eventType) {

        this.eventType = eventType;
    }

    public String getSupplierBusinessName() {
        return supplierBusinessName;
    }

    public void setSupplierBusinessName(String supplierBusinessName) {
        this.supplierBusinessName = supplierBusinessName;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public NotExpiredItem getThisProduct() {
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

    public void setThisProduct(NotExpiredItem thisProduct) {
        this.setCostPrice(thisProduct.getCostPrice());
        this.setName(thisProduct.getName());
        this.setDescription(thisProduct.getDescription());
        this.setEmployeeID(thisProduct.getEmployeeID());
        this.setBatch(thisProduct.getBatch());
        this.setExpiryDate(thisProduct.getExpiryDate());
        this.setId(thisProduct.getId());
        this.setManufactureDate(thisProduct.getManufactureDate());
        this.setPrice(thisProduct.getPrice());
        this.setQuantity(thisProduct.getQuantity());
        this.setSupplierBusinessName(thisProduct.getSupplierBusinessName());
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param productId the id to set
     */
    public void setId(int productId) {
        this.id = productId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param productName the name to set
     */
    public void setName(String productName) {
        this.name = productName;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param productQuantity the quantity to set
     */
    public void setQuantity(int productQuantity) {
        this.quantity = productQuantity;
    }

    /**
     * @return the price
     */
    public float getPrice() {
        return price;
    }

    /**
     * @param productPrice the price to set
     */
    public void setPrice(float productPrice) {
        this.price = productPrice;
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
     * @return the manufactureDate
     */
    public String getManufactureDate() {
        return manufactureDate;
    }

    /**
     * @param productManufactureDate the manufactureDate to set
     */
    public void setManufactureDate(String productManufactureDate) {
        /*
        s - a String object representing a date in in the format "yyyy-[m]m-[d]d".
        The leading zero for mm and dd may also be omitted.
         */
        this.manufactureDate = productManufactureDate;
    }

    /**
     * @return the expiryDate
     */
    public String getExpiryDate() {
        return expiryDate;
    }

    /**
     * @param productExpiryDate the expiryDate to set
     */
    public void setExpiryDate(String productExpiryDate) {
        /*
            s - a String object representing a date in in the format
            "yyyy-[m]m-[d]d". The leading zero for mm and dd may also be omitted.
         */
        this.expiryDate = productExpiryDate;
    }

    /**
     * @return the batch
     */
    public String getBatch() {
        return batch;
    }

    /**
     * @param productBatch the batch to set
     */
    public void setBatch(String productBatch) {
        this.batch = productBatch;
    }
//</editor-fold>
}
