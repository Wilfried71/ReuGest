package fr.reugest.main;

import fr.reugest.frames.EquipementFrame;
import fr.reugest.frames.MenuFrame;
import fr.reugest.frames.PlanningFrame;
import fr.reugest.frames.RoomsFrame;
import fr.reugest.frames.UsersFrame;
import fr.reugest.models.Utilisateur;

/**
 * Class of global variables
 *
 * @author tpeyr
 *
 */
public class Globals {
    
    /**
     * User entity
     */
    public static Utilisateur user;

    /**
     * Main menu frame (stored to navigate properly between different frames)
     */
    public static MenuFrame mainMenu;

    public static UsersFrame usersFrame;

    public static RoomsFrame roomsFrame;

    public static EquipementFrame equipementFrame;
    
    public static PlanningFrame planningFrame;

    public static String version;

    public static String adminUsername;

    public static String adminPassword;

    public static void reloadUsersFrame() {
        usersFrame.dispose();
        usersFrame = new UsersFrame();
        usersFrame.setVisible(true);
    }

    public static void Rooms() {
        roomsFrame.dispose();
        roomsFrame = new RoomsFrame();
        roomsFrame.setVisible(true);
    }

    public static void reloadEquipementFrame() {
        equipementFrame.dispose();
        equipementFrame = new EquipementFrame();
        equipementFrame.setVisible(true);
    }
    
    public static void reloadPlanningFrame() {
        planningFrame.dispose();
        planningFrame = new PlanningFrame();
        planningFrame.setVisible(true);
    }
}
