
package inventorysystem.data;

import com.sun.glass.events.KeyEvent;
import static com.sun.javafx.tk.Toolkit.getToolkit;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.text.NumberFormatter;

/**
 * Class to format input for fields in the application
 * @author Epic
 */
public class NumberFormater {
    public static NumberFormatter integerOnlyField(){
        NumberFormat format = NumberFormat.getCurrencyInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true); // set this to true so that value will be commited on every key stroke instead of on focus lost
        return formatter;
    }
    public static void integerOnlyField(java.awt.event.KeyEvent evt,JLabel errorField){
        char c  = evt.getKeyChar();
        if(!((c >= 0) && (c <='9')) || (c == KeyEvent.VK_BACKSPACE) || (c == KeyEvent.VK_DELETE)){
            errorField.setText("Only text allowed in this field");
            evt.consume();
        }else{
           errorField.setText(""); 
        }
    }
}
