package Entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yudzh_000 on 17.03.2016.
 */
public class Speciality implements  Entity {
    int id;
    String name;
    int price;
    int points;
    String subjects;
    University university;

    public Speciality(){

    }

    public Speciality(String name, int price, int points, String subjects, University university) {
        this.name = name;
        this.price = price;
        this.points = points;
        this.subjects = subjects;
    }

    public static Speciality initFromCursor(Cursor cursor, University university, SQLiteDatabase db) {
        Speciality speciality = new Speciality();
        speciality.university = university;
        speciality.points = cursor.getInt(4);
        speciality.price = cursor.getInt(3);
        speciality.subjects = cursor.getString(5);
        Cursor cursor1 = db.rawQuery("Select name from SpecialityType Where id = ?", new String[]{
                cursor.getString(2)
        });
        cursor1.moveToFirst();
        speciality.name = cursor1.getString(0);

        return speciality;
    }

    public static Speciality initFromJson(JSONObject json, University university) throws JSONException {
        Speciality result = new Speciality();
        result.name = json.getString("name");
        result.price = json.getInt("prise");
        result.points = json.getInt("scores");
        result.subjects = json.getString("subjects");
        result.university = university;
        return result;
    }

    @Override
    public void put(SQLiteDatabase db) {
        String nameID;

        Cursor cursor = db.rawQuery("Select id from SpecialityType where name = ?",new String[]{name});
        cursor.moveToFirst();
        nameID = cursor.getString(0);
        cursor = db.rawQuery("Select * from Speciality Where university_id = ? and type_id = ?",
                new String[]{Integer.toString(university.getId()),nameID});
        if (cursor.moveToFirst()) {
            this.id = cursor.getInt(0);
        } else {
            ContentValues values = new ContentValues();
            values.put("university_id", university.getId());
            values.put("type_id", nameID);
            values.put("price", price);
            values.put("points", points);
            values.put("subjects", subjects);

            db.insert("Speciality", null, values);
            cursor = db.rawQuery("Select * from Speciality Where university_id = ? and type_id = ?",
                    new String[]{Integer.toString(university.getId()),nameID});
            if (cursor.moveToFirst()) {
                this.id = cursor.getInt(0);
            }
        }
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getPoints() {
        return points;
    }

    public String getSubjects() {
        return subjects;
    }
}
