package request;

import model.*;

/**gson model class for loadrequest */
public class LoadRequest{
    
    /**array of user objects */
    private User[] users;
    /**array of person objects */
    private Person[] persons;
    /**array of event objets */
    private Event[] events;

    public LoadRequest(User[] users, Person[] persons, Event[] events) {
        setUsers(users);
        setPersons(persons);
        setEvents(events);
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }

    
}