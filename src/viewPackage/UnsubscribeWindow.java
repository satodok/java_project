package viewPackage;

import controllerPackage.ApplicationController;
import exceptionPackage.UnfoundResearchException;
import modelPackage.Subscription;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UnsubscribeWindow extends JFrame {
    private ApplicationController controller;

    private JLabel subscriptionIDLabel;
    private JTextField subscriptionIDInput;
    private JPanel informationsPanel;
    private JButton searchButton;
    private String subscriptionID;
    private int subscriptionIDINT;

    public UnsubscribeWindow(){
        super("Unsubscribe");
        this.setBounds(100,100,400,400);

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
                subscriptionIDINT = Integer.parseInt(subscriptionID);

                //try{

                //}catch (UnfoundResearchException unfoundResearchException){
                    //JOptionPane.showMessageDialog(null, unfoundResearchException.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                //}
            }

        });

    }
}
