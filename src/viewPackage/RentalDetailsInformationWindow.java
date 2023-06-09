package viewPackage;

import controllerPackage.ApplicationController;
import exceptionPackage.ConnectionException;
import exceptionPackage.RentalDetailsException;
import exceptionPackage.UnfoundResearchException;
import modelPackage.RentalDetailsInformation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class RentalDetailsInformationWindow extends JFrame {
    private ApplicationController controller;

    private GregorianCalendar startDate = new GregorianCalendar();
    private GregorianCalendar endDate = new GregorianCalendar();
    private JPanel panel = new JPanel();

    public RentalDetailsInformationWindow() {
        super("Research rental details in range");
        this.setBounds(100, 100, 600, 600);
        setController(new ApplicationController());

        JButton searchButton = new JButton("Search for range");
        panel.add(searchButton); // Ajoute le bouton de recherche au panneau
        setVisible(true);
        add(panel, BorderLayout.NORTH);


        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Demande de startDate et endDate en utilisant JOptionPane
                String startDateInput = JOptionPane.showInputDialog(null, "Enter the start date (yyyy-mm-dd):");
                String endDateInput = JOptionPane.showInputDialog(null, "Enter the end date (yyyy-mm-dd):");

                try {

                    // Conversion des dates en GregorianCalendar
                    startDate.setTime(Date.valueOf(startDateInput));
                    endDate.setTime(Date.valueOf(endDateInput));

                    ArrayList<RentalDetailsInformation> rentals = controller.findRentalDetailsFromDateRange(new java.sql.Date(startDate.getTimeInMillis()), new java.sql.Date(endDate.getTimeInMillis()));
                    RentalDetailsInformationModel model = new RentalDetailsInformationModel(rentals);

                    JTable table = new JTable(model);
                    JScrollPane scrollPane = new JScrollPane(table);

                    add(scrollPane, BorderLayout.SOUTH);
                    setVisible(true);

                } catch (UnfoundResearchException  | ConnectionException exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage(),
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                }
                catch (IllegalArgumentException | RentalDetailsException exception) {
                    JOptionPane.showMessageDialog(null, "Erreur : mauvaise donnée entrée",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }

    public void setController(ApplicationController controller) {
        this.controller = controller;
    }
}
