package controllerPackage;

import modelPackage.*;
import businessPackage.*;
import exceptionPackage.*;
public class ApplicationController {

    private BusinessManager businessManager;

    public ApplicationController(){
        businessManager = new BusinessManager();
    }

    public MemberAddress findMemberAdressByNationalNumber(Integer nationalNumber) throws UnfoundResearchException,ConnectionException{
        return businessManager.findMemberAdressByNationalNumber(nationalNumber);
    }

}
