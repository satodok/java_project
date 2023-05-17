package viewPackage;

import businessPackage.BusinessManager;

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
    private JMenu businessTask;

    // quit item
    private JMenuItem quit;

    // subscription items
    private JMenuItem subscribe;
    private JMenuItem subscriptionUpdate;
    private JMenuItem subscriptionSearch;
    private JMenuItem unsubscribe;

    // my account item
    private JMenuItem myInformations;
    private JMenuItem register;
    private JMenuItem updateAccount;
    private JMenuItem deleteAccount;

    // rental items
    private JMenuItem rentABike;
    private JMenuItem stations;

    // Researches items
    private JMenuItem searchMemberAddress;
    private JMenuItem searchMembersWithDiscount;
    private JMenuItem searchMembersSubscriptionPlan;
    private JMenuItem searchRentalsDetails;

    //businessTask items
    private JMenuItem stationTask;


    public MenuWindow(){
        super("Libia Vélo");
        this.setBounds(100,100,600,600);
        JOptionPane.showMessageDialog(null,"Welcome to the Libia Vélo app!","Welcome",JOptionPane.INFORMATION_MESSAGE);

        // Lancer le thread de vérification des stocks
        VerifyStockThread stockThread = new VerifyStockThread(this);
        stockThread.start();

        //Création des différents menus
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
        this.businessTask = new JMenu("Business Task");
        this.menuBar.add(businessTask);

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
        subscriptionUpdate = new JMenuItem("Update");
        subscription.add(subscriptionUpdate);

        register = new JMenuItem("Register");
        myAccount.add(register);
        myInformations = new JMenuItem("My informations");
        myAccount.add(myInformations);
        updateAccount = new JMenuItem(("Update my account"));
        myAccount.add(updateAccount);
        deleteAccount = new JMenuItem("Delete my account");
        myAccount.add(deleteAccount);

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

        subscriptionUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SubscriptionUpdateFormPanel subscriptionUpdateFormPanel = new SubscriptionUpdateFormPanel();
                setContentPane(subscriptionUpdateFormPanel);
                revalidate();
            }
        });

        // Selection de updateAccount
        updateAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RegistrationUpdateFormPanel registrationUpdateFormPanel = new RegistrationUpdateFormPanel();
                setContentPane(registrationUpdateFormPanel);
                revalidate();
            }
        });

        //Actions menu register
        register.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                RegistrationFormPanel registrationForm = new RegistrationFormPanel();
                setContentPane(registrationForm);
                revalidate();
            }
        });

        //Action menu information
        myInformations.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MemberInformationsWindow memberInformations = new MemberInformationsWindow();
                memberInformations.setVisible(true);
            }
        });

        //actions menu subscribe
        subscribe.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SubscriptionFormPanel subscribeWindow = new SubscriptionFormPanel();
                setContentPane(subscribeWindow);
                revalidate();
            }
        });

        //actions menu unsubscribe
        unsubscribe.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SubscriptionDeleteWindow unSubscribeWindow = new SubscriptionDeleteWindow();
                unSubscribeWindow.setVisible(true);
            }
        });

        //action menu Subscription search

        subscriptionSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SubscriptionInformationsWindow subscriptionInformationsPanel = new SubscriptionInformationsWindow();
                subscriptionInformationsPanel.setVisible(true);
            }
        });

        //Actions search member address
        searchMemberAddress.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MemberAddressWindow memberAddressPanel = new MemberAddressWindow();
            }
        });

        //Actions search members with discount

        searchMembersWithDiscount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DiscountMemberWindow memberDiscountPanel = new DiscountMemberWindow();
            }
        });

        // Actions search members from subscription plan
        searchMembersSubscriptionPlan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MemberSubscriptionWindow memberSubscriptionPanel = new MemberSubscriptionWindow();
            }
        });

        // Actions search rentals details
        searchRentalsDetails.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RentalDetailsInformationWindow rentalDetailsInformationPanel = new RentalDetailsInformationWindow();
            }
        });

        stationTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BusinessManager businessManager = new BusinessManager();
                businessManager.performBusinessTask1();
            }
        });

        addWindowListener (new WindowAdapter() {
            public void windowClosing (WindowEvent e) {
                System.exit(0);
            }
        } );
        this.setVisible(true);


    }
    public void displayStockInformation(String message) {
        JOptionPane.showMessageDialog(null, message, "Alerte", JOptionPane.PLAIN_MESSAGE);
    }

}