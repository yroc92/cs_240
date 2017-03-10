package edu.byu.cs.familymap.ui.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import edu.byu.cs.familymap.R;
import edu.byu.cs.familymap.ui.fragments.SearchFragment;

/**
 * Created by cole on 4/12/16.
 */
public class SearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FragmentManager fm = this.getSupportFragmentManager();
        searchFragment = (SearchFragment) fm.findFragmentById(R.id.searchRecyclerViewHolder);
        if(searchFragment == null) {
            searchFragment = SearchFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.searchRecyclerViewHolder, searchFragment)
                    .commit();
        }
    }

    private SearchFragment searchFragment;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
