/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.reugest.frames;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import fr.reugest.main.Globals;
import fr.reugest.main.MainClass;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

/**
 * Settings frame (not finished)
 * @author tpeyr
 */
public class SettingsFrame extends JFrame {

    private JPanel panel;
    private JCheckBox chkDarkMode;
    private JButton cancelButton, validateButton;

    /**
     * Constructor
     */
    public SettingsFrame() {
        super("Paramètres");
        this.setSize(new Dimension(360, 160));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Change icon
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/logo_64.png")));

        this.panel = new JPanel(new GridLayout(3, 2, 10, 10));
        this.panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setContentPane(panel);
        this.addWindowListener(this.windowListener);

        // Add components
        this.addComponents();
    }

    private void addComponents() {
        chkDarkMode = new JCheckBox("Mode sombre");
        panel.add(chkDarkMode);
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(new JLabel());

        /**
         * Cancel button
         */
        cancelButton = new JButton("Annuler");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel.add(cancelButton);

        /**
         * Validate button
         */
        validateButton = new JButton("Valider");
        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Change look and feel
                setLookAndFeelDarkOrWhite();
                // Close frame
                dispose();
            }
        });
        panel.add(validateButton);
    }

    /**
     * Window close listener
     */
    WindowListener windowListener = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            Globals.mainMenu.setEnabled(true);
        }
    };

    /**
     * Load data from application.properties
     */
    public void loadDataFromProperties() {
        /**
         * Load data from application.properties
         */
        InputStream is = MainClass.class.getResourceAsStream("/application.properties");
        Properties props = new Properties();
        try {
            props.load(is);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            if (props.getProperty("dark.mode") == null || !props.getProperty("dark.mode").equals("true")) {
                chkDarkMode.setSelected(false);
            } else {
                chkDarkMode.setSelected(true);
            }
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
    }

    /**
     * Load Look and feel (doesn't work)
     */
    public void setLookAndFeelDarkOrWhite() {
        try {
            /**
             * Load data from application.properties
             */
            InputStream is = MainClass.class.getResourceAsStream("/application.properties");
            Properties props = new Properties();
            props.setProperty("dark.mode", (chkDarkMode.isSelected()) ? "true" : "false");
            props.load(is);
            /**
             * Load specific LAF
             */
            if (props.getProperty("dark.mode") == null || !props.getProperty("dark.mode").equals("true")) {
                UIManager.setLookAndFeel(new FlatLightLaf());
            } else {
                UIManager.setLookAndFeel(new FlatDarkLaf());
            }

            /**
             * Write in output stream
             
            OutputStream output = new FileOutputStream(this.getClass().getResource("/application.properties"));
            Properties newProps = new Properties();
            // For each prop in application.properties, rewrite it
            for (Map.Entry<Object, Object> entry : props.entrySet()) {

                newProps.setProperty(entry.getKey().toString(), entry.getValue().toString());

            }
            // save properties to project root folder
            newProps.store(output, null);
            output.close();
            System.out.println(newProps);*/
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
