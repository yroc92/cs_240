package test.java.server;

import main.java.server.Database;
import main.java.server.commonClasses.handlerClasses.ClearResource;

class TestMain {

    public TestMain() {}

    public static void main(String[] args) {
        Database.init();
        DAOTest daoTest = new DAOTest();
        Database db = new Database();
        try {
            ClearResource clear = new ClearResource(db);
            clear.publicClear();    // Attempt to clear the database.
            daoTest.testAddOneOfEachModelToDatabase();
            daoTest.testGeneratePerson();
            daoTest.testGenerateEvents();
            daoTest.testGetPersonReturnsPerson();
            clear.publicClear();

            ServicesTest servicesTest = new ServicesTest();

        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }


}
