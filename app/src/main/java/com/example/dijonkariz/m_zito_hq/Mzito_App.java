package com.example.dijonkariz.m_zito_hq;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

public class Mzito_App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        if(LeakCanary.isInAnalyzerProcess(this))
        {
            return;
        }
        LeakCanary.install(this);
    }
}
