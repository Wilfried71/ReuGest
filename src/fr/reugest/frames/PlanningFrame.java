package fr.reugest.frames;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;
import fr.reugest.frames.createmodal.ReunionCreateModal;
import fr.reugest.main.Globals;
import fr.reugest.models.Affectation;
import fr.reugest.models.Concerner;
import fr.reugest.models.Reunion;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import fr.reugest.models.Salle;
import fr.reugest.models.Utilisateur;
import fr.reugest.models.light.ConcernerLight;
import fr.reugest.models.light.ReunionLight;
import fr.reugest.models.light.UtilisateurLight;
import fr.thomas.orm.Model;
import fr.thomas.swing.JEvent;
import fr.thomas.swing.JScheduler;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PlanningFrame extends BaseFrame {

    private static final long serialVersionUID = -5481781563859385889L;

    /**
     * Models
     */
    private Model<Salle> salleModel;
    private Model<Reunion> reunionModel;
    private Model<Utilisateur> userModel;
    private Model<Concerner> concernerModel;

    /**
     * Lists
     */
    private List<Salle> listSalle;
    private List<Reunion> listReunion;
    private List<Utilisateur> listUsers;

    /**
     * Graphic components
     */
    private DatePicker datePicker;
    private TimePicker timePickerDebut, timePickerFin;
    private JScheduler planning;
    private JPanel schedulerHeaderPanel;
    private JButton btnPrev, btnNext, btnRefresh;
    private JComboBox cboSallesInHeader, cboSalles;
    private java.awt.List formUserList;
    private JTextField txtMotif;

    /**
     * Selected user
     */
    private Reunion selectedReunion;
    // Salle selected in header
    private Salle selectedSalle;

    public PlanningFrame() {
        super();
        // Store in global variables
        Globals.planningFrame = this;
        setMinimumSize(new Dimension(1120, 640));
        this.setTitle("Gestion des réservations");
        this.setLocationRelativeTo(null);

        /**
         * Init models
         */
        salleModel = new Model<Salle>(Salle.class);
        reunionModel = new Model<Reunion>(Reunion.class);
        userModel = new Model<Utilisateur>(Utilisateur.class);

        // Load data
        this.loadFormData();
        // Load left and right panels
        this.loadLeftPanel();
        this.loadRightPanel();

        // After frame loaded
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                loadEvents();
            }
        });
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
            listUsers = userModel.query("SELECT * FROM utilisateur WHERE isDeleted = ? ORDER BY nom,prenom", Arrays.asList(false));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Load left panel components (JTable)
     */
    private void loadLeftPanel() {
        // Create layouts
        pLeft.setLayout(new BorderLayout());
        // Add items
        schedulerHeaderPanel = new JPanel(new FlowLayout());
        this.btnPrev = new JButton("<<");
        schedulerHeaderPanel.add(this.btnPrev);
        this.cboSallesInHeader = new JComboBox(this.listSalle.toArray());
        schedulerHeaderPanel.add(cboSallesInHeader);
        this.btnNext = new JButton(">>");
        schedulerHeaderPanel.add(this.btnNext);
        this.btnRefresh = new JButton(new ImageIcon(getClass().getResource("/refresh.png")));
        this.btnRefresh.setToolTipText("Actualiser");
        schedulerHeaderPanel.add(this.btnRefresh);
        pLeft.add(schedulerHeaderPanel, BorderLayout.NORTH);

        // Planning
        this.planning = new JScheduler(Arrays.asList(), Color.RED);
        pLeft.add(this.planning, BorderLayout.CENTER);
        // Set a border for better rendering
        this.pLeft.setBorder(new EmptyBorder(10, 10, 10, 10));

        // EventHandler on buttons
        btnPrev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                planning.previousWeek();
                loadEvents();
            }
        });
        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                planning.nextWeek();
                loadEvents();
            }
        });
        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadEvents();
            }
        });
    }

    /**
     * Load right panel components (Form)
     */
    private void loadRightPanel() {
        // Set right panel
        this.pRight.setLayout(new GridLayout(10, 2, 25, 10));

        // Motif
        this.pRight.add(new JLabel("Motif : "));
        this.txtMotif = new JTextField();
        this.pRight.add(txtMotif);

        // Datepicker
        this.datePicker = new DatePicker();
        this.pRight.add(new JLabel("Date : "));
        this.pRight.add(datePicker);

        // Time pickers
        this.timePickerDebut = new TimePicker();
        this.pRight.add(new JLabel("Heure de début : "));
        this.pRight.add(timePickerDebut);

        this.timePickerFin = new TimePicker();
        this.pRight.add(new JLabel("Heure de fin : "));
        this.pRight.add(timePickerFin);

        // Users
        this.pRight.add(new JLabel("Utilisateurs"));
        formUserList = new java.awt.List();
        // Allow multiple selection
        formUserList.setMultipleMode(true);
        this.fillUserList();
        this.pRight.add(formUserList);

        // Salle
        this.pRight.add(new JLabel("Salle : "));
        cboSalles = new JComboBox(this.listSalle.toArray());
        this.pRight.add(cboSalles);

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

        // Add rights access
        this.addRightsToForm();

        // Add button event listener
        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateEvent();
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setEnabled(false);
                ReunionCreateModal createModal = new ReunionCreateModal();
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
                        // Delete in DB
                        Model<ConcernerLight> concernModel = new Model<>(ConcernerLight.class);
                        // Delete links
                        List<ConcernerLight> existing = concernModel.query("SELECT * FROM concerner WHERE reunion = ?", Arrays.asList(selectedReunion.getId()));
                        for (ConcernerLight c : existing) {
                            concernModel.delete(c);
                        }
                        reunionModel.delete(selectedReunion);

                        JOptionPane.showMessageDialog(null, "Réunion correctement supprimée.");
                        /**
                         * Reset fields
                         */
                        selectedReunion = null;
                        validateButton.setEnabled(false);
                        deleteButton.setEnabled(false);
                        txtMotif.setText("");
                        datePicker.setDate(null);
                        timePickerDebut.setTime(null);
                        timePickerFin.setTime(null);
                        for (int i = 0; i < listUsers.size(); i++) {
                            formUserList.deselect(i);
                        }
                        loadEvents();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Erreur : " + ex.getMessage());
                    }
                }
            }
        });
        // On change 
        cboSallesInHeader.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                selectedSalle = (Salle) cboSallesInHeader.getSelectedItem();
                loadEvents();
            }
        });
    }

    public void addRightsToForm() {
        // Simple user
        if (Globals.user.getDroit().getId().equals(1L)) {
            addButton.setVisible(false);
            deleteButton.setVisible(false);
            validateButton.setVisible(false);
            txtMotif.setEnabled(false);
            datePicker.setEnabled(false);
            timePickerDebut.setEnabled(false);
            timePickerFin.setEnabled(false);
            cboSalles.setEnabled(false);
            formUserList.setEnabled(false);
        }
    }

    /**
     * Load events on this week, based on planning date and salle
     */
    public void loadEvents() {
        Calendar lundi = planning.getStartOfWeek();
        Calendar dimanche = planning.getEndOfWeek();
        //System.out.println("De " + lundi.getTime() + " à " + dimanche.getTime());
        try {
            this.listReunion = this.reunionModel.query(
                    "SELECT * FROM Reunion WHERE fin > ? AND debut < ? and isValid=? AND salle = ?",
                    Arrays.asList(lundi.getTime(), dimanche.getTime(), true, selectedSalle.getId()));
        } catch (Exception ex) {
            listReunion = new ArrayList<Reunion>();
            JOptionPane.showMessageDialog(null, "Erreur : " + ex.getMessage());
        }
        // Reload events in planning
        planning.setEvents(getJEventsFromReunion(listReunion));
    }

    /**
     * Convert a Reunion list to JEvent list to be displayed on planning
     *
     * @param list
     * @return
     */
    private List<JEvent> getJEventsFromReunion(List<Reunion> list) {
        List<JEvent> events = new ArrayList<>();

        for (Reunion r : list) {
            try {
                // Instanciate event
                JEvent evt = new JEvent(Color.BLUE, r.getDebut(), r.getFin(), r.getMotif(), r);

                // Add click listener
                evt.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // Get selected object
                        JEvent source = (JEvent) e.getSource();
                        Reunion r = (Reunion) source.getEvent();

                        selectedReunion = r;

                        datePicker.setDate(toLocalDate(r.getDebut()));
                        timePickerDebut.setTime(toLocalTime(r.getDebut()));
                        timePickerFin.setTime(toLocalTime(r.getFin()));
                        cboSalles.setSelectedIndex(listSalle.indexOf(getSalleFromId(selectedReunion.getSalle().getId())));
                        txtMotif.setText(r.getMotif());
                        toggleSelectedUsers();
                        validateButton.setEnabled(true);
                        deleteButton.setEnabled(true);
                    }
                });
                events.add(evt);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erreur : " + ex.getMessage());
            }
        }

        return events;
    }

    public LocalDate toLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public LocalTime toLocalTime(Date d) {
        return LocalDateTime.ofInstant(d.toInstant(),
                ZoneId.systemDefault()).toLocalTime();
    }

    /**
     * Return the salle of the list from its id
     *
     * @param id
     * @return
     */
    public Salle getSalleFromId(Long id) {
        for (Salle s : this.listSalle) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    public void fillUserList() {
        formUserList.removeAll();
        for (Utilisateur u : listUsers) {
            formUserList.add(u.toString());
        }
    }

    /**
     * Get selected users for selectedReunion
     */
    private void toggleSelectedUsers() {
        Model<ConcernerLight> model = new Model<ConcernerLight>(ConcernerLight.class);
        List<ConcernerLight> concernList = new ArrayList<>();
        /**
         * reset selection
         */
        for (int i = 0; i < listUsers.size(); i++) {
            formUserList.deselect(i);
        }

        // Get associations
        try {
            concernList = model.query("SELECT * FROM concerner WHERE reunion = ?", Arrays.asList(selectedReunion.getId()));
        } catch (Exception ex) {
            Logger.getLogger(PlanningFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Toggle each association
        for (ConcernerLight c : concernList) {
            formUserList.select(getUserIndexInList(c.getUtilisateur()));
        }
    }

    /**
     * Get the index of a specified user in the userList
     *
     * @return
     */
    private int getUserIndexInList(Long id) {
        for (int i = 0; i < listUsers.size(); i++) {
            if (listUsers.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Update the event
     */
    public void updateEvent() {
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
        r.setId(selectedReunion.getId());
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
                        "SELECT * FROM Reunion WHERE fin > ? AND debut < ? AND salle = ? AND id != ?",
                        Arrays.asList(debut.getTime(), fin.getTime(), r.getIdSalle(), r.getId()));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erreur : " + ex.getMessage());
            }
            // If ther is no overlap
            if (overlapEvents.isEmpty()) {
                try {
                    // Update reunionLight
                    (new Model<ReunionLight>(ReunionLight.class)).update(r);

                    /**
                     * Update links
                     */
                    Model<ConcernerLight> concernModel = new Model<>(ConcernerLight.class);
                    List<Utilisateur> selectedUsers = new ArrayList<>();
                    // Delete 
                    List<ConcernerLight> existing = concernModel.query("SELECT * FROM concerner WHERE reunion = ?", Arrays.asList(selectedReunion.getId()));
                    for (ConcernerLight c : existing) {
                        concernModel.delete(c);
                    }

                    // Get selected users
                    for (int i = 0; i < listUsers.size(); i++) {
                        if (formUserList.isIndexSelected(i)) {
                            selectedUsers.add(listUsers.get(i));
                        }
                    }
                    for (Utilisateur u : selectedUsers) {
                        ConcernerLight cl = new ConcernerLight(u.getId(), r.getId());
                        concernModel.create(cl);
                    }
                    JOptionPane.showMessageDialog(null, "La réunion a bien été modifiée", "Information", JOptionPane.INFORMATION_MESSAGE);

                    // Reload events
                    loadEvents();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erreur : " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Vous ne pouvez pas déplacer cette réunion à ce moment là.\nUne autre réunion occupe déjà ce créneau.\n\nSi aucune réunion n'est visible sur le planning, veuillez contacter l'administrateur.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Le début ne peut pas être après la fin.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }

    }
}
