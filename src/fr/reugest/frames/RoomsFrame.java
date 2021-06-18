package fr.reugest.frames;

import fr.reugest.frames.createmodal.RoomCreateModal;
import fr.reugest.main.Globals;
import fr.reugest.models.Affectation;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import fr.reugest.models.Equipement;
import fr.reugest.models.Salle;
import fr.reugest.models.Utilisateur;
import fr.reugest.models.light.AffectationLight;
import fr.reugest.models.light.ConcernerLight;
import fr.reugest.models.light.SalleLight;
import fr.thomas.orm.Model;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class RoomsFrame extends BaseFrame {

    private static final long serialVersionUID = -5481781563859385889L;

    private JTextField txtLibelle, txtPlaces;

    private Model<Salle> salleModel;
    private Model<Equipement> equipementModel;

    private List<Salle> listSalles;

    /**
     * Selected user
     */
    private Salle selectedSalle;
    private Affectation selectedAffectation;

    private String[] columns = new String[]{"Libelle", "Places", "Equipements"};
    private List<Equipement> listEquipements;
    private java.awt.List formListEquipements;

    public RoomsFrame() {
        super();
        // Store in global variables
        Globals.roomsFrame = this;
        this.setTitle("Gestion des salles");
        this.setLocationRelativeTo(null);

        /**
         * Init models
         */
        salleModel = new Model<Salle>(Salle.class);
        equipementModel = new Model<Equipement>(Equipement.class);

        // Load data
        this.loadFormData();
        // Load left and right panels
        this.loadLeftPanel();
        this.loadRightPanel();
    }

    /**
     * Load data from DB to fill comboboxes
     */
    public void loadFormData() {
        try {
            this.listSalles = salleModel.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            this.listEquipements = equipementModel.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Load left panel components (JTable)
     */
    private void loadLeftPanel() {

        // actual data for the table in a 2d array
        loadSallesInJTable();

        this.table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                validateButton.setEnabled(true);
                deleteButton.setEnabled(true);

                //selectedRoom = listSalles.get(table.getSelectedRow());
                selectedSalle = listSalles.get(table.getSelectedRow());
                // Fill form fields
                txtLibelle.setText(selectedSalle.getLibelle());
                txtPlaces.setText(selectedSalle.getPlaces().toString());
                // Change selection
                toggleSelectedEquipement();

                // Change state to EDITING
                state = BaseFrame.State.EDITING;
            }
        });

        // Make the JTable fill the panel
        table.setPreferredSize(new Dimension(pLeft.getWidth(), pLeft.getHeight()));
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.setFillsViewportHeight(true);
        JScrollPane js = new JScrollPane(table);
        js.setVisible(true);
        pLeft.add(js);

        // Set a border for better rendering
        this.pLeft.setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    /**
     * Load right panel components (Form)
     */
    private void loadRightPanel() {

        //loadEquipementsInJTable();
        // Set right panel
        this.pRight.setLayout(new GridLayout(10, 2, 25, 10));

        this.pRight.add(new JLabel("Libelle : ", JLabel.RIGHT));
        txtLibelle = new JTextField();
        this.pRight.add(txtLibelle);

        this.pRight.add(new JLabel("Places : ", JLabel.RIGHT));
        txtPlaces = new JTextField();
        this.pRight.add(txtPlaces);

        this.pRight.add(new JLabel("Equipements : ", JLabel.RIGHT));
        formListEquipements = new java.awt.List();
        this.pRight.add(formListEquipements);
        this.loadEquipementsInJList();

        /*for(Equipement e: listEquipements){
            this.formListEquipements.add(new JLabel("Equipement : ", JLabel.RIGHT));
            JCheckBox checkbox = new JCheckBox(e.getLibelle());
            this.formListEquipements.add(checkbox);
        }*/
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
                // Get properties from form
                String libelle = txtLibelle.getText();
                long num = Long.parseLong(txtPlaces.getText());
                SalleLight updatedSalle = new SalleLight();
                updatedSalle.setLibelle(libelle);
                try {
                    updatedSalle.setPlaces(Long.parseLong(txtPlaces.getText()));
                    updatedSalle.setId(selectedSalle.getId());
                    /**
                     * Create model
                     */
                    Model<SalleLight> model = new Model<SalleLight>(SalleLight.class);
                    try {
                        model.update(updatedSalle);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Erreur :\n" + ex.getMessage());
                    }

                    /**
                     * Update links
                     */
                    Model<AffectationLight> affectModel = new Model<>(AffectationLight.class);
                    List<Equipement> selectedEquipements = new ArrayList<>();
                    // Delete 
                    List<AffectationLight> existing = affectModel.query("SELECT * FROM affectation WHERE salle = ?", Arrays.asList(selectedSalle.getId()));
                    for (AffectationLight a : existing) {
                        affectModel.delete(a);
                    }

                    // Get selected equipements
                    for (int i = 0; i < listEquipements.size(); i++) {
                        if (formListEquipements.isIndexSelected(i)) {
                            selectedEquipements.add(listEquipements.get(i));
                        }
                    }
                    for (Equipement equipement : selectedEquipements) {
                        AffectationLight al = new AffectationLight(equipement.getId(), selectedSalle.getId());
                        affectModel.create(al);
                    }
                    JOptionPane.showMessageDialog(null, "La salle a bien été modifiée", "Information", JOptionPane.INFORMATION_MESSAGE);
                    
                    Globals.reloadRoomsFrame();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erreur : \nVeuillez entrer un nombre");
                }
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setEnabled(false);
                RoomCreateModal createModal = new RoomCreateModal();
                createModal.setVisible(true);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int input = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment supprimer l'enregistrement ?\n\nCette action est irréversible.", "Select an Option...",
                        JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                // If YES
                if (input == 0) {
                    try {
                        /*Model<AffectationLight> model2 = new Model<>(AffectationLight.class);
                        AffectationLight light2 = new AffectationLight(selectedSalle.getId());*/
                        Model<AffectationLight> affectationModel = new Model<>(AffectationLight.class);
                        List<AffectationLight> equipements = affectationModel.query("SELECT * FROM affectation WHERE salle = ?", Arrays.asList(selectedSalle.getId()));
                        for (AffectationLight equipement : equipements) {
                            affectationModel.delete(equipement);
                        }
                        Model<SalleLight> model = new Model<>(SalleLight.class);
                        SalleLight light = new SalleLight(selectedSalle.getId(),
                                selectedSalle.getLibelle(),
                                selectedSalle.getPlaces());
                        // Update in DB
                        model.delete(light);
                        JOptionPane.showMessageDialog(null, "Salle correctement supprimé.");
                        Globals.reloadRoomsFrame();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Erreur : " + ex.getMessage());
                    }
                }
            }
        });
    }

    /**
     * Load users in table
     */
    public void loadSallesInJTable() {

        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Salle s : this.listSalles) {

            long a = s.getId();
            try {

                List<Equipement> equipements = equipementModel.query("SELECT * FROM equipement WHERE id IN (SELECT equipement FROM affectation WHERE salle = ?)", Arrays.asList(a));

                String affichageEquipements = "";
                for (Equipement e : equipements) {
                    affichageEquipements += e.toString() + ", ";
                }
                if (affichageEquipements.length() > 2) {
                    affichageEquipements = affichageEquipements.substring(0, affichageEquipements.length() - 2);
                }

                tableModel.addRow(new Object[]{s.getLibelle(), s.getPlaces(), affichageEquipements});

            } catch (Exception ex) {
                ex.printStackTrace();

            }

        }
        this.table = new JTable(tableModel);

        /*JScrollPane pane = new JScrollPane(table);
        this.pLeft.add(pane);
        this.table.setPreferredScrollableViewportSize(new Dimension(100, 100));
        this.table.setFillsViewportHeight(true);*/
        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void loadEquipementsInJList() {

        formListEquipements.removeAll();

        for (Equipement u : this.listEquipements) {
            formListEquipements.add(u.getLibelle());
        }

        this.formListEquipements.setMultipleMode(true);
    }

    /**
     * Get selected users for selectedReunion
     */
    private void toggleSelectedEquipement() {
        Model<AffectationLight> model = new Model<AffectationLight>(AffectationLight.class);
        List<AffectationLight> affectList = new ArrayList<>();
        /**
         * reset selection
         */
        for (int i = 0; i < listEquipements.size(); i++) {
            formListEquipements.deselect(i);
        }

        // Get associations
        try {
            affectList = model.query("SELECT * FROM affectation WHERE salle = ?", Arrays.asList(selectedSalle.getId()));
        } catch (Exception ex) {
            Logger.getLogger(PlanningFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Toggle each association
        for (AffectationLight a : affectList) {
            formListEquipements.select(getEquipementIndexInList(a.getEquipement()));
        }
    }

    /**
     * Get the index of a specified equipement in the userList
     *
     * @return
     */
    private int getEquipementIndexInList(Long id) {
        for (int i = 0; i < listEquipements.size(); i++) {
            if (listEquipements.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}
