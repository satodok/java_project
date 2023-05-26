package controllerPackage;

import modelPackage.*;
import businessPackage.*;
import exceptionPackage.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class ApplicationController {

    private BusinessManager businessManager;

    public ApplicationController(){
        businessManager = new BusinessManager();
    }

    //Thread
    public ArrayList<Counter>getStock() throws ConnectionException, UnfoundResearchException{
        return businessManager.getStock();
    }
    //CRUD member
    public void deleteAllRelatedToMembers(ArrayList<String> nationalNumbers) throws ConnectionException, UnfoundResearchException{
        businessManager.deleteAllRelatedToMembers(nationalNumbers);
    }

    //CRUD member
    public ArrayList<Member> getAllMembers() throws ConnectionException,UnfoundResearchException{
       return businessManager.getAllMembers();
    }
    public void addMember(Member member) throws ConnectionException, ExistingElementException{
        businessManager.addMember(member);
    }
    public void updateMember(Member member) throws ConnectionException, WrongArgumentException, UnfoundResearchException, ExistingElementException{
        businessManager.updateMember(member);
    }
    public void addNewSubscription(Subscription subscription) throws ConnectionException {
        businessManager.addNewSubscription(subscription);
    }

    //Récupérer toutes les localités
    public ArrayList<String> getLocalities() throws ConnectionException, UnfoundResearchException{
        return businessManager.getLocalities();
    }
    // Recherche 1
    public ArrayList<String> getAllNationalNumbers() throws ConnectionException, UnfoundResearchException{
        return businessManager.getAllNationalNumbers();
    }

    public MemberAddress findMemberAdressByNationalNumber(String nationalNumber) throws UnfoundResearchException,ConnectionException{
        return businessManager.findMemberAddressByNationalNumber(nationalNumber);
    }

    //Recherche 2
    public ArrayList<DiscountMember> findMembersWithDiscountFromAgeRange(Integer ageMin, Integer ageMax) throws UnfoundResearchException, ConnectionException, MemberDiscountException{
        return businessManager.findMembersWithDiscountFromAgeRange(ageMin, ageMax);
    }

    //Recherche 3
    public ArrayList<Member> findMembersFromSubscriptionPlan(String subscriptionType) throws ConnectionException, UnfoundResearchException{
        return businessManager.findMembersFromSubscriptionPlan(subscriptionType);
    }

    //CRUD member
    public MemberInformations findMemberInformationsByNationalNumber(String nationalNumber) throws UnfoundResearchException,ConnectionException{
        return businessManager.findMemberInformationsByNationalNumber(nationalNumber);
    }

    //Recherche 4
    public ArrayList<RentalDetailsInformation> findRentalDetailsFromDateRange(Date startDate, Date endDate) throws ConnectionException, UnfoundResearchException, RentalDetailsException, WrongArgumentException{
        return businessManager.findRentalDetailsFromDateRange(startDate, endDate);
    }

    //CRUD subscription
    public Subscription findSubscriptionBySubscriptionID(Integer subscriptionID) throws UnfoundResearchException, ConnectionException{
        return businessManager.findSubscriptionBySubscriptionID(subscriptionID);
    }

    public void deleteSubscription(ArrayList<String> subscriptionID) throws ConnectionException, UnfoundResearchException{
        businessManager.deleteSubscription(subscriptionID);
    }

    public ArrayList<Subscription> getAllSubscription() throws ConnectionException, UnfoundResearchException{
        return businessManager.getAllSubscription();
    }

    public ArrayList<Integer> getAllClientNumbers() throws ConnectionException, UnfoundResearchException{
        return businessManager.getAllClientNumbers();
    }
    public ArrayList<Integer> getAllSUbscriptionIDs() throws ConnectionException, UnfoundResearchException{
        return businessManager.getAllSUbscriptionIDs();
    }
    public void updateSubscription(Subscription subscription) throws ConnectionException, WrongArgumentException, UnfoundResearchException, ExistingElementException{
        businessManager.updateSubscription(subscription);
    }

    public StatSubscription getStatSubscription() throws ConnectionException, UnfoundResearchException{
        return businessManager.performBusinessTask2();
    }

    public HashMap performBusinessTask1(ArrayList<Integer> stationNumbers) throws ConnectionException{
        return businessManager.performBusinessTask1(stationNumbers);
    }
}
