package com.example.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.Myadapter;
import com.example.myapplication.bean.kuaidi;
import com.example.myapplication.contrats;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.show.api.ShowApiRequest;
import com.squareup.okhttp.OkHttpClient;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class KuaidiFragment extends Fragment {

    private static KuaidiFragment mkuaidifragment;

    public synchronized static KuaidiFragment newInstance() {

        if (mkuaidifragment == null) {
            mkuaidifragment = new KuaidiFragment();
        }
        return mkuaidifragment;
    }

    private EditText kuaidihao;
    private Button button;
    private ListView listview;
    private kuadiadapter kuadiadapter;
    private Spinner spinner;
    private List<kuaidi> list = new ArrayList<kuaidi>();
    private Context context;
    final AsyncHttpResponseHandler resHandler = new AsyncHttpResponseHandler() {
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable e) {
            Toast.makeText(getActivity(), "获取失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            try {
                String content = new String(responseBody, "utf-8");
                jsonparse(content);
                Log.i("tag", "response is :" + new String(responseBody, "utf-8"));
                kuadiadapter.notifyDataSetChanged();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    };

    private void jsonparse(String content) {
        try {
            JSONObject jsonObject = new JSONObject(content);
            JSONObject showapi_res_body = jsonObject.getJSONObject("showapi_res_body");
            JSONArray data = showapi_res_body.getJSONArray("data");
            kuaidi k;
            for (int i = 0; i < data.length(); i++) {
                JSONObject jsonObject1 = data.getJSONObject(i);
                String loc = jsonObject1.getString("context");
                String time = jsonObject1.getString("time");
                k = new kuaidi(loc, time);
                list.add(k);
                Log.i("tag", loc + time);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kuaidi, container, false);
        kuaidihao = (EditText) view.findViewById(R.id.kuaidinumber);
        button = (Button) view.findViewById(R.id.chaxun);
        listview = (ListView) view.findViewById(R.id.listview);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        pos = "yuantong";
                        break;
                    case 1:
                        pos = "zhongtong";
                        break;
                    case 2:
                        pos = "shunfeng";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShowApiRequest("http://route.showapi.com/64-19", contrats.APPID, contrats.SECERT)
                        .setResponseHandler(resHandler)
                        .addTextPara("com", pos)
                        .addTextPara("nu", kuaidihao.getText().toString().trim())
                        .post();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, "570806807@qq.com");
                intent.putExtra(Intent.EXTRA_CC, "cc");
                intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
                intent.putExtra(Intent.EXTRA_TEXT, "text");
                context.startActivity(Intent.createChooser(intent, "choose"));
            }
        });

        return view;
    }

    private String pos = null;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] kuaisi = getResources().getStringArray(R.array.kuaidi);
        kuadiadapter = new kuadiadapter();
        listview.setAdapter(kuadiadapter);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    class kuadiadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.kuaidi_item, null);
            TextView loc = (TextView) view.findViewById(R.id.loc);
            TextView time = (TextView) view.findViewById(R.id.time);
            loc.setText(list.get(position).getLoc());
            time.setText(list.get(position).getTime());
            return view;
        }
    }


}
