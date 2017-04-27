package inventorysystem.data;

import libs.DateFormatter;
import inventorysystem.Interface.InventoryItem;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import inventorysystem.Interface.InventoryQueryStrategy;

/**
 *
 * @author Epic
 */
public class ExpiredItem extends InventoryItem {

    private int expiryCount;

    public ExpiredItem(InventoryQueryStrategy inventoryStrategy) {
        super(inventoryStrategy);
    }

    /**
     * Return a list of expired products to fill table
     *
     * @return table model
     */
    public DefaultTableModel getModel() {
        Vector<Vector<Object>> data = new Vector<>();
        Vector title = new Vector();
        Vector<Object> v = null;
        title.add("Days To Expiry");
        title.add("Name");
        title.add("Expiry Date");
        title.add("Supplier");
        DateFormatter dateFormatter = new DateFormatter();
        try {
            ResultSet resultSet = (ResultSet) getAll();
            while (resultSet.next()) {
                v = new Vector<>();
                int daysToExpiry = dateFormatter.numberOfDays(resultSet.getString("Expiry Date"), dateFormatter.todaysDate());
                if (daysToExpiry > 0 && daysToExpiry < 14) {
                    v.add(daysToExpiry + " Day(s)");
                    for (int i = 1; i < (title.size()); i++) {
                        v.add(resultSet.getObject(title.get(i).toString()));
                    }
                    data.add(v);
                }
             }

        } catch (SQLException ex) {
            Logger.getLogger(ExpiredItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        expiryCount = data.size();
        return new DefaultTableModel(data, title);
    }

    public DefaultTableModel getThreshholdModel() {
        Vector<Vector<Object>> data = new Vector<>();
        Vector title = new Vector();
        title.add("Name");
        title.add("Quantity");
        title.add("Supplier");
        try {
            ResultSet resultSet = (ResultSet) getBelowThreshold();
            while (resultSet.next()) {
                Vector<Object> v = new Vector<>();
                for (int i = 0; i < (title.size()); i++) {
                    v.add(resultSet.getObject(title.get(i).toString()));
                }
                data.add(v);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ExpiredItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        expiryCount = data.size();
        return new DefaultTableModel(data, title);
    }
/**
 * cc2 = 1
 * @return int 
 */
    public int getCount() {   return expiryCount;   }

}
