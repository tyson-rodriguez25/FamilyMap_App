package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;


import model.Event;


/**
 * data access class for event
 */
public class EventDAO{

    /**SQL connection */
    Connection connection = null;
    /**SQL prepared statement */
    PreparedStatement stmt = null;
    /**SQL ResultSet */
    ResultSet results = null;

    String dataBName = "jdbc:sqlite:familymap.sqlite";

    /**
     * deletes event from the table if its the one you want
     * @param event event in question
     * @throws SQLException throws if event isnt there
     */
    public void dropEvent(Event event) throws SQLException {
        connection  = DriverManager.getConnection(dataBName);
        String delete = "Delete from events where eventId = ?";
        stmt = connection.prepareStatement(delete);
        stmt.setString(1, event.getEventID());
        stmt.executeUpdate();
        stmt.close();
    }
    /**
     * gets the cooresponding event for your eventID
     * @param eventID eventID in question
     * @return returns that event
     * @throws SQLException throws if event isnt there
     */
    public Event getEvent(String eventID) throws SQLException {
        connection = DriverManager.getConnection(dataBName);
        
        String count = "SELECT count(*) from events where eventId = ?";
        stmt = connection.prepareStatement(count);
        stmt.setString(1, eventID);
        results = stmt.executeQuery();
        int num = results.getInt(1);
        stmt.close();
        if(num < 1){
            return null;
        }

        String query = "SELECT * FROM events where eventId = ?";
        stmt = connection.prepareStatement(query);
        stmt.setString(1, eventID);
        results = stmt.executeQuery();
        
        Event e = new Event(
            results.getString(1),
            results.getString(2),
            results.getString(3),
            results.getFloat(4),
            results.getFloat(5),
            results.getString(6),
            results.getString(7),
            results.getString(8),
            results.getInt(9)
        );
        stmt.close();
        return e;
    }
    /**
     * adds the event to the database table
     * @param event event being added
     * @throws SQLException throws if event is already there or if data doesnt fit in the table
     */
    public void addEvent(Event event) throws SQLException {
        connection = DriverManager.getConnection(dataBName);
        
        String count = "SELECT count(*) from events where eventId = ?";
        stmt = connection.prepareStatement(count);
        stmt.setString(1, event.getEventID());
        results = stmt.executeQuery();
        int num = results.getInt(1);
        stmt.close();
        if(num > 0){
            throw new SQLException("Event already in database");
        }
        
        
        String insert = "INSERT INTO events (eventId, userName, personID, latitude, longitude, country, city, eventType, year) values ( ?, ?, ?, ?, ?, ?, ?, ?, ? )";
        stmt = connection.prepareStatement(insert);
        stmt.setString(1, event.getEventID());
        stmt.setString(2, event.getAssociatedUsername());
        stmt.setString(3, event.getPersonID());
        stmt.setFloat(4, event.getLatitude());
        stmt.setFloat(5, event.getLongitude());
        stmt.setString(6, event.getCountry());
        stmt.setString(7, event.getCity());
        stmt.setString(8, event.getEventType());
        stmt.setInt(9, event.getYear());
        stmt.executeUpdate();
        stmt.close();
    }
    /**
     * clears all the data from the table
     * @throws SQLException if error occurs
     */
    public void clearTable() throws SQLException{
        connection = DriverManager.getConnection(dataBName);
        stmt = connection.prepareStatement("Delete from events");
        stmt.executeUpdate();
        stmt.close();
    }
    public Event[] getAssoEvent(String userName) throws SQLException{
        connection = DriverManager.getConnection(dataBName);
        
        String count = "SELECT count(*) from events where userName = ?";
        stmt = connection.prepareStatement(count);
        stmt.setString(1, userName);
        results = stmt.executeQuery();
        int num = results.getInt(1);
        stmt.close();
        if(num < 1){
            return null;
        }

        String query = "SELECT * FROM events where userName = ?";
        stmt = connection.prepareStatement(query);
        stmt.setString(1, userName);
        results = stmt.executeQuery();

        Event[] vents = new Event[num];
        int i = 0;

        while(results.next()){
            vents[i] = new Event(
                results.getString(1),
                results.getString(2),
                results.getString(3),
                results.getFloat(4),
                results.getFloat(5),
                results.getString(6),
                results.getString(7),
                results.getString(8),
                results.getInt(9)
            );
            i++;
        }
        stmt.close();
        return vents;
    }
    public void dropAssoEvents(String userName) throws SQLException{
        connection = DriverManager.getConnection(dataBName);
        stmt = connection.prepareStatement("DELETE FROM events where userName = ?");
        stmt.setString(1, userName);
        stmt.executeUpdate();
        stmt.close();
    }    
    
}