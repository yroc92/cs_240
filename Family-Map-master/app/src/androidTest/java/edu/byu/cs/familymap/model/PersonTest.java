package edu.byu.cs.familymap.model;

import android.test.AndroidTestCase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by cole on 4/12/16.
 */
public class PersonTest extends AndroidTestCase {

    public void setUp() throws Exception {
        super.setUp();
        InputStream inputStream = getContext().getResources().openRawResource(
                getContext().getResources().getIdentifier("data",
                        "raw", getContext().getPackageName())
        );
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(dataInputStream));
        StringBuilder stringBuilder = new StringBuilder();

        String input;
        while((input = streamReader.readLine()) != null) {
            stringBuilder.append(input);
        }

        rootSinglePerson = new JSONObject(stringBuilder.toString()).getJSONObject("person");
        rootFamily = new JSONObject(stringBuilder.toString()).getJSONArray("people");
    }

    public void tearDown() throws Exception {

    }

    private JSONObject rootSinglePerson;

    private JSONArray rootFamily;

    private Person person;

    public void testParsePerson() throws Exception {
        person = Person.parsePerson(rootSinglePerson);
        assertEquals(person.getDescendant(), "username");
        assertEquals(person.getPersonId(), "0285s753-qp86-2d8t-e179-9g16165hyo3u");
        assertEquals(person.getFirstName(), "Anthony");
        assertEquals(person.getLastName(), "Dooling");
        assertTrue(person.isMale());
        assertEquals(person.getFatherId(), "7hbh61c0-ou18-x6eg-4bwi-8q18m14yg2b7");
        assertEquals(person.getMotherId(), "6gw47n5f-ejc5-tj80-42l8-1mq0rv5v9tlb");
        assertEquals(person.getSpouseId(), "yn046xe4-0sjo-z8b9-coy8-17257bkq2982");
    }

    public void testFamilyRelations() throws Exception {
        Person son, spouse, father, mother;
        son = Person.parsePerson(rootSinglePerson);
        spouse = Person.parsePerson((JSONObject) rootFamily.get(2));
        father = Person.parsePerson((JSONObject) rootFamily.get(0));
        mother = Person.parsePerson((JSONObject) rootFamily.get(1));
        assertEquals(son.getSpouseId(), spouse.getPersonId());
        assertEquals(son.getFatherId(), father.getPersonId());
        assertEquals(son.getMotherId(), mother.getPersonId());
    }
}