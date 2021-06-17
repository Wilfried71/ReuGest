package fr.reugest.main;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.SwingUtilities;

import fr.reugest.frames.ConnexionFrame;
import fr.thomas.orm.ORMConfig;
import javax.swing.UIManager;

/**
 * Main class of the ReuGest project
 *
 * @author tpeyr
 *
 */
public class MainClass {

    /**
     * Main method
     *
     * @param args
     */
    public static void main(String[] args) {
        
        
        /**
         * Load data from application.properties
         */
        InputStream is = MainClass.class.getResourceAsStream("/application.properties");
        Properties props = new Properties();
        try {
            props.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if(props.getProperty("dark.mode") == null || !props.getProperty("dark.mode").equals("true")) {
                //UIManager.setLookAndFeel(new FlatIntelliJLaf());
            } else {
                //UIManager.setLookAndFeel(new FlatDarculaLaf());
            }            
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        
        Globals.adminUsername = props.getProperty("admin.username");
        Globals.adminPassword = props.getProperty("admin.password");

        // Configure DB Connection
        ORMConfig.database = props.getProperty("database");
        ORMConfig.server = props.getProperty("server");
        ORMConfig.port = props.getProperty("server.port");
        ORMConfig.username = props.getProperty("username");
        ORMConfig.password = props.getProperty("password");
        ORMConfig.serverTimeZone = props.getProperty("serverTimezone");

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ConnexionFrame f = new ConnexionFrame();
                f.setVisible(true);
            }
        });
    }
}
