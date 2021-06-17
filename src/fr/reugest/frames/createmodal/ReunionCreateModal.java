/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.reugest.frames.createmodal;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;
import fr.reugest.main.Globals;
import fr.reugest.models.Reunion;
import fr.reugest.models.Salle;
import fr.reugest.models.Utilisateur;
import fr.reugest.models.light.ConcernerLight;
import fr.reugest.models.light.ReunionLight;
import fr.thomas.orm.Model;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
public class ReunionCreateModal extends JFrame {

    private DatePicker datePicker;
    private TimePicker timePickerDebut, timePickerFin;
    private JComboBox<Salle> cboSalles;
    private java.awt.List formUserList;
    private JTextField txtMotif;
    private JButton validateButton, cancelButton;
    private JPanel panel;
    
    
    private Model<Utilisateur> userModel;
    private Model<Salle> salleModel;
    private Model<Reunion> reunionModel;

    private List<Salle> listSalle;
    private List<Utilisateur> userList;

    

    private Salle selectedSalle;

    public ReunionCreateModal() {
        super("Créer une réunion");

        /**
         * Init models
         */
        userModel = new Model<>(Utilisateur.class);
        salleModel = new Model<>(Salle.class);
        reunionModel = new Model<>(Reunion.class);

        // Load data
        this.loadFormData();

        this.panel = new JPanel();
        this.setContentPane(panel);

        // Setc panel
        this.panel.setLayout(new GridLayout(10, 2, 25, 10));

        // Motif
        this.panel.add(new JLabel("Motif : "));
        this.txtMotif = new JTextField();
        this.panel.add(txtMotif);

        // Datepicker
        this.datePicker = new DatePicker();
        this.panel.add(new JLabel("Date : "));
        this.panel.add(datePicker);

        // Time pickers
        this.timePickerDebut = new TimePicker();
        this.panel.add(new JLabel("Heure de début : "));
        this.panel.add(timePickerDebut);

        this.timePickerFin = new TimePicker();
        this.panel.add(new JLabel("Heure de fin : "));
        this.panel.add(timePickerFin);

        // Users
        this.panel.add(new JLabel("Utilisateurs"));
        formUserList = new java.awt.List();
        // Allow multiple selection
        formUserList.setMultipleMode(true);
        this.fillUserList();
        this.panel.add(formUserList);

        // Salle
        this.panel.add(new JLabel("Salle : "));
        cboSalles = new JComboBox(this.listSalle.toArray());
        this.panel.add(cboSalles);

        /**
         * Create empty JLabel to fill
         */
        this.panel.add(new JLabel());
        this.panel.add(new JLabel());
        this.panel.add(new JLabel());
        this.panel.add(new JLabel());
        this.panel.add(new JLabel());
        this.panel.add(new JLabel());

        // Create buttons
        this.cancelButton = new JButton("Annuler");
        this.validateButton = new JButton("Valider");
        this.panel.setBorder(new EmptyBorder(10, 10, 10, 10));
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
                Globals.planningFrame.setEnabled(true);
            }
        });

        // On create
        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 * CREATE NEW ENTITY AND FILL FIELDS
                 */
                ReunionLight r = new ReunionLight();

                // Get start date
                Calendar debut = Calendar.getInstance();
                debut.set(Calendar.YEAR, datePicker.getDate().getYear());
                debut.set(Calendar.MONTH, datePicker.getDate().getMonthValue() - 1);
                debut.set(Calendar.DAY_OF_MONTH, datePicker.getDate().getDayOfMonth());
                debut.set(Calendar.HOUR_OF_DAY, timePickerDebut.getTime().getHour());
                debut.set(Calendar.MINUTE, timePickerDebut.getTime().getMinute());
                debut.set(Calendar.SECOND, 0);

                // Get end date
                Calendar fin = Calendar.getInstance();
                fin.set(Calendar.YEAR, datePicker.getDate().getYear());
                fin.set(Calendar.MONTH, datePicker.getDate().getMonthValue() - 1);
                fin.set(Calendar.DAY_OF_MONTH, datePicker.getDate().getDayOfMonth());
                fin.set(Calendar.HOUR_OF_DAY, timePickerFin.getTime().getHour());
                fin.set(Calendar.MINUTE, timePickerFin.getTime().getMinute());
                fin.set(Calendar.SECOND, 0);

                // Set attributes
                r.setMotif(txtMotif.getText());
                r.setDebut(debut.getTime());
                r.setFin(fin.getTime());
                r.setIsValid(true);
                r.setIdSalle(((Salle) cboSalles.getSelectedItem()).getId());

                if (!debut.after(fin)) {
                    // Get events that are overlaped
                    List<Reunion> overlapEvents = new ArrayList<>();
                    try {
                        overlapEvents = reunionModel.query(
                                "SELECT * FROM Reunion WHERE fin > ? AND debut < ? and isValid=? AND salle = ? AND id != ?",
                                Arrays.asList(debut.getTime(), fin.getTime(), true, r.getIdSalle(), r.getId()));
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Erreur : " + ex.getMessage());
                    }
                    // If ther is no overlap
                    if (overlapEvents.isEmpty()) {
                        try {
                            // Update reunionLight
                            r = (new Model<ReunionLight>(ReunionLight.class)).create(r);

                            /**
                             * Update links
                             */
                            Model<ConcernerLight> concernModel = new Model<>(ConcernerLight.class);
                            List<Utilisateur> selectedUsers = new ArrayList<>();
                            
                            // Get selected users
                            for (int i = 0; i < userList.size(); i++) {
                                if (formUserList.isIndexSelected(i)) {
                                    selectedUsers.add(userList.get(i));
                                }
                            }
                            /**
                             * Create each association
                             */
                            for (Utilisateur u : selectedUsers) {
                                ConcernerLight cl = new ConcernerLight(u.getId(), r.getId());
                                concernModel.create(cl);
                            }
                            JOptionPane.showMessageDialog(null, "La réunion a bien été créée.", "Information", JOptionPane.INFORMATION_MESSAGE);

                            // Reload events
                            Globals.planningFrame.loadEvents();
                            Globals.planningFrame.setEnabled(true);
                            
                            // Close
                            dispose();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Erreur : " + ex.getMessage());
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Vous ne pouvez pas déplacer cette réunion à ce moment là.\nUne autre réunion occupe déjà ce créneau.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Le début ne peut pas être après la fin.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
                
            }
        });

        // On close frame
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Enable planning frame
                Globals.planningFrame.setEnabled(true);
            }
        });

        // Change icon
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/logo_64.png")));
    }

    public void fillUserList() {
        formUserList.removeAll();
        for (Utilisateur u : userList) {
            formUserList.add(u.toString());
        }
    }

    /**
     * Load data from DB
     */
    public void loadFormData() {
        try {
            this.listSalle = salleModel.findAll();
            // On init, select the first element
            selectedSalle = this.listSalle.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            userList = userModel.query("SELECT * FROM utilisateur WHERE isDeleted = ? ORDER BY nom,prenom", Arrays.asList(false));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
