package server.commonClasses.DaoClasses;

import server.Database;
import server.commonClasses.modelClasses.Person;

/**
 * Created by Cory on 3/11/17.
 */
public class PersonDAO {
    public void addPerson(Person person) {

    }

    private Database db;

    public PersonDAO() {
        // Get database SINGLETON
        db = Database.getInstance();
        createTable();  // Create table if not already there
    }

    public void createTable() {
        StringBuilder sqlStatement = new StringBuilder();
        sqlStatement.append("CREATE TABLE 'person' (")
                .append("'firstName' TEXT NOT NULL,")
                .append("'lastName' TEXT NOT NULL,")
                .append("'token' TEXT NOT NULL,")
                .append("'gender' TEXT NOT NULL,")
                .append("'personID' TEXT,")
                .append("'fatherID' TEXT,")
                .append("'motherID' TEXT,")
                .append("'spouseID' TEXT)");
        try {
            db.executeSqlStatement(sqlStatement.toString());
        } catch (Exception e) {}
    }

//    public void addPerson(Person newUser) {
////        StringBuilder sqlStatement = new StringBuilder();
//        String sqlSqlStatment = "INSERT INTO person values ('"
//                + newUser.getUsername() + "', '" + newUser.getPassword()
//                + "', '" + newUser.getEmail() + "', '" + newUser.getFirstName()
//                + "', '" + newUser.getLastName() + "', '" + newUser.getToken()
//                + "', '" + newUser.getGender() + "', '" + newUser.getPersonID()
//                + ")";
//        db.executeSqlStatement(sqlSqlStatment);
//    }
}