/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.reugest.frames;

import fr.reugest.main.Globals;
import fr.reugest.models.Equipement;
import fr.reugest.models.Reunion;
import fr.reugest.models.light.ConcernerLight;
import fr.reugest.models.light.ReunionLight;
import fr.thomas.orm.Model;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author tpeyr
 */
public class AdminFrame extends JFrame {

    private JPanel panel;
    private JTable table;
    private String[] columns = {"Motif", "Date", "Début", "Fin", "Salle"};
    private JPopupMenu popupMenu;

    private List<Reunion> listReunions;
    private Model<Reunion> reunionModel;
    
    private Reunion selectedReunion;

    /**
     * Constructor
     */
    public AdminFrame() {
        super("Mon planning");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(1020, 720));
        setLocationRelativeTo(null);
        Globals.adminFrame = this;

        // Model
        reunionModel = new Model<>(Reunion.class);

        panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        setContentPane(panel);

        // Change icon
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/logo_64.png")));
        loadInvalidEvents();
        loadReunionsInJTable();

        panel.add(table);
        add(new JScrollPane(table));
        // Create context menu
        createContextMenu();
        
        // Add click listener
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // If right click
                if(SwingUtilities.isRightMouseButton(e) && selectedReunion != null) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }                
            }
        });
        
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                // If not reloading
                if (table.getSelectedRow() != -1) {
                    selectedReunion = listReunions.get(table.getSelectedRow());
                }
            }
        });
    }

    /**
     * Constructor
     */
    public void loadInvalidEvents() {
        try {
            this.listReunions = this.reunionModel.query(
                    "SELECT * FROM reunion WHERE isValid = ?", Arrays.asList(false));
        } catch (Exception ex) {
            listReunions = new ArrayList<Reunion>();
            JOptionPane.showMessageDialog(null, "Erreur : " + ex.getMessage());
        }
    }

    /**
     * Load reunions in table
     */
    public void loadReunionsInJTable() {

        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");

        for (Reunion r : this.listReunions) {
            tableModel.addRow(new Object[]{
                r.getMotif(),
                dateFormatter.format(r.getDebut()),
                timeFormatter.format(r.getDebut()),
                timeFormatter.format(r.getFin()),
                r.getSalle().getLibelle()
            });
        }
        this.table = new JTable(tableModel);
        this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * Create the context menu
     */
    public void createContextMenu() {
        popupMenu = new JPopupMenu();

        JMenuItem acceptMenu = new JMenuItem("Accepter");
        acceptMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 * Change isValid to true
                 */
                ReunionLight light = new ReunionLight();
                light.setId(selectedReunion.getId());
                light.setDebut(selectedReunion.getDebut());
                light.setFin(selectedReunion.getFin());
                light.setMotif(selectedReunion.getMotif());
                light.setIdSalle(selectedReunion.getSalle().getId());
                light.setIsValid(true);
                try {
                    (new Model<>(ReunionLight.class)).update(light);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erreur :\n" + ex.getMessage());
                }
                JOptionPane.showMessageDialog(null, "La réunion a bien été acceptée.");
                // Reload
                Globals.reloadAdminFrame();
            }
        });
        
        popupMenu.add(acceptMenu);

        JMenuItem refuseMenu = new JMenuItem("Refuser");
        refuseMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int input = JOptionPane.showConfirmDialog(null, "Voulez vous vraiment refuser la réunion ?\n\nCette action est irréversible.", "Select an Option...",
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
                        Globals.reloadAdminFrame();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Erreur : " + ex.getMessage());
                    }
                }
            }
        });

        popupMenu.add(refuseMenu);
    }
}
