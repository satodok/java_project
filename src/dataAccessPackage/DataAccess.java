package dataAccessPackage;

import exceptionPackage.ConnectionException;
import exceptionPackage.UnfoundResearchException;
import modelPackage.MemberAddress;
import modelPackage.DiscountMember;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public interface DataAccess {
    public MemberAddress findMemberAdressByNationalNumber(Integer nationalNumber) throws UnfoundResearchException, ConnectionException;
    public ArrayList<DiscountMember> findMembersWithDiscountFromAgeRange(GregorianCalendar dateMin, GregorianCalendar dateMax) throws UnfoundResearchException, ConnectionException;
    //Member findMembersFromSubscriptionPlan(String subscriptionType);
    //RentalDetails findRentalDetailsFromDateRange(Date startDate, Date endDate);



}
