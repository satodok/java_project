package viewPackage;

import controllerPackage.ApplicationController;

import exceptionPackage.ConnectionException;
import exceptionPackage.UnfoundResearchException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SubscriptionDeleteWindow extends JFrame {

        private ApplicationController controller;

        private JLabel subscriptionIDLabel;
        private JTextField subscriptionIDInput;
        private JPanel informationsPanel;
        private JPanel buttonPanel;
        private JButton searchButton;
        private String subscriptionID;

        public SubscriptionDeleteWindow(){
            super("Unsubscribe");
            this.setBounds(100,100,400,400);
            setController(new ApplicationController());

            subscriptionIDLabel = new JLabel("Enter the delete subscription ID");
            subscriptionIDInput = new JTextField();
            subscriptionIDInput.setPreferredSize(new Dimension(150, 30));

            informationsPanel = new JPanel();
            informationsPanel.add(subscriptionIDLabel);
            informationsPanel.add(subscriptionIDInput);

            buttonPanel = new JPanel();
            searchButton = new JButton("Search");
            buttonPanel.add(searchButton);

            add(informationsPanel,BorderLayout.NORTH);
            add(buttonPanel, BorderLayout.CENTER);
            setVisible(true);

            searchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    subscriptionID = subscriptionIDInput.getText();

                    try{
                        controller.deleteSubscription(subscriptionID);

                    } catch (ConnectionException | UnfoundResearchException sqlException){
                    JOptionPane.showMessageDialog(null, sqlException.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }

            });

        }
    public void setController(ApplicationController controller) {
        this.controller = controller;
    }
}

