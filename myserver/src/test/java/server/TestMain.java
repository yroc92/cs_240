package test.java.server;

import main.java.server.Database;

import java.sql.SQLException;

class TestMain {

    public TestMain() {}

    public static void main(String[] args) {
        Database.init();
//        DAOTest daoTest = new DAOTest();
//        try {
//            daoTest.testAddOneOfEachModelToDatabase();
//            daoTest.testGeneratePerson();
//            daoTest.testGenerateEvents();
//        } catch (SQLException e) {
//            System.err.println(e.getMessage());
//            e.printStackTrace();
//        }
    }


}
