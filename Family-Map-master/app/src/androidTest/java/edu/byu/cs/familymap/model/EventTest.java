package edu.byu.cs.familymap.model;

import android.test.AndroidTestCase;

import com.google.android.gms.maps.model.LatLng;

import junit.framework.TestCase;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import edu.byu.cs.familymap.R;

/**
 * Created by cole on 4/12/16.
 */
public class EventTest extends AndroidTestCase {

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

        root = new JSONObject(stringBuilder.toString()).getJSONObject("event");
    }

    private JSONObject root;

    private Event event;

    public void tearDown() throws Exception {

    }

    public void testParseEvent() throws Exception {
        event = Event.parseEvent(root);
        assertEquals(event.getEventId(), "f9u4er50-8bdc-iuh8-291u-e3yj1lxocei9");
        assertEquals(event.getPersonId(), "x46767dk-4hbx-l9r9-d50z-691n7g2b165y");
        LatLng position = new LatLng(30.25, -96.25);
        assertEquals(event.getPosition(), position);
        assertEquals(event.getCountry(), "United States");
        assertEquals(event.getCity(), "Austin");
        assertEquals(event.getDescription(), "birth");
        assertEquals(event.getYear(), "1914");
        assertEquals(event.getDescendant(), "username");
    }
}