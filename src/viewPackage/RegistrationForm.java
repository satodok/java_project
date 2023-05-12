package viewPackage;

import javax.swing.*;

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
    private JCheckBox maleGender;
    private JCheckBox femaleGender;
    private JCheckBox otherGender;
    private JTextField emailAdress;
    private JCheckBox newsletter;
    private JTextField street;
    private JTextField streetNumber;
    private JTextField locality;
    private JTextField postalCode;

    public RegistrationForm(){

    }
}
