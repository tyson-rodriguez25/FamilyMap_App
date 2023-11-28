package DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.ArrayList;

import model.Person;

/**
 * data access class for person
 */
public class PersonDAO{

    /**SQL connection */
    Connection connection = null;
    /**SQL prepared statement */
    PreparedStatement stmt = null;
    /**SQL ResultSet */
    ResultSet results = null;

    String dataBName = "jdbc:sqlite:familymap.sqlite";
    
    /**
     * deletes person from the table if its the one you want
     * @param person person in question
     * @throws SQLException throws if person isnt there
     */
    public void dropPerson(Person person) throws SQLException {
        connection  = DriverManager.getConnection(dataBName);
        String delete = "Delete from persons where personID = ?";
        stmt = connection.prepareStatement(delete);
        stmt.setString(1, person.getPersonID());
        stmt.executeUpdate();
        stmt.close();
    }
    /**
     * gets the cooresponding person for your eventID
     * @param personID personID in question
     * @return returns that person
     * @throws SQLException throws if person isnt there
     */
    public Person getPerson(String personID) throws SQLException {
        connection = DriverManager.getConnection(dataBName);
        
        String count = "SELECT count(*) from persons where personID = ?";
        stmt = connection.prepareStatement(count);
        stmt.setString(1, personID);
        results = stmt.executeQuery();
        int num = results.getInt(1);
        stmt.close();
        if(num < 1){
            return null;
        }

        String query = "SELECT * FROM persons where personID = ?";
        stmt = connection.prepareStatement(query);
        stmt.setString(1, personID);
        results = stmt.executeQuery();
        
        Person p = new Person(
            results.getString(1),
            results.getString(2),
            results.getString(3),
            results.getString(4),
            results.getString(5),
            results.getString(6),
            results.getString(7),
            results.getString(8)
        );
        stmt.close();
        return p;
    }
    /**
     * adds the person to the database table
     * @param person person being added
     * @throws SQLException throws if person is already there or if data doesnt fit in the table
     */
    public void addPerson(Person person) throws SQLException {
        connection = DriverManager.getConnection(dataBName);
        
        String count = "SELECT count(*) from persons where personID = ?";
        stmt = connection.prepareStatement(count);
        stmt.setString(1, person.getPersonID());
        results = stmt.executeQuery();
        int num = results.getInt(1);
        stmt.close();
        if(num > 0){
            throw new SQLException("Person is already in Database");
        }
        
        String insert = "insert into persons (personID, userName, firstName, lastName, gender, fatherID, motherID, spouseID) values ( ?, ?, ?, ?, ?, ?, ?, ? )";
        stmt = connection.prepareStatement(insert);
        stmt.setString(1, person.getPersonID());
        stmt.setString(2, person.getAssociatedUsername());
        stmt.setString(3, person.getFirstName());
        stmt.setString(4, person.getLastName());
        stmt.setString(5, person.getGender());
        stmt.setString(6, person.getFatherID());
        stmt.setString(7, person.getMotherID());
        stmt.setString(8, person.getSpouseID());
        stmt.executeUpdate();
        stmt.close();
    }
    /**
     * clears all the data from the table
     * @throws SQLException if error occurs
     */
    public void clearTable() throws SQLException{
        connection = DriverManager.getConnection(dataBName);
        stmt = connection.prepareStatement("Delete from persons");
        stmt.executeUpdate();
        stmt.close();
    }
    /**
     * get every person object with the given username
     * @param userName said username to find
     * @return the array of persons with that username
     */
    public Person[] getAssoPerson(String userName) throws SQLException{
        connection = DriverManager.getConnection(dataBName);
        
        String count = "SELECT count(*) from persons where userName = ?";
        stmt = connection.prepareStatement(count);
        stmt.setString(1, userName);
        results = stmt.executeQuery();
        int num = results.getInt(1);
        stmt.close();
        if(num < 1){
            return null;
        }

        String query = "SELECT * FROM persons where userName = ?";
        stmt = connection.prepareStatement(query);
        stmt.setString(1, userName);
        results = stmt.executeQuery();

        Person[] peeps = new Person[num];
        int i = 0;

        while(results.next()){
            peeps[i] = new Person(
                results.getString(1),
                results.getString(2),
                results.getString(3),
                results.getString(4),
                results.getString(5),
                results.getString(6),
                results.getString(7),
                results.getString(8)
            );
            i++;
        }
        stmt.close();

        return peeps;
    }
    public void dropAssoPerson(String userName) throws SQLException{
        connection = DriverManager.getConnection(dataBName);
        stmt = connection.prepareStatement("Delete from persons where userName = ?");
        stmt.setString(1, userName);
        stmt.executeUpdate();
        stmt.close();
    }

}