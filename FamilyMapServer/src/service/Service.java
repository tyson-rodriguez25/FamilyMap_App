package service;

import model.*;
import request.*;
import response.*;
import com.google.gson.Gson;

import java.io.FileReader;
import java.io.Reader;
import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;

import DataAccess.*;

public class Service{
    
    PersonDAO pDao = new PersonDAO();
    EventDAO eDao= new EventDAO();
    AuthDAO aDao = new AuthDAO();
    UserDAO uDao = new UserDAO();
    Database db = new Database();
    int peopleCount = 0;
    int eventCount = 0;
    LocationData locData;
    Names femNames;
    Names malNames;
    Names surNames;
    Gson gson = new Gson();

    public Service(){
        try{
            Reader locationReader = new FileReader("src/service/locations.json");
            Reader femaleNReader = new FileReader("src/service/fnames.json");
            Reader maleNReader = new FileReader("src/service/mnames.json");
            Reader surNReader = new FileReader("src/service/snames.json");
            locData = gson.fromJson(locationReader, LocationData.class);
            femNames = gson.fromJson(femaleNReader, Names.class);
            malNames = gson.fromJson(maleNReader, Names.class);
            surNames = gson.fromJson(surNReader, Names.class);

            db.openConnection();
            db.createTables();
            db.closeConnection(true);


        }
        catch(Exception e){
            System.out.println("Construction Error: " + e.getClass() + " " + e.getMessage());
        }
    }


    /**
     * looks for user in database and if it isnt there, then it makes a personID and a authorization token.
     * uses fill to make a family tree and puts the auth and user into their tables
     * @param r is said request
     * @return returns registerRequest if it works and an error if it doesn't
     */
    public InheritResponse register(RegisterRequest r){
        try{
            String userName = r.getUserName();
            User u = uDao.getUser(userName);
            if(u != null){
                return new ErrorResponse("Error: User already exists");
            }
            UUID uuid = UUID.randomUUID();
            String personID = uuid.toString().substring(0,8);

            uDao.addUser(new User(
                            userName,
                            r.getPassword(),
                            r.getEmail(),
                            r.getFirstName(),
                            r.getLastName(),
                            r.getGender(),
                            personID
                    )
            );
            InheritResponse login = login(new LoginRequest(userName, r.getPassword()));
            if(!(login instanceof LoginResponse)){
                return new ErrorResponse("Error: Login failure");
            }
            String authToken = ((LoginResponse)login).getAuthToken();
            fill(4, userName); // CHANGE FOR FINAL PROJECT!!!!!

            return new RegisterResponse(
                    authToken,
                    userName,
                    personID
            );
        }catch (Exception e) {
            return new ErrorResponse("Register error: " + e.getClass() + " Message: " + e.getMessage());
        }
    }



