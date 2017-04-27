package inventorysystem;

import libs.Property;
import libs.Log;
import inventory.concrete.ConcreteInventoryObservable;
import inventorysystem.Interface.InventoryObserver;
import inventorysystem.gui.login;
import java.awt.Dimension;
import java.util.Vector;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import inventory.concrete.ConcreteLoginObserver;
import inventorysystem.gui.Dashboard;
import inventorysystem.gui.ProductEdit;
import inventorysystem.gui.Products;
import inventorysystem.gui.Supplier;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.EventObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import static javax.swing.SwingUtilities.updateComponentTreeUI;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import inventory.concrete.ConcreteSwitchable;

/**
 *
 * @author Epic
 */
public class InventorySystem implements InventoryObserver {

    private static GroupLayout panelHolder = null;
    private JLayeredPane pane = null;
    private static Vector panelStack;
    
    private login login;

    public InventorySystem() {
    }
    ConcreteInventoryObservable r;

    public InventorySystem(String title) {
        /*
        * Step 1: Create a  vector that  will hold all the panels this application will use.
        * Reason : this is done so we can swap between panels when we need to.
        * Step 2: Create a JLayeredPane this pane will hold the current panel in view
        * our initial panel is obviously the login panel. after which we create the JFrame to
        * hold our application together.
         */
        panelStack = new Vector();
        pane = new JLayeredPane();
        r = new ConcreteInventoryObservable();
        r.add((InventoryObserver)this);//register this class as an observer
        
        login = new login(r);

        //ProductEdit edit = new ProductEdit(r);
        pane.add(login);
        panelStack.add(0, login);

        panelHolder = new javax.swing.GroupLayout(pane);
        pane.setLayout(panelHolder);

        panelHolder.setHorizontalGroup(
                panelHolder.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(login, javax.swing.GroupLayout.DEFAULT_SIZE, 1200, Short.MAX_VALUE)
        );
        panelHolder.setVerticalGroup(
                panelHolder.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(login, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
        );

        applicationFrame();

    }

    private JFrame applicationFrame() {
        JFrame frame = new JFrame("Inventory SYS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(new Dimension(600, 600));
        frame.setLocationRelativeTo(null);
        frame.add(pane);
        frame.setJMenuBar(topMenu());
        frame.setVisible(true);
        frame.show(true);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(InventorySystem.class.getName()).log(Level.SEVERE, null, ex);
        }

        SwingUtilities.updateComponentTreeUI(frame);

        updateComponentTreeUI(frame);
        // on window closing clear the property cache file
        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                Log.getLogger("app closing now");
                Property property = new Property();
                property.clearProperty();
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
        return frame;
    }

    private javax.swing.JMenuBar topMenu() {
        javax.swing.JMenuBar topMenu = new javax.swing.JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitApp = new JMenuItem("Exit");
        fileMenu.add(exitApp);
        exitApp.addActionListener(this::logout);

        //Action menu
        JMenu navigateMenu = new JMenu("Navigate");
        JMenuItem dashboard = new JMenuItem("Dashboard");
        dashboard.addActionListener(this::dashboard);
        navigateMenu.add(dashboard);
        
        JMenu productMenu = new JMenu("Product");
        JMenuItem newProductItem = new JMenuItem("Add Product");
        newProductItem.addActionListener(this::addNewProduct);

        JMenuItem updateProductItem = new JMenuItem("Update Product");
        updateProductItem.addActionListener(this::editProduct);

        productMenu.add(newProductItem);
        productMenu.add(updateProductItem);
        navigateMenu.add(productMenu);

        JMenuItem supplerItem = new JMenuItem("Add Suppler");
        supplerItem.addActionListener(this::addNewSuppler);
        navigateMenu.add(supplerItem);
        
        topMenu.add(fileMenu);
        topMenu.add(navigateMenu);
        //only show add product and other menus if user is admin. sales rep can only handle sales
//        if(login.getCurrentEmployee().getEmployeeType().equalsIgnoreCase("admin")){
//           //TODO: 
//        }
        return topMenu;
    }

    /**
     * @param args the command line arguments 0122954992 GTB
     */
    public static void main(String[] args) {
        InventorySystem is = new InventorySystem("");
    }
//<editor-fold defaultstate="collapsed" desc="Action listener methods">
    private void dashboard(java.awt.event.ActionEvent actionEvent) {
        Property cache = new Property();
        if (cache.readProperty("EMPLOYEE_TYPE").equals("admin")) {
                ConcreteSwitchable concreteSwitchable = new ConcreteSwitchable(panelStack, Boolean.TRUE, panelHolder);
                concreteSwitchable.OnPanelSwitched(new Dashboard());
            } else {
                
            }
    }

    private void logout(java.awt.event.ActionEvent actionEvent) {
        login l = new login(r);
        ConcreteSwitchable concreteSwitchable = new ConcreteSwitchable(panelStack, Boolean.FALSE, panelHolder);
        concreteSwitchable.OnPanelSwitched(new Dashboard());
    }

    /**
     * Action to call new product panel
     *
     * @param evt
     */
    private void addNewProduct(java.awt.event.ActionEvent evt) {
        Products p = new Products(r);
        ConcreteSwitchable concreteSwitchable = new ConcreteSwitchable(panelStack, login.isLoggedIn(), panelHolder);
        concreteSwitchable.OnPanelSwitched(p);
    }

    private void addNewSuppler(java.awt.event.ActionEvent evt) {
        ConcreteSwitchable concreteSwitchable = new ConcreteSwitchable(panelStack, login.isLoggedIn(), panelHolder);
        concreteSwitchable.OnPanelSwitched(new Supplier());
        
    }

    private void editProduct(java.awt.event.ActionEvent evt) {
        ConcreteSwitchable concreteSwitchable = new ConcreteSwitchable(panelStack, login.isLoggedIn(), panelHolder);
        concreteSwitchable.OnPanelSwitched(new ProductEdit(r));
        
    }
public ConcreteSwitchable gotoDashboard(boolean loggedIn){
    ConcreteSwitchable concreteSwitchable = new ConcreteSwitchable(panelStack, loggedIn, panelHolder);
       return concreteSwitchable;
}
    //</editor-fold>
 @Override
    public void update(EventObject object) {
       if(object.getSource().equals(ConcreteLoginObserver.class)){
            gotoDashboard(Boolean.TRUE).OnPanelSwitched(new Dashboard());
        }
    }
}
