package response;

/**gson model class for event responses */
public class EventResponse extends InheritResponse{
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

    public EventResponse(String eventID, String associatedUsername, String personID, float latitude, float longitude, String country, String city, String eventType, int year) {
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
    public String toString() {
        return "EventResponse [associatedUsername=" + associatedUsername + ", city=" + city + ", country=" + country
                + ", eventID=" + eventID + ", eventType=" + eventType + ", latitude=" + latitude + ", longitude="
                + longitude + ", personID=" + personID + ", year=" + year + "]";
    }
    
        
}