package com.swufe.thirdapp.friendsadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.swufe.thirdapp.R;

public class FriendsAdapter extends BaseExpandableListAdapter {
    private String[] group;
    private String[] buddy;
    private Context context;
    private LayoutInflater inflater;

    public FriendsAdapter(String[] group, String[] buddy, Context context) {
        super();
        this.group = group;
        this.buddy = buddy;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return group.length;
    }

    @Override
    public int getChildrenCount(int i) {
        return buddy.length;
    }

    @Override
    public Object getGroup(int i) {
        return group[i];
    }

    @Override
    public Object getChild(int i, int i1) {
        return buddy[i1];
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
        view = inflater.inflate(R.layout.group, null);
        TextView groupNameTextView = (TextView) view.findViewById(R.id.tv_group);
        ImageView ivSelector = (ImageView) view.findViewById(R.id.iv_selector);
        groupNameTextView.setText(getGroup(i).toString());
//        ivSelector.setImageResource(R.drawable.spanner_off);
            // 更换展开分组图片
        if (!b) {
//            ivSelector.setImageResource(R.drawable.spanner_on);
        }
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.child, null);
        TextView nickTextView = (TextView) view.findViewById(R.id.username);
        nickTextView.setText(getChild(i, i1).toString());
        return view;
    }
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
