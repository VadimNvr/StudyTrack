package Services.LocalDataBaseManager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;

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
    private SQLiteDatabase db;

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
        return universities;
    }


    public List<University> loadFilters(String sql, int count, int offset) {
        ArrayList<University> universities = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql + " LIMIT ? OFFSET ?",
                new String[]{Integer.toString(count),
                        Integer.toString(offset)});
        while (cursor.moveToNext()) {
            Town town = Town.getByID(db, cursor.getInt(2));
            universities.add(University.initFromCursor(cursor,town));
        }
        return universities;
    }

    public List<University> loadLiked() {
        ArrayList<University> universities = new ArrayList<>();
        Cursor cursor = db.rawQuery("Select * from University Where is_favourite = 1", null);
        while (cursor.moveToNext()) {
            universities.add(University.initFromCursor(cursor, Town.getByID(db, cursor.getInt(2))));
        }
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
        Cursor cursor = db.rawQuery("Select * from Region", null);
        while (cursor.moveToNext()) {
            region.add(Region.initFromCursor(cursor));
        }
        return region;
    }

    public List<Town> loadTownsWithSpecifickName(String characters) {
        ArrayList<Town> towns = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Town WHERE name LIKE ?;", new String[]{characters+'%'});
        while (cursor.moveToNext()) {
            towns.add(Town.initFromCursor(cursor, null));
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
        return specialities;
    }

}
