package Services.LocalDataBaseManager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.studytrack.app.studytrack_v1.StudyTrackApplication;

import java.util.ArrayList;
import java.util.List;

import Entities.Entity;
import Entities.Region;
import Entities.Speciality;
import Entities.SpecialityType;
import Entities.Town;
import Entities.University;

/**
 * Created by yudzh_000 on 17.03.2016.
 */
public class LocalDataBaseDriver {
    public SQLiteDatabase db;

    public LocalDataBaseDriver(AppCompatActivity activity) {
        db = ((StudyTrackApplication) activity.getApplicationContext()).getDB();
    }

    public LocalDataBaseDriver(Context context) {
        db = ((StudyTrackApplication)context).getDB();
    }

    public List<University> loadUniversities(Town town, int count, int offset) {
        ArrayList<University> universities = new ArrayList<>();

        Cursor cursor = db.rawQuery("Select * from University Where town_id = ? LIMIT ? OFFSET ?",
                new String[]{Integer.toString(town.getId()),Integer.toString(count),
                        Integer.toString(offset)});
        while (cursor.moveToNext()) {
            universities.add(University.initFromCursor(cursor,town));
        }
        cursor.close();
        return universities;
    }


    public List<University> loadFilters(String sql, int count, int offset) {
        ArrayList<University> universities = new ArrayList<>();
        Cursor cursorTest = db.rawQuery("Select Town.name from University left join Town on University.TOWN_ID = Town.ID left join Speciality on University.ID = Speciality.UNIVERSITY_ID \n" +
                "                                                                     left join SpecialityType on SpecialityType.ID = Speciality.TYPE_ID Where Town.name IN (\"Кировск\");", null);
        while (cursorTest.moveToNext()){
            String str = "";
            for (int i = 0; i < cursorTest.getColumnCount(); i++) {
                str+= cursorTest.getString(i) + "\t";
            }
            Log.i("TEST", str);
        }

        Cursor cursor = db.rawQuery(sql + " LIMIT ? OFFSET ?",
                new String[]{Integer.toString(count),
                        Integer.toString(offset)});
        while (cursor.moveToNext()) {
            Town town = Town.getByID(db, cursor.getInt(2));
            universities.add(University.initFromCursor(cursor,town));
        }
        cursor.close();
        return universities;
    }

    public List<University> loadLiked() {
        ArrayList<University> universities = new ArrayList<>();
        Cursor cursor = db.rawQuery("Select * from University Where is_favourite = 1", null);
        while (cursor.moveToNext()) {
            universities.add(University.initFromCursor(cursor, Town.getByID(db, cursor.getInt(2))));
        }
        cursor.close();
        return universities;

    }

    public List<Speciality> loadSpecialies(int count, int offset, University university) {
        ArrayList<Speciality> specialities = new ArrayList<>();
        Cursor cursor = db.rawQuery("Select * from Speciality Where university_id = ? LIMIT ? OFFSET ?",
                new String[]{Integer.toString(university.getId()),
                        Integer.toString(count), Integer.toString(offset)});

        while (cursor.moveToNext()) {
            specialities.add(Speciality.initFromCursor(cursor, university, db));
        }
        cursor.close();
        return specialities;
    }

    public List<Town> loadTowns(Region region) {
        ArrayList<Town> towns = new ArrayList<>();
        Cursor cursor = db.rawQuery("Select * from Town Where region_id = ?", new String[]{Integer.toString(region.getId())});
        while (cursor.moveToNext()) {
            towns.add(Town.initFromCursor(cursor, region));
        }
        cursor.close();
        return towns;
    }

    public <T extends Entity> void saveData(T param) {
        param.put(db);
    }

    public List<Region> loadRegions() {
        ArrayList<Region> region = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery("Select * from Region", null);
            while (cursor.moveToNext()) {
                region.add(Region.initFromCursor(cursor));
            }
            cursor.close();
            return region;
        } catch (Exception e) {
            int i =5;
        }
       return null;
    }
    public Town getTownByName(String name) {
        Cursor cursor = db.rawQuery("SELECT * FROM Town WHERE name LIKE ?;", new String[]{name});
        cursor.moveToFirst();
        Town town = Town.initFromCursor(cursor, null);
        cursor.close();
        return town;
    }
    public List<Town> loadTownsWithSpecifickName(String characters) {
        ArrayList<Town> towns = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Town WHERE LOWER(name) LIKE LOWER(?);", new String[]{characters + '%'});
        int i =0;
        while (cursor.moveToNext() && i < 4) {
            towns.add(Town.initFromCursor(cursor, null));
            i++;
        }
        cursor.close();
        return towns;
    }

    public List<SpecialityType> loadSpecialityTypes(int count, int offset) {
        ArrayList<SpecialityType> specialities = new ArrayList<>();
        Cursor cursor = db.rawQuery("Select * from SpecialityType LIMIT ? OFFSET ?",
                new String[]{Integer.toString(count),Integer.toString(offset)});

        while (cursor.moveToNext()) {
            specialities.add(SpecialityType.initFromCursor(cursor));
        }
        cursor.close();
        return specialities;
    }

    public List<SpecialityType> loadSpecialityWithSpecifickName(String characters) {
        ArrayList<SpecialityType> specialities = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM SpecialityType WHERE LOWER(name) LIKE LOWER(?);", new String[]{characters + '%'});
        int i =0;
        while (cursor.moveToNext() && i < 4) {
            specialities.add(SpecialityType.initFromCursor(cursor));
            i++;
        }
        cursor.close();
        return specialities;
    }
}
