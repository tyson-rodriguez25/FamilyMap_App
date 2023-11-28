package response;

import java.util.Arrays;

import model.Person;

/** gson model class for responses to /person */
public class PersonResponseArray extends InheritResponse {
    /** array of persons owned by user */
    private Person[] data;

    public PersonResponseArray(Person[] data) {
        setPersons(data);
    }

    public Person[] getPersons() {
        return data;
    }

    public void setPersons(Person[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PersonResponseArray [data=" + Arrays.toString(data) + "]";
    }
    
}