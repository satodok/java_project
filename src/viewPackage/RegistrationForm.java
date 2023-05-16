package viewPackage;

import javax.swing.*;
import java.awt.*;
import com.toedter.calendar.JDateChooser;
import controllerPackage.*;
import exceptionPackage.ConnectionException;
import exceptionPackage.UnfoundResearchException;
import modelPackage.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegistrationForm extends JPanel{
    private ApplicationController controller;
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
    private JComboBox localityField;
    private JCheckBox newsletterField;
    private JButton quitButton;


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
        localityField.setSelectedItem(0);
        newsletterField.setSelected(false);
    }

    public boolean validForm(){
        return false;
    }

    public RegistrationForm(){
        setController(new ApplicationController());
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
        try{
            localityField = new JComboBox();
            for(String locality : controller.getLocalities()){
                localityField.addItem(locality);
            }
        }
        catch(UnfoundResearchException | ConnectionException exception){
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        formPanel.add(new JLabel("Street"));
        formPanel.add(streetField);

        formPanel.add(new JLabel("Street number"));
        formPanel.add(streetNumberField);

        formPanel.add(new JLabel("Locality"));
        formPanel.add(localityField);

        formPanel.add(new JLabel("Do you want to subscribe to the newsletter?"));
        newsletterField = new JCheckBox();

        formPanel.add(newsletterField);

        buttonsPanel = new JPanel();

        validationButton = new JButton("Validation");
        reinitialisationButton = new JButton("Reinitialisation");
        quitButton = new JButton("Quit");

        buttonsPanel.add(validationButton);
        buttonsPanel.add(reinitialisationButton);
        buttonsPanel.add(quitButton);

        add(formPanel,BorderLayout.CENTER);
        add(buttonsPanel,BorderLayout.SOUTH);
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

                String errorMessage = "";
                if (nationalNumberField.getText().isEmpty()) {
                    errorMessage += "National number field is mandatory\n";
                }
                if (!nationalNumberField.getText().isEmpty() && nationalNumberField.getText().length() != 11) {
                    errorMessage += "Please, enter a nationalNumber of 11 numbers.\n";
                }
                if (lastNameField.getText().isEmpty()) {
                    errorMessage += "Last name field is mandatory\n";
                }
                if (firstNameField.getText().isEmpty()) {
                    errorMessage += "First name field is mandatory\n";
                }
                // Vérifier si le nom et le prénom sont valides
                if (!firstNameField.getText().isEmpty() && !lastNameField.getText().isEmpty() && !validNames()) {
                    errorMessage += "First name and last name should contain only letters\n";
                }
                if (birthDateField.getDate() == null) {
                    errorMessage += "Birthdate field is mandatory\n";
                }
                if (!phoneNumberField.getText().isEmpty()) {
                    try {
                        Integer phoneNumber = Integer.parseInt(phoneNumberField.getText());

                    } catch (NumberFormatException ex) {
                        errorMessage += "Please enter a valid phone number of 10 to 12 numbers.\n";
                    }
                }
                if (emailAdressField.getText().isEmpty()) {
                    errorMessage += "Email address field is mandatory\n";
                }
                if (!emailAdressField.getText().isEmpty() && !isValidEmailAddress(emailAdressField.getText())) {
                    errorMessage += "Please, enter a valid email address.\n";
                }
                if (streetField.getText().isEmpty()) {
                    errorMessage += "Street field is mandatory\n";
                }
                if (!streetField.getText().isEmpty() && containsOnlyDigits(streetField.getText())) {
                    errorMessage += "Street field should not contain any digits.\n";
                }

                if (streetNumberField.getText().isEmpty()) {
                    errorMessage += "Street number field is mandatory\n";
                }
                if (!streetNumberField.getText().isEmpty()) {
                    try {
                        Integer streetNumber = Integer.parseInt(streetNumberField.getText());

                    } catch (NumberFormatException ex) {
                        errorMessage += "Please enter a valid street number.\n";
                    }
                }
                if (!newsletterField.isSelected()) {
                    errorMessage += "NewsLetter field is mandatory\n";
                }


                // Si le message d'erreur n'est pas vide, afficher un msg d'erreur
                if (errorMessage != "") {
                    JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                }
                // Si tout est correct enregistrer les données dans la BD et valider le formulaire
                else {
                    int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to validate your registration?", "Validation", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        Member member = new Member(nationalNumberField.getText(), lastNameField.getText(), firstNameField.getName(),
                                birthDateField.getDate(), Integer.parseInt(phoneNumberField.getText()),
                                (String) genderComboBox.getSelectedItem(), emailAdressField.getText(),
                                newsletterField.isSelected(), streetField.getText(),
                                Integer.parseInt(streetNumberField.getText()), (String) localityField.getSelectedItem());

                        JOptionPane.showMessageDialog(null, "Registration validated.", "Information", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

    }
    //REGEX pour vérifier si les champs int contiennent que des chiffres
    public boolean containsOnlyDigits(String str) {
        // Utiliser une expression régulière pour vérifier si la chaîne ne contient que des chiffres
        String digitsRegex = "\\d+";
        return str.matches(digitsRegex);
    }

    // REGEX pour vérifier si les noms et prénoms sont valables
    public boolean validNames() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();

        // Utiliser une expression régulière pour vérifier si le nom et le prénom ne contiennent que des lettres
        String lettersRegex = "^[a-zA-Z]+$";
        boolean isFirstNameValid = firstName.matches(lettersRegex);
        boolean isLastNameValid = lastName.matches(lettersRegex);

        return isFirstNameValid && isLastNameValid;
    }
    // REGEX pour vérifier si l'adresse email est valable
    public boolean isValidEmailAddress(String email) {
        // Expression régulière pour vérifier si l'adresse e-mail est correctement formatée
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        // Objet Pattern pour matcher la regex avec l'adresse e-mail fournie
        Pattern pattern = Pattern.compile(emailRegex);

        // Matcher l'adresse e-mail fournie avec la regex
        Matcher matcher = pattern.matcher(email);

        // Retourner true si l'adresse e-mail est correctement formatée, sinon false
        return matcher.matches();
    }

    public void setController(ApplicationController controller) {
        this.controller = controller;
    }
}
