package inventory.concrete;

import inventorysystem.Interface.InventoryObservable;
import inventorysystem.Interface.InventoryObserver;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

/**
 *
 * @author Epic
 */
public class ConcreteInventoryObservable implements InventoryObservable{

    private final List<InventoryObserver> observers = new ArrayList<>();
    private EventObject event;
    
    public synchronized void add(InventoryObserver observer) {
        if(!observers.contains(observer))
                observers.add(observer);
    }

    public synchronized void remove(InventoryObserver observer) {
        if(observers.contains(observer))
            observers.remove(observer); 
    }
    public ConcreteInventoryObservable() {
    }
    /**
     * Pass a trigger event to a listener in the system
     *
     */
    public synchronized void notifyObservers() {
        System.out.println("Tell Observers buo "+getEvent().getSource());
        //Loop through the observers and pick the one that is currently needed and trigger an event
        //the code complexity of this method could be too much but care will be taken to correct others
        for(Object observer : observers){
            System.out.println(""+observer.toString());
            ((InventoryObserver) observer).update(getEvent());

        }
    }

    /**
     * @return the event
     */
    public EventObject getEvent() {
        return event;
    }

    /**
     * @param event the event to set
     */
    public void setEvent(EventObject event) {
        this.event = event;
    }
}
