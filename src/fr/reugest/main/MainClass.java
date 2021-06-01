package fr.reugest.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.SwingUtilities;

import fr.reugest.frames.ConnexionFrame;
import fr.thomas.orm.ORMConfig;
//import com.formdev.flatlaf.FlatIntelliJLaf;

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
