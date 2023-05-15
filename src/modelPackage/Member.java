package modelPackage;

import java.util.Date;
import java.util.GregorianCalendar;

public class Member {
    private String nationalNumber;
    private String lastName;
    private String firstName;
    private GregorianCalendar birthDate;
    private Integer phoneNumber;
    private String gender;
    private String email;
    private Boolean newsletter;
    private String street;
    private Integer streetNumber;
    private Integer clientNumber;


    // Constructeur vide pour la recherche numéro 3
    public Member(){
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setClientNumber(Integer clientNumber) {
        this.clientNumber = clientNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getClientNumber() {
        return clientNumber;
    }
}

