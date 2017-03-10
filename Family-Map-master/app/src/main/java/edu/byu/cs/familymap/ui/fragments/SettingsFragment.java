package edu.byu.cs.familymap.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import edu.byu.cs.familymap.R;
import edu.byu.cs.familymap.model.Model;
import edu.byu.cs.familymap.model.Settings;
import edu.byu.cs.familymap.ui.activities.MainActivity;
import edu.byu.cs.familymap.ui.spinnerlisteners.FamilyTreeSelectedListener;
import edu.byu.cs.familymap.ui.spinnerlisteners.LifeStoryLinesSelectedListener;
import edu.byu.cs.familymap.ui.spinnerlisteners.MapTypeSelectedListener;
import edu.byu.cs.familymap.ui.spinnerlisteners.SpouseLinesSelectedListener;

/**
 * Created by cole on 4/8/16.
 */
public class SettingsFragment extends Fragment {

    public static SettingsFragment newInstance() {
        SettingsFragment settingsFragment = new SettingsFragment();
        settingsFragment.settings = Model.getSettings();
        return settingsFragment;
    }
    
    private Settings settings;


    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        onCreateViewLogout(v);
        onCreateViewResyncData(v);
        onCreateViewMapType(v);
        onCreateViewSpouseLines(v);
        onCreateViewFamilyTreeLines(v);
        onCreateViewLifeStoryLines(v);
        return v;
    }

    private void onCreateViewLogout(View v) {
        RelativeLayout logout = (RelativeLayout) v.findViewById(R.id.logout);
        logout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        settings.logout();
                        startActivity(new Intent(getActivity(), MainActivity.class));
                    }
                }
        );
    }

    private void onCreateViewResyncData(View v) {
        RelativeLayout resyncData = (RelativeLayout) v.findViewById(R.id.resyncData);
        resyncData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        settings.resyncData();
                    }
                }
        );
    }

    private void onCreateViewMapType(View v) {
        Spinner mapTypeSpinner = (Spinner) v.findViewById(R.id.mapTypeSpinner);
        mapTypeSpinner.setOnItemSelectedListener(new MapTypeSelectedListener(mapTypeSpinner));

        int selectionIndex = settings.getMapIntegerValues().indexOf(
                settings.getCurrentMapType()
        );

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                settings.getMapStringValues()
        );
        dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mapTypeSpinner.setAdapter(dataAdapter);
        mapTypeSpinner.setSelection(selectionIndex);
    }

    private void onCreateViewSpouseLines(View v) {
        Spinner spouseLinesSpinner = (Spinner) v.findViewById(R.id.spouseLinesSpinner);
        spouseLinesSpinner.setOnItemSelectedListener(new SpouseLinesSelectedListener(spouseLinesSpinner));

        int selectionIndex = settings.getLineColorIntegerValues().indexOf(
                settings.getCurrentSpouseLineColor()
        );

        ArrayAdapter<String> colorDataAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                settings.getLineColorStringValues()
        );
        colorDataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spouseLinesSpinner.setAdapter(colorDataAdapter);
        spouseLinesSpinner.setSelection(selectionIndex);

        Switch spouseLinesSwitch = (Switch) v.findViewById(R.id.spouseLinesSwitch);
        spouseLinesSwitch.setChecked(settings.isSpouseLineOn());
        spouseLinesSwitch.setShowText(true);

        spouseLinesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings.setSpouseLineOn(!settings.isSpouseLineOn());
            }
        });
    }

    private void onCreateViewFamilyTreeLines(View v) {
        Spinner familyTreeSpinner = (Spinner) v.findViewById(R.id.familyTreeLinesSpinner);
        familyTreeSpinner.setOnItemSelectedListener(new FamilyTreeSelectedListener(familyTreeSpinner));

        int selectionIndex = settings.getLineColorIntegerValues().indexOf(
                settings.getCurrentFamilyTreeLineColor()
        );

        ArrayAdapter<String> colorDataAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                settings.getLineColorStringValues()
        );
        colorDataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        familyTreeSpinner.setAdapter(colorDataAdapter);
        familyTreeSpinner.setSelection(selectionIndex);

        Switch familyTreeSwitch = (Switch) v.findViewById(R.id.familyTreeLinesSwitch);
        familyTreeSwitch.setChecked(settings.isFamilyTreeLineOn());
        familyTreeSwitch.setShowText(true);

        familyTreeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings.setFamilyTreeLineOn(!settings.isFamilyTreeLineOn());
            }
        });
    }

    private void onCreateViewLifeStoryLines(View v) {
        Spinner lifeStorySpinner = (Spinner) v.findViewById(R.id.lifeStoryLinesSpinner);
        lifeStorySpinner.setOnItemSelectedListener(new LifeStoryLinesSelectedListener(lifeStorySpinner));

        int selectionIndex = settings.getLineColorIntegerValues().indexOf(
                settings.getCurrentLifeStoryLineColor()
        );

        ArrayAdapter<String> colorDataAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                settings.getLineColorStringValues()
        );
        colorDataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        lifeStorySpinner.setAdapter(colorDataAdapter);
        lifeStorySpinner.setSelection(selectionIndex);

        final Switch lifeStorySwitch = (Switch) v.findViewById(R.id.lifeStoryLinesSwitch);
        lifeStorySwitch.setChecked(settings.isLifeStoryLineOn());
        lifeStorySwitch.setShowText(true);

        lifeStorySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings.setLifeStoryLineOn(!settings.isLifeStoryLineOn());
            }
        });
    }

    public void onResyncFailed() {
        Toast resyncFailed = Toast.makeText(
                getContext(),
                "Oops, the resync has failed. Please try again.",
                Toast.LENGTH_SHORT
        );
        resyncFailed.show();
    }

    public void onResyncSucceeded() {
        Toast loadingSuccess = Toast.makeText(
                getActivity(),
                "Successfully resynced with server.",
                Toast.LENGTH_SHORT
        );
        loadingSuccess.show();
        Model.getDataImporter().generateData();
        startActivity(new Intent(getActivity(), MainActivity.class));
    }
}
