package com.fetch.fetchrewardsapp;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {
private Context context;
private Map<String, List<String>> entrys;
private List<String> groups;
public MyExpandableListAdapter(Context context, Map<String, List<String>> entrys, List<String> groups){
    this.context = context;
    this.entrys = entrys;
    this.groups = groups;
}
    @Override
    public int getGroupCount() {
        return entrys.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return entrys.get(groups.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return groups.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return entrys.get(groups.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
    String groupName = getGroup(i).toString();
    if(view == null){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.group_item, viewGroup, false);
    }
        TextView item = view.findViewById(R.id.listid);
    item.setTypeface(null, Typeface.BOLD);
    item.setText("ListId " + groupName);
    return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
    String name = getChild(i, i1).toString();
    if(view == null){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.child_item, viewGroup, false);
    }
    TextView item = view.findViewById(R.id.entry);
    item.setText("Name: Item " + name + "," + "\t \t \t \t \t ID: " + name);
    return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
