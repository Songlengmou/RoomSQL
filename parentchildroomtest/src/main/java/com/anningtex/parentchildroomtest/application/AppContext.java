package com.anningtex.parentchildroomtest.application;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.anningtex.parentchildroomtest.db.AppDatabase;

/**
 * @author Administrator
 */
public class AppContext extends Application {
    private static Context mContext;
    @SuppressLint("StaticFieldLeak")
    private static AppContext mInstance;
    private AppDatabase appDB;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mInstance = this;
        appDB = Room.databaseBuilder(this, AppDatabase.class, "CsTest")
                .addMigrations()
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    public static Context getContext() {
        return mContext;
    }

    public static AppContext getInstance() {
        return mInstance;
    }

    public AppDatabase getAppDB() {
        return appDB;
    }
}