package viewPackage;

import controllerPackage.ApplicationController;

import exceptionPackage.ConnectionException;
import exceptionPackage.UnfoundResearchException;
import modelPackage.*;
import modelPackage.Subscription;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RegistrationDeleteWindow extends JFrame {

    private ApplicationController controller;

    private JTable table;
    private JButton deleteButton;

    public RegistrationDeleteWindow()  {
        super("Remove my registration");
        this.setBounds(100,100,1000,600);
        setController(new ApplicationController());

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("National number");
        tableModel.addColumn("Last name");
        tableModel.addColumn("First name");
        tableModel.addColumn("Birth date");
        tableModel.addColumn("phone number");
        tableModel.addColumn("gender");
        tableModel.addColumn("email");
        tableModel.addColumn("newsletter");
        tableModel.addColumn("street");
        tableModel.addColumn("client number");

        try{
            ArrayList<Member> members = controller.getAllMembers();
            for(Member member : members){
                //System.out.println(member.getLastName()+"\n");
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (Member member : members) {
                Object[] rowData = {
                        member.getNationalNumber(),
                        member.getLastName(),
                        member.getFirstName(),
                        dateFormat.format(member.getBirthDate()),
                        member.getPhoneNumber(),
                        member.getGender(),
                        member.getEmail(),
                        member.getNewsletter(),
                        member.getStreet(),
                        member.getStreetNumber(),
                        member.getClientNumber()
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
                    int response = JOptionPane.showConfirmDialog(null, "Your address and your client number will also be removed from our data." +
                            "Are you sure you want to delete everything related to this member?", "Validation", JOptionPane.YES_NO_OPTION);
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
                        controller.deleteAllRelatedToMembers(selectedIDs);
                    } catch (ConnectionException | UnfoundResearchException ex) {
                        ex.printStackTrace();
                    }

                    // Rafraîchir la table après la suppression
                    tableModel.setRowCount(0); // Supprimer toutes les lignes existantes
                    try {
                        for (Member member : controller.getAllMembers()) {
                            Object[] rowData = {
                                    member.getNationalNumber(),
                                    member.getLastName(),
                                    member.getFirstName(),
                                    member.getBirthDate(),
                                    member.getPhoneNumber(),
                                    member.getGender(),
                                    member.getEmail(),
                                    member.getNewsletter(),
                                    member.getStreet(),
                                    member.getStreetNumber(),
                                    member.getClientNumber(),
                            };
                            tableModel.addRow(rowData);
                        }

                    } catch (ConnectionException | UnfoundResearchException exception) {
                        JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
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

