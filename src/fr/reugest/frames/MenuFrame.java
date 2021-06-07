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

public class MenuFrame extends JFrame {

	private JPanel panneau;
	private JMenu gestionMenu, planningMenu, aideMenu;
	private JMenuItem itemUtilisateurs, itemSalles, itemEquipements, itemReserver, itemImprimer, itemParametres,
			itemAbout;
	
	
	// ImageIcon img;
	// JLabel labelImg;

	public MenuFrame() {
		super("ReuGest - Menu principal");
		
		// Instanciate menuBar and items
		JMenuBar mb = new JMenuBar();
		gestionMenu = new JMenu("Gestion");
		planningMenu = new JMenu("Planning");
		aideMenu = new JMenu("Aide");
		panneau = new JPanel(new GridBagLayout());
		// labelImg = new JLabel("Texte : ");
		itemUtilisateurs = new JMenuItem("Utilisateurs");
		itemSalles = new JMenuItem("Salles");
		itemEquipements = new JMenuItem("Equipements");
		itemReserver = new JMenuItem("Réserver");
		itemImprimer = new JMenuItem("Imprimer");
		itemParametres = new JMenuItem("Paramètres");
		itemAbout = new JMenuItem("A propos");
		// img = new ImageIcon("logoReugest.jpg");
		gestionMenu.add(itemUtilisateurs);
		gestionMenu.add(itemSalles);
		gestionMenu.add(itemEquipements);
		planningMenu.add(itemReserver);
		planningMenu.add(itemImprimer);
		aideMenu.add(itemParametres);
		aideMenu.add(itemAbout);
		mb.add(gestionMenu);
		mb.add(planningMenu);
		mb.add(aideMenu);
		setJMenuBar(mb);
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
				JOptionPane.showMessageDialog(null, "R�uGest : Logiciel de gestion de\nplanning\n\nLogiciel �dit� et d�velopp� par\nJean Sauron et Thomas Peyrot.\n\n� Copyright 2021.\nTous droits r�serv�s.");
			}
		});
		
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
	}
	
}
