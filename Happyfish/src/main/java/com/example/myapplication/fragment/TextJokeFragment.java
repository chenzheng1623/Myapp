package com.example.myapplication.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
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

import com.example.myapplication.R;
import com.example.myapplication.adapter.Myadapter2;
import com.example.myapplication.bean.Xiaohau;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.show.api.ShowApiRequest;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
                if (array == null || array.length()==0){
                    swipeRefreshLayout.setRefreshing(false);
                    Snackbar.make(recyclerView,"今天的笑话看完了，亲！",Snackbar.LENGTH_SHORT).show();
                    return;
                }
                Log.i(TAG, "onSuccess " + array.toString());
                Xiaohau x;
                for (int j = 0; j < array.length(); j++) {
                    JSONObject obj=  array.getJSONObject(j);
                    x=new Xiaohau();
                    x.setTime(obj.getString("ct"));
                    x.setTitle(obj.getString("title"));
                    String content=obj.getString("text");
                    content.replace("<p>","  ");
                    content.replace("</p>","\r\n");
                    x.setImg(content);
                    lists.add(x);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // Log.i(TAG, "onSuccess "+lists.toString());
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
    private StaggeredGridLayoutManager manager;
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
        //初始化recycleview
        recyclerView= (RecyclerView)v.findViewById(R.id.recycle);
//      manager =new LinearLayoutManager(getActivity());
//        manager=new GridLayoutManager(getActivity(),2);
        manager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter2 =new Myadapter2(getActivity(),lists);
        recyclerView.setAdapter(adapter2);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastitem +1 == adapter2.getItemCount()){
                    currentpage++;
                    onRefresh();
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//              lastitem=  manager.findLastVisibleItemPosition();
                int a[]=manager.findLastVisibleItemPositions(null);
                Arrays.sort(a);
                lastitem=a[a.length-1];
            }
        });
        return  v;
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }

    private static int currentpage=1;
    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        ShowApiRequest app=   new ShowApiRequest("http://route.showapi.com/341-1","5685","1d9909e9dcbc4e38ad2c439f98bc0291")
                .setResponseHandler(responseHandler)
                .addTextPara("time",getdate())
                .addTextPara("page", currentpage + "");
        app.post();
    }
    public String getdate(){
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

}
