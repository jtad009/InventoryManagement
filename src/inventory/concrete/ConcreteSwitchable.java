package inventory.concrete;

import inventorysystem.Interface.Switchable;
import inventorysystem.gui.login;
import java.util.Vector;
import javax.swing.GroupLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import libs.Property;

/**
 *
 * @author Israel edet
 */
public class ConcreteSwitchable implements Switchable{
private final Vector panelStack;
private final boolean isLoggedIn;
private final GroupLayout panelHolder; 
    public ConcreteSwitchable(Vector panelStack,boolean loggedIn,GroupLayout panelHolder ) {
        this.panelStack = panelStack;
        isLoggedIn = loggedIn;
        this.panelHolder = panelHolder;
    }
    /**
     * switch between panels on the jframe as a means to show different parts of
     * the application in one frame. This is a static method because other
     * classes would use the static vector, which is used as an instance
     * variable will throw a NULL-POINTER-EXCEPTION
     *
     * @param panel
     
     */

    @Override
    public void OnPanelSwitched(JPanel panel) {
         JPanel currentPanel;
        // STEP1: get the  current panel ontop the panel stack
        // Step 2: if the stack is not empty and if we are not trying to replace a panel with itself
        // STep 3: then switch the panel with the one we want to see
        // Precaution: only a loggedin user can change panel or enter application.

        if (isLoggedIn) {
            if (!panelStack.isEmpty()) {
                currentPanel = (JPanel) panelStack.get(0);
                    if (currentPanel != panel) {
                        panelHolder.replace(currentPanel, panel);
                        panelStack.add(0, panel);
                    }
            }
        } else if (panel instanceof login) {
            panelHolder.replace((JPanel) panelStack.get(0), panel);
            panelStack.add(0, panel);
            Property property = new Property();
            property.clearProperty();
            //log user out of system
            JOptionPane.showMessageDialog(null, "You have Logged Out of system");
        } else {
            JOptionPane.showMessageDialog(null, "Login to visit " + panel.getName() + " panel.");
        }
    }
}
