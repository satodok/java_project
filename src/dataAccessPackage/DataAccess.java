package dataAccessPackage;

import exceptionPackage.ConnectionException;
import exceptionPackage.UnfoundException;
import modelPackage.MemberAddress;
import modelPackage.discountMember;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public interface DataAccess {
    public MemberAddress findMemberAdressByNationalNumber(Integer nationalNumber) throws UnfoundException, ConnectionException;
    public ArrayList<discountMember> findMembersWithDiscountFromAgeRange(GregorianCalendar dateMin, GregorianCalendar dateMax) throws UnfoundException, ConnectionException;
    //Member findMembersFromSubscriptionPlan(String subscriptionType);
    //RentalDetails findRentalDetailsFromDateRange(Date startDate, Date endDate);



}
