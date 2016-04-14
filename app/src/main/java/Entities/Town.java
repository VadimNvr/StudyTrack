package Entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONException;
import org.json.JSONObject;

public class Town implements Entity {
    String name;
    int count;
    int id;
    Region region;

    public Town() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public Town(String name) {
        this.name = name;
    }


    public static Town initFromCursor(Cursor cursor, Region region) {
        Town town = new Town();
        town.id = cursor.getInt(0);

        town.name = cursor.getString(2);
        town.region = region;
        town.count = cursor.getInt(3);
        return town;
    }

    public static Town initFromJSON(JSONObject json, Region region) throws JSONException {
        Town town = new Town();
        town.name = json.getString("name");
        town.count = json.getInt("university_count");
        town.region = region;
        return town;
    }


    public static Town getByID(SQLiteDatabase db, int id) {
        Cursor cursor = db.rawQuery("Select * from Town Where id = ?", new String[]{Integer.toString(id)});
        cursor.moveToNext();
        return Town.initFromCursor(cursor, Region.getByID(db,id));
    }

    @Override
    public void put(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("Select * from Town Where name = ?", new String[]{name});
        if (cursor.moveToFirst()) {
            this.id = cursor.getInt(0);
        } else {
            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("count", count);
            values.put("region_id", region.getId());
            db.insert("town", null, values);
            cursor = db.rawQuery("Select * from Town Where name =?", new String[]{name});
            if (cursor.moveToFirst()) {
                this.id = cursor.getInt(0);
            }
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
