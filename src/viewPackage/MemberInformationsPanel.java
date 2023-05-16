package viewPackage;

import controllerPackage.ApplicationController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import exceptionPackage.*;
import modelPackage.MemberInformations;
import exceptionPackage.ConnectionException;
import exceptionPackage.UnfoundResearchException;

public class MemberInformationsPanel extends JFrame{
    private ApplicationController controller;
    private MemberInformations memberInformations;
    private JTextField nationalNumberInupt;
    private String nationalNumber;
    private JLabel nationalNumberLabel;
    private JPanel informationsPanel;
    private MemberInformationsModel model;
    private JScrollPane scrollPane;
    private JTable table;
    private JButton searchButton;

    public MemberInformationsPanel(){
        super("My informations");
        this.setBounds(100,100,900,600);
        setController(new ApplicationController());
        nationalNumberLabel = new JLabel("Enter your national number");
        nationalNumberInupt = new JTextField();
        nationalNumberInupt.setPreferredSize(new Dimension(200, 30));

        informationsPanel = new JPanel();
        informationsPanel.add(nationalNumberLabel);
        informationsPanel.add(nationalNumberInupt);
        setVisible(true);
        add(informationsPanel,BorderLayout.NORTH);

        searchButton = new JButton("Search");
        informationsPanel.add(searchButton);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nationalNumber = nationalNumberInupt.getText();

                try{
                    memberInformations = controller.findMemberInformationsByNationalNumber(nationalNumber);
                    model = new MemberInformationsModel(memberInformations);

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

