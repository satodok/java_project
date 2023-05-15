package controllerPackage;

import modelPackage.*;
import businessPackage.*;
import exceptionPackage.*;

import java.util.ArrayList;

public class ApplicationController {

    private BusinessManager businessManager;

    public ApplicationController(){
        businessManager = new BusinessManager();
    }

    // Recherche 1
    public ArrayList<String> getAllNationalNumbers() throws ConnectionException, AllNationalNumbersException{
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
    public ArrayList<Member> findMembersFromSubscriptionPlan(String subscriptionType) throws ConnectionException, SubscriptionTypeException{
        return businessManager.findMembersFromSubscriptionPlan(subscriptionType);
    }

}
