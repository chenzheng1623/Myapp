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

import com.example.myapplication.R;
import com.example.myapplication.adapter.Myadapter2;
import com.example.myapplication.bean.Xiaohau;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.show.api.ShowApiRequest;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cz on 2015/8/12.
 */
public class TextJokeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public static  String TAG="tag";
    final AsyncHttpResponseHandler responseHandler= new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int i, Header[] headers, byte[] bytes) {
            String s=new String(bytes);
            Log.i(TAG, "onSuccess "+s);
            try {
                JSONObject jsonobj=new JSONObject(s);
                JSONObject jsonobjcon=jsonobj.getJSONObject("showapi_res_body");
                JSONArray array= jsonobjcon.getJSONArray("contentlist");
                Log.i(TAG, "onSuccess " + array.toString());
                Xiaohau x;
                lists.clear();
                for (int j = 0; j < array.length(); j++) {
                    JSONObject obj=  array.getJSONObject(j);
                    x=new Xiaohau();
                    x.setTime(obj.getString("ct"));
                    x.setTitle(obj.getString("title"));
                    x.setImg(obj.getString("text"));
                    lists.add(x);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "onSuccess "+lists.toString());
            adapter2.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }
        @Override
        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            Log.i(TAG, "onFailure ");
        }
    };
    private RecyclerView recyclerView;
    private static TextJokeFragment fragment;
    private List<Xiaohau> lists=new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private Myadapter2 adapter2;
    private LinearLayoutManager manager;
    int lastitem;

    public static Fragment newinstance(){
        if (fragment== null){
            fragment=new TextJokeFragment();
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=LayoutInflater.from(getActivity()).inflate(R.layout.image_joke,null,false);
        //初始化下啦刷新控件
        swipeRefreshLayout= (SwipeRefreshLayout)v.findViewById(R.id.refsh);
        swipeRefreshLayout.setOnRefreshListener(this);
        //出事化recycleview
        recyclerView= (RecyclerView)v.findViewById(R.id.recycle);
        manager =new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter2 =new Myadapter2(getActivity(),lists);
        recyclerView.setAdapter(adapter2);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastitem +1 == adapter2.getItemCount()){
                    onRefresh();
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastitem=  manager.findLastVisibleItemPosition();
            }
        });
        return  v;
    }
   private static int currentpage=1;
    @Override
    public void onRefresh() {
        currentpage++;
        swipeRefreshLayout.setRefreshing(true);
        ShowApiRequest app=   new ShowApiRequest("http://route.showapi.com/341-1","5685","1d9909e9dcbc4e38ad2c439f98bc0291")
                .setResponseHandler(responseHandler)
                .addTextPara("time","2015-08-12")
                .addTextPara("page", "'" + currentpage + "'");
        app.post();
    }
}
