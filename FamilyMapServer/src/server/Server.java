package server;

import static java.net.HttpURLConnection.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.InetSocketAddress;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.File;
import java.nio.file.Files;

import response.*;
import request.*;
import model.*;

import service.Service;

class Server {

    Service service = new Service();
    Gson gson = new Gson();

    public static void main(String[] args) throws Exception {

        Server server = new Server();
        server.startServer();

    }

    void startServer() throws Exception {

        int port = 8080;

        System.out.println("server listening on port: " + port);
        System.out.println();

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", new RootHandler());
        server.createContext("/clear", new ClearHandler());
        server.createContext("/person", new PersonHandler());
        server.createContext("/event", new EventHandler());
        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/fill", new FillHandler());
        server.createContext("/load", new LoadHandler());
        server.createContext("/user/login", new LoginHandler());

        server.start();

    }

    class RootHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String uri = exchange.getRequestURI().toString();
            if(uri.equals("/")){
                uri = "src/web/index.html";
            }
            else{
                uri = "src/web/" + uri;
            }
            File f = new File(uri);
            if(!f.exists()){
                uri = "src/web/HTML/404.html";
                f = new File(uri);
                exchange.sendResponseHeaders(HTTP_NOT_FOUND, 0);
                Files.copy(f.toPath(), exchange.getResponseBody());
                exchange.getResponseBody().close();
            }
            exchange.sendResponseHeaders(HTTP_OK, 0);
            Files.copy(f.toPath(), exchange.getResponseBody());
            exchange.getResponseBody().close();            
        }

    }

    class ClearHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            service = new Service();
            System.out.println("SERVER: /clear handler");
            String method = exchange.getRequestMethod();
            if (!method.equals("POST")) {
                exchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
                ErrorResponse e = new ErrorResponse("Error: Wrong request method");
                Writer out = new OutputStreamWriter(exchange.getResponseBody());
                gson.toJson(e, out);
                out.close();
            } else {
                InheritResponse ir = service.clear();
                if (ir instanceof SuccessResponse) {
                    exchange.sendResponseHeaders(HTTP_OK, 0);
                } else {
                    exchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0); //Changed from HTTP_ACCEPTED
                }
                Writer out = new OutputStreamWriter(exchange.getResponseBody());
                gson.toJson(ir, out);
                out.close();
            }
        }
    }

    class PersonHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            System.out.println("SERVER: /person handler");
            String method = exchange.getRequestMethod();
            method = "GET";
            if (!method.equals("GET")) {
                exchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
                ErrorResponse e = new ErrorResponse("Error: Wrong request method");
                Writer out = new OutputStreamWriter(exchange.getResponseBody());
                gson.toJson(e, out);
                out.close();
            } else {
                String path = exchange.getRequestURI().toString();
                System.out.println("URI " + path);

                String auth = exchange.getRequestHeaders().getFirst("Authorization");
                System.out.println("Auth " + auth);

                if (path.length() > "/person".length()) { //Took away / from person
                    String personID = path.substring(8);
                    System.out.println("PersonID: " + personID);

                    InheritResponse ir = service.getPersonID(personID, auth);
                    if (ir instanceof PersonResponse) {
                        exchange.sendResponseHeaders(HTTP_OK, 0);
                    } else {
                        exchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0); //Changed from HTTP_ACCEPTED
                    }
                    Writer out = new OutputStreamWriter(exchange.getResponseBody());
                    gson.toJson(ir, out);
                    out.close();
                } else {

                    InheritResponse ir = service.getPersonUser(auth);
                    if (ir instanceof PersonResponseArray) {
                        exchange.sendResponseHeaders(HTTP_OK, 0);
                    } else {
                        exchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0); //Changed from HTTP_ACCEPTED
                    }
                    Writer out = new OutputStreamWriter(exchange.getResponseBody());
                    gson.toJson(ir, out);
                    out.close();
                }

            }
        }
    }

    class EventHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            System.out.println("SERVER: /person handler");
            String method = exchange.getRequestMethod();
            method = "GET";
            if (!method.equals("GET")) {
                exchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
                ErrorResponse e = new ErrorResponse("Error: Wrong request method");
                Writer out = new OutputStreamWriter(exchange.getResponseBody());
                gson.toJson(e, out);
                out.close();
            } else {
                String path = exchange.getRequestURI().toString();
                System.out.println("URI " + path);

                String auth = exchange.getRequestHeaders().getFirst("Authorization");
                System.out.println("Auth " + auth);

                if (path.length() > "/event".length()) { //Took away / from event
                    String eventID = path.substring(7);
                    System.out.println("EventID: " + eventID);

                    InheritResponse ir = service.getEventID(eventID, auth);
                    if (ir instanceof EventResponse) {
                        exchange.sendResponseHeaders(HTTP_OK, 0);
                    } else {
                        exchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0); //Changed from HTTP_ACCEPTED
                    }
                    Writer out = new OutputStreamWriter(exchange.getResponseBody());
                    gson.toJson(ir, out);
                    out.close();
                } else {

                    InheritResponse ir = service.getEventUser(auth);
                    if (ir instanceof EventResponseArray) {
                        exchange.sendResponseHeaders(HTTP_OK, 0);
                    } else {
                        exchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0); //Changed from HTTP_ACCEPTED
                    }
                    Writer out = new OutputStreamWriter(exchange.getResponseBody());
                    gson.toJson(ir, out);
                    out.close();
                }

            }
        }
    }

    class RegisterHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            System.out.println("SERVER: /user/register handler");

            String method = exchange.getRequestMethod();
            System.out.println("method: " + method);
            if (!method.equals("POST")) {
                exchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
                ErrorResponse e = new ErrorResponse("Error: Wrong request method");
                Writer out = new OutputStreamWriter(exchange.getResponseBody());
                gson.toJson(e, out);
                out.close();
            } else {
                Reader in = new InputStreamReader(exchange.getRequestBody());
                RegisterRequest rr = gson.fromJson(in, RegisterRequest.class);
                InheritResponse ir = service.register(rr);
                if (ir instanceof RegisterResponse) {
                    exchange.sendResponseHeaders(HTTP_OK, 0);
                } else {
                    exchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0); //Changed from HTTP_ACCEPTED
                }
                Writer out = new OutputStreamWriter(exchange.getResponseBody());
                gson.toJson(ir, out);
                out.close();
            }
        }
    }

    class FillHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException{
            System.out.println("SERVER: /fill handler");
            String method = exchange.getRequestMethod();
            if(!method.equals("POST")){
                exchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
                ErrorResponse e = new ErrorResponse("Error: Wrong request method");
                Writer out = new OutputStreamWriter(exchange.getResponseBody());
                gson.toJson(e, out);
                out.close();
            }
            else{
                String path = exchange.getRequestURI().toString();
                System.out.println("URI " + path);
                String trim = path.substring(6);
                String userName = "";
                int genCount = 4;
                int index = trim.indexOf("/");
                if(index == -1){
                    userName = trim;
                }
                else{
                    userName = trim.substring(0,index);
                    genCount = Integer.parseInt(trim.substring(index+1));
                }
                InheritResponse ir = service.fill(genCount, userName);
                if(ir instanceof SuccessResponse){
                    exchange.sendResponseHeaders(HTTP_OK, 0);
                }
                else{
                    exchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0); //Changed from HTTP_ACCEPTED
                }
                Writer out = new OutputStreamWriter(exchange.getResponseBody());
                gson.toJson(ir, out);
                out.close();  
            }
        }
    }
    class LoginHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            System.out.println("SERVER: /user/login handler");

            String method = exchange.getRequestMethod();
            System.out.println("method: " + method);
            if (!method.equals("POST")) {
                exchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
                ErrorResponse e = new ErrorResponse("Error: Wrong request method");
                Writer out = new OutputStreamWriter(exchange.getResponseBody());
                gson.toJson(e, out);
                out.close();
            } else {
                Reader in = new InputStreamReader(exchange.getRequestBody());
                LoginRequest lr = gson.fromJson(in, LoginRequest.class);
                InheritResponse ir = service.login(lr);
                if (ir instanceof LoginResponse) {
                    exchange.sendResponseHeaders(HTTP_OK, 0);
                } else {
                    exchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0); //Changed from HTTP_ACCEPTED
                }
                Writer out = new OutputStreamWriter(exchange.getResponseBody());
                gson.toJson(ir, out);
                out.close();
            }
        }
    }
    class LoadHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            System.out.println("SERVER: /load handler");

            String method = exchange.getRequestMethod();
            System.out.println("method: " + method);
            if (!method.equals("POST")) {
                exchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
                ErrorResponse e = new ErrorResponse("Error: Wrong request method");
                Writer out = new OutputStreamWriter(exchange.getResponseBody());
                gson.toJson(e, out);
                out.close();
            } else {
                Reader in = new InputStreamReader(exchange.getRequestBody());
                LoadRequest lr = gson.fromJson(in, LoadRequest.class);
                InheritResponse ir = service.load(lr);
                if (ir instanceof SuccessResponse) {
                    exchange.sendResponseHeaders(HTTP_OK, 0);
                } else {
                    exchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0); //Changed from HTTP_ACCEPTED
                }
                Writer out = new OutputStreamWriter(exchange.getResponseBody());
                gson.toJson(ir, out);
                out.close();
            }
        }
    }
    // class AddPersonHandler implements HttpHandler {
    //     @Override
    //     public void handle(HttpExchange exchange) throws IOException {

    //         System.out.println("SERVER: /person/add handler");

    //         // add checks for correct method and authorization
    //         String method = exchange.getRequestMethod();
    //         String auth = exchange.getRequestHeaders().getFirst("Authorization");
    //         System.out.println("method: " + method);

    //         Reader in = new InputStreamReader(exchange.getRequestBody());
    //         Person person = gson.fromJson(in, Person.class);

    //         service.addPerson(person);

    //         exchange.sendResponseHeaders(HTTP_OK, -1); // -1 for no body

    //     }
    // }

    // class GetPersonHandler implements HttpHandler {
    //     @Override
    //     public void handle(HttpExchange exchange) throws IOException {

    //         System.out.println("SERVER: /person/get handler");

    //         // add checks for correct method and authorization
    //         String method = exchange.getRequestMethod();
    //         String auth = exchange.getRequestHeaders().getFirst("Authorization");
    //         System.out.println("method: " + method);

    //         Person person = service.getPerson();

    //         exchange.sendResponseHeaders(HTTP_OK, 0);

    //         Writer out = new OutputStreamWriter(exchange.getResponseBody());
    //         gson.toJson(person, out);
    //         out.close();

    //         System.out.println("response body: " + gson.toJson(person));
    //         System.out.println();

    //     }
    // }

}
