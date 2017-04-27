package inventory.strategy;

import inventorysystem.Interface.InventoryQueryStrategy;

/**
 *
 * @author Epic
 */
public class ConcreteItemByIdStrategy implements InventoryQueryStrategy{

    private final int productId;

    /**
     *Get inventory Item By ID
     * @param productId
     */
    public ConcreteItemByIdStrategy(int productId) {
        this.productId = productId;
    }
    
    @Override
    public String run() {
          return  "SELECT * FROM products INNER JOIN stock_details ON products.product_id = stock_details.product_id INNER JOIN stocking s ON s.stocking_id = stock_details.stocking_id LEFT JOIN suppliers ON suppliers.supplier_id = s.supplier_id WHERE products.product_id = '" + productId + "'";
    }
}
