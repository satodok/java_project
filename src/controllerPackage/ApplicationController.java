package controllerPackage;

import modelPackage.*;
import businessPackage.*;
import exceptionPackage.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class ApplicationController {

    private BusinessManager businessManager;

    public ApplicationController(){
        businessManager = new BusinessManager();
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

}
