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
public class ConcreteAllCustomerStrategy implements InventoryQueryStrategy{

    @Override
    public String run() {
       return "select * from customer";
    }
    
}
