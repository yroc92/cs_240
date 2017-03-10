package edu.byu.cs.familymap.httpclient;

import android.content.Context;
import android.graphics.PointF;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import edu.byu.cs.familymap.model.Event;
import edu.byu.cs.familymap.model.Login;
import edu.byu.cs.familymap.model.Model;
import edu.byu.cs.familymap.model.Person;

/**
 * Created by cole on 3/17/16.
 */
public class Server {
    public Server() {
        this.context = Model.getMainActivity();
    }

    private Login login;

    private Context context;

    public boolean login() {
        login = Model.getCurrentPerson().getLogin();
        try {
            URL url = new URL(
                    "http",
                    login.getServerHost(),
                    login.getServerPort(),
                    "/user/login"
            );

            Task task = new Task(
                    new Task.TaskListener() {
                        @Override
                        public boolean onFinished(List<String> results) {
                            try {
                                for(String result : results) {
                                    JSONObject root = new JSONObject(result);
                                    Login.parseLogin(login, root);
                                }
                                Model.getMainActivity().getLoginFragment().onLogin(true);
                                return true;
                            }
                            catch(JSONException e) {
                                Log.e("Login Task Finished", e.getMessage(), e);
                                login.setAuthToken(null);
                                login.setPersonId(null);
                                Model.getMainActivity().getLoginFragment().onLogin(false);
                                return false;
                            }
                        }
                    },
                    "POST"
            );

            task.execute(url);
        }
        catch(Exception e) {
            Log.e("Server URL Error", e.getMessage(), e);
            return false;
        }
        return login.getAuthToken() != null && login.getPersonId() != null;
    }

    public void getCurrentPerson() {
        try {
            URL url = new URL(
                    "http",
                    login.getServerHost(),
                    login.getServerPort(),
                    "/person/" + login.getPersonId()
            );

            Task task = new Task(
                    new Task.TaskListener(){
                        @Override
                        public boolean onFinished(List<String> results) {
                            try {
                                for(String result : results) {
                                    JSONObject root = new JSONObject(result);
                                    Model.setCurrentPerson(Person.parsePerson(root));
                                }
                                getPeople(false);
                                return true;
                            }
                            catch (JSONException e) {
                                Log.e("Current Person Task", e.getMessage(), e);
                                return false;
                            }
                        }
                    },
                    "GET"
            );

            task.execute(url);
        }
        catch(MalformedURLException e) {
            Log.e("Server URL Error", e.getMessage(), e);
        }
    }

    public void getPeople(final boolean isResync) {
        try {
            URL url = new URL(
                    "http",
                    login.getServerHost(),
                    login.getServerPort(),
                    "/person/"
            );

            Task task = new Task(
                    new Task.TaskListener() {
                        @Override
                        public boolean onFinished(List<String> results) {
                            try {
                                for (String result : results) {
                                    JSONObject root = new JSONObject(result);
                                    Model.setPeople(parsePeople(root));
                                }
                                if(isResync) {
                                    Model.getSettings().resyncSucceeded();
                                }
                                else {
                                    Model.getMainActivity().onPeopleLoaded();
                                }
                                return true;
                            } catch (JSONException e) {
                                Log.e("People Task Finished", e.getMessage(), e);
                                if(isResync) {
                                    Model.getSettings().resyncFailed();
                                }
                                return false;
                            }
                        }
                    },
                    "GET"
            );

            task.execute(url);
        }
        catch(Exception e) {
            Log.e("Server URL Error", e.getMessage(), e);
            if(isResync) {
                Model.getSettings().resyncFailed();
            }
        }
    }

    public void getEvents(final boolean isResync) {
        try {
            URL url = new URL(
                    "http",
                    login.getServerHost(),
                    login.getServerPort(),
                    "/event/"
            );

            Task task = new Task(
                    new Task.TaskListener() {
                        @Override
                        public boolean onFinished(List<String> results) {
                            try {
                                for(String result : results) {
                                    JSONObject root = new JSONObject(result);
                                    Model.setEvents(parseEvents(root));
                                }
                                if(!isResync) {
                                    Model.getMainActivity().onEventsLoaded();
                                }
                                return true;
                            }
                            catch(JSONException e) {
                                Log.e("Events Task", e.getMessage(), e);
                                if(isResync) {
                                    Model.getSettings().resyncFailed();
                                }
                                return false;
                            }
                        }
                    },
                    "GET"
            );

            task.execute(url);
        }
        catch (Exception e) {
            Log.e("Server URL Error", e.getMessage(), e);
            if(isResync) {
                Model.getSettings().resyncFailed();
            }
        }
    }

    private Map<String, Person> parsePeople(JSONObject root) throws JSONException {
        JSONArray data = root.getJSONArray("data");

        Map<String, Person> people = new HashMap<>(data.length());

        for(int index = 0; index < data.length(); index++) {
            Person person = Person.parsePerson(data.getJSONObject(index));
            people.put(person.getPersonId(), person);
        }

        return people;
    }

    public Map<String, Event> parseEvents(JSONObject root) throws JSONException {
        JSONArray data = root.getJSONArray("data");

        Map<String, Event> events = new HashMap<>(data.length());

        for(int index = 0; index < data.length(); index++) {
            Event event = Event.parseEvent(data.getJSONObject(index));
            events.put(event.getEventId(), event);
        }

        return events;
    }

    public void setLogin(Login login) {
        this.login = login;
    }
}
