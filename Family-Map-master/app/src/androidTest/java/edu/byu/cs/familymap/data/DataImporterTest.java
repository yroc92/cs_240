package edu.byu.cs.familymap.data;

import android.test.AndroidTestCase;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import edu.byu.cs.familymap.R;
import edu.byu.cs.familymap.httpclient.Server;
import edu.byu.cs.familymap.model.Model;

/**
 * Created by cole on 4/12/16.
 */
public class DataImporterTest extends AndroidTestCase {

    public void setUp() throws Exception {
        super.setUp();
        dataImporter = new DataImporter();
        server = new Server();
        Model.setEventTypes(new HashSet<String>());

        InputStream inputStream = getContext().getResources().openRawResource(
                R.raw.data);
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(dataInputStream));
        StringBuilder stringBuilder = new StringBuilder();

        String input;
        while((input = streamReader.readLine()) != null) {
            stringBuilder.append(input);
        }

        rootEvents = new JSONObject(stringBuilder.toString()).getJSONObject("events");
    }

    private DataImporter dataImporter;

    private JSONObject rootEvents;

    private Server server;

    public void tearDown() throws Exception {

    }

    public void testGenerateEventTypes() throws Exception {
        Set<String> expectedEventTypes = new HashSet<>();
        expectedEventTypes.add("birth");
        expectedEventTypes.add("death");
        expectedEventTypes.add("christening");
        expectedEventTypes.add("baptism");
        expectedEventTypes.add("census");
        expectedEventTypes.add("marriage");

        Model.setEvents(server.parseEvents(rootEvents));
        Set<String> actualEventTypes = dataImporter.generateEventTypes();
        assertEquals(expectedEventTypes, actualEventTypes);
    }
}