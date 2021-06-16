package fr.reugest.frames;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;
import fr.reugest.main.Globals;
import fr.reugest.models.Concerner;
import fr.reugest.models.Reunion;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import fr.reugest.models.Salle;
import fr.reugest.models.Utilisateur;
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
        // Validate button action listener

        //Add button rights access
        this.addRightsToAddButton();

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

            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
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

    public void addRightsToAddButton() {
        // Simple user
        if (Globals.user.getDroit().getId().equals(1L)) {
            addButton.setVisible(false);
            deleteButton.setVisible(false);
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
                        validateButton.setEnabled(true);
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
     * 
     */
    public void updateEvent() {
        // TODO: Make this method
    }
}
