package fr.reugest.main;

import fr.reugest.frames.MenuFrame;
import fr.reugest.frames.UsersFrame;

/**
 * Class of global variables
 * @author tpeyr
 *
 */
public class Globals {
	
	/**
	 * Main menu frame (stored to navigate properly between different frames)
	 */
	public static MenuFrame mainMenu;
        
        public static UsersFrame usersFrame;
	
	public static String version;
	
	public static String adminUsername;
	
	public static String adminPassword;
        
        public static void reloadUsersFrame() {
            usersFrame.dispose();
            usersFrame = new UsersFrame();
            usersFrame.setVisible(true);
        }
}
