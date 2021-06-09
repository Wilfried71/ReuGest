package fr.reugest.frames;

import com.github.lgooddatepicker.components.CalendarPanel;
import com.github.lgooddatepicker.components.DatePicker;
import fr.reugest.main.Globals;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import fr.reugest.models.Salle;
import fr.reugest.models.Utilisateur;
import fr.thomas.orm.Model;
import fr.thomas.swing.JScheduler;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class PlanningFrame extends BaseFrame {

    private static final long serialVersionUID = -5481781563859385889L;

    private JScheduler planning;
    
    private Model<Salle> salleModel;

    private List<Salle> listSalle;

    /**
     * Selected user
     */
    private Utilisateur selectedUser;

    public PlanningFrame() {
        super();
        // Store in global variables
        Globals.planningFrame = this;
        this.setTitle("Gestion des utilisateurs");
        this.setLocationRelativeTo(null);

        /**
         * Init models
         */
        salleModel = new Model<Salle>(Salle.class);

        // Load data
        this.loadFormData();
        // Load left and right panels
        this.loadLeftPanel();
        this.loadRightPanel();
    }

    /**
     * Load data from DB
     */
    public void loadFormData() {
        try {
            this.listSalle = salleModel.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Load left panel components (JTable)
     */
    private void loadLeftPanel() {
        this.planning = new JScheduler(Arrays.asList(), Color.RED);
        pLeft.add(this.planning);
        // Set a border for better rendering
        this.pLeft.setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    /**
     * Load right panel components (Form)
     */
    private void loadRightPanel() {
        // Set right panel
        this.pRight.setLayout(new GridLayout(10, 2, 25, 10));
        this.pRight.add(new DatePicker());
        /**
         * Create empty JLabel to fill
         */
        this.pRight.add(new JLabel());
        this.pRight.add(new JLabel());
        this.pRight.add(new JLabel());
        this.pRight.add(new JLabel());
        this.pRight.add(new JLabel());
        this.pRight.add(new JLabel());
        this.pRight.add(new JLabel());
        this.pRight.add(this.validateButton);
        this.validateButton.setEnabled(false);
        this.pRight.setBorder(new EmptyBorder(10, 10, 10, 10));
        // Validate button action listener

        // Add button event listener
        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*setEnabled(false);
                UserCreateModal createModal = new UserCreateModal();
                createModal.setVisible(true);*/
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               /* int input = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment supprimer l'enregistrement ?\n\nCette action est irréversible.", "Select an Option...",
                        JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                // If YES
                if (input == 0) {
                    try {
                        Model<UtilisateurLight> model = new Model<>(UtilisateurLight.class);
                        UtilisateurLight light = new UtilisateurLight(selectedUser.getId(),
                                selectedUser.getNom(), selectedUser.getPrenom(), selectedUser.getEmail(),
                                selectedUser.getPassword(), selectedUser.getDroit().getId(),
                                selectedUser.getService().getId(), selectedUser.getFonction().getId(), true);
                        // Update in DB
                        model.update(light);
                        JOptionPane.showMessageDialog(null, "Utilisateur correctement supprimé.");
                        Globals.reloadUsersFrame();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Erreur : " + ex.getMessage());
                    }
                }*/
            }
        });
    }
}
