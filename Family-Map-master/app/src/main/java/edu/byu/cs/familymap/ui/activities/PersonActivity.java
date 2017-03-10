package edu.byu.cs.familymap.ui.activities;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.IconTextView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;

import java.util.List;
import java.util.zip.Inflater;

import edu.byu.cs.familymap.R;
import edu.byu.cs.familymap.model.Model;
import edu.byu.cs.familymap.model.Person;
import edu.byu.cs.familymap.ui.fragments.PersonInfoFragment;
import edu.byu.cs.familymap.ui.fragments.PersonRecyclerViewFragment;

/**
 * Created by cole on 4/4/16.
 */
public class PersonActivity extends AppCompatActivity {

    private PersonInfoFragment personInfoFragment;

    private PersonRecyclerViewFragment personRecyclerViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fm = this.getSupportFragmentManager();
        personInfoFragment = (PersonInfoFragment) fm.findFragmentById(R.id.personInfoHolder);
        if(personInfoFragment == null) {
            personInfoFragment = PersonInfoFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.personInfoHolder, personInfoFragment)
                    .commit();
        }

        personRecyclerViewFragment = (PersonRecyclerViewFragment)
                fm.findFragmentById(R.id.personRecyclerViewHolder);
        if(personRecyclerViewFragment == null) {
            personRecyclerViewFragment = PersonRecyclerViewFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.personRecyclerViewHolder, personRecyclerViewFragment)
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
