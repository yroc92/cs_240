package edu.byu.cs.familymap.ui.recyclerview;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import edu.byu.cs.familymap.R;

/**
 * Created by cole on 4/6/16.
 */
public class ParentViewHolder extends com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder {
    public ParentViewHolder(View itemView) {
        super(itemView);

        title = (TextView) itemView.findViewById(R.id.listParentTitle);
        icon = (ImageButton) itemView.findViewById(R.id.expandArrow);
    }

    public TextView title;

    public ImageButton icon;
}
