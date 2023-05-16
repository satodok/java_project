package viewPackage;

import controllerPackage.ApplicationController;

import exceptionPackage.ConnectionException;
import exceptionPackage.UnfoundResearchException;
import modelPackage.Subscription;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SubscriptionDeleteWindow extends JFrame {

        private ApplicationController controller;

        private JTable table;
        private JButton deleteButton;
        Subscription subscription = new Subscription();

        public SubscriptionDeleteWindow()  {
            super("Unsubscribe");
            this.setBounds(100,100,400,400);
            setController(new ApplicationController());

            SubscriptionInformationsModel  tableModel = new SubscriptionInformationsModel(subscription);

            try{
            ArrayList<Subscription> subscriptionList = controller.getAllSubscription();

                for (Subscription subscription : subscriptionList) {
                    Object[] rowData = {
                            subscription.getSubscriptionID(),
                            subscription.getPrice(),
                            subscription.getDiscount(),
                            subscription.getAutomaticRenewal(),
                            subscription.getCautionPayed(),
                            subscription.getPricePayed(),
                            subscription.getStartDate(),
                            subscription.getEndDate(),
                            subscription.getClientNumber(),
                            subscription.getTypeName()
                    };

                    tableModel.addRow(rowData);
                }

                table = new JTable(tableModel);
                JScrollPane scrollPane = new JScrollPane(table);
                add(scrollPane, BorderLayout.CENTER);

                deleteButton = new JButton("Delete");
                deleteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Récupérer les indices des lignes sélectionnées
                        int[] selectedRows = table.getSelectedRows();

                        // Stocker les ID des lignes sélectionnées
                        ArrayList<String> selectedIDs = new ArrayList<>();
                        for (int row : selectedRows) {
                            String subscriptionId = (String) table.getValueAt(row, 0); // Supposant que la première colonne contient l'ID
                            selectedIDs.add(subscriptionId);
                        }

                        // Appeler la méthode de suppression avec les ID sélectionnés
                        try {
                            controller.deleteSubscription("selectedIDs");
                        } catch (ConnectionException ex) {
                            ex.printStackTrace();
                        } catch (UnfoundResearchException ex) {
                            ex.printStackTrace();
                        }

                        // Rafraîchir la table après la suppression
                        tableModel.setRowCount(0); // Supprimer toutes les lignes existantes
                        try {
                            for (Subscription subscription : controller.getAllSubscription()) {
                                Object[] rowData = {subscription.getPrice(),
                                        subscription.getDiscount(),
                                        subscription.getAutomaticRenewal(),
                                        subscription.getCautionPayed(),
                                        subscription.getPricePayed(),
                                        subscription.getStartDate(),
                                        subscription.getEndDate(),
                                        subscription.getClientNumber(),
                                        subscription.getTypeName()};
                                tableModel.addRow(rowData);
                            }
                        } catch (ConnectionException ex) {
                            ex.printStackTrace();
                        } catch (UnfoundResearchException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            } catch (ConnectionException | UnfoundResearchException sqlException){
                    JOptionPane.showMessageDialog(null, sqlException.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }



        }

    public void setController(ApplicationController controller) {
        this.controller = controller;
    }
}

