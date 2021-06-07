package fr.reugest.frames;

import fr.reugest.frames.createmodal.UserCreateModal;
import fr.reugest.main.Globals;
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
import fr.reugest.models.Service;
import fr.reugest.models.Utilisateur;
import fr.reugest.models.light.UtilisateurLight;
import fr.thomas.orm.Model;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class UsersFrame extends BaseFrame {

    private static final long serialVersionUID = -5481781563859385889L;

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

    /**
     * Selected user
     */
    private Utilisateur selectedUser;

    private String[] columns = new String[]{"Nom", "Prénom", "Email", "Fonction", "Service"};

    public UsersFrame() {
        super();
        // Store in global variables
        Globals.usersFrame = this;
        this.setTitle("Gestion des utilisateurs");
        this.setLocationRelativeTo(null);

        /**
         * Init models
         */
        fonctionModel = new Model<Fonction>(Fonction.class);
        serviceModel = new Model<Service>(Service.class);
        droitModel = new Model<Droit>(Droit.class);
        utilisateurModel = new Model<Utilisateur>(Utilisateur.class);

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

        try {
            this.listUtilisateurs = utilisateurModel.findAll();
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
                // If not reloading
                if (table.getSelectedRow() != -1) {
                    validateButton.setEnabled(true);

                    selectedUser = listUtilisateurs.get(table.getSelectedRow());
                    // Fill form fields
                    txtEmail.setText(selectedUser.getEmail());
                    txtNom.setText(selectedUser.getNom());
                    txtPrenom.setText(selectedUser.getPrenom());
                    cboFonction
                            .setSelectedIndex(listFonctions.indexOf(getFonctionFromId(selectedUser.getFonction().getId())));
                    cboDroit.setSelectedIndex(listDroits.indexOf(getDroitFromId(selectedUser.getDroit().getId())));
                    cboService.setSelectedIndex(listServices.indexOf(getServiceFromId(selectedUser.getService().getId())));
                    // Change state to EDITING
                    state = State.EDITING;
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

        this.pRight.add(new JLabel("Nom : ", JLabel.RIGHT));
        txtNom = new JTextField();
        this.pRight.add(txtNom);

        this.pRight.add(new JLabel("Prénom : ", JLabel.RIGHT));
        txtPrenom = new JTextField();
        this.pRight.add(txtPrenom);

        this.pRight.add(new JLabel("Email : ", JLabel.RIGHT));
        txtEmail = new JTextField();
        this.pRight.add(txtEmail);

        this.pRight.add(new JLabel("Fonction : ", JLabel.RIGHT));
        cboFonction = new JComboBox<>(listFonctions.toArray());
        this.pRight.add(cboFonction);

        this.pRight.add(new JLabel("Service : ", JLabel.RIGHT));
        cboService = new JComboBox<>(listServices.toArray());
        this.pRight.add(cboService);

        this.pRight.add(new JLabel("Droit : ", JLabel.RIGHT));
        cboDroit = new JComboBox<>(listDroits.toArray());
        this.pRight.add(cboDroit);
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
                // Get properties
                String nom = txtNom.getText();
                String prenom = txtPrenom.getText();
                String email = txtEmail.getText();
                Fonction fonction = (Fonction) cboFonction.getSelectedItem();
                Service service = (Service) cboService.getSelectedItem();
                Droit droit = (Droit) cboDroit.getSelectedItem();
                // Instanciate user light
                UtilisateurLight updatedUser = new UtilisateurLight();
                // Set properties
                updatedUser.setNom(nom);
                updatedUser.setPrenom(prenom);
                updatedUser.setEmail(email);
                updatedUser.setIdFonction(fonction.getId());
                updatedUser.setIdService(service.getId());
                updatedUser.setIdDroit(droit.getId());
                updatedUser.setId(selectedUser.getId());
                updatedUser.setPassword(selectedUser.getPassword());
                // Instanciate Model
                Model model = new Model<UtilisateurLight>(UtilisateurLight.class);
                try {
                    // Update user
                    model.update(updatedUser);
                    
                    JOptionPane.showMessageDialog(null, "Utilisateur mofifié avec succès");

                    // Reload frame to replace data
                    Globals.reloadUsersFrame();
                    
                } catch (Exception ex) {
                    Logger.getLogger(UsersFrame.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Erreur :\n" + ex.getMessage());
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

        for (Utilisateur u : this.listUtilisateurs) {
            tableModel.addRow(new Object[]{u.getNom(), u.getPrenom(), u.getEmail(), u.getFonction(), u.getService()});
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
    public Fonction getFonctionFromId(Long id) {
        for (Fonction f : this.listFonctions) {
            if (f.getId().equals(id)) {
                return f;
            }
        }
        return null;
    }

    public Droit getDroitFromId(Long id) {
        for (Droit d : this.listDroits) {
            if (d.getId().equals(id)) {
                return d;
            }
        }
        return null;
    }

    public Service getServiceFromId(Long id) {
        for (Service s : this.listServices) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }
}
