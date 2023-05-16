package dataAccessPackage;

import exceptionPackage.*;
import modelPackage.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public interface DataAccess {
    //Récupérer toutes les localités
    ArrayList<String> getLocalities() throws ConnectionException, UnfoundResearchException;

    // recherches
    public ArrayList<String> getAllNationalNumbers()throws ConnectionException, UnfoundResearchException;
    public MemberAddress findMemberAddressByNationalNumber(String nationalNumber) throws UnfoundResearchException, ConnectionException;
    public ArrayList<DiscountMember> findMembersWithDiscountFromAgeRange(GregorianCalendar dateMin, GregorianCalendar dateMax) throws UnfoundResearchException, ConnectionException;
    public ArrayList <Member> findMembersFromSubscriptionPlan(String subscriptionType) throws ConnectionException,UnfoundResearchException;
    public ArrayList<RentalDetailsInformation> findRentalDetailsFromDateRange(Date startDate, Date endDate) throws ConnectionException, UnfoundResearchException, WrongArgumentException;
    public MemberInformations findMemberInformationsByNationalNumber(String nationalNumber) throws UnfoundResearchException, ConnectionException;
    public Subscription findSubscriptionBySubscriptionID(String subscriptionID) throws UnfoundResearchException, ConnectionException;
    public void addNewSubscription(String subscriptionID, int price, Double discount, Date startDate, Date endDate, boolean automaticRenewal, boolean pricePayed, boolean cautionPayed, String typeName, int clientNumber) throws ConnectionException;
}
