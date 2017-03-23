package main.java.server.services;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
/**
 * Created by Cory on 3/14/17.
 */
public enum GetJson  {

    GET_JSON_SINGLETON;

    private Gson gson;
    private JsonArray fnames;
    private JsonArray mnames;
    private JsonArray locations;
    private JsonArray snames;

    GetJson() {
        init();
    }

    private void init() {
        gson = new Gson();
        String[] paths = {"src/json/fnames.json", "src/json/locations.json", "src/json/mnames.json", "src/json/snames.json"};
        ArrayList<JsonElement> elements = new ArrayList<>();
        for (String path : paths) {
            try {
                FileReader reader = new FileReader(new File(path));
                JsonReader jsonReader = new JsonReader(reader);
                elements.add((JsonElement) gson.fromJson(jsonReader, JsonElement.class));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        fnames = elements.get(0).getAsJsonObject().getAsJsonArray("data");
        locations = elements.get(1).getAsJsonObject().getAsJsonArray("data");
        mnames = elements.get(2).getAsJsonObject().getAsJsonArray("data");
        snames = elements.get(3).getAsJsonObject().getAsJsonArray("data");
    }

    public String getRandomMaleName() {
        Random selector = new Random();
        int n = selector.nextInt(mnames.size() - 1);
        return mnames.get(n).getAsString();
    }

    public JsonObject getRandomLocation() {
        Random selector = new Random();
        int n = selector.nextInt(locations.size() - 1);
        return locations.get(n).getAsJsonObject();
    }

    public String getRandomFemaleName() {
        Random selector = new Random();
        int n = selector.nextInt(fnames.size() - 1);
        return fnames.get(n).getAsString();
    }

    public String getRandomSurname() {
        Random selector = new Random();
        int n = selector.nextInt(snames.size() - 1);
        return snames.get(n).getAsString();
    }

}
