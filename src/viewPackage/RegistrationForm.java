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

    private JTextField nationalNumberField;
    private JTextField lastNameField;
    private JTextField firstNameField;
    private JDateChooser birthDateField;
    private JTextField phoneNumberField;
    private JComboBox<String> genderComboBox;
    private JTextField emailAdressField;
    private JTextField streetField;
    private JTextField streetNumberField;
    private JTextField localityField;
    private JTextField postalCodeField;
    private JCheckBox newsletterField;

    public void resetForm(){
        nationalNumberField.setText("");
        lastNameField.setText("");
        firstNameField.setText("");
        phoneNumberField.setText("");
        genderComboBox.setSelectedIndex(0);
        emailAdressField.setText("");
        birthDateField.setDate(null);
        streetField.setText("");
        streetNumberField.setText("");
        localityField.setText("");
        postalCodeField.setText("");
        newsletterField.setSelected(false);
    }

    public boolean validForm(){
        return false;
    }

    public RegistrationForm(){
        formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 2, 10, 15));

        formPanel.add(new JLabel("National number"));
        nationalNumberField = new JTextField();
        formPanel.add(nationalNumberField);

        formPanel.add(new JLabel("Last name"));
        lastNameField = new JTextField();
        formPanel.add(lastNameField);

        formPanel.add(new JLabel("First name"));
        firstNameField = new JTextField();
        formPanel.add(firstNameField);

        formPanel.add(new JLabel("Birth date"));
        birthDateField = new JDateChooser();
        formPanel.add(birthDateField);

        formPanel.add(new JLabel("Phone Number"));
        phoneNumberField = new JTextField();
        formPanel.add(phoneNumberField);

        formPanel.add(new JLabel("Gender"));
        genderComboBox = new JComboBox<>();
        genderComboBox.addItem("Male");
        genderComboBox.addItem("Female");
        genderComboBox.addItem("Other");
        formPanel.add(genderComboBox);

        formPanel.add(new JLabel("Email address"));
        emailAdressField = new JTextField();
        formPanel.add(emailAdressField);

        streetField = new JTextField();
        streetNumberField = new JTextField();
        localityField = new JTextField();
        postalCodeField = new JTextField();
        formPanel.add(new JLabel("Street"));
        formPanel.add(streetField);
        formPanel.add(new JLabel("Street number"));
        formPanel.add(streetNumberField);
        formPanel.add(new JLabel("Locality"));
        formPanel.add(localityField);
        formPanel.add(new JLabel("Postal code"));
        formPanel.add(postalCodeField);

        formPanel.add(new JLabel("Do you want to subscribe to the newsletter?"));
        newsletterField = new JCheckBox();
        formPanel.add(newsletterField);

        buttonsPanel = new JPanel();

        validationButton = new JButton("Validation");
        reinitialisationButton = new JButton("Reinitialisation");

        buttonsPanel.add(validationButton);
        buttonsPanel.add(reinitialisationButton);

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
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to validate your registration?", "Validation", JOptionPane.YES_NO_OPTION);
                if(response == JOptionPane.YES_OPTION){
                    if(nationalNumberField.getText().length() == 11){
                        int nationalNumber = Integer.parseInt(nationalNumberField.getText());
                        JOptionPane.showMessageDialog(null,"Bon numéro","Bon",JOptionPane.INFORMATION_MESSAGE);
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"mauvais numéro","mauvais",JOptionPane.INFORMATION_MESSAGE);
                    }
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
