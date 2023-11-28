package com.example.familymap.model;

/**model class for objects in person table */
public class Person{

    /**unique identifier of this person */
    private String personID;
    /**owner of this person object */
    private String associatedUsername;
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

    
    public Person(String personID, String associatedUsername, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        setPersonID(personID);
        setAssociatedUsername(associatedUsername);
        setFirstName(firstName);
        setLastName(lastName);
        setGender(gender);
        setFatherID(fatherID);
        setMotherID(motherID);
        setSpouseID(spouseID);
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((associatedUsername == null) ? 0 : associatedUsername.hashCode());
        result = prime * result + ((fatherID == null) ? 0 : fatherID.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((gender == null) ? 0 : gender.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((motherID == null) ? 0 : motherID.hashCode());
        result = prime * result + ((personID == null) ? 0 : personID.hashCode());
        result = prime * result + ((spouseID == null) ? 0 : spouseID.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Person other = (Person) obj;
        if (associatedUsername == null) {
            if (other.associatedUsername != null)
                return false;
        } else if (!associatedUsername.equals(other.associatedUsername))
            return false;
        if (fatherID == null) {
            if (other.fatherID != null)
                return false;
        } else if (!fatherID.equals(other.fatherID))
            return false;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (gender == null) {
            if (other.gender != null)
                return false;
        } else if (!gender.equals(other.gender))
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        if (motherID == null) {
            if (other.motherID != null)
                return false;
        } else if (!motherID.equals(other.motherID))
            return false;
        if (personID == null) {
            if (other.personID != null)
                return false;
        } else if (!personID.equals(other.personID))
            return false;
        if (spouseID == null) {
            if (other.spouseID != null)
                return false;
        } else if (!spouseID.equals(other.spouseID))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Person [associatedUsername=" + associatedUsername + ", fatherID=" + fatherID + ", firstName="
                + firstName + ", gender=" + gender + ", lastName=" + lastName + ", motherID=" + motherID + ", personID="
                + personID + ", spouseID=" + spouseID + "]";
    }

    
}