package dataAccessPackage;

import exceptionPackage.*;
import modelPackage.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public interface DataAccess {
    //Récupérer toutes les localités
    public ArrayList<String> getLocalities() throws ConnectionException, UnfoundResearchException;

    //Thread
    ArrayList<Counter>getStock() throws ConnectionException, UnfoundResearchException;

    // CRUD member
    public ArrayList<Member> getAllMembers() throws ConnectionException,UnfoundResearchException;
    public void deleteAllRelatedToMembers(ArrayList<String> nationalNumbers) throws ConnectionException, UnfoundResearchException;
    public int getPostalCode(String locality) throws ConnectionException, WrongArgumentException;
    public void updateAddress(Integer streetNumber, String street, String locality) throws ConnectionException, WrongArgumentException;
    public void addAddress(Integer streetNumber, String street, String locality) throws ConnectionException, ExistingElementException;
    public void addMember(Member member) throws ConnectionException, ExistingElementException;
    public void updateMember(Member member) throws ConnectionException, WrongArgumentException, UnfoundResearchException, ExistingElementException;

    // recherches
    ArrayList<String> getAllNationalNumbers()throws ConnectionException, UnfoundResearchException;
    MemberAddress findMemberAddressByNationalNumber(String nationalNumber) throws UnfoundResearchException, ConnectionException;
    ArrayList<DiscountMember> findMembersWithDiscountFromAgeRange(GregorianCalendar dateMin, GregorianCalendar dateMax) throws UnfoundResearchException, ConnectionException;
    ArrayList <Member> findMembersFromSubscriptionPlan(String subscriptionType) throws ConnectionException,UnfoundResearchException;
    ArrayList<RentalDetailsInformation> findRentalDetailsFromDateRange(Date startDate, Date endDate) throws ConnectionException, UnfoundResearchException, RentalDetailsException, WrongArgumentException;
    MemberInformations findMemberInformationsByNationalNumber(String nationalNumber) throws UnfoundResearchException, ConnectionException;
    Subscription findSubscriptionBySubscriptionID(Integer subscriptionID) throws UnfoundResearchException, ConnectionException;
    void addNewSubscription(Subscription subscription) throws ConnectionException;
    void deleteSubscription(ArrayList<String> subscriptionID) throws ConnectionException, UnfoundResearchException;
    ArrayList<Subscription> getAllSubscription() throws ConnectionException, UnfoundResearchException;
    ArrayList<Integer> getAllClientNumbers() throws ConnectionException, UnfoundResearchException;
    ArrayList<Integer> getBikesRemainingInStation(ArrayList<Integer> numerosStaion) throws ConnectionException;
    ArrayList<Integer> getAllSUbscriptionIDs() throws ConnectionException, UnfoundResearchException;
    void updateSubscription(Subscription subscription) throws ConnectionException, WrongArgumentException, UnfoundResearchException, ExistingElementException;
    ArrayList<SubscriptionInfo>getStatSubscription()throws ConnectionException, UnfoundResearchException;
}
