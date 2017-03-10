package edu.byu.cs.familymap.ui.spinnerlisteners;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import edu.byu.cs.familymap.model.Model;

/**
 * Created by cole on 4/11/16.
 */
public class SpouseLinesSelectedListener implements AdapterView.OnItemSelectedListener {
    public SpouseLinesSelectedListener(Spinner spouseLinesSpinner) {
        this.spouseLinesSpinner = spouseLinesSpinner;
    }

    private Spinner spouseLinesSpinner;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Model.getSettings().setCurrentSpouseLineColor(
                Model.getSettings().getLineColorIntegerValues().get(
                        position
                )
        );
        spouseLinesSpinner.setSelection(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }
}
