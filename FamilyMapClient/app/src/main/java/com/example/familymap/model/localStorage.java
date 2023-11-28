package com.example.familymap.model;

import java.util.ArrayList;
import java.util.List;

public class localStorage {

    private localStorage() {
    }
    private Person[] p;
    private Event[] e = {};
    private String authToken = null;
    private List<Person> familyMembers = new ArrayList<>();
    private boolean maleFilter = true;
    private boolean femaleFilter = true;

    public boolean isMaleFilter() {
        return maleFilter;
    }

    public void setMaleFilter(boolean maleFilter) {
        this.maleFilter = maleFilter;
    }

    public boolean isFemaleFilter() {
        return femaleFilter;
    }

    public void setFemaleFilter(boolean femaleFilter) {
        this.femaleFilter = femaleFilter;
    }




    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
    public String getAuthToken() {
        return authToken;
    }

    public Person[] getP() {
        return p;
    }

    public void setP(Person[] p) {
        this.p = p;
    }

    public Event[] getE() {
        return e;
    }

    public void setE(Event[] e) {
        this.e = e;
    }

    private static localStorage instance;
    static public localStorage getInstance(){
        if (instance == null ) {
            instance = new localStorage();
        }
        return instance;

    }

    public Event getEventById(String eventId) {
        for( Event event : e) {
            if (eventId.equals(event.getEventID())) {
                return event;
            }
        }
        return null;
    }

    public Person getPersonById(String personId) {
        if (personId == null) {
            return null;
        }
        for( Person person : p) {
            if (personId.equals(person.getPersonID())) {
                return person;
            }
        }
        return null;
    }

    public Person getPersonChildren(String personId) {
        for( Person person : p) {
            if (personId.equals(person.getFatherID())) {
                familyMembers.add(person);
            }
            else if (personId.equals(person.getMotherID())) {
                familyMembers.add(person);
            }
        }
        return null;
    }

    public List<Person> getFamilyMembers(String personId) {
        familyMembers.clear();
        for (Person person : p ) {
            if (personId.equals(person.getPersonID())) {
                if(getPersonById(person.getFatherID()) != null) {
                    familyMembers.add(getPersonById(person.getFatherID()));
                }
                if (getPersonById(person.getMotherID()) != null) {
                    familyMembers.add(getPersonById(person.getMotherID()));
                }
                if (getPersonById(person.getSpouseID()) != null) {
                    familyMembers.add(getPersonById(person.getSpouseID()));
                }
                getPersonChildren(person.getPersonID());
            }
        }
        return familyMembers;
    }

    public List<Event> getEventByPerson(String personId) {
        List<Event> lifeEvents = new ArrayList<Event>();
        for (Event event : e) {
            if (personId.equals(event.getPersonID())) {
                lifeEvents.add(event);
            }
        }
        return lifeEvents;
    }

    public void clearStorage() {
        instance = new localStorage();

    }
}
