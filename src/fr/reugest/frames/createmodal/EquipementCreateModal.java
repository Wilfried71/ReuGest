/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.reugest.frames.createmodal;

import fr.reugest.main.Globals;
import fr.reugest.models.Equipement;
import fr.thomas.orm.Model;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author tpeyr
 */
public class EquipementCreateModal extends JFrame {

    private JTextField txtLibelle;

    private Model<Equipement> equipementModel;


    private JButton validateButton, cancelButton;

    private JPanel panel;

    /**
     * Constructor
     */
    public EquipementCreateModal() {
        super("Créer un équipement");

        /**
         * Init model
         */
        equipementModel = new Model<Equipement>(Equipement.class);

        this.panel = new JPanel();
        this.setContentPane(panel);
        // Set right panel
        this.panel.setLayout(new GridLayout(5, 2, 25, 10));

        /**
         * Create empty JLabel to fill
         */
        this.panel.add(new JLabel("Libellé : ", JLabel.RIGHT));
        txtLibelle = new JTextField();
        this.panel.add(txtLibelle);
        this.panel.add(new JLabel());
        this.panel.add(new JLabel());
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
        this.setSize(360, 200);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        // On cancel creation
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close modal
                dispose();
                // Enable user frame
                Globals.equipementFrame.setEnabled(true);
            }
        });

        // On create
        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try {
                    equipementModel.create(new Equipement(null,txtLibelle.getText()));
                    
                    JOptionPane.showMessageDialog(null, "Équipement créé avec succès");
                    // Close modal
                    dispose();
                       // Reload frame to replace data
                    Globals.reloadEquipementFrame();
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
                Globals.equipementFrame.setEnabled(true);
            }
        });

        // Change icon
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/logo_64.png")));
    }
}
