package edu.byu.cs.familymap.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import edu.byu.cs.familymap.R;
import edu.byu.cs.familymap.model.Filter;
import edu.byu.cs.familymap.model.Model;

/**
 * Created by cole on 3/31/16.
 */
public class FilterListFragment extends Fragment {

    private RecyclerView filterRecyclerView;

    private FilterAdapter filterAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter_list, container, false);

        filterRecyclerView = (RecyclerView) view.findViewById(R.id.filter_recycler_view);
        filterRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        filterAdapter = new FilterAdapter();
        filterRecyclerView.setAdapter(filterAdapter);
    }

    private class FilterHolder extends RecyclerView.ViewHolder {
        public FilterHolder(View itemView) {
            super(itemView);
            eventTitleTextView = (TextView) itemView
                    .findViewById(R.id.eventTitle);
            subTitleTextView = (TextView) itemView
                    .findViewById(R.id.eventSubtitle);
            switchButton = (Switch) itemView
                    .findViewById(R.id.eventSwitch);
        }

        private Filter filter;

        private TextView eventTitleTextView;

        private TextView subTitleTextView;

        private Switch switchButton;

        public void bindFilter(final Filter filter) {
            this.filter = filter;
            eventTitleTextView.setText(this.filter.getFilterTitle());
            subTitleTextView.setText(this.filter.getFilterSubTitle());
            switchButton.setChecked(this.filter.isOn());
            switchButton.setShowText(true);

            switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton button, boolean checked) {
                    Model.getFilters().get(getAdapterPosition()).setOn(!filter.isOn());
                }
            });
        }
    }

    private class FilterAdapter extends RecyclerView.Adapter<FilterHolder> {
        @Override
        public FilterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.list_item_filter, parent, false);
            return new FilterHolder(view);
        }

        @Override
        public void onBindViewHolder(FilterHolder holder, int position) {
            Filter filter = Model.getFilters().get(position);
            holder.bindFilter(filter);
        }

        @Override
        public int getItemCount() {
            return Model.getFilters().size();
        }
    }

}
