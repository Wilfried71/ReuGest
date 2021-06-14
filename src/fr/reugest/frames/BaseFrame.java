package fr.reugest.frames;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

/**
 * First test JFrame
 *
 * @author tpeyr
 *
 */
public class BaseFrame extends JFrame {
    
    private static final long serialVersionUID = -5318525704078317102L;

    /**
     * leftContentPanel and rightContent contains a {@link GridLayout} and the
     * content of the frame.
     */
    protected JPanel panel, pLeft, pCenter, pRight;
    protected JButton validateButton, addButton, deleteButton;
    protected JTable table;
    
    private int BUTTON_SIZE = 42;
    private int BUTTON_TAB_WIDTH = 50;
    private int MIN_WIDTH = 720;
    private int MIN_HEIGHT = 420;
    
    protected enum State {
        READING,
        EDITING,
        CREATING
    }
    
    protected State state;
    
    public BaseFrame() throws HeadlessException {
        super("Base JFrame");
        // Instanciate components
        panel = new JPanel(null);
        pLeft = new JPanel(new GridLayout());
        pCenter = new JPanel();
        pRight = new JPanel();

        // Create action buttons
        this.validateButton = new JButton("Valider");

        // Add button
        addButton = new JButton(new ImageIcon(getClass().getResource("/plus.png")));
        addButton.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
        addButton.setToolTipText("Ajouter");
        // Delete button
        deleteButton = new JButton(new ImageIcon(getClass().getResource("/delete.png")));
        deleteButton.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
        deleteButton.setToolTipText("Supprimer");
        deleteButton.setEnabled(false);
        /**
         * Add resize event
         */
        getRootPane().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                /**
                 * Taille de la moitie de l'ecran si on enleve la bande centrale
                 */
                int tiersMinusIconButtons = (e.getComponent().getWidth() - BUTTON_TAB_WIDTH) / 3;

                // Modifie la taille et la position du panel gauche
                pLeft.setBounds(0, 0, tiersMinusIconButtons * 2, e.getComponent().getHeight());

                // Modifie la taille et la position du panel central, et ajoute une marge en
                // haut
                pCenter.setBounds(tiersMinusIconButtons * 2, 0, BUTTON_TAB_WIDTH, e.getComponent().getHeight());
                pCenter.setBorder(BorderFactory.createEmptyBorder((int) (getHeight() - 184), 0, 0, 0));

                // Modifie la taille et la position du panel de droite
                pRight.setBounds((tiersMinusIconButtons * 2) + BUTTON_TAB_WIDTH, 0, tiersMinusIconButtons + 1,
                        e.getComponent().getHeight());
            }
        });

        // Add buttons to panels
        pCenter.setLayout(new FlowLayout(FlowLayout.CENTER));
        pCenter.add(addButton);
        // Create new empty panel to add a margin top to the delete button
        JPanel deleteButtonMarginPanel = new JPanel();
        deleteButtonMarginPanel.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
        pCenter.add(deleteButtonMarginPanel);
        pCenter.add(deleteButton);

        // Show panels
        panel.add(pLeft);
        panel.add(pCenter);
        panel.add(pRight);
        
        this.add(panel);
        this.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Set fullscreen
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Set state as reading
        this.state = State.READING;

        // Change icon
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/logo_64.png")));
    }
}
