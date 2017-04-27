/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libs;


import java.util.logging.Logger;

/**
 *
 * @author Epic
 */
public class Log {
    private static final Logger LOG = Logger.getLogger(Log.class.getName());
    public static void  getLogger(String msg){
        LOG.info(msg);
    }
}
