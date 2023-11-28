package com.example.familymap.response;

/**gson model class for person responses */
public class PersonResponse extends InheritResponse{
    
    /**owner of this person object */
    private String associatedUsername;
    /**unique identifier of this person */
    private String personID;
    /**first name of person */
    private String firstName;
    /**last name of person */
    private String lastName;
    /**gender of person */
    private String gender;
    /**person id of persons father */
    private String fatherID;
    /**persons id of persons mother */
    private String motherID;
    /**persons id of persons spouse */
    private String spouseID;

    public PersonResponse(String associatedUsername, String personID, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        setAssociatedUsername(associatedUsername);
        setPersonID(personID);
        setFirstName(firstName);
        setLastName(lastName);
        setGender(gender);
        setFatherID(fatherID);
        setMotherID(motherID);
        setSpouseID(spouseID);
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    @Override
    public String toString() {
        return "PersonResponse [associatedUsername=" + associatedUsername + ", fatherID=" + fatherID + ", firstName="
                + firstName + ", gender=" + gender + ", lastName=" + lastName + ", motherID=" + motherID + ", personID="
                + personID + ", spouseID=" + spouseID + "]";
    }

    
}   