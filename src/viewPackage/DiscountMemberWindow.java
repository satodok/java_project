package viewPackage;

import controllerPackage.ApplicationController;
import exceptionPackage.ConnectionException;
import exceptionPackage.MemberDiscountException;
import exceptionPackage.UnfoundResearchException;
import modelPackage.DiscountMember;

import javax.swing.*;
import java.util.ArrayList;

public class DiscountMemberWindow extends JFrame {

    private ApplicationController controller;
    private String ageMinInput, ageMaxInput;
    private Integer ageMin, ageMax;

    public DiscountMemberWindow(){
        super("Research member discount");
        this.setBounds(100,100,400,400);
        setController(new ApplicationController());

        ageMinInput = JOptionPane.showInputDialog(null, "Please enter the minimum age");
        ageMin = Integer.parseInt(ageMinInput);
        ageMaxInput = JOptionPane.showInputDialog(null, "Please enter the maximum age");
        ageMax = Integer.parseInt(ageMaxInput);

        try{
            ArrayList<DiscountMember> membersDiscount = controller.findMembersWithDiscountFromAgeRange(ageMin,ageMax);
            DiscountMemberModel model = new DiscountMemberModel(membersDiscount);
            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);
            this.add(scrollPane);
            setVisible(true);

        }
        catch(UnfoundResearchException unfoundResearchException){
            JOptionPane.showMessageDialog(null, unfoundResearchException.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        catch(ConnectionException connectionException){
            JOptionPane.showMessageDialog(null, connectionException.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        catch(MemberDiscountException memberDiscountException){
            JOptionPane.showMessageDialog(null, memberDiscountException.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }

    }



    public void setController(ApplicationController controller) {
        this.controller = controller;
    }
}
