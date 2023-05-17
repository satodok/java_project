package modelPackage;

import com.toedter.calendar.JCalendar;

import java.util.Date;

public class MemberInformations {
    private String lastName;
    private String firstName;
    private String street;
    private Integer streetNumber;
    private String locality;
    private Integer postalCode;
    private String address;
    private Date birthDate;
    private String phoneNumber;
    private String gender;
    private String emailAddress;
    private boolean newsletter;

    public MemberInformations(){}

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setAddress() {
        address = streetNumber + " "+ street + ", " + postalCode + " " + locality;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setNewsletter(boolean newsletter) {
        this.newsletter = newsletter;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getAddress() {
        return address;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getGender() {
        return gender;
    }

    public boolean getNewsLetter(){
        return newsletter;
    }

    public String getStreet() {
        return street;
    }

    public String getLocality() {
        return locality;
    }

    public Integer getStreetNumber() {
        return streetNumber;
    }
}
