package viewPackage;

import controllerPackage.ApplicationController;
import exceptionPackage.ConnectionException;
import exceptionPackage.RentalDetailsException;
import exceptionPackage.UnfoundResearchException;
import modelPackage.RentalDetailsInformation;

import javax.swing.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class RentalDetailsInformationPanel extends JFrame {
    private ApplicationController controller;

    private GregorianCalendar startDate = new GregorianCalendar();
    private GregorianCalendar endDate = new GregorianCalendar();
    private JPanel panel = new JPanel();

    public RentalDetailsInformationPanel() {
        super("Research rental details in range");
        this.setBounds(100, 100, 400, 400);
        setController(new ApplicationController());

        JButton searchButton = new JButton("Search");
        panel.add(searchButton); // Ajoute le bouton de recherche au panneau

        String startDateInput = JOptionPane.showInputDialog(null, "Enter the start date (yyyy-mm-dd):");
        String endDateInput = JOptionPane.showInputDialog(null, "Enter the end date (yyyy-mm-dd):");

        searchButton.addActionListener(e -> {
            // Demande de startDate et endDate en utilisant JOptionPane

            try {
                // Conversion des dates en GregorianCalendar
                startDate.setTime(Date.valueOf(startDateInput));
                endDate.setTime(Date.valueOf(endDateInput));

                ArrayList<RentalDetailsInformation> rentals = controller.findRentalDetailsFromDateRange(new java.sql.Date(startDate.getTimeInMillis()), new java.sql.Date(endDate.getTimeInMillis()));
                RentalDetailsInformationModel model = new RentalDetailsInformationModel(rentals);
                JTable table = new JTable(model);
                JScrollPane scrollPane = new JScrollPane(table);
                getContentPane().removeAll(); // Supprime les composants existants de la fenêtre
                getContentPane().add(scrollPane); // Ajoute le tableau à la fenêtre
                revalidate(); // Actualise l'affichage de la fenêtre

            } catch (UnfoundResearchException unfoundResearchException) {
                JOptionPane.showMessageDialog(null, unfoundResearchException.getMessage(),
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            } catch (ConnectionException connectionException) {
                JOptionPane.showMessageDialog(null, connectionException.getMessage(),
                        "Erreur", JOptionPane.ERROR_MESSAGE);

            } catch (RentalDetailsException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage(),
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });



        getContentPane().add(panel); // Ajoute le panneau à la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void setController(ApplicationController controller) {
        this.controller = controller;
    }
}