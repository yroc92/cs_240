package edu.byu.cs.familymap.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.familymap.R;
import edu.byu.cs.familymap.model.Model;
import edu.byu.cs.familymap.ui.recyclerview.AbstractParentObject;
import edu.byu.cs.familymap.ui.recyclerview.ExpandableAdapter;

/**
 * Created by cole on 4/6/16.
 */
public class PersonRecyclerViewFragment extends Fragment {

    private RecyclerView personRecyclerView;

    private ExpandableAdapter expandableAdapter;

    private class PersonParentObject extends AbstractParentObject {
        public PersonParentObject() {
            super("Family");
        }
    }

    private class EventParentObject extends AbstractParentObject {
        public EventParentObject() {
            super("Life Events");
        }
    }

    public static PersonRecyclerViewFragment newInstance() {
        return new PersonRecyclerViewFragment();
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        PersonParentObject personParentObject = new PersonParentObject();
        EventParentObject eventParentObject = new EventParentObject();

        personParentObject.setChildObjectList(Model.getFocusedPerson().getFamily());
        eventParentObject.setChildObjectList(Model.getFocusedPerson().getEvents());

        List<ParentObject> parentObjectList = new ArrayList<>();
        parentObjectList.add(personParentObject);
        parentObjectList.add(eventParentObject);

        expandableAdapter = new ExpandableAdapter(getContext(), parentObjectList);
        expandableAdapter.setCustomParentAnimationViewId(R.id.expandArrow);
        expandableAdapter.setParentClickableViewAnimationDefaultDuration();
        expandableAdapter.setParentAndIconExpandOnClick(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_person_recycler_view, container, false);
        personRecyclerView = (RecyclerView) v.findViewById(R.id.personRecyclerView);
        if(personRecyclerView == null) {
            personRecyclerView = new RecyclerView(getContext());
        }
        personRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        personRecyclerView.setAdapter(expandableAdapter);
        return v;
    }
}
