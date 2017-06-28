package edu.byu.cs240.asyncwebaccess.AsyncTasks;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs240.asyncwebaccess.ModelClasses.AuthToken;
import edu.byu.cs240.asyncwebaccess.ModelClasses.Event;
import edu.byu.cs240.asyncwebaccess.ModelClasses.Person;
import edu.byu.cs240.asyncwebaccess.ServerProxy;
import edu.byu.cs240.asyncwebaccess.Services.FamilyMapDataService;

/**
 * Created by corycooper on 3/27/17.
 */

public class SyncData extends AsyncTask<AuthToken, Void, List<JSONObject>> {

    private String url;
    private FragmentActivity activity;
    private ServerProxy.CallListener listener;

    /**
     * Generic constructor
     */
    public SyncData(ServerProxy.CallListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<JSONObject> doInBackground(AuthToken... params) {
        List<JSONObject> data = new ArrayList<>();
        JSONObject eventResponse = ServerProxy.SERVER_PROXY.sendGet(params[0].getAuthToken(), "/event");
        JSONObject personResponse = ServerProxy.SERVER_PROXY.sendGet(params[0].getAuthToken(), "/person");
        data.add(eventResponse);
        data.add(personResponse);

        return data;
    }

    @Override
    public void onPostExecute(List<JSONObject> data) {
        Gson gson = new Gson();
        Type eventType = new TypeToken<List<Event>>(){}.getType();
        Type personType = new TypeToken<List<Person>>(){}.getType();
        try {
            List<Event> events = gson.fromJson(data.get(0).getString("data"), eventType);
            List<Person> persons = gson.fromJson(data.get(1).getString("data"), personType);
            FamilyMapDataService.FAMILY_MAP_DATA_SERVICE.setEventList(events);
            FamilyMapDataService.FAMILY_MAP_DATA_SERVICE.setPersonList(persons);
            listener.onComplete(null);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}