package Entities;

import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;

/**
 * Created by yudzh_000 on 19.03.2016.
 */
public interface Entity extends Serializable {
    void put(SQLiteDatabase db);

    String getName();

}
