package server.commonClasses.DaoClasses;

/**
 * Created by Cory on 3/10/17.
 */
import server.Database;
import server.commonClasses.modelClasses.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
            db.rollback();
            throw e;
        }

    }

    public void addArrayOfUsers(User[] users) throws SQLException {
        for (User user : users) {
            User newUser = new User(user.getUsername(), user.getPassword(), user.getEmail(), user.getFirstName(),
                    user.getLastName(), user.getToken(), user.getGender());
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
            return rs.getString("username");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getUserByUserName(String username) {
        try {
            String sql = "SELECT * FROM 'user' WHERE 'username'=? LIMIT 1";
            PreparedStatement preparedStatement = db.prepare(sql);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            String result = null;
//            System.out.println(rs.getString(1));
            //TODO:fix this being null--v
            if (rs.next()) {
                result = rs.getString(1);
            }
            preparedStatement.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateUser(String username, String columnName, String newValue) {
        try {
            String sqlStatement = "UPDATE 'user' SET ? = ? WHERE 'username' = ?";
            PreparedStatement statement = db.prepare(sqlStatement);
            statement.setString(1, columnName);
            statement.setString(2, newValue);
            statement.setString(3, username);
            db.commitSqlStatement(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(String username) {
        String sql = "DELETE FROM 'person' WHERE 'descendant' = ?";
        //TODO: implement this
    }





//    public boolean userAlreadyExists(User user) {
//        String sqlStatement = ""
//        return false;
//    }
}
