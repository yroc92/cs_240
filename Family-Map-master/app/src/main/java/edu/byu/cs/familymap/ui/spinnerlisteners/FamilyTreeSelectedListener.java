package edu.byu.cs.familymap.ui.spinnerlisteners;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import edu.byu.cs.familymap.model.Model;

/**
 * Created by cole on 4/11/16.
 */
public class FamilyTreeSelectedListener implements AdapterView.OnItemSelectedListener {
    public FamilyTreeSelectedListener(Spinner familyTreeSpinner) {
        this.familyTreeSpinner = familyTreeSpinner;
    }

    private Spinner familyTreeSpinner;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Model.getSettings().setCurrentFamilyTreeLineColor(
                Model.getSettings().getLineColorIntegerValues().get(
                        position
                )
        );
        familyTreeSpinner.setSelection(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }
}
