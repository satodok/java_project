package viewPackage;

import controllerPackage.ApplicationController;
import exceptionPackage.ConnectionException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BikesRemainingPercentsWindow extends JFrame {

    ApplicationController controller;

    public BikesRemainingPercentsWindow() {
        // Création de la fenêtre
        JFrame frame = new JFrame("Research member discount");
        frame.setBounds(100, 100, 400, 400);
        controller = new ApplicationController();

        // Création des composants de la fenêtre
        JLabel instructionLabel = new JLabel("Entrez les numéros de station (séparés par une virgule) :");
        JTextField stationNumbersTextField = new JTextField(20);
        JButton compareButton = new JButton("Comparer");

        compareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String stationNumbersText = stationNumbersTextField.getText();
                String[] stationNumberStrings = stationNumbersText.split(",");
                ArrayList<Integer> stationNumbers = new ArrayList<>();
                String message = "";
                for (String stationNumberString : stationNumberStrings) {
                    int stationNumber = Integer.parseInt(stationNumberString.trim());
                    stationNumbers.add(stationNumber);

                    try {
                        HashMap<Integer, Double> dictPercents = controller.performBusinessTask1(stationNumbers);

                        // Ajouter des paires clé-valeur à la map
                        Set<Map.Entry<Integer, Double>> entries = dictPercents.entrySet();

                        for (Map.Entry<Integer, Double> entry : entries) {
                            Integer key = entry.getKey();
                            Double value = entry.getValue();

                            message += "Station " + key + ": " + value + "% de vélos utilisés\n";
                        }


                    } catch (ConnectionException exception) {
                        exception.printStackTrace();
                    }
                }
                JOptionPane.showMessageDialog(null, message);
            }
        });



        JPanel contentPane = new JPanel();
        contentPane.add(instructionLabel);
        contentPane.add(stationNumbersTextField);
        contentPane.add(compareButton);

        // Définition du panneau de contenu de la fenêtre
        frame.setContentPane(contentPane);
        frame.pack();
        frame.setVisible(true);
    }
}
