/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysystem.gui;

import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Epic
 */
public class BorderPadding {

    public static void addPadding(JComponent c) {
        Border b = c.getBorder();
        Border margin = new EmptyBorder(10, 10, 10, 10);
        c.setBorder(new CompoundBorder(b, margin));
    }   
}
