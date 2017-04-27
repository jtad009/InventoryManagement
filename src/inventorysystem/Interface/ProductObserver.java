/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysystem.Interface;

import inventorysystem.Events.ProductEvent;

/**
 *Product observer observing the inventory for change
 * @author Epic
 */
public interface ProductObserver extends InventoryObserver {
    /**
     * Add new to observed product
     * @param productEvent 
     */
    void add(ProductEvent productEvent);
    
    /**
     * Update changes on product you just observed
     * @param updateEvent 
     */
    void updated(ProductEvent updateEvent);

    

    
    
    
}
