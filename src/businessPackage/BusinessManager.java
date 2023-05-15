package businessPackage;
import modelPackage.*;
import exceptionPackage.*;
import dataAccessPackage.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class BusinessManager {
    private DataAccess dao;
    public BusinessManager(){
        setDao(new DBAccess());
    }

    public void setDao(DataAccess dao) {
        this.dao = dao;
    }

    public ArrayList<String> getAllNationalNumbers() throws ConnectionException, AllNationalNumbersException{
        return dao.getAllNationalNumbers();
    }

    public MemberAddress findMemberAdressByNationalNumber(String nationalNumber) throws UnfoundResearchException,ConnectionException{
            return dao.findMemberAdressByNationalNumber(nationalNumber);
    }

    public ArrayList<DiscountMember> findMembersWithDiscountFromAgeRange(Integer ageMin, Integer ageMax) throws UnfoundResearchException, ConnectionException, MemberDiscountException{

        GregorianCalendar dateMin, dateMax;

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int transformationDateMin = currentYear - ageMin;
        int transformationDateMax = currentYear - ageMax;

        dateMax = new GregorianCalendar(transformationDateMin, 11, 31);
        dateMin = new GregorianCalendar(transformationDateMax, 0, 1);

        return dao.findMembersWithDiscountFromAgeRange(dateMin, dateMax);
    }
}