/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.reugest.frames;

import fr.reugest.main.Globals;
import fr.reugest.models.Reunion;
import fr.thomas.orm.Model;
import fr.thomas.swing.JEvent;
import fr.thomas.swing.JScheduler;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author tpeyr
 */
public class PrintFrame extends JFrame {

    private JPanel panel, headerPanel;
    private JScheduler planning;
    private JButton btnPrevious, btnNext, btnPrint;

    private List<Reunion> listReunions;
    private Model<Reunion> reunionModel;

    public PrintFrame() {
        super("Mon planning");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(1020, 720));
        setLocationRelativeTo(null);

        // Model
        reunionModel = new Model<>(Reunion.class);

        panel = new JPanel(new BorderLayout());
        setContentPane(panel);

        /**
         * Buttons
         */
        headerPanel = new JPanel(new FlowLayout());
        btnPrint = new JButton(new ImageIcon(getClass().getResource("/print.png")));
        btnPrevious = new JButton("<<");
        btnNext = new JButton(">>");
        headerPanel.add(btnPrevious);
        headerPanel.add(btnPrint);
        headerPanel.add(btnNext);
        panel.add(headerPanel, BorderLayout.NORTH);

        /**
         * Planning
         */
        planning = new JScheduler(Arrays.asList(), Color.RED);
        panel.add(planning, BorderLayout.CENTER);

        // Change icon
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/logo_64.png")));
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                loadEvents();
            }
        });
        btnPrevious.addActionListener(new ActionListener() {
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
        btnPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportAsImage();
            }
        });
    }

    public void loadEvents() {
        Calendar lundi = planning.getStartOfWeek();
        Calendar dimanche = planning.getEndOfWeek();

        try {
            this.listReunions = this.reunionModel.query(
                    "SELECT * FROM reunion WHERE id IN (SELECT reunion FROM concerner WHERE utilisateur = ?) AND fin > ? AND debut < ? and isValid=?",
                    Arrays.asList(Globals.user.getId(), lundi.getTime(), dimanche.getTime(), true));
        } catch (Exception ex) {
            listReunions = new ArrayList<Reunion>();
            JOptionPane.showMessageDialog(null, "Erreur : " + ex.getMessage());
        }
        // Reload events in planning
        planning.setEvents(getJEventsFromReunion(listReunions));
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
                events.add(new JEvent(Color.BLUE, r.getDebut(), r.getFin(), r.getMotif(), r));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erreur : " + ex.getMessage());
            }
        }
        return events;
    }

    private void exportAsImage() {
        
        /**
         * Remove bar for screenshot
         */
        planning.getCalendar().set(Calendar.HOUR_OF_DAY, 1);
        Calendar now = Calendar.getInstance();
        planning.refresh();
        
        /**
         * Take screenshot
         */
        setSize(planning.getSize());
        BufferedImage image = new BufferedImage(planning.getWidth(), planning.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        planning.printAll(g);
        g.dispose();
        try {
            ImageIO.write(image, "png", new File("D://test.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * Add red bar
         */
        planning.getCalendar().set(Calendar.HOUR_OF_DAY, now.get(Calendar.HOUR_OF_DAY));
        planning.refresh();
    }
}
