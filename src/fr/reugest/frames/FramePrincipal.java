package fr.reugest.frames;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class FramePrincipal extends JFrame {

    private JPanel panneau;
    JMenu Gestion, planning, aide;
    JMenuItem i1, i2, i3, i4, i5, i6, i7;
    JButton boutonQuitter;
    // ImageIcon img;
    // JLabel labelImg;

    public FramePrincipal() {
        JFrame f = new JFrame("Menu and MenuItem Example");
        JMenuBar mb = new JMenuBar();
        Gestion = new JMenu("Gestion");
        planning = new JMenu("Planning");
        aide = new JMenu("Aide");
        boutonQuitter = new JButton("Quitter");
        panneau = new JPanel(new BorderLayout());
        // labelImg = new JLabel("Texte : ");
        i1 = new JMenuItem("Utilisateurs");
        i2 = new JMenuItem("Salles");
        i3 = new JMenuItem("Equipements");
        i4 = new JMenuItem("Réserver");
        i5 = new JMenuItem("Imprimer");
        i6 = new JMenuItem("Paramètre");
        i7 = new JMenuItem("A propos");
        // img = new ImageIcon("logoReugest.jpg");
        Gestion.add(i1);
        Gestion.add(i2);
        Gestion.add(i3);
        planning.add(i4);
        planning.add(i5);
        aide.add(i6);
        aide.add(i7);
        mb.add(Gestion);
        mb.add(planning);
        mb.add(aide);
        mb.add(boutonQuitter);
        f.setJMenuBar(mb);
        f.setSize(400, 400);
        f.setLayout(null);
        f.setVisible(true);
        this.boutonQuitter.addActionListener(new EcouteurBoutonQuitter());
        // labelImg.setIcon(img);
    }

    class EcouteurBoutonQuitter implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            System.out.println("Il s'agit du bouton Quitter");
            System.exit(0);

        }

    }

}
