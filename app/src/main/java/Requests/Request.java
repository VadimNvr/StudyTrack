package Requests;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import Entities.Entity;
import Services.DataBaseManager.DataBaseDriver;
import Services.LocalDataBaseManager.LocalDataBaseDriver;

/**
 * Created by yudzh_000 on 17.03.2016.
 */
public abstract class Request<T extends Entity> extends AsyncTask<Void,Void,List<T>> {
    LocalDataBaseDriver localDb;
    DataBaseDriver db;

    public Request(AppCompatActivity activity) {
        this.localDb = new LocalDataBaseDriver(activity);
        this.db = new DataBaseDriver();
    }

    public Request(Context context) {
        this.localDb = new LocalDataBaseDriver(context);
        this.db = new DataBaseDriver();
    }

    public void saveToLocal(List<T> entities) {
        StorageData<T> saver = new StorageData<>(localDb);
        saver.execute(entities);
    }


    protected class StorageData <T extends Entity> extends AsyncTask<List<T>,Void,Void> {

        LocalDataBaseDriver db;

        public StorageData(LocalDataBaseDriver db) {
            this.db = db;
        }


        @Override
        protected Void doInBackground(List<T>... params) {
            for (T param : params[0]) {
                localDb.saveData(param);
            }
            return null;
        }
    }
}


