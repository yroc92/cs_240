package edu.byu.cs.familymap.ui.recyclerview;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.IconTextView;
import android.widget.TextView;

import edu.byu.cs.familymap.R;

/**
 * Created by cole on 4/6/16.
 */
public class ChildViewHolder extends com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder {
    public ChildViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        title = (TextView) itemView.findViewById(R.id.listChildItemTitle);
        subTitle = (TextView) itemView.findViewById(R.id.listChildItemSubTitle);
        iconTextView = (IconTextView) itemView.findViewById(R.id.listChildIcon);
    }

    public TextView title;

    public TextView subTitle;

    public IconTextView iconTextView;

    public View itemView;

    public void setOnClickListener(final AbstractChildListItem object, final OnChildListItemClickListener listener) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onChildListItemClick(object);
            }
        });
    }
}
