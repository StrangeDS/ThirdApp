package com.swufe.thirdapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.swufe.thirdapp.MainActivity;
import com.swufe.thirdapp.R;
import com.swufe.thirdapp.Talk;
import com.swufe.thirdapp.refreshableview.RefreshableView;
import com.swufe.thirdapp.slideadapter.SlideAdapter;
import com.swufe.thirdapp.slidelistview.SlideListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainFragment extends Fragment {
    private SlideListView listView = null;
    private List<String> list=new ArrayList<String>();
//    List<HashMap<String, String>> data = new ArrayList<>();
    private SlideAdapter adapter = null;
    private RefreshableView refreshableView = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        list.add("ss1ss");
        list.add("ss2ss");
        list.add("ss3ss");
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
                Intent intent = new Intent(view.getContext(), Talk.class);
//                intent.putExtra("name", adapter.getItem(i).);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
