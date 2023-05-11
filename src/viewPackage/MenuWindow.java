package viewPackage;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MenuWindow extends JFrame{
    private JMenuBar menuBar;

    private JMenu application;
    private JMenu bikeRental;
    private JMenu signUp;
    private JMenu informations;
    private JMenu subscription;

    private JMenuItem quitButton;
    private JMenuItem bikeRentalButton;
    private JMenuItem signUpButton;
    private JMenuItem informationsButton;
    private JMenuItem subscriptionButton;

    public MenuWindow(){
        super("Libia Vélo");
        this.setBounds(100,100,600,600);

        this.menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        this.signUp = new JMenu("Sign up");
        this.menuBar.add(signUp);
        this.informations = new JMenu("Informations");
        this.menuBar.add(informations);
        this.subscription = new JMenu("Subscription");
        this.menuBar.add(subscription);
        this.bikeRental = new JMenu("Rental");
        this.menuBar.add(bikeRental);

        // ajouter bouton quitter avec ExitListener

        this.setVisible(true);
    }
}
