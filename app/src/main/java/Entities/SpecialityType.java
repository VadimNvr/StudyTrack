package Entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yudzh_000 on 03.04.2016.
 */
public class SpecialityType implements Entity {
    String name;
    int id;
    public SpecialityType(String name) {
        this.name = name;
    }

    @Override
    public void put(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("Select * from SpecialityType Where name = ?", new String[]{name});
        if (cursor.moveToFirst()) {
            this.id = cursor.getInt(0);
        } else {
            ContentValues values = new ContentValues();
            values.put("name", name);
            db.insert("SpecialityType", null, values);
            cursor = db.rawQuery("Select * from SpecialityType Where name =?", new String[]{name});
            if (cursor.moveToFirst()) {
                this.id = cursor.getInt(0);
            }
        }
    }

    public static SpecialityType initFromCursor(Cursor cursor) {
        SpecialityType type = new SpecialityType(cursor.getString(0));
        return type;
    }

    public static SpecialityType initFromJSON(JSONObject json) throws JSONException {
        SpecialityType type = new SpecialityType(json.getString("name"));
        return type;
    }

    @Override
    public String getName() {
        return name;
    }
}
