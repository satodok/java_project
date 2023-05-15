package dataAccessPackage;

import exceptionPackage.*;
import modelPackage.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public interface DataAccess {

    // recherches
    public ArrayList<String> getAllNationalNumbers()throws ConnectionException, AllNationalNumbersException;
    public MemberAddress findMemberAddressByNationalNumber(String nationalNumber) throws UnfoundResearchException, ConnectionException;
    public ArrayList<DiscountMember> findMembersWithDiscountFromAgeRange(GregorianCalendar dateMin, GregorianCalendar dateMax) throws UnfoundResearchException, ConnectionException;
    public ArrayList <Member> findMembersFromSubscriptionPlan(String subscriptionType) throws ConnectionException,SubscriptionTypeException;
    //RentalDetails findRentalDetailsFromDateRange(Date startDate, Date endDate);



}
