package dataAccessPackage;

import modelPackage.*;
import exceptionPackage.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class DBAccess implements DataAccess{

    @Override
    public MemberAddress findMemberAdressByNationalNumber(Integer nationalNumber) throws UnfoundResearchException, ConnectionException{

        try{
            MemberAddress memberAddress;
            System.out.println("Ok");
            Connection connection = SingletonConnection.getInstance("mdp");
            System.out.println("Ok numero 2");
            // Instruction
            String sqlInstruction = "SELECT m.firstName, m.lastName, a.street, a.streetNumber, l.postalCode, l.name\n" +
                    "FROM libiavelo.member m \n" +
                    "JOIN libiavelo.address a ON (m.street = a.street AND m.streetNumber = a.streetNumber)\n" +
                    "JOIN libiavelo.locality l ON(l.postalCode = a.postalCode AND l.name = a.locality)\n" +
                    "WHERE m.nationalNumber = ?";

            //Creation du preparedStatement a partir de l'instruction sql
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, nationalNumber);

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
            Connection connection = SingletonConnection.getInstance("Fr!te1017");
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
            int discount, clientNumber;
            ArrayList<DiscountMember> members = new ArrayList<>();

            while(data.next()) {
                member = new DiscountMember();

                firstName = data.getString("firstName");
                member.setFirstName(firstName);

                lastName = data.getString("lastName");
                member.setLastName(lastName);

                discount = data.getInt("discount");
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
}
