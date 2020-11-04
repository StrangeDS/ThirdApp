package com.swufe.thirdapp.main;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Main extends ListView {
    private SimpleAdapter adapter;

    public Main(Context context) {
        super(context);
    }

    public Main(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == 1) {
            int i = (int) ev.getX();//距离边框的距离
            int j = (int) ev.getRawX();// 距离屏幕的距离
            Log.i("TAG", "i:"+ i);
            Log.i("TAG", "j："+ j);
            Log.i("TAG", "getright()："+ j);
            if (i > 780 & i > j - 820) {
                Log.i("TAG", "有了！");
                ev.setAction(MotionEvent.ACTION_CANCEL);
//                adapter.
                return true;
            }
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
    }
}
