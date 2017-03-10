package edu.byu.cs.familymap.ui.spinnerlisteners;

import android.graphics.AvoidXfermode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import edu.byu.cs.familymap.model.Model;

/**
 * Created by cole on 4/11/16.
 */
public class MapTypeSelectedListener implements AdapterView.OnItemSelectedListener {
    public MapTypeSelectedListener(Spinner mapTypeSpinner) {
        this.mapTypeSpinner = mapTypeSpinner;
    }

    private Spinner mapTypeSpinner;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Model.getSettings().setCurrentMapType(
                Model.getSettings().getMapIntegerValues().get(
                        position
                )
        );
        mapTypeSpinner.setSelection(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }
}
