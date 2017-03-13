package server;

import server.commonClasses.DaoClasses.UserDAO;

import javax.swing.text.StyleContext;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Cory on 3/11/17.
 */
public class Database {
    static {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }
        catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection conn;
    private UserDAO userDao;

    // Create singleton instance
    private static Database db = new Database();
    // Private constructor for singleton creation
    private Database() {}
    // Return singleton
    public static Database getInstance(){
        return db;
    }

    public void openConnection() {
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

    public void closeConnection(boolean commit) {
        try {
            if (commit) {
                conn.commit();
            }
            else {
                conn.rollback();
            }

            conn.close();
            conn = null;
        }
        catch (SQLException e) {
            System.out.println("closeConnection failed");
            e.printStackTrace();
        }
    }


//    public void createTables() {
//        try {
//            Statement stmt = null;
//            try {
//                stmt = conn.createStatement();
//                stmt.executeUpdate(userDao.createTable());
//            }
//            finally {
//                if (stmt != null) {
//                    stmt.close();
//                    stmt = null;
//                }
//            }
//        }
//        catch (SQLException e) {
//            System.out.println("createTables failed");
//            e.printStackTrace();
//        }
//    }


//    public void addPerson() {}
//    public void addEvent() {}
//    public void authToken() {}

    public void executeSqlStatement(String statement) throws SQLException {
        try {
            openConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(statement);
            closeConnection(true);
        } catch (SQLException e) {
            closeConnection(false);
            throw e;
        }
    }

    //TODO Make Id generator
}
