package inventory.strategy;

import inventorysystem.Interface.InventoryQueryStrategy;


/**
 *This strategy gets all products that are not expired in db
 * @author Epic
 */
public class ConcreteNotExpiredStrategy implements InventoryQueryStrategy{
    @Override
    public String run() {
        return "SELECT products.product_id as ID ,product_name as Name,stock_quantity as Quantity,sell_price as 'Sale Price',cost_price as 'Cost Price',product_manufacture_date as 'Manufacture Date',product_expiry_date as 'Expiry Date',product_batch_no as Batch,supplier_name as Supplier, product_description as Description FROM products INNER JOIN stock_details ON products.product_id = stock_details.product_id INNER JOIN stocking s ON s.stocking_id = stock_details.stocking_id LEFT JOIN suppliers ON suppliers.supplier_id = s.supplier_id WHERE stock_quantity > " + InventoryQueryStrategy.THRESHOLD + "";
    }
}
