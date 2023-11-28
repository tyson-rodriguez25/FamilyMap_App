package test;

import model.User;
import DataAccess.UserDAO;
import DataAccess.Database;
import request.LoginRequest;
import service.Service;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
public class UserDAOTest {
    UserDAO usnd = new UserDAO();
    User u1 = new User("nick4", "1234", "nick@gamil.com", "Nicolas", "Brown", "m", "5678");
    User u2 = new User("Shmob", "1235", "Shmob@gamil.com", "Glob", "Green", "m", "5679");
    User u3 = new User("Glob", "1236", "Glob@gamil.com", "Shmob", "Blue", "m", "5670");
    User u4 = new User("Bob", "1237", "bob@gamil.com", "Bob", "Gold", "m", "5000");

    @BeforeAll
    public static void SetUp() {
        try {
            Database db = new Database();
            db.openConnection();
            db.createTables();
            db.closeConnection(true);
        } catch (Exception e) {
            System.out.println("Database Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }

    @BeforeEach
    public void Dump() {
        try {
            usnd.clearTable();
        } catch (Exception e) {
            System.out.println("Dump Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
    //test if user is added
    @Test
    public void testAddUser() {
        try {
            usnd.addUser(u1);
            User testUser = usnd.getUser(u1.getUserName());
            assertEquals(u1, testUser);
        } catch (Exception e) {
            System.out.println("AddUser Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
    //if failed to add, what happens
    @Test
    public void testAddUserFail() {
        try {
            User testUser = usnd.getUser("Phil");
            assertNull(testUser);
        } catch (Exception e) {
            System.out.println("AddUserfail Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
    //tests if user can be retrieved
    @Test
    public void testGetUser() {
        try {
            usnd.addUser(u1);
            User testUser = usnd.getUser(u1.getUserName());
            assertEquals(u1, testUser);
        } catch (Exception e) {
            System.out.println("Adduser Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
    //tests if adduser fails what would happen
    @Test
    public void testGetUserFail() {
        try {
            User testUser = usnd.getUser("Phil");
            assertNull(testUser);
        } catch (Exception e) {
            System.out.println("AddUserfail Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
    //tests if table gets cleared
    @Test
    public void testClearUserTable() {
        try {
            usnd.addUser(u1);
            usnd.addUser(u2);
            usnd.addUser(u3);
            usnd.addUser(u4);
            usnd.clearTable();
            assertNull(usnd.getUser(u1.getUserName()));
            assertNull(usnd.getUser(u2.getUserName()));
            assertNull(usnd.getUser(u3.getUserName()));
            assertNull(usnd.getUser(u4.getUserName()));

        } catch (Exception e) {
            System.out.println("Add user Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }

    @Test
    public void testCheckLogin(){
        try{
            usnd.addUser(u1);
            String check = usnd.checkLogin(u1.getUserName(), u1.getPassword());
            assertEquals(u1.getPersonID(), check);
        }
        catch (Exception e) {
            System.out.println("checkLogin Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
    @Test
    public void testCheckLoginFail(){
        try{
            String check = usnd.checkLogin(u1.getUserName(), u1.getPassword());
            assertEquals(null, check);
        }
        catch (Exception e) {
            System.out.println("checkLogin Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
}
