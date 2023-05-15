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
    private String nationalNumberInupt;
    private Integer nationalNumber;

    public MemberInformations(){
        super("My informations");
        this.setBounds(100,100,400,400);
    }
    public void setController(ApplicationController controller) {
        this.controller = controller;
    }
}

