package model;

/**model class for objects in event table */
public class Event{

    /**unique identifier of this event */
    private String eventID;
    /** owner of the event*/
    private String associatedUsername;
    /**person event happened to */
    private String personID;
    /**latitude of event */
    private float latitude;
    /**longitude of event */
    private float longitude;
    /**country in which event took place */
    private String country;
    /**city where event happened */
    private String city;
    /**type of event that occcured */
    private String eventType;
    /**year event occured */
    private int year;

    public Event(String eventID, String associatedUsername, String personID, float latitude, float longitude, String country, String city, String eventType, int year) {
        setEventID(eventID);
        setAssociatedUsername(associatedUsername);
        setPersonID(personID);
        setLatitude(latitude);
        setLongitude(longitude);
        setCountry(country);
        setCity(city);
        setEventType(eventType);
        setYear(year);
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
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

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((associatedUsername == null) ? 0 : associatedUsername.hashCode());
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        result = prime * result + ((eventID == null) ? 0 : eventID.hashCode());
        result = prime * result + ((eventType == null) ? 0 : eventType.hashCode());
        result = prime * result + Float.floatToIntBits(latitude);
        result = prime * result + Float.floatToIntBits(longitude);
        result = prime * result + ((personID == null) ? 0 : personID.hashCode());
        result = prime * result + year;
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
        Event other = (Event) obj;
        if (associatedUsername == null) {
            if (other.associatedUsername != null)
                return false;
        } else if (!associatedUsername.equals(other.associatedUsername))
            return false;
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        if (country == null) {
            if (other.country != null)
                return false;
        } else if (!country.equals(other.country))
            return false;
        if (eventID == null) {
            if (other.eventID != null)
                return false;
        } else if (!eventID.equals(other.eventID))
            return false;
        if (eventType == null) {
            if (other.eventType != null)
                return false;
        } else if (!eventType.equals(other.eventType))
            return false;
        if (Float.floatToIntBits(latitude) != Float.floatToIntBits(other.latitude))
            return false;
        if (Float.floatToIntBits(longitude) != Float.floatToIntBits(other.longitude))
            return false;
        if (personID == null) {
            if (other.personID != null)
                return false;
        } else if (!personID.equals(other.personID))
            return false;
        if (year != other.year)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Event [userName=" + associatedUsername + ", city=" + city + ", country=" + country
                + ", eventID=" + eventID + ", eventType=" + eventType + ", latitude=" + latitude + ", longitude="
                + longitude + ", personID=" + personID + ", year=" + year + "]";
    }

    
    
}