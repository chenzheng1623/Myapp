package com.example.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.bean.Xiaohau;

import java.util.List;

/**
 * Created by cz on 2015/8/11.
 */
public class Myadapter2 extends RecyclerView.Adapter<Myadapter2.myholder> {
    private Context context;
    private List<Xiaohau> lists;
    public Myadapter2(Context context, List<Xiaohau> lists){
        this.context=context;
        this.lists=lists;
    }

    @Override
    public myholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.text_item,parent,false);
        myholder holder=new myholder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final myholder holder, int position) {
        Xiaohau xiaohu=lists.get(position);
        String time=xiaohu.getTime();
        time=time.substring(0,time.length()-4);
        holder.time.setText(time);
        holder.title.setText(xiaohu.getTitle());
        holder.content.setText(xiaohu.getImg());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class  myholder extends RecyclerView.ViewHolder {
        TextView title;
        TextView content;
        TextView time;

        public myholder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.text_title);
            content= (TextView) itemView.findViewById(R.id.text_content);
            time= (TextView) itemView.findViewById(R.id.text_time);
        }

    }


    interface  onlongclickretry{
        void retry();
    }


}
