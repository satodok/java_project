package dataAccessPackage;

import modelPackage.MemberAddress;

import java.util.Date;

public interface DataAccess {
    MemberAddress findMemberAdressByNationalNumber(Integer nationalNumber);
    //MemberDiscount findMembersWithDiscountFromAgeRange(Integer ageMin, Integer ageMax);
    //Member findMembersFromSubscriptionPlan(String subscriptionType);
    //RentalDetails findRentalDetailsFromDateRange(Date startDate, Date endDate);



}
