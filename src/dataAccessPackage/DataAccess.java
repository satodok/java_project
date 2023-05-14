package dataAccessPackage;

import exceptionPackage.ConnectionException;
import exceptionPackage.UnfoundException;
import modelPackage.MemberAddress;
import modelPackage.discountMemberAgeRangeModel;

import java.util.ArrayList;
import java.util.Date;

public interface DataAccess {
    public MemberAddress findMemberAdressByNationalNumber(Integer nationalNumber) throws UnfoundException, ConnectionException;
    ArrayList<discountMemberAgeRangeModel> findMembersWithDiscountFromAgeRange(Integer ageMin, Integer ageMax);
    //Member findMembersFromSubscriptionPlan(String subscriptionType);
    //RentalDetails findRentalDetailsFromDateRange(Date startDate, Date endDate);



}
