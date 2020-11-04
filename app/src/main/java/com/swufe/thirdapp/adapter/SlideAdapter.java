package com.swufe.thirdapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.swufe.thirdapp.R;
import com.swufe.thirdapp.slidelistview.SlideItem;

import java.util.List;

public class SlideAdapter extends BaseAdapter{

    private List<String> dataList;
    private Context context;
    private LayoutInflater inflater;
    public SlideAdapter(Context context, List<String> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            View content=inflater.inflate(R.layout.adapter_item_content,null);
            View menu=inflater.inflate(R.layout.adapter_item_menu,null);
            holder=new ViewHolder(content,menu);
            SlideItem slideItem=new SlideItem(context);
            slideItem.setContentView(content,menu);
            convertView=slideItem;
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.username.setText(dataList.get(position));
        holder.itemTvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataList.remove(position);
                SlideAdapter.super.notifyDataSetInvalidated();
                Toast.makeText(context,"删除啦",Toast.LENGTH_SHORT).show();
            }
        });
        holder.itemTvNoRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"这个没做",Toast.LENGTH_SHORT).show();
            }
        });
        holder.itemTvToTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = dataList.get(position);
                dataList.remove(position);
                dataList.add(0, s);
                SlideAdapter.super.notifyDataSetInvalidated();
                Toast.makeText(context,"置顶了熬",Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    class ViewHolder{
        TextView itemTvToTop;
        TextView itemTvNoRead;
        TextView itemTvDelete;
        TextView username;

        public ViewHolder(View center,View menu) {
            this.itemTvToTop = (TextView) menu.findViewById(R.id.item_to_top);
            this.itemTvNoRead = (TextView) menu.findViewById(R.id.item_no_read);
            this.itemTvDelete = (TextView) menu.findViewById(R.id.item_delete);
            this.username = (TextView)center.findViewById(R.id.username);
        }
    }

}