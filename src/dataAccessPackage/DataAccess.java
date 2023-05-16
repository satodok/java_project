package dataAccessPackage;

import exceptionPackage.*;
import modelPackage.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public interface DataAccess {
    //Récupérer toutes les localités
    public ArrayList<String> getLocalities() throws ConnectionException, UnfoundResearchException;

    // CRUD member
    public void addAddress(String streetNumber, String street, String locality) throws ConnectionException, ExistingElementException;
    public void addMember(Member member) throws ConnectionException, ExistingElementException;

    // recherches
    public ArrayList<String> getAllNationalNumbers()throws ConnectionException, UnfoundResearchException;
    public MemberAddress findMemberAddressByNationalNumber(String nationalNumber) throws UnfoundResearchException, ConnectionException;
    public ArrayList<DiscountMember> findMembersWithDiscountFromAgeRange(GregorianCalendar dateMin, GregorianCalendar dateMax) throws UnfoundResearchException, ConnectionException;
    public ArrayList <Member> findMembersFromSubscriptionPlan(String subscriptionType) throws ConnectionException,UnfoundResearchException;
    public ArrayList<RentalDetailsInformation> findRentalDetailsFromDateRange(Date startDate, Date endDate) throws ConnectionException, UnfoundResearchException, RentalDetailsException, WrongArgumentException;
    public MemberInformations findMemberInformationsByNationalNumber(String nationalNumber) throws UnfoundResearchException, ConnectionException;
    public Subscription findSubscriptionBySubscriptionID(String subscriptionID) throws UnfoundResearchException, ConnectionException;

    void addNewSubscription(String subscriptionID, int price, Double discount, Date startDate, Date endDate, boolean automaticRenewal, boolean pricePayed, boolean cautionPayed, String typeName, int clientNumber) throws ConnectionException;
}
