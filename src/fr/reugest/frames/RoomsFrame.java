package fr.reugest.frames;

import fr.reugest.frames.createmodal.UserCreateModal;
import fr.reugest.main.Globals;
import static fr.reugest.main.Globals.roomsFrame;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import fr.reugest.models.Droit;
import fr.reugest.models.Fonction;
import fr.reugest.models.Salle;
import fr.reugest.models.Service;
import fr.reugest.models.Utilisateur;
import fr.reugest.models.light.SalleLight;
import fr.reugest.models.light.UtilisateurLight;
import fr.thomas.orm.Model;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class RoomsFrame extends BaseFrame {

    private static final long serialVersionUID = -5481781563859385889L;

    private JTextField txtLibelle, txtPlaces;

    private Model<Salle> salleModel;

    private List<Salle> listSalles;

    /**
     * Selected user
     */
    private Salle selectedSalle;

    private String[] columns = new String[]{"Libelle", "Places"};

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
    }

    /**
     * Load left panel components (JTable)
     */
    private void loadLeftPanel() {

        // actual data for the table in a 2d array
        loadUsersInJTable();

        this.table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                validateButton.setEnabled(true);

                selectedSalle = listSalles.get(table.getSelectedRow());
                // Fill form fields
                txtLibelle.setText(selectedSalle.getLibelle());
                txtPlaces.setText(selectedSalle.getPlaces().toString());
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
        // Set right panel
        this.pRight.setLayout(new GridLayout(10, 2, 25, 10));

        this.pRight.add(new JLabel("Libelle : ", JLabel.RIGHT));
        txtLibelle = new JTextField();
        this.pRight.add(txtLibelle);

        this.pRight.add(new JLabel("Places : ", JLabel.RIGHT));
        txtPlaces = new JTextField();
        this.pRight.add(txtPlaces);
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
                String libelle = txtLibelle.getText();
                //System.out.println(Nom);
                //Long places = txtPlaces.getText();
                //System.out.println(Prenom);
                //System.out.println(Email);
                //System.out.println(Droit);
                //utilisateurModel.update(selectedSalle)
                System.out.println(selectedSalle);
                SalleLight updatedSalle = new SalleLight();
                updatedSalle.setLibelle(libelle);
                try {
                    updatedSalle.setPlaces(Long.parseLong(txtPlaces.getText()));

                    updatedSalle.setId(selectedSalle.getId());
                    Model model = new Model<UtilisateurLight>(UtilisateurLight.class);
                    try {
                        model.update(updatedSalle);
                        JOptionPane.showMessageDialog(null, "Utilisateur mofifié avec succès");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Erreur :\n" + ex.getMessage());
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur : \nVeuillez entrer un nombre");
                }
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setEnabled(false);
                UserCreateModal createModal = new UserCreateModal();
                createModal.setVisible(true);
            }
        });
    }

    /**
     * Load users in table
     */
    public void loadUsersInJTable() {

        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Salle u : this.listSalles) {
            tableModel
                    .addRow(new Object[]{u.getLibelle(), u.getPlaces()});
        }
        this.table = new JTable(tableModel);
        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * Return the function of the list from its id
     *
     * @param id
     * @return
     */
}
