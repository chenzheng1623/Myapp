package com.example.myapplication;

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
import android.view.Window;

import com.example.myapplication.fragment.ImageJokeFragment;
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
        listfragment=new ArrayList<>();
        listfragment.add(ImageJokeFragment.newinstance(1));
        listfragment.add(TextJokeFragment.newinstance());
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        return super.onTouchEvent(event);
    }
}
