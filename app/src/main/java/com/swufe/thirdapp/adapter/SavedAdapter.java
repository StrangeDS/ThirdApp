package com.swufe.thirdapp.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.swufe.thirdapp.R;
import com.swufe.thirdapp.avatar.Avatar;
import com.swufe.thirdapp.slidelistview.SlideItem;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SavedAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<HashMap<String, String>> list;
    private int layout;
    public SavedAdapter(Context context,int R, ArrayList<HashMap<String, String>> dataList) {
        this.context = context;
        this.list = dataList;
        this.layout = R;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        HashMap<String, String> item = list.get(position);

        if (convertView==null){
            convertView  = View.inflate(context, layout, null);
            holder = new ViewHolder();
            holder.username = (TextView)convertView.findViewById(R.id.username);
            holder.password = (TextView)convertView.findViewById(R.id.password);
            holder.delete = (Button) convertView.findViewById(R.id.delete);
            holder.roundedImageView = (RoundedImageView) convertView.findViewById(R.id.avatar);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.username.setText(item.get("username"));
        holder.password.setText(item.get("password"));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.remove(position);
                SavedAdapter.super.notifyDataSetChanged();
            }
        });
        Avatar avatar = null;
        if(item.get("avatar") == null){
            avatar = new Avatar("1023221456");
        }
        else {
            avatar = new Avatar(item.get("avatar"));
        }
        holder.roundedImageView.setImageBitmap(avatar.getBitmap());
        return convertView;
    }

    class ViewHolder{
        TextView username;
        TextView password;
        Button delete;
        RoundedImageView roundedImageView;
    }
}
