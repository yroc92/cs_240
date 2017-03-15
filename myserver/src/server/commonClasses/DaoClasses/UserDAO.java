package server.commonClasses.DaoClasses;

/**
 * Created by Cory on 3/10/17.
 */
import server.Database;
import server.commonClasses.modelClasses.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            insert.setInt(8, newUser.getPersonID());

            insert.execute();
            db.commitSqlStatement(insert);
            insert.close();
        } catch (SQLException e) {
            e.printStackTrace();
            db.rollback();
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


//    public boolean userAlreadyExists(User user) {
//        String sqlStatement = ""
//        return false;
//    }
}
