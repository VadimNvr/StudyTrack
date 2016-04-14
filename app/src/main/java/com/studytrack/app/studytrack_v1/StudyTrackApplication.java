package com.studytrack.app.studytrack_v1;

import android.app.Application;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;

import java.util.concurrent.ExecutionException;

import Services.LocalDataBaseManager.DBHelper;

/**
 * Created by vadim on 31.01.16.
 */
public class StudyTrackApplication extends Application {
    public SQLiteDatabase database;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //deleteDatabase("mainDB");

        DBHelper dbHelper = new DBHelper(this);

        database = dbHelper.getWritableDatabase();

        try {
            dbHelper.loadData();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public SQLiteDatabase getDB() {
        return database;
    }
}
