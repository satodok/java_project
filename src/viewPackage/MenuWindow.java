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
    private JMenuItem subscriptionList;
    private JMenuItem unsubscribe;

    // my account item
    private JMenuItem myInformations;
    private JMenuItem register;

    // rental items
    private JMenuItem rentABike;
    private JMenuItem stations;

    // Researches items
    private JMenuItem searchMemberAddress;
    private JMenuItem searchMembersWithDiscount;


    public MenuWindow(){
        super("Libia Vélo");
        this.setBounds(100,100,600,600);
        JOptionPane.showMessageDialog(null,"Welcome to the Libia Vélo app!","Welcome",JOptionPane.INFORMATION_MESSAGE);

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

        searchMemberAddress = new JMenuItem("Search member address");
        researches.add(searchMemberAddress);
        searchMembersWithDiscount = new JMenuItem("Search members with discount");
        researches.add(searchMembersWithDiscount);

        register.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RegistrationForm registrationForm = new RegistrationForm();
                setContentPane(registrationForm);
                revalidate();
            }
        });

        myInformations.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MemberInformations memberInformations = new MemberInformations();
                memberInformations.setVisible(true);
            }
        });

        subscribe.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SubscribeWindow subscribeWindow = new SubscribeWindow();
                subscribeWindow.setVisible(true);
            }
        });

        unsubscribe.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UnsubscribeWindow unSubscribeWindow = new UnsubscribeWindow();
                unSubscribeWindow.setVisible(true);
            }
        });

        subscriptionList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SubscriptionInformations subscriptionInformations = new SubscriptionInformations();
                subscriptionInformations.setVisible(true);
            }
        }
        );
        searchMemberAddress.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MemberAddressPanel memberAddressPanel = new MemberAddressPanel();
            }
        });

        searchMembersWithDiscount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MemberDiscountPanel memberDiscountPanel = new MemberDiscountPanel();
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