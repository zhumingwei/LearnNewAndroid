package org.myteam.learnnewandroid.CustomRecyclerViewItemAnimation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.myteam.learnnewandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhumingwei on 16/10/26.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder> {
    Context context;
    private List<String> data ;
    public RecyclerViewAdapter (Context context){
        this.context = context;
    }

    public void initData() {
        data = new ArrayList<>();
        for(int i=0 ;i<7;i++){
            data.add("这个是数据"+(i+1));
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_recyclerview_item_animation,null);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {
    TextView tv;
    public MyViewHolder(View itemView) {
        super(itemView);
        tv = (TextView) itemView.findViewById(R.id.textview);
    }
}
