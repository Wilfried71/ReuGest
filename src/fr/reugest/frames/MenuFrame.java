package fr.reugest.frames;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import fr.reugest.main.Globals;
import fr.reugest.models.Droit;
import fr.reugest.models.Salle;
import fr.thomas.orm.Model;

public class MenuFrame extends JFrame {

    private JPanel panneau;
    private JMenu gestionMenu, planningMenu, aideMenu;
    private JMenuItem itemUtilisateurs, itemSalles, itemEquipements, itemReserver, itemImprimer, itemParametres,
            itemAbout;
    private JMenuBar mb;

    public MenuFrame() {
        super("ReuGest - Menu principal");

        // Instanciate menuBar and items
        this.createMenu();
        panneau = new JPanel(new GridBagLayout());
        setMinimumSize(new Dimension(720, 480));
        setContentPane(panneau);
        // Create image logo
        Image img = (new ImageIcon(getClass().getResource("/logo.jpg")))
                .getImage().getScaledInstance(400, 400, java.awt.Image.SCALE_SMOOTH);

        panneau.add(new JLabel(new ImageIcon(img)));
        // Set fullscreen
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Add listeners on menu items
        this.addItemsClickListeners();

        // Change icon
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/logo_64.png")));

        // Close definitively the application
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Save this object in globals variables
        Globals.mainMenu = this;
    }

    /**
     * Action listeners on menu items
     */
    private void addItemsClickListeners() {

        /**
         * Add onClick listener to "about" item
         */
        itemAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "RéuGest : Logiciel de gestion de\nplanning\n\nLogiciel édité et développé par\nJean Sauron et Thomas Peyrot.\n\n© Copyright 2021.\nTous droits réservés.");
            }
        });

        /**
         * Add onClick listener to "Planning" item
         */
        itemReserver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    /**
                     * Check if there is at least one salle
                     */
                    Model<Salle> salleModel = new Model<>(Salle.class);
                    if (salleModel.findAll().size() != 0) {
                        // Create new equipements frame
                        PlanningFrame planningFrame = new PlanningFrame();
                        
                        // Disable the main menu frame
                        Globals.mainMenu.setEnabled(false);
                        
                        /**
                         * When the users Frame is closed, activate menu form
                         */
                        planningFrame.setVisible(true);
                        
                        // When frame is closed
                        planningFrame.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                super.windowClosed(e);
                                //Enable main menu frame
                                Globals.mainMenu.setEnabled(true);
                            }
                        });
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Aucune salle créée.\nVeuillez en créer une.", "Information", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        /**
         * Add onClick listener to "Settings" item
         */
        itemParametres.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "La fonctionnalité n'a pas été développée.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    /**
     * Create menuBar
     */
    public void createMenu() {
        // Create menubar
        this.mb = new JMenuBar();

        Droit d = Globals.user.getDroit();

        /**
         * Gestion menu
         */
        // If Admin
        if (d.getId().equals(3L)) {
            gestionMenu = new JMenu("Gestion");
            itemUtilisateurs = new JMenuItem("Utilisateurs");
            itemSalles = new JMenuItem("Salles");
            itemEquipements = new JMenuItem("Equipements");
            gestionMenu.add(itemUtilisateurs);
            gestionMenu.add(itemSalles);
            gestionMenu.add(itemEquipements);
            mb.add(gestionMenu);

            /**
             * Add onClick listener to "Utilisateurs" item
             */
            itemUtilisateurs.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Create new user frame
                    UsersFrame usersFrame = new UsersFrame();

                    // Disable the main menu frame
                    Globals.mainMenu.setEnabled(false);

                    /**
                     * When the users Frame is closed, activate menu form
                     */
                    usersFrame.setVisible(true);

                    // When frame is closed
                    usersFrame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            super.windowClosed(e);
                            //Enable main menu frame
                            Globals.mainMenu.setEnabled(true);
                        }
                    });
                }
            });

            /**
             * Add onClick listener to "Salles" item
             */
            itemSalles.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Create new salle frame
                    RoomsFrame roomsFrame = new RoomsFrame();

                    // Disable the main menu frame
                    Globals.mainMenu.setEnabled(false);

                    /**
                     * When the users Frame is closed, activate menu form
                     */
                    roomsFrame.setVisible(true);

                    // When frame is closed
                    roomsFrame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            super.windowClosed(e);
                            //Enable main menu frame
                            Globals.mainMenu.setEnabled(true);
                        }
                    });
                }
            });

            /**
             * Add onClick listener to "Equipements" item
             */
            itemEquipements.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Create new equipements frame
                    EquipementFrame equipementFrame = new EquipementFrame();

                    // Disable the main menu frame
                    Globals.mainMenu.setEnabled(false);

                    /**
                     * When the users Frame is closed, activate menu form
                     */
                    equipementFrame.setVisible(true);

                    // When frame is closed
                    equipementFrame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            super.windowClosed(e);
                            //Enable main menu frame
                            Globals.mainMenu.setEnabled(true);
                        }
                    });
                }
            });
        }

        /**
         * Planning menu
         */
        planningMenu = new JMenu("Planning");
        itemReserver = new JMenuItem("Réserver");
        itemImprimer = new JMenuItem("Mon planning");
        planningMenu.add(itemReserver);
        planningMenu.add(itemImprimer);
        mb.add(planningMenu);

        /**
         * Help menu
         */
        aideMenu = new JMenu("Aide");
        itemParametres = new JMenuItem("Paramètres");
        itemAbout = new JMenuItem("A propos");
        aideMenu.add(itemParametres);
        aideMenu.add(itemAbout);
        mb.add(aideMenu);

        /**
         * Set menubar
         */
        setJMenuBar(mb);
    }
}
