package viewPackage;

import javax.swing.*;
import java.awt.*;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class RegistrationForm extends JPanel{
    private JPanel formPanel;
    private JPanel buttonsPanel;

    private JButton validationButton;
    private JButton reinitialisationButton;

    private JTextField nationalNumber;
    private JTextField lastName;
    private JTextField firstName;
    private JDateChooser birthDate;
    private JTextField phoneNumber;
    private JComboBox<String> gender;
    private JTextField emailAdress;
    private JTextField street;
    private JTextField streetNumber;
    private JTextField locality;
    private JTextField postalCode;
    private JCheckBox newsletter;

    public void resetForm(){
        nationalNumber.setText("");
        lastName.setText("");
        firstName.setText("");
        phoneNumber.setText("");
        gender.setSelectedIndex(0);
        emailAdress.setText("");
        birthDate.setDate(null);
        street.setText("");
        streetNumber.setText("");
        locality.setText("");
        postalCode.setText("");
        newsletter.setSelected(false);
    }

    public RegistrationForm(){
        formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 2, 10, 15));

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
        birthDate = new JDateChooser();
        formPanel.add(birthDate);

        formPanel.add(new JLabel("Phone Number"));
        phoneNumber = new JTextField();
        formPanel.add(phoneNumber);

        formPanel.add(new JLabel("Gender"));
        gender = new JComboBox<>();
        gender.addItem("Male");
        gender.addItem("Female");
        gender.addItem("Other");
        formPanel.add(gender);

        formPanel.add(new JLabel("Email address"));
        emailAdress = new JTextField();
        formPanel.add(emailAdress);

        street = new JTextField();
        streetNumber = new JTextField();
        locality = new JTextField();
        postalCode = new JTextField();
        formPanel.add(new JLabel("Street"));
        formPanel.add(street);
        formPanel.add(new JLabel("Street number"));
        formPanel.add(streetNumber);
        formPanel.add(new JLabel("Locality"));
        formPanel.add(locality);
        formPanel.add(new JLabel("Postal code"));
        formPanel.add(postalCode);

        formPanel.add(new JLabel("Do you want to subscribe to the newsletter?"));
        newsletter = new JCheckBox();
        formPanel.add(newsletter);

        buttonsPanel = new JPanel();

        validationButton = new JButton("Validation");
        reinitialisationButton = new JButton("Reinitialisation");

        buttonsPanel.add(validationButton);
        buttonsPanel.add(reinitialisationButton);

        reinitialisationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetForm();
                JOptionPane.showMessageDialog(null, "Form reset.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        validationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to validate your registration?", "Validation", JOptionPane.YES_NO_OPTION);
                if(response == JOptionPane.YES_OPTION){
                    // VERIFIER LES DONNEES
                    // SI ELLES SONT BONNES
                        // ENTRER LES DONNEES DANS LA BD
                        JOptionPane.showMessageDialog(null, "Registration validated.", "Information", JOptionPane.INFORMATION_MESSAGE);
                        resetForm();
                    // SI ELLES SONT PAS BONNES
                        // MESSAGE ERREUR ET RECOMMENCER
                }
            }
        });

        add(formPanel,BorderLayout.CENTER);
        add(buttonsPanel,BorderLayout.SOUTH);
    }
}
