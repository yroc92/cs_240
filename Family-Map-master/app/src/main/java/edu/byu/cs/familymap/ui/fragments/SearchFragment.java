package edu.byu.cs.familymap.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.byu.cs.familymap.R;
import edu.byu.cs.familymap.model.Event;
import edu.byu.cs.familymap.model.Filter;
import edu.byu.cs.familymap.model.Model;
import edu.byu.cs.familymap.model.Person;
import edu.byu.cs.familymap.ui.activities.MapActivity;
import edu.byu.cs.familymap.ui.activities.PersonActivity;
import edu.byu.cs.familymap.ui.recyclerview.AbstractChildListItem;
import edu.byu.cs.familymap.ui.recyclerview.ChildViewHolder;
import edu.byu.cs.familymap.ui.recyclerview.EventChildListItem;
import edu.byu.cs.familymap.ui.recyclerview.OnChildListItemClickListener;
import edu.byu.cs.familymap.ui.recyclerview.PersonChildListItem;

/**
 * Created by cole on 4/12/16.
 */
public class SearchFragment extends Fragment {
    public static SearchFragment newInstance() {
        SearchFragment searchFragment = new SearchFragment();
        searchFragment.searchResults = new ArrayList<>();
        return searchFragment;
    }

    private RecyclerView searchRecyclerView;

    private EditText searchEditText;

    private List<AbstractChildListItem> searchResults;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchRecyclerView = (RecyclerView) view.findViewById(R.id.searchRecyclerView);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchRecyclerView.setAdapter(new SearchAdapter());

        searchEditText = (EditText) view.findViewById(R.id.searchEditText);

        Button searchButton = (Button) view.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchResults.clear();
                generateSearchResults(searchEditText.getText());
                Log.i("Search Button", "Searching for: " + searchEditText.getText());
                searchRecyclerView.setAdapter(new SearchAdapter());
            }
        });
        return view;
    }

    private void generateSearchResults(CharSequence query) {
        searchPeople(query);
        searchEvents(query);
    }

    private void searchPeople(CharSequence query) {
        for(Map.Entry<String, Person> entry : Model.getPeople().entrySet()) {
            if(entry.getValue().getFullName().toLowerCase().contains(query.toString().toLowerCase())) {
                PersonChildListItem personChildListItem = new PersonChildListItem(
                        entry.getValue().getFullName(),
                        null,
                        entry.getValue().isMale(),
                        entry.getKey()
                );
                searchResults.add(personChildListItem);
            }
        }
    }

    private void searchEvents(CharSequence query) {
        for(Map.Entry<String, Event> entry : Model.getEvents().entrySet()) {
            if(entry.getValue().getDescription().toLowerCase().contains(query.toString().toLowerCase()) ||
                    entry.getValue().getCity().toLowerCase().contains(query.toString().toLowerCase()) ||
                    entry.getValue().getCountry().toLowerCase().contains(query.toString().toLowerCase()) ||
                    entry.getValue().getYear().contains(query.toString())) {
                EventChildListItem eventChildListItem = new EventChildListItem(
                        entry.getValue().getDescription(),
                        entry.getValue().getCity() + ", " + entry.getValue().getCountry(),
                        entry.getValue().getYear(),
                        null,
                        entry.getKey()
                );
                // TODO Apply filters to event
                searchResults.add(eventChildListItem);
            }
        }
    }

    private class SearchAdapter extends RecyclerView.Adapter<ChildViewHolder> {
        @Override
        public ChildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.list_item_child, parent, false);
            return new ChildViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ChildViewHolder holder, int position) {
            final AbstractChildListItem searchResult = searchResults.get(position);
            holder.title.setText(searchResult.getTitle());
            holder.subTitle.setText(searchResult.getSubTitle());
            if(searchResult.getClass().equals(EventChildListItem.class)) {
                holder.iconTextView.setText("{fa-map-marker}");
                holder.iconTextView.setTextColor(
                        getContext().getResources().getColor(R.color.defaultEvent,
                                getContext().getTheme())
                );
                holder.setOnClickListener(searchResult,
                        new OnChildListItemClickListener() {
                            @Override
                            public void onChildListItemClick(AbstractChildListItem childListItem) {
                                Intent mapActivityIntent = new Intent(getContext(), MapActivity.class);
                                mapActivityIntent.putExtra("SELECTED_EVENT", searchResult.getId());
                                getContext().startActivity(mapActivityIntent);
                                Log.i("EventClickListener", "It's me!!");
                            }
                        }
                );
            }
            else if(searchResult.getClass().equals(PersonChildListItem.class)) {
                final PersonChildListItem personSearchResult = (PersonChildListItem) searchResult;
                if(personSearchResult.gender) {
                    holder.iconTextView.setText("{fa-male}");
                    holder.iconTextView.setTextColor(
                            getContext().getResources().getColor(R.color.male,
                                    getContext().getTheme())
                    );
                }
                else {
                    holder.iconTextView.setText("{fa-female}");
                    holder.iconTextView.setTextColor(
                            getContext().getResources().getColor(R.color.female,
                                    getContext().getTheme())
                    );
                }
                holder.setOnClickListener(personSearchResult,
                        new OnChildListItemClickListener() {
                            @Override
                            public void onChildListItemClick(AbstractChildListItem childListItem) {
                                Model.setFocusedPerson(
                                        Model.getPeople().get(personSearchResult.getId())
                                );
                                Model.getFocusedPerson().generateFamily();
                                Model.getFocusedPerson().generateEvents();
                                getContext().startActivity(new Intent(getContext(), PersonActivity.class));
                                Log.i("PersonClickListener", "Consider me clicked");
                            }
                });
            }
        }

        @Override
        public int getItemCount() {
            return searchResults.size();
        }
    }
}
