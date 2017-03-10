package edu.byu.cs.familymap.ui.spinnerlisteners;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import edu.byu.cs.familymap.model.Model;

/**
 * Created by cole on 4/11/16.
 */
public class LifeStoryLinesSelectedListener  implements AdapterView.OnItemSelectedListener {
    public LifeStoryLinesSelectedListener(Spinner lifeStoryLinesSpinner) {
        this.lifeStoryLinesSpinner = lifeStoryLinesSpinner;
    }

    private Spinner lifeStoryLinesSpinner;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Model.getSettings().setCurrentLifeStoryLineColor(
                Model.getSettings().getLineColorIntegerValues().get(
                        position
                )
        );
        lifeStoryLinesSpinner.setSelection(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }
}
