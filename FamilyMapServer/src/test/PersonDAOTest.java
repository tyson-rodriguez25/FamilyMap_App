package test;

import model.Person;
import DataAccess.PersonDAO;
import DataAccess.Database;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class PersonDAOTest {
    PersonDAO psnd = new PersonDAO();
    Person p1 = new Person("1234", "nick2", "Nick", "Ray", "m", "2345", "3456", "4567");
    Person p2 = new Person("1111", "nick2", "Nickle", "Tay", "f", "2424", "3434", "4545");
    Person p3 = new Person("2222", "nick3", "Nicky", "Hay", "m", "2223", "3222", "4247");
    Person p4 = new Person("3333", "nick4", "Nickhe", "Say", "f", "2323", "3534", "4549");

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
            psnd.clearTable();
        } catch (Exception e) {
            System.out.println("Dump Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
    //tests if person is added
    @Test
    public void testAddPerson() {
        try {
            psnd.addPerson(p1);
            Person testPerson = psnd.getPerson("1234");
            assertEquals(p1, testPerson);
        } catch (Exception e) {
            System.out.println("AddPErson Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
    //tests if person add fails
    @Test
    public void testAddPersonFail() {
        try {
            Person testPerson = psnd.getPerson("3321");
            assertNull(testPerson);
        } catch (Exception e) {
            System.out.println("AddPersonfail Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
    //tests if data is returned correctly
    @Test
    public void testGetPerson() {
        try {
            psnd.addPerson(p1);
            Person testPerson = psnd.getPerson("1234");
            assertEquals(p1, testPerson);
        } catch (Exception e) {
            System.out.println("AddPErson Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
    //tests person that isnt there
    @Test
    public void testGetPersonFail() {
        try {
            Person testPerson = psnd.getPerson("3321");
            assertNull(testPerson);
        } catch (Exception e) {
            System.out.println("AddPersonfail Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
    //tests if table is emptied
    @Test
    public void testClearPersonTable() {
        try {
            psnd.addPerson(p1);
            psnd.addPerson(p2);
            psnd.addPerson(p3);
            psnd.addPerson(p4);
            psnd.clearTable();
            Person testPerson = psnd.getPerson("1234");
            assertNull(psnd.getPerson(p1.getPersonID()));
            assertNull(psnd.getPerson(p2.getPersonID()));
            assertNull(psnd.getPerson(p3.getPersonID()));
            assertNull(psnd.getPerson(p4.getPersonID()));

        } catch (Exception e) {
            System.out.println("AddPErson Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
    //tests if get assoperson works
    @Test
    public void testGetAssoPerson(){
        try{
            psnd.addPerson(p1);
            psnd.addPerson(p2);
            psnd.addPerson(p3);
            Person[] expected = {p1, p2};
            Person[] peeps = psnd.getAssoPerson(p1.getAssociatedUsername());
            assertNotNull(peeps);
            assertTrue(peeps.length == expected.length);
            for(int i = 0 ; i < peeps.length; i++){
                assertEquals(expected[i], peeps[i]);
            }


        } catch (Exception e) {
            System.out.println("assoperson Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }

    }
    @Test
    public void testDropAssoPerson() {
        try {
            psnd.addPerson(p1);
            psnd.addPerson(p2);
            psnd.addPerson(p3);
            psnd.addPerson(p4);
            psnd.dropAssoPerson(p1.getAssociatedUsername());
            assertNull(psnd.getPerson(p1.getPersonID()));
            assertNull(psnd.getPerson(p2.getPersonID()));
            assertNotNull(psnd.getPerson(p3.getPersonID()));
            assertNotNull(psnd.getPerson(p4.getPersonID()));

        } catch (Exception e) {
            System.out.println("dropassoperson Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
}
