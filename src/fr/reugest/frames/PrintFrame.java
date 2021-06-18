/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.reugest.frames;

import fr.reugest.util.FileTypeFilter;
import fr.reugest.main.Globals;
import fr.reugest.models.Reunion;
import fr.thomas.orm.Model;
import fr.thomas.swing.JEvent;
import fr.thomas.swing.JScheduler;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 * Print frame
 * @author tpeyr
 */
public class PrintFrame extends JFrame {

    private JPanel panel, headerPanel;
    private JScheduler planning;
    private JButton btnPrevious, btnNext, btnPrint;

    private List<Reunion> listReunions;
    private Model<Reunion> reunionModel;

    /**
     * Constructor
     */
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
                try {
                                        
                    /**
                     * Get destination
                     */
                    JFileChooser fileChooser = new JFileChooser();
                    
                    /**
                     * Change look and feel
                     */
                    LookAndFeel old = UIManager.getLookAndFeel();
                    try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } catch (Throwable ex) {
                        old = null;
                    }
                    fileChooser.updateUI();
                    
                    // Set file chooser title
                    fileChooser.setDialogTitle("Choisissez un emplacement pour le fichier");
                    // File filter
                    FileFilter ff = new FileTypeFilter(".pdf", "Fichiers PDF");
                    fileChooser.setFileFilter(ff);
                    int result = fileChooser.showSaveDialog(null);
                    /**
                     * If selected file
                     */
                    if (result == JFileChooser.APPROVE_OPTION) {
                        // Take screenshot of planning component
                        File screenshot = getScreenShot();
                        // Create pdf
                        File pdf = fileChooser.getSelectedFile();

                        String dest = pdf.getAbsolutePath() + ".pdf";
                        PDDocument document = new PDDocument();

                        // Create page
                        PDPage page = new PDPage(new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth()));
                        document.addPage(page);
                        PDPageContentStream contentStream = new PDPageContentStream(document, page);

                        // Write title
                        String title = "Planning de " + Globals.user.getNom() + " " + Globals.user.getPrenom() + " : Semaine du " + getStringFromDate(planning.getStartOfWeek().getTime()) + " au " + getStringFromDate(planning.getEndOfWeek().getTime());
                        PDFont font = PDType1Font.HELVETICA_BOLD;
                        int marginTop = 5;
                        int fontSize = 14; // Or whatever font size you want.
                        float titleWidth = font.getStringWidth(title) / 1000 * fontSize;
                        float titleHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;
                        contentStream.beginText();
                        contentStream.setFont(font, fontSize);
                        contentStream.newLineAtOffset((page.getMediaBox().getWidth() - titleWidth) / 2, page.getMediaBox().getHeight() - marginTop - titleHeight);
                        contentStream.showText(title);
                        contentStream.endText();

                        //IMG
                        PDImageXObject img = PDImageXObject.createFromFile("export.png", document);
                        contentStream.drawImage(img, 20, 10, PDRectangle.A4.getHeight() - 40, PDRectangle.A4.getWidth() - 40);
                        // Save document in destination
                        contentStream.close();
                        document.save(dest);
                        document.close();

                        // Delete temp screenshot
                        screenshot.delete();

                        // Open new file
                        Desktop.getDesktop().open(new File(dest));
                    }
                    
                    /**
                     * Change look and feel
                     */
                    try {
                        UIManager.setLookAndFeel(old);
                    } catch (Throwable ex) {
                        old = null;
                    }
                    fileChooser.updateUI();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erreur", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    /**
     * Load events from database and refresh planning
     */
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

    private File getScreenShot() {

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
            ImageIO.write(image, "png", new File("export.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * Add red bar
         */
        planning.getCalendar().set(Calendar.HOUR_OF_DAY, now.get(Calendar.HOUR_OF_DAY));
        planning.refresh();
        return new File("export.png");
    }

    private String getStringFromDate(Date d) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(d);
    }
}
