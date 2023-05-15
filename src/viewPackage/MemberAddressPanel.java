package viewPackage;

import controllerPackage.ApplicationController;
import exceptionPackage.AllNationalNumbersException;
import exceptionPackage.ConnectionException;
import exceptionPackage.UnfoundResearchException;
import modelPackage.MemberAddress;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MemberAddressPanel extends JFrame {
    private ApplicationController controller;
    private String nationalNumberInupt;
    private String nationalNumber;
    private JComboBox<String> nationalNumbersComboBox;
    private JPanel comboBoxPanel;
    public MemberAddressPanel() {
        super("Research member address");
        this.setBounds(100,100,600,600);
        setController(new ApplicationController());

        try {
            // Créer la JComboBox et ajouter des numéros nationaux à partir de getAllNationalNumbers()
            nationalNumbersComboBox = new JComboBox<String>();
            for (String nationalNumber : controller.getAllNationalNumbers()) {
                nationalNumbersComboBox.addItem(nationalNumber);
            }

            // Ajouter la JComboBox à un panneau
            comboBoxPanel = new JPanel();
            comboBoxPanel.add(nationalNumbersComboBox);
            setVisible(true);
            add(comboBoxPanel);
            JButton researchButton = new JButton("Search");
            comboBoxPanel.add(researchButton);

            // Ajouter le panneau à un conteneur

            // Ajouter un écouteur d'événements pour la JComboBox
            researchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Récupérer le numéro national choisi
                    nationalNumber = (String) nationalNumbersComboBox.getSelectedItem();

                    // Lancer la recherche
                    try {
                        MemberAddress memberAddress = controller.findMemberAdressByNationalNumber(nationalNumber);
                        MemberAddressModel model = new MemberAddressModel(memberAddress);

                        //Afficher le resultat de la recherche
                        JTable table = new JTable(model);
                        JScrollPane scrollPane = new JScrollPane(table);
                        add(scrollPane, BorderLayout.SOUTH);

                        setVisible(true);
                    } catch (UnfoundResearchException | ConnectionException exception) {
                        JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

        } catch (AllNationalNumbersException | ConnectionException exception ) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }




    public void setController(ApplicationController controller) {
        this.controller = controller;
    }
}
