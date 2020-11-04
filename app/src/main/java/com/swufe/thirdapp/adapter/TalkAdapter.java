package com.swufe.thirdapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.swufe.thirdapp.R;
import com.swufe.thirdapp.avatar.Avatar;

import java.util.ArrayList;
import java.util.HashMap;

public class TalkAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<HashMap<String, String>> list;
    private int R_left;
    private int R_right;

    final private String FROM_ME = "0";// 自己发送出去的消息
    final private String FROM_YOU = "1";// 收到对方的消息

    public TalkAdapter(Context content, ArrayList<HashMap<String, String>> list, int R_left, int R_right){
        super();
        this.context = content;
        this.list = list;
        this.R_left = R_left;
        this.R_right = R_right;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        HashMap<String, String> item = list.get(position);

        if(convertView == null){
            if(item.get("flag").equals(FROM_YOU)){
                convertView  = View.inflate(context, R_left, null);
            }else{
                convertView  =  View.inflate(context, R_right, null);
            }
            holder = new ViewHolder();
            holder.name = (TextView)convertView.findViewById(R.id.name);
            holder.text = (TextView)convertView.findViewById(R.id.text);
            holder.avatar = (RoundedImageView)convertView.findViewById(R.id.avatar);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(item.get("name"));
        holder.text.setText(item.get("text"));
        Avatar avatar = null;
        if(item.get("avatar") == null){
            avatar = new Avatar("1023221456");
        }
        else {
            avatar = new Avatar(item.get("avatar"));
        }
        holder.avatar.setImageBitmap(avatar.getBitmap());
        return convertView;
    }

    static class ViewHolder
    {
        TextView name;
        TextView text;
        RoundedImageView avatar;
    }
}
