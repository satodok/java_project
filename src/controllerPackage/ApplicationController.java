package controllerPackage;

import modelPackage.*;
import businessPackage.*;
import exceptionPackage.*;

import java.util.ArrayList;

public class ApplicationController {

    private BusinessManager businessManager;

    public ApplicationController(){
        businessManager = new BusinessManager();
    }

    public ArrayList<String> getAllNationalNumbers() throws ConnectionException, AllNationalNumbersException{
        return businessManager.getAllNationalNumbers();
    }

    public MemberAddress findMemberAdressByNationalNumber(String nationalNumber) throws UnfoundResearchException,ConnectionException{
        return businessManager.findMemberAdressByNationalNumber(nationalNumber);
    }

    public ArrayList<DiscountMember> findMembersWithDiscountFromAgeRange(Integer ageMin, Integer ageMax) throws UnfoundResearchException, ConnectionException{
        return businessManager.findMembersWithDiscountFromAgeRange(ageMin, ageMax);
    }

}
