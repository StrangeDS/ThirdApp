package com.swufe.thirdapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.swufe.thirdapp.R;
import com.swufe.thirdapp.activity.Talk;
import com.swufe.thirdapp.adapter.FriendsAdapter;

public class FriendsFragment extends Fragment {
    private ExpandableListView elvCompany = null;
    private TextView tvLoadMore= null;
    private FriendsAdapter adapter = null;
    private View view = null;
    // 群组名称（一级条目内容）
    private String[] group = new String[] { "我的好友", "陌生人"};
    private String[] carsList = new String[] { "张三", "李四", "王五", "赵六"};

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.friends, null);
        tvLoadMore = (TextView) view.findViewById(R.id.tv_load_more);
        elvCompany = (ExpandableListView) view.findViewById(R.id.android_list);
        adapter = new FriendsAdapter(group, carsList, getContext());
        elvCompany.setAdapter(adapter);

        setListeners();

        return view;

    }

    private void setListeners() {
        elvCompany.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return false;
            }
        });
        // 分组关闭
        elvCompany.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        // 子项点击
        elvCompany.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                Toast.makeText(getActivity(), group[groupPosition] + ":" + carsList[childPosition], Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), Talk.class);
                intent.putExtra("name", adapter.getChild(groupPosition, childPosition).toString());
                startActivity(intent);
                return true;
            }
        });

        tvLoadMore.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "没有更多数据了", Toast.LENGTH_SHORT)
                        .show();

            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
