package Entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yudzh_000 on 18.03.2016.
 */
public class Region implements Entity {

    String name;
    int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void put(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("Select * from Region Where name = ?", new String[]{name});
        if (cursor.moveToFirst()) {
            this.id = cursor.getInt(0);
        } else {
            ContentValues values = new ContentValues();
            values.put("name", name);
            db.insert("region", null, values);
            cursor = db.rawQuery("Select * from Region Where name =?", new String[]{name});
            if (cursor.moveToFirst()) {
                this.id = cursor.getInt(0);
            }
        }
    }

    public static Region initFromCursor(Cursor cursor) {
        Region region = new Region();
        region.id = cursor.getInt(0);
        region.name = cursor.getString(1);
        return region;
    }

    public static Region initFromJSON(JSONObject json) throws JSONException {
        Region region = new Region();
        region.name = json.getString("name");
        return region;
    }

    public static Region getByID(SQLiteDatabase db, int id) {
        Cursor cursor = db.rawQuery("Select * from Region Where id = ?", new String[]{Integer.toString(id)});
        cursor.moveToNext();
        return Region.initFromCursor(cursor);
    }

    @Override
    public String toString() {
        return name;
    }
}
