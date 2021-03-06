package com.example.myapplication;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class SettingActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setContentView(R.layout.activity_setting);
        if (hasHeaders()){
            Button b=new Button(this);
            b.setText("设置操作");
            setListFooter(b);
        }
    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        super.onBuildHeaders(target);
        loadHeadersFromResource(R.xml.preference_headers,target);
    }

    public static  String TAG="tag";
    @Override
    protected boolean isValidFragment(String fragmentName) {
        return  true;
    }

    public  static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener
            =new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            if (preference instanceof ListPreference){
                ListPreference listpreference = (ListPreference) preference;
                Log.i(TAG, "onPreferenceChange "+newValue.toString());
                int index=listpreference.findIndexOfValue(newValue.toString());
                Log.i(TAG, "onPreferenceChange "+index);
                preference.setSummary(listpreference.getEntries()[index]);
            }

            return true;
        }
    };
    public static  class  prefs1Fragment extends PreferenceFragment{

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference);
            bindPreferenceSummaryToValue(findPreference("gender"));
            bindPreferenceSummaryToValue(findPreference("ring_key"));
            bindPreferenceSummaryToValue(findPreference("name"));
            bindPreferenceSummaryToValue(findPreference("autoSave"));
        }

    }
    private static void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

//            sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
//                    PreferenceManager.getDefaultSharedPreferences(preference.getContext())
//                    .getString(preference.getKey(),"cz"));
    }

    public static class prefs2Fragment extends  PreferenceFragment{
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.display_prefs);
            String website= getArguments().getString("website");
            Toast.makeText(getActivity(),"网站域名："+website,Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setting, menu);
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
}
