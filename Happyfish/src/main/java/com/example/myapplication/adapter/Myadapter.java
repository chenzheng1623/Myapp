package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.bean.Xiaohau;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by cz on 2015/8/11.
 */
public class Myadapter extends RecyclerView.Adapter<Myadapter.myholder> {
    private Context context;
    private List<Xiaohau> lists;
    private int flag;
    public Myadapter(Context context, List<Xiaohau> lists,int flag){
        this.context=context;
        this.lists=lists;
        this.flag=flag;
    }

    @Override
    public myholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.recy_item,parent,false);
        myholder holder=new myholder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final myholder holder, int position) {
        Xiaohau xiaohu=lists.get(position);
        String time=xiaohu.getTime();
        time=time.substring(0,time.length()-4);
        holder.time.setText(time);
        if (flag==1){
            DisplayImageOptions options=new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.food_u)
                    .showImageOnFail(R.drawable.hotel_u)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();
            ImageLoader.getInstance().displayImage(xiaohu.getImg(),holder.img,options);
        }
        if (flag==2){

        }
        holder.title.setText(xiaohu.getTitle());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class  myholder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView img;
        TextView time;

        public myholder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.title);
            img= (ImageView) itemView.findViewById(R.id.img);
            time= (TextView) itemView.findViewById(R.id.time);
        }

    }


    interface  onlongclickretry{
        void retry();
    }


}
