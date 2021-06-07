package fr.reugest.frames;

import fr.reugest.frames.createmodal.EquipementCreateModal;
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
import fr.reugest.models.light.AffectationLight;
import fr.thomas.orm.Model;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class EquipementFrame extends BaseFrame {

    private static final long serialVersionUID = -5481781563859385889L;

    private JTextField txtLibelle;

    private Model<Equipement> equipementModel;

    private List<Equipement> listEquipement;

    /**
     * Selected user
     */
    private Equipement selectedEquipement;

    private String[] columns = new String[]{"Libellé"};

    public EquipementFrame() {
        super();
        // Store in global variables
        Globals.equipementFrame = this;
        this.setTitle("Gestion des équipements");
        this.setLocationRelativeTo(null);

        /**
         * Init model
         */
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
            this.listEquipement = equipementModel.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Load left panel components (JTable)
     */
    private void loadLeftPanel() {

        // actual data for the table in a 2d array
        loadEquipementsInJTable();

        this.table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                // If not reloading
                if (table.getSelectedRow() != -1) {
                    // Enable action buttons
                    validateButton.setEnabled(true);
                    deleteButton.setEnabled(true);

                    selectedEquipement = listEquipement.get(table.getSelectedRow());
                    // Fill form fields
                    txtLibelle.setText(selectedEquipement.getLibelle());
                }

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
        // Set right panel
        this.pRight.setLayout(new GridLayout(10, 2, 25, 10));

        this.pRight.add(new JLabel("Libellé : ", JLabel.RIGHT));
        txtLibelle = new JTextField();
        this.pRight.add(txtLibelle);

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
                // Instanciate Model
                Model model = new Model<Equipement>(Equipement.class);
                try {
                    // Update equipement
                    model.update(new Equipement(selectedEquipement.getId(), txtLibelle.getText()));

                    JOptionPane.showMessageDialog(null, "Équipement modifié avec succès");

                    // Reload frame to replace data
                    Globals.reloadEquipementFrame();

                } catch (Exception ex) {
                    Logger.getLogger(EquipementFrame.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Erreur :\n" + ex.getMessage());
                }
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setEnabled(false);
                EquipementCreateModal createModal = new EquipementCreateModal();
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
                        // Delete relational dependencies
                        Model affectationModel = new Model<AffectationLight>(AffectationLight.class);
                        List<AffectationLight> affectations = affectationModel.query("SELECT * FROM affectation WHERE equipement = ?", Arrays.asList(selectedEquipement.getId()));

                        // If there is at least one association with this equipement
                        if (!affectations.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Cet équipement ne peut pas être supprimmé\ncar il est lié à au moins une salle.","Erreur",JOptionPane.ERROR_MESSAGE);
                        } else {
                            // Delete object
                            (new Model<>(Equipement.class)).delete(selectedEquipement);

                            JOptionPane.showMessageDialog(null, "Équipement correctement supprimé.");
                        }
                        // Reload frame
                        Globals.reloadEquipementFrame();
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
    public void loadEquipementsInJTable() {

        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Equipement e : this.listEquipement) {
            tableModel.addRow(new Object[]{e.getLibelle()});
        }
        this.table = new JTable(tableModel);
        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
}
