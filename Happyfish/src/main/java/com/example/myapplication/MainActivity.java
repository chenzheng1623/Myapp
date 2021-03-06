package com.example.myapplication;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.myapplication.fragment.ImageJokeFragment;
import com.example.myapplication.fragment.KuaidiFragment;
import com.example.myapplication.fragment.TextJokeFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static  String TAG="tag";
    private ViewPager viewPager;
    private Toolbar toolbar;
    private List<String> listtitles;
    private TabLayout tabLayout;
    private List<Fragment>listfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            //设置退出动画
            getWindow().setExitTransition(new Explode());
            getWindow().setEnterTransition(new Explode());
        }
        toolbar= (Toolbar) findViewById(R.id.id_toolbar);
        tabLayout= (TabLayout) findViewById(R.id.tabLayout);
        setSupportActionBar(toolbar);
        listtitles=new ArrayList<>();
        listtitles.add("搞笑图片");
        listtitles.add("笑话");
        listtitles.add("快递查询");
        listfragment=new ArrayList<>();
        listfragment.add(ImageJokeFragment.newinstance(1));
        listfragment.add(TextJokeFragment.newinstance());
        listfragment.add(KuaidiFragment.newInstance());
        viewPager= (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return listfragment.get(i);
            }
            @Override
            public int getCount() {
                return listfragment.size();
            }
            @Override
            public CharSequence getPageTitle(int position) {
                return listtitles.get(position);
            }
        });
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this,SettingActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    boolean isFirst=true;
    long firsttime=0;
    @Override
    public void onBackPressed() {
        if (isFirst){
            firsttime= System.currentTimeMillis();
            Toast.makeText(MainActivity.this,"再按一次退出",Toast.LENGTH_SHORT).show();
            isFirst=false;
        }else {
            long secondtime= System.currentTimeMillis();
            if ((secondtime-firsttime)<2000){
                finish();
            }else {
                firsttime=secondtime;
            }
            isFirst=true;
        }
    }
}
