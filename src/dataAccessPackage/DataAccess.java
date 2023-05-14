package dataAccessPackage;

import modelPackage.MemberAddress;
import modelPackage.discountMemberAgeRangeModel;

import java.util.ArrayList;
import java.util.Date;

public interface DataAccess {
    MemberAddress findMemberAdressByNationalNumber(Integer nationalNumber);
    ArrayList<discountMemberAgeRangeModel> findMembersWithDiscountFromAgeRange(Integer ageMin, Integer ageMax);
    //Member findMembersFromSubscriptionPlan(String subscriptionType);
    //RentalDetails findRentalDetailsFromDateRange(Date startDate, Date endDate);



}
