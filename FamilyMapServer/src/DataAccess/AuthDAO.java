package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;


import model.Auth;

/**
 * data access class for auth
 */
public class AuthDAO{

    /**SQL connection */
    Connection connection = null;
    /**SQL prepared statement */
    PreparedStatement stmt = null;
    /**SQL ResultSet */
    ResultSet results = null;
    
    String dataBName = "jdbc:sqlite:familymap.sqlite";

    /**
     * deletes token from the table if its the one you want
     * @param token token in question
     * @throws SQLException throws if token isnt there
     */
    public void dropTok(Auth token) throws SQLException {
        connection  = DriverManager.getConnection(dataBName);
        String delete = "Delete from authToken where userName = ?";
        stmt = connection.prepareStatement(delete);
        stmt.setString(1, token.getUserName());
        stmt.executeUpdate();
        stmt.close();
    }
    /**
     * gets the cooresponding auth for your personID
     * @param userName userName in question
     * @return returns that auth
     * @throws SQLException throws if token isnt there
     */
    public Auth getTok(String userName) throws SQLException {
        connection = DriverManager.getConnection(dataBName);
        
        String count = "SELECT count(*) from authToken where userName = ?";
        stmt = connection.prepareStatement(count);
        stmt.setString(1, userName);
        results = stmt.executeQuery();
        int num = results.getInt(1);
        stmt.close();
        if(num < 1){
            return null;
        }

        String query = "SELECT * FROM authToken where userName = ?";
        stmt = connection.prepareStatement(query);
        stmt.setString(1, userName);
        results = stmt.executeQuery();
        
        Auth a = new Auth(
            results.getString(1),
            results.getString(2)
        );
        stmt.close();
        return a;
    }
    /**
     * adds the token to the database table
     * @param token token being added
     * @throws SQLException throws if token is already there or if data doesnt fit in the table
     */
    public void addTok(Auth token) throws SQLException {
        connection = DriverManager.getConnection(dataBName);
        
        String count = "SELECT count(*) from authToken where authToken = ?";
        stmt = connection.prepareStatement(count);
        stmt.setString(1, token.getAuthToken());
        results = stmt.executeQuery();
        int num = results.getInt(1);
        stmt.close();
        if(num > 1){
            throw new SQLException("AuthToken already exists");
        }
        
        String insert = "insert into authToken (userName, authToken) values ( ?, ?)";
        stmt = connection.prepareStatement(insert);
        stmt.setString(1, token.getUserName());
        stmt.setString(2, token.getAuthToken());
        stmt.executeUpdate();
        stmt.close();
    }
    /**
     * clears all the data from the table
     * @throws SQLException if error occurs
     */
    public void clearTable() throws SQLException{
        connection = DriverManager.getConnection(dataBName);
        stmt = connection.prepareStatement("Delete from authToken");
        stmt.executeUpdate();
        stmt.close();
    }

    public String getAssoUserName(String authToken) throws SQLException{
        
        connection = DriverManager.getConnection(dataBName);
        
        String count = "SELECT count(*) from authToken where authToken = ?";
        stmt = connection.prepareStatement(count);
        stmt.setString(1, authToken);
        results = stmt.executeQuery();
        int num = results.getInt(1);
        stmt.close();
        if(num < 1){
            return null;
        }

        String query = "SELECT userName from authToken where authToken = ?";
        stmt = connection.prepareStatement(query);
        stmt.setString(1, authToken);
        results = stmt.executeQuery();
        String userName = results.getString(1);
        stmt.close();

        return userName;

    }

}