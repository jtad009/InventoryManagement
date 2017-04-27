/*
 * To change this license header, choose License Headers in Project Property.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libs;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 *
 * @author Epic
 */
public class Property {
    
    public enum constants{
    EMPLOYEE_ID,
    EMPLOYEE_NAME,
    EMPLOYEE_USERNAME, EMPLOYEE_TYPE
    }

    private final Properties prop;

    public Property() {
        prop = new Properties();

    }
/**
 * Save to property file
 * @param key
 * @param value 
 */
    public void writeProperty(String key, String value) {

        OutputStream output = null;

        try {

            output = new FileOutputStream("config.properties");
            // set the properties value
            prop.setProperty(key, value);

            // save properties to project root folder
            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
/**
 * Get a value from the property file
 * @param key 
 * @return String with the value
 */
    public String readProperty(String key) {
        InputStream input = null;
        String val = "";
        try {

            input = new FileInputStream("config.properties");

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            val = prop.getProperty(key);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return val;
    }
    /**
     * Clear property file
     */
    public void clearProperty()
    {
        this.prop.clear();
    }
    /**
     * Remove a certain information from the property file 
     * @param key key to remove
     */
    public void clearProperty(String key){
        this.prop.remove(key);
    }
}
