package viewPackage;

import javax.swing.*;

public class UnsubscribeWindow extends JFrame {

    public UnsubscribeWindow(){
        super("Subscribe");
        this.setBounds(100,100,400,400);
        String subscriptionIDInupt = JOptionPane.showInputDialog(null, "Please enter your actual subscription ID");
        int subscriptionID = Integer.parseInt(subscriptionIDInupt);
    }
}
