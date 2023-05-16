package dataAccessPackage;

import modelPackage.*;
import exceptionPackage.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class DBAccess implements DataAccess{
    // Récupérer toutes les localités


    @Override
    public ArrayList<String> getLocalities() throws ConnectionException, UnfoundResearchException {
        try{
            ArrayList<String> localities = new ArrayList<>();
            Connection connection = SingletonConnection.getInstance("mdp");
            String sqlInstruction = "SELECT name FROM libiavelo.locality";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            ResultSet data = preparedStatement.executeQuery();

            String locality;
            while(data.next()){
                locality = data.getString("name");
                localities.add(locality);
            }
            return localities;
        }
        catch(SQLException sqlException){
            throw new UnfoundResearchException("Erreur : aucun résultat ne correspond à votre recherche.");
        }
    }

    // Recherche numéro 1
    @Override
    public ArrayList<String> getAllNationalNumbers() throws ConnectionException, UnfoundResearchException{
        try{
            ArrayList<String>nationalNumbers = new ArrayList<>();
            Connection connection = SingletonConnection.getInstance("mdp");
            String sqlInstruction = "SELECT nationalNumber FROM libiavelo.member";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            ResultSet data = preparedStatement.executeQuery();
            String nationalNumber;

            while(data.next()){
                nationalNumber = data.getString("nationalNumber");
                nationalNumbers.add(nationalNumber);
            }
            return nationalNumbers;
        }
        catch(SQLException sqlException){
            throw new UnfoundResearchException("Erreur : aucun résultat ne correspond à votre recherche.");
        }
        }


    @Override
    public MemberAddress findMemberAddressByNationalNumber(String nationalNumber) throws UnfoundResearchException, ConnectionException{

        try{
            MemberAddress memberAddress;
            Connection connection = SingletonConnection.getInstance("mdp");
            // Instruction
            String sqlInstruction = "SELECT m.firstName, m.lastName, a.street, a.streetNumber, l.postalCode, l.name\n" +
                    "FROM libiavelo.member m \n" +
                    "JOIN libiavelo.address a ON (m.street = a.street AND m.streetNumber = a.streetNumber)\n" +
                    "JOIN libiavelo.locality l ON(l.postalCode = a.postalCode AND l.name = a.locality)\n" +
                    "WHERE m.nationalNumber = ?";

            //Creation du preparedStatement a partir de l'instruction sql
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setString(1, nationalNumber);

            // executer la requete et recuperer le resultat
            ResultSet data = preparedStatement.executeQuery();
            data.next();
            memberAddress = new MemberAddress();
            String lastName = data.getString("lastName");
            String firstName = data.getString("firstName");
            String street = data.getString("street");
            String locality = data.getString("name");
            Integer streetNumber = data.getInt("streetNumber");
            Integer postalCode = data.getInt("postalCode");
            memberAddress.setFirstName(firstName);
            memberAddress.setLastName(lastName);
            memberAddress.setAddress(streetNumber + " "+ street + ", " + postalCode + " " + locality);
            return memberAddress;

        }
        catch(SQLException sqlException){
            throw new UnfoundResearchException("Erreur : aucun résultat ne correspond à votre recherche.");
        }

    }

@Override
    public ArrayList<DiscountMember> findMembersWithDiscountFromAgeRange(GregorianCalendar dateMin, GregorianCalendar dateMax) throws UnfoundResearchException, ConnectionException{

        try {
            Connection connection = SingletonConnection.getInstance("mdp");
            // Instruction
            String sql = "SELECT m.firstName, m.lastName, s.discount, c.clientNumber\n" +
                    "from member m\n" +
                    "inner join card c\n" +
                    "on m.clientNumber = c.clientNumber\n" +
                    "inner join subscription s\n" +
                    "on s.clientNumber = c.clientNumber\n" +
                    "where m.birthDate >= ? AND m.birthDate <= ?";

            //Creation du preparedStatement a partir de l'instruction sql
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, new java.sql.Date(dateMax.getTimeInMillis()));
            preparedStatement.setDate(2, new java.sql.Date(dateMin.getTimeInMillis()));

            // executer la requete et recuperer le resultat
            ResultSet data = preparedStatement.executeQuery();

            DiscountMember member;
            String firstName, lastName;
            Double discount;
            Integer  clientNumber;
            ArrayList<DiscountMember> members = new ArrayList<>();

            while(data.next()) {
                member = new DiscountMember();

                firstName = data.getString("firstName");
                member.setFirstName(firstName);

                lastName = data.getString("lastName");
                member.setLastName(lastName);

                discount = data.getDouble("discount");
                member.setDiscount(discount);

                clientNumber = data.getInt("clientNumber");
                member.setClientNumber(clientNumber);

                members.add(member);
            }

            return members;

        } catch (SQLException sqlException) {
            throw new UnfoundResearchException("Erreur : aucun résultat ne correspond à votre recherche.");
        }
    }

    // Recherche numéro 3


    @Override
    public ArrayList<Member> findMembersFromSubscriptionPlan(String subscriptionType) throws ConnectionException, UnfoundResearchException {
        try{
            Connection connection = SingletonConnection.getInstance("mdp");
            // Instruction SQL
            String sqlInstruction = "SELECT m.firstName, m.lastName, m.clientNumber\n" +
                    "FROM libiavelo.member m\n" +
                    "JOIN libiavelo.card c ON (c.clientNumber = m.clientNumber)\n" +
                    "JOIN libiavelo.subscription sub ON (sub.clientNumber = c.clientNumber)\n" +
                    "WHERE sub.typeName = ?";

            //Creation du preparedStatement a partir de l'instruction sql
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setString(1, subscriptionType);

            // executer la requete et recuperer le resultat
            ResultSet data = preparedStatement.executeQuery();
            Member member;
            ArrayList<Member>members = new ArrayList<>();
            while(data.next()){
                member = new Member();
                String firstName = data.getString("firstName");
                member.setFirstName(firstName);
                String lastName = data.getString("lastName");
                member.setLastName(lastName);
                Integer clientNumber = data.getInt("clientNumber");
                member.setClientNumber(clientNumber);

                members.add(member);
            }
            return members;

        }
        catch(SQLException sqlException){
            throw new  UnfoundResearchException("Erreur : aucun résultat ne correspond à votre recherche.");
        }
    }

    //recherche 4


    @Override
    public ArrayList<RentalDetailsInformation> findRentalDetailsFromDateRange(Date startDate, Date endDate) throws ConnectionException, UnfoundResearchException, WrongArgumentException{
        try {
            Connection connection = SingletonConnection.getInstance("mdp");
            // Instruction
            String sql = "Select s.name, b.typeName, c.clientNumber, m.firstName, m.lastName\n" +
                    "from station s\n" +
                    "inner join rental r\n" +
                    "on s.stationNumber = r.stationNumber\n" +
                    "inner join bike b\n" +
                    "on b.serialNumber = r.bikeSerialNumber\n" +
                    "inner join type t\n" +
                    "on t.typeName = b.typeName\n" +
                    "inner join card c\n" +
                    "on c.clientNumber = r.clientNumber\n" +
                    "inner join member m\n" +
                    "on m.clientNumber = c.clientNumber\n" +
                    "where r.startDate > ? And r.startDate < ?;";

            //Creation du preparedStatement a partir de l'instruction sql
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, startDate);
            preparedStatement.setDate(2, endDate);

            // executer la requete et recuperer le resultat
            ResultSet data = preparedStatement.executeQuery();

            ArrayList<RentalDetailsInformation> rentals = new ArrayList<>();
            RentalDetailsInformation rental;

            String firstName, lastName, name, type;
            Integer clientNumber;

            while(data.next()) {
                rental = new RentalDetailsInformation();

                name = data.getString("name");
                rental.setName(name);

                type = data.getString("typeName");
                rental.setType(type);

                clientNumber = data.getInt("clientNumber");
                rental.setClientNumber(clientNumber);

                firstName = data.getString("firstName");
                rental.setFirstName(firstName);

                lastName = data.getString("lastName");
                rental.setLastName(lastName);

                rentals.add(rental);
            }
            if(rentals.isEmpty()){
                throw new UnfoundResearchException("Erreur : aucun résultat ne correspond à votre recherche.");
            }
            return rentals;

        } catch (SQLException sqlException) {
            throw new IllegalArgumentException();
        }
    }
    @Override
    public MemberInformations findMemberInformationsByNationalNumber(String nationalNumber) throws UnfoundResearchException, ConnectionException {
        try {
            MemberInformations memberInformations;
            Connection connection = SingletonConnection.getInstance("mdp");
            // Instruction
            String sqlInstruction = "SELECT m.clientNumber, m.firstName, m.lastName, m.birthDate, m.phoneNumber, m.gender, m.email, m.newsletter, m.street, m.streetNumber,a.locality, a.postalCode\n" +
                    "FROM libiavelo.member m \n" +
                    "JOIN libiavelo.address a ON (m.street = a.street AND m.streetNumber = a.streetNumber) \n" +
                    "JOIN libiavelo.locality l ON (l.postalCode = a.postalCode AND l.name = a.locality)  \n" +
                    "WHERE m.nationalNumber = ?";

            //Creation du preparedStatement a partir de l'instruction sql
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setString(1, nationalNumber);

            // executer la requete et recuperer le resultat
            ResultSet data = preparedStatement.executeQuery();
            data.next();
            memberInformations = new MemberInformations();
            String lastName = data.getString("lastName");
            String firstName = data.getString("firstName");
            Date birthDate = data.getDate("birthDate");
            Integer phoneNumber = data.getInt("phoneNumber");
            String gender = data.getString("gender");
            String email = data.getString("email");
            Boolean newsletter = data.getBoolean("newsletter");
            String street = data.getString("street");
            String locality = data.getString("locality");
            Integer streetNumber = data.getInt("streetNumber");
            Integer postalCode = data.getInt("postalCode");

            memberInformations.setFirstName(firstName);
            memberInformations.setLastName(lastName);
            memberInformations.setBirthDate(birthDate);
            memberInformations.setPhoneNumber(phoneNumber);
            memberInformations.setGender(gender);
            memberInformations.setEmailAddress(email);
            memberInformations.setNewsletter(newsletter);
            memberInformations.setAddress(streetNumber + " "+ street + ", " + postalCode + " " + locality);
            return memberInformations;

        }
        catch(SQLException sqlException){
            throw new UnfoundResearchException("Erreur : aucun résultat ne correspond à votre recherche.");
        }
    }

    public Subscription findSubscriptionBySubscriptionID(String subscriptionID) throws UnfoundResearchException, ConnectionException{
        try{
            Subscription subscription;
            Connection connection = SingletonConnection.getInstance("mdp");
            String sqlInstruction = "SELECT s.subscriptionID, s.price, s.discount, s.startDate, s.endDate, s.automaticRenewal, s.pricePayed, s.cautionPayed, s.typeName, s.clientNumber\n" +
                    "FROM libiavelo.subscription s\n" +
                    "JOIN libiavelo.type t ON (s.typeName = t.typeName)\n" +
                    "JOIN libiavelo.card c ON (s.clientNumber = c.clientNumber)\n" +
                    "WHERE s.subscriptionID = ?";
            //Creation du preparedStatement a partir de l'instruction sql
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setString(1, subscriptionID);

            // executer la requete et recuperer le resultat
            ResultSet data = preparedStatement.executeQuery();
            data.next();
            subscription = new Subscription();

            int price = data.getInt("price");
            double discount = data.getDouble("discount");
            Date startDate = data.getDate("startDate");
            Date endDate = data.getDate("endDate");
            Boolean automaticRenewal = data.getBoolean("automaticRenewal");
            Boolean pricePayed = data.getBoolean("pricePayed");
            Boolean cautionPayed = data.getBoolean("cautionPayed");
            String typeName = data.getString("typeName");
            int clientNumber = data.getInt("clientNumber");

            subscription.setPrice(price);
            subscription.setDiscount(discount);
            subscription.setStartDate(startDate);
            subscription.setEndDate(endDate);
            subscription.setAutomaticRenewal(automaticRenewal);
            subscription.setPricePayed(pricePayed);
            subscription.setCautionPayed(cautionPayed);
            subscription.setTypeName(typeName);
            subscription.setClientNumber(clientNumber);

            return subscription;
        }
        catch(SQLException sqlException){
            throw new UnfoundResearchException("Erreur : aucun résultat ne correspond à votre recherche.");
        }
    }
}
