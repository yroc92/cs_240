package main.java.server.commonClasses.DaoClasses;

import main.java.server.Database;
import main.java.server.commonClasses.modelClasses.AuthToken;
import main.java.server.commonClasses.modelClasses.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Cory on 3/14/17.
 *
 * This class generates auth tokens for user login authentication.
 */
public class AuthTokenDAO {

    private Database db;

    public AuthTokenDAO(Database db) { this.db = db; }

    /**
     * Add an auth token to the database.
     * @param newToken
     */
    public void addAuthToken(AuthToken newToken) {
        try {
            String sqlStatement = "INSERT INTO auth_token values (?, ?, ?, DateTime('now'))";
            PreparedStatement insert = this.db.prepare(sqlStatement);
            insert.setString(1, newToken.getAuthToken());
            insert.setString(2, newToken.getPersonID());
            insert.setString(3, newToken.getUsername());

            insert.execute();
            db.commitSqlStatement(insert);
        } catch (SQLException e) {
            System.err.println("Could not add auth token");
            e.printStackTrace();
            db.rollback();
        }
    }

    /**
     * Returns a user found by its auth token.
     * @param authToken
     * @return
     * @throws SQLException
     */
    public User getUserByAuthToken(String authToken) throws SQLException {
        String findUserSql = "SELECT * FROM user WHERE token = ?";
        PreparedStatement findUserStmt = null;
        ResultSet findUserResults;
        // Search the database for qualifying user.
        try {
            findUserStmt = db.prepare(findUserSql);
            findUserStmt.setString(1, authToken);
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
}
