/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysystem.Interface;

import java.util.EventListener;
import java.util.EventObject;

/**
 *
 * @author Epic
 */
public interface InventoryObserver extends EventListener {
    
    /**
     * Get the recent update made to an observable currently being observed
     * @param object the object that carried out the event we are alerting you of
     */
    public abstract void update(EventObject object);
}
