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
import fr.reugest.models.light.UtilisateurLight;
import fr.thomas.orm.Model;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author tpeyr
 */
public class UserCreateModal extends JFrame {

    private JTextField txtNom, txtPrenom, txtEmail;
    private JComboBox<Object> cboFonction, cboService, cboDroit;
    private JPasswordField txtPassword;

    private Model<Fonction> fonctionModel;
    private Model<Service> serviceModel;
    private Model<Droit> droitModel;
    private Model<UtilisateurLight> utilisateurModel;

    private List<Fonction> listFonctions;
    private List<Service> listServices;
    private List<Droit> listDroits;

    private JButton validateButton, cancelButton;

    private JPanel panel;

    public UserCreateModal() {
        super("Créer un utilisateur");

        /**
         * Init models
         */
        fonctionModel = new Model<Fonction>(Fonction.class);
        serviceModel = new Model<Service>(Service.class);
        droitModel = new Model<Droit>(Droit.class);
        utilisateurModel = new Model<UtilisateurLight>(UtilisateurLight.class);

        // Load data
        this.loadFormData();

        this.panel = new JPanel();
        this.setContentPane(panel);
        // Set right panel
        this.panel.setLayout(new GridLayout(10, 2, 25, 10));

        this.panel.add(new JLabel("Nom : ", JLabel.RIGHT));
        txtNom = new JTextField();
        this.panel.add(txtNom);

        this.panel.add(new JLabel("Prénom : ", JLabel.RIGHT));
        txtPrenom = new JTextField();
        this.panel.add(txtPrenom);

        this.panel.add(new JLabel("Email : ", JLabel.RIGHT));
        txtEmail = new JTextField();
        this.panel.add(txtEmail);

        this.panel.add(new JLabel("Mot de passe : ", JLabel.RIGHT));
        txtPassword = new JPasswordField();
        this.panel.add(txtPassword);

        this.panel.add(new JLabel("Fonction : ", JLabel.RIGHT));
        cboFonction = new JComboBox<>(listFonctions.toArray());
        this.panel.add(cboFonction);

        this.panel.add(new JLabel("Service : ", JLabel.RIGHT));
        cboService = new JComboBox<>(listServices.toArray());
        this.panel.add(cboService);

        this.panel.add(new JLabel("Droit : ", JLabel.RIGHT));
        cboDroit = new JComboBox<>(listDroits.toArray());
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

        // On cancel creation
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close modal
                dispose();
                // Enable user frame
                Globals.usersFrame.setEnabled(true);
            }
        });

        // On create
        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UtilisateurLight newUser = new UtilisateurLight();
                newUser.setEmail(txtEmail.getText());
                newUser.setNom(txtNom.getText());
                newUser.setPrenom(txtPrenom.getText());
                newUser.setPassword(new String(txtPassword.getPassword()));
                newUser.setIdDroit(((Droit) cboDroit.getSelectedItem()).getId());
                newUser.setIdFonction(((Fonction) cboFonction.getSelectedItem()).getId());
                newUser.setIdService(((Service) cboService.getSelectedItem()).getId());
                try {
                    utilisateurModel.create(newUser);
                    
                    JOptionPane.showMessageDialog(null, "Utilisateur créé avec succès");
                    // Close modal
                    dispose();
                       // Reload frame to replace data
                    Globals.reloadUsersFrame();
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
                Globals.usersFrame.setEnabled(true);
            }
        });

        // Change icon
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/logo_64.png")));
    }

    /**
     * Load data from DB to fill comboboxes
     */
    private void loadFormData() {
        try {
            this.listFonctions = fonctionModel.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            this.listServices = serviceModel.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            this.listDroits = droitModel.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
