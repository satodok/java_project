package viewPackage;

import javax.swing.*;
import java.awt.*;

public class MemberInformations extends JFrame{

    public MemberInformations(){
        super("My informations");
        this.setBounds(100,100,400,400);
        String nationalNumberInupt = JOptionPane.showInputDialog(null, "Please enter your national number");
        Integer nationalNumber = Integer.parseInt(nationalNumberInupt);

        // Après récupération, faire recherche dans la base de données pour afficher les informations du membre
        // Vérifier si le format de l'entrée est bonne
    }
}
