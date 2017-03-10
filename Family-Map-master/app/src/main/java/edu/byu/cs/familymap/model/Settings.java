package edu.byu.cs.familymap.model;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Polyline;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.familymap.ui.fragments.SettingsFragment;

/**
 * Created by cole on 4/8/16.
 */
public class Settings implements Serializable {
    public Settings() {
        currentMapType = GoogleMap.MAP_TYPE_NORMAL;
        mapStringValues = new ArrayList<>(4);
        mapIntegerValues = new ArrayList<>(4);
        lineColorIntegerValues = new ArrayList<>();
        lineColorStringValues = new ArrayList<>();
        loadMapTypes();
        loadLineColorTypes();
        currentSpouseLineColor = lineColorIntegerValues.get(0);
        spouseLineOn = true;
        currentFamilyTreeLineColor = lineColorIntegerValues.get(1);
        familyTreeLineOn = true;
        currentLifeStoryLineColor = lineColorIntegerValues.get(2);
        lifeStoryLineOn = true;
    }

    private SettingsFragment settingsFragment;

    private int currentMapType;

    private List<String> mapStringValues;

    private List<Integer> mapIntegerValues;

    private int currentSpouseLineColor;

    private boolean spouseLineOn;

    private int currentFamilyTreeLineColor;

    private boolean familyTreeLineOn;

    private int currentLifeStoryLineColor;

    private boolean lifeStoryLineOn;

    private List<String> lineColorStringValues;

    private List<Integer> lineColorIntegerValues;

    public void logout() {
        Model.setCurrentPerson(null);
    }

    public void resyncData() {
        Model.getServer().getEvents(true);
        Model.getServer().getPeople(true);
        Model.getDataImporter().generateData();
    }

    public void resyncFailed() {
        settingsFragment.onResyncFailed();
    }

    public void resyncSucceeded() {
        settingsFragment.onResyncSucceeded();
    }

    private void loadMapTypes() {
        mapStringValues.add(0, "Normal");
        mapIntegerValues.add(0, GoogleMap.MAP_TYPE_NORMAL);

        mapStringValues.add(1, "Hybrid");
        mapIntegerValues.add(1, GoogleMap.MAP_TYPE_HYBRID);

        mapStringValues.add(2, "Satellite");
        mapIntegerValues.add(2, GoogleMap.MAP_TYPE_SATELLITE);

        mapStringValues.add(3, "Terrain");
        mapIntegerValues.add(3, GoogleMap.MAP_TYPE_TERRAIN);
    }

    private void loadLineColorTypes() {
        lineColorStringValues.add(0, "Red");
        lineColorIntegerValues.add(0, Color.RED);

        lineColorStringValues.add(1, "Blue");
        lineColorIntegerValues.add(1, Color.BLUE);

        lineColorStringValues.add(2, "Green");
        lineColorIntegerValues.add(2, Color.GREEN);

        lineColorStringValues.add(3, "Black");
        lineColorIntegerValues.add(3, Color.BLACK);
    }

    public int getCurrentMapType() {
        return currentMapType;
    }

    public void setCurrentMapType(int currentMapType) {
        this.currentMapType = currentMapType;
    }

    public List<String> getLineColorStringValues() {
        return lineColorStringValues;
    }

    public void setLineColorStringValues(List<String> lineColorStringValues) {
        this.lineColorStringValues = lineColorStringValues;
    }

    public int getCurrentFamilyTreeLineColor() {
        return currentFamilyTreeLineColor;
    }

    public void setCurrentFamilyTreeLineColor(int currentFamilyTreeLineColor) {
        this.currentFamilyTreeLineColor = currentFamilyTreeLineColor;
    }

    public int getCurrentLifeStoryLineColor() {
        return currentLifeStoryLineColor;
    }

    public void setCurrentLifeStoryLineColor(int currentLifeStoryLineColor) {
        this.currentLifeStoryLineColor = currentLifeStoryLineColor;
    }

    public List<Integer> getLineColorIntegerValues() {
        return lineColorIntegerValues;

    }

    public void setLineColorIntegerValues(List<Integer> lineColorIntegerValues) {
        this.lineColorIntegerValues = lineColorIntegerValues;
    }

    public SettingsFragment getSettingsFragment() {
        return settingsFragment;
    }

    public void setSettingsFragment(SettingsFragment settingsFragment) {
        this.settingsFragment = settingsFragment;
    }

    public List<String> getMapStringValues() {
        return mapStringValues;
    }

    public void setMapStringValues(List<String> mapStringValues) {
        this.mapStringValues = mapStringValues;
    }

    public int getCurrentSpouseLineColor() {
        return currentSpouseLineColor;
    }

    public boolean isSpouseLineOn() {
        return spouseLineOn;
    }

    public void setSpouseLineOn(boolean spouseLineOn) {
        this.spouseLineOn = spouseLineOn;
    }

    public boolean isFamilyTreeLineOn() {
        return familyTreeLineOn;
    }

    public void setFamilyTreeLineOn(boolean familyTreeLineOn) {
        this.familyTreeLineOn = familyTreeLineOn;
    }

    public boolean isLifeStoryLineOn() {
        return lifeStoryLineOn;
    }

    public void setLifeStoryLineOn(boolean lifeStoryLineOn) {
        this.lifeStoryLineOn = lifeStoryLineOn;
    }

    public void setCurrentSpouseLineColor(int currentSpouseLineColor) {
        this.currentSpouseLineColor = currentSpouseLineColor;
    }

    public List<Integer> getMapIntegerValues() {
        return mapIntegerValues;

    }

    public void setMapIntegerValues(List<Integer> mapIntegerValues) {
        this.mapIntegerValues = mapIntegerValues;
    }
}
