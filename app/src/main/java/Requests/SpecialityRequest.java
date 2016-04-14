package Requests;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import Entities.Speciality;
import Entities.University;

/**
 * Created by yudzh_000 on 03.04.2016.
 */
public class SpecialityRequest extends Request<Speciality> {
    int count;
    int offset;
    University university;

    public SpecialityRequest(AppCompatActivity activity, int offset, int count, University university) {
        super(activity);
        this.count = count;
        this.offset = offset;
        this.university = university;
    }

    @Override
    protected List<Speciality> doInBackground(Void... params) {
        List<Speciality> result = new ArrayList<>();
        if(university.getLoaded() !=0) {
            result.addAll(this.localDb.loadSpecialies(count, offset, university));
        } else {
            if (result.isEmpty() || result.size() < count) {
                List<Speciality> extra = this.db.loadSpecialies(university, count - result.size(),
                        offset + result.size());
                saveToLocal(extra);
                result.addAll(extra);
            }
        }
        return result;
    }
}
