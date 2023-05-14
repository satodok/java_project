package dataAccessPackage;

import modelPackage.*;
import exceptionPackage.*;

import java.lang.reflect.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBAccess implements DataAccess{

    @Override
    public MemberAddress findMemberAdressByNationalNumber(Integer nationalNumber) throws UnfoundException, ConnectionException{

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
            throw new UnfoundException("Erreur : aucun résultat ne correspond à votre recherche.");
        }

    }


























































































    ArrayList<discountMemberAgeRangeModel> findMembersWithDiscountFromAgeRange(Integer ageMin, Integer ageMax);
}
