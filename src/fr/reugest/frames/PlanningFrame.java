package fr.reugest.frames;

import com.github.lgooddatepicker.components.CalendarPanel;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;
import fr.reugest.main.Globals;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import fr.reugest.models.Salle;
import fr.reugest.models.Utilisateur;
import fr.thomas.orm.Model;
import fr.thomas.swing.JScheduler;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.util.Arrays;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class PlanningFrame extends BaseFrame {

    private static final long serialVersionUID = -5481781563859385889L;

    /**
     * Models
     */
    private Model<Salle> salleModel;

    /**
     * Lists
     */
    private List<Salle> listSalle;
    
    /**
     * Graphic components
     */
    private DatePicker datePicker;
    private TimePicker timePickerDebut, timePickerFin;
    private JScheduler planning;
    private JPanel schedulerHeaderPanel;
    private JButton btnPrev, btnNext;

    /**
     * Selected user
     */
    private Utilisateur selectedUser;

    public PlanningFrame() {
        super();
        // Store in global variables
        Globals.planningFrame = this;
        setMinimumSize(new Dimension(1120,640));
        this.setTitle("Gestion des réservations");
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
        // Create layouts
        pLeft.setLayout(new BorderLayout());
        // Add items
        schedulerHeaderPanel = new JPanel(new FlowLayout());
        this.btnPrev = new JButton("<<");
        schedulerHeaderPanel.add(this.btnPrev);
        schedulerHeaderPanel.add(new JComboBox(this.listSalle.toArray()));
        this.btnNext = new JButton(">>");
        schedulerHeaderPanel.add(this.btnNext);
        pLeft.add(schedulerHeaderPanel, BorderLayout.NORTH);        
        
        
        // Planning
        this.planning = new JScheduler(Arrays.asList(), Color.RED);
        pLeft.add(this.planning, BorderLayout.CENTER);
        // Set a border for better rendering
        this.pLeft.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // EventHandler on buttons
        btnPrev.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                planning.previousWeek();
            }
        });
        btnNext.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                planning.nextWeek();
            }
        });
    }

    /**
     * Load right panel components (Form)
     */
    private void loadRightPanel() {
        // Set right panel
        this.pRight.setLayout(new GridLayout(10, 2, 25, 10));
        
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
        
        
        /**
         * Create empty JLabel to fill
         */
        
        this.pRight.add(new JLabel("Salle : "));
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

        //Add button rights access
        this.addRightsToAddButton();
        
        // Add button event listener
        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
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
    }
    
    
    public void addRightsToAddButton() {
        // Simple user
        if(Globals.user.getDroit().getId().equals(1L)) {
            addButton.setVisible(false);
            deleteButton.setVisible(false);
        }
    }
}
