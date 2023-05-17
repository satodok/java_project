package viewPackage;

import javax.swing.*;
import java.awt.*;
import com.toedter.calendar.JDateChooser;
import controllerPackage.*;
import exceptionPackage.ConnectionException;
import exceptionPackage.ExistingElementException;
import exceptionPackage.UnfoundResearchException;
import modelPackage.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegistrationFormPanel extends JPanel{
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
    private JTextField genderField;
    private JTextField emailAdressField;
    private JTextField streetField;
    private JTextField streetNumberField;
    private JComboBox localityField;
    private JCheckBox newsletterField;
    private JButton quitButton;


    public RegistrationFormPanel(){
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
        genderField = new JTextField();
        formPanel.add(genderField);

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


                // Si le message d'erreur n'est pas vide, afficher un msg d'erreur
                String errorMessage = checkForm();
                if (!errorMessage.isEmpty()) {
                    JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                }

                // Si tout est correct valider le formulaire et enregistrer les données dans la BD
                else {
                    // transformer les valeurs facultatives en null si elles sont vides
                    if(phoneNumberField.getText().isEmpty()){
                        phoneNumberField.setText(null);
                    }
                    if(genderField.getText().isEmpty()){
                        genderField.setText(null);
                    }
                    // Demander la validation du formulaire avant de le sauver
                    int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to validate your registration?", "Validation", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        JOptionPane.showMessageDialog(null, "Registration validated.", "Information", JOptionPane.INFORMATION_MESSAGE);
                        setVisible(false);
                        Member member = new Member(nationalNumberField.getText(), lastNameField.getText(), firstNameField.getText(),
                                birthDateField.getDate(), phoneNumberField.getText(),
                                (String) genderField.getText(), emailAdressField.getText(),
                                newsletterField.isSelected(), streetField.getText(),
                                streetNumberField.getText(), (String) localityField.getSelectedItem());
                        try{
                            controller.addMember(member);
                        }
                        catch(ExistingElementException | ConnectionException exception){
                            JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                        }

                    }
                }
            }
        });

    }
    // Methode permettant de vérifier si le formulaire est valide ou non
    public String checkForm(){
        // Verification s'il y a des erreurs ou non
        String errorMessage = "";
        if (nationalNumberField.getText().isEmpty()) {
            errorMessage += "National number field is mandatory\n";
        }
        if (!nationalNumberField.getText().isEmpty() && nationalNumberField.getText().length() != 11
                || !containsOnlyDigits(nationalNumberField.getText())) {
            errorMessage += "Please, enter a national number of 11 numbers.\n";
        }
        if (lastNameField.getText().isEmpty()) {
            errorMessage += "Last name field is mandatory\n";
        }
        if (firstNameField.getText().isEmpty()) {
            errorMessage += "First name field is mandatory\n";
        }
        if (!firstNameField.getText().isEmpty() && !containsOnlyLetters(firstNameField.getText())) {
            errorMessage += "First name field must contain only letters\n";
        }
        if (!lastNameField.getText().isEmpty() && !containsOnlyLetters(lastNameField.getText())) {
            errorMessage += "Last name field must contain only letters\n";
        }
        if (birthDateField.getDate() == null) {
            errorMessage += "Birth date field is mandatory\n";
        }
        if ((!phoneNumberField.getText().isEmpty())){
            if(!containsOnlyDigits(phoneNumberField.getText()) ||phoneNumberField.getText().length() != 10){
                errorMessage += "Phone number field must contain 10 digits.\n";
            }
        }
        if (!genderField.getText().isEmpty() && !containsOnlyLetters(genderField.getText())) {
            errorMessage += "Gender field must contain only letters\n";
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
        if (!streetField.getText().isEmpty() && !containsOnlyLetters(streetField.getText())) {
            errorMessage += "Street field must contain only letters.\n";
        }

        if (streetNumberField.getText().isEmpty()) {
            errorMessage += "Street number field is mandatory\n";
        }
        if (!streetNumberField.getText().isEmpty() && !containsOnlyDigits(streetNumberField.getText())) {
            errorMessage += "Street number field must contain only digits.";
        }
        return errorMessage;

    }
    // Reinitialisation du formulaire
    public void resetForm(){
        nationalNumberField.setText("");
        lastNameField.setText("");
        firstNameField.setText("");
        phoneNumberField.setText("");
        genderField.setText("");
        emailAdressField.setText("");
        birthDateField.setDate(null);
        streetField.setText("");
        streetNumberField.setText("");
        localityField.setSelectedItem(0);
        newsletterField.setSelected(false);
    }

    //REGEX pour vérifier si les champs int contiennent que des chiffres
    public boolean containsOnlyDigits(String str) {
        // Utiliser une expression régulière pour vérifier si la chaîne ne contient que des chiffres
        String digitsRegex = "\\d+";
        return str.matches(digitsRegex);
    }
    // REGEX pour vérifier si les champs String ne contiennent que des lettres
    public boolean containsOnlyLetters(String input) {
        String lettersRegex = "^[a-zA-Z\\s-]+$";
        return input.matches(lettersRegex);
    }

    // REGEX pour vérifier si les noms et prénoms sont valables
    public boolean validNames() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();

        // REGEX pour vérifier si le nom et le prénom ne contiennent que des lettres
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
