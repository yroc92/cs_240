package edu.byu.cs.familymap.ui.activities;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import edu.byu.cs.familymap.R;
import edu.byu.cs.familymap.model.Model;
import edu.byu.cs.familymap.ui.fragments.MapFragment;

/**
 * Created by cole on 4/7/16.
 */
public class MapActivity extends AppCompatActivity {

    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String eventId = getIntent().getExtras().getString("SELECTED_EVENT");
        if(eventId != null) {
            Log.i("MapActivity", "EventId is: " + eventId);
        }

        FragmentManager fm = this.getSupportFragmentManager();

        mapFragment = (MapFragment) fm.findFragmentById(R.id.mapFragmentHolder);

        if(mapFragment == null) {
            mapFragment = MapFragment.newInstance(Model.getEvents().get(eventId));
            fm.beginTransaction()
                    .add(R.id.mapFragmentHolder, mapFragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
