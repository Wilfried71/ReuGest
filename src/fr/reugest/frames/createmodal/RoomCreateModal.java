/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.reugest.frames.createmodal;

import fr.reugest.main.Globals;
import fr.reugest.models.Droit;
import fr.reugest.models.Equipement;
import fr.reugest.models.Fonction;
import fr.reugest.models.Salle;
import fr.reugest.models.Service;
import fr.reugest.models.light.AffectationLight;
import fr.reugest.models.light.SalleLight;
import fr.reugest.models.light.UtilisateurLight;
import fr.thomas.orm.Model;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author jeansauron
 */
public class RoomCreateModal extends JFrame{

    private JTextField txtLibelle, txtPlaces;

    private List<Equipement> listEquipements;
    private java.awt.List formListEquipements;
    
    private Model<Equipement> equipementModel;
    private Model<SalleLight> salleModel;
    
    private List<Equipement> equipementList;
    
    
    
    
    private JButton validateButton, cancelButton;

    private JPanel panel;

    /**
     * Constructor
     */
    public RoomCreateModal() {
        super("Créer une salle");

        /**
         * Init models
         */

        // Load data
        equipementModel = new Model<Equipement>(Equipement.class);
        
        salleModel = new Model<SalleLight>(SalleLight.class);

        // Load data
        this.loadFormData();
        

        this.panel = new JPanel();
        this.setContentPane(panel);
        // Set right panel
        this.panel.setLayout(new GridLayout(5, 2, 25, 10));

        this.panel.add(new JLabel("Libelle : ", JLabel.RIGHT));
        txtLibelle = new JTextField();
        this.panel.add(txtLibelle);

        this.panel.add(new JLabel("Places : ", JLabel.RIGHT));
        txtPlaces = new JTextField();
        this.panel.add(txtPlaces);

        this.panel.add(new JLabel("Equipements : ", JLabel.RIGHT)); 
        formListEquipements = new java.awt.List();
        formListEquipements.setMultipleMode(true);
        this.loadEquipementsInJList();
        this.panel.add(formListEquipements);
        
        
        //this.loadEquipementsInJList();
        /**
         * Create empty JLabel to fill
         */
        this.panel.add(new JLabel());
        this.panel.add(new JLabel());
        

        // Create buttons
        this.cancelButton = new JButton("Annuler");
        this.validateButton = new JButton("Valider");

        this.panel.add(this.cancelButton);
        this.panel.add(this.validateButton);
        this.panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(360, 640);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        // On cancel creation
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close modal
                dispose();
                // Enable user frame
                Globals.roomsFrame.setEnabled(true);
            }
        });

        // On create
        validateButton.addActionListener(new ActionListener() {
            
            SalleLight s = new SalleLight();
            
            @Override
            public void actionPerformed(ActionEvent e) {
                SalleLight newRoom = new SalleLight();
                newRoom.setLibelle(txtLibelle.getText());
                long num = Long.parseLong(txtPlaces.getText());
                newRoom.setPlaces(num);
                try {
                    s = salleModel.create(newRoom);
                    //r = (new Model<ReunionLight>(ReunionLight.class)).create(r);
                    
                    Model<AffectationLight> affectationModel = new Model<>(AffectationLight.class);
                            List<Equipement> selectedEquipements = new ArrayList<>();
                            
                            // Get selected users
                            for (int i = 0; i < listEquipements.size(); i++) {
                                if (formListEquipements.isIndexSelected(i)) {
                                    selectedEquipements.add(listEquipements.get(i));
                                }
                            }
                            /**
                             * Create each association
                             */
                           
                            for (Equipement u : selectedEquipements) {
                                System.out.println(newRoom.getId());
                                System.out.println(u.getId());
                                AffectationLight al = new AffectationLight(u.getId(), s.getId());
                                affectationModel.create(al);
                            }
                            
                    JOptionPane.showMessageDialog(null, "Salle créée avec succès");
                    // Close modal
                    dispose();
                       // Reload frame to replace data
                    Globals.reloadRoomsFrame();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erreur :\n" + ex.getMessage());
                }
            }
        });

        // On close frame
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Enable user frame
                Globals.roomsFrame.setEnabled(true);
            }
        });

        // Change icon
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/logo_64.png")));
    }

    /**
     * Load all equipements
     */
    public void loadFormData() {        
        try {
            this.listEquipements = equipementModel.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    /**
     * Load data from DB to fill comboboxes
     */
    private void loadEquipementsInJList() {
        
        formListEquipements.removeAll();

        for (Equipement u : this.listEquipements) {
            formListEquipements.add(u.getLibelle());
        }
        
        this.formListEquipements.setMultipleMode(true);
    }
    
    

    /*private void addWindowListener(WindowAdapter windowAdapter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void setIconImage(Image image) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setVisible(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
}
