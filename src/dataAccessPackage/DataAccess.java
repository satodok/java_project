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
    public ArrayList<Member> getAllMembers() throws ConnectionException,UnfoundResearchException;
    public void deleteAllRelatedToMembers(ArrayList<String> nationalNumbers) throws ConnectionException, UnfoundResearchException;
    public int getPostalCode(String locality) throws ConnectionException, WrongArgumentException;
    public void updateAddress(Integer streetNumber, String street, String locality) throws ConnectionException, WrongArgumentException;
    public void addAddress(Integer streetNumber, String street, String locality) throws ConnectionException, ExistingElementException;
    public void addMember(Member member) throws ConnectionException, ExistingElementException;
    public void updateMember(Member member) throws ConnectionException, WrongArgumentException, UnfoundResearchException, ExistingElementException;

    // recherches
    public ArrayList<String> getAllNationalNumbers()throws ConnectionException, UnfoundResearchException;
    public MemberAddress findMemberAddressByNationalNumber(String nationalNumber) throws UnfoundResearchException, ConnectionException;
    public ArrayList<DiscountMember> findMembersWithDiscountFromAgeRange(GregorianCalendar dateMin, GregorianCalendar dateMax) throws UnfoundResearchException, ConnectionException;
    public ArrayList <Member> findMembersFromSubscriptionPlan(String subscriptionType) throws ConnectionException,UnfoundResearchException;
    public ArrayList<RentalDetailsInformation> findRentalDetailsFromDateRange(Date startDate, Date endDate) throws ConnectionException, UnfoundResearchException, RentalDetailsException, WrongArgumentException;
    public MemberInformations findMemberInformationsByNationalNumber(String nationalNumber) throws UnfoundResearchException, ConnectionException;
    public Subscription findSubscriptionBySubscriptionID(String subscriptionID) throws UnfoundResearchException, ConnectionException;
    public void addNewSubscription(Subscription subscription) throws ConnectionException;
    void deleteSubscription(ArrayList<String> subscriptionID) throws ConnectionException, UnfoundResearchException;
    public ArrayList<Subscription> getAllSubscription() throws ConnectionException, UnfoundResearchException;
    public ArrayList<Integer> getAllClientNumbers() throws ConnectionException, UnfoundResearchException;
    }
