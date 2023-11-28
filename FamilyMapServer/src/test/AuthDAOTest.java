package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import DataAccess.Database;
import DataAccess.AuthDAO;
import model.Auth;

public class AuthDAOTest {
    AuthDAO asnd = new AuthDAO();
    Auth a1 = new Auth("nick3", "1234");
    Auth a2 = new Auth("nick2", "1243");


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
            asnd.clearTable();
        } catch (Exception e) {
            System.out.println("Dump Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
    //test if auth is added
    @Test
    public void testAddAuth() {
        try {
            asnd.addTok(a1);
            Auth testAuth = asnd.getTok("nick3");
            assertEquals(a1, testAuth);
        } catch (Exception e) {
            System.out.println("AddAuth Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
    //if failed to add, what happens
    @Test
    public void testAddAuthFail() {
        try {
            Auth testAuth = asnd.getTok("Phil");
            assertNull(testAuth);
        } catch (Exception e) {
            System.out.println("AddAuthfail Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
    //tests if auth can be retrieved
    @Test
    public void testGetAuth() {
        try {
            asnd.addTok(a1);
            Auth testAuth = asnd.getTok("nick3");
            assertEquals(a1, testAuth);
        } catch (Exception e) {
            System.out.println("AddAuth Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
    //tests if addauth fails what would happen
    @Test
    public void testGetAuthFail() {
        try {
            Auth testAuth = asnd.getTok("Phil");
            assertNull(testAuth);
        } catch (Exception e) {
            System.out.println("AddAuthfail Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
    //tests if table gets cleared
    @Test
    public void testClearAuthTable() {
        try {
            asnd.addTok(a1);
            asnd.addTok(a2);
            asnd.clearTable();
            assertNull(asnd.getTok(a1.getUserName()));
            assertNull(asnd.getTok(a2.getUserName()));

        } catch (Exception e) {
            System.out.println("clearAuthTable Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
//    @Test
//    public void testGetAssoUserName(){
//        try{
//            asnd.addTok(a1);
//            asnd.addTok(a2);
//            asnd.addTok(a3);
//            Person[] expected = {p1, p2};
//            Person[] peeps = psnd.getAssoPerson(p1.getAssociatedUsername());
//            assertNotNull(peeps);
//            assertTrue(peeps.length == expected.length);
//            for(int i = 0 ; i < peeps.length; i++){
//                assertEquals(expected[i], peeps[i]);
//            }
//
//
//        } catch (Exception e) {
//            System.out.println("assoperson Error " + e.getClass());
//            System.out.println("Message: " + e.getMessage());
//        }
//
//    }
}
