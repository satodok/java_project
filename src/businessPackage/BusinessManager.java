package businessPackage;
import modelPackage.*;
import exceptionPackage.*;
import dataAccessPackage.*;

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

    // CRUD member
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

    public ArrayList<Integer> getAllSUbscriptionIDs() throws ConnectionException, UnfoundResearchException{
        return dao.getAllSUbscriptionIDs();
    }
    public void updateSubscription(Subscription subscription) throws ConnectionException, WrongArgumentException, UnfoundResearchException, ExistingElementException{
        dao.updateSubscription(subscription);
    }
}
