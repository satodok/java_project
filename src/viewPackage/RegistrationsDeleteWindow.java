package viewPackage;

import controllerPackage.ApplicationController;

import javax.swing.*;

public class RegistrationsDeleteWindow extends JFrame {
    private ApplicationController controller;
    private String nationalNumberInupt;
    private Integer nationalNumber;

    public RegistrationsDeleteWindow(){
        super("My informations");
        this.setBounds(100,100,400,400);
        setController(new ApplicationController());
        nationalNumberInupt = JOptionPane.showInputDialog(null, "Please enter your national number");
        nationalNumber = Integer.parseInt(nationalNumberInupt);

        // SUPPRIMER LA LIGNE
    }
    public void setController(ApplicationController controller) {
        this.controller = controller;
    }
}
