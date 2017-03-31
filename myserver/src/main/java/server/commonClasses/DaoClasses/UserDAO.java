package main.java.server.commonClasses.DaoClasses;

/**
 * Created by Cory on 3/10/17.
 */
import main.java.server.Database;
import main.java.server.commonClasses.modelClasses.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {

    private Database db;

    public UserDAO(Database db) {
        this.db = db;
    }

    /**
     * Add a new user to the database.
     * @param newUser
     * @throws SQLException
     */
    public void addUser(User newUser) throws SQLException {

        try {
            String sqlStatement = "INSERT INTO user values (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insert = this.db.prepare(sqlStatement);
            insert.setString(1, newUser.getUsername());
            insert.setString(2, newUser.getPassword());
            insert.setString(3, newUser.getEmail());
            insert.setString(4, newUser.getFirstName());
            insert.setString(5, newUser.getLastName());
            insert.setString(6, newUser.getToken());
            insert.setString(7, newUser.getGender());
            insert.setString(8, newUser.getPersonID());

            insert.execute();
            db.commitSqlStatement(insert);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            db.rollback();
            throw e;
        }

    }

    /**
     * Add an array of users to the database.
     * @param users
     * @throws SQLException
     */
    public void addArrayOfUsers(ArrayList<User> users) throws SQLException {
        for (User user : users) {
            User newUser = new User(user.getUsername(), user.getPassword(), user.getEmail(), user.getFirstName(),
                    user.getLastName(), user.getGender());
            addUser(newUser);
        }
    }

    /**
     * Returns a user found by their userName.
     * @param userName
     * @return
     * @throws SQLException
     */
    public User getUserByUserName(String userName) throws SQLException {
        String findUserSql = "SELECT * FROM user WHERE userName = ?";
        PreparedStatement findUserStmt = null;
        ResultSet findUserResults;
        // Search the database for qualifying user.
        try {
            findUserStmt = db.prepare(findUserSql);
            findUserStmt.setString(1, userName);
            findUserResults = findUserStmt.executeQuery();

            // Return the result set object if it is valid.
            if (findUserResults.next()) {
                User foundUser = new User(findUserResults.getString("userName"),
                        findUserResults.getString("password"),
                        findUserResults.getString("email"),
                        findUserResults.getString("firstName"),
                        findUserResults.getString("lastName"),
                        findUserResults.getString("gender"),
                        findUserResults.getString("token"),
                        findUserResults.getString("personID"));
                return foundUser;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Failed to find user, " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (findUserStmt != null) {
                findUserStmt.close();
            }
        }
        return null;
    }

    /**
     * Logs in a user.
     * @param userName
     * @param password
     * @return
     * @throws SQLException
     */
    public User login(String userName, String password) throws SQLException {
        // Initialize variables.
        PreparedStatement loginStmt = null;
        ResultSet loginResults = null;
        // Search the database for qualifying login credentials.
        try {
            String loginSql = "SELECT * FROM user WHERE userName = ? AND password = ?";
            loginStmt = db.prepare(loginSql);
            loginStmt.setString(1, userName);
            loginStmt.setString(2, password);
            loginResults = loginStmt.executeQuery();

            // Return the result set object if it is valid.
            if (loginResults.next()) {
                User foundUser = new User(loginResults.getString("userName"),
                        loginResults.getString("password"),
                        loginResults.getString("email"),
                        loginResults.getString("firstName"),
                        loginResults.getString("lastName"),
                        loginResults.getString("gender"),
                        loginResults.getString("token"),
                        loginResults.getString("personID"));
                return foundUser;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Failed to find userName/password, " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (loginStmt != null) {
                loginStmt.close();
            }
        }

        return null;

    }
}
