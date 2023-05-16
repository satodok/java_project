package viewPackage;

import controllerPackage.ApplicationController;
import exceptionPackage.ConnectionException;
import exceptionPackage.UnfoundResearchException;
import modelPackage.Subscription;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SubscriptionInformationsWindow extends JFrame {
    private ApplicationController controller;
    private Subscription subscription;
    private JTextField subscriptionIDInput;
    private String subscriptionID;
    private JLabel subscriptionIDLabel;
    private JPanel informationsPanel;
    private SubscriptionInformationsModel model;
    private JScrollPane scrollPane;
    private JTable table;
    private JButton searchButton;

    public SubscriptionInformationsWindow(){
        super("Subscription search");
        this.setBounds(100,100,900,600);
        setController(new ApplicationController());
        subscriptionIDLabel = new JLabel("Enter the search subscription ID");
        subscriptionIDInput = new JTextField();
        subscriptionIDInput.setPreferredSize(new Dimension(150, 30));

        informationsPanel = new JPanel();
        informationsPanel.add(subscriptionIDLabel);
        informationsPanel.add(subscriptionIDInput);
        setVisible(true);
        add(informationsPanel,BorderLayout.NORTH);

        searchButton = new JButton("Search");
        informationsPanel.add(searchButton);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subscriptionID = subscriptionIDInput.getText();

                try{
                    subscription = controller.findSubscriptionBySubscriptionID(subscriptionID);
                    model = new SubscriptionInformationsModel(subscription);

                    if(scrollPane != null){
                        remove(scrollPane);
                    }

                    table = new JTable(model);
                    scrollPane = new JScrollPane(table);
                    add(scrollPane, BorderLayout.CENTER);
                    setVisible(true);
                }
                catch (UnfoundResearchException | ConnectionException exception){
                    JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    public void setController(ApplicationController controller) {
        this.controller = controller;
    }
}
