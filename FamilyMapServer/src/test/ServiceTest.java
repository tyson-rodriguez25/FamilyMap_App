package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import model.*;
import DataAccess.*;
import request.*;
import service.*;
import response.*;

public class ServiceTest {

    
    Service service = new Service();

    UserDAO ud = new UserDAO();
    User u1 = new User("nick3", "1234", "nick@gamil.com", "Nicolas", "Ray", "m", "5678");

    PersonDAO pd = new PersonDAO();
    Person p1 = new Person("5678", "nick3", "Nicolas", "Ray", "m", "2345", "3456", "4567");

    AuthDAO ad = new AuthDAO();
    Auth a1 = new Auth("nick3", "1111");

    EventDAO ed = new EventDAO();
    Event e1 = new Event("1234", "nick3", "5678", 420.69f, 69.420f, "Russia", "Moskow", "Marriage", 1996);

    User[] u = {u1};
    Event[] e = {e1};
    Person[] p = {p1};


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
            service.clear();
        } catch (Exception e) {
            System.out.println("Dump Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }

    @Test
    public void registerTest(){
        try{
            RegisterRequest rr = new RegisterRequest("nick2", "fruitbat", "me@gfys.com", "yup", "cat", "f");
            InheritResponse ir = service.register(rr);
            assertTrue(ir instanceof RegisterResponse);
        } catch (Exception e) {
            System.out.println("RegisterRequest Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }

    }
    @Test
    public void registerFailTest(){
        try{
            RegisterRequest rr = new RegisterRequest("nick2", "fruitbat", "me@gfys.com", "yup", "cat", "t");
            InheritResponse ir = service.register(rr);
            assertTrue(ir instanceof ErrorResponse);
        } catch (Exception e) {
            System.out.println("RegisterRequest Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
    @Test
    public void fillTest(){
        try{
            UserDAO ud = new UserDAO();
            User u1 = new User("nick3", "1234", "nick@gamil.com", "Nicolas", "Brown", "m", "5678");
            ud.addUser(u1);
            InheritResponse ir = service.fill(4, u1.getUserName());
            assertTrue(ir instanceof SuccessResponse);

        } catch (Exception e) {
            System.out.println("Fill Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
    @Test
    public void fillFailTest(){
        try{
            UserDAO ud = new UserDAO();
            User u1 = new User("nick3", "1234", "nick@gamil.com", "Nicolas", "Brown", "m", "5678");
            //ud.addUser(u1);
            InheritResponse ir = service.fill(4, u1.getUserName());
            assertTrue(ir instanceof ErrorResponse);

        } catch (Exception e) {
            System.out.println("Fill Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
    @Test
    public void clearTest(){
        try{
            UserDAO usnd = new UserDAO();
            EventDAO esnd = new EventDAO();
            PersonDAO psnd = new PersonDAO();
            AuthDAO asnd = new AuthDAO();
            
            User u1 = new User("nick3", "1234", "nick@gamil.com", "Nicolas", "Brown", "m", "5678");
            Person p1 = new Person("1234", "nick2", "Nick", "Ray", "m", "2345", "3456", "4567");
            Auth a1 = new Auth("nick3", "1234");
            Event e1 = new Event("nick3", "1234", "4321", 420.69f, 69.420f, "Russia", "Moskow", "Marriage", 1996);

            usnd.addUser(u1);
            esnd.addEvent(e1);
            psnd.addPerson(p1);
            asnd.addTok(a1);
            
            service.clear();
            
            assertNull(usnd.getUser(u1.getUserName()));
            assertNull(psnd.getPerson(p1.getPersonID()));
            assertNull(asnd.getTok(a1.getUserName()));
            assertNull(esnd.getEvent(e1.getEventID()));

        } catch (Exception e) {
            System.out.println("Clear Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
    @Test
    public void loginTest(){
        try{
            UserDAO usnd = new UserDAO();
            User u1 = new User("nick3", "1234", "nick@gamil.com", "Nicolas", "Brown", "m", "5678");
            usnd.addUser(u1);
            LoginRequest lr = new LoginRequest(u1.getUserName(), u1.getPassword());
            InheritResponse ir = service.login(lr);
            assertTrue(ir instanceof LoginResponse);
        } catch (Exception e) {
            System.out.println("Login Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }

    @Test
    public void loginFailTest(){
        try{
            UserDAO usnd = new UserDAO();
            User u1 = new User("nick3", "1234", "nick@gamil.com", "Nicolas", "Brown", "m", "5678");
            //usnd.addUser(u1);
            LoginRequest lr = new LoginRequest(u1.getUserName(), u1.getPassword());
            InheritResponse ir = service.login(lr);
            assertTrue(ir instanceof ErrorResponse);
        } catch (Exception e) {
            System.out.println("Login Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
    @Test
    public void loadTest(){
        try{
            LoadRequest lr = new LoadRequest(u,p,e);
            InheritResponse ir = service.load(lr);
            assertNotNull(ud.getUser(u1.getUserName()));
            assertNotNull(ed.getEvent(e1.getEventID()));
            assertNotNull(pd.getPerson(p1.getPersonID()));
        } catch (Exception e) {
            System.out.println("LoadRequest Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
    @Test
    public void loadFailTest(){
        try{
            User[] u2 = {};
            Person[] p2 = {};
            Event[] e2 = {};
            LoadRequest lr = new LoadRequest(u2,p2,e2);
            InheritResponse ir = service.load(lr);
            assertNull(ud.getUser(u1.getUserName()));
            assertNull(ed.getEvent(e1.getEventID()));
            assertNull(pd.getPerson(p1.getPersonID()));
        } catch (Exception e) {
            System.out.println("LoadRequest Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
    @Test
    public void getPersonIDTest(){
        try{
            ud.addUser(u1);
            pd.addPerson(p1);
            ad.addTok(a1);
            InheritResponse ir = service.getPersonID(p1.getPersonID(), a1.getAuthToken());
            assertTrue(ir instanceof PersonResponse);

        } catch (Exception e) {
            System.out.println("getpersonID Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
    @Test
    public void getPersonIDFailTest(){
        try{
            ud.addUser(u1);
            pd.addPerson(p1);
            //ad.addTok(a1);
            InheritResponse ir = service.getPersonID(p1.getPersonID(), a1.getAuthToken());
            assertTrue(ir instanceof ErrorResponse);

        } catch (Exception e) {
            System.out.println("getpersonID Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
    @Test
    public void getPersonUserTest(){
        try{
            ud.addUser(u1);
            pd.addPerson(p1);
            ad.addTok(a1);
            InheritResponse ir = service.getPersonUser(a1.getAuthToken());
            assertTrue(ir instanceof PersonResponseArray);
        } catch (Exception e) {
            System.out.println("getPersonUSer Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
    @Test
    public void getPersonUserFailTest(){
        try{
            ud.addUser(u1);
            pd.addPerson(p1);
            //ad.addTok(a1);
            InheritResponse ir = service.getPersonUser(a1.getAuthToken());
            assertTrue(ir instanceof ErrorResponse);
        } catch (Exception e) {
            System.out.println("getPersonUSer Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
    @Test
    public void getEventIDTest(){
        try{
            ud.addUser(u1);
            pd.addPerson(p1);
            ad.addTok(a1);
            ed.addEvent(e1);
            InheritResponse ir = service.getEventID(e1.getEventID(), a1.getAuthToken());

            assertTrue(ir instanceof EventResponse);

        } catch (Exception e) {
            System.out.println("getEventID Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
    @Test
    public void getEventIDFailTest(){
        try{
            ud.addUser(u1);
            pd.addPerson(p1);
            ad.addTok(a1);
            //ed.addEvent(e1);
            InheritResponse ir = service.getEventID(e1.getEventID(), a1.getAuthToken());
            assertTrue(ir instanceof ErrorResponse);

        } catch (Exception e) {
            System.out.println("getEventID Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
    @Test
    public void getEventUserTest(){
        try{
            ud.addUser(u1);
            pd.addPerson(p1);
            ad.addTok(a1);
            ed.addEvent(e1);
            InheritResponse ir = service.getEventUser(a1.getAuthToken());
            assertTrue(ir instanceof EventResponseArray);

        } catch (Exception e) {
            System.out.println("getEventUser Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }
    @Test
    public void getEventUserFailTest(){
        try{
            //ud.addUser(u1);
            //pd.addPerson(p1);
            //ad.addTok(a1);
            //ed.addEvent(e1);
            InheritResponse ir = service.getEventUser(a1.getAuthToken());
            assertTrue(ir instanceof ErrorResponse);

        } catch (Exception e) {
            System.out.println(" getEventUser Error " + e.getClass());
            System.out.println("Message: " + e.getMessage());
        }
    }

}
