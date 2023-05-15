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
        setController(new ApplicationController());
        nationalNumberInupt = JOptionPane.showInputDialog(null, "Please enter your national number");
        nationalNumber = Integer.parseInt(nationalNumberInupt);
        try{
            MemberAddress memberAddress = controller.findMemberAdressByNationalNumber(nationalNumber);
            MemberAddressModel model = new MemberAddressModel(memberAddress);
            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);
            this.add(scrollPane);
            setVisible(true);
        }
        catch (UnfoundResearchException unfoundResearchException){
            JOptionPane.showMessageDialog(null, unfoundResearchException.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        catch(ConnectionException connectionException){
            JOptionPane.showMessageDialog(null, connectionException.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void setController(ApplicationController controller) {
        this.controller = controller;
    }
}

