/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.reugest.frames;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.reugest.main.Globals;
import fr.reugest.models.Utilisateur;
import fr.thomas.orm.Model;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author jeansauron
 */
public class ConnexionFrame extends JFrame {

    private JPanel connexionPanneau = new JPanel();
    private JButton boutonConnexion = new JButton("Connexion");
    private JButton boutonQuitter = new JButton("Quitter");
    private JLabel login = new JLabel("Login : ");
    private JLabel password = new JLabel("Password : ");
    private JTextField champLogin = new JTextField("", 20);
    private JPasswordField champPassword = new JPasswordField("", 20);
    private JPanel connexionHaut = new JPanel(new BorderLayout());
    private JPanel connexionCentre = new JPanel(new BorderLayout());
    private JPanel connexionBas = new JPanel(new BorderLayout());
    private JPanel connexionHautHaut = new JPanel(new BorderLayout());
    private JPanel connexionHautBas = new JPanel(new BorderLayout());
    private JPanel connexionCentreDroite = new JPanel(new BorderLayout());
    private JPanel connexionCentreGauche = new JPanel(new BorderLayout());
    private JPanel connexionCentreBas = new JPanel(new BorderLayout());
    private JPanel connexionBasDroite = new JPanel(new BorderLayout());
    private JPanel connexionBasGauche = new JPanel(new BorderLayout());
    private JPanel connexionBasBas = new JPanel(new BorderLayout());
    private JPanel connexionBasHaut = new JPanel(new BorderLayout());
    private ImageIcon icone = new ImageIcon(getClass().getResource("/logo.jpg"));
    private GridLayout grid = new GridLayout(3, 2);
    private JLabel messageError = new JLabel("L'adresse mail ou le mot de passe est incorrect");

    // create model
    private Model<Utilisateur> utilisateurModel = new Model<Utilisateur>(Utilisateur.class);

    public ConnexionFrame() {
        super("Connexion");
        this.setBounds(100, 100, 350, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Load image icon
        Image i = this.icone.getImage().getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
        this.messageError.setForeground(Color.red);
        this.messageError.setVisible(false);

        connexionHautHaut.add(this.login);
        connexionCentreDroite.add(this.password);
        connexionHautBas.add(this.champLogin);
        connexionCentreGauche.add(this.champPassword);
        connexionBasDroite.add(this.boutonConnexion);
        connexionBasGauche.add(this.boutonQuitter);
        connexionBasHaut.add(this.messageError);
        connexionBasBas.add(new JLabel(new ImageIcon(i)));
        connexionPanneau.add(this.connexionHaut, BorderLayout.NORTH);
        connexionHaut.add(this.connexionHautHaut, BorderLayout.NORTH);
        connexionHaut.add(this.connexionHautBas, BorderLayout.SOUTH);

        connexionPanneau.add(this.connexionCentre, BorderLayout.CENTER);
        connexionCentre.add(this.connexionCentreDroite, BorderLayout.NORTH);
        connexionCentre.add(this.connexionCentreGauche, BorderLayout.SOUTH);

        connexionPanneau.add(this.connexionBas, BorderLayout.SOUTH);
        connexionBas.add(this.connexionBasHaut, BorderLayout.NORTH);
        connexionBas.add(this.connexionBasBas, BorderLayout.SOUTH);
        connexionBas.add(this.connexionBasDroite, BorderLayout.WEST);
        connexionBas.add(this.connexionBasGauche, BorderLayout.EAST);

        this.boutonConnexion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 * If control key pressed, auto login
                 */
                if ((e.getModifiers() & ActionEvent.CTRL_MASK) == ActionEvent.CTRL_MASK) {
                    champLogin.setText("admin@admin.fr");
                    champPassword.setText("root");
                }
                if (champPassword.getPassword() != null && new String(champPassword.getPassword()) != "") {
                    try {
                        List<Utilisateur> user = utilisateurModel.query(
                                "SELECT * FROM utilisateur WHERE email = ? AND password = ?",
                                Arrays.asList(champLogin.getText(), new String(champPassword.getPassword())));
                        /**
                         * If the user is not found
                         */
                        if (user.isEmpty()) {
                            messageError.setVisible(true);
                        } else {
                            // Save current user
                            Globals.user = user.get(0);
                            // Create menu
                            MenuFrame menuFrame = new MenuFrame();
                            menuFrame.setVisible(true);
                            // Store the main menu as global variable
                            Globals.mainMenu = menuFrame;
                            
                            // Delete current frame
                            dispose();
                        }

                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            }
        });

        this.boutonQuitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // if(champPassword.getText()!= null && champPassword.getText() != "")
        this.getContentPane().add(this.connexionPanneau);

        this.setResizable(false);

    }
}
