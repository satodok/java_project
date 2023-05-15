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
    private String nationalNumber;
    private JLabel nationalNumberLabel;
    private JComboBox<String> nationalNumbersComboBox;
    private JPanel comboBoxPanel;
    private JButton researchButton;
    private MemberAddress memberAddress;
    private MemberAddressModel model;
    private JScrollPane scrollPane;
    private JTable table;

    public MemberAddressPanel() {
        super("Research member address");
        this.setBounds(100, 100, 600, 600);
        setController(new ApplicationController());

        try {
            // Créer la JComboBox et ajouter des numéros nationaux à partir de getAllNationalNumbers()
            nationalNumberLabel = new JLabel("Choose a national number");
            nationalNumbersComboBox = new JComboBox<>();

            for (String nationalNumber : controller.getAllNationalNumbers()) {
                nationalNumbersComboBox.addItem(nationalNumber);
            }

            // Ajouter la JComboBox à un panneau
            comboBoxPanel = new JPanel();
            comboBoxPanel.add(nationalNumberLabel);
            comboBoxPanel.add(nationalNumbersComboBox);
            setVisible(true);
            add(comboBoxPanel, BorderLayout.NORTH);

            //Ajouter un bouton de recherche
            researchButton = new JButton("Search");
            comboBoxPanel.add(researchButton);

            // Ajouter un écouteur d'événements pour le bouton
            researchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Récupérer le numéro national choisi
                    nationalNumber = (String) nationalNumbersComboBox.getSelectedItem();

                    // Lancer la recherche
                    try {
                        memberAddress = controller.findMemberAdressByNationalNumber(nationalNumber);
                        model = new MemberAddressModel(memberAddress);

                        // Vider la JTable si des infos ont déjà été affichées avec le bouton search
                        if (scrollPane != null) {
                            remove(scrollPane);
                        }
                        //Afficher le resultat de la recherche
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
        catch (AllNationalNumbersException | ConnectionException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void setController(ApplicationController controller) {

        this.controller = controller;
    }
}




