package edu.byu.cs.familymap.ui.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

import edu.byu.cs.familymap.R;
import edu.byu.cs.familymap.model.Model;
import edu.byu.cs.familymap.ui.activities.MapActivity;
import edu.byu.cs.familymap.ui.activities.PersonActivity;

/**
 * Created by cole on 4/6/16.
 */
public class ExpandableAdapter extends ExpandableRecyclerAdapter<ParentViewHolder, ChildViewHolder> {
    public ExpandableAdapter(Context context, List<ParentObject> parentObjects) {
        super(context, parentObjects);
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    private Context context;

    private LayoutInflater inflater;

    @Override
    public void onBindParentViewHolder(ParentViewHolder holder, int position,
                                       Object abstractParentObject) {
        AbstractParentObject parentObject = (AbstractParentObject) abstractParentObject;
        holder.title.setText(parentObject.getParentTitleText());
    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder childViewHolder, int position,
                                      final Object parentListItem) {
        assert parentListItem.getClass().equals(AbstractChildListItem.class);
        final AbstractChildListItem parentChildListItem = (AbstractChildListItem) parentListItem;

        childViewHolder.title.setText(parentChildListItem.getTitle());
        childViewHolder.subTitle.setText(parentChildListItem.getSubTitle());

        if(parentChildListItem.getClass().equals(EventChildListItem.class)) {
            childViewHolder.iconTextView.setText("{fa-map-marker}");
            childViewHolder.setOnClickListener(parentChildListItem,
                    new OnChildListItemClickListener() {
                        @Override
                        public void onChildListItemClick(AbstractChildListItem childListItem) {
                            Intent mapActivityIntent = new Intent(context, MapActivity.class);
                            mapActivityIntent.putExtra("SELECTED_EVENT", ((AbstractChildListItem) parentListItem).getId());
                            context.startActivity(mapActivityIntent);
                            Log.i("EventClickListener", "It's me!!");
                        }
                    });
        }
        else if(parentChildListItem.getClass().equals(PersonChildListItem.class)) {
            final PersonChildListItem personChildListItem = (PersonChildListItem) parentListItem;
            if(personChildListItem.gender) {
                childViewHolder.iconTextView.setText("{fa-male}");
                childViewHolder.iconTextView.setTextColor(context.getResources().getColor(R.color.male));
            }
            else {
                childViewHolder.iconTextView.setText("{fa-female}");
                childViewHolder.iconTextView.setTextColor(context.getResources().getColor(R.color.female));
            }
            childViewHolder.setOnClickListener(parentChildListItem,
                new OnChildListItemClickListener() {
                    @Override
                    public void onChildListItemClick(AbstractChildListItem childListItem) {
                        Model.setFocusedPerson(
                                Model.getPeople().get(personChildListItem.getId())
                        );
                        Model.getFocusedPerson().generateFamily();
                        Model.getFocusedPerson().generateEvents();
                        context.startActivity(new Intent(context, PersonActivity.class));
                        Log.i("PersonClickListener", "Consider me clicked");
                    }
            });
        }
    }

    @Override
    public ParentViewHolder onCreateParentViewHolder(final ViewGroup parentViewGroup) {
        View view = inflater.inflate(R.layout.list_item_parent, parentViewGroup, false);
        return new ParentViewHolder(view);
    }

    @Override
    public ChildViewHolder onCreateChildViewHolder(final ViewGroup childViewGroup) {
        View view = inflater.inflate(R.layout.list_item_child, childViewGroup, false);
        return new ChildViewHolder(view);
    }
}
