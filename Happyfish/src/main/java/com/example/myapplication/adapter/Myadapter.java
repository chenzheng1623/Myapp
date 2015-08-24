package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.bean.Xiaohau;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by cz on 2015/8/11.
 */
public class Myadapter extends RecyclerView.Adapter<Myadapter.myholder> {
    private Context context;
    private List<Xiaohau> lists;
    private int flag;
    DisplayImageOptions options;
    private LruCache<String ,BitmapDrawable> bitmaplruvache;

    public Myadapter(Context context, List<Xiaohau> lists,int flag){
        this.context=context;
        this.lists=lists;
        this.flag=flag;
        options =new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.food_u)
                .showImageOnFail(R.drawable.hotel_u)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int caschesize = maxMemory/8;
        bitmaplruvache = new LruCache<String ,BitmapDrawable>(caschesize){
            @Override
            protected int sizeOf(String key, BitmapDrawable value) {
                return value.getBitmap().getByteCount();
            }
        };
    }

    @Override
    public myholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.img_item,parent,false);
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
        //  ImageLoader.getInstance().displayImage(xiaohu.getImg(),holder.img,options);
        String url = xiaohu.getImg();
        BitmapDrawable bitmapDrawable=getbitmapfromcache(url);
        if (bitmapDrawable==null){
            new ImageTask(holder.img).execute(url);
        }else {
            holder.img.setImageDrawable(bitmapDrawable);
        }
    }
    @Override
    public int getItemCount() {
        return lists.size();
    }

    BitmapDrawable getbitmapfromcache(String key){
        return  bitmaplruvache.get(key);
    }

    class  ImageTask extends AsyncTask<String,Void,BitmapDrawable>{
        ImageView imageview;
        ImageTask(ImageView imageview){
            this.imageview=imageview;
        }
        @Override
        protected BitmapDrawable doInBackground(String... params) {
            return  getimg(params[0]);
        }

        @Override
        protected void onPostExecute(BitmapDrawable bitmapDrawable) {
            if (bitmapDrawable !=null && imageview !=null){
                imageview.setImageDrawable(bitmapDrawable);
            }
        }
    }

    BitmapDrawable getimg(String url){
        Bitmap bitmap = null;
        try {
            URL httpurl=new URL(url);
            HttpURLConnection conn= (HttpURLConnection) httpurl.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(3000);
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            conn.setDefaultUseCaches(false);
            if (conn.getResponseCode() == 200){
                bitmap= BitmapFactory.decodeStream(conn.getInputStream());
            }
            conn.disconnect();
            BitmapDrawable drawable=new BitmapDrawable(context.getResources(),bitmap);
            //将图片放入缓存。
            if (bitmaplruvache.get(url)==null){
                bitmaplruvache.put(url, drawable);
            }
            return drawable;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        }
        return  null;
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



}