    String[] generateParents(int genCount, int year, String userName) throws SQLException{
        if(genCount == 0){
            String[] empty = {null, null};
            return empty;
        }

        UUID uuid = UUID.randomUUID();
        String fatherID = uuid.toString().substring(0,8);
        uuid = UUID.randomUUID();
        String motherID = uuid.toString().substring(0,8);

        String[] parents = {fatherID, motherID};
        String[] DadParents = generateParents(genCount-1, year-24, userName);
        String[] MomParents = generateParents(genCount-1, year-24, userName);
        String lastName = getSurName();

        pDao.addPerson(new Person(
                        fatherID,
                        userName,
                        getMalName(),
                        lastName,
                        "m",
                        DadParents[0],
                        DadParents[1],
                        motherID
                )
        );
        peopleCount++;
        generateEvents(fatherID, userName, year);
        pDao.addPerson(new Person(
                        motherID,
                        userName,
                        getFemName(),
                        lastName,
                        "f",
                        MomParents[0],
                        MomParents[1],
                        fatherID
                )
        );
        peopleCount++;
        generateEvents(motherID, userName, year);
        generateMarriage(fatherID, motherID, userName, year);
        return parents;
    }
    /**
     * go through each of the DAOs and call dropTable to empty the tables
     * @return successresult if works and errorresult if dont work
     */
    public InheritResponse clear() {
        try {
            db.openConnection();
            db.createTables();
            db.closeConnection(true);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        try{
            pDao.clearTable();
            eDao.clearTable();
            aDao.clearTable();
            uDao.clearTable();
            return new SuccessResponse("Clear succeeded");

        }catch (Exception e) {
            return new ErrorResponse("Clear error: " + e.getClass() + " Message: " + e.getMessage());
        }
    }

    /**
     * If the user exists then delete pre-existing family and generate new family tree
     * @return successResponse if it works and errorResponse if it fails
     */
    public InheritResponse fill(int genCount, String userName){
        try{
            //clear();
            peopleCount = 0;
            eventCount = 0;
            int year = 1996;
            User user = uDao.getUser(userName);
            if(user == null){
                return new ErrorResponse("Error: The userName does not exist");
            }
            if(genCount < 0){
                return new ErrorResponse("Error: Invalid number of generations");
            }
            pDao.dropAssoPerson(userName);
            eDao.dropAssoEvents(userName);
            String [] parents = generateParents(genCount, year-40, userName);

            pDao.addPerson(new Person(
                            user.getPersonID(),
                            userName,
                            user.getFirstName(),
                            user.getLastName(),
                            user.getGender(),
                            parents[0],
                            parents[1],
                            null
                    )
            );
            peopleCount++;

            UUID uuid = UUID.randomUUID();
            String eventID = uuid.toString().substring(0,8);
            Location birthlocal = getLocation();
            eDao.addEvent(new Event(
                            eventID,
                            userName,
                            user.getPersonID(),
                            birthlocal.latitude,
                            birthlocal.longitude,
                            birthlocal.country,
                            birthlocal.city,
                            "birth",
                            year
                    )
            );
            eventCount++;

            return new SuccessResponse("Successfully added " + peopleCount + " persons and " + eventCount + " events to the database.");

        }catch (Exception e) {
            return new ErrorResponse("Fill error: " + e.getClass() + " Message: " + e.getMessage());
        }
    }

    /**
     * if you have the right authToken then use the eventDao to retrieve the event with that eventID
     * @return  successresult if works and errorresult if didnt work
     */
    public InheritResponse getEventID(String eventID, String authString){
        try{
            String userName = aDao.getAssoUserName(authString);
            if(userName == null){
                return new ErrorResponse("Error: The authString is not here");
            }
            Event eve = eDao.getEvent(eventID);
            if(eve == null){
                return new ErrorResponse("Error: The eventID is not here");
            }
            if(!eve.getAssociatedUsername().equals(userName)){
                return new ErrorResponse("Error: The event does not belong to the user");
            }

            return new EventResponse(
                    eve.getEventID(),
                    eve.getAssociatedUsername(),
                    eve.getPersonID(),
                    eve.getLatitude(),
                    eve.getLongitude(),
                    eve.getCountry(),
                    eve.getCity(),
                    eve.getEventType(),
                    eve.getYear()
            );


        } catch (Exception e) {
            return new ErrorResponse("Server error: " + e.getClass() + " Message: " + e.getMessage());
        }
    }

    void generateEvents(String personID, String userName, int refYear) throws SQLException{

        UUID uuid = UUID.randomUUID();
        String eventID = uuid.toString().substring(0,8);

        Location birthlocal = getLocation();

        eDao.addEvent(new Event(
                        eventID,
                        userName,
                        personID,
                        birthlocal.latitude,
                        birthlocal.longitude,
                        birthlocal.country,
                        birthlocal.city,
                        "birth",
                        refYear
                )
        );
        eventCount++;



        uuid = UUID.randomUUID();
        eventID = uuid.toString().substring(0,8);
        Location deathlocal = getLocation();

        eDao.addEvent(new Event(
                        eventID,
                        userName,
                        personID,
                        deathlocal.latitude,
                        deathlocal.longitude,
                        deathlocal.country,
                        deathlocal.city,
                        "death",
                        refYear+95
                )
        );
        eventCount++;
    }

    void generateMarriage(String fatherID, String motherID, String userName, int refYear) throws SQLException{

        UUID uuid = UUID.randomUUID();
        String fatherEventID = uuid.toString().substring(0,8);

        uuid = UUID.randomUUID();
        String motherEventID = uuid.toString().substring(0,8);

        Location marriagelocal = getLocation();

        eDao.addEvent(new Event(
                        fatherEventID,
                        userName,
                        fatherID,
                        marriagelocal.latitude,
                        marriagelocal.longitude,
                        marriagelocal.country,
                        marriagelocal.city,
                        "marriage",
                        refYear+25
                )
        );
        eventCount++;

        eDao.addEvent(new Event(
                        motherEventID,
                        userName,
                        motherID,
                        marriagelocal.latitude,
                        marriagelocal.longitude,
                        marriagelocal.country,
                        marriagelocal.city,
                        "marriage",
                        refYear+25
                )
        );
        eventCount++;
    }
    /**
     * add each item in the array to its table through the DAO
     * @return successresult if works and errorresult if didnt work
     */

    /**
     * check username and password and see if they are correct
     * get an authtoken for the login
     * @param r is said request
     * @return successresult if works and errorresult if didnt work
     */
    public InheritResponse login(LoginRequest r){
        try{
            String userName = r.getUserName();
            String password = r.getPassword();
            String personID = uDao.checkLogin(userName, password);
            if(personID == null){
                return new ErrorResponse("Error: Invalid Login");
            }
            UUID uuid = UUID.randomUUID();
            String authToken = uuid.toString().substring(0,8);
            aDao.addTok(new Auth(userName, authToken));

            return new LoginResponse(
                    authToken,
                    userName,
                    personID
            );

        }catch (Exception e) {
            return new ErrorResponse("Login error: " + e.getClass() + " Message: " + e.getMessage());
        }
    }

    /**
     * if you have the right authToken then use the personDao to retrieve the person with that personID
     * @return  successresult if works and errorresult if didnt work
     */
    public InheritResponse getPersonID(String personID, String authString){
        try{
            String userName = aDao.getAssoUserName(authString);
            if(userName == null){
                return new ErrorResponse("Error: The authString is not here");
            }
            Person pep = pDao.getPerson(personID);
            if(pep == null){
                return new ErrorResponse("Error: The personID is not here");
            }
            if(!pep.getAssociatedUsername().equals(userName)){
                return new ErrorResponse("Error: The person does not belong to the user");
            }

            return new PersonResponse(
                pep.getAssociatedUsername(),
                pep.getPersonID(),
                pep.getFirstName(),
                pep.getLastName(),
                pep.getGender(),
                pep.getFatherID(),
                pep.getMotherID(),
                pep.getSpouseID()
            );


        } catch (Exception e) {
            return new ErrorResponse("Server error: " + e.getClass() + " Message: " + e.getMessage());
        }
        
    }
    /**
     *  if you have the right authToken and auth table to get the right user name and then use the personDao to retrieve all persons associated with that username
     * @return successresult if works and errorresult if didnt work
     */
    public InheritResponse getPersonUser(String authToken){
        try{
            String userName = aDao.getAssoUserName(authToken);
            if(userName == null){
                return new ErrorResponse("Error: The authToken is not here");
            }

            Person[] pop = pDao.getAssoPerson(userName);
            return new PersonResponseArray(pop);
            
        } catch (Exception e) {
            return new ErrorResponse("Server error: " + e.getClass() + " Message: " + e.getMessage());
        }
    }

    public InheritResponse load(LoadRequest r){
        //clear();
        try{
            clear();
            Person[] personsList = r.getPersons();
            User[] usersList = r.getUsers();
            Event[] eventsList = r.getEvents();

            for(User uLoad: usersList){
                uDao.addUser(uLoad);
            }
            for(Person pLoad: personsList){
                pDao.addPerson(pLoad);
            }

            for(Event eLoad: eventsList){
                eDao.addEvent(eLoad);
            }

            return new SuccessResponse("Successfully added " + usersList.length + " users, " + personsList.length + " persons, and " + eventsList.length + " events to the database.");

        } catch (Exception e) {
            return new ErrorResponse("Load error: " + e.getClass() + " Message: " + e.getMessage());
        }
    }

    /**
     *  if you have the right authToken and auth table to get the right user name and then use the eventDao to retrieve all events associated with that username
     * @return successresult if works and errorresult if didnt work
     */
    public InheritResponse getEventUser(String authToken){
        try{
            String userName = aDao.getAssoUserName(authToken);
            if(userName == null){
                return new ErrorResponse("Error: The authToken is not here");
            }

            Event[] evo = eDao.getAssoEvent(userName);
            return new EventResponseArray(evo);
            
        } catch (Exception e) {
            return new ErrorResponse("Server error: " + e.getClass() + " Message: " + e.getMessage());
        }
    }
    String getMalName(){
        Random rand = new Random();
        int i = rand.nextInt(malNames.data.length);
        return malNames.data[i];
    }

    String getFemName(){
        Random rand = new Random();
        int i = rand.nextInt(femNames.data.length);
        return femNames.data[i];
    }

    Location getLocation(){
        Random rand = new Random();
        int i = rand.nextInt(locData.data.length);
        return locData.data[i];
    }

    String getSurName(){
        Random rand = new Random();
        int i = rand.nextInt(surNames.data.length);
        return surNames.data[i];
    }    

    
}

class LocationData{
    Location[] data;
}
class Location{
    String country;
    String city;
    float latitude;
    float longitude;
}
class Names{
    String[] data;
}
  