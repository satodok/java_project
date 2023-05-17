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

    //Fonction pour le CRUD membre

    @Override
    public void deleteAllRelatedToMembers(ArrayList<String> nationalNumbers) throws ConnectionException, UnfoundResearchException {
        Connection connection = SingletonConnection.getInstance();
        try {
            for (String nationalNumber : nationalNumbers) {

                // Supprimer la ligne de la table Member
                String query = "DELETE FROM libiavelo.member WHERE nationalNumber = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1,nationalNumber);
                statement.executeUpdate();

                MemberInformations member = findMemberInformationsByNationalNumber(nationalNumber);
                String street = member.getStreet();
                Integer streetNumber = member.getStreetNumber();
                Integer clientNumber = member.getClientNumber();
                //Supprimer la ligne liée à la table card
                String sqlInstruction = "DELETE FROM libiavelo.card WHERE clientNumber = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
                preparedStatement.setInt(1,member.getClientNumber());
                preparedStatement.executeUpdate();

                //Supprimer la ligne liée à la table address
                String sqlInstruction2 = "DELETE FROM libiavelo.address WHERE street = ? AND streetNumber = ?";
                PreparedStatement queryStatement = connection.prepareStatement(sqlInstruction2);
                queryStatement.setString(1,member.getStreet());
                queryStatement.setInt(2, member.getStreetNumber());
                queryStatement.executeUpdate();


            }

        } catch (SQLException sqlException) {
            System.out.println("Erreur : " + sqlException.getMessage());
            throw new UnfoundResearchException("aucune valeur disponible à supprimer");
        }
    }


    //Fonction pour le CRUD membre


    @Override
    public ArrayList<Member> getAllMembers() throws ConnectionException, UnfoundResearchException {
        try{
            ArrayList<Member> members = new ArrayList<>();
            Connection connection = SingletonConnection.getInstance();
            String sqlInstruction = "SELECT * FROM libiavelo.member";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);

            ResultSet data = preparedStatement.executeQuery();
            while(data.next()){
                Member member = new Member();
                member.setNationalNumber(data.getString("nationalNumber"));
                member.setNewsletter(data.getBoolean("newsletter"));
                member.setLastName(data.getString("lastName"));
                member.setFirstName(data.getString("firstName"));
                if ( ! data.wasNull( ) ) {
                    member.setGender(data.getString("gender"));
                    member.setPhoneNumber(data.getString("phoneNumber"));
                    long date = data.getDate("birthDate").getTime();
                    member.setBirthDate(new java.util.Date(date));
                }

                member.setEmail(data.getString("email"));
                member.setStreet(data.getString("street"));
                member.setClientNumber(data.getInt("clientNumber"));
                member.setStreetNumber(String.valueOf(data.getInt("streetNumber")));

                    members.add(member);
            }
            return members;
        }
        catch(SQLException sqlException){
            System.out.println("Erreur : "+ sqlException.getMessage());
            throw new UnfoundResearchException("Erreur : aucun membre enregistré");
        }
    }

    // Fonction pour le CRUD membre
    @Override
    public int getPostalCode(String locality) throws ConnectionException, WrongArgumentException {
        try{
            Connection connection = SingletonConnection.getInstance();
            String sqlInstruction = "SELECT postalCode FROM libiavelo.locality WHERE name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setString(1, locality);
            ResultSet data = preparedStatement.executeQuery();

            data.next();
            return data.getInt("postalCode");

        }
        catch(SQLException sqlException){
            throw  new WrongArgumentException("erreur : mauvaise localité entrée");
        }
    }

    // Fonction utilisée pour le CRUD membre
    @Override
    public void addAddress(Integer streetNumber, String street, String locality) throws ExistingElementException, ConnectionException {
        //Récupérer le codePostal depuis la localité choisie par le membre dans la ComboBox du formulaire
        try{

            updateAddress(streetNumber,street, locality);
            Integer postalCode = getPostalCode(locality);
            // Insérer l'adresse du membre dans la BD
            Connection connection = SingletonConnection.getInstance();
            String sqlInstruction2 = "INSERT into libiavelo.address (street, streetNumber, postalCode, locality)\n" +
                    "VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction2);
            preparedStatement.setString(1, street);
            preparedStatement.setInt(2, streetNumber);
            preparedStatement.setInt(3, postalCode);
            preparedStatement.setString(4, locality);
            preparedStatement.executeUpdate();
        }
        catch(SQLException sqlException){
            System.out.println("Erreur : " + sqlException.getMessage());
            throw new ExistingElementException("Erreur : l'adresse que vous essayez d'entrer existe déjà");
        }
    }

    //Fonction principale pour l'update du CRUD membre


    //Fonction pour le CRUD member

    @Override
    public void updateAddress(Integer streetNumber, String street, String locality) throws ConnectionException, WrongArgumentException {

        int postalCode = getPostalCode(locality);
        try{
            Connection connection = SingletonConnection.getInstance();
            String query = "UPDATE libiavelo.address \n" +
                    "SET postalCode = ?, locality = ? \n" +
                    "WHERE street = ? AND streetNumber = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, postalCode);
            preparedStatement.setString(2, locality);
            preparedStatement.setString(3, street);
            preparedStatement.setInt(4, streetNumber);
            preparedStatement.executeUpdate();
        }
        catch(SQLException sqlException){
            throw new WrongArgumentException("Erreur : mauvaises entrées rentrées");
        }
    }

    @Override
    public void updateMember(Member member) throws ConnectionException, WrongArgumentException, UnfoundResearchException, ExistingElementException {
        try {
            Connection connection = SingletonConnection.getInstance();
            MemberInformations memberAddressCheck = findMemberInformationsByNationalNumber(member.getNationalNumber());
            String street = memberAddressCheck.getStreet();
            Integer streetNumber = memberAddressCheck.getStreetNumber();
            String locality = memberAddressCheck.getLocality();

            //l'adresse a changé, add la nouvelle addresse dans la table address et update member avec la nouvelle adresse
            if ((!member.getStreet().equals(memberAddressCheck.getStreet()))
                    || (Integer.parseInt(member.getStreetNumber()) != memberAddressCheck.getStreetNumber())) {
                addAddress(Integer.parseInt(member.getStreetNumber()), member.getStreet(), member.getLocality());
                String sqlInstruction = "UPDATE libiavelo.member \n" +
                        "SET lastName = ?, firstName = ?, birthDate = ?, phoneNumber = ?, " +
                        "gender = ?, email = ?, newsletter = ?, street = ?, streetNumber = ? \n" +
                        "WHERE nationalNumber = ?";

                PreparedStatement statement = connection.prepareStatement(sqlInstruction);
                statement.setString(1, member.getLastName());
                statement.setString(2, member.getFirstName());

                statement.setDate(3, new java.sql.Date(member.getBirthDate().getTime()));
                statement.setString(4, member.getPhoneNumber());
                statement.setString(5, member.getGender());
                statement.setString(6, member.getEmail());
                statement.setBoolean(7, member.getNewsletter());
                statement.setString(8, member.getStreet());
                statement.setInt(9, Integer.parseInt(member.getStreetNumber()));
                statement.setString(10, member.getNationalNumber());

                statement.executeUpdate();
            }
            //Le numéro et la rue n'ont pas changé
            else if (member.getStreet().equals(memberAddressCheck.getStreet()) &&
                    Integer.parseInt(member.getStreetNumber()) == (memberAddressCheck.getStreetNumber())) {
                //La localité a changé, il faut modifier l'occurence de la table address
                    if (!member.getLocality().equals(memberAddressCheck.getLocality())) {
                    updateAddress(streetNumber,street,locality);
                    }
                    //Update la table member sans modifier les valeurs d'adresse
                    String query = "UPDATE libiavelo.member \n" +
                            "SET lastName = ?, firstName = ?, birthDate = ?, phoneNumber = ?, " +
                            "gender = ?, email = ?, newsletter = ? \n" +
                            "WHERE nationalNumber = ?";

                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, member.getLastName());
                    preparedStatement.setString(2, member.getFirstName());

                    preparedStatement.setDate(3, new java.sql.Date(member.getBirthDate().getTime()));
                    preparedStatement.setString(4, member.getPhoneNumber());
                    preparedStatement.setString(5, member.getGender());
                    preparedStatement.setString(6, member.getEmail());
                    preparedStatement.setBoolean(7, member.getNewsletter());
                    preparedStatement.setString(8, member.getStreet());

                    preparedStatement.executeUpdate();

            }
        }
        catch(SQLException sqlException){
            String errorMessage = "Erreur : " + sqlException.getMessage();
            System.out.println(errorMessage);
            throw new WrongArgumentException("Erreur : mauvaises valeurs entrées.");
        }

    }

    //Fonction principale pour l'ajout du CRUD membre
    @Override
    public void addMember(Member member) throws ConnectionException, ExistingElementException {
        try{
            //Appel a la fonction d'ajout d'adresse
            addAddress(Integer.parseInt(member.getStreetNumber()), member.getStreet(), member.getLocality());
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
            preparedStatement.setDate(1, new java.sql.Date(dateMin.getTimeInMillis()));
            preparedStatement.setDate(2, new java.sql.Date(dateMax.getTimeInMillis()));

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
            String phoneNumber = data.getString("phoneNumber");
            String gender = data.getString("gender");
            String email = data.getString("email");
            Boolean newsletter = data.getBoolean("newsletter");
            String street = data.getString("street");
            String locality = data.getString("locality");
            Integer streetNumber = data.getInt("streetNumber");
            Integer postalCode = data.getInt("postalCode");
            Integer clientNumber = data.getInt("clientNumber");

            memberInformations.setFirstName(firstName);
            memberInformations.setLastName(lastName);
            memberInformations.setBirthDate(birthDate);
            memberInformations.setPhoneNumber(phoneNumber);
            memberInformations.setGender(gender);
            memberInformations.setEmailAddress(email);
            memberInformations.setNewsletter(newsletter);
            memberInformations.setLocality(locality);
            memberInformations.setStreet(street);
            memberInformations.setStreetNumber(streetNumber);
            memberInformations.setPostalCode(postalCode);
            memberInformations.setClientNumber(clientNumber);
            memberInformations.setAddress();
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

    @Override
    public ArrayList<Integer> getBikesRemainingInStation(ArrayList<Integer> numerosStation) throws ConnectionException {
        try {
            ArrayList<Integer> bikesRemainingInStation = new ArrayList<>();
            Connection connection = SingletonConnection.getInstance();

            String sqlInstruction = "Select c.stationNumber, c.bikesRemaining\n" +
                    "from counter c\n" +
                    "where c.stationNumber = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);

            for (int numero : numerosStation) {
                // Ajoutez chaque valeur à la requête préparée
                preparedStatement.setInt(1, numero);  // Remplacez 1 par l'index de la colonne correspondante
                // Exécutez la requête préparée pour chaque valeur
                //preparedStatement.executeQuery();
            }
            ResultSet data = preparedStatement.executeQuery();

            int nbBikes;
            while(data.next()){
                nbBikes = data.getInt("bikesRemaining");
                bikesRemainingInStation.add(nbBikes);
            }

            return bikesRemainingInStation;


        }catch(SQLException sqlException){
            throw new ConnectionException("Erreur : impossible de se connecter à la BD");
        }

    }
}
