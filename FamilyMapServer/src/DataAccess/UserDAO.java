package DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

/**
 * data access class for user
 */
public class UserDAO{

    /**SQL connection */
    Connection connection = null;
    /**SQL prepared statement */
    PreparedStatement stmt = null;
    /**SQL ResultSet */
    ResultSet results = null;

    String dataBName = "jdbc:sqlite:familymap.sqlite";
    
    /**
     * deletes user from the table if its the one you want
     * @param user user in question
     * @throws SQLException throws if user isnt there
     */
    public void dropUser(User user) throws SQLException {
        connection  = DriverManager.getConnection(dataBName);
        String delete = "Delete from user where userName = ?";
        stmt = connection.prepareStatement(delete);
        stmt.executeUpdate();
        stmt.close();
    }
    /**
     * gets the cooresponding user for your personID
     * @param personID personID in question
     * @return returns that user
     * @throws SQLException throws if user isnt there
     */
    public User getUser(String userName) throws SQLException {
        connection = DriverManager.getConnection(dataBName);
        
        String count = "SELECT count(*) from user where userName = ?";
        stmt = connection.prepareStatement(count);
        stmt.setString(1, userName);
        results = stmt.executeQuery();
        int num = results.getInt(1);
        stmt.close();
        if(num < 1){
            return null;
        }
        
        String query = "SELECT * FROM user where userName = ?";
        stmt = connection.prepareStatement(query);
        stmt.setString(1, userName);
        results = stmt.executeQuery();
        
        User user = new User("", "", "", "", "", "", "");
        user.setUserName(results.getString(1));
        user.setPassword(results.getString(2));
        user.setEmail(results.getString(3));
        user.setFirstName(results.getString(4));
        user.setLastName(results.getString(5));
        user.setGender(results.getString(6));
        user.setPersonID(results.getString(7));
        stmt.close();
        return user;
    }
    /**
     * adds the user to the database table
     * @param user user being added
     * @throws SQLException throws if user is already there or if data doesnt fit in the table
     */
    public void addUser(User user) throws SQLException {
        connection = DriverManager.getConnection(dataBName);
        
        String count = "SELECT count(*) from user where userName = ?";
        stmt = connection.prepareStatement(count);
        stmt.setString(1, user.getUserName());
        results = stmt.executeQuery();
        int num = results.getInt(1);
        stmt.close();
        if(num > 0){
            throw new SQLException("User already exists");
        }
        
        
        String insert = "insert into user (userName, password, email, firstName, lastName, gender, personID) values ( ?, ?, ?, ?, ?, ?, ? )";
        stmt = connection.prepareStatement(insert);
        stmt.setString(1, user.getUserName());
        stmt.setString(2, user.getPassword());
        stmt.setString(3, user.getEmail());
        stmt.setString(4, user.getFirstName());
        stmt.setString(5, user.getLastName());
        stmt.setString(6, user.getGender());
        stmt.setString(7, user.getPersonID());
        stmt.executeUpdate();
        stmt.close();
    }
    /**
     * clears all the data from the table
     * @throws SQLException if error occurs
     */
    public void clearTable() throws SQLException{
        connection = DriverManager.getConnection(dataBName);
        stmt = connection.prepareStatement("Delete from user");
        stmt.executeUpdate();
        stmt.close();
    }

    public String checkLogin(String userName, String password)throws SQLException{
        connection = DriverManager.getConnection(dataBName);
        String count = "SELECT count(*) from user where userName = ? and password = ?";
        stmt = connection.prepareStatement(count);
        stmt.setString(1, userName);
        stmt.setString(2, password);
        results = stmt.executeQuery();
        int num = results.getInt(1);
        stmt.close();
        if(num < 1){
            return null;
        }
        
        String query = "SELECT PersonID FROM user where userName = ? and password = ?";
        stmt = connection.prepareStatement(query);
        stmt.setString(1, userName);
        stmt.setString(2, password);
        results = stmt.executeQuery();
        String pID = results.getString(1);
        stmt.close();
        
        return pID;
    }

}