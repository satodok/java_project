package viewPackage;

import javax.swing.*;
import java.awt.*;

public class RegistrationForm extends JPanel {
    private JPanel formPanel;
    private JPanel buttonsPanel;

    private JButton validationButton;
    private JButton backButton;
    private JButton reinitialisationButton;

    private JTextField nationalNumber;
    private JTextField lastName;
    private JTextField firstName;
    private JComboBox<Integer> yearOfBirth;
    private JComboBox<Integer> monthOfBirth;
    private JComboBox<Integer> dayOfBirth;
    private JTextField phoneNumber;
    private JComboBox<String> gender;
    private JTextField emailAdress;
    private JCheckBox newsletter;
    private JTextField street;
    private JTextField streetNumber;
    private JTextField locality;
    private JTextField postalCode;

    public RegistrationForm(){
        formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 2, 10, 10));

        formPanel.add(new JLabel("National number"));
        nationalNumber = new JTextField();
        formPanel.add(nationalNumber);

        formPanel.add(new JLabel("Last name"));
        lastName = new JTextField();
        formPanel.add(lastName);

        formPanel.add(new JLabel("First name"));
        firstName = new JTextField();
        formPanel.add(firstName);

        formPanel.add(new JLabel("Birth date"));
        Integer[] yearsOfBirth = {2000,2001,2002,2003}; // bibliothèque ?
        yearOfBirth = new JComboBox<>(yearsOfBirth);


        buttonsPanel = new JPanel();

        validationButton = new JButton("Validation");
        backButton = new JButton("Back");
        reinitialisationButton = new JButton("Reinitialisation");

        buttonsPanel.add(validationButton);
        buttonsPanel.add(backButton);
        buttonsPanel.add(reinitialisationButton);

        add(buttonsPanel,BorderLayout.SOUTH);
        add(formPanel,BorderLayout.CENTER);
    }
}
