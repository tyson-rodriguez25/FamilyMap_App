package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import DataAccess.Database;
import DataAccess.EventDAO;
import model.Event;
public class EventDAOTest {
    EventDAO esnd = new EventDAO();
    Event e1 = new Event("nick3", "1234", "4321", 420.69f, 69.420f, "Russia", "Moskow", "Marriage", 1996);
    Event e2 = new Event("nick3", "1243", "4312", 520.67f, 67.520f, "China", "Hong Kong", "Death", 1997);
    Event e3 = new Event("nick1", "1211", "4311", 520.64f, 67.524f, "Mexico", "Mexico City", "Death", 1997);


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
            esnd.clearTable();
        } catch (Exception e) {
            System.out.println("Dump Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
    //test if event is added
    @Test
    public void testAddEvent() {
        try {
            esnd.addEvent(e1);
            Event testEvent = esnd.getEvent(e1.getEventID());
            assertEquals(e1, testEvent);
        } catch (Exception e) {
            System.out.println("AddEvent Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
    //if failed to add, what happens
    @Test
    public void testAddEventFail() {
        try {
            Event testEvent = esnd.getEvent("Phil");
            assertNull(testEvent);
        } catch (Exception e) {
            System.out.println("AddEventfail Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
    //tests if event can be retrieved
    @Test
    public void testGetEvent() {
        try {
            esnd.addEvent(e1);
            Event testEvent = esnd.getEvent(e1.getEventID());
            assertEquals(e1, testEvent);
        } catch (Exception e) {
            System.out.println("Addevent Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
    //tests if addevent fails what would happen
    @Test
    public void testGetEventFail() {
        try {
            Event testEvent = esnd.getEvent("Phil");
            assertNull(testEvent);
        } catch (Exception e) {
            System.out.println("Addeventfail Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
    //tests if table gets cleared
    @Test
    public void testClearEventTable() {
        try {
            esnd.addEvent(e1);
            esnd.addEvent(e2);
            esnd.clearTable();
            assertNull(esnd.getEvent(e1.getEventID()));
            assertNull(esnd.getEvent(e2.getEventID()));

        } catch (Exception e) {
            System.out.println("clearEventTable Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
    //tests if get assoevent works
    @Test
    public void testGetAssoEvent(){
        try{
            esnd.addEvent(e1);
            esnd.addEvent(e2);
            esnd.addEvent(e3);
            Event[] expected = {e1, e2};
            Event[] vents = esnd.getAssoEvent("nick3");
            assertNotNull(vents);
            assertTrue(vents.length == expected.length);
            for(int i = 0 ; i < vents.length; i++){
                assertEquals(expected[i], vents[i]);
            }


        } catch (Exception e) {
            System.out.println("clearEventTable Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }

    }
    @Test
    public void testDropAssoevent() {
        try {
            esnd.addEvent(e1);
            esnd.addEvent(e2);
            esnd.addEvent(e3);
            esnd.dropAssoEvents(e1.getAssociatedUsername());
            assertNull(esnd.getEvent(e1.getEventID()));
            assertNull(esnd.getEvent(e2.getEventID()));
            assertNotNull(esnd.getEvent(e3.getEventID()));

        } catch (Exception e) {
            System.out.println("dropassoevent Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }


}
