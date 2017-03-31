/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysystem.Events;

import inventorysystem.Interface.LoginImplementation;
import inventorysystem.Interface.LoginInterface;
import inventorysystem.Interface.ProductListener;
import inventorysystem.gui.ProductEdit;
import inventorysystem.gui.Products;
import inventorysystem.gui.login;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Epic
 */
public class Request {

    public List<Object> listeners = new ArrayList<>();

    public synchronized void addLoginListener(LoginInterface loginInterface) {
        listeners.add((LoginInterface) loginInterface);
    }

    public synchronized void removeProductListener(LoginInterface loginInterface) {
        listeners.add((LoginInterface) loginInterface);
    }

    public synchronized void addProductListener(ProductListener productListener) {
        listeners.add((ProductListener) productListener);
    }

    public synchronized void removeProductListener(ProductListener productListener) {
        listeners.add((ProductListener) productListener);
    }

    public Request() {
    }
    /**
     * Pass a trigger event to a listener in the system
     *
     * @param event
     */
    public synchronized void _fireEvents(EventObject event) {
        //Loop through the listeners and pick the one that is currently needed and trigger an event
        //the code complexity of this method could be too much but care will be taken to correct others
        
        for (int i = 0; i < listeners.size(); i++) {
            Object object = listeners.get(i);
            System.out.println(object);
            if (event.getSource() instanceof login && object instanceof LoginImplementation) {
                ((LoginInterface) object).handleEvent((LoginEvent) event);
            } else if ((event.getSource() instanceof Products || event.getSource() instanceof ProductEdit) && object instanceof ProductListener) {
                System.out.println("New "+((ProductEvent) event).getProduct().getEventType());
                ((ProductListener) object).addProduct((ProductEvent) event);
                ((ProductListener) object).updateProduct((ProductEvent) event);
            }
        }
    }
}
