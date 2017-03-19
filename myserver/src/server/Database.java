package server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import server.commonClasses.DaoClasses.EventDAO;
import server.commonClasses.DaoClasses.PersonDAO;
import server.commonClasses.DaoClasses.UserDAO;
import server.commonClasses.modelClasses.Person;
import server.commonClasses.modelClasses.ResponseMessage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
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
    //TODO: add event dao for creating tables

    public Database() {
        gson = new Gson();
        Database.init();
        userDao = new UserDAO(this);
        personDAO = new PersonDAO(this);
        eventDAO = new EventDAO(this);

    }

    public static void init() {

        if (Database.conn != null) {
            return;
        }

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


    private static void openConnection() {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:familymap.sqlite";

            // Open a database connection
            conn = DriverManager.getConnection(CONNECTION_URL);

            // Start a transaction
            conn.setAutoCommit(false);
        }
        catch (SQLException e) {
            System.out.println("openConnection failed");
            e.printStackTrace();
        }
    }

    /**
     * Gets the last inserted value.
     * @return The generated key (only the most recent), -1 otherwise
     * @throws SQLException
     */
//    public int getGeneratedKey() throws SQLException
//    {
//        PreparedStatement getGeneratedKeys = prepare("SELECT last_insert_rowid()");
//        ResultSet rs = getGeneratedKeys.executeQuery();
//        int key = -1;
//
//        if (rs.next())
//        {
//            key = rs.getInt(1);
//        }
//
//        getGeneratedKeys.close();	// Very important to do when we're done! Otherwise 'SQL logic error or missing database'
//        return key;
//}
    public static void commitSqlStatement() throws SQLException {
        Database.conn.commit();
    }

    public void commitSqlStatement(Statement statement) throws SQLException {
        statement.close();
        Database.conn.commit();
    }

    public void rollback() {
        try {
            Database.conn.rollback();
        } catch (SQLException e) {
            System.err.println("Unable to rollback: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public PreparedStatement prepare(String sql) {
        try {
            return Database.conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public void addPerson() {}
//    public void addEvent() {}
//    public void authToken() {}



//    public void executeSqlStatement(String statement) throws SQLException {
//        Statement stmt = conn.createStatement();
//        commitSqlStatement(stmt);
//
//    }
    //TODO Make Id generator

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

    public UserDAO getUserDao() {
        return userDao;
    }

    public PersonDAO getPersonDAO() {
        return personDAO;
    }

    public EventDAO getEventDAO() { return eventDAO; }

     private static void createPersonTable() {
        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("CREATE TABLE 'person' (")
                .append("'firstName' TEXT NOT NULL, ")
                .append("'lastName' TEXT NOT NULL, ")
                .append("'gender' TEXT NOT NULL, ")
                .append("'descendant' TEXT NOT NULL, ")
                .append("'personID' INTEGER, ")
                .append("'fatherID' INTEGER, ")
                .append("'motherID' INTEGER, ")
                .append("'spouseID' INTEGER)");
        try {
            Database.conn.prepareStatement(sqlStatement.toString()).execute();
            commitSqlStatement();
        } catch (SQLException e) {
            System.out.println("Table Person already exists");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void createUserTable() {
        String sql = "CREATE TABLE 'user' ('username' TEXT NOT NULL PRIMARY KEY, 'password' TEXT NOT NULL, " +
                "'email' TEXT NOT NULL, 'firstName' TEXT NOT NULL, 'lastName' TEXT NOT NULL, 'token' TEXT," +
                "'gender' TEXT NOT NULL, 'personID' INTEGER)";
        try {
            Database.conn.prepareStatement(sql).execute();
            commitSqlStatement();
        } catch (SQLException e) {
            System.out.println("Table user already exists");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void createAuthTokenTable() {
        String sql = "CREATE TABLE 'auth_token' ('username' TEXT NOT NULL, 'token' TEXT PRIMARY KEY)";
        try {
            Database.conn.prepareStatement(sql).execute();
            commitSqlStatement();
        } catch (SQLException e) {
            System.out.println("Table auth_token already exists");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void createEventTable() {
        String sql = "CREATE TABLE 'event' ('eventID' INTEGER NOT NULL PRIMARY KEY, 'personID' TEXT NOT NULL, " +
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

    /*
    Checks the Person table in the database for the highest personID value and returns it.
     */
    public int getMaxPersonID() throws SQLException {
        String sqlStatement = "SELECT MAX(personID) FROM person";
        PreparedStatement getMax = prepare(sqlStatement);
        ResultSet rs = getMax.executeQuery();
        int max = 0;
        if (rs.next()) {
            max = rs.getInt(1);
        }
        getMax.close();
        return max;
    }

    /*
    Helper function to send HTTP response objects to the client.
     */
    public void sendResponse(HttpExchange exchange, String bodyMessage, int responseHeader, int contentLength) throws IOException {
        PrintWriter printWriter = new PrintWriter(exchange.getResponseBody());
        if (bodyMessage != null) {
            gson.toJson(new ResponseMessage("Invalid username parameter"), printWriter); // Write response
        }
        exchange.sendResponseHeaders(responseHeader, contentLength);
        printWriter.close();
    }

}
