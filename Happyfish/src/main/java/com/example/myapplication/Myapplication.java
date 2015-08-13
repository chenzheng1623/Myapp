package com.example.myapplication;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by cz on 2015/8/11.
 */
public class Myapplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration cong=ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(cong);
    }
}
