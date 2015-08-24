package com.example.myapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.Myadapter;
import com.example.myapplication.adapter.Myadapter2;
import com.example.myapplication.bean.Xiaohau;
import com.example.myapplication.contrats;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.show.api.ShowApiRequest;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by cz on 2015/8/12.
 */
public class ImageJokeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public static  String TAG="tag";
    final AsyncHttpResponseHandler responseHandler= new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int i, Header[] headers, byte[] bytes) {
            String s=new String(bytes);
            try {
                JSONObject jsonobj=new JSONObject(s);
                JSONObject jsonobjcon=jsonobj.getJSONObject("showapi_res_body");
                JSONArray array= jsonobjcon.getJSONArray("contentlist");
                if (array ==null || array.length()==0){
                    Toast.makeText(getActivity(),"你已经把今天的内容看完了亲！",Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                    return;
                }
                Log.i(TAG, "onSuccess " + array.toString());
                Xiaohau x;
                for (int j = 0; j < array.length(); j++) {
                    JSONObject obj=  array.getJSONObject(j);
                    x=new Xiaohau();
                    x.setTime(obj.getString("ct"));
                    x.setTitle(obj.getString("title"));
                    if (currentflag==2){
                        x.setImg(obj.getString("text"));
                    }else {
                        x.setImg(obj.getString("img"));
                    }
                    lists.add(x);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "onSuccess "+lists.toString());
            if (currentflag==2){
                adapter2.notifyDataSetChanged();
            }else {
                adapter.notifyDataSetChanged();
            }
            swipeRefreshLayout.setRefreshing(false);
        }
        @Override
        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            Log.i(TAG, "onFailure ");
        }
    };
    private RecyclerView myrecycleview;
    private static ImageJokeFragment fragment;
    private List<Xiaohau> lists=new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private Myadapter adapter;
    private Myadapter2 adapter2;
    private StaggeredGridLayoutManager manager;
    int lastitem;
    private int currentflag;

    public static Fragment newinstance(int flag){
        fragment=new ImageJokeFragment();
        Bundle b=new Bundle();
        b.putInt("flag",flag);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Bundle b=getArguments();
        currentflag=b.getInt("flag");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=LayoutInflater.from(getActivity()).inflate(R.layout.image_joke,null,false);
        //初始化下啦刷新控件
        swipeRefreshLayout= (SwipeRefreshLayout)v.findViewById(R.id.refsh);
        swipeRefreshLayout.setOnRefreshListener(this);
        //出事化recycleview
        myrecycleview = (RecyclerView)v.findViewById(R.id.recycle);
//        manager =new LinearLayoutManager(getActivity());
//        manager=new GridLayoutManager(getActivity(),2);
        //瀑布流
        manager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        myrecycleview.setLayoutManager(manager);
        myrecycleview.setItemAnimator(new DefaultItemAnimator());
        if (currentflag==1){
            adapter =new Myadapter(getActivity(),lists,currentflag);
            myrecycleview.setAdapter(adapter);
        }
        if (currentflag==2){
            adapter2 =new Myadapter2(getActivity(),lists);
            myrecycleview.setAdapter(adapter2);
        }
        myrecycleview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.i(TAG, "onScrollStateChanged "+newState);
                //如果滑动停止，显示的最后一项是 list的最后一项就刷新。
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastitem + 1 == adapter.getItemCount()) {
                    //加载下一页
                    currentpage++;
                    onRefresh();
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                lastitem = manager.findLastVisibleItemPosition();
                int a[]=manager.findLastVisibleItemPositions(null);
                Arrays.sort(a);
                lastitem=a[a.length-1];
            }
        });
        return  v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (currentflag==1){
            url=contrats.IMG_JOKE_URL;
        }
        if (currentflag==2){
            url=contrats.TEXT_JOKE_URL;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }

    //页数
    private static int currentpage=1;
    String url="";

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        ShowApiRequest app=   new ShowApiRequest(url,contrats.APPID,contrats.SECERT)
                .setResponseHandler(responseHandler)
                .addTextPara("time",getdate())
                .addTextPara("page", currentpage +"");
        app.post();
    }
    public String getdate(){
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
}
