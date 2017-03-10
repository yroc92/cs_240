package edu.byu.cs.familymap.ui.activities;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import edu.byu.cs.familymap.R;
import edu.byu.cs.familymap.model.Filter;
import edu.byu.cs.familymap.ui.fragments.FilterListFragment;

/**
 * Created by cole on 3/31/16.
 */
public class FilterActivity extends AppCompatActivity {

    private FilterListFragment filterListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fm = getSupportFragmentManager();
        filterListFragment = (FilterListFragment) fm.findFragmentById(R.id.filter_list_container);

        if(filterListFragment == null) {
            filterListFragment = new FilterListFragment();
            fm.beginTransaction()
                    .add(R.id.filter_list_container, filterListFragment)
                    .commit();
        }
    }

    @Override
    public void onPause() {
        FragmentTransaction ft = getSupportFragmentManager()
                .beginTransaction();
        ft.remove(filterListFragment);
        ft.commit();
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                //getSupportFragmentManager().popBackStack();
                finish();
                //onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
