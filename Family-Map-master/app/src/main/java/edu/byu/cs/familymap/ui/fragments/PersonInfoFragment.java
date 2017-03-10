package edu.byu.cs.familymap.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import org.w3c.dom.Text;

import edu.byu.cs.familymap.R;
import edu.byu.cs.familymap.model.Model;
import edu.byu.cs.familymap.model.Person;
import edu.byu.cs.familymap.ui.activities.MainActivity;

/**
 * Created by cole on 4/4/16.
 */
public class PersonInfoFragment extends Fragment {

    private Person person;

    public static PersonInfoFragment newInstance() {
        PersonInfoFragment personInfoFragment = new PersonInfoFragment();
        personInfoFragment.setPerson(Model.getFocusedPerson());
        return personInfoFragment;
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        person = Model.getFocusedPerson();
        setHasOptionsMenu(true);
        assert person != null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_person_info, container, false);

        TextView firstName = (TextView) v.findViewById(R.id.firstName);
        TextView lastName = (TextView) v.findViewById(R.id.lastName);
        TextView gender = (TextView) v.findViewById(R.id.gender);

        firstName.setText(person.getFirstName());
        lastName.setText(person.getLastName());
        if(person.isMale()) {
            gender.setText("Male");
        }
        else {
            gender.setText("Female");
        }
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_go_to_top, menu);

        MenuItem goToTopMenuItem = menu.findItem(R.id.goToTopButton);
        goToTopMenuItem.setIcon(
                new IconDrawable(getActivity(), Iconify.IconValue.fa_angle_double_up)
                .actionBarSize()
                .colorRes(R.color.menuItemColor)
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.goToTopButton:
                startActivity(new Intent(getActivity(), MainActivity.class));
        }
        return true;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
