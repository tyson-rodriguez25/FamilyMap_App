package com.example.familymap.server;

import com.example.familymap.model.*;
import com.example.familymap.request.*;
import com.example.familymap.response.*;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;


public class proxy {

    Gson gson = new Gson();

    String host;
    String port;

    public proxy(String host, String port) {
        this.host = host;
        this.port = port;
    }

    public LoginResponse login(LoginRequest lr) throws Exception{
        System.out.println("PROXY: login:");
        System.out.println(lr);

        String url = "http://" + host + ":" + port + "/user/login";
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        //connection.setRequestProperty("Authorization", "1a2b4c8d");
        connection.connect();

        Writer out = new OutputStreamWriter(connection.getOutputStream());
        gson.toJson(lr, out);
        out.close();

        System.out.println("request body: " + gson.toJson(lr));
        System.out.println();

        // need to wait for response or operation will fail
        // add checks for failure status codes
        int status = connection.getResponseCode();
        Reader in = new InputStreamReader(connection.getInputStream());
        if(status == 200){
            LoginResponse logr = gson.fromJson(in, LoginResponse.class);
            return logr;
        }
        else{
            ErrorResponse er = gson.fromJson(in, ErrorResponse.class);
            return null;
        }

    }
    public RegisterResponse register(RegisterRequest regr) throws Exception{
        System.out.println("PROXY: register:");
        System.out.println(regr);

        String url = "http://" + host + ":" + port + "/user/register";
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        //connection.setRequestProperty("Authorization", "1a2b4c8d");
        connection.connect();

        Writer out = new OutputStreamWriter(connection.getOutputStream());
        gson.toJson(regr, out);
        out.close();

        System.out.println("request body: " + gson.toJson(regr));
        System.out.println();

        // need to wait for response or operation will fail
        // add checks for failure status codes
        int status = connection.getResponseCode();
        Reader in = new InputStreamReader(connection.getInputStream());
        if(status == 200){
            RegisterResponse rr = gson.fromJson(in, RegisterResponse.class);
            return rr;
        }
        else{
            ErrorResponse er = gson.fromJson(in, ErrorResponse.class);
            return null;
        }

    }
    public Event[] event(String authToken) throws Exception{
        System.out.println("PROXY: login:");
        System.out.println(authToken);

        String url = "http://" + host + ":" + port + "/event";
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setRequestProperty("Authorization", authToken);
        connection.connect();

        // need to wait for response or operation will fail
        // add checks for failure status codes
        int status = connection.getResponseCode();
        Reader in = new InputStreamReader(connection.getInputStream());
        if(status == 200){
            EventResponseArray era = gson.fromJson(in, EventResponseArray.class);
            Event[] events = era.getEvents();
            return events;
        }
        else{
            ErrorResponse er = gson.fromJson(in, ErrorResponse.class);
            return null;
        }

    }
    public Person[] person(String authToken) throws Exception{
        System.out.println("PROXY: login:");
        System.out.println(authToken);

        String url = "http://" + host + ":" + port + "/person";
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setRequestProperty("Authorization", authToken);
        connection.connect();

        // need to wait for response or operation will fail
        // add checks for failure status codes
        int status = connection.getResponseCode();
        Reader in = new InputStreamReader(connection.getInputStream());
        if(status == 200){
            PersonResponseArray pra = gson.fromJson(in, PersonResponseArray.class);
            in.close();
            Person[] persons = pra.getPersons();
            return persons;
        }
        else{
            ErrorResponse er = gson.fromJson(in, ErrorResponse.class);
            return null;
        }

    }



    // void addPerson(Person person) throws Exception {

    //     System.out.println("PROXY: addPerson:");
    //     System.out.println(person);

    //     String url = "http://localhost:8888/person/add";
    //     HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

    //     connection.setRequestMethod("POST");
    //     connection.setDoOutput(true);
    //     connection.setRequestProperty("Authorization", "1a2b4c8d");
    //     connection.connect();

    //     Writer out = new OutputStreamWriter(connection.getOutputStream());
    //     gson.toJson(person, out);
    //     out.close();

    //     System.out.println("request body: " + gson.toJson(person));
    //     System.out.println();

    //     // need to wait for response or operation will fail
    //     // add checks for failure status codes
    //     int status = connection.getResponseCode();

    // }

    // Person getPerson() throws Exception {

    //     String url = "http://localhost:8888/person/get";
    //     HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

    //     connection.setRequestMethod("GET");
    //     connection.setDoOutput(false);
    //     connection.setRequestProperty("Authorization", "1a2b4c8d");
    //     connection.connect();

    //     // add checks for failure status codes
    //     int status = connection.getResponseCode();

    //     Reader in = new InputStreamReader(connection.getInputStream());
    //     Person person = gson.fromJson(in, Person.class);

    //     System.out.println("PROXY: getPerson:");
    //     System.out.println(person);
    //     System.out.println();

    //     return person;

    // }

}