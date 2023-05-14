package businessPackage;
import modelPackage.*;
import exceptionPackage.*;
import dataAccessPackage.*;
public class BusinessManager {
    private DataAccess dao;
    public BusinessManager(){
        setDao(new DBAccess());
    }

    public void setDao(DataAccess dao) {
        this.dao = dao;
    }

    public MemberAddress findMemberAdressByNationalNumber(Integer nationalNumber) throws UnfoundResearchException,ConnectionException{
            return dao.findMemberAdressByNationalNumber(nationalNumber);
    }
}
