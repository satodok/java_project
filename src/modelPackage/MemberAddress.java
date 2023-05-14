package modelPackage;

public class MemberAddress {
    String lastName;
    String firstName;
    Integer postalCode;
    String locality;
    Integer streetNumber;
    String street;

    public MemberAddress(String lastName, String firstName, Integer postalCode,
                         String locality, Integer streetNumber, String street){
        this.lastName = lastName;
        this.firstName = firstName;
        this.postalCode = postalCode;
        this.locality = locality;
        this.streetNumber = streetNumber;
        this.street = street;
    }
}
