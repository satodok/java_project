package viewPackage;

import controllerPackage.ApplicationController;

import javax.swing.*;
import java.awt.*;
import controllerPackage.ApplicationController;
import exceptionPackage.ConnectionException;
import exceptionPackage.UnfoundResearchException;
import modelPackage.MemberAddress;

public class MemberInformations extends JFrame{
    private ApplicationController controller;
    private JTextField nationalNumberInupt;
    private Integer nationalNumber;
    private JLabel nationalNumberLabel;

    public MemberInformations(){
        super("My informations");
        this.setBounds(100,100,600,600);
        setController(new ApplicationController());

    }
    public void setController(ApplicationController controller) {
        this.controller = controller;
    }
}

