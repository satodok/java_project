package viewPackage;

import controllerPackage.ApplicationController;
import exceptionPackage.ConnectionException;
import exceptionPackage.UnfoundResearchException;
import modelPackage.Member;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MemberSubscriptionWindow extends JFrame{
    private ApplicationController controller;
    private String subscriptionType;
    private JLabel subscriptionTypeLabel;
    private JComboBox<String> subscriptionTypeComboBox;
    private JPanel comboBoxPanel;
    private JButton researchButton;
    private ArrayList<Member> members;
    private MemberSubscriptionModel model;
    private JScrollPane scrollPane;
    private JTable table;

    public MemberSubscriptionWindow() {
        super("Research members from subscription type");
        this.setBounds(100, 100, 600, 600);
        setController(new ApplicationController());
        // Créer la JComboBox et ajouter la liste des abonnements disponibles
        subscriptionTypeLabel = new JLabel("Choose a subscription plan");
        subscriptionTypeComboBox = new JComboBox<>();
        subscriptionTypeComboBox.addItem("BRONZE");
        subscriptionTypeComboBox.addItem("SILVER");
        subscriptionTypeComboBox.addItem("GOLD");

        // Ajouter la JComboBox à un panneau
        comboBoxPanel = new JPanel();
        comboBoxPanel.add(subscriptionTypeLabel);
        comboBoxPanel.add(subscriptionTypeComboBox);
        setVisible(true);
        add(comboBoxPanel, BorderLayout.NORTH);

        // Ajouter un boutton de recherche
        researchButton = new JButton("Search");
        comboBoxPanel.add(researchButton);

        // Ajouter un écouteur d'évènements pour le bouton
        researchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupérer le type d'abonnement choisi
                subscriptionType = (String) subscriptionTypeComboBox.getSelectedItem();
                // Lancer la recherche
                try {
                    members = controller.findMembersFromSubscriptionPlan(subscriptionType);
                    model = new MemberSubscriptionModel(members);

                    // Vider la JTable si des infos ont déjà été affichées avec le bouton search
                    if (scrollPane != null) {
                        remove(scrollPane);
                    }
                    //Afficher le résultat de la recherche
                    table = new JTable(model);
                    scrollPane = new JScrollPane(table);
                    add(scrollPane, BorderLayout.CENTER);
                    setVisible(true);
                }
                catch (UnfoundResearchException | ConnectionException exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

    }

    private void setController(ApplicationController applicationController) {
        this.controller = applicationController;
    }
}
