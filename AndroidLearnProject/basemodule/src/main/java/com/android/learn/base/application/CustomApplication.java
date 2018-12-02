package com.android.learn.base.application;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.LayoutInflater;

import com.github.moduth.blockcanary.BlockCanary;
import com.github.moduth.blockcanary.BlockCanaryContext;
import com.github.moduth.blockcanary.BuildConfig;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.wind.me.xskinloader.SkinInflaterFactory;
import com.wind.me.xskinloader.SkinManager;


public class CustomApplication extends Application {
    public static ConnectivityManager connectivityManager;
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        connectivityManager= (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        context=this;

        LeakCanary.install(this);
        BlockCanary.install(this, new AppContext()).start();
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(getApplicationContext());

        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE,"");
        MobclickAgent.onEvent(this, "enter","CustomApplication");//前统计的事件ID
//        initSkinLoader();
        SkinInflaterFactory.setFactory(LayoutInflater.from(this));  // for skin change
        SkinManager.get().init(this);
    }

    public class AppContext extends BlockCanaryContext {
        private static final String TAG = "AppContext";

        @Override
        public String provideQualifier() {
            String qualifier = "";
            try {
                PackageInfo info = getApplicationContext().getPackageManager()
                        .getPackageInfo(getApplicationContext().getPackageName(), 0);
                qualifier += info.versionCode + "_" + info.versionName + "_YYB";
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(TAG, "provideQualifier exception", e);
            }
            return qualifier;
        }

        @Override
        public int provideBlockThreshold() {
            return 1000;
        }

        @Override
        public boolean displayNotification() {
            return BuildConfig.DEBUG;
        }

        @Override
        public boolean stopWhenDebugging() {
            return false;
        }
    }
    /**
     * Must call init first
     */
//    private void initSkinLoader() {
//        Settings.createInstance(this);
//        // 初始化皮肤框架
//        SkinChangeHelper.getInstance().init(this);
//        //初始化上次缓存的皮肤
//        SkinChangeHelper.getInstance().refreshSkin(null);
//    }

    }
