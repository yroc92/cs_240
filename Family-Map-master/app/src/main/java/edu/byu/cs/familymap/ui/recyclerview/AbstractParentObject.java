package edu.byu.cs.familymap.ui.recyclerview;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;

/**
 * Created by cole on 4/6/16.
 */
public abstract class AbstractParentObject implements ParentObject {
    public AbstractParentObject() {}
    public AbstractParentObject(String parentTitleText) {
        this.parentTitleText = parentTitleText;
    }

    private String parentTitleText;

    private List<Object> childrenList;

    @Override
    public List<Object> getChildObjectList() {
        return childrenList;
    }

    @Override
    public void setChildObjectList(List<Object> list) {
        childrenList = list;
    }

    public String getParentTitleText() {
        return parentTitleText;
    }

    public void setParentTitleText(String parentTitleText) {
        this.parentTitleText = parentTitleText;
    }
}
