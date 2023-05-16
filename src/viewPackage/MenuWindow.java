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
    private JMenu researches;

    // quit item
    private JMenuItem quit;

    // subscription items
    private JMenuItem subscribe;
    private String ok;
    private JMenuItem subscriptionSearch;
    private JMenuItem unsubscribe;

    // my account item
    private JMenuItem myInformations;
    private JMenuItem register;
    private JMenuItem update;

    // rental items
    private JMenuItem rentABike;
    private JMenuItem stations;

    // Researches items
    private JMenuItem searchMemberAddress;
    private JMenuItem searchMembersWithDiscount;
    private JMenuItem searchMembersSubscriptionPlan;
    private JMenuItem searchRentalsDetails;


    public MenuWindow(){
        super("Libia Vélo");
        this.setBounds(100,100,600,600);
        JOptionPane.showMessageDialog(null,"Welcome to the Libia Vélo app!","Welcome",JOptionPane.INFORMATION_MESSAGE);

        this.menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        this.application = new JMenu("Application");
        this.menuBar.add(application);
        this.myAccount = new JMenu("Account");
        this.menuBar.add(myAccount);
        this.subscription = new JMenu("Subscription");
        this.menuBar.add(subscription);
        this.bikeRental = new JMenu("Rental");
        this.menuBar.add(bikeRental);
        this.researches = new JMenu("Researches");
        this.menuBar.add(researches);

        quit = new JMenuItem("Quit");
        application.add(quit);
        ExitListener exitListener = new ExitListener();
        quit.addActionListener(exitListener);

        subscribe = new JMenuItem("Subscribe");
        subscription.add(subscribe);
        unsubscribe = new JMenuItem("Unsubscribe");
        subscription.add(unsubscribe);
        subscriptionSearch = new JMenuItem("Subscription search");
        subscription.add(subscriptionSearch);

        register = new JMenuItem("Register");
        myAccount.add(register);
        myInformations = new JMenuItem("My informations");
        myAccount.add(myInformations);
        update = new JMenuItem(("Update"));
        myAccount.add(update);

        rentABike = new JMenuItem("Rent a bike");
        bikeRental.add(rentABike);
        stations = new JMenuItem("Stations list");
        bikeRental.add(stations);

        searchMemberAddress = new JMenuItem("Search member address");
        researches.add(searchMemberAddress);
        searchMembersWithDiscount = new JMenuItem("Search members with discount");
        researches.add(searchMembersWithDiscount);
        searchMembersSubscriptionPlan = new JMenuItem("Search members from subscription plan");
        researches.add(searchMembersSubscriptionPlan);
        searchRentalsDetails = new JMenuItem("Search rentalsDetails from date Range");
        researches.add(searchRentalsDetails);

        update.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                RegistrationUpdateFormPanel registrationUpdateFormPanel = new RegistrationUpdateFormPanel();
                setContentPane(registrationUpdateFormPanel);
                revalidate();
            }
        });
        register.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                RegistrationFormPanel registrationForm = new RegistrationFormPanel();
                setContentPane(registrationForm);
                revalidate();
            }
        });

        myInformations.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MemberInformationsWindow memberInformations = new MemberInformationsWindow();
                memberInformations.setVisible(true);
            }
        });

        subscribe.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SubscriptionFormPanel subscribeWindow = new SubscriptionFormPanel();
                subscribeWindow.setVisible(true);
            }
        });
        unsubscribe.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SubscriptionDeleteWindow unSubscribeWindow = new SubscriptionDeleteWindow();
                unSubscribeWindow.setVisible(true);
            }
        });

        subscriptionSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SubscriptionInformationsWindow subscriptionInformationsPanel = new SubscriptionInformationsWindow();
                subscriptionInformationsPanel.setVisible(true);
            }
        });
        searchMemberAddress.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MemberAddressWindow memberAddressPanel = new MemberAddressWindow();
            }
        });

        searchMembersWithDiscount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DiscountMemberWindow memberDiscountPanel = new DiscountMemberWindow();
            }
        });

        searchMembersSubscriptionPlan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MemberSubscriptionWindow memberSubscriptionPanel = new MemberSubscriptionWindow();
            }
        });

        searchRentalsDetails.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RentalDetailsInformationWindow rentalDetailsInformationPanel = new RentalDetailsInformationWindow();
            }
        });

        addWindowListener (new WindowAdapter() {
            public void windowClosing (WindowEvent e) {
                System.exit(0);
            }
        } );
        this.setVisible(true);
    }

}