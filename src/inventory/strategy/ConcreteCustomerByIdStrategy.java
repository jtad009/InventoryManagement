/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.strategy;

import inventorysystem.Interface.InventoryQueryStrategy;

/**
 *
 * @author Epic
 */
public class ConcreteCustomerByIdStrategy implements InventoryQueryStrategy{
    private final int customerID;

    public ConcreteCustomerByIdStrategy(int customerID) {
        this.customerID = customerID;
    }
    @Override
    public String run() {
        return  "SELECT * FROM customers where customer_id = '"+customerID+"'";
    }
    
}
