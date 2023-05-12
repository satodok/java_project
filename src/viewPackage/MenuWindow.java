package viewPackage;

import javax.swing.*;
import java.awt.event.*;

public class MenuWindow extends JFrame{
    private JMenuBar menuBar;

    // menus
    private JMenu application;
    private JMenu bikeRental;
    private JMenu myAccount;
    private JMenu subscription;

    // quit item
    private JMenuItem quit;

    // subscription items
    private JMenuItem subscribe;
    private JMenuItem subscriptionList;
    private JMenuItem unsubscribe;

    // my account item
    private JMenuItem myInformations;
    private JMenuItem register;

    // rental items
    private JMenuItem rentABike;
    private JMenuItem stations;


    public MenuWindow(){
        super("Libia Vélo");
        this.setBounds(100,100,600,600);

        this.menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        this.application = new JMenu("Application");
        this.menuBar.add(application);
        this.myAccount = new JMenu("My account");
        this.menuBar.add(myAccount);
        this.subscription = new JMenu("Subscription");
        this.menuBar.add(subscription);
        this.bikeRental = new JMenu("Rental");
        this.menuBar.add(bikeRental);

        quit = new JMenuItem("Quit");
        application.add(quit);
        ExitListener exitListener = new ExitListener();
        quit.addActionListener(exitListener);

        subscribe = new JMenuItem("Subscribe");
        subscription.add(subscribe);
        unsubscribe = new JMenuItem("Unsubscribe");
        subscription.add(unsubscribe);
        subscriptionList = new JMenuItem("Subscription list");
        subscription.add(subscriptionList);

        register = new JMenuItem("Register");
        myAccount.add(register);
        myInformations = new JMenuItem("My informations");
        myAccount.add(myInformations);

        rentABike = new JMenuItem("Rent a bike");
        bikeRental.add(rentABike);
        stations = new JMenuItem("Stations list");
        bikeRental.add(stations);


        register.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RegistrationForm registrationForm = new RegistrationForm();
                setContentPane(registrationForm);
                revalidate();
            }
        });
        this.setVisible(true);
    }
    private class ExitListener implements ActionListener {
        public void actionPerformed (ActionEvent event) {
            System.exit(0);
        }
    }
}
