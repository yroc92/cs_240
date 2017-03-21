package server.commonClasses.DaoClasses;

import server.Database;
import server.commonClasses.modelClasses.AuthToken;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Cory on 3/14/17.
 *
 * This class generates auth tokens for user login authentication.
 */
public class AuthTokenDAO {

    private Database db;

    public AuthTokenDAO(Database db) { this.db = db; }

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

}
