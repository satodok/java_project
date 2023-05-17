package dataAccessPackage;

import modelPackage.*;
import exceptionPackage.*;

import javax.swing.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class DBAccess implements DataAccess{

    // Fonction utilisée pour l'ajout pour le CRUD membre
    @Override
    public void addAddress(String streetNumber, String street, String locality) throws ExistingElementException, ConnectionException {
        //Récupérer le codePostal depuis la localité choisi par le membre dans la ComboBox du formulaire
        try{
            Connection connection = SingletonConnection.getInstance();
            String sqlInstruction = "SELECT postalCode FROM libiavelo.locality WHERE name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setString(1, locality);
            ResultSet data = preparedStatement.executeQuery();

            Integer postalCode;
            data.next();

            postalCode = data.getInt("postalCode");
            // Insérer l'adresse du membre dans la BD
            connection = SingletonConnection.getInstance();
            String sqlInstruction2 = "INSERT into libiavelo.address (street, streetNumber, postalCode, locality)\n" +
                    "VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sqlInstruction2);
            preparedStatement.setString(1, street);
            preparedStatement.setString(2, streetNumber);
            preparedStatement.setInt(3, postalCode);
            preparedStatement.setString(4, locality);
            preparedStatement.executeUpdate();
        }
        catch(SQLException sqlException){
            throw new ExistingElementException("Erreur : l'adresse que vous essayez d'entrer existe déjà");
        }
    }

    //Fonction principale pour l'ajout pour le CRUD membre
    @Override
    public void addMember(Member member) throws ConnectionException, ExistingElementException {
        try{
            //Appel a la fonction d'ajout d'adresse
            addAddress(member.getStreetNumber(), member.getStreet(), member.getLocality());
            Connection connection = SingletonConnection.getInstance();

            // Créer la carte du membre et donc son numéro client
            String sqlInstruction1 = "INSERT into libiavelo.card (clientNumber) values (NULL);\n";
            PreparedStatement statement = connection.prepareStatement(sqlInstruction1);
            statement.executeUpdate();

            //Insérer toutes les valeurs dans la table membre
            String sqlInstruction2 = "INSERT into libiavelo.member(nationalNumber, lastName, firstName, birthDate, phoneNumber, gender, email, newsletter, street, streetNumber, clientNumber)\n" +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, LAST_INSERT_ID());\n";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction2);

            preparedStatement.setString(1, member.getNationalNumber());
            preparedStatement.setString(2, member.getLastName());
            preparedStatement.setString(3, member.getFirstName());
            preparedStatement.setDate(4, new java.sql.Date(member.getBirthDate().getTime()));
            preparedStatement.setString(5, member.getPhoneNumber());
            preparedStatement.setString(6, member.getGender());
            preparedStatement.setString(7, member.getEmail());
            preparedStatement.setBoolean(8, member.getNewsletter());
            preparedStatement.setString(9, member.getStreet());
            preparedStatement.setString(10, member.getStreetNumber());
            preparedStatement.executeUpdate();


        }
        catch(SQLException sqlException){
            throw new ExistingElementException("Erreur : ce membre existe déjà");
        }
    }

    // Récupérer toutes les localités
    @Override
    public ArrayList<String> getLocalities() throws ConnectionException, UnfoundResearchException {
        try{
            ArrayList<String> localities = new ArrayList<>();
            Connection connection = SingletonConnection.getInstance();
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
            Connection connection = SingletonConnection.getInstance();
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
            Connection connection = SingletonConnection.getInstance();
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
            Connection connection = SingletonConnection.getInstance();
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
            Connection connection = SingletonConnection.getInstance();
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
            Connection connection = SingletonConnection.getInstance();
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
            Connection connection = SingletonConnection.getInstance();
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
            Connection connection = SingletonConnection.getInstance();
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

            String ID = data.getString("subscriptionID");
            int price = data.getInt("price");
            double discount = data.getDouble("discount");
            Date startDate = data.getDate("startDate");
            Date endDate = data.getDate("endDate");
            Boolean automaticRenewal = data.getBoolean("automaticRenewal");
            Boolean pricePayed = data.getBoolean("pricePayed");
            Boolean cautionPayed = data.getBoolean("cautionPayed");
            String typeName = data.getString("typeName");
            int clientNumber = data.getInt("clientNumber");

            subscription.setSubscriptionID(ID);
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

    @Override
    public void addNewSubscription(Subscription subscription) throws ConnectionException {
        try{
            Connection connection = SingletonConnection.getInstance();


            String sqlInstruction = "INSERT INTO libiavelo.subscription (price, discount, startDate, endDate, " +
                    "automaticRenewal, pricePayed, cautionPayed, typeName, clientNumber)\n" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

            //Creation du preparedStatement a partir de l'instruction sql
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, subscription.getPrice());
            preparedStatement.setDouble(2, subscription.getDiscount());
            preparedStatement.setDate(3, new java.sql.Date(subscription.getStartDate().getTime()));
            preparedStatement.setDate(4, new java.sql.Date(subscription.getEndDate().getTime()));
            preparedStatement.setBoolean(5, subscription.getAutomaticRenewal());
            preparedStatement.setBoolean(6, subscription.getPricePayed());
            preparedStatement.setBoolean(7, subscription.getCautionPayed());
            preparedStatement.setString( 8, subscription.getTypeName());
            preparedStatement.setInt(9,subscription.getClientNumber());

            preparedStatement.executeUpdate();



        }catch (SQLException sqlException){
            throw new ConnectionException("Erreur lors de la connection à la base de donnée");
        }
    }


    @Override
    public void deleteSubscription(ArrayList<String> subscriptionID) throws ConnectionException, UnfoundResearchException {
        try {
            //test
            Connection connection = SingletonConnection.getInstance();
            String sqlInstruction = "DELETE FROM libiavelo.subscription \n" +
                    "WHERE subscriptionId = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            for (String subscription : subscriptionID) {
                // Ajoutez chaque valeur à la requête préparée
                preparedStatement.setString(1, subscription);  // Remplacez 1 par l'index de la colonne correspondante
                // Exécutez la requête préparée pour chaque valeur
                preparedStatement.executeUpdate();
            }

        } catch (SQLException sqlException) {
            throw new UnfoundResearchException("rien trouvé");
        }
    }


    @Override
    public ArrayList<Subscription> getAllSubscription() throws ConnectionException, UnfoundResearchException{
        try{
            ArrayList<Subscription>subscriptions = new ArrayList<>();
            Connection connection = SingletonConnection.getInstance();
            String sqlInstruction = "SELECT * FROM libiavelo.subscription";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            ResultSet data = preparedStatement.executeQuery();


            while(data.next()){
                Subscription subscription = new Subscription();

                String ID = data.getString("subscriptionId");
                int price = data.getInt("price");
                double discount = data.getDouble("discount");
                Date startDate = data.getDate("startDate");
                Date endDate = data.getDate("endDate");
                Boolean automaticRenewal = data.getBoolean("automaticRenewal");
                Boolean pricePayed = data.getBoolean("pricePayed");
                Boolean cautionPayed = data.getBoolean("cautionPayed");
                String typeName = data.getString("typeName");
                int clientNumber = data.getInt("clientNumber");

                subscription.setSubscriptionID(ID);
                subscription.setPrice(price);
                subscription.setDiscount(discount);
                subscription.setStartDate(startDate);
                subscription.setEndDate(endDate);
                subscription.setAutomaticRenewal(automaticRenewal);
                subscription.setPricePayed(pricePayed);
                subscription.setCautionPayed(cautionPayed);
                subscription.setTypeName(typeName);
                subscription.setClientNumber(clientNumber);

                subscriptions.add(subscription);
            }
            return subscriptions;
        }
        catch(SQLException sqlException){
            throw new UnfoundResearchException("Erreur : aucun résultat ne correspond à votre recherche.");
        }
    }

    public ArrayList<Integer> getAllClientNumbers() throws ConnectionException, UnfoundResearchException{
        try{
            ArrayList<Integer> clientNumbers = new ArrayList<>();
            Connection connection = SingletonConnection.getInstance();
            String sqlInstruction = "SELECT clientNumber FROM libiavelo.card";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            ResultSet data = preparedStatement.executeQuery();
            int clientNumber;

            while(data.next()){
                clientNumber = data.getInt("clientNumber");
                clientNumbers.add(clientNumber);
            }
            return clientNumbers;
        }
        catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
            throw new UnfoundResearchException("Error : no results were found from your search");
        }
    }
}
