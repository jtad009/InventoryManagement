
package inventorysystem.Events;

import java.util.EventObject;
import inventorysystem.data.Product;
/**
 * When a product is added to the inventory, The product event is called 
 * @author Israel Edet
 */
public class ProductEvent extends EventObject {
    Product _thisProduct ;
    
    /**
     * Constructor for product event
     * @param source
     * @param product 
     */
    public ProductEvent(Object source, Product product) {
        super(source);
        _thisProduct = product;
    }
    /**
     * Get the current product
     * @return product
     */
    public Product getProduct(){
        return _thisProduct;
    }
}
