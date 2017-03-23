package main.java.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import org.sqlite.SQLiteConfig;
import main.java.server.commonClasses.DaoClasses.AuthTokenDAO;
import main.java.server.commonClasses.DaoClasses.EventDAO;
import main.java.server.commonClasses.DaoClasses.PersonDAO;
import main.java.server.commonClasses.DaoClasses.UserDAO;
import main.java.server.commonClasses.modelClasses.ResponseMessage;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.*;

/**
 * Created by Cory on 3/11/17.
 */
public class Database {

    public static Connection conn;

    private Gson gson;
    private UserDAO userDao;
    private PersonDAO personDAO;
    private EventDAO eventDAO;
    private AuthTokenDAO authTokenDAO;

    public Database() {
        gson = new Gson();
        Database.init();
        userDao = new UserDAO(this);
        personDAO = new PersonDAO(this);
        eventDAO = new EventDAO(this);
        authTokenDAO = new AuthTokenDAO(this);
    }

    public static void init() {

        if (Database.conn != null) { return; }
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }
        catch(ClassNotFoundException e) {
            e.printStackTrace();
        }

        openConnection();
        createAllTables();
    }

    /**
     * Initialize the connection for the database.
     */
    private static void openConnection() {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:familymap.sqlite";

            SQLiteConfig config = new SQLiteConfig();

            config.setReadOnly(false);
            // Open a database connection
            conn = DriverManager.getConnection(CONNECTION_URL, config.toProperties());

            // Start a transaction
            conn.setAutoCommit(false);
        }
        catch (SQLException e) {
            System.out.println("openConnection failed");
            e.printStackTrace();
        }
    }

    public static void commitSqlStatement() throws SQLException {
        Database.conn.commit();
    }

    /**
     * Commit a prepared statement by closing it.
     * @param statement
     * @throws SQLException
     */
    public void commitSqlStatement(Statement statement) throws SQLException {
        statement.close();
        Database.conn.commit();
    }

    /**
     * Preserve the database in the event of an error being thrown.
     */
    public void rollback() {
        try {
            Database.conn.rollback();
        } catch (SQLException e) {
            System.err.println("Unable to rollback: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Ready a prepared statement.
     * @param sql
     * @return
     */
    public PreparedStatement prepare(String sql) {
        try {
            return Database.conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Creates all the database tables. Happens at initialization.
     */
    public static void createAllTables() {
        try {
            createUserTable();
            createPersonTable();
            createEventTable();
            createAuthTokenTable();
        } catch (Exception e) {
            System.err.println("Could not create all tables");
        }
    }

    public UserDAO getUserDao() { return userDao; }

    public PersonDAO getPersonDAO() {
        return personDAO;
    }

    public EventDAO getEventDAO() { return eventDAO; }

    public AuthTokenDAO getAuthTokenDAO() { return authTokenDAO; }

    /**
     * Create the person table in the database.
     */
    private static void createPersonTable() {
        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("CREATE TABLE IF NOT EXISTS 'person' (")
                .append("'firstName' TEXT NOT NULL, ")
                .append("'lastName' TEXT NOT NULL, ")
                .append("'gender' TEXT NOT NULL, ")
                .append("'descendant' TEXT NOT NULL, ")
                .append("'personID' TEXT NOT NULL, ")
                .append("'fatherID' TEXT, ")
                .append("'motherID' TEXT, ")
                .append("'spouseID' TEXT)");
        try {
            Database.conn.prepareStatement(sqlStatement.toString()).execute();
            commitSqlStatement();
        } catch (SQLException e) {
            System.out.println("Table Person already exists");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Create the user table in the database.
     */
    private static void createUserTable() {
        String sql = "CREATE TABLE IF NOT EXISTS 'user' ('userName' TEXT NOT NULL PRIMARY KEY, 'password' TEXT NOT NULL, " +
                "'email' TEXT NOT NULL, 'firstName' TEXT NOT NULL, 'lastName' TEXT NOT NULL, 'token' TEXT NOT NULL, " +
                "'gender' TEXT NOT NULL, 'personID' TEXT NOT NULL)";
        try {
            Database.conn.prepareStatement(sql).execute();
            commitSqlStatement();
        } catch (SQLException e) {
            System.out.println("Table user already exists");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Create the auth token table in the database.
     */
    private static void createAuthTokenTable() {
        String sql = "CREATE TABLE IF NOT EXISTS 'auth_token' ('token' TEXT PRIMARY KEY, " +
                "'personID' TEXT NOT NULL, 'userName' TEXT NOT NULL, 'creationDate' INTEGER NOT NULL)";
        try {
            Database.conn.prepareStatement(sql).execute();
            commitSqlStatement();
        } catch (SQLException e) {
            System.out.println("Table auth_token already exists");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Create the event table in the database.
     */
    private static void createEventTable() {
        String sql = "CREATE TABLE IF NOT EXISTS 'event' ('eventID' TEXT NOT NULL PRIMARY KEY, 'personID' TEXT NOT NULL, " +
                "'year' INTEGER NOT NULL, 'latitude' REAL NOT NULL, 'longitude' REAL NOT NULL, 'country' TEXT NOT NULL, " +
                "'city' TEXT NOT NULL, 'eventType' TEXT NOT NULL, 'descendant' TEXT NOT NULL)";
        try {
            Database.conn.prepareStatement(sql).execute();
            commitSqlStatement();
        } catch (SQLException e) {
            System.out.println("Table auth_token already exists");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Helper function to send HTTP response strings to the client.
     */
    public void sendResponse(HttpExchange exchange, String bodyMessage, int responseHeader, int contentLength) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(exchange.getResponseBody());
        exchange.sendResponseHeaders(responseHeader, contentLength); // Send response headers
        gson.toJson(new ResponseMessage(bodyMessage), outputStreamWriter); // Write response body with provided string.
        outputStreamWriter.close();
    }
     /**
     * Helper function to send HTTP response objects to the client.
     */
    public void sendResponse(HttpExchange exchange, Object object, int responseHeader, int contentLength) throws IOException {
        OutputStreamWriter outputStream = new OutputStreamWriter(exchange.getResponseBody());
        exchange.sendResponseHeaders(responseHeader, contentLength); // Send response headers
        if (object != null) {
            gson.toJson(object, object.getClass(), outputStream); // Write response body
        }
        outputStream.close();
    }


}
