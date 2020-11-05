package com.swufe.thirdapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.swufe.thirdapp.R;
import com.swufe.thirdapp.activity.Talk;
import com.swufe.thirdapp.refreshableview.RefreshableView;
import com.swufe.thirdapp.adapter.SlideAdapter;
import com.swufe.thirdapp.slidelistview.SlideListView;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    private SlideListView listView = null;
    private List<String> list=new ArrayList<String>();
//    List<HashMap<String, String>> data = new ArrayList<>();
    private SlideAdapter adapter = null;
    private RefreshableView refreshableView = null;
    private int record;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        list.add("张三");
        list.add("李四");
        list.add("王麻子");
        View view = inflater.inflate(R.layout.news, null);
        listView = (SlideListView) view.findViewById(R.id.list_view);
        adapter = new SlideAdapter(view.getContext(), list);
        listView.setAdapter(adapter);
        refreshableView = (RefreshableView) view.findViewById(R.id.refreshable_view);
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refreshableView.finishRefreshing();
            }
        }, 0);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(view.getContext(),"跳转",Toast.LENGTH_SHORT).show();
                record = i;
                Intent intent = new Intent(view.getContext(), Talk.class);
                intent.putExtra("name", adapter.getItem(i).toString());
                startActivityForResult(intent, 1);
//                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1){
            switch (requestCode) {
                case 1:
                    list.set(record, data.getStringExtra("new_text"));
                    adapter.notifyDataSetChanged();
                    break;
                case 0:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
