package businessPackage;
import modelPackage.*;
import exceptionPackage.*;
import dataAccessPackage.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class BusinessManager {
    private DataAccess dao;
    public BusinessManager(){
        setDao(new DBAccess());
    }

    public void setDao(DataAccess dao) {
        this.dao = dao;
    }

    //Thread
    public ArrayList<Counter>getStock() throws ConnectionException, UnfoundResearchException{
        return dao.getStock();
    }

    // CRUD member
    public void deleteAllRelatedToMembers(ArrayList<String> nationalNumbers) throws ConnectionException, UnfoundResearchException{
        dao.deleteAllRelatedToMembers(nationalNumbers);
    }
    public ArrayList<Member> getAllMembers() throws ConnectionException,UnfoundResearchException{
        return dao.getAllMembers();
    }
    public void addMember(Member member) throws ConnectionException, ExistingElementException{
        dao.addMember(member);
    }
    public void updateMember(Member member) throws ConnectionException, WrongArgumentException, UnfoundResearchException, ExistingElementException{
         dao.updateMember(member);
    }
    public void addNewSubscription(Subscription subscription) throws ConnectionException {
        dao.addNewSubscription(subscription);
    }
        //Récupérer toutes les localités
    public ArrayList<String> getLocalities() throws ConnectionException, UnfoundResearchException{
        return dao.getLocalities();
    }

    //Recherche 1
    public ArrayList<String> getAllNationalNumbers() throws ConnectionException, UnfoundResearchException{
        return dao.getAllNationalNumbers();
    }

    public MemberAddress findMemberAddressByNationalNumber(String nationalNumber) throws UnfoundResearchException,ConnectionException{
            return dao.findMemberAddressByNationalNumber(nationalNumber);
    }

    // Recherche 2
    public ArrayList<DiscountMember> findMembersWithDiscountFromAgeRange(Integer ageMin, Integer ageMax) throws UnfoundResearchException, ConnectionException, MemberDiscountException{

        GregorianCalendar dateMin, dateMax;

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int transformationDateMin = currentYear - ageMin;
        int transformationDateMax = currentYear - ageMax;

        dateMax = new GregorianCalendar(transformationDateMin, 11, 31);
        dateMin = new GregorianCalendar(transformationDateMax, 0, 1);

        return dao.findMembersWithDiscountFromAgeRange(dateMin, dateMax);
    }

    //Recherche 3
    public ArrayList<Member> findMembersFromSubscriptionPlan(String subscriptionType) throws ConnectionException, UnfoundResearchException{
        return dao.findMembersFromSubscriptionPlan(subscriptionType);
    }

    // CRUD member


    public MemberInformations findMemberInformationsByNationalNumber(String nationalNumber) throws UnfoundResearchException,ConnectionException{
        return dao.findMemberInformationsByNationalNumber(nationalNumber);
    }

    //recherche 4
    public ArrayList<RentalDetailsInformation> findRentalDetailsFromDateRange(Date startDate, Date endDate) throws ConnectionException, UnfoundResearchException, WrongArgumentException, RentalDetailsException {
        return dao.findRentalDetailsFromDateRange(startDate, endDate);
    }

    //CRUD subscription
    public Subscription findSubscriptionBySubscriptionID(String subscriptionID) throws UnfoundResearchException, ConnectionException{
        return dao.findSubscriptionBySubscriptionID(subscriptionID);
    }

    public void deleteSubscription(ArrayList<String> subscriptionID) throws ConnectionException, UnfoundResearchException{
        dao.deleteSubscription(subscriptionID);
    }

    public ArrayList<Subscription> getAllSubscription() throws ConnectionException, UnfoundResearchException{
        return dao.getAllSubscription();
    }

    public ArrayList<Integer> getAllClientNumbers() throws ConnectionException, UnfoundResearchException{
        return dao.getAllClientNumbers();
    }

    public void performBusinessTask1() {
        // Création de la fenêtre
        JFrame frame = new JFrame("Research member discount");
        frame.setBounds(100, 100, 400, 400);

        // Création des composants de la fenêtre
        JLabel instructionLabel = new JLabel("Entrez les numéros de station (séparés par une virgule) :");
        JTextField stationNumbersTextField = new JTextField(20);
        JButton compareButton = new JButton("Comparer");

        // Ajout d'un ActionListener au bouton "Comparer"
        compareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String stationNumbersText = stationNumbersTextField.getText();
                String[] stationNumberStrings = stationNumbersText.split(",");
                ArrayList<Integer> stationNumbers = new ArrayList<>();

                for (String stationNumberString : stationNumberStrings) {
                    int stationNumber = Integer.parseInt(stationNumberString.trim());
                    stationNumbers.add(stationNumber);


                    try {
                        // Appeler la fonction getBikesRemainingInStation avec les numéros de station obtenus
                        ArrayList<Integer> bikesRemaining = dao.getBikesRemainingInStation(stationNumbers);

                        int currentBikesRemaining = 0;
                        for (int i = 0; i < stationNumbers.size(); i++) {

                            int currentStationNumber = stationNumbers.get(i);
                            for (int j = 0; j < 3; j++) {
                                currentBikesRemaining += bikesRemaining.get(j); // Initialise currentBikesRemaining à la valeur de bikesRemaining correspondante
                            }
                            double percentageUsed = ((20.0 - currentBikesRemaining) / 20.0) * 100.0;
                            JOptionPane.showMessageDialog(null, "Station " + currentStationNumber + ": " + percentageUsed + "% de vélos utilisés");

                            currentBikesRemaining = 0; // Réinitialiser la valeur pour la prochaine station
                        }


                        stationNumbers.clear();

                    } catch (ConnectionException sqlException) {
                        sqlException.printStackTrace();
                    }
                }
            }
        });

        // Création du panneau de contenu et ajout des composants
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
