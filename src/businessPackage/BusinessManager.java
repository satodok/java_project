package businessPackage;
import modelPackage.*;
import exceptionPackage.*;
import dataAccessPackage.*;

import javax.swing.*;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.math.BigDecimal;

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
    public Subscription findSubscriptionBySubscriptionID(Integer subscriptionID) throws UnfoundResearchException, ConnectionException{
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

    public HashMap performBusinessTask1(ArrayList<Integer> stationNumbers) {
        HashMap<Integer, Double> bikesRemainingPercentages = new HashMap();

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

                            bikesRemainingPercentages.put(currentStationNumber, percentageUsed);

                            currentBikesRemaining = 0; // Réinitialiser la valeur pour la prochaine station
                        }
                        stationNumbers.clear();

                    } catch (ConnectionException exception) {
                        exception.printStackTrace();
                    }
                    return bikesRemainingPercentages;
    }


    public ArrayList<Integer> getAllSUbscriptionIDs() throws ConnectionException, UnfoundResearchException{
        return dao.getAllSUbscriptionIDs();
    }
    public void updateSubscription(Subscription subscription) throws ConnectionException, WrongArgumentException, UnfoundResearchException, ExistingElementException{
        dao.updateSubscription(subscription);
    }

    public StatSubscription performBusinessTask2(){
        int cptGold = 0;
        int cptSilver = 0;
        int cptBronze = 0;
        int totalPriceGold = 0;
        int totalPriceSilver = 0;
        int totalPriceBronze = 0;
        int totalPrice = 0;
        int totalSub = 0;
        double percentGold = 0;
        double percentSilver = 0;
        double percentBronze = 0;

        StatSubscription statSubscription = null;

        try {
            for (SubscriptionInfo subscriptionInfo : dao.getStatSubscription()) {
                switch (subscriptionInfo.getType()) {
                    case "GOLD":
                        cptGold += 1;
                        totalPriceGold += subscriptionInfo.getPrice();
                        break;
                    case "SILVER":
                        cptSilver += 1;
                        totalPriceSilver += subscriptionInfo.getPrice();
                        break;
                    case "BRONZE":
                        cptBronze += 1;
                        totalPriceBronze += subscriptionInfo.getPrice();
                        break;
                }

                totalSub = cptGold + cptSilver + cptBronze;
                totalPrice = totalPriceGold + totalPriceSilver +totalPriceBronze;


                percentGold = ((double)cptGold / totalSub)*100;
                percentSilver = ((double)cptSilver/ totalSub)*100;
                percentBronze = ((double)cptBronze / totalSub)*100;

                BigDecimal bigDecimal1 = new BigDecimal(percentGold);
                BigDecimal goldDecimal = bigDecimal1.setScale(2, BigDecimal.ROUND_HALF_UP);

                BigDecimal bigDecimal2 = new BigDecimal(percentSilver);
                BigDecimal silverDecimal = bigDecimal2.setScale(2, BigDecimal.ROUND_HALF_UP);

                BigDecimal bigDecimal3 = new BigDecimal(percentBronze);
                BigDecimal bronzeDecimal = bigDecimal3.setScale(2, BigDecimal.ROUND_HALF_UP);


                statSubscription = new StatSubscription(totalSub, totalPrice, goldDecimal, silverDecimal, bronzeDecimal);


            }
        }

        catch (UnfoundResearchException | ConnectionException exception){
            JOptionPane.showMessageDialog(null, "Erreur",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        return statSubscription;
    }
}
