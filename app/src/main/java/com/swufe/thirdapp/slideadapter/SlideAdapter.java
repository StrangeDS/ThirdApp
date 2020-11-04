package com.swufe.thirdapp.slideadapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.swufe.thirdapp.R;
import com.swufe.thirdapp.Talk;
import com.swufe.thirdapp.slidelistview.SlideItem;

import java.util.List;
import java.util.Map;

public class SlideAdapter extends BaseAdapter implements View.OnClickListener{

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
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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
        holder.itemTvDelete.setOnClickListener(this);
        holder.itemTvNoRead.setOnClickListener(this);
        holder.itemTvToTop.setOnClickListener(this);
        return convertView;
    }

    class ViewHolder{
        TextView itemTvToTop;
        TextView itemTvNoRead;
        TextView itemTvDelete;

        public ViewHolder(View center,View menu) {
            this.itemTvToTop = (TextView) menu.findViewById(R.id.item_to_top);
            this.itemTvNoRead = (TextView) menu.findViewById(R.id.item_no_read);
            this.itemTvDelete = (TextView) menu.findViewById(R.id.item_delete);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.to_talk:
//                Intent intent = new Intent(Talk);
            case R.id.item_no_read:
                Toast.makeText(context,"标为未读",Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_to_top:
                Toast.makeText(context,"置顶了熬",Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_delete:
                Toast.makeText(context,"删除啦",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}