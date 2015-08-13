package com.example.myapplication.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.Myadapter;
import com.example.myapplication.bean.Xiaohau;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.show.api.ShowApiRequest;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
            Gson gson=new Gson();
            try {
                JSONObject jsonobj=new JSONObject(s);
                JSONObject jsonobjcon=jsonobj.getJSONObject("showapi_res_body");
                JSONArray array= jsonobjcon.getJSONArray("contentlist");
                if (array ==null || array.length()==0){
                    Toast.makeText(getActivity(),"你已经把今天的图片看完了亲！",Toast.LENGTH_SHORT).show();
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
                    x.setImg(obj.getString("img"));
                    lists.add(x);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "onSuccess "+lists.toString());
            adapter.notifyDataSetChanged();
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
    private LinearLayoutManager manager;
    int lastitem;
    private static int imgjoke=1;
    private static int textjoke=2;

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
        manager =new LinearLayoutManager(getActivity());
        myrecycleview.setLayoutManager(manager);
        myrecycleview.setItemAnimator(new DefaultItemAnimator());

        adapter =new Myadapter(getActivity(),lists);
        myrecycleview.setAdapter(adapter);
        myrecycleview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastitem + 1 == adapter.getItemCount()) {
                    onRefresh();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastitem = manager.findLastVisibleItemPosition();
            }
        });
        return  v;
    }
    private static int currentpage=0;
    @Override
    public void onRefresh() {
        currentpage++;
        swipeRefreshLayout.setRefreshing(true);
        ShowApiRequest app=   new ShowApiRequest("http://route.showapi.com/341-2","5685","1d9909e9dcbc4e38ad2c439f98bc0291")
                .setResponseHandler(responseHandler)
                .addTextPara("time",getdate())
                .addTextPara("page", currentpage +"");
        app.post();
    }


    public String getdate(){
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }


}
