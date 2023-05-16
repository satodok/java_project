package modelPackage;

import java.util.Date;
import java.util.GregorianCalendar;

public class Member {
    private String nationalNumber;
    private String lastName;
    private String firstName;
    private Date birthDate;
    private String phoneNumber;
    private String gender;
    private String email;
    private Boolean newsletter;
    private String street;
    private String streetNumber;
    private Integer clientNumber;
    private String locality;
    private Integer postalCode;


    // Constructeur vide pour la recherche numéro 3
    public Member(){}
    public Member(String nationalNumber,String lastName,String firstName, Date birthDate,
                  String phoneNumber, String gender, String email, Boolean newsletter,
                  String street, String streetNumber, String locality){
        this.nationalNumber = nationalNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.email = email;
        this.newsletter = newsletter;
        this.street = street;
        this.streetNumber = streetNumber;
        this.locality = locality;
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

    public Integer getClientNumber() {
        return clientNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    public Integer getPostalCode() {
        return postalCode;
    }

    public String getLocality() {
        return locality;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Boolean getNewsletter() {
        return newsletter;
    }

    public String getStreet() {
        return street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getNationalNumber() {
        return nationalNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }
    public String getGender() {
        return gender;
    }

}

