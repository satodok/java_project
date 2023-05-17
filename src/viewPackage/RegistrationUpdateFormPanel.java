package viewPackage;

import com.toedter.calendar.JDateChooser;
import controllerPackage.ApplicationController;
import exceptionPackage.ConnectionException;
import exceptionPackage.ExistingElementException;
import exceptionPackage.UnfoundResearchException;
import modelPackage.Member;
import modelPackage.MemberInformations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationUpdateFormPanel extends JPanel {
    private ApplicationController controller;
    private JPanel searchButtonPanel;
    private JButton searchButton;
    private JComboBox<String>nationalNumbersBox;
    private JPanel formPanel;
    private JPanel buttonsPanel;
    private JButton validationButton;
    private JButton quitButton;

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
    private JPanel comboBoxPannel;

    public RegistrationUpdateFormPanel() {
        controller = new ApplicationController();
        formPanel = new JPanel();
        comboBoxPannel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 2, 10, 15));

        nationalNumbersBox = new JComboBox<>();
        try {
            for (String nationalNumber : controller.getAllNationalNumbers()) {
                nationalNumbersBox.addItem(nationalNumber);
            }
        } catch (UnfoundResearchException | ConnectionException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        comboBoxPannel.add(nationalNumbersBox);
        searchButton = new JButton("FindMemberInfo");
        comboBoxPannel.add(searchButton);
        add(comboBoxPannel,CENTER_ALIGNMENT);

        buttonsPanel = new JPanel();
        validationButton = new JButton("Validation");
        quitButton = new JButton("Quit");

        buttonsPanel.add(validationButton);
        buttonsPanel.add(quitButton);
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
                    if (phoneNumberField.getText().isEmpty()) {
                        phoneNumberField.setText(null);
                    }
                    if (genderField.getText().isEmpty()) {
                        genderField.setText(null);
                    }
                    // Demander la validation du formulaire avant de le sauver
                    int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to validate your registration?", "Validation", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        JOptionPane.showMessageDialog(null, "Registration validated.", "Information", JOptionPane.INFORMATION_MESSAGE);
                        setVisible(false);
                        Member member = new Member((String) nationalNumbersBox.getSelectedItem(), lastNameField.getText(), firstNameField.getText(),
                                birthDateField.getDate(), phoneNumberField.getText(),
                                (String) genderField.getText(), emailAdressField.getText(),
                                newsletterField.isSelected(), streetField.getText(),
                                streetNumberField.getText(), (String) localityField.getSelectedItem());
                        try {
                            controller.updateMember(member);
                        } catch (ConnectionException | ExistingElementException | UnfoundResearchException exception) {
                            JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
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
                    MemberInformations member = controller.findMemberInformationsByNationalNumber((String)nationalNumbersBox.getSelectedItem());
                    comboBoxPannel.setVisible(false);

                    formPanel.add(new JLabel("Last name"));
                    lastNameField = new JTextField();
                    lastNameField.setText(member.getLastName());
                    formPanel.add(lastNameField);

                    formPanel.add(new JLabel("First name"));
                    firstNameField = new JTextField();
                    firstNameField.setText(member.getFirstName());
                    formPanel.add(firstNameField);

                    formPanel.add(new JLabel("Birth date"));
                    birthDateField = new JDateChooser();
                    birthDateField.setDate(member.getBirthDate());
                    formPanel.add(birthDateField);

                    formPanel.add(new JLabel("Phone Number"));
                    phoneNumberField = new JTextField();
                    phoneNumberField.setText(String.valueOf(member.getPhoneNumber()));
                    formPanel.add(phoneNumberField);

                    formPanel.add(new JLabel("Gender"));
                    genderField = new JTextField();
                    genderField.setText(member.getGender());
                    formPanel.add(genderField);

                    formPanel.add(new JLabel("Email address"));
                    emailAdressField = new JTextField();
                    emailAdressField.setText(member.getEmailAddress());
                    formPanel.add(emailAdressField);

                    formPanel.add(new JLabel("Street"));
                    streetField = new JTextField();
                    streetField.setText(member.getStreet());
                    formPanel.add(streetField);

                    formPanel.add(new JLabel("Street number"));
                    streetNumberField = new JTextField();
                    streetNumberField.setText(String.valueOf(member.getStreetNumber()));
                    formPanel.add(streetNumberField);
                    try {
                        localityField = new JComboBox();
                        for (String locality : controller.getLocalities()) {
                            localityField.addItem(locality);
                        }
                    } catch (UnfoundResearchException | ConnectionException exception) {
                        JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }

                    formPanel.add(new JLabel("Locality"));
                    localityField.setSelectedItem(member.getLocality());
                    formPanel.add(localityField);




                    formPanel.add(new JLabel("Do you want to subscribe to the newsletter?"));
                    newsletterField = new JCheckBox();
                    newsletterField.setSelected(member.getNewsLetter());
                    formPanel.add(newsletterField);


                    add(formPanel);
                    add(buttonsPanel);
                }
                catch(UnfoundResearchException | ConnectionException exception){
                    JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    };

    public String checkForm(){
        // Verification s'il y a des erreurs ou non
        String errorMessage = "";
        if (lastNameField.getText().isEmpty()) {
            errorMessage += "Last name field cannot be removed\n";
        }
        if (firstNameField.getText().isEmpty()) {
            errorMessage += "First name field cannot be removed\n";
        }
        if (!containsOnlyLetters(firstNameField.getText())) {
            errorMessage += "First name field must contain only letters\n";
        }
        if (!containsOnlyLetters(lastNameField.getText())) {
            errorMessage += "Last name field must contain only letters\n";
        }
        if (birthDateField.getDate() == null) {
            errorMessage += "Birth date field cannot be removed\n";
        }
        if ((!phoneNumberField.getText().isEmpty())){
            if(!containsOnlyDigits(phoneNumberField.getText()) ||phoneNumberField.getText().length() != 10 ||
                    !phoneNumberField.getText().startsWith("0")){
                errorMessage += "Phone number field must contain 10 digits and start with 0.\n";
            }
        }
        if (!genderField.getText().isEmpty() && !containsOnlyLetters(genderField.getText())) {
            errorMessage += "Gender field must contain only letters\n";
        }
        if (emailAdressField.getText().isEmpty()) {
            errorMessage += "Email address field cannot be removed\n";
        }
        if (!isValidEmailAddress(emailAdressField.getText())) {
            errorMessage += "Please, enter a valid email address.\n";
        }
        if (streetField.getText().isEmpty()) {
            errorMessage += "Street field cannot be removed\n";
        }
        if (!containsOnlyLetters(streetField.getText())) {
            errorMessage += "Street field must contain only letters.\n";
        }

        if (streetNumberField.getText().isEmpty()) {
            errorMessage += "Street number field cannot be removed\n";
        }
        if (!containsOnlyDigits(streetNumberField.getText())) {
            errorMessage += "Street number field must contain only digits.";
        }
        return errorMessage;

    }


    // Utiliser une expression régulière pour vérifier si la chaîne ne contient que des chiffres
    public boolean containsOnlyDigits(String str) {
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




}

