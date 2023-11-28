package DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;



public class Database {
    private Connection conn;

    static {
        try {
            //This is how we set up the driver for our database
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Whenever we want to make a change to our database we will have to open a connection and use
    //Statements created by that connection to initiate transactions
    public Connection openConnection() throws DataAccessException {
        try {
            //The Structure for this Connection is driver:language:path
            //The pathing assumes you start in the root of your project unless given a non-relative path
            final String CONNECTION_URL = "jdbc:sqlite:familymap.sqlite";

            // Open a database connection to the file given in the path
            conn = DriverManager.getConnection(CONNECTION_URL);

            // Start a transaction
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to open connection to database");
        }

        return conn;
    }

    //When we are done manipulating the database it is important to close the connection. This will
    //End the transaction and allow us to either commit our changes to the database or rollback any
    //changes that were made before we encountered a potential error.

    //IMPORTANT: IF YOU FAIL TO CLOSE A CONNECTION AND TRY TO REOPEN THE DATABASE THIS WILL CAUSE THE
    //DATABASE TO LOCK. YOUR CODE MUST ALWAYS INCLUDE A CLOSURE OF THE DATABASE NO MATTER WHAT ERRORS
    //OR PROBLEMS YOU ENCOUNTER
    public void closeConnection(boolean commit) throws DataAccessException {
        try {
            if (commit) {
                //This will commit the changes to the database
                conn.commit();
            } else {
                //If we find out something went wrong, pass a false into closeConnection and this
                //will rollback any changes we made during this connection
                conn.rollback();
            }

            conn.close();
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to close database connection");
        }
    }

    public void createTables() throws DataAccessException {

        try (Statement stmt = conn.createStatement()){
            //First lets open our connection to our database.

            //We pull out a statement from the connection we just established
            //Statements are the basis for our transactions in SQL
            //Format this string to be exaclty like a sql create table command
            String createEvent = "CREATE TABLE IF NOT EXISTS events (" +
                "eventId varchar(255) primary key," +
                "userName varchar(255) not null," +
                "personId varchar(255) not null," +
                "latitude float not null," +
                "longitude float not null," +
                "country varchar(255) not null," +
                "city varchar(255) not null," +
                "eventType varchar(255) not null," +
                "year int not null" +
            ")";

            stmt.executeUpdate(createEvent);

            String createUser = "CREATE TABLE IF NOT EXISTS user(" +
                "userName varchar not null unique," +
                "password varchar(255) not null," +
                "email varchar(255) not null," +
                "firstName varchar(255) not null," +
                "lastName varchar(255) not null," +
                "gender varchar(1) check (gender = 'm' or gender = 'f')," +
                "personID varchar(10) primary key" +
            ")";
            stmt.executeUpdate(createUser);

            String createPerson = "CREATE TABLE IF NOT EXISTS persons(" +
                "personID varchar(255) primary key," +
                "userName varchar(255) not null," +
                "firstName varchar(255) not null," +
                "lastName varchar(255) not null," +
                "gender varchar(1) check (gender = 'm' or gender = 'f')," +
                "fatherID varchar(255)," +
                "motherID varchar(255)," +
                "spouseID varchar(255)" +
            ")";
            stmt.executeUpdate(createPerson);

            String createAuth = "CREATE TABLE IF NOT EXISTS authToken (" +
                "userName varchar(255) not null," +
                "authToken varchar(255) primary key" +
                ")";
            stmt.executeUpdate(createAuth);
//            conn.close();

            //if we got here without any problems we succesfully created the table and can commit
        } catch (SQLException e) {
            //if our table creation caused an error, we can just not commit the changes that did happen
            throw new DataAccessException("SQL Error encountered while creating tables");
        }


    }

    public void clearTables() throws DataAccessException
    {

        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM events";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
    }
}

