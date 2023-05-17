package viewPackage;

import com.toedter.calendar.JDateChooser;
import controllerPackage.ApplicationController;
import exceptionPackage.ConnectionException;
import exceptionPackage.UnfoundResearchException;
import modelPackage.Subscription;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SubscriptionFormPanel extends JPanel {
    private ApplicationController controller;

    private JPanel formPanel;
    private JPanel buttonsPanel;

    private JButton validationButton;
    private JButton reinitialisationButton;
    private JButton quitButton;

    private JTextField price;
    private JTextField discount;
    private JCheckBox pricePayed;
    private JCheckBox cautionPayed;
    private JDateChooser startDate;
    private JDateChooser endDate;
    private JCheckBox automaticRenewal;
    private JComboBox<String> typeName;
    private JComboBox<Integer> clientNumber;


    public SubscriptionFormPanel(){
        setController(new ApplicationController());
        formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 2, 10, 15));

        formPanel.add(new JLabel("Price"));
        price = new JTextField();
        formPanel.add(price);

        formPanel.add(new JLabel("Possible discount"));
        discount = new JTextField();
        formPanel.add(discount);

        formPanel.add(new JLabel("Price payed"));
        pricePayed = new JCheckBox();
        formPanel.add(pricePayed);

        formPanel.add(new JLabel("Caution payed"));
        cautionPayed = new JCheckBox();
        formPanel.add(cautionPayed);

        formPanel.add(new JLabel("Start date"));
        startDate = new JDateChooser();
        formPanel.add(startDate);

        formPanel.add(new JLabel("End date"));
        endDate = new JDateChooser();
        formPanel.add(endDate);

        formPanel.add(new JLabel("Automatic renewal"));
        automaticRenewal = new JCheckBox();
        formPanel.add(automaticRenewal);

        String[] types = {"BRONZE","SILVER","GOLD"};
        formPanel.add(new JLabel("Type"));
        typeName = new JComboBox<>(types);
        formPanel.add(typeName);

        try{
            clientNumber = new JComboBox();
            for(Integer number : controller.getAllClientNumbers()){
                clientNumber.addItem(number);
            }
        }
        catch(UnfoundResearchException | ConnectionException exception){
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        formPanel.add(new JLabel("Client number"));
        formPanel.add(clientNumber);

        buttonsPanel = new JPanel();

        validationButton = new JButton("Validation");
        reinitialisationButton = new JButton("Reinitialisation");
        quitButton = new JButton("Quit");

        buttonsPanel.add(validationButton);
        buttonsPanel.add(reinitialisationButton);
        buttonsPanel.add(quitButton);

        add(formPanel,BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.SOUTH);

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        reinitialisationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to reset the form?", "Validation", JOptionPane.YES_NO_OPTION);
                if(response == JOptionPane.YES_OPTION) {
                    resetForm();
                    JOptionPane.showMessageDialog(null, "Form reset.", "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        validationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String errorMessage = checkForm();
                if (!errorMessage.isEmpty()) {
                    JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    if(discount.getText().isEmpty()){
                        discount.setText(null);
                    }
                    int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to validate your registration?", "Validation", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        JOptionPane.showMessageDialog(null, "Registration validated.", "Information", JOptionPane.INFORMATION_MESSAGE);
                        setVisible(false);
                        Subscription subscription = new Subscription(Integer.parseInt(price.getText()),
                                Double.parseDouble(discount.getText()),startDate.getDate(),endDate.getDate(),
                                automaticRenewal.isSelected(),pricePayed.isSelected(),cautionPayed.isSelected(),
                                (String) typeName.getSelectedItem(),(Integer) clientNumber.getSelectedItem());

                        try{
                            controller.addNewSubscription(subscription);
                        }
                        catch (ConnectionException exception){
                            JOptionPane.showMessageDialog(null, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                }
            }
        });
    }
    public String checkForm(){
        String errorMessage = "";
        if (price.getText().isEmpty()) {
            errorMessage += "Price field is mandatory\n";
        }
        if(price.getText().isEmpty() || !containsOnlyDigits(price.getText())){
            errorMessage += "The price must only contain digits\n";
        }
        if (startDate.getDate() == null) {
            errorMessage += "Start date field is mandatory\n";
        }
        if(Double.parseDouble(discount.getText()) >= 1.0){
            errorMessage += "Discount must be below 100%\n";
        }

        return errorMessage;
    }
    public void resetForm(){
        price.setText("");
        pricePayed.setSelected(false);
        cautionPayed.setSelected(false);
        startDate.setDate(null);
        typeName.setSelectedItem(0);
        automaticRenewal.setSelected(false);
    }
    public boolean containsOnlyDigits(String str) {
        // Utiliser une expression régulière pour vérifier si la chaîne ne contient que des chiffres
        String digitsRegex = "\\d+";
        return str.matches(digitsRegex);
    }
    public void setController(ApplicationController controller) {
        this.controller = controller;
    }

}