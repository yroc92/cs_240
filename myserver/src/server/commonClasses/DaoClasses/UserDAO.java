package server.commonClasses.DaoClasses;

/**
 * Created by Cory on 3/10/17.
 */
import server.Database;
import server.commonClasses.modelClasses.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {

    private Database db;

    public UserDAO(Database db) {
        this.db = db;
    }

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
//            e.printStackTrace();
            System.err.println(e.getMessage());
            db.rollback();
            throw e;
        }

    }

    public void addArrayOfUsers(ArrayList<User> users) throws SQLException {
        for (User user : users) {
            User newUser = new User(user.getUsername(), user.getPassword(), user.getEmail(), user.getFirstName(),
                    user.getLastName(), user.getGender());
            addUser(newUser);
        }
    }

    public String getUserByPersonId(int id) {
        try {
            String sqlStatement = "SELECT * FROM 'user' WHERE 'personID' = ?";
            PreparedStatement preparedStatement = db.prepare(sqlStatement);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery(sqlStatement);
            preparedStatement.close();
            return rs.getString("userName");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public String getUserByUserName(String userName) {
//        try {
//            String sql = "SELECT * FROM 'user' WHERE 'userName'=? LIMIT 1";
//            PreparedStatement preparedStatement = db.prepare(sql);
//            preparedStatement.setString(1, userName);
//            ResultSet rs = preparedStatement.executeQuery();
//            String result = null;
////            System.out.println(rs.getString(1));
//            //TODO:fix this being null--v
//            if (rs.next()) {
//                result = rs.getString(1);
//            }
//            preparedStatement.close();
//            return result;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public void updateUser(String userName, String columnName, String newValue) {
        try {
            String sqlStatement = "UPDATE 'user' SET ? = ? WHERE 'userName' = ?";
            PreparedStatement statement = db.prepare(sqlStatement);
            statement.setString(1, columnName);
            statement.setString(2, newValue);
            statement.setString(3, userName);
            statement.execute();
            db.commitSqlStatement(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
