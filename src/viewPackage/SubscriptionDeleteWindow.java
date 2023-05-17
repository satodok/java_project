package viewPackage;

import controllerPackage.ApplicationController;

import exceptionPackage.ConnectionException;
import exceptionPackage.UnfoundResearchException;
import modelPackage.Subscription;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SubscriptionDeleteWindow extends JFrame {

        private ApplicationController controller;

        private JTable table;
        private JButton deleteButton;

        public SubscriptionDeleteWindow()  {
            super("Unsubscribe");
            this.setBounds(100,100,1000,600);
            setController(new ApplicationController());

            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("Susbcription ID");
            tableModel.addColumn("Price");
            tableModel.addColumn("Discount");
            tableModel.addColumn("Automatic renewal");
            tableModel.addColumn("Caution payed");
            tableModel.addColumn("Price payed");
            tableModel.addColumn("Start date");
            tableModel.addColumn("End date");
            tableModel.addColumn("Client number");
            tableModel.addColumn("Type name");

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
                table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                JScrollPane scrollPane = new JScrollPane(table);
                add(scrollPane, BorderLayout.CENTER);

                deleteButton = new JButton("Delete");
                deleteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete ?", "Validation delete", JOptionPane.YES_NO_OPTION);
                        if (response == JOptionPane.YES_OPTION) {

                            // Récupérer les indices des lignes sélectionnées
                            int[] selectedRows = table.getSelectedRows();

                            // Stocker les ID des lignes sélectionnées
                            ArrayList<String> selectedIDs = new ArrayList<>();
                            for (int row : selectedRows) {
                                String id = (String) table.getValueAt(row, 0); // Supposant que la première colonne contient l'ID
                                selectedIDs.add(id);
                            }

                            // Appeler la méthode de suppression avec les ID sélectionnés
                            try {
                                controller.deleteSubscription(selectedIDs);
                            } catch (ConnectionException | UnfoundResearchException ex) {
                                ex.printStackTrace();
                            }

                        // Rafraîchir la table après la suppression
                        tableModel.setRowCount(0); // Supprimer toutes les lignes existantes
                        try {
                            for (Subscription subscription : controller.getAllSubscription()) {
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
                                        subscription.getTypeName()};
                                tableModel.addRow(rowData);
                            }
                        } catch (ConnectionException | UnfoundResearchException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                });
                add(deleteButton, BorderLayout.SOUTH);
            } catch (ConnectionException | UnfoundResearchException exception){
                    JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
        }

    public void setController(ApplicationController controller) {
        this.controller = controller;
    }
}

