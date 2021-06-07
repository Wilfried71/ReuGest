/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.reugest.frames.createmodal;

import fr.reugest.main.Globals;
import fr.reugest.models.Droit;
import fr.reugest.models.Fonction;
import fr.reugest.models.Service;
import fr.reugest.models.Utilisateur;
import fr.thomas.orm.Model;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author tpeyr
 */
public class UserCreateModal extends JFrame{
    
    private JTextField txtNom, txtPrenom, txtEmail;
    private JComboBox<Object> cboFonction, cboService, cboDroit;

    private Model<Fonction> fonctionModel;
    private Model<Service> serviceModel;
    private Model<Droit> droitModel;
    private Model<Utilisateur> utilisateurModel;

    private List<Fonction> listFonctions;
    private List<Service> listServices;
    private List<Droit> listDroits;
    private List<Utilisateur> listUtilisateurs;
    
    private JButton validateButton, cancelButton;
    
    private JPanel panel;
    
    public UserCreateModal() {
        super("Créer un utilisateur");
        this.panel = new JPanel();
        this.setContentPane(panel);
        // Set right panel
        this.panel.setLayout(new GridLayout(9, 2, 25, 10));

        this.panel.add(new JLabel("Nom : ", JLabel.RIGHT));
        txtNom = new JTextField();
        this.panel.add(txtNom);

        this.panel.add(new JLabel("Prénom : ", JLabel.RIGHT));
        txtPrenom = new JTextField();
        this.panel.add(txtPrenom);

        this.panel.add(new JLabel("Email : ", JLabel.RIGHT));
        txtEmail = new JTextField();
        this.panel.add(txtEmail);

        this.panel.add(new JLabel("Fonction : ", JLabel.RIGHT));
        cboFonction = new JComboBox<>(/*listFonctions.toArray()*/);
        this.panel.add(cboFonction);

        this.panel.add(new JLabel("Service : ", JLabel.RIGHT));
        cboService = new JComboBox<>(/*listServices.toArray()*/);
        this.panel.add(cboService);

        this.panel.add(new JLabel("Droit : ", JLabel.RIGHT));
        cboDroit = new JComboBox<>(/*listDroits.toArray()*/);
        this.panel.add(cboDroit);
        /**
         * Create empty JLabel to fill
         */
        this.panel.add(new JLabel());
        this.panel.add(new JLabel());
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
        
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close modal
                dispose();
                // Enable user frame
                Globals.usersFrame.setEnabled(true);
            }
        });
        
        // Change icon
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/logo_64.png")));
    }
}
