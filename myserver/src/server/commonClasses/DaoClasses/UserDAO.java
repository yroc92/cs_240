package server.commonClasses.DaoClasses;

/**
 * Created by Cory on 3/10/17.
 */
import server.Database;
import server.commonClasses.modelClasses.User;

import java.sql.SQLException;

public class UserDAO {

    private Database db;

    public UserDAO() {
        // Get database SINGLETON
        db = Database.getInstance();
        createTable();  // Create table if not already there
    }

    public void createTable() {
        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("CREATE TABLE 'user' ('username' TEXT PRIMARY KEY  NOT NULL , 'password' TEXT, 'email' TEXT," +
                            " 'firstName' TEXT, 'lastName' TEXT, 'token' TEXT, 'gender' TEXT, 'personID' TEXT)");
        try {
            db.executeSqlStatement(sqlStatement.toString());
        } catch (Exception e) {
            System.out.println("Could not create User table. Might already exist.");
        }
    }

    public void addUser(User newUser) throws SQLException {
//        StringBuilder sqlStatement = new StringBuilder();
        String sqlSqlStatment = "INSERT INTO user values ('"
                + newUser.getUsername() + "', '" + newUser.getPassword()
                + "', '" + newUser.getEmail() + "', '" + newUser.getFirstName()
                + "', '" + newUser.getLastName() + "', '" + newUser.getToken()
                + "', '" + newUser.getGender() + "', '" + newUser.getPersonID()
                + "')";
        db.executeSqlStatement(sqlSqlStatment);
    }
}
