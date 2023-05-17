package viewPackage;

import com.toedter.calendar.JDateChooser;
import controllerPackage.ApplicationController;
import exceptionPackage.ConnectionException;
import exceptionPackage.ExistingElementException;
import exceptionPackage.UnfoundResearchException;
import modelPackage.Subscription;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SubscriptionUpdateFormPanel extends JPanel{
    private ApplicationController controller;
    private JPanel formPanel;
    private JPanel comboBoxPanel;
    private JPanel buttonsPanel;

    private JComboBox<Integer> subscriptionIDBox;
    private JTextField price;
    private JTextField discount;
    private JCheckBox pricePayed;
    private JCheckBox cautionPayed;
    private JDateChooser startDate;
    private JDateChooser endDate;
    private JCheckBox automaticRenewal;
    private JComboBox<String> typeName;
    private JComboBox<Integer> clientNumber;

    private JButton searchButton;
    private JButton validationButton;
    private JButton quitButton;

    public SubscriptionUpdateFormPanel(){
        controller = new ApplicationController();
        formPanel = new JPanel();
        formPanel.setLayout((new GridLayout(0,2,10,15)));
        comboBoxPanel = new JPanel();

        subscriptionIDBox = new JComboBox<>();
        try{
            for(Integer subscriptionID : controller.getAllSUbscriptionIDs()){
                subscriptionIDBox.addItem(subscriptionID);
            }
        }catch (UnfoundResearchException | ConnectionException exception){
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        comboBoxPanel.add(subscriptionIDBox);
        searchButton = new JButton("Find subscription ID");
        comboBoxPanel.add(searchButton);
        add(comboBoxPanel,CENTER_ALIGNMENT);
        add(formPanel,CENTER_ALIGNMENT);


        buttonsPanel = new JPanel();
        validationButton = new JButton("Validation");
        quitButton = new JButton("Quit");
        buttonsPanel.add(validationButton);
        buttonsPanel.add(quitButton);
        add(buttonsPanel,CENTER_ALIGNMENT);

        validationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String errorMessage = checkForm();
                if(!errorMessage.isEmpty()){
                    JOptionPane.showMessageDialog(null,errorMessage,"Error",JOptionPane.ERROR_MESSAGE);
                }
                else{
                    int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to validate your registration?", "Validation", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        JOptionPane.showMessageDialog(null, "Registration validated.", "Information", JOptionPane.INFORMATION_MESSAGE);
                        setVisible(false);
                        Subscription subscription = new Subscription((Integer) subscriptionIDBox.getSelectedItem(), Integer.parseInt(price.getText()),
                                Double.parseDouble(discount.getText()),startDate.getDate(),endDate.getDate(),
                                automaticRenewal.isSelected(),pricePayed.isSelected(),cautionPayed.isSelected(),
                                (String) typeName.getSelectedItem(),(Integer) clientNumber.getSelectedItem());

                        try{
                            controller.updateSubscription(subscription);
                        }
                        catch (ConnectionException | ExistingElementException | UnfoundResearchException exception){
                            JOptionPane.showMessageDialog(null, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Subscription subscription = controller.findSubscriptionBySubscriptionID((Integer) subscriptionIDBox.getSelectedItem());
                    comboBoxPanel.setVisible(false);

                    formPanel.add(new JLabel("Price"));
                    price = new JTextField(String.valueOf(subscription.getPrice()));
                    formPanel.add(price);

                    formPanel.add(new JLabel("Possible discount"));
                    discount = new JTextField(String.valueOf(subscription.getDiscount()));
                    formPanel.add(discount);

                    formPanel.add(new JLabel("Price payed"));
                    pricePayed = new JCheckBox();
                    pricePayed.setSelected(subscription.getPricePayed());
                    formPanel.add(pricePayed);

                    formPanel.add(new JLabel("Caution payed"));
                    cautionPayed = new JCheckBox();
                    cautionPayed.setSelected(subscription.getCautionPayed());
                    formPanel.add(cautionPayed);

                    formPanel.add(new JLabel("Start date"));
                    startDate = new JDateChooser(subscription.getStartDate());
                    formPanel.add(startDate);

                    formPanel.add(new JLabel("End date"));
                    endDate = new JDateChooser(subscription.getEndDate());
                    formPanel.add(endDate);

                    formPanel.add(new JLabel("Automatic renewal"));
                    automaticRenewal = new JCheckBox();
                    automaticRenewal.setSelected(subscription.getAutomaticRenewal());
                    formPanel.add(automaticRenewal);

                    String[] types = {"BRONZE","SILVER","GOLD"};
                    formPanel.add(new JLabel("Type"));
                    typeName = new JComboBox<>(types);
                    typeName.setSelectedItem(subscription.getTypeName());
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
                }
                catch(UnfoundResearchException | ConnectionException exception){
                    JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }
    public String checkForm() {
        String errorMessage = "";
        if (price.getText().isEmpty()) {
            errorMessage += "Price field is mandatory\n";
        }
        if (!price.getText().isEmpty() && !containsOnlyDigits(price.getText())) {
            errorMessage += "The price must only contain digits\n";
        }
        if (startDate.getDate() == null) {
            errorMessage += "Start date field is mandatory\n";
        }
        if (discount.getText().isEmpty()) {
            discount.setText("0"); // Valeur par défaut si le champ est vide
        }
        if (Double.parseDouble(discount.getText()) >= 1.0) {
            errorMessage += "Discount must be below 100%\n";
        }
        if (Double.parseDouble(discount.getText()) < 0) {
            errorMessage += "Discount must be up 0%\n";
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
        String digitsRegex = "\\d+";
        return str.matches(digitsRegex);
    }
    public void setController(ApplicationController controller) {
        this.controller = controller;
    }
}
