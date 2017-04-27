
package inventorysystem.Interface;

import javax.swing.JPanel;

/**
 * This interface is called anytime we need to switch a panel
 * with another.
 * @version 1.0
 * @author Epic
 */
public interface Switchable {
    /**
     * This is called when we need to switch a panel and then we 
     * take in as param the new panel to switch to
     * @param panelToSwitch panel we want to move to
     */
    public void OnPanelSwitched(JPanel panelToSwitch);
}
